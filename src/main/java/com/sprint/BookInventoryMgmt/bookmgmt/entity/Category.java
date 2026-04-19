package com.sprint.BookInventoryMgmt.bookmgmt.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @Column(name = "cat_id")
    private Integer catId;

    @Column(name = "cat_description")
    private String catDescription;

    @OneToMany(mappedBy = "category")
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