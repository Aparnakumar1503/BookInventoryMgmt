package com.sprint.BookInventoryMgmt.ordermgmt.service;

import com.sprint.BookInventoryMgmt.ordermgmt.dto.requestdto.PurchaseLogRequestDTO;
import com.sprint.BookInventoryMgmt.ordermgmt.dto.responsedto.PurchaseLogResponseDTO;

import java.util.List;


public interface PurchaseLogService {

    PurchaseLogResponseDTO addPurchase(PurchaseLogRequestDTO requestDTO);

    List<PurchaseLogResponseDTO> getAll();

    String delete(Long userId);
}