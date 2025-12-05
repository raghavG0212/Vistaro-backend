package com.project.vistaro.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.vistaro.exception.ResourceNotFoundException;
import com.project.vistaro.model.User;
import com.project.vistaro.repository.UserDao;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User addUser(User user) {
        // Hash password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDao.addUser(user);
    }

    @Override
    public User updateUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDao.updateUser(user);
    }

    @Override
    public User getUserById(Integer id) {
        return userDao.getUserById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + id));
    }

    @Override
    public String checkUserRole(Integer id) {
        return userDao.checkUserRole(id);
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }
}
