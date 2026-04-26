package com.sprint.bookinventorymgmt.usermgmt.service;

import com.sprint.bookinventorymgmt.usermgmt.dto.requestdto.UserRequestDTO;
import com.sprint.bookinventorymgmt.usermgmt.dto.responsedto.UserResponseDTO;
import com.sprint.bookinventorymgmt.usermgmt.entity.PermRole;
import com.sprint.bookinventorymgmt.usermgmt.entity.User;
import com.sprint.bookinventorymgmt.usermgmt.exceptions.DuplicateUsernameException;
import com.sprint.bookinventorymgmt.usermgmt.exceptions.PasswordReuseException;
import com.sprint.bookinventorymgmt.usermgmt.exceptions.PermRoleNotFoundException;
import com.sprint.bookinventorymgmt.usermgmt.exceptions.UserDeleteNotAllowedException;
import com.sprint.bookinventorymgmt.usermgmt.exceptions.UserNotFoundException;
import com.sprint.bookinventorymgmt.usermgmt.repository.IPermRoleRepository;
import com.sprint.bookinventorymgmt.usermgmt.repository.IUserMgmtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserMgmtServiceImpl implements IUserMgmtService {

    @Autowired
    private  IUserMgmtRepository userRepo;
    @Autowired
    private  IPermRoleRepository permRoleRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;



    @Override
    public UserResponseDTO addUser(UserRequestDTO dto) {
        if (userRepo.existsByUserNameIgnoreCase(dto.getUserName())) {
            throw new DuplicateUsernameException("Username already exists: " + dto.getUserName());
        }

        PermRole role = permRoleRepo.findById(dto.getRoleNumber())
                .orElseThrow(() ->
                        new PermRoleNotFoundException("Role not found with id: " + dto.getRoleNumber()));

        User entity = new User();
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setUserName(dto.getUserName());
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity.setRole(role);

        User saved = userRepo.save(entity);
        return mapToDTO(saved);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepo.findAll();
        List<UserResponseDTO> response = new ArrayList<>();

        for (User user : users) {
            response.add(mapToDTO(user));
        }

        return response;
    }

    @Override
    public UserResponseDTO getUserById(Integer userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found with id: " + userId));
        return mapToDTO(user);
    }

    @Override
    public UserResponseDTO updateUser(Integer userId, UserRequestDTO dto) {
        User user = userRepo.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found with id: " + userId));

        if (!user.getUserName().equalsIgnoreCase(dto.getUserName())
                && userRepo.existsByUserNameIgnoreCase(dto.getUserName())) {
            throw new DuplicateUsernameException("Username already exists: " + dto.getUserName());
        }

        PermRole role = permRoleRepo.findById(dto.getRoleNumber())
                .orElseThrow(() ->
                        new PermRoleNotFoundException("Role not found with id: " + dto.getRoleNumber()));

        if (dto.getPassword() != null
                && !dto.getPassword().isBlank()
                && passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new PasswordReuseException("New password must be different from the current password.");
        }

        if (isUserUnchanged(user, dto, role)) {
            throw new ResponseStatusException(HttpStatus.NOT_MODIFIED, "No changes detected for user id: " + userId);
        }

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setUserName(dto.getUserName());
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        user.setRole(role);

        User updated = userRepo.save(user);
        return mapToDTO(updated);
    }

    @Override
    public String deleteUser(Integer userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found with id: " + userId));
        if (isCurrentLoggedInUser(user)) {
            throw new UserDeleteNotAllowedException("The currently logged-in user cannot delete their own account.");
        }
        userRepo.delete(user);
        return "User deleted successfully with id: " + userId;
    }

    private UserResponseDTO mapToDTO(User entity) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setUserId(entity.getUserId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setUserName(entity.getUserName());
        dto.setRoleNumber(entity.getRole() != null ? entity.getRole().getRoleNumber() : null);
        dto.setPermRole(entity.getRole() != null ? entity.getRole().getPermRole() : null);
        return dto;
    }

    private boolean isUserUnchanged(User user, UserRequestDTO dto, PermRole role) {
        boolean passwordUnchanged = dto.getPassword() == null
                || dto.getPassword().isBlank()
                || passwordEncoder.matches(dto.getPassword(), user.getPassword());

        return Objects.equals(user.getFirstName(), dto.getFirstName())
                && Objects.equals(user.getLastName(), dto.getLastName())
                && Objects.equals(user.getPhoneNumber(), dto.getPhoneNumber())
                && Objects.equals(user.getUserName(), dto.getUserName())
                && Objects.equals(user.getRole() != null ? user.getRole().getRoleNumber() : null, role.getRoleNumber())
                && passwordUnchanged;
    }

    private boolean isCurrentLoggedInUser(User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            return false;
        }
        return user.getUserName() != null
                && user.getUserName().equalsIgnoreCase(authentication.getName());
    }
}
