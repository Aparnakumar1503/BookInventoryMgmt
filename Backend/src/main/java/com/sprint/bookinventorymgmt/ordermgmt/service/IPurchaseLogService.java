package com.sprint.bookinventorymgmt.ordermgmt.service;

import com.sprint.bookinventorymgmt.ordermgmt.dto.requestDto.PurchaseLogRequestDTO;
import com.sprint.bookinventorymgmt.ordermgmt.dto.responseDto.PurchaseLogResponseDTO;

import java.util.List;


public interface IPurchaseLogService {

    PurchaseLogResponseDTO addPurchase(PurchaseLogRequestDTO requestDTO);

    List<PurchaseLogResponseDTO> getAll();
    List<PurchaseLogResponseDTO> getByUserId(Integer userId);

    String delete(Integer userId, Integer inventoryId);
}
