package com.sprint.bookinventorymgmt.ordermgmt.dto.responseDto;
import lombok.Data;

// DTO class used to send purchase response data from backend to client
@Data
// Lombok annotation that automatically generates getters, setters, toString, equals, and hashCode
public class PurchaseLogResponseDTO {

    // Stores the user ID associated with the purchase response
    private Integer userId;

    // Stores the inventory ID associated with the purchase response
    private Integer inventoryId;

    // Default constructor required for object creation and framework compatibility
    public PurchaseLogResponseDTO() {
    }

    // Getter method to retrieve inventoryId value from response object
    public Integer getInventoryId() {
        return inventoryId;
    }

    // Setter method to assign inventoryId value while building response
    public void setInventoryId(Integer inventoryId) {
        this.inventoryId = inventoryId;
    }

    // Getter method to retrieve userId value from response object
    public Integer getUserId() {
        return userId;
    }

    // Setter method to assign userId value while building response
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    // Parameterized constructor used to create response object with values
    public PurchaseLogResponseDTO(Integer inventoryId, Integer userId) {
        this.inventoryId = inventoryId;
        this.userId = userId;
    }
}