package com.example.givenget.controller;

import java.util.List;
import java.util.Map;

import com.example.givenget.model.User;
import com.example.givenget.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/givenget/users")
@Tag(name = "Users", description = "APIs for managing users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@Operation(summary = "Create a new user (internal use)")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "User created successfully",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
		@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
	})
	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user){
		return ResponseEntity.ok(userService.createUser(user));
	}

	@Operation(summary = "Get all users (admin-only access)")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "List of users retrieved",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
		@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
	})
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers(){
		return ResponseEntity.ok(userService.getAllUsers());
	}

	@Operation(summary = "Get a user by ID")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "User found",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
		@ApiResponse(responseCode = "404", description = "User not found", content = @Content),
		@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
	})
	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(
		@Parameter(description = "ID of the user to retrieve") @PathVariable String id){
		return userService.getUserById(id)
				.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@Operation(summary = "Update a user's information")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "User updated successfully",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
		@ApiResponse(responseCode = "404", description = "User not found", content = @Content),
		@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
	})
	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody Map<String, Object> updates) {
		return userService.updateUserFields(id, updates)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@Operation(summary = "Fully update a user, including liked/donated/received items")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User updated successfully",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
			@ApiResponse(responseCode = "404", description = "User not found", content = @Content),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
	})
	@PutMapping("/full/{id}")
	public ResponseEntity<User> fullyUpdateUser(@PathVariable String id, @RequestBody User updatedUser) {
		return userService.updateUser(id, updatedUser)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@Operation(summary = "Delete a user by ID")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "User deleted successfully", content = @Content),
		@ApiResponse(responseCode = "404", description = "User not found", content = @Content),
		@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(
		@Parameter(description = "ID of the user to delete") @PathVariable String id){
		userService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}
}
