package com.sprint.BookInventoryMgmt.BookMgmt.Entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @Column(name = "PublisherID")
    private Integer publisherId;

    @Column(name = "Name", nullable = false, length = 50)
    @NotBlank(message = "Publisher name is required")
    private String name;

    @Column(name = "City", length = 30)
    private String city;

    // Many Publishers → One State
    @ManyToOne
    @JoinColumn(name = "StateCode", referencedColumnName = "StateCode")
    @NotNull(message = "State is required")
    private State state;

    // One Publisher → Many Books
    @OneToMany(mappedBy = "publisher")
    @JsonIgnore
    private List<Book> books;
}