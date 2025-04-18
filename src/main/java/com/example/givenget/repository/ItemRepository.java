package com.example.givenget.repository;

import com.example.givenget.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ItemRepository extends MongoRepository<Item, String> {
	@Query("{ 'donor_id': ?0 }")
	List<Item> findByDonorId(String donorId);
}
