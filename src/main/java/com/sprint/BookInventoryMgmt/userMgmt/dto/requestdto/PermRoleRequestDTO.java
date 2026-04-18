package com.sprint.BookInventoryMgmt.userMgmt.dto.requestdto;

import jakarta.validation.constraints.NotBlank;

public class PermRoleRequestDTO {
    @NotBlank(message = "Role name is required")
    private String permRole;

    // No-arg constructor
    public PermRoleRequestDTO() {}

    // All-arg constructor
    public PermRoleRequestDTO(String permRole) {
        this.permRole = permRole;
    }

    // Getter
    public String getPermRole() {
        return permRole;
    }

    // Setter
    public void setPermRole(String permRole) {
        this.permRole = permRole;
    }
}
