# Project 4: Form Handling & Validation

In this project, we'll build a web application with form handling and validation using Spring Boot and Thymeleaf. We'll create a user registration and management system that demonstrates how to handle form submissions, validate user input, and provide feedback to users.

## Learning Objectives

- Create web views using Thymeleaf templates
- Handle form submissions in Spring MVC
- Implement client-side and server-side validation
- Display validation errors to users
- Work with multiple form objects
- Use Spring's form binding capabilities
- Upload and handle files

## What is Thymeleaf?

Thymeleaf is a modern server-side Java template engine that allows you to create HTML templates that can be rendered on the server. It integrates well with Spring and provides natural templating - templates that can be viewed in a browser as static prototypes but also work as dynamic views when processed by the application.

**Key benefits of Thymeleaf:**

- **Natural templates** - Valid HTML that can be viewed in browsers
- **Spring integration** - First-class integration with Spring MVC
- **Dialect extension** - Custom dialects can be created
- **Fragment inclusion** - Reuse parts of templates
- **Layout inheritance** - Templates can extend other templates

## Project Steps

### Step 1: Setting Up the Project

Let's create a Spring Boot application with Thymeleaf and validation support.

1. **Project Structure**

   Create the following directory structure:

   ```
   04-form-handling/
   ├── src/
   │   ├── main/
   │   │   ├── java/
   │   │   │   └── com/
   │   │   │       └── example/
   │   │   │           └── formhandling/
   │   │   │               ├── FormHandlingApplication.java
   │   │   │               ├── controller/
   │   │   │               │   ├── UserController.java
   │   │   │               │   └── ProfileController.java
   │   │   │               ├── model/
   │   │   │               │   ├── User.java
   │   │   │               │   └── Profile.java
   │   │   │               ├── repository/
   │   │   │               │   ├── UserRepository.java
   │   │   │               │   └── ProfileRepository.java
   │   │   │               └── service/
   │   │   │                   └── UserService.java
   │   │   └── resources/
   │   │       ├── application.properties
   │   │       ├── static/
   │   │       │   ├── css/
   │   │       │   │   └── styles.css
   │   │       │   └── js/
   │   │       │       └── validation.js
   │   │       └── templates/
   │   │           ├── fragments/
   │   │           │   ├── header.html
   │   │           │   └── footer.html
   │   │           ├── layout/
   │   │           │   └── main.html
   │   │           ├── user/
   │   │           │   ├── list.html
   │   │           │   ├── form.html
   │   │           │   └── view.html
   │   │           ├── profile/
   │   │           │   └── form.html
   │   │           ├── error/
   │   │           │   ├── 404.html
   │   │           │   └── error.html
   │   │           └── index.html
   │   └── test/
   │       └── java/
   │           └── com/
   │               └── example/
   │                   └── formhandling/
   │                       ├── FormHandlingApplicationTests.java
   │                       └── controller/
   │                           └── UserControllerTests.java
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
       <artifactId>form-handling</artifactId>
       <version>0.0.1-SNAPSHOT</version>
       <name>form-handling</name>
       <description>Form Handling and Validation with Spring Boot</description>
       
       <properties>
           <java.version>11</java.version>
       </properties>
       
       <dependencies>
           <!-- Spring Boot Web Starter -->
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-web</artifactId>
           </dependency>
           
           <!-- Thymeleaf -->
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-thymeleaf</artifactId>
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
           
           <!-- DevTools -->
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-devtools</artifactId>
               <scope>runtime</scope>
               <optional>true</optional>
           </dependency>
           
           <!-- Testing -->
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
   - `spring-boot-starter-thymeleaf`: Provides Thymeleaf template engine integration

### Step 2: Creating the Application Class

```java
package com.example.formhandling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FormHandlingApplication {

    public static void main(String[] args) {
        SpringApplication.run(FormHandlingApplication.class, args);
    }
}
```

### Step 3: Configuring the Application

Let's set up the application properties:

```properties
# Server configuration
server.port=8080
spring.application.name=form-handling

# H2 Database
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# JPA Configuration
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Thymeleaf Configuration
spring.thymeleaf.cache=false
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html
spring.thymeleaf.mode=HTML

# Multipart file upload
spring.servlet.multipart.enabled=true
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB

# Logging
logging.level.org.springframework.web=INFO
logging.level.org.hibernate=ERROR
```

### Step 4: Creating the Model Classes

First, let's create the User entity:

```java
package com.example.formhandling.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    @Column(nullable = false)
    private String firstName;
    
    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    @Column(nullable = false)
    private String lastName;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    @Column(nullable = false, unique = true)
    private String email;
    
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", 
             message = "Password must contain at least one digit, one lowercase, one uppercase letter, and one special character")
    @Column(nullable = false)
    private String password;
    
    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    @Column(nullable = false)
    private LocalDate dateOfBirth;
    
    @NotBlank(message = "Gender is required")
    @Column(nullable = false)
    private String gender;
    
    @NotNull(message = "Terms acceptance is required")
    @Column(nullable = false)
    private Boolean termsAccepted;
    
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Profile profile;
    
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
```

Now, let's create the Profile entity:

```java
package com.example.formhandling.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Profile {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Size(max = 1000, message = "Bio must be less than 1000 characters")
    @Column(length = 1000)
    private String bio;
    
    @Column
    private String phoneNumber;
    
    @Column
    private String address;
    
    @Column
    private String city;
    
    @Column
    private String country;
    
    @Column
    private String postalCode;
    
    @Column
    private String profilePictureUrl;
    
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
```

**Key Points:**
- Validation annotations like `@NotBlank`, `@Size`, `@Email`, `@Pattern` define constraints
- Each constraint provides a custom error message
- `@Pattern` for password enforces a strong password policy
- Entities are connected through a one-to-one relationship

### Step 5: Creating the Repositories

Now, let's create repositories for both entities:

```java
package com.example.formhandling.repository;

import com.example.formhandling.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByEmail(String email);
    
    boolean existsByEmail(String email);
}
```

```java
package com.example.formhandling.repository;

