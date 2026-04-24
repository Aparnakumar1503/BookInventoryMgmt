package com.sprint.bookinventorymgmt.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprint.bookinventorymgmt.common.ResponseBuilder;
import com.sprint.bookinventorymgmt.usermgmt.entity.User;
import com.sprint.bookinventorymgmt.usermgmt.repository.IUserMgmtRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Loads users from the database and assigns the module authority used to protect endpoints.
     */
    @Bean
    public UserDetailsService userDetailsService(IUserMgmtRepository userRepository) {
        return username -> {
            User user = userRepository.findByUserNameIgnoreCase(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            if (user.getRole() != null && user.getRole().getPermRole() != null) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().getPermRole().toUpperCase()));
            }
            for (String authority : SecurityAuthorities.moduleAuthoritiesFor(user.getUserName())) {
                authorities.add(new SimpleGrantedAuthority(authority));
            }

            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUserName())
                    .password(user.getPassword())
                    .authorities(authorities)
                    .build();
        };
    }

    /**
     * Protects the company-defined endpoint list and keeps login flow simple for presentation.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, ObjectMapper objectMapper) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/error",
                                "/api/v1/auth/login"
                        ).permitAll()
                        .requestMatchers("/api/v1/auth/me", "/api/v1/auth/logout").authenticated()
                        .requestMatchers("/api/v1/books/*/authors/**")
                        .hasAuthority(SecurityAuthorities.OWNER_USER_AUTHOR)
                        .requestMatchers("/api/v1/books/*/reviews/**")
                        .hasAuthority(SecurityAuthorities.OWNER_REVIEW)
                        .requestMatchers("/api/v1/books/**", "/api/v1/categories/**", "/api/v1/publishers/**", "/api/v1/states/**")
                        .hasAuthority(SecurityAuthorities.OWNER_BOOK)
                        .requestMatchers("/api/v1/authors/**", "/api/v1/users/**", "/api/v1/roles/**")
                        .hasAuthority(SecurityAuthorities.OWNER_USER_AUTHOR)
                        .requestMatchers("/api/v1/inventory/**", "/api/v1/book-conditions/**")
                        .hasAuthority(SecurityAuthorities.OWNER_INVENTORY)
                        .requestMatchers("/api/v1/orders/**", "/api/v1/cart/**")
                        .hasAuthority(SecurityAuthorities.OWNER_ORDER)
                        .requestMatchers("/api/v1/reviews/**", "/api/v1/reviewers/**")
                        .hasAuthority(SecurityAuthorities.OWNER_REVIEW)
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            if (request.getRequestURI().startsWith("/api/")) {
                                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                                objectMapper.writeValue(
                                        response.getWriter(),
                                        ResponseBuilder.error(HttpStatus.UNAUTHORIZED.value(),
                                                "Authentication is required to access this resource.")
                                );
                            } else {
                                response.sendError(HttpStatus.UNAUTHORIZED.value(), "Authentication is required.");
                            }
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            if (request.getRequestURI().startsWith("/api/")) {
                                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                                objectMapper.writeValue(
                                        response.getWriter(),
                                        ResponseBuilder.error(HttpStatus.FORBIDDEN.value(),
                                                "You are not allowed to access this resource.")
                                );
                            } else {
                                response.sendError(HttpStatus.FORBIDDEN.value(), "Access is denied.");
                            }
                        })
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .addFilterBefore(new FrontendApiAccessFilter(objectMapper), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /**
     * Encodes and verifies user passwords with BCrypt.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Exposes the authentication manager so the REST login endpoint can create a session.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * Allows the Angular application on port 4200 to call the secured API with the session cookie.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

final class SecurityAuthorities {

    static final String OWNER_USER_AUTHOR = "OWNER_USER_AUTHOR";
    static final String OWNER_BOOK = "OWNER_BOOK";
    static final String OWNER_INVENTORY = "OWNER_INVENTORY";
    static final String OWNER_ORDER = "OWNER_ORDER";
    static final String OWNER_REVIEW = "OWNER_REVIEW";

    private SecurityAuthorities() {
    }

    static List<String> moduleAuthoritiesFor(String username) {
        String normalized = normalize(username);

        return switch (normalized) {
            case "aparna" -> List.of(OWNER_USER_AUTHOR);
            case "moses" -> List.of(OWNER_BOOK);
            case "sobika" -> List.of(OWNER_INVENTORY);
            case "janapriya" -> List.of(OWNER_ORDER);
            case "swarnalatha" -> List.of(OWNER_REVIEW);
            default -> List.of();
        };
    }

    static boolean isOwnerAuthority(String authority) {
        return OWNER_USER_AUTHOR.equals(authority)
                || OWNER_BOOK.equals(authority)
                || OWNER_INVENTORY.equals(authority)
                || OWNER_ORDER.equals(authority)
                || OWNER_REVIEW.equals(authority);
    }

    private static String normalize(String username) {
        return username == null ? "" : username.toLowerCase(Locale.ROOT);
    }
}

final class FrontendApiAccessFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    FrontendApiAccessFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Blocks browser document navigation to REST URLs so API access happens through the frontend application.
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        if (isBlockedBrowserNavigation(request)) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            objectMapper.writeValue(
                    response.getWriter(),
                    ResponseBuilder.error(HttpStatus.FORBIDDEN.value(),
                            "API endpoints must be accessed through the frontend application on localhost:4200.")
            );
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean isBlockedBrowserNavigation(HttpServletRequest request) {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod()) || !request.getRequestURI().startsWith("/api/")) {
            return false;
        }

        String fetchMode = request.getHeader("Sec-Fetch-Mode");
        String fetchDest = request.getHeader("Sec-Fetch-Dest");
        return "navigate".equalsIgnoreCase(fetchMode) || "document".equalsIgnoreCase(fetchDest);
    }
}
