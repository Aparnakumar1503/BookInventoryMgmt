package com.sprint.BookInventoryMgmt.InventoryMgmt.requestdto;

import com.sprint.BookInventoryMgmt.InventoryMgmt.entity.*;
import com.sprint.BookInventoryMgmt.InventoryMgmt.responsedto.*;

public class InventoryMapper {

    public static Inventory toInventoryEntity(InventoryRequestDTO dto) {
        Inventory inv = new Inventory();
        inv.setIsbn(dto.getIsbn());
        inv.setRanks(dto.getRanks());
        inv.setPurchased(dto.getPurchased() != null ? dto.getPurchased() : false);
        return inv;
    }

    public static InventoryResponseDTO toInventoryResponse(Inventory inv) {
        return new InventoryResponseDTO(
                inv.getInventoryId(),
                inv.getIsbn(),
                inv.getRanks(),
                inv.getPurchased()
        );
    }

    public static BookConditionResponseDTO toBookConditionResponse(BookCondition bc) {
        return new BookConditionResponseDTO(
                bc.getRanks(),
                bc.getDescription(),
                bc.getFullDescription(),
                bc.getPrice()
        );
    }
}