package com.sprint.bookinventorymgmt.bookmgmt.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "state")
public class State {

    @Id
    @Column(name = "state_code", nullable = false, unique = true)
    @NotBlank(message = "State code cannot be blank")
    @Size(min = 2, max = 10, message = "State code must be between 2 and 10 characters")
    private String stateCode;

    @Column(nullable = false)
    @NotBlank(message = "State name cannot be blank")
    @Size(min = 2, max = 100, message = "State name must be between 2 and 100 characters")
    private String stateName;

    public State() {}

    public String getStateCode() { return stateCode; }
    public void setStateCode(String stateCode) { this.stateCode = stateCode; }

    public String getStateName() { return stateName; }
    public void setStateName(String stateName) { this.stateName = stateName; }
}