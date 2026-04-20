package com.sprint.bookinventorymgmt.usermgmt.controller;


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

		// POST /api/v1/users
		@PostMapping
		public ResponseEntity<UserResponseDTO> addUser(@Valid @RequestBody UserRequestDTO requestDTO) {
			return ResponseEntity.status(HttpStatus.CREATED).body(service.addUser(requestDTO));
		}

		// GET /api/v1/users/{userId}
		@GetMapping("/{userId}")
		public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Integer userId) {
			return ResponseEntity.ok(service.getUserById(userId));
		}

		// PUT /api/v1/users/{userId}
		@PutMapping("/{userId}")
		public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Integer userId,
														  @Valid @RequestBody UserRequestDTO requestDTO) {
			return ResponseEntity.ok(service.updateUser(userId, requestDTO));
		}
	}

