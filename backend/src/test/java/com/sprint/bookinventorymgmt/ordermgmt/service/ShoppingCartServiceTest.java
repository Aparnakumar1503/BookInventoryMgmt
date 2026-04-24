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

    private ShoppingCartRequestDTO request(int userId, String isbn) {
        ShoppingCartRequestDTO dto = new ShoppingCartRequestDTO();
        dto.setUserId(userId);
        dto.setIsbn(isbn);
        return dto;
    }

    @Test
    void addCart_persistsCartItem() {
        ShoppingCartResponseDTO result = service.addCart(request(31, "CART-31"));

        assertEquals(31, result.getUserId());
        assertEquals("CART-31", result.getIsbn());
    }

    @Test
    void getAll_returnsInsertedCartItems() {
        service.addCart(request(32, "CART-32"));

        assertFalse(service.getAll().isEmpty());
    }

    @Test
    void getByUserId_returnsItemsForSpecificUser() {
        service.addCart(request(33, "CART-33"));

        List<ShoppingCartResponseDTO> carts = service.getByUserId(33);

        assertEquals(1, carts.size());
        assertEquals("CART-33", carts.get(0).getIsbn());
    }

    @Test
    void delete_removesExistingCartItem() {
        service.addCart(request(34, "CART-34"));

        String result = service.delete(34, "CART-34");

        assertTrue(result.contains("Cart Deleted Successfully"));
    }

    @Test
    void getAll_edgeCase_returnsNonNullList() {
        assertNotNull(service.getAll());
    }

    @Test
    void addCart_rejectsNullOrIncompleteRequest() {
        assertThrows(EmptyCartException.class, () -> service.addCart(null));
        assertThrows(EmptyCartException.class, () -> service.addCart(new ShoppingCartRequestDTO()));
    }

    @Test
    void addCart_rejectsDuplicateItem() {
        service.addCart(request(35, "CART-35"));

        assertThrows(DuplicateCartItemException.class, () -> service.addCart(request(35, "CART-35")));
    }

    @Test
    void delete_throwsWhenCartItemDoesNotExist() {
        assertThrows(ShoppingCartNotFoundException.class, () -> service.delete(999, "NOTEXIST"));
    }
}