import com.example.formhandling.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    
    Profile findByUserId(Long userId);
}
```

### Step 6: Creating the Service Layer

Let's create a service to handle business logic:

```java
package com.example.formhandling.service;

import com.example.formhandling.model.Profile;
import com.example.formhandling.model.User;
import com.example.formhandling.repository.ProfileRepository;
import com.example.formhandling.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final Path fileStorageLocation;
    
    @Autowired
    public UserService(UserRepository userRepository, ProfileRepository profileRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        
        // Create a directory for storing profile pictures
        this.fileStorageLocation = Paths.get("uploads/profile-pictures")
                .toAbsolutePath().normalize();
        
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }
    
    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    // Get user by ID
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }
    
    // Create a new user
    @Transactional
    public User createUser(User user) {
        // Check if email already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already in use: " + user.getEmail());
        }
        
        // Create initial profile
        Profile profile = new Profile();
        profile.setUser(user);
        user.setProfile(profile);
        
        return userRepository.save(user);
    }
    
    // Update an existing user
    @Transactional
    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id);
        
        // Update basic user information
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        
        // Only update email if it has changed and doesn't conflict
        if (!user.getEmail().equals(userDetails.getEmail())) {
            if (userRepository.existsByEmail(userDetails.getEmail())) {
                throw new IllegalArgumentException("Email already in use: " + userDetails.getEmail());
            }
            user.setEmail(userDetails.getEmail());
        }
        
        user.setGender(userDetails.getGender());
        user.setDateOfBirth(userDetails.getDateOfBirth());
        
        return userRepository.save(user);
    }
    
    // Delete a user
    @Transactional
    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }
    
    // Update user profile
    @Transactional
    public Profile updateProfile(Long userId, Profile profileDetails, MultipartFile profilePicture) throws IOException {
        User user = getUserById(userId);
        Profile profile = user.getProfile();
        
        // Update profile fields
        profile.setBio(profileDetails.getBio());
        profile.setPhoneNumber(profileDetails.getPhoneNumber());
        profile.setAddress(profileDetails.getAddress());
        profile.setCity(profileDetails.getCity());
        profile.setCountry(profileDetails.getCountry());
        profile.setPostalCode(profileDetails.getPostalCode());
        
        // Process profile picture if provided
        if (profilePicture != null && !profilePicture.isEmpty()) {
            String fileName = UUID.randomUUID().toString() + "_" + profilePicture.getOriginalFilename();
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            
            Files.copy(profilePicture.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            
            // Store the file path
            profile.setProfilePictureUrl("/uploads/profile-pictures/" + fileName);
        }
        
        return profileRepository.save(profile);
    }
    
    // Get user profile
    public Profile getUserProfile(Long userId) {
        return profileRepository.findByUserId(userId);
    }
}
```

**Key Points:**
- `@Transactional` annotation ensures database operations are atomic
- The service handles business logic like checking for duplicate emails
- File handling for profile pictures

### Step 7: Creating Controllers

Let's create controllers to handle HTTP requests:

```java
package com.example.formhandling.controller;

import com.example.formhandling.model.User;
import com.example.formhandling.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;

@Controller
@RequestMapping("/users")
public class UserController {
    
    private final UserService userService;
    
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    // Show all users
    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "user/list";
    }
    
    // Show user registration form
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("today", LocalDate.now());
        return "user/form";
    }
    
    // Process user registration
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user, 
                              BindingResult result, 
                              Model model,
                              RedirectAttributes redirectAttributes) {
        
        // Check for validation errors
        if (result.hasErrors()) {
            model.addAttribute("today", LocalDate.now());
            return "user/form";
        }
        
        try {
            userService.createUser(user);
            redirectAttributes.addFlashAttribute("message", "Registration successful!");
            return "redirect:/users";
        } catch (IllegalArgumentException e) {
            result.rejectValue("email", "error.user", e.getMessage());
            model.addAttribute("today", LocalDate.now());
            return "user/form";
        }
    }
    
    // Show user edit form
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("today", LocalDate.now());
        return "user/form";
    }
    
    // Process user update
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable Long id,
                            @Valid @ModelAttribute("user") User user,
                            BindingResult result,
                            Model model,
                            RedirectAttributes redirectAttributes) {
        
        // Check for validation errors
        if (result.hasErrors()) {
            model.addAttribute("today", LocalDate.now());
            return "user/form";
        }
        
        try {
            userService.updateUser(id, user);
            redirectAttributes.addFlashAttribute("message", "User updated successfully!");
            return "redirect:/users";
        } catch (IllegalArgumentException e) {
            result.rejectValue("email", "error.user", e.getMessage());
            model.addAttribute("today", LocalDate.now());
            return "user/form";
        }
    }
    
    // Show user details
    @GetMapping("/{id}")
    public String viewUser(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "user/view";
    }
    
    // Delete user
    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        userService.deleteUser(id);
        redirectAttributes.addFlashAttribute("message", "User deleted successfully!");
        return "redirect:/users";
    }
}
```

Now, let's create a controller for profile management:

```java
package com.example.formhandling.controller;

