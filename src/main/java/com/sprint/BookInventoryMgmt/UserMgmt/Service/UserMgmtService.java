package com.sprint.BookInventoryMgmt.UserMgmt.Service;

import java.util.List;

import com.sprint.BookInventoryMgmt.UserMgmt.Entity.User;

public interface UserMgmtService {
	User saveUser(User user);

    List<User> getAllUsers();

    User getUserById(Integer id);

    void deleteUser(Integer id);

}
