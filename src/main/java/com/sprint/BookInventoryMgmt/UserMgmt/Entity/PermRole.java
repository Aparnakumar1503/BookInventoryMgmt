package com.sprint.BookInventoryMgmt.UserMgmt.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
<<<<<<< HEAD
import jakarta.validation.constraints.NotBlank;


@Entity
@Table(name = "permrole")
public class PermRole {

	@Id
	@Column(name = "RoleNumber")
	private Integer roleNumber;
	
	@NotBlank(message = "Role name is required")
	@Column(name = "PermRole", length = 30)
	private String permRole;

	// Constructors
	public PermRole() {}

	public PermRole(Integer roleNumber, String permRole) {
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

=======


	@Entity
	@Table(name = "permrole")
	public class PermRole {

	    @Id
	    @Column(name = "RoleNumber")
	    private Integer roleNumber;

	    @Column(name = "PermRole", length = 30)
	    private String permRole;

	    // Constructors
	    public PermRole() {}

	    public PermRole(Integer roleNumber, String permRole) {
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

>>>>>>> 31dab6d (Modules Created)


