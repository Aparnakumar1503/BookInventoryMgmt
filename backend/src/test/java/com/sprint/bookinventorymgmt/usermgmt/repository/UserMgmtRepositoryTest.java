package com.sprint.bookinventorymgmt.usermgmt.repository;

import com.sprint.bookinventorymgmt.usermgmt.entity.PermRole;
import com.sprint.bookinventorymgmt.usermgmt.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class UserMgmtRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private IUserMgmtRepository userRepository;

    @Autowired
    private IPermRoleRepository permRoleRepository;

    private PermRole savedRole;

    @BeforeEach
    void setUp() {
        savedRole = permRoleRepository.saveAndFlush(new PermRole(null, "Guest"));
    }

    @Test
    void derivedQueries_findByUsername_lastName_andExistsChecks_work() {
        userRepository.saveAndFlush(new User("John", "Doe", "9876543210", "johndoe", "pass123", savedRole));
        userRepository.saveAndFlush(new User("Jane", "Doe", "1234567890", "janedoe", "pass456", savedRole));

        assertTrue(userRepository.findByUserName("johndoe").isPresent());
        assertTrue(userRepository.findByUserNameIgnoreCase("JOHNDOE").isPresent());
        assertEquals(2, userRepository.findByLastName("Doe").size());
        assertTrue(userRepository.existsByUserName("johndoe"));
        assertTrue(userRepository.existsByUserNameIgnoreCase("JOHNDOE"));
    }

    @Test
    void customQueries_findByUsernameAndPassword_andRoleNumber_work() {
        userRepository.saveAndFlush(new User("John", "Doe", "9876543210", "johndoe", "pass123", savedRole));
        userRepository.saveAndFlush(new User("Jane", "Doe", "1234567890", "janedoe", "pass456", savedRole));

        assertTrue(userRepository.findByUserNameAndPassword("johndoe", "pass123").isPresent());
        assertEquals(2, userRepository.findByRoleNumber(savedRole.getRoleNumber()).size());
    }

    @Test
    void customQuery_updatePassword_updatesPersistedUser() {
        User saved = userRepository.saveAndFlush(new User("John", "Doe", "9876543210", "johndoe", "pass123", savedRole));

        assertEquals(1, userRepository.updatePassword(saved.getUserId(), "newpass123"));
        entityManager.clear();
        assertEquals("newpass123", userRepository.findById(saved.getUserId()).orElseThrow().getPassword());
    }
}
