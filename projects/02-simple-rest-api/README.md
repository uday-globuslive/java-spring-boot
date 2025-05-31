# Project 2: Simple REST API

In this project, we'll build on our knowledge from Project 1 to create a complete REST API with multiple endpoints supporting different HTTP methods (GET, POST, PUT, DELETE). We'll implement a simple product management API that demonstrates core REST principles.

## Learning Objectives

- Understand REST API design principles
- Create controllers with different HTTP method handlers
- Work with path variables and request parameters
- Handle request bodies and response entities
- Implement proper HTTP status codes
- Document REST APIs

## What is a REST API?

REST (Representational State Transfer) is an architectural style for designing networked applications. RESTful APIs use HTTP requests to perform CRUD (Create, Read, Update, Delete) operations on resources, which are represented as URLs.

**Key REST Principles:**

- **Stateless** - Server doesn't store client state between requests
- **Client-Server** - Separation of concerns between client and server
- **Cacheable** - Responses must define themselves as cacheable or non-cacheable
- **Uniform Interface** - Resources are identified in requests and manipulated through representations
- **Layered System** - Client cannot tell if it's connected directly to the end server

## Project Steps

### Step 1: Setting Up the Project

Let's create a Spring Boot application for our REST API. We'll continue using Maven.

1. **Project Structure**

   Create the following directory structure:

   ```
   02-simple-rest-api/
   ├── src/
   │   ├── main/
   │   │   ├── java/
   │   │   │   └── com/
   │   │   │       └── example/
   │   │   │           └── simplerestapi/
   │   │   │               ├── SimpleRestApiApplication.java
   │   │   │               ├── controller/
   │   │   │               │   └── ProductController.java
   │   │   │               └── model/
   │   │   │                   └── Product.java
   │   │   └── resources/
   │   │       └── application.properties
   │   └── test/
   │       └── java/
   │           └── com/
   │               └── example/
   │                   └── simplerestapi/
   │                       ├── SimpleRestApiApplicationTests.java
   │                       └── controller/
   │                           └── ProductControllerTests.java
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
       <artifactId>simple-rest-api</artifactId>
       <version>0.0.1-SNAPSHOT</version>
       <name>simple-rest-api</name>
       <description>Simple REST API Project</description>
       
       <properties>
           <java.version>11</java.version>
       </properties>
       
       <dependencies>
           <!-- Spring Boot Web Starter -->
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-web</artifactId>
           </dependency>
           
           <!-- Spring Boot DevTools -->
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-devtools</artifactId>
               <scope>runtime</scope>
               <optional>true</optional>
           </dependency>
           
           <!-- Spring Boot Validation -->
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-validation</artifactId>
           </dependency>
           
           <!-- Lombok to reduce boilerplate code -->
           <dependency>
               <groupId>org.projectlombok</groupId>
               <artifactId>lombok</artifactId>
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
   - `spring-boot-starter-validation`: For validating input data
   - `lombok`: For reducing boilerplate code (getters, setters, constructors)

### Step 2: Creating the Application Class

```java
package com.example.simplerestapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SimpleRestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleRestApiApplication.class, args);
    }
}
```

### Step 3: Creating the Product Model

We'll create a simple Product class to represent the resource we'll be managing:

```java
package com.example.simplerestapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    
    private Long id;
    
    @NotBlank(message = "Product name is required")
    private String name;
    
    private String description;
    
    @Positive(message = "Price must be positive")
    private double price;
    
    // Equals and hashCode methods are automatically generated by Lombok's @Data
}
```

**Key Points:**
- `@Data`: Lombok annotation that generates getters, setters, equals, hashCode, and toString methods
- `@NotBlank`, `@Positive`: Validation annotations to ensure input data meets our requirements
- `@NoArgsConstructor`, `@AllArgsConstructor`: Lombok annotations to generate constructors

### Step 4: Creating the Product Controller

Now let's create a REST controller with endpoints for CRUD operations:

```java
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
```

**Key Points:**

1. **@RestController**: Indicates this class handles REST requests

2. **@RequestMapping("/api/products")**: Base URL path for all endpoints in this controller

3. **HTTP Method Annotations**:
   - `@GetMapping`: HTTP GET - Retrieving resources
   - `@PostMapping`: HTTP POST - Creating resources
   - `@PutMapping`: HTTP PUT - Updating resources (full replacement)
   - `@DeleteMapping`: HTTP DELETE - Removing resources
   - `@PatchMapping`: HTTP PATCH - Partial update of resources

4. **Path Variables**:
   - `@PathVariable` extracts values from the URL path

5. **Request Parameters**:
   - `@RequestParam` extracts query parameters from the URL

6. **Request Body**:
   - `@RequestBody` maps the HTTP request body to a Java object
   - `@Valid` triggers validation of the request body

7. **Response Entity**:
   - `ResponseEntity` allows complete control over the HTTP response
   - We use it to send different status codes based on the operation result

8. **Status Codes**:
   - 200 OK: Successful GET, PUT, PATCH
   - 201 Created: Successful POST
   - 204 No Content: Successful DELETE
   - 404 Not Found: Resource not found

### Step 5: Configuration

Let's update the application.properties file:

```properties
# Server configuration
server.port=8080
spring.application.name=simple-rest-api

