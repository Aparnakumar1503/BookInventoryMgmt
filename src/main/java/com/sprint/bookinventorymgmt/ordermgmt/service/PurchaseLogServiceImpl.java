package com.sprint.bookinventorymgmt.ordermgmt.service;

import com.sprint.bookinventorymgmt.ordermgmt.entity.PurchaseLog;
import com.sprint.bookinventorymgmt.ordermgmt.entity.PurchaseLogId;
import com.sprint.bookinventorymgmt.ordermgmt.exceptions.BookAlreadyPurchasedException;
import com.sprint.bookinventorymgmt.ordermgmt.exceptions.CheckoutFailedException;
import com.sprint.bookinventorymgmt.ordermgmt.exceptions.OrderProcessingException;
import com.sprint.bookinventorymgmt.ordermgmt.exceptions.PurchaseNotFoundException;
import com.sprint.bookinventorymgmt.ordermgmt.repository.IPurchaseLogRepository;
import com.sprint.bookinventorymgmt.ordermgmt.dto.requestDto.PurchaseLogRequestDTO;
import com.sprint.bookinventorymgmt.ordermgmt.dto.responseDto.PurchaseLogResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseLogServiceImpl implements IPurchaseLogService {

    @Autowired
    private  IPurchaseLogRepository repo;

    public PurchaseLogServiceImpl(IPurchaseLogRepository repo) {
        this.repo = repo;
    }

    @Override
    public PurchaseLogResponseDTO addPurchase(PurchaseLogRequestDTO dto) {
        if (dto == null || dto.getUserId() == null || dto.getInventoryId() == null) {
            throw new OrderProcessingException("UserId and inventoryId are required to create a purchase");
        }

        PurchaseLog existingPurchase = repo.findByUserIdAndInventoryId(dto.getUserId(), dto.getInventoryId());
        if (existingPurchase != null) {
            throw new BookAlreadyPurchasedException(
                    "Purchase already exists for userId: " + dto.getUserId() + " inventoryId: " + dto.getInventoryId());
        }

        PurchaseLog entity = new PurchaseLog(dto.getUserId(), dto.getInventoryId());

        PurchaseLog saved;
        try {
            saved = repo.save(entity);
        } catch (Exception e) {
            throw new CheckoutFailedException("Failed to create purchase for inventoryId: " + dto.getInventoryId());
        }

        return mapToDTO(saved);
    }

    @Override
    public List<PurchaseLogResponseDTO> getAll() {
        return repo.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public String delete(Integer userId, Integer inventoryId) {
        // composite key — need both userId and inventoryId to identify a purchase
        PurchaseLogId id = new PurchaseLogId(userId, inventoryId);

        PurchaseLog purchase = repo.findById(id)
                .orElseThrow(() ->
                        new PurchaseNotFoundException("Purchase not found for userId: " + userId + " inventoryId: " + inventoryId)
                );

        repo.delete(purchase);
        return "Deleted Successfully for userId: " + userId + " inventoryId: " + inventoryId;
    }

    private PurchaseLogResponseDTO mapToDTO(PurchaseLog entity) {
        PurchaseLogResponseDTO dto = new PurchaseLogResponseDTO();
        dto.setUserId(entity.getUserId());
        dto.setInventoryId(entity.getInventoryId());
        return dto;
    }
}
