package com.sprint.bookinventorymgmt.usermgmt.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
@Entity
@Table(name = "permrole")
public class PermRole {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_number") // ✅ FIXED
	private Integer roleNumber;

	@NotBlank(message = "Role name is required")
	@Column(name = "perm_role", length = 30) // ✅ also fix this for consistency
	private String permRole;

	public PermRole() {}

	public PermRole(Integer roleNumber, String permRole) {
		this.roleNumber = roleNumber;
		this.permRole = permRole;
	}

	// getters & setters


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


