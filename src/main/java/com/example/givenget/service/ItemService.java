package com.example.givenget.service;

import com.example.givenget.model.Item;
import com.example.givenget.repository.ItemRepository;
import org.springframework.stereotype.Service;

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
                        updatedItem.location(),
                        updatedItem.donorId(),
                        existingItem.createdAt()
                ))
                .map(itemRepository::save);
    }

    // Delete
    public void deleteItem(String id) {
        itemRepository.deleteById(id);
    }
}
