package com.sprint.bookinventorymgmt.usermgmt.controller;

import com.sprint.bookinventorymgmt.common.ResponseBuilder;
import com.sprint.bookinventorymgmt.common.ResponseStructure;
import com.sprint.bookinventorymgmt.usermgmt.dto.requestdto.UserRequestDTO;
import com.sprint.bookinventorymgmt.usermgmt.dto.responsedto.UserResponseDTO;
import com.sprint.bookinventorymgmt.usermgmt.service.IUserMgmtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserMgmtController {

    private final IUserMgmtService service;

    public UserMgmtController(IUserMgmtService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ResponseStructure<UserResponseDTO>> addUser(
            @Valid @RequestBody UserRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ResponseBuilder.success(
                                HttpStatus.CREATED.value(),
                                "User created successfully",
                                service.addUser(requestDTO)
                        )
                );
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseStructure<UserResponseDTO>> getUserById(@PathVariable Integer userId) {
        return ResponseEntity.ok(
                ResponseBuilder.success(
                        HttpStatus.OK.value(),
                        "User fetched successfully",
                        service.getUserById(userId)
                )
        );
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ResponseStructure<UserResponseDTO>> updateUser(
            @PathVariable Integer userId,
            @Valid @RequestBody UserRequestDTO requestDTO) {
        return ResponseEntity.ok(
                ResponseBuilder.success(
                        HttpStatus.OK.value(),
                        "User updated successfully",
                        service.updateUser(userId, requestDTO)
                )
        );
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ResponseStructure<String>> deleteUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(
                ResponseBuilder.success(
                        HttpStatus.OK.value(),
                        "User deleted successfully",
                        service.deleteUser(userId)
                )
        );
    }
}
