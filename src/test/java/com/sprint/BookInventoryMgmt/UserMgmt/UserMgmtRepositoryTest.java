package com.sprint.BookInventoryMgmt.UserMgmt;

import com.sprint.BookInventoryMgmt.UserMgmt.Entity.User;
import com.sprint.BookInventoryMgmt.UserMgmt.Repository.UserMgmtRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserMgmtRepositoryTest {
    @Autowired
    private UserMgmtRepository userRepository;

    @Test
    void testCreateUser() {
        User user = new User();
        user.setFirstName("Champ");
        user.setLastName("User");
        user.setUserName("champ123");
        user.setPassword("pass123");

        User saved = userRepository.save(user);

        assertNotNull(saved.getUserId());
    }

    @Test
    void testReadUser() {
        User user = userRepository.findAll().stream().findFirst().orElse(null);
        assertNotNull(user);
    }

    @Test
    void testUpdateUser() {
        User user = userRepository.findAll().stream().findFirst().orElse(null);

        if (user != null) {
            user.setFirstName("UpdatedName");
            User updated = userRepository.save(user);

            assertEquals("UpdatedName", updated.getFirstName());
        }
    }

    @Test
    void testDeleteUser() {
        User user = userRepository.findAll().stream().findFirst().orElse(null);

        if (user != null) {
            userRepository.deleteById(user.getUserId());

            boolean exists = userRepository.existsById(user.getUserId());
            assertFalse(exists);
        }
    }
}
