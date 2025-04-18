package com.example.givenget.controller;

import com.example.givenget.model.Item;
import com.example.givenget.service.ItemService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/givenget/items")
@Tag(name = "Items", description = "APIs for managing donation items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @Operation(summary = "Create a new item")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Item created successfully",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Item.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized - Token missing or invalid", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Item> createItem(@RequestBody Item item) {
        return ResponseEntity.ok(itemService.createItem(item));
    }

    @Operation(summary = "Get all donation items")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of items retrieved",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Item.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorizedr", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        return ResponseEntity.ok(itemService.getAllItems());
    }

    @Operation(summary = "Get an item by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Item found",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Item.class))),
        @ApiResponse(responseCode = "404", description = "Item not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(
        @Parameter(description = "ID of the item to retrieve") @PathVariable String id) {
        return itemService.getItemById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update an existing item")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Item updated successfully",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Item.class))),
        @ApiResponse(responseCode = "404", description = "Item not found", content = @Content),
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(
        @Parameter(description = "ID of the item to update") @PathVariable String id,
        @RequestBody Item item) {
        return itemService.updateItem(id, item)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete an item by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Item deleted successfully", content = @Content),
        @ApiResponse(responseCode = "404", description = "Item not found", content = @Content),
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(
        @Parameter(description = "ID of the item to delete") @PathVariable String id) {
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
}
