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

    private PurchaseLogRequestDTO request(int userId, int inventoryId) {
        PurchaseLogRequestDTO dto = new PurchaseLogRequestDTO();
        dto.setUserId(userId);
        dto.setInventoryId(inventoryId);
        return dto;
    }

    @Test
    void addPurchase_persistsPurchase() {
        PurchaseLogResponseDTO result = service.addPurchase(request(21, 1000021));

        assertEquals(21, result.getUserId());
        assertEquals(1000021, result.getInventoryId());
    }

    @Test
    void getAll_returnsInsertedPurchases() {
        service.addPurchase(request(22, 1000022));

        assertFalse(service.getAll().isEmpty());
    }

    @Test
    void getByUserId_returnsPurchasesForSpecificUser() {
        service.addPurchase(request(23, 1000023));

        List<PurchaseLogResponseDTO> purchases = service.getByUserId(23);

        assertEquals(1, purchases.size());
        assertEquals(23, purchases.get(0).getUserId());
    }

    @Test
    void delete_removesExistingPurchase() {
        service.addPurchase(request(24, 1000024));

        String result = service.delete(24, 1000024);

        assertTrue(result.contains("Deleted Successfully"));
    }

    @Test
    void addPurchase_mapsResponseFields() {
        PurchaseLogResponseDTO result = service.addPurchase(request(25, 1000025));

        assertNotNull(result);
        assertEquals(1000025, result.getInventoryId());
    }

    @Test
    void getAll_edgeCase_returnsNonNullList() {
        assertNotNull(service.getAll());
    }

    @Test
    void addPurchase_rejectsNullOrIncompleteRequest() {
        assertThrows(OrderProcessingException.class, () -> service.addPurchase(null));
        assertThrows(OrderProcessingException.class, () -> service.addPurchase(new PurchaseLogRequestDTO()));
    }

    @Test
    void addPurchase_rejectsDuplicateCompositeKey() {
        service.addPurchase(request(26, 1000026));

        assertThrows(BookAlreadyPurchasedException.class, () -> service.addPurchase(request(26, 1000026)));
    }

    @Test
    void delete_throwsWhenPurchaseDoesNotExist() {
        assertThrows(PurchaseNotFoundException.class, () -> service.delete(999, 9999));
    }
}
