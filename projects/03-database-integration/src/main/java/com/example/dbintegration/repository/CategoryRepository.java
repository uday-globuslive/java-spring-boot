package com.example.dbintegration.repository;

import com.example.dbintegration.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    // Spring Data JPA automatically implements this method based on its name
    Optional<Category> findByNameIgnoreCase(String name);
    
    // Check if a category exists by name (case insensitive)
    boolean existsByNameIgnoreCase(String name);
}