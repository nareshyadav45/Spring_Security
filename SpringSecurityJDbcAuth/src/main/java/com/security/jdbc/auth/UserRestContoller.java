package com.security.jdbc.auth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class UserRestContoller {
	
	@GetMapping("/")
	public String welcome()
	{
	return "welocme to home page";	
	}	
	
	@GetMapping("/user")
	public String user() {
		return "User Access Page......";}
	
	@GetMapping("/admin")
	public String admin() {
		return "Admin accessable page";
	}
	

}
