package com.sprint.BookInventoryMgmt.userMgmt.dto.responsedto;

public class UserResponseDTO {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String userName;
    private String roleName;

    public UserResponseDTO() {}

    public UserResponseDTO(Integer userId, String firstName,
                           String lastName, String userName,
                           String roleName) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.roleName = roleName;
    }

    // getters & setters

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}
