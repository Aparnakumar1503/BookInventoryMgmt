package com.sprint.BookInventoryMgmt.BookMgmt.Entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
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
    @Column(name = "StateCode", length = 2)
    @NotBlank(message = "State code is required")
    private String stateCode;

    @Column(name = "StateName", length = 50)
    private String stateName;

    @OneToMany(mappedBy = "state")
    @JsonIgnore
    private List<Publisher> publishers;
}