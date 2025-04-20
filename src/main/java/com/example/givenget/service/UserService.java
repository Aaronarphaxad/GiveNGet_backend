package com.example.givenget.service;

import java.util.List;
import java.util.Map;
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
				updatedUser.likedIDItems(),
				updatedUser.donatedIDItems(),
				updatedUser.receivedIDItems(),
				updatedUser.notifications(),
				updatedUser.createdAt()
				)).map(userRepository::save);
	}

	//Update fields
	public Optional<User> updateUserFields(String id, Map<String, Object> updates) {
		return userRepository.findById(id).map(existingUser -> {
			String name = updates.containsKey("name") ? (String) updates.get("name") : existingUser.name();
			String email = updates.containsKey("email") ? (String) updates.get("email") : existingUser.email();
			String phoneNum = updates.containsKey("phoneNum") ? (String) updates.get("phoneNum") : existingUser.phoneNum();
			String location = updates.containsKey("location") ? (String) updates.get("location") : existingUser.location();

			// Create new updated user
			User updatedUser = new User(
					existingUser.id(),
					name,
					phoneNum,
					email,
					existingUser.password(), // Keep password unchanged
					location,
					existingUser.rating(),
					existingUser.likedIDItems(),
					existingUser.donatedIDItems(),
					existingUser.receivedIDItems(),
					existingUser.notifications(),
					existingUser.createdAt()
			);

			return userRepository.save(updatedUser);
		});
	}


	//Delete
	public void deleteUser(String id) {
		userRepository.deleteById(id);
	}
}