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
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@Validated
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
            @RequestParam(defaultValue = "0") @PositiveOrZero(message = "Page must be 0 or greater") Integer page,
            @RequestParam(defaultValue = "10") @Positive(message = "Size must be greater than 0") Integer size) {
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
            @PathVariable @Positive(message = "User ID must be greater than 0") Integer userId,
            @PathVariable @Positive(message = "Role ID must be greater than 0") Integer roleId) {
        UserResponseDTO user = userService.getUserById(userId);
        PermRoleResponseDTO targetRole = permRoleService.getRoleById(roleId);

        if (isAdminRole(targetRole) && !currentUserIsAdmin()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only an Admin can assign the Admin role.");
        }

        UserRequestDTO requestDTO = new UserRequestDTO();
        requestDTO.setFirstName(user.getFirstName());
        requestDTO.setLastName(user.getLastName());
        requestDTO.setPhoneNumber(user.getPhoneNumber());
        requestDTO.setUserName(user.getUserName());
        requestDTO.setPassword("");
        requestDTO.setRoleNumber(targetRole.getRoleNumber());

        return ResponseEntity.ok(
                ResponseBuilder.success(
                        HttpStatus.OK.value(),
                        "User role updated successfully",
                        userService.updateUser(userId, requestDTO)
                )
        );
    }

    private boolean isAdminRole(PermRoleResponseDTO role) {
        return role.getPermRole() != null && "admin".equalsIgnoreCase(role.getPermRole());
    }

    private boolean currentUserIsAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null
                && authentication.getAuthorities().stream()
                .anyMatch(authority -> "ROLE_ADMIN".equalsIgnoreCase(authority.getAuthority()));
    }
}
