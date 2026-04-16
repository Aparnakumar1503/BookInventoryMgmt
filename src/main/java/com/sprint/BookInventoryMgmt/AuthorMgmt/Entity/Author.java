package com.sprint.BookInventoryMgmt.AuthorMgmt.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "author")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Author {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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


}