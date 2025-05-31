package com.example.dbintegration.repository;

import com.example.dbintegration.model.Category;
import com.example.dbintegration.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProductRepositoryTests {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Test
    public void testFindByNameContainingIgnoreCase() {
        // Given the data.sql inserts, search for products containing "phone"
        List<Product> products = productRepository.findByNameContainingIgnoreCase("phone");
        
        // Then
        assertThat(products).isNotEmpty();
        assertThat(products.get(0).getName()).containsIgnoringCase("phone");
    }
    
    @Test
    public void testFindByPriceBetween() {
        // Given the data.sql inserts, search for products in price range
        double minPrice = 100.0;
        double maxPrice = 300.0;
        List<Product> products = productRepository.findByPriceBetween(minPrice, maxPrice);
        
        // Then
        assertThat(products).isNotEmpty();
        products.forEach(product -> {
            assertThat(product.getPrice()).isBetween(minPrice, maxPrice);
        });
    }
    
    @Test
    public void testFindByCategoryId() {
        // Get the first category
        Category category = categoryRepository.findAll().get(0);
        
        // Find products in that category
        List<Product> products = productRepository.findByCategoryId(category.getId());
        
        // Then
        assertThat(products).isNotEmpty();
        products.forEach(product -> {
            assertThat(product.getCategory().getId()).isEqualTo(category.getId());
        });
    }
    
    @Test
    public void testPagination() {
        // Given
        int pageSize = 3;
        PageRequest pageRequest = PageRequest.of(0, pageSize, Sort.by("price").descending());
        
        // When
        Page<Product> productPage = productRepository.findAll(pageRequest);
        
        // Then
        assertThat(productPage.getContent()).hasSize(pageSize);
        assertThat(productPage.getTotalElements()).isGreaterThanOrEqualTo(pageSize);
        
        // Verify sorting (products should be in descending price order)
        List<Product> products = productPage.getContent();
        for (int i = 0; i < products.size() - 1; i++) {
            assertThat(products.get(i).getPrice()).isGreaterThanOrEqualTo(products.get(i + 1).getPrice());
        }
    }
}