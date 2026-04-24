package com.sprint.bookinventorymgmt.ordermgmt.service;

import com.sprint.bookinventorymgmt.ordermgmt.dto.requestDto.PurchaseLogRequestDTO;
import com.sprint.bookinventorymgmt.ordermgmt.dto.responseDto.PurchaseLogResponseDTO;
import com.sprint.bookinventorymgmt.ordermgmt.exceptions.BookAlreadyPurchasedException;
import com.sprint.bookinventorymgmt.ordermgmt.exceptions.OrderProcessingException;
import com.sprint.bookinventorymgmt.ordermgmt.exceptions.PurchaseNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class PurchaseLogServiceTest {

    @Autowired
    private IPurchaseLogService service;

    @Test
    void addPurchase_success() {
        PurchaseLogRequestDTO dto = request(1, 100);

        PurchaseLogResponseDTO result = service.addPurchase(dto);

        assertNotNull(result);
        assertEquals(1, result.getUserId());
        assertEquals(100, result.getInventoryId());
    }

    @Test
    void getAll_returnsCreatedPurchases() {
        service.addPurchase(request(2, 200));

        List<PurchaseLogResponseDTO> result = service.getAll();

        assertFalse(result.isEmpty());
    }

    @Test
    void getByUserId_returnsMatchingPurchases() {
        service.addPurchase(request(3, 300));

        List<PurchaseLogResponseDTO> result = service.getByUserId(3);

        assertFalse(result.isEmpty());
        assertEquals(3, result.get(0).getUserId());
    }

    @Test
    void delete_success() {
        service.addPurchase(request(4, 400));

        String result = service.delete(4, 400);

        assertTrue(result.contains("Deleted"));
    }

    @Test
    void delete_notFound() {
        assertThrows(PurchaseNotFoundException.class, () -> service.delete(999, 9999));
    }

    @Test
    void addPurchase_nullRequest() {
        assertThrows(OrderProcessingException.class, () -> service.addPurchase(null));
    }

    @Test
    void addPurchase_invalidRequest() {
        assertThrows(OrderProcessingException.class, () -> service.addPurchase(new PurchaseLogRequestDTO()));
    }

    @Test
    void addPurchase_duplicateCompositeKey() {
        PurchaseLogRequestDTO dto = request(11, 1100);
        service.addPurchase(dto);

        assertThrows(BookAlreadyPurchasedException.class, () -> service.addPurchase(request(11, 1100)));
    }

    private PurchaseLogRequestDTO request(int userId, int inventoryId) {
        PurchaseLogRequestDTO dto = new PurchaseLogRequestDTO();
        dto.setUserId(userId);
        dto.setInventoryId(inventoryId);
        return dto;
    }
}
