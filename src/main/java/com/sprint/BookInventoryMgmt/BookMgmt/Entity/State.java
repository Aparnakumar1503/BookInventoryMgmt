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
@Table(name = "state")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class State {

    @Id
    @Column(name = "statecode", length = 2)
    private String stateCode;

    @Column(name = "statename", length = 50)
    private String stateName;

    // 🔁 One State → Many Publishers
    @OneToMany(mappedBy = "state")
    private List<Publisher> publishers;
}