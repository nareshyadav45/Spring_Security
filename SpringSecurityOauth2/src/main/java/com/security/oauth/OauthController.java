package com.security.oauth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OauthController {

	@GetMapping("/")
	public String welcome() {
		return "welcome to oauth authetication.............";
	}
	
}