import com.example.formhandling.model.Profile;
import com.example.formhandling.model.User;
import com.example.formhandling.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/profiles")
public class ProfileController {
    
    private final UserService userService;
    
    @Autowired
    public ProfileController(UserService userService) {
        this.userService = userService;
    }
    
    // Show profile edit form
    @GetMapping("/edit/{userId}")
    public String showProfileForm(@PathVariable Long userId, Model model) {
        User user = userService.getUserById(userId);
        Profile profile = userService.getUserProfile(userId);
        
        model.addAttribute("user", user);
        model.addAttribute("profile", profile);
        
        return "profile/form";
    }
    
    // Process profile update
    @PostMapping("/update/{userId}")
    public String updateProfile(@PathVariable Long userId,
                               @Valid @ModelAttribute("profile") Profile profile,
                               BindingResult result,
                               @RequestParam(value = "profilePicture", required = false) MultipartFile profilePicture,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        
        // Check for validation errors
        if (result.hasErrors()) {
            User user = userService.getUserById(userId);
            model.addAttribute("user", user);
            return "profile/form";
        }
        
        try {
            userService.updateProfile(userId, profile, profilePicture);
            redirectAttributes.addFlashAttribute("message", "Profile updated successfully!");
            return "redirect:/users/" + userId;
        } catch (IOException e) {
            User user = userService.getUserById(userId);
            model.addAttribute("user", user);
            model.addAttribute("error", "Error uploading profile picture: " + e.getMessage());
            return "profile/form";
        }
    }
}
```

### Step 8: Creating Thymeleaf Templates

Let's create the main layout template:

```html
<!-- src/main/resources/templates/layout/main.html -->
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" 
      xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title layout:title-pattern="$CONTENT_TITLE - $LAYOUT_TITLE">User Management</title>
    
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" 
          rel="stylesheet">
    
    <!-- Custom CSS -->
    <link th:href="@{/css/styles.css}" rel="stylesheet">
</head>
<body>
    <!-- Header -->
    <div th:replace="fragments/header :: header"></div>
    
    <!-- Main Content -->
    <div class="container mt-4 mb-4">
        <!-- Display any flash messages -->
        <div th:if="${message}" class="alert alert-success alert-dismissible fade show" role="alert">
            <span th:text="${message}"></span>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
        
        <!-- Content Section -->
        <div layout:fragment="content">
            <!-- Page content goes here -->
        </div>
    </div>
    
    <!-- Footer -->
    <div th:replace="fragments/footer :: footer"></div>
    
    <!-- Bootstrap JS Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    
    <!-- Custom JavaScript -->
    <script th:src="@{/js/validation.js}"></script>
    
    <!-- Additional scripts -->
    <th:block layout:fragment="scripts"></th:block>
</body>
</html>
```

Now, let's create the header and footer fragments:

```html
<!-- src/main/resources/templates/fragments/header.html -->
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
</head>
<body>
    <header th:fragment="header">
        <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
            <div class="container">
                <a class="navbar-brand" th:href="@{/}">User Management</a>
                
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" 
                        data-bs-target="#navbarNav" aria-controls="navbarNav" 
                        aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <a class="nav-link" th:href="@{/}">Home</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" th:href="@{/users}">Users</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" th:href="@{/users/register}">Register</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    </header>
</body>
</html>
```

```html
<!-- src/main/resources/templates/fragments/footer.html -->
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
</head>
<body>
    <footer th:fragment="footer" class="bg-light py-3 mt-5">
        <div class="container text-center">
            <p class="mb-0">&copy; 2023 User Management System. All rights reserved.</p>
            <p class="mb-0">Built with Spring Boot & Thymeleaf</p>
        </div>
    </footer>
</body>
</html>
```

Next, let's create the index page:

```html
<!-- src/main/resources/templates/index.html -->
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org"
      xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
      layout:decorate="~{layout/main}">
<head>
    <title>Home</title>
</head>
<body>
    <div layout:fragment="content">
        <div class="jumbotron">
            <h1 class="display-4">Welcome to User Management System!</h1>
            <p class="lead">
                This application demonstrates form handling and validation using Spring Boot and Thymeleaf.
            </p>
            <hr class="my-4">
            <p>
                You can register new users, view user details, edit user information, and manage profiles.
            </p>
            <a class="btn btn-primary btn-lg" th:href="@{/users}" role="button">View Users</a>
            <a class="btn btn-success btn-lg" th:href="@{/users/register}" role="button">Register New User</a>
        </div>
    </div>
</body>
</html>
```

Now, let's create the user list page:

```html
<!-- src/main/resources/templates/user/list.html -->
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org"
      xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
      layout:decorate="~{layout/main}">
<head>
    <title>Users</title>
