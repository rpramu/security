package com.example.demo.services;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.entities.User;

public class UserInfoDetails implements UserDetails {
	
	private  String username;
	private String password;
	private List<GrantedAuthority> authorities;
	
	public UserInfoDetails(User userInfo) {
		this.username = userInfo.getName();
		this.password = userInfo.getPassword();
		this.authorities = List.of(userInfo.getRole().split(","))
				.stream()
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
		
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities(){
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

}
