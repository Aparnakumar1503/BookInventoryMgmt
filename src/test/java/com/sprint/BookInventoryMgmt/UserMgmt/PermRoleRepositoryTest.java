package com.sprint.BookInventoryMgmt.UserMgmt;

import com.sprint.BookInventoryMgmt.UserMgmt.Entity.PermRole;
import com.sprint.BookInventoryMgmt.UserMgmt.Repository.PermRoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class PermRoleRepositoryTest {
    @Autowired
    private PermRoleRepository roleRepository;

    @Test
    void testReadRoles() {
        List<PermRole> roles = roleRepository.findAll();

        assertNotNull(roles);
        assertTrue(roles.size() > 0);
    }
}
