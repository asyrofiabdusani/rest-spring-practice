package com.rofi.restserver.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rofi.restserver.dto.UserDto;
import com.rofi.restserver.exceptions.UserServiceException;
import com.rofi.restserver.model.UserDetailRequestModel;
import com.rofi.restserver.responses.UserRest;
import com.rofi.restserver.services.UserService;
import com.rofi.restserver.responses.ErrorMessages;

@RestController
@RequestMapping("users")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping
	public List<UserRest> getUsers() {
		List<UserRest> returnValue = new ArrayList<>();

		List<UserDto> users = userService.getUsers();

		for (UserDto userDto : users) {
			UserRest userModel = new UserRest();
			BeanUtils.copyProperties(userDto, userModel);
			returnValue.add(userModel);
		}
		return returnValue;
	}

	@GetMapping("/search")
	public UserRest getUser(@RequestParam(value = "id", defaultValue = "0") long id, @RequestParam(value = "uid", defaultValue = "") String uid, @RequestParam(value = "username", defaultValue = "") String username, @RequestParam(value = "email", defaultValue = "") String email) {
		UserRest returnValue = new UserRest();

		UserDto userDto =null;
		
		if (id != 0) {
			userDto = userService.getUserById(id);
		} else if (uid.length() != 0){
			userDto = userService.getUserByUid(uid);
		}  else if (username.length() != 0){
			userDto = userService.getUserByUsername(username);
		} else if (email.length() != 0){
			userDto = userService.getUserByEmail(email);
		} else {
			throw new UserServiceException("wrong param");
		}
		
		BeanUtils.copyProperties(userDto, returnValue);
		return returnValue;
	}
	
	@PostMapping
	public UserRest postUser(@RequestBody UserDetailRequestModel userRequest) throws Exception{
		UserRest returnValue = new UserRest();
		
		if (userRequest.getUsername().isEmpty() || userRequest.getEmail().isEmpty() || userRequest.getPassword().isEmpty()) {
			throw new UserServiceException(ErrorMessages.MISING_REQUIRED_FIELD.getErrorMessage());
		}
		
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userRequest, userDto);

		UserDto createdUser = userService.createUser(userDto);
		BeanUtils.copyProperties(createdUser, returnValue);
		
		return returnValue;
		
	}
}
