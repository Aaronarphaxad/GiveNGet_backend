package com.example.givenget.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.givenget.model.User;
import com.example.givenget.repository.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}
	
	//Create
	public User createUser(User user) {
		return userRepository.save(user);
	}

	//Read all
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
	
	//Read one
	public Optional<User> getUserById(String id){
		return userRepository.findById(id);
	}
	
	//
	public Optional<User> updateUser(String id, User updatedUser){
		return userRepository.findById(id).map(existingUser -> new User(
				id,
				updatedUser.name(),
				updatedUser.phoneNum(),
				updatedUser.email(),
				updatedUser.password(),
				updatedUser.location(),
				updatedUser.rating(),
				updatedUser.likedItems(),
				updatedUser.donatedItems(),
				updatedUser.receivedItems(),
				updatedUser.notifications(),
				updatedUser.createdAt()
				)).map(userRepository::save);
	}
	
	//Delete
	public void deleteUser(String id) {
		userRepository.deleteById(id);
	}
}