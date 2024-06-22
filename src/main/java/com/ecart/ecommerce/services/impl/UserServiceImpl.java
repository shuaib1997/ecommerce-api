package com.ecart.ecommerce.services.impl;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecart.ecommerce.dto.UserDTO;
import com.ecart.ecommerce.exception.UserAlreadyExistsException;
import com.ecart.ecommerce.model.User;
import com.ecart.ecommerce.repositories.UserRepository;
import com.ecart.ecommerce.services.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@Service
public class UserServiceImpl implements UserService{
	
	private final UserRepository userRepository;
	
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

	@Override
	public void registerUser(UserDTO userDTO) {
		Optional<User> existingUser = userRepository.findByUsername(userDTO.getUsername());
		existingUser.ifPresent(ex -> {
		    throw new UserAlreadyExistsException("User with username " + userDTO.getUsername() + " already exists!");
		});

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPhone(userDTO.getPhone());
        user.setAddress(userDTO.getAddress());
        user.setRole(userDTO.getRole());
        user.setEnabled(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());

        userRepository.save(user);
		
	}

	@Override
    public String loginUser(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
            	// Create a session and get the session ID
                request.getSession(true); // true to create a new session if one does not exist
                String sessionId = request.getSession().getId();
                
                // Set the session ID in a cookie
                Cookie cookie = new Cookie("JSESSIONIDD", sessionId);
                cookie.setHttpOnly(true);
                cookie.setPath("/");
                response.addCookie(cookie);
                
                return "Login successful, session ID set in cookie";
            }
        }
        throw new RuntimeException("Invalid username or password");
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

	@Override
	public void forgotPassword(String email) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUserProfile(Long userId, UserDTO userDTO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUser(Long userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<UserDTO> getAllUsers() {
		List<User> users = userRepository.findAll();
		return users.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public UserDTO getUserById(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private UserDTO convertToDTO(User user) {
	    UserDTO userDTO = new UserDTO();
	    userDTO.setId(user.getId());
	    userDTO.setPassword(user.getPassword());
	    userDTO.setUsername(user.getUsername());
	    userDTO.setEmail(user.getEmail());
	    userDTO.setFirstName(user.getFirstName());
	    userDTO.setLastName(user.getLastName());
	    userDTO.setPhone(user.getPhone());
	    userDTO.setAddress(user.getAddress());
	    userDTO.setRole(user.getRole());
	    userDTO.setEnabled(user.isEnabled());
	    userDTO.setCreatedAt(user.getCreatedAt());
	    userDTO.setUpdatedAt(user.getUpdatedAt());
	    userDTO.setLastLogin(user.getLastLogin());
	    return userDTO;
	}



}
