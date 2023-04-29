package com.rofi.restserver.services.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rofi.restserver.entities.UserEntity;
import com.rofi.restserver.exceptions.UserServiceException;
import com.rofi.restserver.repositories.UserRepository;
import com.rofi.restserver.dto.UserDto;
import com.rofi.restserver.dto.UserUtil;
import com.rofi.restserver.services.UserService;

@Service
public class UserServiceImplentation implements UserService {
	@Autowired
	UserRepository userRepository;
	

	@Override
	public List<UserDto> getUsers() {
		List<UserDto> returnValue = new ArrayList<>();

		Iterable<UserEntity> users = userRepository.findAll();

		for (UserEntity userEntity : users) {
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(userEntity, userDto);
			returnValue.add(userDto);
		}

		return returnValue;
	}

	@Override
	public UserDto getUserById(long id) {
		UserDto returnValue = new UserDto();
		UserEntity userEntity = userRepository.findById(id);

		if (userEntity == null) {
			throw new UserServiceException("User with Id = " + id + " not found");
		}

		BeanUtils.copyProperties(userEntity, returnValue);

		return returnValue;
	}

	@Override
	public UserDto getUserByUid(String uid) {
		UserDto returnValue = new UserDto();
		UserEntity userEntity = userRepository.findByUid(uid);

		if (userEntity == null) {
			throw new UserServiceException("User with Uid = " + uid + " not found");
		}

		BeanUtils.copyProperties(userEntity, returnValue);

		return returnValue;
	}

	@Override
	public UserDto getUserByUsername(String username) {
		UserDto returnValue = new UserDto();
		UserEntity userEntity = userRepository.findByUsername(username);

		if (userEntity == null) {
			throw new UserServiceException("User with username = " + username + " not found");
		}

		BeanUtils.copyProperties(userEntity, returnValue);

		return returnValue;
	}

	@Override
	public UserDto getUserByEmail(String email) {
		UserDto returnValue = new UserDto();
		UserEntity userEntity = userRepository.findByEmail(email);

		if (userEntity == null) {
			throw new UserServiceException("User with email = " + email + " not found");
		}

		BeanUtils.copyProperties(userEntity, returnValue);

		return returnValue;
	}

	@Override
	public UserDto createUser(UserDto userDto) {
		if(userRepository.findByEmail(userDto.getEmail()) != null) throw new UserServiceException("Email already exist");
		
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(userDto, userEntity);
		
		UserUtil utils = new UserUtil();
		String publicUserId = utils.generatedUserId(21);
		
		userEntity.setUid(publicUserId);
		
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
		
//		userEntity.setPassword(encoder.encode(userDto.getPassword()));
		
		userEntity.setPassword(userDto.getPassword());
		
		UserEntity storedUserDetails =  userRepository.save(userEntity);
		
		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(storedUserDetails, returnValue);
		
		return returnValue;
	}

}
