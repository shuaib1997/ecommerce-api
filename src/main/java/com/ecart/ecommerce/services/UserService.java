package com.ecart.ecommerce.services;

import java.util.List;

import com.ecart.ecommerce.dto.UserDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {

	public void registerUser(UserDTO userDTO);

	public String loginUser(String username, String password, HttpServletRequest request, HttpServletResponse response);

	public void forgotPassword(String email);

	public void updateUserProfile(Long userId, UserDTO userDTO);

	public void deleteUser(Long userId);
	
	public List<UserDTO> getAllUsers();
	
	public UserDTO getUserById(Long userId);

}
