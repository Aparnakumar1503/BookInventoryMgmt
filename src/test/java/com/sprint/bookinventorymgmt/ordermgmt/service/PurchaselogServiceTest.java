package com.sprint.bookinventorymgmt.ordermgmt.service;

import com.sprint.bookinventorymgmt.inventorymgmt.entity.Inventory;
import com.sprint.bookinventorymgmt.inventorymgmt.exceptions.InventoryNotFoundException;
import com.sprint.bookinventorymgmt.inventorymgmt.exceptions.InventoryPurchaseException;
import com.sprint.bookinventorymgmt.inventorymgmt.repository.IInventoryRepository;
import com.sprint.bookinventorymgmt.ordermgmt.dto.requestDto.PurchaseLogRequestDTO;
import com.sprint.bookinventorymgmt.ordermgmt.dto.responseDto.PurchaseLogResponseDTO;
import com.sprint.bookinventorymgmt.ordermgmt.dto.responseDto.ShoppingCartResponseDTO;
import com.sprint.bookinventorymgmt.ordermgmt.exceptions.BookAlreadyPurchasedException;
import com.sprint.bookinventorymgmt.ordermgmt.exceptions.OrderProcessingException;
import com.sprint.bookinventorymgmt.ordermgmt.exceptions.PurchaseNotFoundException;
import com.sprint.bookinventorymgmt.ordermgmt.entity.ShoppingCart;
import com.sprint.bookinventorymgmt.ordermgmt.repository.IShoppingCartRepository;
import org.junit.jupiter.api.BeforeEach;
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
    @Autowired
    private IInventoryRepository inventoryRepository;
    @Autowired
    private IShoppingCartRepository shoppingCartRepository;
    @Autowired
    private IShoppingCartService shoppingCartService;

    private PurchaseLogRequestDTO request(int userId, int inventoryId) {
        PurchaseLogRequestDTO dto = new PurchaseLogRequestDTO();
        dto.setUserId(userId);
        dto.setInventoryId(inventoryId);
        return dto;
    }

    @BeforeEach
    void clearState() {
        shoppingCartRepository.deleteAll();
        inventoryRepository.deleteAll();
    }

    private Inventory createInventory(String isbn) {
        return inventoryRepository.save(new Inventory(null, isbn, 1, false));
    }

    @Test
    void addPurchase_persistsPurchase() {
        Inventory inventory = createInventory("1111111111111");
        PurchaseLogResponseDTO result = service.addPurchase(request(21, inventory.getInventoryId()));

        assertEquals(21, result.getUserId());
        assertEquals(inventory.getInventoryId(), result.getInventoryId());
        assertTrue(inventoryRepository.findById(inventory.getInventoryId()).orElseThrow().getPurchased());
    }

    @Test
    void getAll_returnsInsertedPurchases() {
        Inventory inventory = createInventory("2222222222222");
        service.addPurchase(request(22, inventory.getInventoryId()));

        assertFalse(service.getAll().isEmpty());
    }

    @Test
    void getByUserId_returnsPurchasesForSpecificUser() {
        Inventory inventory = createInventory("3333333333333");
        service.addPurchase(request(23, inventory.getInventoryId()));

        List<PurchaseLogResponseDTO> purchases = service.getByUserId(23);

        assertEquals(1, purchases.size());
        assertEquals(23, purchases.get(0).getUserId());
    }

    @Test
    void delete_removesExistingPurchase() {
        Inventory inventory = createInventory("4444444444444");
        service.addPurchase(request(24, inventory.getInventoryId()));

        String result = service.delete(24, inventory.getInventoryId());

        assertTrue(result.contains("Deleted Successfully"));
    }

    @Test
    void addPurchase_mapsResponseFields() {
        Inventory inventory = createInventory("5555555555555");
        PurchaseLogResponseDTO result = service.addPurchase(request(25, inventory.getInventoryId()));

        assertNotNull(result);
        assertEquals(inventory.getInventoryId(), result.getInventoryId());
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
        Inventory inventory = createInventory("6666666666666");
        service.addPurchase(request(26, inventory.getInventoryId()));

        assertThrows(BookAlreadyPurchasedException.class, () -> service.addPurchase(request(26, inventory.getInventoryId())));
    }

    @Test
    void getCartByUser_hidesAndRemovesPurchasedItems() {
        Inventory inventory = createInventory("6666666666667");
        service.addPurchase(request(27, inventory.getInventoryId()));
        shoppingCartRepository.save(new ShoppingCart(27, inventory.getIsbn()));

        List<ShoppingCartResponseDTO> cartItems = shoppingCartService.getByUserId(27);

        assertTrue(cartItems.isEmpty());
        assertEquals(0L, shoppingCartRepository.countByUserId(27));
    }

    @Test
    void delete_throwsWhenPurchaseDoesNotExist() {
        assertThrows(PurchaseNotFoundException.class, () -> service.delete(999, 9999));
    }

    @Test
    void addPurchase_rejectsMissingInventory() {
        assertThrows(InventoryNotFoundException.class, () -> service.addPurchase(request(30, 999999)));
    }

    @Test
    void addPurchase_rejectsAlreadyPurchasedInventory() {
        Inventory inventory = inventoryRepository.save(new Inventory(null, "7777777777777", 1, true));

        assertThrows(InventoryPurchaseException.class, () -> service.addPurchase(request(31, inventory.getInventoryId())));
    }

    @Test
    void addPurchase_removesMatchingCartItem() {
        Inventory inventory = createInventory("8888888888888");
        shoppingCartRepository.save(new ShoppingCart(32, inventory.getIsbn()));

        service.addPurchase(request(32, inventory.getInventoryId()));

        assertEquals(0L, shoppingCartRepository.countByUserId(32));
    }
}
