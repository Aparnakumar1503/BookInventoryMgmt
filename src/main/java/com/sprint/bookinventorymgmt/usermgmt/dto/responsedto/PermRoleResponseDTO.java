package com.sprint.BookInventoryMgmt.usermgmt.dto.responsedto;

import lombok.Builder;

@Builder
public class PermRoleResponseDTO {
    private Integer roleNumber;
    private String permRole;

    public PermRoleResponseDTO() {}

    public PermRoleResponseDTO(Integer roleNumber, String permRole) {
        this.roleNumber = roleNumber;
        this.permRole = permRole;
    }

    public Integer getRoleNumber() { return roleNumber; }
    public void setRoleNumber(Integer roleNumber) { this.roleNumber = roleNumber; }
    public String getPermRole() { return permRole; }
    public void setPermRole(String permRole) { this.permRole = permRole; }
}
