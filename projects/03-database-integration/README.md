# Project 3: Database Integration with Spring Data JPA

In this project, we'll enhance our REST API from Project 2 by integrating a database using Spring Data JPA. We'll replace our in-memory storage with a persistent database and learn how to use JPA repositories to interact with it.

## Learning Objectives

- Understand Spring Data JPA and Object-Relational Mapping (ORM)
- Configure database connections in Spring Boot
- Create JPA entities and repositories
- Implement CRUD operations using Spring Data repositories
- Handle database relationships (One-to-Many, Many-to-Many)
- Work with transactions and data validation

## What is Spring Data JPA?

Spring Data JPA is part of the larger Spring Data family and makes it easy to implement JPA (Java Persistence API) based repositories. It reduces the boilerplate code required for data access and provides powerful abstractions for working with databases.

**Key benefits of Spring Data JPA:**

- **Reduces boilerplate code** - No need to write SQL for basic CRUD operations
- **Query methods** - Generate queries from method names
- **Pagination and sorting** - Built-in support for limiting results
- **Custom queries** - Support for JPQL and native SQL when needed
- **Auditing** - Automatic tracking of created/modified timestamps

## Project Steps

### Step 1: Setting Up the Project

Let's create a Spring Boot application with JPA support. We'll use H2 as an in-memory database for simplicity, but the concepts apply to any database.

1. **Project Structure**

   Create the following directory structure:

   ```
   03-database-integration/
   ├── src/
   │   ├── main/
   │   │   ├── java/
   │   │   │   └── com/
   │   │   │       └── example/
   │   │   │           └── dbintegration/
   │   │   │               ├── DatabaseIntegrationApplication.java
   │   │   │               ├── controller/
   │   │   │               │   └── ProductController.java
   │   │   │               ├── model/
   │   │   │               │   ├── Product.java
   │   │   │               │   └── Category.java
   │   │   │               ├── repository/
   │   │   │               │   ├── ProductRepository.java
   │   │   │               │   └── CategoryRepository.java
   │   │   │               └── service/
   │   │   │                   └── ProductService.java
   │   │   └── resources/
   │   │       ├── application.properties
   │   │       └── data.sql
   │   └── test/
   │       └── java/
   │           └── com/
   │               └── example/
   │                   └── dbintegration/
   │                       ├── DatabaseIntegrationApplicationTests.java
   │                       └── repository/
   │                           └── ProductRepositoryTests.java
   └── pom.xml
   ```

2. **Maven Configuration (pom.xml)**

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <project xmlns="http://maven.apache.org/POM/4.0.0" 
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
                                https://maven.apache.org/xsd/maven-4.0.0.xsd">
       <modelVersion>4.0.0</modelVersion>
       
       <parent>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-parent</artifactId>
           <version>2.7.9</version>
           <relativePath/> <!-- lookup parent from repository -->
       </parent>
       
       <groupId>com.example</groupId>
       <artifactId>database-integration</artifactId>
       <version>0.0.1-SNAPSHOT</version>
       <name>database-integration</name>
       <description>Database Integration with Spring Data JPA</description>
       
       <properties>
           <java.version>11</java.version>
       </properties>
       
       <dependencies>
           <!-- Spring Boot Web Starter -->
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-web</artifactId>
           </dependency>
           
           <!-- Spring Data JPA -->
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-data-jpa</artifactId>
           </dependency>
           
           <!-- Validation -->
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-validation</artifactId>
           </dependency>
           
           <!-- H2 Database -->
           <dependency>
               <groupId>com.h2database</groupId>
               <artifactId>h2</artifactId>
               <scope>runtime</scope>
           </dependency>
           
           <!-- Lombok -->
           <dependency>
               <groupId>org.projectlombok</groupId>
               <artifactId>lombok</artifactId>
               <optional>true</optional>
           </dependency>
           
           <!-- Spring Boot DevTools -->
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-devtools</artifactId>
               <scope>runtime</scope>
               <optional>true</optional>
           </dependency>
           
           <!-- Spring Boot Test -->
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-test</artifactId>
               <scope>test</scope>
           </dependency>
       </dependencies>
       
       <build>
           <plugins>
               <plugin>
                   <groupId>org.springframework.boot</groupId>
                   <artifactId>spring-boot-maven-plugin</artifactId>
                   <configuration>
                       <excludes>
                           <exclude>
                               <groupId>org.projectlombok</groupId>
                               <artifactId>lombok</artifactId>
                           </exclude>
                       </excludes>
                   </configuration>
               </plugin>
           </plugins>
       </build>
   </project>
   ```

   **New dependencies:**
   - `spring-boot-starter-data-jpa`: Provides Spring Data JPA
   - `h2`: H2 in-memory database

### Step 2: Creating the Application Class

```java
package com.example.dbintegration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // Enable JPA auditing
public class DatabaseIntegrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatabaseIntegrationApplication.class, args);
    }
}
```

**Key Points:**
- `@EnableJpaAuditing`: Enables JPA auditing to automatically populate created/modified dates

### Step 3: Configure Database Connection

Let's set up the database connection in application.properties:

```properties
# H2 Database Configuration
spring.datasource.url=jdbc:h2:mem:productdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# H2 Console (for development)
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# JPA Configuration
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Enable initialization of schema using data.sql
spring.jpa.defer-datasource-initialization=true
spring.sql.init.mode=always

