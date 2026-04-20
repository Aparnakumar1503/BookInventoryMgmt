package com.sprint.BookInventoryMgmt.usermgmt.service;

import com.sprint.BookInventoryMgmt.usermgmt.dto.requestdto.UserRequestDTO;
import com.sprint.BookInventoryMgmt.usermgmt.dto.responsedto.UserResponseDTO;
import com.sprint.BookInventoryMgmt.usermgmt.entity.PermRole;
import com.sprint.BookInventoryMgmt.usermgmt.entity.User;
import com.sprint.BookInventoryMgmt.usermgmt.exceptions.InvalidCredentialsException;
import com.sprint.BookInventoryMgmt.usermgmt.exceptions.PermRoleNotFoundException;
import com.sprint.BookInventoryMgmt.usermgmt.exceptions.UserNotFoundException;
import com.sprint.BookInventoryMgmt.usermgmt.repository.IPermRoleRepository;
import com.sprint.BookInventoryMgmt.usermgmt.repository.IUserMgmtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMgmtServiceImpl implements IUserMgmtService {
    @Autowired
    private IUserMgmtRepository userRepo;
    @Autowired
    private IPermRoleRepository permRoleRepo;

    public UserMgmtServiceImpl(IUserMgmtRepository userRepo, IPermRoleRepository permRoleRepo) {
        this.userRepo = userRepo;
        this.permRoleRepo = permRoleRepo;
    }

    @Override
    public UserResponseDTO addUser(UserRequestDTO dto) {
        PermRole role = permRoleRepo.findById(dto.getRoleNumber())
                .orElseThrow(() ->
                        new PermRoleNotFoundException("Role not found with id: " + dto.getRoleNumber()));

        User entity = new User();
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setUserName(dto.getUserName());
        entity.setPassword(dto.getPassword());
        entity.setRole(role);

        User saved = userRepo.save(entity);
        return mapToDTO(saved);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userRepo.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
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

        PermRole role = permRoleRepo.findById(dto.getRoleNumber())
                .orElseThrow(() ->
                        new PermRoleNotFoundException("Role not found with id: " + dto.getRoleNumber()));

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setUserName(dto.getUserName());
        user.setPassword(dto.getPassword());
        user.setRole(role);

        User updated = userRepo.save(user);
        return mapToDTO(updated);
    }

    @Override
    public String deleteUser(Integer userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found with id: " + userId));
        userRepo.delete(user);
        return "User deleted successfully with id: " + userId;
    }

    @Override
    public UserResponseDTO getUserByUserName(String userName) {
        User user = userRepo.findByUserName(userName)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found with username: " + userName));
        return mapToDTO(user);
    }

    @Override
    public List<UserResponseDTO> getUsersByRoleNumber(Integer roleNumber) {
        List<User> users = userRepo.findByRoleNumber(roleNumber);
        if (users.isEmpty()) {
            throw new UserNotFoundException("No users found with role number: " + roleNumber);
        }
        return users.stream().map(this::mapToDTO).toList();
    }

    @Override
    public String updatePassword(Integer userId, String newPassword) {
        userRepo.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found with id: " + userId));

        int updated = userRepo.updatePassword(userId, newPassword);
        if (updated > 0) {
            return "Password updated successfully for userId: " + userId;
        }
        return "Password update failed for userId: " + userId;
    }

    //  new login method
    @Override
    public UserResponseDTO login(String userName, String password) {
        User user = userRepo.findByUserNameAndPassword(userName, password)
                .orElseThrow(() ->
                        new InvalidCredentialsException("Invalid username or password"));
        return mapToDTO(user);
    }

    private UserResponseDTO mapToDTO(User entity) {
        return UserResponseDTO.builder()
                .userId(entity.getUserId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .phoneNumber(entity.getPhoneNumber())
                .userName(entity.getUserName())
                .roleNumber(entity.getRole() != null ? entity.getRole().getRoleNumber() : null)
                .permRole(entity.getRole() != null ? entity.getRole().getPermRole() : null)
                .build();
    }

}
