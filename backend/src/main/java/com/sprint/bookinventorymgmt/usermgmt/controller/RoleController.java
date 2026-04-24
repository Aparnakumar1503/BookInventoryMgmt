package com.sprint.bookinventorymgmt.usermgmt.controller;

import com.sprint.bookinventorymgmt.common.ResponseBuilder;
import com.sprint.bookinventorymgmt.common.PaginatedResponse;
import com.sprint.bookinventorymgmt.common.PaginationUtils;
import com.sprint.bookinventorymgmt.common.ResponseStructure;
import com.sprint.bookinventorymgmt.usermgmt.dto.requestdto.UserRequestDTO;
import com.sprint.bookinventorymgmt.usermgmt.dto.responsedto.PermRoleResponseDTO;
import com.sprint.bookinventorymgmt.usermgmt.dto.responsedto.UserResponseDTO;
import com.sprint.bookinventorymgmt.usermgmt.service.IPermRoleService;
import com.sprint.bookinventorymgmt.usermgmt.service.IUserMgmtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoleController {
    @Autowired
    private  IPermRoleService permRoleService;
    @Autowired
    private  IUserMgmtService userService;

    public RoleController(IPermRoleService permRoleService, IUserMgmtService userService) {
        this.permRoleService = permRoleService;
        this.userService = userService;
    }

    @GetMapping("/api/v1/roles")
    public ResponseEntity<ResponseStructure<PaginatedResponse<PermRoleResponseDTO>>> getAllRoles(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.ok(
                ResponseBuilder.success(
                        HttpStatus.OK.value(),
                        "Roles fetched successfully",
                        PaginationUtils.paginate(permRoleService.getAllRoles(), page, size)
                )
        );
    }

    @PutMapping("/api/v1/users/{userId}/roles/{roleId}")
    public ResponseEntity<ResponseStructure<UserResponseDTO>> updateUserRole(
            @PathVariable Integer userId,
            @PathVariable Integer roleId) {
        UserResponseDTO user = userService.getUserById(userId);

        UserRequestDTO requestDTO = new UserRequestDTO();
        requestDTO.setFirstName(user.getFirstName());
        requestDTO.setLastName(user.getLastName());
        requestDTO.setPhoneNumber(user.getPhoneNumber());
        requestDTO.setUserName(user.getUserName());
        requestDTO.setPassword("");
        requestDTO.setRoleNumber(roleId);

        return ResponseEntity.ok(
                ResponseBuilder.success(
                        HttpStatus.OK.value(),
                        "User role updated successfully",
                        userService.updateUser(userId, requestDTO)
                )
        );
    }
}
