package com.sprint.bookinventorymgmt.usermgmt.dto.responsedto;

import lombok.Builder;

@Builder
public class UserResponseDTO {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String userName;
    private Integer roleNumber; // role details
    private String permRole;   // role name — richer response

    public UserResponseDTO() {}

    public UserResponseDTO(Integer userId, String firstName, String lastName,
                           String phoneNumber, String userName,
                           Integer roleNumber, String permRole) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.userName = userName;
        this.roleNumber = roleNumber;
        this.permRole = permRole;
    }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public Integer getRoleNumber() { return roleNumber; }
    public void setRoleNumber(Integer roleNumber) { this.roleNumber = roleNumber; }
    public String getPermRole() { return permRole; }
    public void setPermRole(String permRole) { this.permRole = permRole; }
}
