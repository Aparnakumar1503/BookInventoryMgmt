package com.sprint.bookinventorymgmt.usermgmt.service;

import java.util.List;

import com.sprint.bookinventorymgmt.usermgmt.entity.User;

public interface IUserMgmtService {
    User createUser(User user);

    User getUserById(Integer id);

    List<User> getAllUsers();

    User updateUser(Integer id, User user);

    void deleteUser(Integer id);
    User login(String username, String password);

}
