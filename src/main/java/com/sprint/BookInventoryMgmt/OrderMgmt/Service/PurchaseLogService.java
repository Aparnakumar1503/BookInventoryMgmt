package com.sprint.BookInventoryMgmt.OrderMgmt.Service;

import com.sprint.BookInventoryMgmt.OrderMgmt.dto.requestdto.PurchaseLogRequestDTO;
import com.sprint.BookInventoryMgmt.OrderMgmt.dto.responsedto.PurchaseLogResponseDTO;

import java.util.List;

public interface PurchaseLogService {

    PurchaseLogResponseDTO addPurchase(PurchaseLogRequestDTO dto);

    List<PurchaseLogResponseDTO> getAll();

    String delete(Long userId);
}