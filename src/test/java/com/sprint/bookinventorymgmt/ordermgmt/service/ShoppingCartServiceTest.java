package com.sprint.bookinventorymgmt.ordermgmt.service;

import com.sprint.bookinventorymgmt.ordermgmt.dto.requestDto.ShoppingCartRequestDTO;
import com.sprint.bookinventorymgmt.ordermgmt.dto.responseDto.ShoppingCartResponseDTO;
import com.sprint.bookinventorymgmt.ordermgmt.exceptions.DuplicateCartItemException;
import com.sprint.bookinventorymgmt.ordermgmt.exceptions.EmptyCartException;
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

    @Test
    void testAddCart() {
        ShoppingCartRequestDTO dto = new ShoppingCartRequestDTO();
        dto.setUserId(1);
        dto.setIsbn("ISBN1");

        ShoppingCartResponseDTO result = service.addCart(dto);

        assertNotNull(result);
        assertEquals("ISBN1", result.getIsbn());
    }

    @Test
    void testGetAll() {
        ShoppingCartRequestDTO dto = new ShoppingCartRequestDTO();
        dto.setUserId(2);
        dto.setIsbn("A");

        service.addCart(dto);

        List<ShoppingCartResponseDTO> result = service.getAll();

        assertFalse(result.isEmpty());
    }

    @Test
    void testDeleteSuccess() {
        ShoppingCartRequestDTO dto = new ShoppingCartRequestDTO();
        dto.setUserId(3);
        dto.setIsbn("B");

        service.addCart(dto);

        String result = service.delete(3, "B");

        assertTrue(result.contains("Deleted"));
    }

    @Test
    void testAddMultiple() {
        ShoppingCartRequestDTO dto1 = new ShoppingCartRequestDTO();
        dto1.setUserId(4);
        dto1.setIsbn("X");

        ShoppingCartRequestDTO dto2 = new ShoppingCartRequestDTO();
        dto2.setUserId(5);
        dto2.setIsbn("Y");

        service.addCart(dto1);
        service.addCart(dto2);

        assertTrue(service.getAll().size() >= 2);
    }

    @Test
    void testMappingCorrect() {
        ShoppingCartRequestDTO dto = new ShoppingCartRequestDTO();
        dto.setUserId(6);
        dto.setIsbn("MAP");

        ShoppingCartResponseDTO res = service.addCart(dto);

        assertEquals(6, res.getUserId());
    }

    @Test
    void testGetAllNotEmpty() {
        ShoppingCartRequestDTO dto = new ShoppingCartRequestDTO();
        dto.setUserId(7);
        dto.setIsbn("Z");

        service.addCart(dto);

        assertFalse(service.getAll().isEmpty());
    }

    @Test
    void testDeleteFlow() {
        ShoppingCartRequestDTO dto = new ShoppingCartRequestDTO();
        dto.setUserId(8);
        dto.setIsbn("DEL");

        service.addCart(dto);
        service.delete(8, "DEL");

        assertThrows(ShoppingCartNotFoundException.class, () -> service.delete(8, "DEL"));
    }

    @Test
    void testDeleteNotFound() {
        assertThrows(ShoppingCartNotFoundException.class, () -> service.delete(999, "NOTEXIST"));
    }

    @Test
    void testAddNull() {
        assertThrows(EmptyCartException.class, () -> service.addCart(null));
    }

    @Test
    void testAddInvalidDTO() {
        ShoppingCartRequestDTO dto = new ShoppingCartRequestDTO();

        assertThrows(EmptyCartException.class, () -> service.addCart(dto));
    }

    @Test
    void testDeleteNull() {
        assertThrows(Exception.class, () -> service.delete(null, null));
    }

    @Test
    void testGetAllInitiallyEmpty() {
        List<ShoppingCartResponseDTO> list = service.getAll();

        assertNotNull(list);
    }

    @Test
    void testAddDuplicateCartItem() {
        ShoppingCartRequestDTO dto = new ShoppingCartRequestDTO();
        dto.setUserId(12);
        dto.setIsbn("DUP-CART");

        service.addCart(dto);

        assertThrows(DuplicateCartItemException.class, () -> service.addCart(dto));
    }
}
