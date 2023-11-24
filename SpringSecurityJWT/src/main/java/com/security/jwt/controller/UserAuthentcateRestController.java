package com.security.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.security.jwt.sec.config.JwtUtil;
import com.security.jwt.sec.config.MyUserDeatailService;
import com.security.jwt.sec.config.UserAuthenticate;

@RestController
public class UserAuthentcateRestController {

	@Autowired
	private AuthenticationManager authManger;
	
	@Autowired
	private MyUserDeatailService userDetails;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@PostMapping("/userAuthenticate")
	public String authentciateUser(@RequestBody UserAuthenticate userData) throws Exception{
		try {
		authManger.authenticate(new UsernamePasswordAuthenticationToken(userData.getUsername(), userData.getPassword()));
		}catch (Exception e) {
			throw new Exception("credentials are in valid");
		}
		
		UserDetails userDetails = this.userDetails.loadUserByUsername(userData.getUsername());
		
		String token = this.jwtUtil.generateToken(userDetails);
		
		return token;
	}
	
	
}
