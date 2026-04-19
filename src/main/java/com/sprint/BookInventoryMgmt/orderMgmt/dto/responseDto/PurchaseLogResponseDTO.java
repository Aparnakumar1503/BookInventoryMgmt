package com.sprint.BookInventoryMgmt.ordermgmt.dto.responsedto;
import lombok.Data;

@Data
public class PurchaseLogResponseDTO {

    private Integer userId;
    private Integer inventoryId;

    public PurchaseLogResponseDTO() {
    }

    public Integer getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Integer inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public PurchaseLogResponseDTO(Integer inventoryId,Integer userId) {
        this.inventoryId = inventoryId;
        this.userId = userId;
    }
}