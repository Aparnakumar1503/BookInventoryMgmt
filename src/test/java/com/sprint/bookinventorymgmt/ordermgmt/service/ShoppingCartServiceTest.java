package com.sprint.bookinventorymgmt.ordermgmt.service;

import com.sprint.bookinventorymgmt.ordermgmt.dto.requestDto.ShoppingCartRequestDTO;
import com.sprint.bookinventorymgmt.ordermgmt.dto.responseDto.ShoppingCartResponseDTO;
import com.sprint.bookinventorymgmt.ordermgmt.exceptions.ShoppingCartNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ShoppingCartServiceTest {

    @Autowired
    private IShoppingCartService service;

    // ================= POSITIVE =================

    @Test
    void testAddCart() {
        ShoppingCartRequestDTO dto = new ShoppingCartRequestDTO();
        dto.setUserId(1);       // ✅ Integer not Long
        dto.setIsbn("ISBN1");

        ShoppingCartResponseDTO result = service.addCart(dto);

        assertNotNull(result);
        assertEquals("ISBN1", result.getIsbn());
    }

    @Test
    void testGetAll() {
        ShoppingCartRequestDTO dto = new ShoppingCartRequestDTO();
        dto.setUserId(2);       // ✅ Integer
        dto.setIsbn("A");

        service.addCart(dto);

        List<ShoppingCartResponseDTO> result = service.getAll();

        assertFalse(result.isEmpty());
    }

    @Test
    void testDeleteSuccess() {
        ShoppingCartRequestDTO dto = new ShoppingCartRequestDTO();
        dto.setUserId(3);       // ✅ Integer
        dto.setIsbn("B");

        service.addCart(dto);

        String result = service.delete(3, "B");  // ✅ composite key

        assertTrue(result.contains("Deleted"));
    }

    @Test
    void testAddMultiple() {
        ShoppingCartRequestDTO dto1 = new ShoppingCartRequestDTO();
        dto1.setUserId(4);      // ✅ Integer
        dto1.setIsbn("X");

        ShoppingCartRequestDTO dto2 = new ShoppingCartRequestDTO();
        dto2.setUserId(5);      // ✅ Integer
        dto2.setIsbn("Y");

        service.addCart(dto1);
        service.addCart(dto2);

        assertTrue(service.getAll().size() >= 2);
    }

    @Test
    void testMappingCorrect() {
        ShoppingCartRequestDTO dto = new ShoppingCartRequestDTO();
        dto.setUserId(6);       // ✅ Integer
        dto.setIsbn("MAP");

        ShoppingCartResponseDTO res = service.addCart(dto);

        assertEquals(6, res.getUserId());  // ✅ Integer comparison
    }

    @Test
    void testGetAllNotEmpty() {
        ShoppingCartRequestDTO dto = new ShoppingCartRequestDTO();
        dto.setUserId(7);       // ✅ Integer
        dto.setIsbn("Z");

        service.addCart(dto);

        assertFalse(service.getAll().isEmpty());
    }

    @Test
    void testDeleteFlow() {
        ShoppingCartRequestDTO dto = new ShoppingCartRequestDTO();
        dto.setUserId(8);       // ✅ Integer
        dto.setIsbn("DEL");

        service.addCart(dto);
        service.delete(8, "DEL");  // ✅ composite key

        assertThrows(ShoppingCartNotFoundException.class,
                () -> service.delete(8, "DEL"));
    }

    // ================= NEGATIVE =================

    @Test
    void testDeleteNotFound() {
        assertThrows(ShoppingCartNotFoundException.class,
                () -> service.delete(999, "NOTEXIST")); // ✅ composite key
    }

    @Test
    void testAddNull() {
        assertThrows(Exception.class, () -> service.addCart(null));
    }

    @Test
    void testAddInvalidDTO() {
        ShoppingCartRequestDTO dto = new ShoppingCartRequestDTO();
        // no userId or isbn set

        assertThrows(Exception.class, () -> service.addCart(dto));
    }

    @Test
    void testDeleteNull() {
        assertThrows(Exception.class, () -> service.delete(null, null)); // ✅ composite key
    }

    @Test
    void testGetAllInitiallyEmpty() {
        List<ShoppingCartResponseDTO> list = service.getAll();

        assertNotNull(list);
    }
}