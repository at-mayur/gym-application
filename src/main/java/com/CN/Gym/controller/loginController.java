package com.CN.Gym.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class loginController {
	
	@GetMapping("/login")
	public String getLogin() {
		return "loginPage.html";
	}

}
