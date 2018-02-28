package com.madongfang.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.madongfang.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	public User findByUsernameAndPassword(String username, String password);
	
	public User findByToken(String token);
}
