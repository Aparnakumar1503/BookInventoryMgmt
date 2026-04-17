package com.sprint.BookInventoryMgmt.OrderMgmt.Service;

import com.sprint.BookInventoryMgmt.OrderMgmt.DTO.PurchaseLogRequestDTO;
import com.sprint.BookInventoryMgmt.OrderMgmt.DTO.PurchaseLogResponseDTO;
import com.sprint.BookInventoryMgmt.OrderMgmt.Entity.PurchaseLog;
import java.util.List;


import java.util.List;

public interface PurchaseLogService {

    PurchaseLogResponseDTO addPurchase(PurchaseLogRequestDTO dto);

    List<PurchaseLogResponseDTO> getAll();

    String delete(Long userId);
}