package com.example.simplerestapi.controller;

import com.example.simplerestapi.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateProduct() throws Exception {
        // Create a product object
        Product product = new Product(null, "Test Product", "Description", 29.99);
        
        // Convert product to JSON
        String productJson = objectMapper.writeValueAsString(product);
        
        // Perform POST request and validate response
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Test Product"))
                .andExpect(jsonPath("$.price").value(29.99));
    }
    
    @Test
    public void testGetProduct() throws Exception {
        // First create a product
        Product product = new Product(null, "Test Product", "Description", 29.99);
        String productJson = objectMapper.writeValueAsString(product);
        
        String createResult = mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productJson))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        
        // Extract ID from the created product
        Product createdProduct = objectMapper.readValue(createResult, Product.class);
        Long productId = createdProduct.getId();
        
        // Perform GET request and validate response
        mockMvc.perform(get("/api/products/" + productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productId))
                .andExpect(jsonPath("$.name").value("Test Product"));
    }
    
    @Test
    public void testProductNotFound() throws Exception {
        // Request a non-existent product
        mockMvc.perform(get("/api/products/999"))
                .andExpect(status().isNotFound());
    }
    
    // Additional tests for PUT, DELETE, PATCH, etc. would follow the same pattern
}