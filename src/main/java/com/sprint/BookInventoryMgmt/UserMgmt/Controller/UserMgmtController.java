package com.sprint.BookInventoryMgmt.UserMgmt.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sprint.BookInventoryMgmt.UserMgmt.Entity.PermRole;
import com.sprint.BookInventoryMgmt.UserMgmt.Entity.User;
import com.sprint.BookInventoryMgmt.UserMgmt.Repository.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
public class UserMgmtController {
	
	    private final UserMgmtRepository userRepository;
	    private final PermRoleRepository roleRepository;

	    public UserMgmtController(UserMgmtRepository userRepository,
	                    PermRoleRepository roleRepository) {
	        this.userRepository = userRepository;
	        this.roleRepository = roleRepository;
	    }

	    //  CREATE USER
	    @PostMapping
	    public User createUser(@Valid @RequestBody User user) {
	        return userRepository.save(user);
	    }

	    //  GET USER BY ID
	    @GetMapping("/{userId}")
	    public User getUser(@PathVariable Integer userId) {
	        return userRepository.findById(userId).orElse(null);
	    }

	    //  UPDATE USER
	    @PutMapping("/{userId}")
	    public User updateUser(@PathVariable Integer userId,
	                           @RequestBody User updatedUser) {

	        Optional<User> userOptional = userRepository.findById(userId);

	        if (userOptional.isPresent()) {
	            User user = userOptional.get();

	            user.setFirstName(updatedUser.getFirstName());
	            user.setLastName(updatedUser.getLastName());
	            user.setUserName(updatedUser.getUserName());
	            user.setPassword(updatedUser.getPassword());

	            return userRepository.save(user);
	        }

	        return null;
	    }

	    // GET ALL ROLES
	    @GetMapping("/roles")
	    public List<PermRole> getAllRoles() {
	        return roleRepository.findAll();
	    }

	    // ✅ ASSIGN ROLE TO USER
	    @PutMapping("/{userId}/roles/{roleId}")
	    public User assignRole(@PathVariable Integer userId,
	                           @PathVariable Integer roleId) {

	        User user = userRepository.findById(userId).orElse(null);
	        PermRole role = roleRepository.findById(roleId).orElse(null);

	        if (user != null && role != null) {
	            user.setRole(role);
	            return userRepository.save(user);
	        }

	        return null;
	    }
	}

