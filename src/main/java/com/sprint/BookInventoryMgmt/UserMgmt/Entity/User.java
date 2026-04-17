package com.sprint.BookInventoryMgmt.UserMgmt.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "UserID")
	    private Integer userId;

	    @NotBlank(message = "First name is required")
	    @Column(name = "FirstName", nullable = false, length = 20)
	    private String firstName;

	    @NotBlank(message = "Last name is required")
	    @Column(name = "LastName", nullable = false, length = 30)
	    private String lastName;

	    @Column(name = "PhoneNumber", length = 14)
	    private String phoneNumber;

	    @NotBlank(message = "Username is required")
	    @Column(name = "UserName", nullable = false, length = 30)
	    private String userName;

	    @NotBlank(message = "Password is required")
	    @Size(min = 4, message = "Password must be at least 4 characters")
	    @Column(name = "Password", nullable = false, length = 30)
	    private String password;

	    @ManyToOne(fetch = FetchType.EAGER)
	    @JoinColumn(name = "RoleNumber")
	    private PermRole role;

	// Constructors
	public User() {}

	public User(String firstName, String lastName, String phoneNumber,
			String userName, String password, PermRole role) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.userName = userName;
		this.password = password;
		this.role = role;
	}

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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public PermRole getRole() {
		return role;
	}

	public void setRole(PermRole role) {
		this.role = role;
	}


}

