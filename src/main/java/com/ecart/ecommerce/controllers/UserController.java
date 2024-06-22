package com.ecart.ecommerce.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecart.ecommerce.dto.LoginRequest;
import com.ecart.ecommerce.dto.UserDTO;
import com.ecart.ecommerce.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Endpoint for user registration
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserDTO userDTO) {
        userService.registerUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    // Endpoint for user login
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        String cookieId = userService.loginUser(loginRequest.getUsername(), loginRequest.getPassword(), request, response);
        return ResponseEntity.ok(cookieId); // Return cookieId as per your implementation
    }

    // Endpoint for handling forgot password request
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        userService.forgotPassword(email);
        return ResponseEntity.ok("Password reset link sent to your email");
    }

    // Endpoint to update user profile
    @PutMapping("/{userId}")
    public ResponseEntity<String> updateUserProfile(@PathVariable Long userId, @Valid @RequestBody UserDTO userDTO) {
        userService.updateUserProfile(userId, userDTO);
        return ResponseEntity.ok("User profile updated successfully");
    }

    // Endpoint to delete user account
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUserAccount(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User account deleted successfully");
    }
    
 // Endpoint to get user by ID
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) {
        UserDTO userDTO = userService.getUserById(userId);
        return ResponseEntity.ok(userDTO);
    }

    // Endpoint to get all users
    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
