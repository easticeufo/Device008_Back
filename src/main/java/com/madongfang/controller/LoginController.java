package com.madongfang.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.madongfang.api.LoginApi;
import com.madongfang.api.UserApi;
import com.madongfang.entity.User;
import com.madongfang.service.UserService;

@Controller
@RequestMapping(value="/api/login")
public class LoginController {
	
	@PostMapping
	public @ResponseBody UserApi login(HttpSession session, @RequestBody LoginApi loginApi) 
	{
		User user = userService.userLogin(loginApi.getUsername(), loginApi.getPassword());
		session.setAttribute("user", user);
		UserApi userApi = new UserApi();
		userApi.setId(user.getId());
		userApi.setLevel(user.getLevel());
		userApi.setToken(user.getToken());
		userApi.setUsername(user.getUsername());
		userApi.setName(user.getName());
		
		return userApi;
	}
	
	@Autowired
	private UserService userService;
}
