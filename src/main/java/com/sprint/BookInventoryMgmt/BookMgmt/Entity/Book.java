package com.sprint.BookInventoryMgmt.BookMgmt.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "book")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @Column(name = "isbn")
    private String isbn;

    private String title;
    private String description;
    private String edition;

    @ManyToOne
    @JoinColumn(name = "cat_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;
}