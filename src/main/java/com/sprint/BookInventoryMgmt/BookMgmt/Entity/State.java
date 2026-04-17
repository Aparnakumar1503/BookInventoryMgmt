package com.sprint.BookInventoryMgmt.BookMgmt.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "state")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class State {

    @Id
    @Column(name = "state_code")
    private String stateCode;

    @Column(name = "state_name")
    private String stateName;
}