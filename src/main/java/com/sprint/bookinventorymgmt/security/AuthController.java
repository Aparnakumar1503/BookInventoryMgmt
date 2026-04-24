package com.sprint.bookinventorymgmt.security;

import com.sprint.bookinventorymgmt.common.ResponseBuilder;
import com.sprint.bookinventorymgmt.common.ResponseStructure;
import com.sprint.bookinventorymgmt.usermgmt.entity.User;
import com.sprint.bookinventorymgmt.usermgmt.repository.IUserMgmtRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final IUserMgmtRepository userRepository;

    public AuthController(AuthenticationManager authenticationManager, IUserMgmtRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    /**
     * Authenticates the user and stores the security context in the HTTP session for the Angular frontend.
     */
    @PostMapping("/login")
    public ResponseEntity<ResponseStructure<AuthenticatedUserResponse>> login(
            @Valid @RequestBody LoginRequest loginRequest,
            HttpServletRequest request,
            HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken.unauthenticated(
                        loginRequest.username(),
                        loginRequest.password()
                )
        );

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        new HttpSessionSecurityContextRepository().saveContext(context, request, response);

        AuthenticatedUserResponse payload = buildUserResponse(
                authentication.getName(),
                authentication.getAuthorities()
        );

        return ResponseEntity.ok(
                ResponseBuilder.success(HttpStatus.OK.value(), "Login successful", payload)
        );
    }

    /**
     * Returns the authenticated user so the UI can show only the owned module endpoints.
     */
    @GetMapping("/me")
    public ResponseEntity<ResponseStructure<AuthenticatedUserResponse>> me(Authentication authentication) {
        AuthenticatedUserResponse payload = buildUserResponse(
                authentication.getName(),
                authentication.getAuthorities()
        );

        return ResponseEntity.ok(
                ResponseBuilder.success(HttpStatus.OK.value(), "Authenticated user fetched successfully", payload)
        );
    }

    /**
     * Clears the current session so the frontend can log out cleanly.
     */
    @PostMapping("/logout")
    public ResponseEntity<ResponseStructure<String>> logout(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        request.logout();
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        SecurityContextHolder.clearContext();

        return ResponseEntity.ok(
                ResponseBuilder.success(HttpStatus.OK.value(), "Logout successful", "Logged out successfully")
        );
    }

    /**
     * Combines database user details with granted authorities for a simple session summary.
     */
    private AuthenticatedUserResponse buildUserResponse(
            String username,
            java.util.Collection<? extends GrantedAuthority> authorities) {
        User user = userRepository.findByUserNameIgnoreCase(username).orElse(null);
        List<String> authorityValues = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .sorted()
                .toList();

        return new AuthenticatedUserResponse(
                username,
                user != null ? user.getFirstName() : "",
                user != null ? user.getLastName() : "",
                user != null && user.getRole() != null ? user.getRole().getPermRole() : "",
                authorityValues,
                authorityValues.stream()
                        .filter(SecurityAuthorities::isOwnerAuthority)
                        .map(this::toFrontendModuleId)
                        .toList()
        );
    }

    /**
     * Converts owner authorities into the Angular module ids used by the frontend router.
     */
    private String toFrontendModuleId(String authority) {
        return switch (authority == null ? "" : authority.toUpperCase(Locale.ROOT)) {
            case SecurityAuthorities.OWNER_USER_AUTHOR -> "users-authors";
            case SecurityAuthorities.OWNER_BOOK -> "books";
            case SecurityAuthorities.OWNER_INVENTORY -> "inventory";
            case SecurityAuthorities.OWNER_ORDER -> "orders";
            case SecurityAuthorities.OWNER_REVIEW -> "reviews";
            default -> "";
        };
    }
}

record LoginRequest(
        @NotBlank(message = "Username is required")
        String username,
        @NotBlank(message = "Password is required")
        String password) {
}

class AuthenticatedUserResponse {

    private final String username;
    private final String firstName;
    private final String lastName;
    private final String role;
    private final List<String> authorities;
    private final List<String> modules;

    AuthenticatedUserResponse(
            String username,
            String firstName,
            String lastName,
            String role,
            List<String> authorities,
            List<String> modules) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.authorities = authorities;
        this.modules = modules;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getRole() {
        return role;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public List<String> getModules() {
        return modules;
    }
}
