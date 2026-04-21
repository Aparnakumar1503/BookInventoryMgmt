package com.sprint.bookinventorymgmt.usermgmt.service;

import com.sprint.bookinventorymgmt.usermgmt.dto.requestdto.UserRequestDTO;
import com.sprint.bookinventorymgmt.usermgmt.dto.responsedto.UserResponseDTO;
import com.sprint.bookinventorymgmt.usermgmt.entity.PermRole;
import com.sprint.bookinventorymgmt.usermgmt.entity.User;
import com.sprint.bookinventorymgmt.usermgmt.exceptions.DuplicateUsernameException;
import com.sprint.bookinventorymgmt.usermgmt.exceptions.InvalidCredentialsException;
import com.sprint.bookinventorymgmt.usermgmt.exceptions.PermRoleNotFoundException;
import com.sprint.bookinventorymgmt.usermgmt.exceptions.UserNotFoundException;
import com.sprint.bookinventorymgmt.usermgmt.repository.IPermRoleRepository;
import com.sprint.bookinventorymgmt.usermgmt.repository.IUserMgmtRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserMgmtServiceTest {
    @Mock
    private IUserMgmtRepository userRepo;

    @Mock
    private IPermRoleRepository permRoleRepo;

    @InjectMocks
    private UserMgmtServiceImpl service;

    private User user;
    private PermRole permRole;
    private UserRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        permRole = new PermRole(1, "Guest");

        user = new User("John", "Doe", "9876543210", "johndoe", "pass123", permRole);
        user.setUserId(1);

        requestDTO = UserRequestDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("9876543210")
                .userName("johndoe")
                .password("pass123")
                .roleNumber(1)
                .build();
    }

    @Test
    void testAddUser_Positive() {
        when(userRepo.existsByUserName("johndoe")).thenReturn(false);
        when(permRoleRepo.findById(1)).thenReturn(Optional.of(permRole));
        when(userRepo.save(any(User.class))).thenReturn(user);

        UserResponseDTO result = service.addUser(requestDTO);

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("johndoe", result.getUserName());
        verify(userRepo).save(any(User.class));
    }

    @Test
    void testGetUserById_Positive() {
        when(userRepo.findById(1)).thenReturn(Optional.of(user));

        UserResponseDTO result = service.getUserById(1);

        assertNotNull(result);
        assertEquals(1, result.getUserId());
        assertEquals("johndoe", result.getUserName());
        verify(userRepo).findById(1);
    }

    @Test
    void testLogin_Positive() {
        when(userRepo.findByUserNameAndPassword("johndoe", "pass123"))
                .thenReturn(Optional.of(user));

        UserResponseDTO result = service.login("johndoe", "pass123");

        assertNotNull(result);
        assertEquals("johndoe", result.getUserName());
        verify(userRepo).findByUserNameAndPassword("johndoe", "pass123");
    }

    @Test
    void testAddUser_Negative_RoleNotFound() {
        when(userRepo.existsByUserName("johndoe")).thenReturn(false);
        when(permRoleRepo.findById(99)).thenReturn(Optional.empty());

        UserRequestDTO invalidDTO = UserRequestDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .userName("johndoe")
                .password("pass123")
                .roleNumber(99)
                .build();

        assertThrows(PermRoleNotFoundException.class, () -> service.addUser(invalidDTO));
        verify(permRoleRepo).findById(99);
    }

    @Test
    void testAddUser_Negative_DuplicateUsername() {
        when(userRepo.existsByUserName("johndoe")).thenReturn(true);

        assertThrows(DuplicateUsernameException.class, () -> service.addUser(requestDTO));
        verify(userRepo, never()).save(any(User.class));
    }

    @Test
    void testGetUserById_Negative_NotFound() {
        when(userRepo.findById(99)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> service.getUserById(99));
        verify(userRepo).findById(99);
    }

    @Test
    void testLogin_Negative_InvalidCredentials() {
        when(userRepo.findByUserNameAndPassword("johndoe", "wrongpass"))
                .thenReturn(Optional.empty());

        assertThrows(InvalidCredentialsException.class, () -> service.login("johndoe", "wrongpass"));
        verify(userRepo).findByUserNameAndPassword("johndoe", "wrongpass");
    }

    @Test
    void testGetAllUsers_Edge_EmptyList() {
        when(userRepo.findAll()).thenReturn(Collections.emptyList());

        List<UserResponseDTO> result = service.getAllUsers();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userRepo).findAll();
    }
}
