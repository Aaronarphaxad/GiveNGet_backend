package com.example.givenget;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.givenget.model.Item;
import com.example.givenget.model.User;
import com.example.givenget.repository.ItemRepository;
import com.example.givenget.repository.UserRepository;

@SpringBootApplication
public class GivengetServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GivengetServerApplication.class, args);
	}
	
	@Bean
    public CommandLineRunner loadData(ItemRepository itemRepository) {
        return args -> {
            if (itemRepository.count() == 0) {
                Item sampleItem = new Item(
                        null,
                        "Sample Chair",
                        "A gently used wooden chair",
                        "Furniture",
                        List.of("https://example.com/images/chair1.jpg"),
                        "Downtown Library",
                        "donor123",
                        LocalDateTime.now()
                );
                itemRepository.save(sampleItem);
                System.out.println("✅ Sample data inserted");
            } else {
                System.out.println("ℹ️ Sample data already exists, skipping insert.");
            }
        };
    }
	
	@Bean  //default user
	public CommandLineRunner loadUserData(UserRepository userRepository) {
        return args -> {
           
            if (userRepository.count() == 0) {
                User sampleUser = new User(
                        null,
                        "John",
                        "1234567890",
                        "John@123.com",
                        "700 Royal Avenue",
                        4,
                        List.of(),
                        List.of(),
                        List.of(),
                        List.of("donor123"),
                        LocalDateTime.now()
                );
                userRepository.save(sampleUser);
                System.out.println("✅ User data inserted");
            } else {
                System.out.println("ℹ️ User data already exists, skipping insert.");
            }
        };
    }
}
