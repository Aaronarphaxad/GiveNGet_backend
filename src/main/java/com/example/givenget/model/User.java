package com.example.givenget.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public record User(
		@Id
		String id,
		String name,
		String phoneNum,
		String email,
		String password,
		String location,
		int rating,
		List<Item> likedItems,
		List<Item> donatedItems,
		List<Item> receivedItems,
		List<String> notifications,
		LocalDateTime createdAt
		) {
		public User{
			if(createdAt == null) {
				createdAt = LocalDateTime.now();
			}
		}
}
