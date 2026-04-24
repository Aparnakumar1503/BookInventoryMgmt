package com.sprint.bookinventorymgmt.inventorymgmt.service;

import com.sprint.bookinventorymgmt.inventorymgmt.entity.Inventory;
import com.sprint.bookinventorymgmt.inventorymgmt.exceptions.*;
import com.sprint.bookinventorymgmt.inventorymgmt.repository.IInventoryRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceImplTest {

    @Mock
    IInventoryRepository repository;

    @InjectMocks
    InventoryServiceImpl service;

    Inventory inventory;

    @BeforeEach
    void setup() {

        inventory = new Inventory();
        inventory.setInventoryId(1);
        inventory.setIsbn("12345");
        inventory.setRanks(1);
        inventory.setPurchased(false);
    }

    // POSITIVE 1
    @Test
    void saveInventorySuccess() {

        when(repository.findByIsbn("12345"))
                .thenReturn(new ArrayList<>());

        when(repository.save(any()))
                .thenReturn(inventory);

        assertNotNull(service.saveInventory(inventory));
    }

    // POSITIVE 2
    @Test
    void getByIdSuccess() {

        when(repository.findById(1))
                .thenReturn(Optional.of(inventory));

        assertEquals(1,
                service.getById(1).getInventoryId());
    }

    // POSITIVE 3
    @Test
    void purchaseSuccess() {

        when(repository.findById(1))
                .thenReturn(Optional.of(inventory));

        when(repository.save(any()))
                .thenReturn(inventory);

        assertTrue(
                service.markAsPurchased(1)
                        .getPurchased()
        );
    }

    // POSITIVE 4
    @Test
    void deleteSuccess() {

        when(repository.findById(1))
                .thenReturn(Optional.of(inventory));

        assertEquals(
                200,
                service.deleteInventory(1)
                        .getStatusCode()
        );
    }


    // NEGATIVE 1
    @Test
    void duplicateInventory() {

        when(repository.findByIsbn("12345"))
                .thenReturn(List.of(inventory));

        assertThrows(
                DuplicateInventoryException.class,
                ()->service.saveInventory(inventory)
        );
    }

    // NEGATIVE 2
    @Test
    void invalidInventoryData() {

        inventory.setIsbn(null);

        assertThrows(
                InvalidInventoryDataException.class,
                ()->service.saveInventory(inventory)
        );
    }

    // NEGATIVE 3
    @Test
    void inventoryNotFound() {

        when(repository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(
                InventoryNotFoundException.class,
                ()->service.getById(1)
        );
    }

    // NEGATIVE 4
    @Test
    void alreadyPurchased() {

        inventory.setPurchased(true);

        when(repository.findById(1))
                .thenReturn(Optional.of(inventory));

        assertThrows(
                InventoryPurchaseException.class,
                ()->service.markAsPurchased(1)
        );
    }

}