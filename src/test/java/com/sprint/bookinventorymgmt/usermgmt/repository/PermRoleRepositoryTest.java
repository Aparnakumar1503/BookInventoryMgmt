package com.sprint.bookinventorymgmt.usermgmt.repository;

import com.sprint.bookinventorymgmt.usermgmt.entity.PermRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class PermRoleRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private IPermRoleRepository repository;

    @Test
    void testSavePermRole() {
        PermRole role = new PermRole(null, "Admin");
        PermRole saved = repository.saveAndFlush(role);

        assertNotNull(saved);
        assertNotNull(saved.getRoleNumber()); // auto-generated
        assertEquals("Admin", saved.getPermRole());
    }

    @Test
    void testFindByPermRole() {
        repository.saveAndFlush(new PermRole(null, "Guest"));

        Optional<PermRole> result = repository.findByPermRole("Guest");

        assertTrue(result.isPresent());
        assertEquals("Guest", result.get().getPermRole());
    }

    @Test
    void testExistsByPermRole() {
        repository.saveAndFlush(new PermRole(null, "StoreOwner"));

        boolean exists = repository.existsByPermRole("StoreOwner");

        assertTrue(exists);
    }

    // Custom Query 1 — search by keyword
    @Test
    void testSearchByPermRole() {
        repository.saveAndFlush(new PermRole(null, "RegisteredUser"));
        repository.saveAndFlush(new PermRole(null, "Admin"));

        List<PermRole> result = repository.searchByPermRole("user");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("RegisteredUser", result.get(0).getPermRole());
    }

    // Custom Query 2 — count all roles
    @Test
    void testCountAllRoles() {
        repository.saveAndFlush(new PermRole(null, "Guest"));
        repository.saveAndFlush(new PermRole(null, "Admin"));

        Long count = repository.countAllRoles();

        assertNotNull(count);
        assertEquals(2L, count);
    }

    // Custom Query 3 — update role name
    @Test
    void testUpdatePermRole() {
        PermRole saved = repository.saveAndFlush(new PermRole(null, "OldRole"));

        int updated = repository.updatePermRole(saved.getRoleNumber(), "NewRole");

        assertEquals(1, updated);
        entityManager.clear();

        Optional<PermRole> result = repository.findById(saved.getRoleNumber());
        assertTrue(result.isPresent());
        assertEquals("NewRole", result.get().getPermRole());
    }
}
