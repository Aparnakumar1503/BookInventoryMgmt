package com.sprint.bookinventorymgmt.ordermgmt.service;

import com.sprint.bookinventorymgmt.ordermgmt.entity.PurchaseLog;
import com.sprint.bookinventorymgmt.ordermgmt.entity.PurchaseLogId;
import com.sprint.bookinventorymgmt.ordermgmt.exceptions.PurchaseNotFoundException;
import com.sprint.bookinventorymgmt.ordermgmt.repository.IPurchaseLogRepository;
import com.sprint.bookinventorymgmt.ordermgmt.dto.requestDto.PurchaseLogRequestDTO;
import com.sprint.bookinventorymgmt.ordermgmt.dto.responseDto.PurchaseLogResponseDTO;
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
        // use composite key constructor
        PurchaseLog entity = new PurchaseLog(dto.getUserId(), dto.getInventoryId());

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