package com.sprint.bookinventorymgmt.security;

import java.util.List;

public class AuthenticatedUserResponse {

    private final String username;
    private final String firstName;
    private final String lastName;
    private final String role;
    private final List<String> authorities;
    private final List<String> modules;

    public AuthenticatedUserResponse(
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
