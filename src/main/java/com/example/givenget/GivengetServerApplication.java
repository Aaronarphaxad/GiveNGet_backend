package com.example.givenget;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
	public CommandLineRunner loadData(ItemRepository itemRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
	    return args -> {
	    	//clear data
	    	userRepository.deleteAll();
	    	itemRepository.deleteAll();
	    	
	    	//save users
	    	if (userRepository.count() == 0) {
	        	List<User> sampleUsers = List.of(
	        			new User(null,
	        	                "Reiben",
	        	                "2345678907",
	        	                "Reiben@123.com",
	        	                passwordEncoder.encode("Otakurino123"), // ✅ hash the password
	        	                "107 Royal Avenue",
	        	                4,
	        	                List.of(),//likedItems
	        	                List.of(),//donatedItems
	        	                List.of(),//receivedItems
	        	                List.of("Otakurino"),
	        	                LocalDateTime.now()),
	        			
	        			new User(null,
	        	                "Aaron",
	        	                "1234567890",
	        	                "Aaron@123.com",
	        	                passwordEncoder.encode("Aaronarphaxa"), // ✅ hash the password
	        	                "707 Royal Avenue",
	        	                4,
	        	                List.of(),
	        	                List.of(),
	        	                List.of(),
	        	                List.of("donor123"),
	        	                LocalDateTime.now()),
	        			
	        			new User(null,
	        	                "Isabel",
	        	                "7890123456",
	        	                "Isabel@123.com",
	        	                passwordEncoder.encode("Isabel"), // ✅ hash the password
	        	                "207 Royal Avenue",
	        	                4,
	        	                List.of(),
	        	                List.of(),
	        	                List.of(),
	        	                List.of("donor123"),
	        	                LocalDateTime.now()),
	        			
	        			new User(null,
	        	                "Yaolong",
	        	                "2369785806",
	        	                "Yaolong@123.com",
	        	                passwordEncoder.encode("123456"), // ✅ hash the password
	        	                "606 Royal Avenue",
	        	                4,
	        	                List.of(),
	        	                List.of(),
	        	                List.of(),
	        	                List.of("donor123"),
	        	                LocalDateTime.now())
	        			);
	        	List<User> savedUsers = userRepository.saveAll(sampleUsers);
	            System.out.println("Users inserted: " + savedUsers.size());
	            
	            // get userID
		    	String reibenId = savedUsers.get(0).id();
		    	String aaronId = savedUsers.get(1).id();
		    	String isabelId = savedUsers.get(2).id();
		    	String yaolongId = savedUsers.get(3).id();
	            
	            // connect to items
		    	if (itemRepository.count() == 0) {
		            List<Item> sampleItems = List.of(
		                new Item(null, "Winter Jacket", "A warm jacket for winter", "Clothing",
		                        List.of("https://res.cloudinary.com/dcmmplalc/image/upload/v1744916001/winter-jacket_w77q4u.jpg"),
		                        reibenId, "Downtown Library", "10-02-2025", true, "New", LocalDateTime.now()),

		                new Item(null, "Study Desk", "Perfect desk for students", "Furniture",
		                        List.of("https://res.cloudinary.com/dcmmplalc/image/upload/v1744916028/study-desk_oydimj.jpg"),
		                        aaronId, "School Lounge", "10-02-2025", true, "Fairly new", LocalDateTime.now()),

		                new Item(null, "Shoes for kids", "Perfect shoes for kids", "Clothing",
		                        List.of("https://res.cloudinary.com/dcmmplalc/image/upload/v1744916028/kids-shoes_qzd5ja.jpg"),
		                        isabelId, "Community Hall", "10-02-2025", true, "Used", LocalDateTime.now()),

		                new Item(null, "Study Desk", "Perfect desk for students", "Electronics",
		                        List.of("https://res.cloudinary.com/dcmmplalc/image/upload/v1744916028/study-desk-3_f9aqzk.jpg"),
		                        yaolongId, "Room 405", "10-02-2025", false, "Fairly new", LocalDateTime.now()),

		                new Item(null, "Study Desk", "Perfect desk for students", "Books",
		                        List.of("https://res.cloudinary.com/dcmmplalc/image/upload/v1744916030/study-desk-2_ha0pts.jpg"),
		                        reibenId, "Library", "10-02-2025", true, "New", LocalDateTime.now()),

		                new Item(null, "Piano", "Perfect for music lovers", "Furniture",
		                        List.of("https://res.cloudinary.com/dcmmplalc/image/upload/v1744916028/piano_s9akqa.jpg"),
		                        yaolongId, "Auditorium", "10-02-2025", false, "Used", LocalDateTime.now())
		            );

		            itemRepository.saveAll(sampleItems);
		            System.out.println("Sample data inserted");
		            
		            List<Item> savedItems = itemRepository.saveAll(sampleItems);
                    System.out.println("Items inserted: " + savedItems.size());
                    
                    //connect to donatedIDItems
                    savedItems.forEach(item -> {
                        // based on donorId to find the user
                        User donor = userRepository.findById(item.donorId())
                            .orElseThrow(() -> new IllegalStateException("No user matched: " + item.donorId()));

                        List<String> newDonations = new ArrayList<>(donor.donatedIDItems());
                        newDonations.add(item.id());

                        User updatedUser = new User(
                            donor.id(),
                            donor.name(),
                            donor.phoneNum(),
                            donor.email(),
                            donor.password(),
                            donor.location(),
                            donor.rating(),
                            donor.likedIDItems(),
                            newDonations,  // renew data
                            donor.receivedIDItems(),
                            donor.notifications(),
                            donor.createdAt()
                        );

                        userRepository.save(updatedUser);
                        System.out.println("Renew donor: "+donor.name() + " done");
                    });

		        } else {
		            System.out.println("Sample data already exists, skipping insert.");
		        }
		    	
	        } else {
	            System.out.println("User data already exists, skipping insert");
	        }
	    };
	}
}