</head>
<body>
    <div layout:fragment="content">
        <h2>User List</h2>
        
        <a th:href="@{/users/register}" class="btn btn-primary mb-3">
            <i class="bi bi-plus-circle"></i> Register New User
        </a>
        
        <div class="table-responsive">
            <table class="table table-striped table-hover">
                <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Gender</th>
                        <th>Date of Birth</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <tr th:if="${users.empty}">
                        <td colspan="6" class="text-center">No users found</td>
                    </tr>
                    <tr th:each="user : ${users}">
                        <td th:text="${user.id}"></td>
                        <td th:text="${user.firstName + ' ' + user.lastName}"></td>
                        <td th:text="${user.email}"></td>
                        <td th:text="${user.gender}"></td>
                        <td th:text="${#temporals.format(user.dateOfBirth, 'dd-MM-yyyy')}"></td>
                        <td>
                            <div class="btn-group" role="group">
                                <a th:href="@{/users/{id}(id=${user.id})}" class="btn btn-info btn-sm">
                                    <i class="bi bi-eye"></i> View
                                </a>
                                <a th:href="@{/users/edit/{id}(id=${user.id})}" class="btn btn-warning btn-sm">
                                    <i class="bi bi-pencil"></i> Edit
                                </a>
                                <a th:href="@{/profiles/edit/{userId}(userId=${user.id})}" class="btn btn-primary btn-sm">
                                    <i class="bi bi-person"></i> Profile
                                </a>
                                <form th:action="@{/users/delete/{id}(id=${user.id})}" method="post" 
                                      onsubmit="return confirm('Are you sure you want to delete this user?')">
                                    <button type="submit" class="btn btn-danger btn-sm">
                                        <i class="bi bi-trash"></i> Delete
                                    </button>
                                </form>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>
```

Next, let's create the user form:

```html
<!-- src/main/resources/templates/user/form.html -->
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org"
      xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
      layout:decorate="~{layout/main}">
<head>
    <title th:text="${user.id == null ? 'Register User' : 'Edit User'}"></title>
</head>
<body>
    <div layout:fragment="content">
        <h2 th:text="${user.id == null ? 'Register New User' : 'Edit User'}"></h2>
        
        <div class="card">
            <div class="card-body">
                <form th:action="${user.id == null ? @{/users/register} : @{/users/update/{id}(id=${user.id}))}" 
                      th:object="${user}" method="post" novalidate class="needs-validation">
                    
                    <!-- First Name -->
                    <div class="mb-3">
                        <label for="firstName" class="form-label">First Name *</label>
                        <input type="text" th:field="*{firstName}" class="form-control" 
                               th:classappend="${#fields.hasErrors('firstName')} ? 'is-invalid' : ''" 
                               id="firstName" required>
                        <div class="invalid-feedback" th:if="${#fields.hasErrors('firstName')}" 
                             th:errors="*{firstName}"></div>
                    </div>
                    
                    <!-- Last Name -->
                    <div class="mb-3">
                        <label for="lastName" class="form-label">Last Name *</label>
                        <input type="text" th:field="*{lastName}" class="form-control" 
                               th:classappend="${#fields.hasErrors('lastName')} ? 'is-invalid' : ''" 
                               id="lastName" required>
                        <div class="invalid-feedback" th:if="${#fields.hasErrors('lastName')}" 
                             th:errors="*{lastName}"></div>
                    </div>
                    
                    <!-- Email -->
                    <div class="mb-3">
                        <label for="email" class="form-label">Email *</label>
                        <input type="email" th:field="*{email}" class="form-control" 
                               th:classappend="${#fields.hasErrors('email')} ? 'is-invalid' : ''" 
                               id="email" required>
                        <div class="invalid-feedback" th:if="${#fields.hasErrors('email')}" 
                             th:errors="*{email}"></div>
                    </div>
                    
                    <!-- Password (only shown on registration) -->
                    <div class="mb-3" th:if="${user.id == null}">
                        <label for="password" class="form-label">Password *</label>
                        <input type="password" th:field="*{password}" class="form-control" 
                               th:classappend="${#fields.hasErrors('password')} ? 'is-invalid' : ''" 
                               id="password" required>
                        <div class="invalid-feedback" th:if="${#fields.hasErrors('password')}" 
                             th:errors="*{password}"></div>
                        <small class="form-text text-muted">
                            Password must be at least 8 characters long and contain at least one digit, one lowercase, 
                            one uppercase letter, and one special character.
                        </small>
                    </div>
                    
                    <!-- Date of Birth -->
                    <div class="mb-3">
                        <label for="dateOfBirth" class="form-label">Date of Birth *</label>
                        <input type="date" th:field="*{dateOfBirth}" class="form-control" 
                               th:classappend="${#fields.hasErrors('dateOfBirth')} ? 'is-invalid' : ''" 
                               id="dateOfBirth" th:max="${today}" required>
                        <div class="invalid-feedback" th:if="${#fields.hasErrors('dateOfBirth')}" 
                             th:errors="*{dateOfBirth}"></div>
                    </div>
                    
                    <!-- Gender -->
                    <div class="mb-3">
                        <label class="form-label">Gender *</label>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" th:field="*{gender}" 
                                   id="genderMale" value="Male" required>
                            <label class="form-check-label" for="genderMale">Male</label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" th:field="*{gender}" 
                                   id="genderFemale" value="Female">
                            <label class="form-check-label" for="genderFemale">Female</label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" th:field="*{gender}" 
                                   id="genderOther" value="Other">
                            <label class="form-check-label" for="genderOther">Other</label>
                        </div>
                        <div class="invalid-feedback" th:if="${#fields.hasErrors('gender')}" 
                             th:errors="*{gender}"></div>
                    </div>
                    
                    <!-- Terms and Conditions (only shown on registration) -->
                    <div class="mb-3 form-check" th:if="${user.id == null}">
                        <input type="checkbox" th:field="*{termsAccepted}" class="form-check-input" 
                               th:classappend="${#fields.hasErrors('termsAccepted')} ? 'is-invalid' : ''" 
                               id="termsAccepted" required>
                        <label class="form-check-label" for="termsAccepted">
                            I agree to the terms and conditions *
                        </label>
                        <div class="invalid-feedback" th:if="${#fields.hasErrors('termsAccepted')}" 
                             th:errors="*{termsAccepted}"></div>
                    </div>
                    
                    <!-- Submit Button -->
                    <button type="submit" class="btn btn-primary">
                        <span th:text="${user.id == null ? 'Register' : 'Update'}"></span>
                    </button>
                    
                    <a th:href="@{/users}" class="btn btn-secondary">Cancel</a>
                </form>
            </div>
        </div>
    </div>
    
    <!-- Additional scripts for client-side validation -->
    <th:block layout:fragment="scripts">
        <script>
            // Example of JavaScript form validation enhancement
            (function() {
                'use strict'
                
                // Fetch all forms we want to apply custom validation styles to
                var forms = document.querySelectorAll('.needs-validation')
                
                // Loop over them and prevent submission
                Array.prototype.slice.call(forms)
                    .forEach(function (form) {
                        form.addEventListener('submit', function (event) {
                            if (!form.checkValidity()) {
                                event.preventDefault()
                                event.stopPropagation()
                            }
                            
                            form.classList.add('was-validated')
                        }, false)
                    })
            })()
        </script>
    </th:block>
