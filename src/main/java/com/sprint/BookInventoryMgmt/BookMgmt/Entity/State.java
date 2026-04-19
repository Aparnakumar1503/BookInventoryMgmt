package com.sprint.BookInventoryMgmt.bookmgmt.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "state")
public class State {

    @Id
    @Column(name = "state_code")
    private String stateCode;

    private String stateName;

    public State() {}

    public String getStateCode() { return stateCode; }
    public void setStateCode(String stateCode) { this.stateCode = stateCode; }

    public String getStateName() { return stateName; }
    public void setStateName(String stateName) { this.stateName = stateName; }
}