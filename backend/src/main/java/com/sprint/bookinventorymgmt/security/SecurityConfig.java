package com.sprint.bookinventorymgmt.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprint.bookinventorymgmt.common.ResponseBuilder;
import com.sprint.bookinventorymgmt.usermgmt.entity.User;
import com.sprint.bookinventorymgmt.usermgmt.repository.IUserMgmtRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            ObjectMapper objectMapper) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/v1/auth/login",
                                "/api/v1/auth/logout",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).permitAll()
                        .requestMatchers("/api/v1/authors/**", "/api/v1/books/*/authors/**").hasAuthority(SecurityAuthorities.OWNER_USER_AUTHOR)
                        .requestMatchers("/api/v1/users/**", "/api/v1/roles/**").hasAuthority(SecurityAuthorities.OWNER_USER_AUTHOR)
                        .requestMatchers("/api/v1/books/*/reviews/**", "/api/v1/reviews/**", "/api/v1/reviewers/**").hasAuthority(SecurityAuthorities.OWNER_REVIEW)
                        .requestMatchers("/api/v1/books/**", "/api/v1/categories/**", "/api/v1/publishers/**", "/api/v1/states/**").hasAuthority(SecurityAuthorities.OWNER_BOOK)
                        .requestMatchers("/api/v1/inventory/**", "/api/v1/book-conditions/**").hasAuthority(SecurityAuthorities.OWNER_INVENTORY)
                        .requestMatchers("/api/v1/orders/**", "/api/v1/cart/**").hasAuthority(SecurityAuthorities.OWNER_ORDER)
                        .requestMatchers("/api/v1/auth/me").authenticated()
                        .anyRequest().denyAll()
                )
                .exceptionHandling(ex -> ex.authenticationEntryPoint((request, response, authException) ->
                                writeSecurityError(response, objectMapper, HttpServletResponse.SC_UNAUTHORIZED,
                                        "Authentication is required to access this resource."))
                        .accessDeniedHandler((request, response, accessDeniedException) ->
                                writeSecurityError(response, objectMapper, HttpServletResponse.SC_FORBIDDEN,
                                        "You are not allowed to access this resource.")))
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService(IUserMgmtRepository userRepository) {
        return username -> {
            User user = userRepository.findAll().stream()
                    .filter(existingUser -> existingUser.getUserName() != null
                            && existingUser.getUserName().equalsIgnoreCase(username))
                    .findFirst()
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

            List<GrantedAuthority> authorities = new ArrayList<>();
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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(
            @Value("${app.security.cors.allowed-origins}") String allowedOrigins) {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.stream(allowedOrigins.split(","))
                .map(String::trim)
                .filter(value -> !value.isBlank())
                .toList());
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setExposedHeaders(List.of("Authorization"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    private void writeSecurityError(
            HttpServletResponse response,
            ObjectMapper objectMapper,
            int status,
            String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        objectMapper.writeValue(response.getWriter(), ResponseBuilder.error(status, message));
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

    static List<String> moduleIdsFor(String username) {
        String normalized = normalize(username);

        return switch (normalized) {
            case "aparna" -> List.of("users-authors");
            case "moses" -> List.of("books");
            case "sobika" -> List.of("inventory");
            case "janapriya" -> List.of("orders");
            case "swarnalatha" -> List.of("reviews");
            default -> List.of();
        };
    }

    private static String normalize(String username) {
        return username == null ? "" : username.toLowerCase(Locale.ROOT);
    }
}
