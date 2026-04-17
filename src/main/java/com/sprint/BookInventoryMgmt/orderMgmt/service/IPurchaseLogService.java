package com.sprint.BookInventoryMgmt.orderMgmt.service;

import com.sprint.BookInventoryMgmt.orderMgmt.dto.requestDto.PurchaseLogRequestDTO;
import com.sprint.BookInventoryMgmt.orderMgmt.dto.responseDto.PurchaseLogResponseDTO;

import java.util.List;

public interface IPurchaseLogService {

    PurchaseLogResponseDTO addPurchase(PurchaseLogRequestDTO dto);

    List<PurchaseLogResponseDTO> getAll();

    String delete(Long userId);
}