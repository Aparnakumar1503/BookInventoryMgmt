package com.sprint.BookInventoryMgmt.InventoryMgmt.requestdto;

import com.sprint.BookInventoryMgmt.InventoryMgmt.entity.*;
import com.sprint.BookInventoryMgmt.InventoryMgmt.responsedto.*;

public class InventoryMapper {

    // INVENTORY
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

    // BOOK CONDITION
    public static BookCondition toBookConditionEntity(BookConditionRequestDTO dto) {
        BookCondition bc = new BookCondition();
        bc.setRanks(dto.getRanks());
        bc.setDescription(dto.getDescription());
        bc.setFullDescription(dto.getFullDescription());
        bc.setPrice(dto.getPrice());
        return bc;
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