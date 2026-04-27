package com.sprint.bookinventorymgmt.usermgmt.service;
import com.sprint.bookinventorymgmt.usermgmt.dto.requestdto.PermRoleRequestDTO;
import com.sprint.bookinventorymgmt.usermgmt.dto.responsedto.PermRoleResponseDTO;
import com.sprint.bookinventorymgmt.usermgmt.entity.PermRole;
import com.sprint.bookinventorymgmt.usermgmt.exceptions.PermRoleNotFoundException;
import com.sprint.bookinventorymgmt.usermgmt.repository.IPermRoleRepository;
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
public class PermRoleServiceImplTest {
    @Mock // create a fake repo
    private IPermRoleRepository repo;

    @InjectMocks // inject fake repo into service
    private PermRoleServiceImpl service;

    private PermRole permRole;
    private PermRoleRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        permRole = new PermRole(1, "Admin");

        requestDTO = new PermRoleRequestDTO("Admin");
    }

    // ==================== POSITIVE (3) ====================

    @Test
    void testAddRole_Positive() {
        when(repo.save(any(PermRole.class))).thenReturn(permRole);

        PermRoleResponseDTO result = service.addRole(requestDTO);

        assertNotNull(result);
        assertEquals("Admin", result.getPermRole());
        verify(repo, times(1)).save(any(PermRole.class));
    }

    @Test
    void testGetRoleById_Positive() {
        // here optional prevent nullpointerexception
        when(repo.findById(1)).thenReturn(Optional.of(permRole));

        PermRoleResponseDTO result = service.getRoleById(1);

        assertNotNull(result);
        assertEquals(1, result.getRoleNumber());
        assertEquals("Admin", result.getPermRole());
        verify(repo, times(1)).findById(1);
    }

    @Test
    void testDeleteRole_Positive() {
        when(repo.findById(1)).thenReturn(Optional.of(permRole));
        doNothing().when(repo).delete(permRole);

        String result = service.deleteRole(1);

        assertEquals("Role deleted successfully with id: 1", result);
        verify(repo, times(1)).delete(permRole);
    }

    // ==================== NEGATIVE (3) ====================

    @Test
    void testGetRoleById_Negative_NotFound() {
        when(repo.findById(99)).thenReturn(Optional.empty());

        assertThrows(PermRoleNotFoundException.class, () ->
                service.getRoleById(99));
        verify(repo, times(1)).findById(99);
    }

    @Test
    void testDeleteRole_Negative_NotFound() {
        when(repo.findById(99)).thenReturn(Optional.empty());

        assertThrows(PermRoleNotFoundException.class, () ->
                service.deleteRole(99));
        verify(repo, times(1)).findById(99);
    }

    @Test
    void testSearchByPermRole_Negative_NotFound() {
        when(repo.searchByPermRole("xyz")).thenReturn(Collections.emptyList());

        assertThrows(PermRoleNotFoundException.class, () ->
                service.searchByPermRole("xyz"));
        verify(repo, times(1)).searchByPermRole("xyz");
    }

    // ==================== EDGE CASE (1) ====================

    @Test
    void testGetAllRoles_Edge_EmptyList() {
        when(repo.findAll()).thenReturn(Collections.emptyList());

        List<PermRoleResponseDTO> result = service.getAllRoles();

        assertNotNull(result);
        assertTrue(result.isEmpty()); // valid but empty — edge case
        verify(repo, times(1)).findAll();
    }
}
