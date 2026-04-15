package com.sprint.BookInventoryMgmt.BookMgmt.Entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "publisher")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Publisher {

    @Id
    @Column(name = "publisherid")
    private Integer publisherId;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "city", length = 30)
    private String city;

    // 🔗 Many Publishers → One State
    @ManyToOne
    @JoinColumn(name = "statecode", referencedColumnName = "statecode")
    private State state;

    // 🔁 One Publisher → Many Books
    @OneToMany(mappedBy = "publisher")
    private List<Book> books;
}