</body>
</html>
```

Now, let's create the user view page:

```html
<!-- src/main/resources/templates/user/view.html -->
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org"
      xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
      layout:decorate="~{layout/main}">
<head>
    <title>User Details</title>
</head>
<body>
    <div layout:fragment="content">
        <h2>User Details</h2>
        
        <div class="card mb-3">
            <div class="card-header bg-primary text-white">
                <h5 class="card-title mb-0">
                    <span th:text="${user.firstName + ' ' + user.lastName}"></span>
                </h5>
            </div>
            <div class="card-body">
                <div class="row">
                    <!-- Profile Picture (if available) -->
                    <div class="col-md-4 mb-3" th:if="${user.profile != null && user.profile.profilePictureUrl != null}">
                        <img th:src="${user.profile.profilePictureUrl}" class="img-thumbnail" 
                             alt="Profile Picture" style="max-height: 200px;">
                    </div>
                    
                    <!-- User Information -->
                    <div class="col-md-8">
                        <table class="table table-borderless">
                            <tbody>
                                <tr>
                                    <th>ID:</th>
                                    <td th:text="${user.id}"></td>
                                </tr>
                                <tr>
                                    <th>Email:</th>
                                    <td th:text="${user.email}"></td>
                                </tr>
                                <tr>
                                    <th>Gender:</th>
                                    <td th:text="${user.gender}"></td>
                                </tr>
                                <tr>
                                    <th>Date of Birth:</th>
                                    <td th:text="${#temporals.format(user.dateOfBirth, 'dd-MM-yyyy')}"></td>
                                </tr>
                                <tr>
                                    <th>Registered On:</th>
                                    <td th:text="${#temporals.format(user.createdAt, 'dd-MM-yyyy HH:mm')}"></td>
                                </tr>
                                <tr>
                                    <th>Last Updated:</th>
                                    <td th:text="${#temporals.format(user.updatedAt, 'dd-MM-yyyy HH:mm')}"></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                
                <!-- Profile Information (if available) -->
                <div th:if="${user.profile != null}" class="mt-4">
                    <h4>Profile Information</h4>
                    <hr>
                    
                    <div th:if="${user.profile.bio != null && !user.profile.bio.empty}" class="mb-3">
                        <h5>Bio</h5>
                        <p th:text="${user.profile.bio}"></p>
                    </div>
                    
                    <div class="row">
                        <div class="col-md-6">
                            <h5>Contact Information</h5>
                            <table class="table table-borderless">
                                <tbody>
                                    <tr th:if="${user.profile.phoneNumber != null}">
                                        <th>Phone:</th>
                                        <td th:text="${user.profile.phoneNumber}"></td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        
                        <div class="col-md-6">
                            <h5>Address</h5>
                            <address th:if="${user.profile.address != null || user.profile.city != null || 
                                            user.profile.country != null || user.profile.postalCode != null}">
                                <span th:if="${user.profile.address != null}" th:text="${user.profile.address}"></span><br th:if="${user.profile.address != null}">
                                <span th:if="${user.profile.city != null}" th:text="${user.profile.city}"></span>
                                <span th:if="${user.profile.postalCode != null}" th:text="${user.profile.postalCode}"></span><br th:if="${user.profile.city != null || user.profile.postalCode != null}">
                                <span th:if="${user.profile.country != null}" th:text="${user.profile.country}"></span>
                            </address>
                            <p th:unless="${user.profile.address != null || user.profile.city != null || 
                                           user.profile.country != null || user.profile.postalCode != null}">
                                No address information provided.
                            </p>
                        </div>
                    </div>
                </div>
            </div>
            <div class="card-footer">
                <div class="btn-group" role="group">
                    <a th:href="@{/users}" class="btn btn-secondary">
                        <i class="bi bi-arrow-left"></i> Back to List
                    </a>
                    <a th:href="@{/users/edit/{id}(id=${user.id})}" class="btn btn-warning">
                        <i class="bi bi-pencil"></i> Edit User
                    </a>
                    <a th:href="@{/profiles/edit/{userId}(userId=${user.id})}" class="btn btn-primary">
                        <i class="bi bi-person"></i> Edit Profile
                    </a>
                    <form th:action="@{/users/delete/{id}(id=${user.id})}" method="post" 
                          onsubmit="return confirm('Are you sure you want to delete this user?')">
                        <button type="submit" class="btn btn-danger">
                            <i class="bi bi-trash"></i> Delete User
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
```

Finally, let's create the profile form:

```html
<!-- src/main/resources/templates/profile/form.html -->
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org"
      xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
      layout:decorate="~{layout/main}">
