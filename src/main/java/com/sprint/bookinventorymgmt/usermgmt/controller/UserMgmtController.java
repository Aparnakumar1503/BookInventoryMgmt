package com.sprint.bookinventorymgmt.usermgmt.controller;

import com.sprint.bookinventorymgmt.common.PaginatedResponse;
import com.sprint.bookinventorymgmt.common.ResponseBuilder;
import com.sprint.bookinventorymgmt.common.ResponseStructure;
import com.sprint.bookinventorymgmt.usermgmt.dto.requestdto.UserRequestDTO;
import com.sprint.bookinventorymgmt.usermgmt.dto.responsedto.UserResponseDTO;
import com.sprint.bookinventorymgmt.usermgmt.service.IUserMgmtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserMgmtController {

    private final IUserMgmtService userService;

    public UserMgmtController(IUserMgmtService userService) {
        this.userService = userService;
    }

    /**
     * Creates a user.
     */
    @PostMapping("/api/v1/users")
    public ResponseEntity<ResponseStructure<UserResponseDTO>> addUser(@Valid @RequestBody UserRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseBuilder.success(
                        HttpStatus.CREATED.value(),
                        "User created successfully",
                        userService.addUser(requestDTO)
                ));
    }

    /**
     * Returns one user by identifier.
     */
    @GetMapping("/api/v1/users/{userId}")
    public ResponseEntity<ResponseStructure<UserResponseDTO>> getUserById(@PathVariable Integer userId) {
        return ResponseEntity.ok(
                ResponseBuilder.success(
                        HttpStatus.OK.value(),
                        "User fetched successfully",
                        userService.getUserById(userId)
                )
        );
    }

    /**
     * Updates one user by identifier.
     */
    @PutMapping("/api/v1/users/{userId}")
    public ResponseEntity<ResponseStructure<UserResponseDTO>> updateUser(
            @PathVariable Integer userId,
            @Valid @RequestBody UserRequestDTO requestDTO) {
        return ResponseEntity.ok(
                ResponseBuilder.success(
                        HttpStatus.OK.value(),
                        "User updated successfully",
                        userService.updateUser(userId, requestDTO)
                )
        );
    }
}