# Jackson configuration (JSON serialization)
spring.jackson.date-format=yyyy-MM-dd HH:mm:ss
spring.jackson.time-zone=UTC

# Logging
logging.level.org.springframework.web=INFO
```

### Step 6: Writing Tests

Let's create tests for our REST controller:

```java
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
```

**Key Points:**
- `@WebMvcTest`: A Spring Boot test annotation that sets up the Spring MVC infrastructure for testing controllers
- `MockMvc`: Allows us to perform HTTP requests in tests
- `ObjectMapper`: Converts Java objects to/from JSON
- We test various scenarios like successful creation, retrieval, and "not found" cases

### Step 7: Running the Application

Run the application the same way as in Project 1:

```bash
mvn spring-boot:run
```

## RESTful API Best Practices

Here are some best practices for designing RESTful APIs:

1. **Use Proper HTTP Methods**:
   - GET: Retrieve data
   - POST: Create a new resource
   - PUT: Update (replace) a resource
   - DELETE: Remove a resource
   - PATCH: Partially update a resource

2. **Use Meaningful HTTP Status Codes**:
   - 2xx for success (200 OK, 201 Created, 204 No Content)
   - 4xx for client errors (400 Bad Request, 404 Not Found)
   - 5xx for server errors (500 Internal Server Error)

3. **Consistent Resource Naming**:
   - Use nouns, not verbs (e.g., `/products`, not `/getProducts`)
   - Use plural for collection resources
   - Use clear, readable paths (e.g., `/products/{id}/reviews`)

4. **Versioning**:
   - Include API version in the URL or header
   - Example: `/api/v1/products`

5. **Pagination for Large Collections**:
   - Use limit/offset or page/size parameters
   - Return metadata about the pagination state

6. **Filtering, Sorting, and Field Selection**:
   - Allow clients to specify what they need
   - Example: `/products?category=electronics&sort=price&fields=id,name,price`

7. **HATEOAS (Hypertext As The Engine Of Application State)**:
   - Include links to related resources
   - Helps clients navigate the API without hardcoded URLs

## HTTP Status Codes Explained

| Code | Name | Description |
|------|------|-------------|
| 200 | OK | The request was successful |
| 201 | Created | Resource was successfully created |
| 204 | No Content | The request was successful, but no content to return |
| 400 | Bad Request | The request was malformed or invalid |
| 401 | Unauthorized | Authentication is required and has failed |
| 403 | Forbidden | The server understood the request but refuses to authorize it |
| 404 | Not Found | The requested resource could not be found |
| 405 | Method Not Allowed | The method is not supported for the requested resource |
| 500 | Internal Server Error | Something went wrong on the server |

## Spring Web Annotations Explained

| Annotation | Purpose |
|------------|---------|
| @RestController | Marks the class as a REST controller |
| @RequestMapping | Maps HTTP requests to handler methods |
| @GetMapping, @PostMapping, etc. | Shorthand for @RequestMapping with specific HTTP method |
| @PathVariable | Extracts values from the URI path |
| @RequestParam | Extracts query parameters from the request |
| @RequestBody | Maps the HTTP request body to an object |
| @Valid | Triggers validation on the annotated argument |
| @ResponseStatus | Specifies the HTTP status code to return |

## Challenges to Try

1. **Add Validation**: Enhance the validation for products (e.g., min/max price, name length)
2. **Implement Sorting**: Add the ability to sort products by name, price, etc.
3. **Add Pagination**: Implement pagination for the product list endpoint
4. **Add HATEOAS**: Include links to related resources in responses using Spring HATEOAS
5. **Add Exception Handling**: Create a global exception handler for consistent error responses

## Next Steps

In this project, you've learned how to:
- Create a RESTful API with Spring Boot
- Implement CRUD operations with proper HTTP methods
- Use path variables, request parameters, and request bodies
- Return appropriate HTTP status codes
- Test REST controllers

In [Project 3: Database Integration](../03-database-integration/README.md), we'll connect our REST API to a database using Spring Data JPA, replacing our in-memory storage with persistent data storage.

## Additional Resources

- [Spring REST Documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-controller)
- [Spring Boot Web Starter](https://docs.spring.io/spring-boot/docs/current/reference/html/web.html)
- [RESTful API Design Best Practices](https://restfulapi.net/)