<head>
    <title>Edit Profile</title>
</head>
<body>
    <div layout:fragment="content">
        <h2>Edit Profile for <span th:text="${user.firstName + ' ' + user.lastName}"></span></h2>
        
        <!-- Error message for file upload -->
        <div th:if="${error}" class="alert alert-danger alert-dismissible fade show" role="alert">
            <span th:text="${error}"></span>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
        
        <div class="card">
            <div class="card-body">
                <form th:action="@{/profiles/update/{userId}(userId=${user.id})}" 
                      th:object="${profile}" method="post" enctype="multipart/form-data" 
                      class="needs-validation" novalidate>
                    
                    <!-- Profile Picture -->
                    <div class="mb-3">
                        <label for="profilePicture" class="form-label">Profile Picture</label>
                        
                        <!-- Display current profile picture if exists -->
                        <div class="mb-2" th:if="${profile.profilePictureUrl != null}">
                            <img th:src="${profile.profilePictureUrl}" class="img-thumbnail" 
                                 alt="Current Profile Picture" style="max-height: 150px;">
                            <p class="text-muted">Current profile picture</p>
                        </div>
                        
                        <input type="file" class="form-control" id="profilePicture" 
                               name="profilePicture" accept="image/*">
                        <small class="form-text text-muted">
                            Upload a new profile picture (JPEG, PNG, or GIF). Maximum size: 10MB.
                        </small>
                    </div>
                    
                    <!-- Bio -->
                    <div class="mb-3">
                        <label for="bio" class="form-label">Bio</label>
                        <textarea th:field="*{bio}" class="form-control" 
                                  th:classappend="${#fields.hasErrors('bio')} ? 'is-invalid' : ''" 
                                  id="bio" rows="4"></textarea>
                        <div class="invalid-feedback" th:if="${#fields.hasErrors('bio')}" 
                             th:errors="*{bio}"></div>
                        <small class="form-text text-muted">
                            Tell us about yourself in a few sentences.
                        </small>
                    </div>
                    
                    <!-- Contact Information -->
                    <h4 class="mt-4">Contact Information</h4>
                    <hr>
                    
                    <!-- Phone Number -->
                    <div class="mb-3">
                        <label for="phoneNumber" class="form-label">Phone Number</label>
                        <input type="tel" th:field="*{phoneNumber}" class="form-control" id="phoneNumber">
                    </div>
                    
                    <!-- Address Information -->
                    <h4 class="mt-4">Address</h4>
                    <hr>
                    
                    <!-- Address -->
                    <div class="mb-3">
                        <label for="address" class="form-label">Street Address</label>
                        <input type="text" th:field="*{address}" class="form-control" id="address">
                    </div>
                    
                    <!-- City and Postal Code in a row -->
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="city" class="form-label">City</label>
                            <input type="text" th:field="*{city}" class="form-control" id="city">
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="postalCode" class="form-label">Postal Code</label>
                            <input type="text" th:field="*{postalCode}" class="form-control" id="postalCode">
                        </div>
                    </div>
                    
                    <!-- Country -->
                    <div class="mb-3">
                        <label for="country" class="form-label">Country</label>
                        <select th:field="*{country}" class="form-select" id="country">
                            <option value="">-- Select Country --</option>
                            <option value="United States">United States</option>
                            <option value="Canada">Canada</option>
                            <option value="United Kingdom">United Kingdom</option>
                            <option value="Australia">Australia</option>
                            <option value="India">India</option>
                            <option value="Germany">Germany</option>
                            <option value="France">France</option>
                            <option value="Japan">Japan</option>
                            <option value="China">China</option>
                            <option value="Brazil">Brazil</option>
                            <option value="Other">Other</option>
                        </select>
                    </div>
                    
                    <!-- Submit Button -->
                    <button type="submit" class="btn btn-primary">Save Profile</button>
                    <a th:href="@{/users/{id}(id=${user.id})}" class="btn btn-secondary">Cancel</a>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
```

### Step 9: Adding CSS and JavaScript

Let's create a CSS file for custom styling:

```css
/* src/main/resources/static/css/styles.css */
body {
    padding-bottom: 60px;
}

.card {
    margin-bottom: 20px;
    border-radius: 5px;
}

.card-header {
    border-radius: 5px 5px 0 0;
}

.card-footer {
    background-color: #f8f9fa;
    padding: 15px;
    border-radius: 0 0 5px 5px;
}

