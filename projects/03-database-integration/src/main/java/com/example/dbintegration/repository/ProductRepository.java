package com.example.dbintegration.repository;

import com.example.dbintegration.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    // Find products by category ID
    List<Product> findByCategoryId(Long categoryId);
    
    // Find products with name containing the given string (case insensitive)
    List<Product> findByNameContainingIgnoreCase(String name);
    
    // Find products by price range
    List<Product> findByPriceBetween(double minPrice, double maxPrice);
    
    // Find products with low stock (less than the given quantity)
    List<Product> findByStockQuantityLessThan(int quantity);
    
    // Custom JPQL query to find products by category name
    @Query("SELECT p FROM Product p WHERE p.category.name = :categoryName")
    List<Product> findByCategoryName(@Param("categoryName") String categoryName);
    
    // Example of pagination: Find all products with pagination
    Page<Product> findAll(Pageable pageable);
    
    // Find products by category with pagination
    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);
}