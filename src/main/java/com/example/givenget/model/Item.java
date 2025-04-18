
package com.example.givenget.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "items")
public record Item(
        @Id
        String id,
        String title,
        String description,
        String category,
        List<String> imageUrls,
        String donorId,
        String location,
        String datePosted,
        Boolean availability,
        String condition,
        LocalDateTime createdAt
) {
    public Item {
        if (datePosted == null || datePosted.isBlank()) {
            datePosted = LocalDateTime.now().toLocalDate().toString(); // e.g., "2025-04-16"
        }
    }
}