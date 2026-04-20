package com.sprint.BookInventoryMgmt.usermgmt.controller;

import com.sprint.BookInventoryMgmt.usermgmt.dto.responsedto.PermRoleResponseDTO;
import com.sprint.BookInventoryMgmt.usermgmt.dto.responsedto.UserResponseDTO;
import com.sprint.BookInventoryMgmt.usermgmt.service.IPermRoleService;
import com.sprint.BookInventoryMgmt.usermgmt.service.IUserMgmtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

public class RoleController {
    @Autowired
    private IPermRoleService permRoleService;
    @Autowired
    private  IUserMgmtService userService;

    public RoleController(IPermRoleService permRoleService, IUserMgmtService userService) {
        this.permRoleService = permRoleService;
        this.userService = userService;
    }

    // GET /api/v1/roles
    @GetMapping("/api/v1/roles")
    public ResponseEntity<List<PermRoleResponseDTO>> getAllRoles() {
        return ResponseEntity.ok(permRoleService.getAllRoles());
    }

    // PUT /api/v1/users/{userId}/roles/{roleId}
    @PutMapping("/api/v1/users/{userId}/roles/{roleId}")
    public ResponseEntity<UserResponseDTO> updateUserRole(@PathVariable Integer userId,
                                                          @PathVariable Integer roleId) {
        // fetch user — update role using roleId
        UserResponseDTO user = userService.getUserById(userId);

        // build request with existing user details and new roleId
        com.sprint.BookInventoryMgmt.usermgmt.dto.requestdto.UserRequestDTO requestDTO =
                com.sprint.BookInventoryMgmt.usermgmt.dto.requestdto.UserRequestDTO.builder()
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .phoneNumber(user.getPhoneNumber())
                        .userName(user.getUserName())
                        .password("") // password not changed here
                        .roleNumber(roleId) // update to new role
                        .build();

        return ResponseEntity.ok(userService.updateUser(userId, requestDTO));
    }
}
