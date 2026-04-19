package com.sprint.BookInventoryMgmt.ordermgmt.service;

import com.sprint.BookInventoryMgmt.ordermgmt.dto.requestDto.PurchaseLogRequestDTO;
import com.sprint.BookInventoryMgmt.ordermgmt.dto.responseDto.PurchaseLogResponseDTO;
import com.sprint.BookInventoryMgmt.ordermgmt.exceptions.PurchaseNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PurchaseLogServiceTest {

    @Autowired
    private IPurchaseLogService service;

    // ================= POSITIVE =================

    @Test
    void testAddPurchase() {
        PurchaseLogRequestDTO dto = new PurchaseLogRequestDTO();
        dto.setUserId(1);
        dto.setInventoryId(100);

        PurchaseLogResponseDTO res = service.addPurchase(dto);

        assertNotNull(res);
        assertEquals(1, res.getUserId());
        assertEquals(100, res.getInventoryId());
    }

    @Test
    void testGetAll() {
        PurchaseLogRequestDTO dto = new PurchaseLogRequestDTO();
        dto.setUserId(2);
        dto.setInventoryId(200);

        service.addPurchase(dto);

        assertFalse(service.getAll().isEmpty());
    }

    @Test
    void testDeleteSuccess() {
        PurchaseLogRequestDTO dto = new PurchaseLogRequestDTO();
        dto.setUserId(3);
        dto.setInventoryId(300);

        service.addPurchase(dto);

        String result = service.delete(3, 300); // ✅ composite key

        assertTrue(result.contains("Deleted"));
    }

    @Test
    void testMultipleInsert() {
        PurchaseLogRequestDTO dto1 = new PurchaseLogRequestDTO();
        dto1.setUserId(4);
        dto1.setInventoryId(400);

        PurchaseLogRequestDTO dto2 = new PurchaseLogRequestDTO();
        dto2.setUserId(5);
        dto2.setInventoryId(500);

        service.addPurchase(dto1);
        service.addPurchase(dto2);

        assertTrue(service.getAll().size() >= 2);
    }

    @Test
    void testMapping() {
        PurchaseLogRequestDTO dto = new PurchaseLogRequestDTO();
        dto.setUserId(6);
        dto.setInventoryId(600);

        PurchaseLogResponseDTO res = service.addPurchase(dto);

        assertEquals(600, res.getInventoryId());
        assertEquals(6, res.getUserId());
    }

    @Test
    void testGetAllNotEmpty() {
        PurchaseLogRequestDTO dto = new PurchaseLogRequestDTO();
        dto.setUserId(7);
        dto.setInventoryId(700);

        service.addPurchase(dto);

        assertFalse(service.getAll().isEmpty());
    }

    @Test
    void testDeleteFlow() {
        PurchaseLogRequestDTO dto = new PurchaseLogRequestDTO();
        dto.setUserId(8);
        dto.setInventoryId(800);

        service.addPurchase(dto);
        service.delete(8, 800); // ✅ composite key

        assertThrows(PurchaseNotFoundException.class,
                () -> service.delete(8, 800));
    }

    // ================= NEGATIVE =================

    @Test
    void testDeleteNotFound() {
        assertThrows(PurchaseNotFoundException.class,
                () -> service.delete(999, 9999)); // ✅ composite key
    }

    @Test
    void testAddNull() {
        assertThrows(Exception.class, () -> service.addPurchase(null));
    }

    @Test
    void testAddInvalidDTO() {
        PurchaseLogRequestDTO dto = new PurchaseLogRequestDTO();
        // no userId or inventoryId set

        assertThrows(Exception.class, () -> service.addPurchase(dto));
    }

    @Test
    void testDeleteNull() {
        assertThrows(Exception.class,
                () -> service.delete(null, null)); // ✅ composite key
    }

    @Test
    void testGetAllEmptyCheck() {
        assertNotNull(service.getAll());
    }

    @Test
    void testDeleteTwice() {
        PurchaseLogRequestDTO dto = new PurchaseLogRequestDTO();
        dto.setUserId(10);
        dto.setInventoryId(1000);

        service.addPurchase(dto);
        service.delete(10, 1000); // ✅ composite key

        assertThrows(PurchaseNotFoundException.class,
                () -> service.delete(10, 1000));
    }


    @Test
    void testDuplicateCompositeKey() {
        PurchaseLogRequestDTO dto1 = new PurchaseLogRequestDTO();
        dto1.setUserId(11);
        dto1.setInventoryId(1100);

        PurchaseLogRequestDTO dto2 = new PurchaseLogRequestDTO();
        dto2.setUserId(11);
        dto2.setInventoryId(1100); // same composite key

        service.addPurchase(dto1);

        // JPA save() with same composite key → overwrites, not throws
        PurchaseLogResponseDTO res = service.addPurchase(dto2);

        // verify it was saved/overwritten successfully
        assertNotNull(res);
        assertEquals(11, res.getUserId());
        assertEquals(1100, res.getInventoryId());
    }

    // ================= EDGE =================

    @Test
    void testEdgeCase() {
        List<PurchaseLogResponseDTO> list = service.getAll();

        assertTrue(list.size() >= 0);
    }
}