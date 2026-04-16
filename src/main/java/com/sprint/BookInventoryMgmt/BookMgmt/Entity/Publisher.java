package com.sprint.BookInventoryMgmt.BookMgmt.Entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "publisher")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Publisher {

    @Id
    @Column(name = "publisher_id")
    private Integer publisherId;

    @NotBlank
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "city", length = 30)
    private String city;

    @ManyToOne
    @JoinColumn(name = "state_code")
    private State state;

    @OneToMany(mappedBy = "publisher")
    @JsonIgnore
    private List<Book> books;
}