package com.sprint.BookInventoryMgmt.UserMgmt.Controller;

import com.sprint.BookInventoryMgmt.UserMgmt.DTO.UserResponseDTO;
import com.sprint.BookInventoryMgmt.UserMgmt.Entity.PermRole;
import com.sprint.BookInventoryMgmt.UserMgmt.Entity.User;
import com.sprint.BookInventoryMgmt.UserMgmt.Service.PermRoleService;
import com.sprint.BookInventoryMgmt.UserMgmt.Service.UserMgmtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PermRoleController {
    @Autowired
    private PermRoleService roleService;

    @Autowired
    private UserMgmtService userService;

    // GET ROLES
    @GetMapping("/roles")
    public List<PermRole> getAllRoles() {
        return roleService.getAllRoles();
    }

    // ASSIGN ROLE
    @PutMapping("/users/{userId}/roles/{roleId}")
    public UserResponseDTO assignRole(@PathVariable Integer userId,
                                      @PathVariable Integer roleId) {

        User user = userService.getUserById(userId);
        PermRole role = roleService.getRoleById(roleId);

        user.setRole(role);

        User updated = userService.updateUser(userId, user);

        UserResponseDTO dto = new UserResponseDTO();
        dto.setUserId(updated.getUserId());
        dto.setFirstName(updated.getFirstName());
        dto.setLastName(updated.getLastName());
        dto.setUserName(updated.getUserName());
        dto.setRoleName(updated.getRole().getPermRole());

        return dto;
    }
}
