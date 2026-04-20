package com.sprint.bookinventorymgmt.usermgmt.dto.requestdto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public class UserRequestDTO {

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    private String phoneNumber; // optional

    @NotBlank(message = "Username is required")
    private String userName;

    @NotBlank(message = "Password is required")
    @Size(min = 4, message = "Password must be at least 4 characters")
    private String password;

    @NotNull(message = "Role number is required")
    private Integer roleNumber; // only need roleNumber — not full PermRole object

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
