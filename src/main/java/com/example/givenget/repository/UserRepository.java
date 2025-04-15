package com.example.givenget.repository;

import com.example.givenget.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.*;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);

    // Temporary in-memory password map for demo (replace with secure storage)
    Map<String, String> PASSWORD_STORE = new HashMap<>();

    default void storePassword(String email, String encodedPassword) {
        PASSWORD_STORE.put(email, encodedPassword);
    }

    default String getPasswordByEmail(String email) {
        return PASSWORD_STORE.get(email);
    }
}
