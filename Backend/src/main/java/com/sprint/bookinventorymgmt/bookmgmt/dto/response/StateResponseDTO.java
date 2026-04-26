package com.sprint.bookinventorymgmt.bookmgmt.dto.response;

public class StateResponseDTO {

    private String stateCode;
    private String stateName;

    public StateResponseDTO() {}

    public StateResponseDTO(String stateCode, String stateName) {
        this.stateCode = stateCode;
        this.stateName = stateName;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
}