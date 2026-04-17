package com.sprint.BookInventoryMgmt.UserMgmt;

import com.sprint.BookInventoryMgmt.UserMgmt.Entity.PermRole;
import com.sprint.BookInventoryMgmt.UserMgmt.Entity.User;
import com.sprint.BookInventoryMgmt.UserMgmt.Repository.PermRoleRepository;
import com.sprint.BookInventoryMgmt.UserMgmt.Repository.UserMgmtRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
public class UserMgmtRepositoryTest {

    @Autowired
    private UserMgmtRepository userRepository;

    @Autowired
    private PermRoleRepository roleRepository;

    @Test
    void testCreateUser() {

        PermRole role = new PermRole();
        role.setPermRole("USER");

        PermRole savedRole = roleRepository.save(role);

        User user = new User();
        user.setFirstName("Champ");
        user.setLastName("User");
        user.setUserName("champ123");
        user.setPassword("pass123");
        user.setRole(savedRole);

        User saved = userRepository.save(user);

        assertNotNull(saved.getUserId());
    }

    @Test
    void testReadUser() {

        PermRole role = new PermRole();
        role.setPermRole("USER");

        PermRole savedRole = roleRepository.save(role);

        User user = new User();
        user.setFirstName("Test");
        user.setLastName("User");
        user.setUserName("test123");
        user.setPassword("pass123");
        user.setRole(savedRole);

        userRepository.save(user);

        User found = userRepository.findAll().get(0);

        assertNotNull(found);
        assertEquals("Test", found.getFirstName());
    }

    @Test
    void testUpdateUser() {

        PermRole role = new PermRole();
        role.setPermRole("USER");

        PermRole savedRole = roleRepository.save(role);

        User user = new User();
        user.setFirstName("OldName");
        user.setLastName("User");
        user.setUserName("update123");
        user.setPassword("pass123");
        user.setRole(savedRole);

        User saved = userRepository.save(user);

        saved.setFirstName("UpdatedName");

        User updated = userRepository.save(saved);

        assertEquals("UpdatedName", updated.getFirstName());
    }

    @Test
    void testDeleteUser() {

        PermRole role = new PermRole();
        role.setPermRole("USER");

        PermRole savedRole = roleRepository.save(role);

        User user = new User();
        user.setFirstName("Delete");
        user.setLastName("Test");
        user.setUserName("delete123");
        user.setPassword("pass123");
        user.setRole(savedRole);

        User saved = userRepository.save(user);

        userRepository.deleteById(saved.getUserId());

        assertFalse(userRepository.existsById(saved.getUserId()));
    }
}

