package com.sprint.bookinventorymgmt.security;

import com.sprint.bookinventorymgmt.common.ResponseBuilder;
import com.sprint.bookinventorymgmt.common.ResponseStructure;
import com.sprint.bookinventorymgmt.security.dto.AuthDtos.AuthRequest;
import com.sprint.bookinventorymgmt.security.dto.AuthDtos.AuthTokenResponse;
import com.sprint.bookinventorymgmt.security.dto.AuthDtos.AuthenticatedUserResponse;
import com.sprint.bookinventorymgmt.usermgmt.entity.User;
import com.sprint.bookinventorymgmt.usermgmt.exceptions.InvalidCredentialsException;
import com.sprint.bookinventorymgmt.usermgmt.repository.IUserMgmtRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@Validated
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final IUserMgmtRepository userRepository;

    public AuthController(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            IUserMgmtRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseStructure<AuthTokenResponse>> login(@Valid @RequestBody AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username(), request.password())
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtService.generateToken(userDetails);
            AuthenticatedUserResponse user = buildUserResponse(userDetails.getUsername(), userDetails.getAuthorities());

            AuthTokenResponse payload = new AuthTokenResponse(
                    "Bearer",
                    token,
                    Instant.now().plusMillis(jwtService.getExpirationMs()).toEpochMilli(),
                    user
            );

            return ResponseEntity.ok(
                    ResponseBuilder.success(HttpStatus.OK.value(), "Login successful", payload)
            );
        } catch (BadCredentialsException ex) {
            throw new InvalidCredentialsException("Invalid username or password");
        }
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
        User user = userRepository.findByUserNameIgnoreCase(username).orElse(null);
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
