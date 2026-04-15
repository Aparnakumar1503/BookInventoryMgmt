package com.sprint.BookInventoryMgmt.BookMgmt.Entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    @Column(name = "catid")
    private Integer catId;

    @Column(name = "catdescription", length = 24)
    private String catDescription;

    // 🔁 One Category → Many Books
    @OneToMany(mappedBy = "category")
    private List<Book> books;
}