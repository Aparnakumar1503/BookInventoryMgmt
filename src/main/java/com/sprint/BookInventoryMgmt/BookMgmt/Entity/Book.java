package com.sprint.BookInventoryMgmt.BookMgmt.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "book")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @Column(name = "isbn", length = 13)
    private String isbn;

    @Column(name = "title", nullable = false, length = 70)
    private String title;

    @Column(name = "description", length = 100)
    private String description;

    @Column(name = "edition", length = 30)
    private String edition;

    // 🔗 Many Books → One Category
    @ManyToOne
    @JoinColumn(name = "category", referencedColumnName = "catid")
    private Category category;

    // 🔗 Many Books → One Publisher
    @ManyToOne
    @JoinColumn(name = "publisherid", referencedColumnName = "publisherid", nullable = false)
    private Publisher publisher;
}