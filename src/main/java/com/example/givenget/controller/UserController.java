package com.example.givenget.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.givenget.model.User;
import com.example.givenget.service.UserService;

@RestController
@RequestMapping("/api/givenget/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	//Create
	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user){
		return ResponseEntity.ok(userService.createUser(user));
	}
	
	//Read all
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers(){
		return ResponseEntity.ok(userService.getAllUsers());
	}
	
	//Read one
	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable String id){
		return userService.getUserById(id)
				.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}
	
	//Update
	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user){
		return userService.updateUser(id, user)
				.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}
	
	//Delete
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable String id){
		userService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}
}