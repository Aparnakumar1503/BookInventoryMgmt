package com.sprint.BookInventoryMgmt.userMgmt.controller;

import com.sprint.BookInventoryMgmt.userMgmt.dto.responsedto.UserResponseDTO;
import com.sprint.BookInventoryMgmt.userMgmt.entity.PermRole;
import com.sprint.BookInventoryMgmt.userMgmt.entity.User;
import com.sprint.BookInventoryMgmt.userMgmt.service.IPermRoleService;
import com.sprint.BookInventoryMgmt.userMgmt.service.IUserMgmtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PermRoleController {
    @Autowired
    private IPermRoleService roleService;

    @Autowired
    private IUserMgmtService userService;

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
