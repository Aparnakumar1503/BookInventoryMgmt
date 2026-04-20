package com.sprint.BookInventoryMgmt.usermgmt.dto.requestdto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public class PermRoleRequestDTO {
    @NotBlank(message = "Role name is required")
    private String permRole;

    public PermRoleRequestDTO() {}

    public PermRoleRequestDTO(String permRole) {
        this.permRole = permRole;
    }

    public String getPermRole() { return permRole; }
    public void setPermRole(String permRole) { this.permRole = permRole; }
}

