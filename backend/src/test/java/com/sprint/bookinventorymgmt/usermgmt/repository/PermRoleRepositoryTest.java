package com.sprint.bookinventorymgmt.usermgmt.repository;

import com.sprint.bookinventorymgmt.usermgmt.entity.PermRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class PermRoleRepositoryTest {

    @Autowired
    private IPermRoleRepository repository;

    @Test
    void derivedQueries_findByPermRole_andExistsByPermRole_work() {
        repository.saveAndFlush(new PermRole(null, "Guest"));

        assertTrue(repository.findByPermRole("Guest").isPresent());
        assertTrue(repository.existsByPermRole("Guest"));
    }

    @Test
    void customQuery_searchByPermRole_returnsKeywordMatches() {
        repository.saveAndFlush(new PermRole(null, "RegisteredUser"));
        repository.saveAndFlush(new PermRole(null, "Admin"));

        assertEquals(1, repository.searchByPermRole("user").size());
    }
}