# Logging
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
```

**Key Configuration Properties:**
- `spring.datasource.*`: Database connection details
- `spring.h2.console.*`: Configuration for H2 web console (available at http://localhost:8080/h2-console)
- `spring.jpa.hibernate.ddl-auto`: Controls schema generation (update, create, create-drop, validate, none)
- `spring.jpa.show-sql`: Shows SQL in logs
- `spring.jpa.defer-datasource-initialization`: Ensures schema is created before data.sql is executed

### Step 4: Creating the Entity Models

First, let's create a Category entity:

```java
package com.example.dbintegration.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Category name is required")
    @Column(nullable = false, unique = true)
    private String name;
    
    private String description;
    
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private Set<Product> products = new HashSet<>();
    
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    // Helper method to add a product to this category
    public void addProduct(Product product) {
        products.add(product);
        product.setCategory(this);
    }
    
    // Helper method to remove a product from this category
    public void removeProduct(Product product) {
        products.remove(product);
        product.setCategory(null);
    }
}
```

Now, let's create the Product entity:

```java
package com.example.dbintegration.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Product name is required")
    @Column(nullable = false)
    private String name;
    
    @Column(length = 1000)
    private String description;
    
    @Positive(message = "Price must be positive")
    @Column(nullable = false)
    private double price;
    
    @Column(nullable = false)
    private int stockQuantity;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
    
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
```

**Key JPA Annotations:**

1. **@Entity**: Marks the class as a JPA entity (a table in the database)
2. **@Table**: Specifies the table name
3. **@Id**: Marks the primary key
4. **@GeneratedValue**: Defines how the primary key is generated
5. **@Column**: Customizes the column definition
6. **@ManyToOne, @OneToMany**: Defines relationships between entities
7. **@CreatedDate, @LastModifiedDate**: Automatically populates timestamps
8. **@EntityListeners**: Hooks up JPA auditing

### Step 5: Creating the Repositories

Spring Data JPA repositories provide methods for CRUD operations with minimal coding.

First, let's create the CategoryRepository:

```java
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
```

Now, let's create the ProductRepository:

```java
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
```

**Key Points:**
- `JpaRepository<T, ID>`: Provides CRUD operations for the entity of type T with ID of type ID
- Spring Data JPA implements repository methods automatically based on their names
- `@Query`: Allows for custom JPQL or native SQL queries
- `Pageable`: Enables pagination of results

### Step 6: Creating the Service Layer

The service layer encapsulates business logic and interacts with repositories:

```java
package com.example.dbintegration.service;

