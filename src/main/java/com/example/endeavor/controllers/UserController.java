package com.example.endeavor.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.endeavor.entities.User;
import com.example.endeavor.repositories.UserRepository;

@RestController
@RequestMapping("/api/v1")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/users")
	public List<User> getUsers() {
		return userRepository.findAll();
	}
	
	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId)
		throws InvalidConfigurationPropertyValueException {
	        User user = userRepository.findById(userId)
	          .orElseThrow(() -> new InvalidConfigurationPropertyValueException
	        		  ("User ", userId, " not found"));
	        return ResponseEntity.ok().body(user);
	}
	
	@PostMapping("/users")
	public User createUser (@Valid @RequestBody User user) {
		return userRepository.save(user);
	}
	
	@PutMapping("/users/{id}")
	public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long userId,
			@Valid @RequestBody User userDetails) throws InvalidConfigurationPropertyValueException {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new InvalidConfigurationPropertyValueException("User ", userId, " not found"));

		user.setEmailAddress(userDetails.getEmailAddress());
		user.setLastName(userDetails.getLastName());
		user.setFirstName(userDetails.getFirstName());
		final User updatedUser = userRepository.save(user);
		return ResponseEntity.ok(updatedUser);
	}
	
	@DeleteMapping("/users/{id}")
	public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId)
			throws InvalidConfigurationPropertyValueException {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new InvalidConfigurationPropertyValueException
						("User ", userId, " not found"));

		userRepository.delete(user);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	
}
