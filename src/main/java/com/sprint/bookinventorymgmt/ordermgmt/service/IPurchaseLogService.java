package com.sprint.BookInventoryMgmt.ordermgmt.service;

import com.sprint.BookInventoryMgmt.ordermgmt.dto.requestDto.PurchaseLogRequestDTO;
import com.sprint.BookInventoryMgmt.ordermgmt.dto.responseDto.PurchaseLogResponseDTO;

import java.util.List;

public interface IPurchaseLogService {

    PurchaseLogResponseDTO addPurchase(PurchaseLogRequestDTO requestDTO);

    List<PurchaseLogResponseDTO> getAll();

    String delete(Integer userId, Integer inventoryId);
}