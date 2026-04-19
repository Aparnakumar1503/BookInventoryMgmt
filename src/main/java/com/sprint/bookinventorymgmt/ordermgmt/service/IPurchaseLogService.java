package com.sprint.bookinventorymgmt.ordermgmt.service;

import com.sprint.bookinventorymgmt.ordermgmt.dto.requestdto.PurchaseLogRequestDTO;
import com.sprint.bookinventorymgmt.ordermgmt.dto.responsedto.PurchaseLogResponseDTO;

import java.util.List;


public interface IPurchaseLogService {

    PurchaseLogResponseDTO addPurchase(PurchaseLogRequestDTO requestDTO);

    List<PurchaseLogResponseDTO> getAll();

    String delete(Integer userId, Integer inventoryId);
}