package com.project.vistaro.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.project.vistaro.dto.AuthResponse;
import com.project.vistaro.dto.LoginRequest;
import com.project.vistaro.dto.UserDto;
import com.project.vistaro.model.User;
import com.project.vistaro.model.UserRole;
import com.project.vistaro.security.JwtUtil;
import com.project.vistaro.service.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    private User dtoToEntity(UserDto dto) {
        User entity = new User();
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setPassword(dto.getPassword());
        entity.setCity(dto.getCity());
        if (dto.getRole() != null) {
            entity.setRole(UserRole.valueOf(dto.getRole().toUpperCase()));
        }
        return entity;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody UserDto dto) {

        User saved = userService.addUser(dtoToEntity(dto));

        String roleName = saved.getRole() != null ? saved.getRole().name() : "USER";
        String token = jwtUtil.generateToken(saved.getEmail(), roleName);

        AuthResponse resp = new AuthResponse(
                token,
                saved.getEmail(),
                roleName,
                saved.getCity(),
                saved.getUserId()
        );

        return ResponseEntity.ok(resp);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest req) {

        User user = userService.getUserByEmail(req.getEmail());

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).build();
        }

        String roleName = user.getRole().name();
        String token = jwtUtil.generateToken(user.getEmail(), roleName);

        AuthResponse resp = new AuthResponse(
                token,
                user.getEmail(),
                roleName,
                user.getCity(),
                user.getUserId()
        );

        return ResponseEntity.ok(resp);
    }
}
