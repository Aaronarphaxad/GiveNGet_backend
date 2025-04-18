package com.example.givenget;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
	public WebMvcConfigurer corsConfigurer() {
	    return new WebMvcConfigurer() {
	        @Override
	        public void addCorsMappings(CorsRegistry registry) {
	            registry.addMapping("/**").allowedOrigins("*");
	        }
	    };
	}

	
	@Bean
	public CommandLineRunner loadData(ItemRepository itemRepository) {
	    return args -> {
	    	itemRepository.deleteAll();
	    	
	        if (itemRepository.count() == 0) {
	            List<Item> sampleItems = List.of(
	                new Item(null, "Winter Jacket", "A warm jacket for winter", "Clothing",
	                        List.of("https://res.cloudinary.com/dcmmplalc/image/upload/v1744916001/winter-jacket_w77q4u.jpg"),
	                        "Reiben", "Downtown Library", "10-02-2025", true, "New", LocalDateTime.now()),

	                new Item(null, "Study Desk", "Perfect desk for students", "Furniture",
	                        List.of("https://res.cloudinary.com/dcmmplalc/image/upload/v1744916028/study-desk_oydimj.jpg"),
	                        "Aaron", "School Lounge", "10-02-2025", true, "Fairly new", LocalDateTime.now()),

	                new Item(null, "Shoes for kids", "Perfect shoes for kids", "Clothing",
	                        List.of("https://res.cloudinary.com/dcmmplalc/image/upload/v1744916028/kids-shoes_qzd5ja.jpg"),
	                        "Isabel", "Community Hall", "10-02-2025", true, "Used", LocalDateTime.now()),

	                new Item(null, "Study Desk", "Perfect desk for students", "Electronics",
	                        List.of("https://res.cloudinary.com/dcmmplalc/image/upload/v1744916028/study-desk-3_f9aqzk.jpg"),
	                        "Yaolong", "Room 405", "10-02-2025", false, "Fairly new", LocalDateTime.now()),

	                new Item(null, "Study Desk", "Perfect desk for students", "Books",
	                        List.of("https://res.cloudinary.com/dcmmplalc/image/upload/v1744916030/study-desk-2_ha0pts.jpg"),
	                        "Reiben", "Library", "10-02-2025", true, "New", LocalDateTime.now()),

	                new Item(null, "Piano", "Perfect for music lovers", "Furniture",
	                        List.of("https://res.cloudinary.com/dcmmplalc/image/upload/v1744916028/piano_s9akqa.jpg"),
	                        "Yaolong", "Auditorium", "10-02-2025", false, "Used", LocalDateTime.now())
	            );

	            itemRepository.saveAll(sampleItems);
	            System.out.println("✅ Sample data inserted");
	        } else {
	            System.out.println("ℹ️ Sample data already exists, skipping insert.");
	        }
	    };
	}
	
	@Bean
	public CommandLineRunner loadUserData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
	    return args -> {
	        if (userRepository.count() == 0) {
	            User sampleUser = new User(
	                null,
	                "John",
	                "1234567890",
	                "John@123.com",
	                passwordEncoder.encode("123456"), // ✅ hash the password
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
	            System.out.println("ℹ️ User data already exists, skipping insert");
	        }
	    };
	}

}
