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
    // checking is there a role name with guest
    @Test
    void derivedQueries_findByPermRole_andExistsByPermRole_work() {
        repository.saveAndFlush(new PermRole(null, "Guest"));

        assertTrue(repository.findByPermRole("Guest").isPresent());
        assertTrue(repository.existsByPermRole("Guest"));// does this role exists
    }

    @Test
    void customQuery_searchByPermRole_returnsKeywordMatches() {
        repository.saveAndFlush(new PermRole(null, "RegisteredUser"));
        repository.saveAndFlush(new PermRole(null, "Admin"));
        //Find roles where name contains "user" expecting size to be 1
        assertEquals(1, repository.searchByPermRole("user").size());
    }
}