.form-control:focus {
    border-color: #86b7fe;
    box-shadow: 0 0 0 0.25rem rgb(13 110 253 / 25%);
}

.btn-group .btn {
    margin-right: 5px;
}

.btn-group form {
    display: inline-block;
}

.jumbotron {
    padding: 2rem;
    background-color: #e9ecef;
    border-radius: .3rem;
    margin-bottom: 2rem;
}

.invalid-feedback {
    display: block;
}

/* Custom validation styles */
.was-validated .form-control:invalid,
.form-control.is-invalid {
    border-color: #dc3545;
    padding-right: calc(1.5em + 0.75rem);
    background-image: url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' width='12' height='12' fill='none' stroke='%23dc3545' viewBox='0 0 12 12'%3e%3ccircle cx='6' cy='6' r='4.5'/%3e%3cpath stroke-linejoin='round' d='M5.8 3.6h.4L6 6.5z'/%3e%3ccircle cx='6' cy='8.2' r='.6' fill='%23dc3545' stroke='none'/%3e%3c/svg%3e");
    background-repeat: no-repeat;
    background-position: right calc(0.375em + 0.1875rem) center;
    background-size: calc(0.75em + 0.375rem) calc(0.75em + 0.375rem);
}

.was-validated .form-control:valid,
.form-control.is-valid {
    border-color: #198754;
    padding-right: calc(1.5em + 0.75rem);
    background-image: url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' width='8' height='8' viewBox='0 0 8 8'%3e%3cpath fill='%23198754' d='M2.3 6.73L.6 4.53c-.4-1.04.46-1.4 1.1-.8l1.1 1.4 3.4-3.8c.6-.63 1.6-.27 1.2.7l-4 4.6c-.43.5-.8.4-1.1.1z'/%3e%3c/svg%3e");
    background-repeat: no-repeat;
    background-position: right calc(0.375em + 0.1875rem) center;
    background-size: calc(0.75em + 0.375rem) calc(0.75em + 0.375rem);
}
```

And a JavaScript file for client-side validation:

```javascript
/* src/main/resources/static/js/validation.js */
// This file contains custom client-side validation logic

document.addEventListener('DOMContentLoaded', function() {
    // Password strength indicator
    const passwordField = document.getElementById('password');
    if (passwordField) {
        passwordField.addEventListener('input', function() {
            evaluatePasswordStrength(this.value);
        });
    }
    
    // Form validation enhancement
    const forms = document.querySelectorAll('.needs-validation');
    Array.prototype.slice.call(forms)
        .forEach(function(form) {
            form.addEventListener('submit', function(event) {
                if (!form.checkValidity()) {
                    event.preventDefault();
                    event.stopPropagation();
                }
                
                form.classList.add('was-validated');
            }, false);
        });
});

// Password strength evaluation function
function evaluatePasswordStrength(password) {
    // Initialize variables
    let strength = 0;
    let feedback = '';
    
    // If password field doesn't exist on current page, return
    const passwordField = document.getElementById('password');
    if (!passwordField) return;
    
    // Create feedback element if it doesn't exist
    let feedbackElement = document.getElementById('password-strength-feedback');
    if (!feedbackElement) {
        feedbackElement = document.createElement('div');
        feedbackElement.id = 'password-strength-feedback';
        feedbackElement.className = 'mt-2';
        passwordField.parentNode.appendChild(feedbackElement);
    }
    
    // Check password strength
    if (password.length >= 8) strength += 1;
    if (password.match(/[a-z]+/)) strength += 1;
    if (password.match(/[A-Z]+/)) strength += 1;
    if (password.match(/[0-9]+/)) strength += 1;
    if (password.match(/[^a-zA-Z0-9]+/)) strength += 1;
    
    // Determine feedback based on strength
    switch (strength) {
        case 0:
        case 1:
            feedback = '<span class="text-danger">Very weak password</span>';
            break;
        case 2:
            feedback = '<span class="text-warning">Weak password</span>';
            break;
        case 3:
            feedback = '<span class="text-info">Medium strength password</span>';
            break;
        case 4:
            feedback = '<span class="text-success">Strong password</span>';
            break;
        case 5:
            feedback = '<span class="text-success">Very strong password</span>';
            break;
    }
    
    // Update feedback element
    feedbackElement.innerHTML = feedback;
}
```

### Step 10: Running the Application

Run the application the same way as before:

```bash
mvn spring-boot:run
```

After starting, you can access the application at http://localhost:8080/

## Thymeleaf Template Engine Concepts Explained

### 1. Thymeleaf Expressions

Thymeleaf uses various expression syntaxes:

- **${...}**: Variable expressions
  ```html
  <span th:text="${user.firstName}">John</span>
  ```

- **@{...}**: URL expressions
  ```html
  <a th:href="@{/users/{id}(id=${user.id})}">View</a>
  ```

- **#{...}**: Message expressions (for internationalization)
  ```html
  <h1 th:text="#{welcome.message}">Welcome</h1>
  ```

- **~{...}**: Fragment expressions
  ```html
  <div th:replace="fragments/header :: header"></div>
  ```

### 2. Thymeleaf Attributes

Thymeleaf provides many attributes for HTML manipulation:

- **th:text**: Sets the text content of a tag
  ```html
  <span th:text="${user.name}">Default Name</span>
  ```

- **th:each**: Iterates over collections
  ```html
  <tr th:each="user : ${users}">
      <td th:text="${user.name}">Default Name</td>
  </tr>
  ```

- **th:if/th:unless**: Conditional rendering
  ```html
  <div th:if="${not #lists.isEmpty(users)}">
      Users found!
  </div>
  <div th:unless="${not #lists.isEmpty(users)}">
      No users found.
  </div>
  ```

- **th:object**: Specifies the object for a form
  ```html
  <form th:object="${user}" method="post">
      <input type="text" th:field="*{name}">
  </form>
  ```

- **th:field**: Binds a form field to an object property
  ```html
  <input type="text" th:field="*{firstName}">
  ```

### 3. Form Handling and Validation

Spring MVC and Thymeleaf work together for form handling:

1. **Model Attributes**: Objects added to the model are available in Thymeleaf templates
2. **Form Binding**: `th:object` and `th:field` bind form inputs to object properties
3. **Validation**: `@Valid` triggers validation, `BindingResult` contains errors
4. **Error Display**: `th:errors` displays validation errors

Example flow:
```java
// Controller
@PostMapping("/register")
public String registerUser(@Valid @ModelAttribute("user") User user, 
                          BindingResult result, 
                          Model model) {
    if (result.hasErrors()) {
        return "user/form"; // Return to form with errors
    }
    // Process valid form
    return "redirect:/success";
}
```

```html
<!-- Template -->
<form th:object="${user}" method="post">
    <input type="text" th:field="*{name}" 
           th:classappend="${#fields.hasErrors('name')} ? 'is-invalid' : ''">
    <div class="invalid-feedback" th:if="${#fields.hasErrors('name')}" 
         th:errors="*{name}"></div>
