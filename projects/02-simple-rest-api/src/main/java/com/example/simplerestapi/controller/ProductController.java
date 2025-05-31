package com.example.simplerestapi.controller;

import com.example.simplerestapi.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    // In-memory storage for products
    private final Map<Long, Product> productMap = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    // GET all products
    @GetMapping
    public List<Product> getAllProducts() {
        return new ArrayList<>(productMap.values());
    }

    // GET a specific product by ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        if (!productMap.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productMap.get(id));
    }

    // POST a new product
    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        // Assign a new ID
        long newId = idGenerator.getAndIncrement();
        product.setId(newId);
        
        // Save the product
        productMap.put(newId, product);
        
        // Return 201 Created with the new product
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    // PUT (update) an existing product
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody Product product) {
        
        if (!productMap.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        
        // Ensure ID in URL matches product ID
        product.setId(id);
        
        // Update the product
        productMap.put(id, product);
        
        return ResponseEntity.ok(product);
    }

    // DELETE a product
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (!productMap.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        
        productMap.remove(id);
        return ResponseEntity.noContent().build();
    }
    
    // PATCH a product (partial update)
    @PatchMapping("/{id}")
    public ResponseEntity<Product> partialUpdateProduct(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {
        
        if (!productMap.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        
        Product product = productMap.get(id);
        
        // Apply partial updates
        if (updates.containsKey("name")) {
            product.setName((String) updates.get("name"));
        }
        
        if (updates.containsKey("description")) {
            product.setDescription((String) updates.get("description"));
        }
        
        if (updates.containsKey("price")) {
            product.setPrice(((Number) updates.get("price")).doubleValue());
        }
        
        // Save the updated product
        productMap.put(id, product);
        
        return ResponseEntity.ok(product);
    }
    
    // Search products by name (demonstrates request parameters)
    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String name) {
        List<Product> results = new ArrayList<>();
        
        for (Product product : productMap.values()) {
            if (product.getName().toLowerCase().contains(name.toLowerCase())) {
                results.add(product);
            }
        }
        
        return results;
    }
}