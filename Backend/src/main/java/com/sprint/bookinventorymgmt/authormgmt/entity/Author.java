package com.sprint.bookinventorymgmt.authormgmt.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "author")

public class Author {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "author_id")
	private Integer authorId;

	@NotBlank(message = "First name is required")
	@Column(name = "first_name")
	private String firstName;

	@NotBlank(message = "Last name is required")
	@Column(name = "last_name")
	private String lastName;

	@Column(name = "photo")
	private String photo;

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	public @NotBlank(message = "First name is required") String getFirstName() {
		return firstName;
	}

	public void setFirstName(@NotBlank(message = "First name is required") String firstName) {
		this.firstName = firstName;
	}

	public @NotBlank(message = "Last name is required") String getLastName() {
		return lastName;
	}

	public void setLastName(@NotBlank(message = "Last name is required") String lastName) {
		this.lastName = lastName;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Author() {
	}

	public Author(Integer authorId, String firstName, String lastName,String photo) {
		this.authorId = authorId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.photo = photo;
	}
}
