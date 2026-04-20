package com.sprint.bookinventorymgmt.bookmgmt.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "publisher")
public class Publisher {

    @Id
    @Column(name = "publisher_id", nullable = false, unique = true)
    @NotNull(message = "Publisher ID cannot be null")
    private Integer publisherId;

    @Column(nullable = false)
    @NotBlank(message = "Publisher name cannot be blank")
    @Size(min = 2, max = 100, message = "Publisher name must be between 2 and 100 characters")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "City cannot be blank")
    private String city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_code", nullable = false)
    @NotNull(message = "State is required")
    private State state;

    @OneToMany(mappedBy = "publisher", fetch = FetchType.LAZY)
    private List<Book> books;

    public Publisher() {}

    public Integer getPublisherId() { return publisherId; }
    public void setPublisherId(Integer publisherId) { this.publisherId = publisherId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public State getState() { return state; }
    public void setState(State state) { this.state = state; }

    public List<Book> getBooks() { return books; }
    public void setBooks(List<Book> books) { this.books = books; }
}