package com.sprint.BookInventoryMgmt.BookMgmt.DTO.requestdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PublisherRequestDTO {

    @NotBlank
    private String name;

    private String city;

    @NotNull
    private String stateCode;

    public PublisherRequestDTO() {}

    public PublisherRequestDTO(String name, String city, String stateCode) {
        this.name = name;
        this.city = city;
        this.stateCode = stateCode;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getStateCode() { return stateCode; }
    public void setStateCode(String stateCode) { this.stateCode = stateCode; }
}