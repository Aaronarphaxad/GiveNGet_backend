package com.example.givenget.service;

import com.example.givenget.model.Item;
import com.example.givenget.repository.ItemRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    // Create
    public Item createItem(Item item) {
        return itemRepository.save(item);
    }

    // Read All
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    // Read One
    public Optional<Item> getItemById(String id) {
        return itemRepository.findById(id);
    }

    // Update
    public Optional<Item> updateItem(String id, Item updatedItem) {
        return itemRepository.findById(id)
                .map(existingItem -> new Item(
                        id,
                        updatedItem.title(),
                        updatedItem.description(),
                        updatedItem.category(),
                        updatedItem.imageUrls(),
                        updatedItem.donorId(),     // ✅ fixed typo here
                        updatedItem.location(),
                        updatedItem.datePosted(),
                        updatedItem.availability(),
                        updatedItem.condition(),
                        existingItem.createdAt()   // preserve original createdAt
                ))
                .map(itemRepository::save);
    }

    // Delete
    public void deleteItem(String id) {
        itemRepository.deleteById(id);
    }
    
    
    // list by DonorId
    public List<Item> getItemsByDonorId(String donorId) {
    	try {
            List<Item> items = itemRepository.findByDonorId(donorId);
            if (items.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No donations found");
            }
            return items;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Query failed", e);
        }
    }
}