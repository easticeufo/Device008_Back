package com.madongfang.service;

import java.util.Base64;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.madongfang.api.ReturnApi;
import com.madongfang.api.UserApi;
import com.madongfang.entity.User;
import com.madongfang.exception.HttpBadRequestException;
import com.madongfang.exception.HttpNotFoundException;
import com.madongfang.exception.HttpUnauthorizedException;
import com.madongfang.repository.UserRepository;
import com.madongfang.util.CommonUtil;

@Service
public class UserService {

	public User userLogin(String username, String password) {
		User user = userRepository.findByUsernameAndPassword(username, password);
		if (user == null)
		{
			throw new HttpUnauthorizedException(new ReturnApi(-1, "用户名或密码错误"));
		}
		
		String token = user.getId() + "_" + String.valueOf(new Date().getTime()) + "_" + commonUtil.getRandomStringByLength(6);
		user.setToken(new String(Base64.getEncoder().encode(token.getBytes())));
		user = userRepository.save(user);
		
		return user;
	}
	
	public User userLogin(String token) {
		return userRepository.findByToken(token);
	}
	
	public List<UserApi> getUsers() {
		List<UserApi> users = new LinkedList<>();
		for (User user : userRepository.findAll()) {
			users.add(convertToUserApi(user));
		}
		
		return users;
	}
	
	public UserApi addUser(UserApi userApi) {
		User user = new User();
		user.setLevel(userApi.getLevel());
		user.setPassword(userApi.getPassword());
		user.setToken(null);
		user.setUsername(userApi.getUsername());
		user.setName(userApi.getName());
		
		user = userRepository.save(user);
		
		return convertToUserApi(user);
	}
	
	public UserApi getUser(int userId, User loginUser) {
		if (userId == 0)
		{
			userId = loginUser.getId();
		}
		User user = userRepository.findOne(userId);
		if (user == null)
		{
			throw new HttpNotFoundException(new ReturnApi(-1, "用户不存在！"));
		}
		
		return convertToUserApi(user);
	}
	
	public UserApi updateUser(int userId, UserApi userApi) {
		User user = userRepository.findOne(userId);
		if (user == null)
		{
			throw new HttpNotFoundException(new ReturnApi(-1, "用户不存在！"));
		}
		
		if (userApi.getLevel() != null)
		{
			user.setLevel(userApi.getLevel());
		}
		if (userApi.getPassword() != null)
		{
			user.setPassword(userApi.getPassword());
		}
		if (userApi.getUsername() != null)
		{
			user.setUsername(userApi.getUsername());
		}
		if (userApi.getName() != null)
		{
			user.setName(userApi.getName());
		}
		user = userRepository.save(user);
		
		return convertToUserApi(user);
	}
	
	public void deleteUser(int userId) {
		User user = userRepository.findOne(userId);
		if (user != null && user.getLevel() == 1)
		{
			throw new HttpBadRequestException(new ReturnApi(-1, "不能删除1级管理员"));
		}
		userRepository.delete(userId);
	}
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CommonUtil commonUtil;
	
	private UserApi convertToUserApi(User user)
	{
		UserApi userApi = new UserApi();
		userApi.setId(user.getId());
		userApi.setLevel(user.getLevel());
		userApi.setUsername(user.getUsername());
		userApi.setName(user.getName());
		
		return userApi;
	}
}
