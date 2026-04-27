package com.sprint.bookinventorymgmt.usermgmt.dto.requestdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
public class UserRequestDTO {

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 20, message = "First name must be between 2 and 20 characters")
    @Pattern(regexp = "^[A-Za-z][A-Za-z '-]*$", message = "First name must contain only letters, spaces, apostrophes, or hyphens")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 1, max = 30, message = "Last name must be between 1 and 30 characters")
    @Pattern(regexp = "^[A-Za-z][A-Za-z '-]*$", message = "Last name must contain only letters, spaces, apostrophes, or hyphens")
    private String lastName;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be exactly 10 digits")
    private String phoneNumber;

    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 50, message = "Username must be between 4 and 50 characters")
    @Pattern(regexp = "^[A-Za-z0-9._]+$", message = "Username can contain only letters, numbers, dots, and underscores")
    private String userName;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d]).+$",
            message = "Password must contain uppercase, lowercase, number, and special character")
    private String password;

    @NotNull(message = "Role number is required")
    @Positive(message = "Role number must be greater than 0")
    private Integer roleNumber;

    public UserRequestDTO() {}

    public UserRequestDTO(String firstName, String lastName, String phoneNumber,
                          String userName, String password, Integer roleNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.userName = userName;
        this.password = password;
        this.roleNumber = roleNumber;
    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Integer getRoleNumber() { return roleNumber; }
    public void setRoleNumber(Integer roleNumber) { this.roleNumber = roleNumber; }
}
