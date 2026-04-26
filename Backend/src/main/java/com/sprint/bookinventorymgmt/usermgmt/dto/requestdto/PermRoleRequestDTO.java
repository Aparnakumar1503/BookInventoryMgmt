package com.sprint.bookinventorymgmt.usermgmt.dto.requestdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public class PermRoleRequestDTO {
    @NotBlank(message = "Role name is required")
    @Size(min = 2, max = 30, message = "Role name must be between 2 and 30 characters")
    @Pattern(regexp = "^[A-Za-z][A-Za-z ]*$", message = "Role name must contain only letters and spaces")
    private String permRole;
    //constructor

    public PermRoleRequestDTO() {}

    public PermRoleRequestDTO(String permRole) {
        this.permRole = permRole;
    }

    public String getPermRole() { return permRole; }
    public void setPermRole(String permRole) { this.permRole = permRole; }
}

