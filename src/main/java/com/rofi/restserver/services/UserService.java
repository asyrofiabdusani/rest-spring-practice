package com.rofi.restserver.services;

import java.util.List;

import com.rofi.restserver.dto.UserDto;

public interface UserService {
	List<UserDto> getUsers();
	UserDto getUserById(long id);
	UserDto getUserByUid(String uid);
	UserDto getUserByUsername(String username);
	UserDto getUserByEmail(String email);
	UserDto createUser(UserDto userDto);
	
}
