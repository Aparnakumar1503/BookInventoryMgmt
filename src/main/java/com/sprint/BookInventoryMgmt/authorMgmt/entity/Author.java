package com.sprint.BookInventoryMgmt.authorMgmt.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "author")
public class Author {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull(message = "Author ID is required")
	@Column(name = "AuthorID")
	private Integer authorId;

	@NotBlank(message = "First name is required")
	@Column(name = "FirstName")
	private String firstName;

	@NotBlank(message = "Last name is required")
	@Column(name = "LastName")
	private String lastName;

	@Column(name = "Photo")
	private String photo;

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
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

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public Author(Integer authorId, String lastName, String firstName, String photo) {
		this.authorId = authorId;
		this.lastName = lastName;
		this.firstName = firstName;
		this.photo = photo;
	}
	public Author() {}


}