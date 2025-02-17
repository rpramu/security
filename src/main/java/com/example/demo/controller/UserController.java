package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.AuthRquest;
import com.example.demo.entities.User;
import com.example.demo.services.JWTService;
import com.example.demo.services.UserInfoService;

@RestController
@RequestMapping("/auth")
public class UserController {
	
	@Autowired
	private UserInfoService service;
	
	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@GetMapping("/Welcom")
	public String welcome() {
		return "Welcome this endpoint is not secur";
	}
	
	@PostMapping("/addNewUser")
	public String addNewUser(@RequestBody User user) {
		return service.addUser(user);
	}
	
	@GetMapping("/user/userProfile")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public String userProfile() {
		return "Welcome to User Profile";
	}
	
	@GetMapping("/user/adminProfile")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String adminProfile() {
		return "Welcome to Admin Profile";
	}
	
	@PostMapping("/generateToke")
	public String authenticateAndGetToken(@RequestBody AuthRquest authRequest) {
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		if(authentication.isAuthenticated()) {
			return jwtService.generateToken(authRequest.getUsername());
		}else {
			throw new UsernameNotFoundException("Invalid user rquest!");
		}
		
	}
	
	
	

}
