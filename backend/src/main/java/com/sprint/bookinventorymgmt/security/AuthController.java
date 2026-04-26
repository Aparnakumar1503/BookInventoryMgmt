package com.sprint.bookinventorymgmt.security;

import com.sprint.bookinventorymgmt.common.ResponseBuilder;
import com.sprint.bookinventorymgmt.common.ResponseStructure;
import com.sprint.bookinventorymgmt.security.dto.AuthDtos.AuthRequest;
import com.sprint.bookinventorymgmt.security.dto.AuthDtos.AuthenticatedUserResponse;
import com.sprint.bookinventorymgmt.usermgmt.entity.User;
import com.sprint.bookinventorymgmt.usermgmt.repository.IUserMgmtRepository;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@Validated
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final IUserMgmtRepository userRepository;

    public AuthController(
            AuthenticationManager authenticationManager,
            IUserMgmtRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseStructure<AuthenticatedUserResponse>> login(
            @Valid @RequestBody AuthRequest request,
            HttpServletRequest httpRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username(), request.password())
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(securityContext);

            HttpSession session = httpRequest.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);

            AuthenticatedUserResponse user = buildUserResponse(userDetails.getUsername(), userDetails.getAuthorities());

            return ResponseEntity.ok(
                    ResponseBuilder.success(HttpStatus.OK.value(), "Login successful", user)
            );
        } catch (BadCredentialsException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseStructure<String>> logout(HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        SecurityContextHolder.clearContext();

        return ResponseEntity.ok(
                ResponseBuilder.success(HttpStatus.OK.value(), "Logout successful", "Session ended successfully")
        );
    }

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

    private AuthenticatedUserResponse buildUserResponse(
            String username,
            Collection<? extends GrantedAuthority> authorities) {
        User user = userRepository.findAll().stream()
                .filter(existingUser -> existingUser.getUserName() != null
                        && existingUser.getUserName().equalsIgnoreCase(username))
                .findFirst()
                .orElse(null);
        List<String> authorityValues = new ArrayList<>();
        for (GrantedAuthority authority : authorities) {
            authorityValues.add(authority.getAuthority());
        }
        Collections.sort(authorityValues);

        return new AuthenticatedUserResponse(
                username,
                user != null ? user.getFirstName() : "",
                user != null ? user.getLastName() : "",
                user != null && user.getRole() != null ? user.getRole().getPermRole() : "",
                authorityValues,
                SecurityAuthorities.moduleIdsFor(username)
        );
    }
}
