package com.sprint.bookinventorymgmt.bookmgmt.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @Column(name = "cat_id", nullable = false, unique = true)
    @NotNull(message = "Category ID cannot be null")
    private Integer catId;

    @Column(name = "cat_description", nullable = false)
    @NotBlank(message = "Category description cannot be blank")
    @Size(min = 2, max = 100, message = "Category description must be between 2 and 100 characters")
    private String catDescription;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Book> books;

    public Category() {}

    public Category(Integer catId, String catDescription) {
        this.catId = catId;
        this.catDescription = catDescription;
    }

    public Integer getCatId() { return catId; }
    public void setCatId(Integer catId) { this.catId = catId; }

    public String getCatDescription() { return catDescription; }
    public void setCatDescription(String catDescription) { this.catDescription = catDescription; }

    public List<Book> getBooks() { return books; }
    public void setBooks(List<Book> books) { this.books = books; }
}