import com.example.dbintegration.model.Category;
import com.example.dbintegration.model.Product;
import com.example.dbintegration.repository.CategoryRepository;
import com.example.dbintegration.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ProductService {
    
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    
    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }
    
    // Get all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    // Get product by ID
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
    }
    
    // Create a new product
    @Transactional
    public Product createProduct(Product product, Long categoryId) {
        // Find the category or throw exception if not found
        Category category = null;
        if (categoryId != null) {
            category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + categoryId));
            product.setCategory(category);
        }
        
        return productRepository.save(product);
    }
    
    // Update an existing product
    @Transactional
    public Product updateProduct(Long id, Product productDetails, Long categoryId) {
        Product product = getProductById(id);
        
        // Update product fields
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setStockQuantity(productDetails.getStockQuantity());
        
        // Update category if provided
        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + categoryId));
            product.setCategory(category);
        }
        
        return productRepository.save(product);
    }
    
    // Delete a product
    @Transactional
    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }
    
    // Search products by name
    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }
    
    // Get products by category
    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }
    
    // Get products with pagination
    public Page<Product> getProductsWithPagination(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
    
    // Get products by price range
    public List<Product> getProductsByPriceRange(double minPrice, double maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }
    
    // Get products with low stock
    public List<Product> getProductsWithLowStock(int threshold) {
        return productRepository.findByStockQuantityLessThan(threshold);
    }
}
```

**Key Points:**
- `@Service`: Marks the class as a Spring service
- `@Transactional`: Ensures that operations happen in a transaction
- The service delegates data access to repositories
- Business logic can be added to service methods

### Step 7: Creating the REST Controller

Now, let's create a controller that uses our service:

```java
package com.example.dbintegration.controller;

