package com.sprint.BookInventoryMgmt.ordermgmt.service;

import com.sprint.BookInventoryMgmt.ordermgmt.entity.PurchaseLog;
import com.sprint.BookInventoryMgmt.ordermgmt.entity.PurchaseLogId;
import com.sprint.BookInventoryMgmt.ordermgmt.exceptions.PurchaseNotFoundException;
import com.sprint.BookInventoryMgmt.ordermgmt.exceptions.BookAlreadyPurchasedException;
import com.sprint.BookInventoryMgmt.ordermgmt.repository.IPurchaseLogRepository;
import com.sprint.BookInventoryMgmt.ordermgmt.dto.requestDto.PurchaseLogRequestDTO;
import com.sprint.BookInventoryMgmt.ordermgmt.dto.responseDto.PurchaseLogResponseDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseLogServiceImpl implements IPurchaseLogService {

    private final IPurchaseLogRepository repo;

    public PurchaseLogServiceImpl(IPurchaseLogRepository repo) {
        this.repo = repo;
    }

    // ✅ ADD PURCHASE
    @Override
    public PurchaseLogResponseDTO addPurchase(PurchaseLogRequestDTO dto) {

        // ✅ FIX 1: NULL VALIDATION
        if (dto == null || dto.getUserId() == null || dto.getInventoryId() == null) {
            throw new IllegalArgumentException("Invalid purchase data");
        }

        // ✅ CHECK DUPLICATE
        PurchaseLog existing = repo.findByUserIdAndInventoryId(
                dto.getUserId(), dto.getInventoryId()
        );

        if (existing != null) {
            throw new BookAlreadyPurchasedException(
                    "User already purchased this book for userId: " + dto.getUserId()
            );
        }

        PurchaseLog entity = new PurchaseLog(dto.getUserId(), dto.getInventoryId());
        PurchaseLog saved = repo.save(entity);

        return mapToDTO(saved);
    }

    // ✅ GET ALL (FIXED — NO EXCEPTION)
    @Override
    public List<PurchaseLogResponseDTO> getAll() {

        List<PurchaseLog> purchases = repo.findAll();

        List<PurchaseLogResponseDTO> list = new ArrayList<>();

        for (PurchaseLog purchase : purchases) {
            list.add(mapToDTO(purchase));
        }

        return list; // ✅ always return list (even empty)
    }

    // ✅ DELETE
    @Override
    public String delete(Integer userId, Integer inventoryId) {

        if (userId == null || inventoryId == null) {
            throw new IllegalArgumentException("Invalid delete data");
        }

        PurchaseLogId id = new PurchaseLogId(userId, inventoryId);

        PurchaseLog purchase = repo.findById(id)
                .orElseThrow(() ->
                        new PurchaseNotFoundException(
                                "Purchase not found for userId: " + userId + " inventoryId: " + inventoryId
                        )
                );

        repo.delete(purchase);

        return "Deleted Successfully for userId: " + userId + " inventoryId: " + inventoryId;
    }

    // ✅ MAPPER
    private PurchaseLogResponseDTO mapToDTO(PurchaseLog entity) {

        PurchaseLogResponseDTO dto = new PurchaseLogResponseDTO();
        dto.setUserId(entity.getUserId());
        dto.setInventoryId(entity.getInventoryId());

        return dto;
    }
}