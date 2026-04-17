package com.sprint.BookInventoryMgmt.OrderMgmt.Service;

import com.sprint.BookInventoryMgmt.OrderMgmt.dto.responsedto.PurchaseLogResponseDTO;
import com.sprint.BookInventoryMgmt.OrderMgmt.Entity.PurchaseLog;
import com.sprint.BookInventoryMgmt.OrderMgmt.Exception.PurchaseNotFoundException;
import com.sprint.BookInventoryMgmt.OrderMgmt.Repository.PurchaseLogRepository;

import com.sprint.BookInventoryMgmt.OrderMgmt.dto.requestdto.PurchaseLogRequestDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseLogServiceImpl implements PurchaseLogService {

    private final PurchaseLogRepository repo;

    public PurchaseLogServiceImpl(PurchaseLogRepository repo) {
        this.repo = repo;
    }

    @Override
    public PurchaseLogResponseDTO addPurchase(PurchaseLogRequestDTO dto) {

        // DTO → Entity
        PurchaseLog entity = new PurchaseLog();
        entity.setUserId(dto.getUserId());
        entity.setInventoryId(dto.getInventoryId());

        PurchaseLog saved = repo.save(entity);

        // Entity → DTO
        PurchaseLogResponseDTO response = new PurchaseLogResponseDTO();
        response.setUserId(saved.getUserId());
        response.setInventoryId(saved.getInventoryId());

        return response;
    }

    @Override
    public List<PurchaseLogResponseDTO> getAll() {

        List<PurchaseLog> purchases = repo.findAll();
        List<PurchaseLogResponseDTO> responseList = new ArrayList<>();

        for (PurchaseLog p : purchases) {
            PurchaseLogResponseDTO dto = new PurchaseLogResponseDTO();
            dto.setUserId(p.getUserId());
            dto.setInventoryId(p.getInventoryId());

            responseList.add(dto);
        }

        return responseList;
    }

    @Override
    public String delete(Long userId) {

        PurchaseLog purchase = repo.findById(userId)
                .orElseThrow(() ->
                        new PurchaseNotFoundException("Purchase not found for id: " + userId)
                );

        repo.delete(purchase);

        return "Deleted Successfully for id: " + userId;
    }
}