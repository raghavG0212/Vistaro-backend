package com.project.vistaro.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.project.vistaro.exception.ResourceNotFoundException;
import com.project.vistaro.model.User;
import com.project.vistaro.model.UserRole;
import com.project.vistaro.repository.UserDao;

class UserServiceImplTest {

    @Mock
    private UserDao userDao;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // -----------------------------
    // Helper: create a sample user
    // -----------------------------
    private User createUser() {
        User u = new User();
        u.setUserId(1);
        u.setName("Alice");
        u.setEmail("alice@example.com");
        u.setPhone("1234567890");
        u.setCity("Mumbai");
        u.setPassword("rawpass");
        u.setRole(UserRole.USER);
        return u;
    }

    // ------------------------------------------------------------------------------------------
    // TEST 1: addUser() — Should encode password and call DAO.save()
    // ------------------------------------------------------------------------------------------
    @Test
    void testAddUser() {

        User user = createUser();

        when(passwordEncoder.encode("rawpass")).thenReturn("encoded-pass");
        when(userDao.addUser(any(User.class))).thenReturn(user);

        User result = userService.addUser(user);

        assertEquals("encoded-pass", result.getPassword());
        verify(passwordEncoder, times(1)).encode("rawpass");
        verify(userDao, times(1)).addUser(user);
    }

    // ------------------------------------------------------------------------------------------
    // TEST 2: updateUser() — Should update fields and encode password if new password provided
    // ------------------------------------------------------------------------------------------
    @Test
    void testUpdateUser_WithPasswordChange() {

        User existing = createUser();
        existing.setPassword("oldpass");

        User updated = createUser();
        updated.setPassword("newpass");

        when(userDao.getUserById(1)).thenReturn(Optional.of(existing));
        when(passwordEncoder.encode("newpass")).thenReturn("encoded-newpass");
        when(userDao.updateUser(any(User.class))).thenReturn(existing);

        User result = userService.updateUser(updated);

        assertEquals("encoded-newpass", result.getPassword());
        verify(passwordEncoder).encode("newpass");
        verify(userDao).updateUser(existing);
    }

    // ------------------------------------------------------------------------------------------
    // TEST 3: updateUser() — Should NOT encode password if empty or null
    // ------------------------------------------------------------------------------------------
    @Test
    void testUpdateUser_NoPasswordChange() {

        User existing = createUser();
        existing.setPassword("oldpass");

        User updated = createUser();
        updated.setPassword(""); // empty

        when(userDao.getUserById(1)).thenReturn(Optional.of(existing));
        when(userDao.updateUser(any(User.class))).thenReturn(existing);

        User result = userService.updateUser(updated);

        assertEquals("oldpass", result.getPassword());  // unchanged
        verify(passwordEncoder, never()).encode(anyString());
    }

    // ------------------------------------------------------------------------------------------
    // TEST 4: getUserById() — Success case
    // ------------------------------------------------------------------------------------------
    @Test
    void testGetUserById_Success() {
        User u = createUser();
        when(userDao.getUserById(1)).thenReturn(Optional.of(u));

        User result = userService.getUserById(1);

        assertEquals("Alice", result.getName());
        verify(userDao).getUserById(1);
    }

    // ------------------------------------------------------------------------------------------
    // TEST 5: getUserById() — Not found
    // ------------------------------------------------------------------------------------------
    @Test
    void testGetUserById_NotFound() {
        when(userDao.getUserById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            userService.getUserById(1);
        });
    }

    // ------------------------------------------------------------------------------------------
    // TEST 6: checkUserRole() — Should call DAO method
    // ------------------------------------------------------------------------------------------
    @Test
    void testCheckUserRole() {
        when(userDao.checkUserRole(1)).thenReturn("USER");

        String role = userService.checkUserRole(1);

        assertEquals("USER", role);
        verify(userDao).checkUserRole(1);
    }

    // ------------------------------------------------------------------------------------------
    // TEST 7: getUserByEmail() — Success
    // ------------------------------------------------------------------------------------------
    @Test
    void testGetUserByEmail_Success() {
        User u = createUser();
        when(userDao.getUserByEmail("alice@example.com")).thenReturn(Optional.of(u));

        User result = userService.getUserByEmail("alice@example.com");

        assertEquals("Alice", result.getName());
        verify(userDao).getUserByEmail("alice@example.com");
    }

    // ------------------------------------------------------------------------------------------
    // TEST 8: getUserByEmail() — Not found
    // ------------------------------------------------------------------------------------------
    @Test
    void testGetUserByEmail_NotFound() {
        when(userDao.getUserByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            userService.getUserByEmail("unknown@example.com");
        });
    }
}
