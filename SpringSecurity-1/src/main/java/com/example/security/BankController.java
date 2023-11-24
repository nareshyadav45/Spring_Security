package com.example.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankController {

	@GetMapping("/")
	public String welcome() {
		return "welocme page....";
		
	}
	
	@GetMapping("/transefer")
	public String transfer() {
		return "Balnace transfer";
	}
	
	@GetMapping("/balance")
	public String balanceCheck() {
		return "balance is Rs/-20000 INR";
	}
	@GetMapping("/about")
	public String about() {
		return "To know everyThing about bank...";
	}
}
