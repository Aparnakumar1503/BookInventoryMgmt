package com.sprint.bookinventorymgmt.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import java.util.function.Supplier;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String APARNA = "Aparna";
    private static final String MOSES = "Moses";
    private static final String SOBIKA = "Sobika";
    private static final String JANAPRIYA = "Janapriya";
    private static final String SWARNALATHA = "SwarnaLatha";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        // Aparna: UserMgmt and AuthorMgmt
                        .requestMatchers("/api/v1/users/**").access(this::aparnaOnly)
                        .requestMatchers("/api/v1/roles/**").access(this::aparnaOnly)
                        .requestMatchers("/api/v1/authors/**").access(this::aparnaOnly)
                        .requestMatchers("/api/v1/books/*/authors/**").access(this::aparnaOnly)

                        // Moses: BookMgmt
                        .requestMatchers("/api/v1/books/**").access(this::mosesOnly)
                        .requestMatchers("/api/v1/categories/**").access(this::mosesOnly)
                        .requestMatchers("/api/v1/publishers/**").access(this::mosesOnly)
                        .requestMatchers("/api/v1/states/**").access(this::mosesOnly)

                        // Sobika: InventoryMgmt
                        .requestMatchers("/api/v1/inventory/**").access(this::sobikaOnly)
                        .requestMatchers("/api/v1/book-conditions/**").access(this::sobikaOnly)

                        // Janapriya: OrderMgmt
                        .requestMatchers("/purchase/**").access(this::janapriyaOnly)
                        .requestMatchers("/cart/**").access(this::janapriyaOnly)

                        // SwarnaLatha: ReviewMgmt
                        .requestMatchers("/api/reviews/**").access(this::swarnalathaOnly)
                        .requestMatchers("/api/reviewers/**").access(this::swarnalathaOnly)

                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails aparna = moduleUser(APARNA, passwordEncoder);
        UserDetails moses = moduleUser(MOSES, passwordEncoder);
        UserDetails sobika = moduleUser(SOBIKA, passwordEncoder);
        UserDetails janapriya = moduleUser(JANAPRIYA, passwordEncoder);
        UserDetails swarnaLatha = moduleUser(SWARNALATHA, passwordEncoder);

        return new InMemoryUserDetailsManager(aparna, moses, sobika, janapriya, swarnaLatha);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    private UserDetails moduleUser(String username, PasswordEncoder passwordEncoder) {
        return User.withUsername(username)
                .password(passwordEncoder.encode("password"))
                .roles("MEMBER")
                .build();
    }

    private AuthorizationDecision aparnaOnly(
            Supplier<Authentication> authentication,
            RequestAuthorizationContext context) {
        return userOnly(authentication, APARNA);
    }

    private AuthorizationDecision mosesOnly(
            Supplier<Authentication> authentication,
            RequestAuthorizationContext context) {
        return userOnly(authentication, MOSES);
    }

    private AuthorizationDecision sobikaOnly(
            Supplier<Authentication> authentication,
            RequestAuthorizationContext context) {
        return userOnly(authentication, SOBIKA);
    }

    private AuthorizationDecision janapriyaOnly(
            Supplier<Authentication> authentication,
            RequestAuthorizationContext context) {
        return userOnly(authentication, JANAPRIYA);
    }

    private AuthorizationDecision swarnalathaOnly(
            Supplier<Authentication> authentication,
            RequestAuthorizationContext context) {
        return userOnly(authentication, SWARNALATHA);
    }

    private AuthorizationDecision userOnly(Supplier<Authentication> authentication, String username) {
        Authentication currentUser = authentication.get();
        return new AuthorizationDecision(
                currentUser != null
                        && currentUser.isAuthenticated()
                        && username.equals(currentUser.getName())
        );
    }
}
