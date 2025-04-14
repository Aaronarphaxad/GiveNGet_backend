package com.example.givenget.repository;

import com.example.givenget.model.User;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
//	List<User> findByUserId(String userId);

}
