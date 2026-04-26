package com.sprint.bookinventorymgmt.security.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public final class AuthDtos {

    private AuthDtos() {
    }

    public record AuthRequest(
            @NotBlank(message = "Username is required") String username,
            @NotBlank(message = "Password is required") String password) {
    }

    public record AuthenticatedUserResponse(
            String username,
            String firstName,
            String lastName,
            String role,
            List<String> authorities,
            List<String> modules) {
    }
}
