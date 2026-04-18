package com.sprint.BookInventoryMgmt.ordermgmt.service;

import com.sprint.BookInventoryMgmt.ordermgmt.entity.PurchaseLog;
import com.sprint.BookInventoryMgmt.ordermgmt.exception.PurchaseNotFoundException;
import com.sprint.BookInventoryMgmt.ordermgmt.repository.PurchaseLogRepository;

import com.sprint.BookInventoryMgmt.ordermgmt.dto.requestdto.PurchaseLogRequestDTO;
import com.sprint.BookInventoryMgmt.ordermgmt.dto.responsedto.PurchaseLogResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseLogServiceImpl implements PurchaseLogService {

    private final PurchaseLogRepository repo;

    public PurchaseLogServiceImpl(PurchaseLogRepository repo) {
        this.repo = repo;
    }

    @Override
    public PurchaseLogResponseDTO addPurchase(PurchaseLogRequestDTO dto) {

        PurchaseLog entity = new PurchaseLog();
        entity.setUserId(dto.getUserId());
        entity.setInventoryId(dto.getInventoryId());

        PurchaseLog saved = repo.save(entity);

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
    public String delete(Long userId) {

        PurchaseLog purchase = repo.findById(userId)
                .orElseThrow(() ->
                        new PurchaseNotFoundException("Purchase not found: " + userId)
                );

        repo.delete(purchase);
        return "Deleted Successfully for id: " + userId;
    }

    private PurchaseLogResponseDTO mapToDTO(PurchaseLog entity) {
        PurchaseLogResponseDTO dto = new PurchaseLogResponseDTO();
        dto.setUserId(entity.getUserId());
        dto.setInventoryId(entity.getInventoryId());
        return dto;
    }
}