package com.sprint.BookInventoryMgmt.UserMgmt.Service;

import com.sprint.BookInventoryMgmt.UserMgmt.Entity.User;
import com.sprint.BookInventoryMgmt.UserMgmt.Exception.InvalidCredentialsException;
import com.sprint.BookInventoryMgmt.UserMgmt.Exception.UserNotFoundException;
import com.sprint.BookInventoryMgmt.UserMgmt.Repository.UserMgmtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMgmtServiceImpl implements UserMgmtService {
    @Autowired
    private UserMgmtRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(Integer id, User user) {

        User existing = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        existing.setFirstName(user.getFirstName());
        existing.setLastName(user.getLastName());
        existing.setPhoneNumber(user.getPhoneNumber());
        existing.setUserName(user.getUserName());
        existing.setPassword(user.getPassword());
        existing.setRole(user.getRole());

        return userRepository.save(existing);
    }
    @Override
    public User login(String username, String password) {

        // Check username
        User user = userRepository.findByUserName(username)
                .orElseThrow(() ->
                        new InvalidCredentialsException("Invalid username or password"));

        // Check password
        if (!user.getPassword().equals(password)) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        //  Success
        return user;
    }

    @Override
    public void deleteUser(Integer id) {

        User existing = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        userRepository.delete(existing);
    }

}
