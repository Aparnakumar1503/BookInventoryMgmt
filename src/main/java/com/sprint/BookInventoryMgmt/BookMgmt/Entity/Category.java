package com.sprint.BookInventoryMgmt.BookMgmt.Entity;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    @Column(name = "cat_id")
    private Integer catId;

    @NotBlank
    @Column(name = "cat_description", length = 24)
    private String catDescription;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<Book> books;
}