package com.sprint.BookInventoryMgmt.userMgmt.dto.responsedto;

public class PermRoleResponseDTO {
    private Integer roleNumber;
    private String permRole;

    // No-arg constructor
    public PermRoleResponseDTO() {}

    // All-arg constructor
    public PermRoleResponseDTO(Integer roleNumber, String permRole) {
        this.roleNumber = roleNumber;
        this.permRole = permRole;
    }

    // Getters and Setters

    public Integer getRoleNumber() {
        return roleNumber;
    }

    public void setRoleNumber(Integer roleNumber) {
        this.roleNumber = roleNumber;
    }

    public String getPermRole() {
        return permRole;
    }

    public void setPermRole(String permRole) {
        this.permRole = permRole;
    }
}
