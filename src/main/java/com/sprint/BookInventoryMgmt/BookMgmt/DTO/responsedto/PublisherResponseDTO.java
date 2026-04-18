package com.sprint.BookInventoryMgmt.BookMgmt.DTO.responsedto;

public class PublisherResponseDTO {

    private Integer publisherId;
    private String name;
    private String city;
    private String stateCode;
    private String stateName;

    public PublisherResponseDTO() {}

    public PublisherResponseDTO(Integer publisherId, String name, String city,
                                String stateCode, String stateName) {
        this.publisherId = publisherId;
        this.name = name;
        this.city = city;
        this.stateCode = stateCode;
        this.stateName = stateName;
    }

    public Integer getPublisherId() { return publisherId; }
    public void setPublisherId(Integer publisherId) { this.publisherId = publisherId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getStateCode() { return stateCode; }
    public void setStateCode(String stateCode) { this.stateCode = stateCode; }

    public String getStateName() { return stateName; }
    public void setStateName(String stateName) { this.stateName = stateName; }
}