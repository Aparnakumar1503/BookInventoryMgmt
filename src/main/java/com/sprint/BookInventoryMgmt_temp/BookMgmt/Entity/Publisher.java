package com.sprint.BookInventoryMgmt.BookMgmt.Entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "publisher")
public class Publisher {

    @Id
    @Column(name = "publisher_id")
    private Integer publisherId;

    private String name;
    private String city;

    @ManyToOne
    @JoinColumn(name = "state_code")
    private State state;

    @OneToMany(mappedBy = "publisher")
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