package com.sprint.bookinventorymgmt.usermgmt;

import com.sprint.bookinventorymgmt.usermgmt.entity.PermRole;
import com.sprint.bookinventorymgmt.usermgmt.repository.IPermRoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class IPermRoleRepositoryTest {
    @Autowired
    private IPermRoleRepository roleRepository;

    @Test
    void testSaveAndReadRoles() {

        // CREATE
        PermRole role = new PermRole();
        role.setRoleNumber(1);   // 🔥 mandatory
        role.setPermRole("ADMIN");

        roleRepository.save(role);

        // READ
        List<PermRole> roles = roleRepository.findAll();

        // ASSERT
        assertNotNull(roles);
        assertEquals(1, roles.size());
        assertEquals("ADMIN", roles.get(0).getPermRole());
    }
}
