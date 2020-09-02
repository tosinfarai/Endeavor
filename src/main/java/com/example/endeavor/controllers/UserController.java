package com.example.endeavor.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.example.endeavor.exceptions.ResourceNotFoundException;
import com.example.endeavor.repositories.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping
	public List<User> getUsers() {
		return userRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId)
		throws ResourceNotFoundException {
	        User user = userRepository.findById(userId)
	          .orElseThrow(() -> new ResourceNotFoundException
	        		  ("User " + userId + " not found"));
	        return ResponseEntity.ok().body(user);
	}
	
	@PostMapping
	public User createUser (@Valid @RequestBody User user) {
		return userRepository.save(user);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long userId,
			@Valid @RequestBody User userDetails) throws ResourceNotFoundException {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException
						("User " + userId + " not found"));

		user.setEmailAddress(userDetails.getEmailAddress());
		user.setLastName(userDetails.getLastName());
		user.setFirstName(userDetails.getFirstName());
		user.setUsername(userDetails.getUsername());
		final User updatedUser = userRepository.save(user);
		return ResponseEntity.ok(updatedUser);
	}
	
	@DeleteMapping("/{id}")
	public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId)
			throws ResourceNotFoundException {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException
						("User " + userId + " not found"));

		userRepository.delete(user);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	
}
