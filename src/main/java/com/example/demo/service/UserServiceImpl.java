package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.config.AppConstants;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.payload.UserDto;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private RoleRepository roleRepository;
	

	@Override
	public List<UserDto> getAllUsers() {
		List<User> listUsers = userRepo.findAll();
		List<UserDto> listDtoUsers = listUsers.stream().map(user -> mapper.map(user, UserDto.class))
				.collect(Collectors.toList());
		return listDtoUsers;
	}

	@Override
	public UserDto getUserById(Integer id) {
		User usr = userRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		return mapper.map(usr, UserDto.class);
	}

	@Override
	public UserDto registerUser(UserDto user) {
		User usr = userRepo.save(mapper.map(user, User.class));
		return mapper.map(usr, UserDto.class);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer id) {
		User usr = userRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		usr.setName(userDto.getName());
		usr.setEmail(userDto.getEmail());
		usr.setPassword(userDto.getPassword());
		usr.setAbout(userDto.getAbout());
		
		User updatedUser = userRepo.save(usr);
		return mapper.map(updatedUser, UserDto.class);
	}

	@Override
	public void deleteUser(Integer id) {
		User user = userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User", "Id", id));
		userRepo.delete(user);
	}

	@Override
	public UserDto registerNewUser(UserDto dto) {
		User user = this.mapper.map(dto, User.class);
		
		user.setPassword(encoder.encode(user.getPassword()));
		
		Role role = roleRepository.findById(AppConstants.NORMAL_USER).get();
		
		user.getRoles().add(role);
		
		User newUser = this.userRepo.save(user);
		
		return mapper.map(newUser, UserDto.class);
	}

	

}
