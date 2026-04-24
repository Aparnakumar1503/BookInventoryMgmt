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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ShoppingCartServiceTest {

    @Autowired
    private IShoppingCartService service;

    @Test
    void addCart_success() {
        ShoppingCartResponseDTO result = service.addCart(request(1, "ISBN1"));

        assertNotNull(result);
        assertEquals("ISBN1", result.getIsbn());
    }

    @Test
    void getAll_returnsCreatedItems() {
        service.addCart(request(2, "A"));

        List<ShoppingCartResponseDTO> result = service.getAll();

        assertFalse(result.isEmpty());
    }

    @Test
    void getByUserId_returnsMatchingItems() {
        service.addCart(request(3, "B"));

        List<ShoppingCartResponseDTO> result = service.getByUserId(3);

        assertFalse(result.isEmpty());
        assertEquals(3, result.get(0).getUserId());
    }

    @Test
    void delete_success() {
        service.addCart(request(4, "C"));

        String result = service.delete(4, "C");

        assertTrue(result.contains("Deleted"));
    }

    @Test
    void delete_notFound() {
        assertThrows(ShoppingCartNotFoundException.class, () -> service.delete(999, "NOTEXIST"));
    }

    @Test
    void addCart_nullRequest() {
        assertThrows(EmptyCartException.class, () -> service.addCart(null));
    }

    @Test
    void addCart_invalidRequest() {
        assertThrows(EmptyCartException.class, () -> service.addCart(new ShoppingCartRequestDTO()));
    }

    @Test
    void addCart_duplicateItem() {
        ShoppingCartRequestDTO dto = request(12, "DUP-CART");
        service.addCart(dto);

        assertThrows(DuplicateCartItemException.class, () -> service.addCart(dto));
    }

    private ShoppingCartRequestDTO request(int userId, String isbn) {
        ShoppingCartRequestDTO dto = new ShoppingCartRequestDTO();
        dto.setUserId(userId);
        dto.setIsbn(isbn);
        return dto;
    }
}
