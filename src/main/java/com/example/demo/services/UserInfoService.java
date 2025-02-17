package com.example.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entities.User;
import com.example.demo.entities.repository.UserRepository;

@Service
public class UserInfoService implements UserDetailsService {
	
	private final UserRepository repository;
    private final PasswordEncoder encode;

    // Constructor injection to avoid circular dependency and improve clarity
    @Autowired
    public UserInfoService(UserRepository repository, PasswordEncoder encode) {
        this.repository = repository;
        this.encode = encode;
    }
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<User> userDetail = repository.findByEmail(username);
		return userDetail.map(UserInfoDetails :: new)
				.orElseThrow(() -> new UsernameNotFoundException("user not found: " + username));
	}
	
	
	public String addUser(User user) {
		
		user.setPassword(encode.encode(user.getPassword()));
		repository.save(user);
		return "User Added Successfully";
	}
	

}
