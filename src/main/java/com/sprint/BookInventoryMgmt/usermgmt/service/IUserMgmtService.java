package com.sprint.BookInventoryMgmt.usermgmt.service;

import java.util.List;

import com.sprint.BookInventoryMgmt.usermgmt.dto.requestdto.UserRequestDTO;
import com.sprint.BookInventoryMgmt.usermgmt.dto.responsedto.UserResponseDTO;
import com.sprint.BookInventoryMgmt.usermgmt.entity.User;

public interface IUserMgmtService {
    UserResponseDTO addUser(UserRequestDTO requestDTO);
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO getUserById(Integer userId);
    UserResponseDTO updateUser(Integer userId, UserRequestDTO requestDTO);
    String deleteUser(Integer userId);
    UserResponseDTO getUserByUserName(String userName);
    List<UserResponseDTO> getUsersByRoleNumber(Integer roleNumber);
    String updatePassword(Integer userId, String newPassword);
    UserResponseDTO login(String userName, String password);

}
