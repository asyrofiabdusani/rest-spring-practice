package com.rofi.restserver.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.rofi.restserver.entities.UserEntity;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long>{

	UserEntity findById(long id);

	UserEntity findByUid(String uid);

	UserEntity findByEmail(String email);

	UserEntity findByUsername(String username);

}
