package com.sprint.BookInventoryMgmt.orderMgmt.service;

import com.sprint.BookInventoryMgmt.orderMgmt.dto.responseDto.PurchaseLogResponseDTO;
import com.sprint.BookInventoryMgmt.orderMgmt.entity.PurchaseLog;
import com.sprint.BookInventoryMgmt.orderMgmt.exceptions.PurchaseNotFoundException;
import com.sprint.BookInventoryMgmt.orderMgmt.repository.IPurchaseLogRepository;

import com.sprint.BookInventoryMgmt.orderMgmt.dto.requestDto.PurchaseLogRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseLogServiceImpl implements IPurchaseLogService {

    @Autowired
     IPurchaseLogRepository repo;

    public PurchaseLogServiceImpl(){}

    public PurchaseLogServiceImpl(IPurchaseLogRepository repo) {

        this.repo = repo;
    }

    @Override
    public PurchaseLogResponseDTO addPurchase(PurchaseLogRequestDTO dto) {

        PurchaseLog entity = new PurchaseLog();
        entity.setUserId(dto.getUserId());
        entity.setInventoryId(dto.getInventoryId());

        PurchaseLog saved = repo.save(entity);

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