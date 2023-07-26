package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.User;
import com.example.demo.payload.UserDto;

public interface UserService {
	
	UserDto registerNewUser(UserDto dto);
	
	public List<UserDto> getAllUsers();
	public UserDto getUserById(Integer id);
	public UserDto registerUser(UserDto user); 
	public UserDto updateUser(UserDto user, Integer id);
	public void deleteUser(Integer id);
	
	
}
