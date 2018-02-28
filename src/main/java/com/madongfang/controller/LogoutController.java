package com.madongfang.controller;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/api/logout")
public class LogoutController {

	@PostMapping
	public void logout(HttpSession session)
	{
		session.invalidate();
	}
}