</form>
```

## Spring MVC Form Handling Concepts

### 1. Model-View-Controller (MVC) Pattern

Spring MVC follows the MVC pattern:
- **Model**: Data and business logic
- **View**: UI templates (Thymeleaf in our case)
- **Controller**: Handles HTTP requests and responses

### 2. Request Mapping

Controllers use annotations to map HTTP requests:

```java
@Controller
@RequestMapping("/users")
public class UserController {
    
    @GetMapping
    public String listUsers() { /* ... */ }
    
    @PostMapping("/register")
    public String registerUser() { /* ... */ }
}
```

### 3. Model Attributes

The `Model` object holds attributes accessible to views:

```java
@GetMapping("/register")
public String showRegistrationForm(Model model) {
    model.addAttribute("user", new User());
    return "user/form";
}
```

### 4. Form Submission

Form submission is handled with `@ModelAttribute`:

```java
@PostMapping("/register")
public String registerUser(@Valid @ModelAttribute("user") User user, 
                          BindingResult result) {
    if (result.hasErrors()) {
        return "user/form";
    }
    userService.createUser(user);
    return "redirect:/users";
}
```

### 5. Redirect After Post

After form submission, it's best practice to redirect:

```java
return "redirect:/users";
```

This prevents form resubmission when refreshing the page.

## Validation Concepts

### 1. Bean Validation (JSR-380)

Java Bean Validation uses annotations to define constraints:

- `@NotNull`: Value must not be null
- `@NotBlank`: String must not be null and must contain at least one non-whitespace character
- `@Size`: String, collection, map, or array size must be within bounds
- `@Min`/`@Max`: Number must be greater/less than or equal to the specified value
- `@Email`: String must be a valid email address
- `@Pattern`: String must match the specified regular expression
- `@Past`/`@Future`: Date must be in the past/future

### 2. Custom Validation

You can create custom validators for complex validation:

```java
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueEmailValidator.class)
public @interface UniqueEmail {
    String message() default "Email already in use";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return email != null && !userRepository.existsByEmail(email);
    }
}
```

### 3. Validation Groups

Validation groups allow different validation rules for different scenarios:

```java
// Define groups
public interface CreateValidation {}
public interface UpdateValidation {}

// Use groups in entity
@NotBlank(groups = {CreateValidation.class, UpdateValidation.class})
private String name;

@NotBlank(groups = CreateValidation.class)
@Null(groups = UpdateValidation.class)
private String password;

// Specify group in controller
@PostMapping("/register")
public String registerUser(@Validated(CreateValidation.class) @ModelAttribute("user") User user, 
                          BindingResult result) {
    // ...
}
```

## Challenges to Try

1. **Add User Search**: Implement a search feature for users
2. **Add Password Reset**: Create a password reset functionality
3. **Implement File Uploads**: Allow users to upload profile pictures
4. **Add Client-Side Validation**: Enhance forms with JavaScript validation
5. **Implement Pagination**: Add pagination to the user list
6. **Add User Roles**: Implement different user roles (admin, user, etc.)
7. **Create a Dashboard**: Build a user dashboard with statistics

## Next Steps

In this project, you've learned how to:
- Create web views with Thymeleaf
- Handle form submissions
- Implement validation
- Upload files
- Create a complete web application

In [Project 5: Spring Security Basics](../05-spring-security-basics/README.md), we'll enhance our application with authentication and authorization using Spring Security.

## Additional Resources

- [Thymeleaf Documentation](https://www.thymeleaf.org/documentation.html)
- [Spring MVC Documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html)
- [Bean Validation Specification](https://beanvalidation.org/2.0/spec/)
- [Spring Boot Form Handling Guide](https://spring.io/guides/gs/handling-form-submission/)