import com.example.dbintegration.model.Product;
import com.example.dbintegration.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    
    private final ProductService productService;
    
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    
    // Get all products
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
    
    // Get a product by ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(product);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Create a new product
    @PostMapping
    public ResponseEntity<Product> createProduct(
            @Valid @RequestBody Product product,
            @RequestParam(required = false) Long categoryId) {
        
        Product newProduct = productService.createProduct(product, categoryId);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }
    
    // Update a product
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody Product product,
            @RequestParam(required = false) Long categoryId) {
        
        try {
            Product updatedProduct = productService.updateProduct(id, product, categoryId);
            return ResponseEntity.ok(updatedProduct);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Delete a product
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Search products by name
    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String name) {
        return productService.searchProductsByName(name);
    }
    
    // Get products by category
    @GetMapping("/category/{categoryId}")
    public List<Product> getProductsByCategory(@PathVariable Long categoryId) {
        return productService.getProductsByCategory(categoryId);
    }
    
    // Get products with pagination
    @GetMapping("/page")
    public Page<Product> getProductsWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? 
                Sort.Direction.DESC : Sort.Direction.ASC;
        
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        return productService.getProductsWithPagination(pageRequest);
    }
    
    // Get products by price range
    @GetMapping("/price-range")
    public List<Product> getProductsByPriceRange(
            @RequestParam double min,
            @RequestParam double max) {
        
        return productService.getProductsByPriceRange(min, max);
    }
    
    // Get products with low stock
    @GetMapping("/low-stock")
    public List<Product> getProductsWithLowStock(
            @RequestParam(defaultValue = "10") int threshold) {
        
        return productService.getProductsWithLowStock(threshold);
    }
    
    // Handle entity not found exception
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleEntityNotFoundException(EntityNotFoundException e) {
        Map<String, String> error = new HashMap<>();
        error.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
```

**Key Points:**
- `@ExceptionHandler`: Handles specific exceptions thrown by controller methods
- `PageRequest`: Creates a request for a specific page with sort settings
- The controller delegates business logic to the service

### Step 8: Adding Sample Data

Create a `data.sql` file in `src/main/resources` to pre-populate the database:

```sql
-- Insert categories
INSERT INTO categories (name, description, created_at, updated_at) VALUES
('Electronics', 'Electronic devices and gadgets', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('Clothing', 'Clothes and fashion accessories', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('Books', 'Books and literature', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('Home & Garden', 'Home decor and garden supplies', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

-- Insert products
INSERT INTO products (name, description, price, stock_quantity, category_id, created_at, updated_at) VALUES
('Smartphone', 'Latest model smartphone with advanced features', 699.99, 50, 1, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('Laptop', 'High-performance laptop for professionals', 1299.99, 25, 1, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('Headphones', 'Wireless noise-cancelling headphones', 199.99, 100, 1, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('T-shirt', 'Cotton t-shirt with logo print', 19.99, 200, 2, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('Jeans', 'Classic blue denim jeans', 49.99, 150, 2, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('Java Programming', 'Comprehensive guide to Java programming', 39.99, 75, 3, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('Spring Boot in Action', 'Learn Spring Boot development', 44.99, 60, 3, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('Coffee Table', 'Modern wooden coffee table', 249.99, 15, 4, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('Plant Pot', 'Ceramic pot for indoor plants', 18.99, 120, 4, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
```

### Step 9: Writing Tests

Let's write a test for the ProductRepository:

```java
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
```

**Key Points:**
- `@DataJpaTest`: Sets up a test environment for JPA repositories
- The tests verify that the repository methods work as expected
- The test data comes from `data.sql`

### Step 10: Running the Application

Run the application the same way as before:

```bash
mvn spring-boot:run
```

After starting, you can:
1. Access the H2 console at http://localhost:8080/h2-console (use the JDBC URL, username, and password from application.properties)
2. Test the REST API endpoints using tools like Postman or cURL

## Database Concepts Explained

### 1. Entity Relationships

JPA supports several types of entity relationships:

- **@OneToOne**: One entity is related to exactly one other entity
  ```java
  @OneToOne
  @JoinColumn(name = "profile_id")
  private UserProfile profile;
  ```

- **@OneToMany / @ManyToOne**: One entity is related to multiple instances of another entity
  ```java
  // In Category.java
  @OneToMany(mappedBy = "category")
  private Set<Product> products;
  
  // In Product.java
  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;
  ```

- **@ManyToMany**: Multiple entities are related to multiple instances of another entity
  ```java
  @ManyToMany
  @JoinTable(
      name = "product_tag",
      joinColumns = @JoinColumn(name = "product_id"),
      inverseJoinColumns = @JoinColumn(name = "tag_id")
  )
  private Set<Tag> tags;
  ```

### 2. JPA Repository Methods

Spring Data JPA repositories can have several types of methods:

- **Built-in methods**: `findById()`, `findAll()`, `save()`, `delete()`, etc.
- **Query methods**: Methods that follow a naming convention to generate queries
  ```java
  List<Product> findByNameContainingIgnoreCase(String name);
  ```
- **Custom JPQL queries**: Queries defined using the Java Persistence Query Language
  ```java
  @Query("SELECT p FROM Product p WHERE p.category.name = :categoryName")
  List<Product> findByCategoryName(@Param("categoryName") String categoryName);
  ```
- **Native SQL queries**: Raw SQL queries
  ```java
  @Query(value = "SELECT * FROM products WHERE price > ?1", nativeQuery = true)
  List<Product> findExpensiveProducts(double price);
  ```

### 3. Transactions

The `@Transactional` annotation ensures that a method executes within a transaction:

```java
@Transactional
public Product updateProduct(Long id, Product productDetails) {
    // All operations here happen in a single transaction
    // If an exception occurs, all changes are rolled back
}
```

Transaction attributes include:
- `propagation`: How to handle existing transactions
- `isolation`: The isolation level for the transaction
- `timeout`: How long the transaction can run before timing out
- `readOnly`: Whether the transaction is read-only (optimizes performance)
- `rollbackFor`/`noRollbackFor`: Exceptions that trigger/don't trigger rollback

## Database Configuration Options

Spring Boot supports many database configuration options:

### 1. Connection Pooling

```properties
# HikariCP connection pool (default in Spring Boot)
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.idle-timeout=600000
```

### 2. Different Databases

```properties
# MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/mydb
spring.datasource.username=user
spring.datasource.password=password
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/mydb
spring.datasource.username=user
spring.datasource.password=password
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

### 3. Schema Generation

```properties
# Options: none, validate, update, create, create-drop
spring.jpa.hibernate.ddl-auto=update
```

## Challenges to Try

1. **Add a Review Entity**: Create a Review entity with a many-to-one relationship to Product
2. **Implement Sorting**: Add sorting capabilities to product endpoints
3. **Add More Validation**: Enhance entity validation (e.g., length constraints, patterns)
4. **Implement Caching**: Add Spring's caching support to improve performance
5. **Create a Custom Exception Handler**: Implement a global exception handler for better error responses
6. **Implement Soft Delete**: Add a "deleted" flag to entities instead of physically removing them

## Next Steps

In this project, you've learned how to:
- Set up a Spring Boot application with database support
- Create JPA entities and repositories
- Define entity relationships
- Implement service and controller layers
- Use Spring Data JPA for data access
- Configure database properties
- Test repository functionality

In [Project 4: Form Handling & Validation](../04-form-handling/README.md), we'll build on these concepts to create a web application with form handling and validation.

## Additional Resources

- [Spring Data JPA Documentation](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [Hibernate ORM Documentation](https://hibernate.org/orm/documentation/)
- [H2 Database Documentation](https://www.h2database.com/html/main.html)