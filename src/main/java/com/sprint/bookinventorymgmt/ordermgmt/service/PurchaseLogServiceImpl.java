package com.sprint.bookinventorymgmt.ordermgmt.service;

import com.sprint.bookinventorymgmt.ordermgmt.entity.PurchaseLog;
import com.sprint.bookinventorymgmt.ordermgmt.entity.PurchaseLogId;
import com.sprint.bookinventorymgmt.ordermgmt.exceptions.*;
import com.sprint.bookinventorymgmt.ordermgmt.repository.IPurchaseLogRepository;
import com.sprint.bookinventorymgmt.ordermgmt.dto.requestDto.PurchaseLogRequestDTO;
import com.sprint.bookinventorymgmt.ordermgmt.dto.responseDto.PurchaseLogResponseDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseLogServiceImpl implements IPurchaseLogService {

    @Autowired
    private IPurchaseLogRepository repo;

    public PurchaseLogServiceImpl(IPurchaseLogRepository repo) {
        this.repo = repo;
    }

    @Override
    public PurchaseLogResponseDTO addPurchase(PurchaseLogRequestDTO dto) {

        if (dto == null || dto.getUserId() == null || dto.getInventoryId() == null) {
            throw new OrderProcessingException("UserId and inventoryId are required");
        }

        PurchaseLog existing = repo.findByUserIdAndInventoryId(dto.getUserId(), dto.getInventoryId());
        if (existing != null) {
            throw new BookAlreadyPurchasedException(
                    "Already purchased for userId: " + dto.getUserId());
        }

        PurchaseLog entity = new PurchaseLog(dto.getUserId(), dto.getInventoryId());

        PurchaseLog saved;
        try {
            saved = repo.save(entity);
        } catch (Exception e) {
            throw new CheckoutFailedException("Failed to create purchase");
        }

        return mapToDTO(saved);
    }

    @Override
    public List<PurchaseLogResponseDTO> getAll() {

        List<PurchaseLog> list = repo.findAll();
        List<PurchaseLogResponseDTO> response = new ArrayList<>();

        for (PurchaseLog p : list) {
            response.add(mapToDTO(p));
        }

        return response;
    }

    @Override
    public List<PurchaseLogResponseDTO> getByUserId(Integer userId) {

        List<PurchaseLog> list = repo.findByIdUserId(userId);
        List<PurchaseLogResponseDTO> response = new ArrayList<>();

        for (PurchaseLog p : list) {
            response.add(mapToDTO(p));
        }

        return response;
    }

    @Override
    public String delete(Integer userId, Integer inventoryId) {

        PurchaseLogId id = new PurchaseLogId(userId, inventoryId);

        PurchaseLog purchase = repo.findById(id)
                .orElseThrow(() ->
                        new PurchaseNotFoundException("Purchase not found"));

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