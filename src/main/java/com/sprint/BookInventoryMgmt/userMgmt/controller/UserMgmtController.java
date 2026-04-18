package com.sprint.BookInventoryMgmt.userMgmt.controller;

import java.util.List;

import com.sprint.BookInventoryMgmt.userMgmt.dto.LoginDTO;
import com.sprint.BookInventoryMgmt.userMgmt.dto.requestdto.UserRequestDTO;
import com.sprint.BookInventoryMgmt.userMgmt.dto.responsedto.UserResponseDTO;
import com.sprint.BookInventoryMgmt.userMgmt.service.IPermRoleService;
import com.sprint.BookInventoryMgmt.userMgmt.service.IUserMgmtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sprint.BookInventoryMgmt.userMgmt.entity.PermRole;
import com.sprint.BookInventoryMgmt.userMgmt.entity.User;

import jakarta.validation.Valid;
@RestController
@RequestMapping("/api/v1/users")
public class UserMgmtController {
	@Autowired
	private IUserMgmtService userService;

	@Autowired
	private IPermRoleService roleService;

	// CREATE
	@PostMapping
	public UserResponseDTO createUser(@Valid @RequestBody UserRequestDTO dto) {

		PermRole role = roleService.getRoleById(dto.getRoleId());

		User user = new User();
		user.setFirstName(dto.getFirstName());
		user.setLastName(dto.getLastName());
		user.setPhoneNumber(dto.getPhoneNumber());
		user.setUserName(dto.getUserName());
		user.setPassword(dto.getPassword());
		user.setRole(role);

		return mapToResponse(userService.createUser(user));
	}

	// GET BY ID
	@GetMapping("/{userId}")
	public UserResponseDTO getUser(@PathVariable Integer userId) {
		return mapToResponse(userService.getUserById(userId));
	}

	// GET ALL
	@GetMapping
	public List<UserResponseDTO> getAllUsers() {
		return userService.getAllUsers()
				.stream()
				.map(this::mapToResponse)
				.toList();
	}

	// UPDATE
	@PutMapping("/{userId}")
	public UserResponseDTO updateUser(@PathVariable Integer userId,
									  @RequestBody UserRequestDTO dto) {

		PermRole role = roleService.getRoleById(dto.getRoleId());

		User user = new User();
		user.setFirstName(dto.getFirstName());
		user.setLastName(dto.getLastName());
		user.setPhoneNumber(dto.getPhoneNumber());
		user.setUserName(dto.getUserName());
		user.setPassword(dto.getPassword());
		user.setRole(role);

		return mapToResponse(userService.updateUser(userId, user));
	}

	// DELETE
	@DeleteMapping("/{userId}")
	public String deleteUser(@PathVariable Integer userId) {
		userService.deleteUser(userId);
		return "User deleted successfully";
	}

	// LOGIN (kept inside user controller )
	@PostMapping("/login")
	public UserResponseDTO login(@RequestBody LoginDTO loginDTO) {

		User user = userService.login(
				loginDTO.getUserName(),
				loginDTO.getPassword()
		);

		return mapToResponse(user);
	}

	// MAPPER
	private UserResponseDTO mapToResponse(User user) {

		UserResponseDTO dto = new UserResponseDTO();
		dto.setUserId(user.getUserId());
		dto.setFirstName(user.getFirstName());
		dto.setLastName(user.getLastName());
		dto.setUserName(user.getUserName());
		dto.setRoleName(user.getRole().getPermRole());

		return dto;
	}

	}

