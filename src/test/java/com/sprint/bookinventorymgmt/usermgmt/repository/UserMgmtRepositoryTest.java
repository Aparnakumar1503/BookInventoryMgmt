//package com.sprint.bookinventorymgmt.usermgmt.repository;
//
//import com.sprint.bookinventorymgmt.usermgmt.entity.PermRole;
//import com.sprint.bookinventorymgmt.usermgmt.entity.User;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//@DataJpaTest
//public class UserMgmtRepositoryTest {
//    @Autowired
//    private TestEntityManager entityManager;
//
//    @Autowired
//    private IUserMgmtRepository userRepository;
//
//    @Autowired
//    private IPermRoleRepository permRoleRepository; // ✅ needed for User FK
//
//    private PermRole savedRole;
//
//    @BeforeEach
//    void setUp() {
//        // ✅ save PermRole first — User has FK on roleNumber
//        savedRole = permRoleRepository.saveAndFlush(new PermRole(null, "Guest"));
//    }
//
//    @Test
//    void testSaveUser() {
//        User user = new User("John", "Doe", "9876543210", "johndoe", "pass123", savedRole);
//        User saved = userRepository.saveAndFlush(user);
//
//        assertNotNull(saved);
//        assertNotNull(saved.getUserId()); // auto-generated
//        assertEquals("John", saved.getFirstName());
//        assertEquals("johndoe", saved.getUserName());
//    }
//
//    @Test
//    void testFindByUserName() {
//        userRepository.saveAndFlush(
//                new User("John", "Doe", "9876543210", "johndoe", "pass123", savedRole));
//
//        Optional<User> result = userRepository.findByUserName("johndoe");
//
//        assertTrue(result.isPresent());
//        assertEquals("johndoe", result.get().getUserName());
//    }
//
//    @Test
//    void testFindByLastName() {
//        userRepository.saveAndFlush(
//                new User("John", "Doe", "9876543210", "johndoe", "pass123", savedRole));
//        userRepository.saveAndFlush(
//                new User("Jane", "Doe", "1234567890", "janedoe", "pass456", savedRole));
//
//        List<User> result = userRepository.findByLastName("Doe");
//
//        assertNotNull(result);
//        assertEquals(2, result.size());
//    }
//
//    @Test
//    void testExistsByUserName() {
//        userRepository.saveAndFlush(
//                new User("John", "Doe", "9876543210", "johndoe", "pass123", savedRole));
//
//        boolean exists = userRepository.existsByUserName("johndoe");
//
//        assertTrue(exists);
//    }
//
//    // Custom Query 1 — find by username and password
//    @Test
//    void testFindByUserNameAndPassword() {
//        userRepository.saveAndFlush(
//                new User("John", "Doe", "9876543210", "johndoe", "pass123", savedRole));
//
//        Optional<User> result = userRepository.findByUserNameAndPassword("johndoe", "pass123");
//
//        assertTrue(result.isPresent());
//        assertEquals("johndoe", result.get().getUserName());
//    }
//
//    // Custom Query 2 — find by role number
//    @Test
//    void testFindByRoleNumber() {
//        userRepository.saveAndFlush(
//                new User("John", "Doe", "9876543210", "johndoe", "pass123", savedRole));
//        userRepository.saveAndFlush(
//                new User("Jane", "Doe", "1234567890", "janedoe", "pass456", savedRole));
//
//        List<User> result = userRepository.findByRoleNumber(savedRole.getRoleNumber());
//
//        assertNotNull(result);
//        assertEquals(2, result.size());
//    }
//
//    // Custom Query 3 — update password
//    @Test
//    void testUpdatePassword() {
//        User saved = userRepository.saveAndFlush(
//                new User("John", "Doe", "9876543210", "johndoe", "pass123", savedRole));
//
//        int updated = userRepository.updatePassword(saved.getUserId(), "newpass123");
//
//        assertEquals(1, updated);
//        entityManager.clear();
//
//        Optional<User> result = userRepository.findById(saved.getUserId());
//        assertTrue(result.isPresent());
//        assertEquals("newpass123", result.get().getPassword());
//    }
//}
