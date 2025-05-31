# Project 5: Spring Security Basics

In this project, we'll enhance our web application by implementing authentication and authorization using Spring Security. We'll build a secure web application with user registration, login, role-based access control, and password encryption.

## Learning Objectives

- Understand Spring Security architecture and concepts
- Implement user authentication with form-based login
- Configure authorization with role-based access control
- Secure application endpoints
- Implement password encoding and storage
- Handle authentication success and failure events
- Create a user registration process
- Implement remember-me functionality
- Understand security best practices

## What is Spring Security?

Spring Security is a powerful and highly customizable authentication and authorization framework. It is the de facto standard for securing Spring-based applications. Spring Security focuses on providing both authentication and authorization to Java applications.

**Key features of Spring Security:**

- **Comprehensive security framework** - Handles authentication, authorization, and more
- **Flexible configuration** - Java or XML configuration
- **Protection against attacks** - CSRF, session fixation, clickjacking, etc.
- **Servlet API integration** - Works with standard Servlet API
- **Extensible architecture** - Customize to meet specific requirements
- **OAuth2 and OpenID Connect support** - Modern authentication protocols

## Project Steps

### Step 1: Setting Up the Project

Let's create a Spring Boot application with Spring Security support.

1. **Project Structure**

   Create the following directory structure:

   ```
   05-spring-security-basics/
   ├── src/
   │   ├── main/
   │   │   ├── java/
   │   │   │   └── com/
   │   │   │       └── example/
   │   │   │           └── securitydemo/
   │   │   │               ├── SecurityDemoApplication.java
   │   │   │               ├── config/
   │   │   │               │   ├── SecurityConfig.java
   │   │   │               │   └── WebConfig.java
   │   │   │               ├── controller/
   │   │   │               │   ├── HomeController.java
   │   │   │               │   ├── UserController.java
   │   │   │               │   └── AdminController.java
   │   │   │               ├── model/
   │   │   │               │   ├── User.java
   │   │   │               │   └── Role.java
   │   │   │               ├── repository/
   │   │   │               │   ├── UserRepository.java
   │   │   │               │   └── RoleRepository.java
   │   │   │               ├── service/
   │   │   │               │   ├── UserService.java
   │   │   │               │   └── UserDetailsServiceImpl.java
   │   │   │               └── security/
   │   │   │                   ├── CustomAuthenticationSuccessHandler.java
   │   │   │                   └── CustomAuthenticationFailureHandler.java
   │   │   └── resources/
   │   │       ├── application.properties
   │   │       ├── data.sql
   │   │       ├── static/
   │   │       │   ├── css/
   │   │       │   │   └── styles.css
   │   │       │   └── js/
   │   │       │       └── script.js
   │   │       └── templates/
   │   │           ├── fragments/
   │   │           │   ├── header.html
   │   │           │   └── footer.html
   │   │           ├── admin/
   │   │           │   ├── index.html
   │   │           │   └── users.html
   │   │           ├── user/
   │   │           │   └── profile.html
   │   │           ├── auth/
   │   │           │   ├── login.html
   │   │           │   └── register.html
   │   │           ├── error/
   │   │           │   ├── 403.html
   │   │           │   └── error.html
   │   │           └── index.html
   │   └── test/
   │       └── java/
   │           └── com/
   │               └── example/
   │                   └── securitydemo/
   │                       ├── SecurityDemoApplicationTests.java
   │                       └── controller/
   │                           └── HomeControllerTests.java
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
       <artifactId>security-demo</artifactId>
       <version>0.0.1-SNAPSHOT</version>
       <name>security-demo</name>
       <description>Spring Security Basics</description>
       
       <properties>
           <java.version>11</java.version>
       </properties>
       
       <dependencies>
           <!-- Spring Boot Web -->
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-web</artifactId>
           </dependency>
           
           <!-- Spring Security -->
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-security</artifactId>
           </dependency>
           
           <!-- Thymeleaf and Thymeleaf Security -->
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-thymeleaf</artifactId>
           </dependency>
           <dependency>
               <groupId>org.thymeleaf.extras</groupId>
               <artifactId>thymeleaf-extras-springsecurity5</artifactId>
           </dependency>
           
           <!-- Data JPA -->
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
           <dependency>
               <groupId>org.springframework.security</groupId>
               <artifactId>spring-security-test</artifactId>
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
   - `spring-boot-starter-security`: Provides Spring Security
   - `thymeleaf-extras-springsecurity5`: Adds security tags to Thymeleaf
   - `spring-security-test`: Testing utilities for Spring Security

### Step 2: Creating the Application Class

```java
package com.example.securitydemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SecurityDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityDemoApplication.class, args);
    }
}
```

### Step 3: Configuring the Application

Let's set up the application properties:

```properties
# Server configuration
server.port=8080
spring.application.name=security-demo

# H2 Database
spring.datasource.url=jdbc:h2:mem:securitydb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# JPA Configuration
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Initialize the database with data.sql
spring.jpa.defer-datasource-initialization=true
spring.sql.init.mode=always

# Thymeleaf Configuration
spring.thymeleaf.cache=false
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html
spring.thymeleaf.mode=HTML

# Logging
logging.level.org.springframework.security=DEBUG
logging.level.com.example.securitydemo=DEBUG
```

### Step 4: Creating the Model Classes

First, let's create the Role entity:

```java
package com.example.securitydemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String name;
    
    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();
    
    public Role(String name) {
        this.name = name;
    }
}
```

Now, let's create the User entity:

```java
package com.example.securitydemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
    
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Column(nullable = false, unique = true)
    private String username;
    
    @NotBlank(message = "Password is required")
    @Column(nullable = false)
    private String password;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String fullName;
    
    @Column(nullable = false)
    private boolean enabled = true;
    
    @Column(nullable = false)
    private boolean accountNonExpired = true;
    
    @Column(nullable = false)
    private boolean accountNonLocked = true;
    
    @Column(nullable = false)
    private boolean credentialsNonExpired = true;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
    
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    // Helper method to add a role
    public void addRole(Role role) {
        this.roles.add(role);
        role.getUsers().add(this);
    }
    
    // Helper method to remove a role
    public void removeRole(Role role) {
        this.roles.remove(role);
        role.getUsers().remove(this);
    }
}
```

**Key Points:**
- The User entity includes fields required by Spring Security
- The `enabled`, `accountNonExpired`, `accountNonLocked`, and `credentialsNonExpired` fields are used by Spring Security
- The User-Role relationship is Many-to-Many (a user can have multiple roles, and a role can belong to multiple users)

### Step 5: Creating the Repositories

First, let's create the RoleRepository:

```java
package com.example.securitydemo.repository;

import com.example.securitydemo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    
    Optional<Role> findByName(String name);
}
```

Now, let's create the UserRepository:

```java
package com.example.securitydemo.repository;

import com.example.securitydemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUsername(String username);
    
    Optional<User> findByEmail(String email);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
}
```

### Step 6: Creating the UserDetailsService Implementation

Spring Security uses the `UserDetailsService` interface to load user-specific data during authentication. Let's implement it:

```java
package com.example.securitydemo.service;

import com.example.securitydemo.model.Role;
import com.example.securitydemo.model.User;
import com.example.securitydemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    
    private final UserRepository userRepository;
    
    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        }
        
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                user.isAccountNonExpired(),
                user.isCredentialsNonExpired(),
                user.isAccountNonLocked(),
                authorities);
    }
}
```

**Key Points:**
- The `loadUserByUsername` method loads a user by username
- It converts our custom User entity to Spring Security's UserDetails
- Roles are converted to GrantedAuthorities with a "ROLE_" prefix
- User account status flags are passed to the UserDetails

### Step 7: Creating the User Service

Let's create a service to handle user operations:

```java
package com.example.securitydemo.service;

import com.example.securitydemo.model.Role;
import com.example.securitydemo.model.User;
import com.example.securitydemo.repository.RoleRepository;
import com.example.securitydemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    public UserService(UserRepository userRepository, 
                      RoleRepository roleRepository, 
                      PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
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
    
    // Get user by username
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    // Register a new user
    @Transactional
    public User registerUser(User user, String roleName) {
        // Check if username or email already exists
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists: " + user.getUsername());
        }
        
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + user.getEmail());
        }
        
        // Encode the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // Assign role
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new EntityNotFoundException("Role not found: " + roleName));
        
        user.addRole(role);
        
        return userRepository.save(user);
    }
    
    // Update user
    @Transactional
    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id);
        
        // Update fields
        user.setFullName(userDetails.getFullName());
        user.setEmail(userDetails.getEmail());
        
        // If a new password is provided, encode it
        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }
        
        return userRepository.save(user);
    }
    
    // Delete user
    @Transactional
    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }
    
    // Add role to user
    @Transactional
    public User addRoleToUser(Long userId, String roleName) {
        User user = getUserById(userId);
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new EntityNotFoundException("Role not found: " + roleName));
        
        user.addRole(role);
        return userRepository.save(user);
    }
    
    // Remove role from user
    @Transactional
    public User removeRoleFromUser(Long userId, String roleName) {
        User user = getUserById(userId);
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new EntityNotFoundException("Role not found: " + roleName));
        
        user.removeRole(role);
        return userRepository.save(user);
    }
    
    // Enable or disable user
    @Transactional
    public User setUserEnabled(Long userId, boolean enabled) {
        User user = getUserById(userId);
        user.setEnabled(enabled);
        return userRepository.save(user);
    }
}
```

**Key Points:**
- The service handles user registration, updates, and role management
- The `PasswordEncoder` is used to securely hash passwords
- `@Transactional` ensures database operations are atomic

### Step 8: Creating Security Configuration

Now, let's configure Spring Security:

```java
package com.example.securitydemo.config;

import com.example.securitydemo.security.CustomAuthenticationFailureHandler;
import com.example.securitydemo.security.CustomAuthenticationSuccessHandler;
import com.example.securitydemo.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    private final UserDetailsServiceImpl userDetailsService;
    private final CustomAuthenticationSuccessHandler successHandler;
    private final CustomAuthenticationFailureHandler failureHandler;
    
    @Autowired
    public SecurityConfig(UserDetailsServiceImpl userDetailsService,
                         CustomAuthenticationSuccessHandler successHandler,
                         CustomAuthenticationFailureHandler failureHandler) {
        this.userDetailsService = userDetailsService;
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            // CSRF protection is enabled by default
            
            // Define which URL paths should be secured and which should not
            .authorizeRequests()
                .antMatchers("/", "/home", "/auth/**", "/register").permitAll()
                .antMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                .antMatchers("/h2-console/**").permitAll() // For H2 console (development only)
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
            .and()
            
            // Configure form login
            .formLogin()
                .loginPage("/auth/login")
                .loginProcessingUrl("/auth/login-process")
                .successHandler(successHandler)
                .failureHandler(failureHandler)
                .permitAll()
            .and()
            
            // Configure logout
            .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"))
                .logoutSuccessUrl("/auth/login?logout")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            .and()
            
            // Configure remember-me
            .rememberMe()
                .key("uniqueAndSecretKey")
                .tokenValiditySeconds(86400) // 1 day
                .rememberMeParameter("remember-me")
            .and()
            
            // Configure error handling
            .exceptionHandling()
                .accessDeniedPage("/error/403")
            .and()
            
            // For H2 console (development only)
            .headers().frameOptions().sameOrigin();
    }
}
```

**Key Points:**
- `WebSecurityConfigurerAdapter` is the base class for security configuration
- `configure(AuthenticationManagerBuilder)` sets up the authentication mechanism
- `configure(HttpSecurity)` defines URL security, login/logout handling, and more
- `PasswordEncoder` bean is used to hash passwords
- `permitAll()` allows access to specific URLs without authentication
- Role-based access is configured with `hasRole()` and `hasAnyRole()`

### Step 9: Creating Custom Authentication Handlers

Let's create custom authentication success and failure handlers:

```java
package com.example.securitydemo.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    
    private final SimpleUrlAuthenticationSuccessHandler adminSuccessHandler =
            new SimpleUrlAuthenticationSuccessHandler("/admin");
    
    private final SimpleUrlAuthenticationSuccessHandler userSuccessHandler =
            new SimpleUrlAuthenticationSuccessHandler("/user/profile");
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, 
                                       HttpServletResponse response, 
                                       Authentication authentication) throws IOException, ServletException {
        
        // Log the successful authentication
        String username = authentication.getName();
        System.out.println("User " + username + " logged in successfully");
        
        // Set the default target URL based on user role
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        
        boolean isAdmin = authorities.stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
        
        if (isAdmin) {
            adminSuccessHandler.onAuthenticationSuccess(request, response, authentication);
        } else {
            userSuccessHandler.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
```

```java
package com.example.securitydemo.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, 
                                       HttpServletResponse response, 
                                       AuthenticationException exception) throws IOException, ServletException {
        
        // Log the authentication failure
        String username = request.getParameter("username");
        System.out.println("Failed login attempt for user: " + username);
        
        // Add error message to the session
        request.getSession().setAttribute("error", "Invalid username or password");
        
        // Redirect to login page with error parameter
        response.sendRedirect("/auth/login?error");
    }
}
```

**Key Points:**
- The success handler redirects users to different pages based on their roles
- The failure handler adds error information for display on the login page
- Both handlers log authentication events

### Step 10: Creating Controllers

Let's create controllers for different parts of the application:

```java
package com.example.securitydemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    @GetMapping({"", "/", "/home"})
    public String home() {
        return "index";
    }
    
    @GetMapping("/auth/login")
    public String login() {
        return "auth/login";
    }
    
    @GetMapping("/error/403")
    public String accessDenied() {
        return "error/403";
    }
}
```

```java
package com.example.securitydemo.controller;

import com.example.securitydemo.model.User;
import com.example.securitydemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class UserController {
    
    private final UserService userService;
    
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/user/profile")
    public String userProfile(Model model) {
        // Get the current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        User user = userService.getUserByUsername(username)
                .orElseThrow(() -> new IllegalStateException("User not found: " + username));
        
        model.addAttribute("user", user);
        return "user/profile";
    }
    
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }
    
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user,
                             BindingResult result,
                             RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            return "auth/register";
        }
        
        try {
            userService.registerUser(user, "USER");
            redirectAttributes.addFlashAttribute("message", "Registration successful! You can now log in.");
            return "redirect:/auth/login";
        } catch (IllegalArgumentException e) {
            result.rejectValue("username", "error.user", e.getMessage());
            return "auth/register";
        }
    }
}
```

```java
package com.example.securitydemo.controller;

import com.example.securitydemo.model.User;
import com.example.securitydemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {
    
    private final UserService userService;
    
    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping({"", "/"})
    public String adminHome() {
        return "admin/index";
    }
    
    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/users";
    }
    
    @GetMapping("/users/{id}")
    public String viewUser(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "admin/user-detail";
    }
    
    @PostMapping("/users/{id}/toggle-status")
    public String toggleUserStatus(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        User user = userService.getUserById(id);
        boolean newStatus = !user.isEnabled();
        
        userService.setUserEnabled(id, newStatus);
        
        String message = "User " + user.getUsername() + " is now " + (newStatus ? "enabled" : "disabled");
        redirectAttributes.addFlashAttribute("message", message);
        
        return "redirect:/admin/users";
    }
    
    @PostMapping("/users/{id}/add-role")
    public String addRoleToUser(@PathVariable Long id,
                               @RequestParam String roleName,
                               RedirectAttributes redirectAttributes) {
        
        try {
            userService.addRoleToUser(id, roleName);
            redirectAttributes.addFlashAttribute("message", "Role added successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/admin/users/" + id;
    }
    
    @PostMapping("/users/{id}/remove-role")
    public String removeRoleFromUser(@PathVariable Long id,
                                    @RequestParam String roleName,
                                    RedirectAttributes redirectAttributes) {
        
        try {
            userService.removeRoleFromUser(id, roleName);
            redirectAttributes.addFlashAttribute("message", "Role removed successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/admin/users/" + id;
    }
}
```

### Step 11: Creating Thymeleaf Templates

Let's create the necessary Thymeleaf templates:

```html
<!-- src/main/resources/templates/index.html -->
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/extras/spring-security">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home - Spring Security Demo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link th:href="@{/css/styles.css}" rel="stylesheet">
</head>
<body>
    <div th:replace="fragments/header :: header"></div>
    
    <div class="container mt-5">
        <div class="jumbotron">
            <h1 class="display-4">Welcome to Spring Security Demo!</h1>
            <p class="lead">This application demonstrates authentication and authorization using Spring Security.</p>
            <hr class="my-4">
            
            <div sec:authorize="!isAuthenticated()">
                <p>Please log in to access protected resources.</p>
                <a class="btn btn-primary btn-lg" th:href="@{/auth/login}" role="button">Login</a>
                <a class="btn btn-secondary btn-lg" th:href="@{/register}" role="button">Register</a>
            </div>
            
            <div sec:authorize="isAuthenticated()">
                <p>You are logged in as: <span sec:authentication="name"></span></p>
                <p>Roles: <span sec:authentication="principal.authorities"></span></p>
                
                <div sec:authorize="hasRole('ADMIN')">
                    <a class="btn btn-danger btn-lg" th:href="@{/admin}" role="button">Admin Dashboard</a>
                </div>
                
                <div sec:authorize="hasRole('USER')">
                    <a class="btn btn-success btn-lg" th:href="@{/user/profile}" role="button">My Profile</a>
                </div>
                
                <form th:action="@{/auth/logout}" method="post" class="d-inline">
                    <button type="submit" class="btn btn-warning btn-lg">Logout</button>
                </form>
            </div>
        </div>
    </div>
    
    <div th:replace="fragments/footer :: footer"></div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script th:src="@{/js/script.js}"></script>
</body>
</html>
```

```html
<!-- src/main/resources/templates/fragments/header.html -->
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/extras/spring-security">
<head>
    <meta charset="UTF-8">
</head>
<body>
    <header th:fragment="header">
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <div class="container">
                <a class="navbar-brand" th:href="@{/}">Spring Security Demo</a>
                
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" 
                        data-bs-target="#navbarNav" aria-controls="navbarNav" 
                        aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav me-auto">
                        <li class="nav-item">
                            <a class="nav-link" th:href="@{/}">Home</a>
                        </li>
                        
                        <li class="nav-item" sec:authorize="hasRole('ADMIN')">
                            <a class="nav-link" th:href="@{/admin}">Admin Dashboard</a>
                        </li>
                        
                        <li class="nav-item" sec:authorize="hasAnyRole('USER', 'ADMIN')">
                            <a class="nav-link" th:href="@{/user/profile}">My Profile</a>
                        </li>
                    </ul>
                    
                    <div class="d-flex">
                        <div sec:authorize="!isAuthenticated()">
                            <a class="btn btn-outline-light me-2" th:href="@{/auth/login}">Login</a>
                            <a class="btn btn-light" th:href="@{/register}">Register</a>
                        </div>
                        
                        <div sec:authorize="isAuthenticated()">
                            <span class="navbar-text me-3">
                                Welcome, <span sec:authentication="name"></span>!
                            </span>
                            <form th:action="@{/auth/logout}" method="post" class="d-inline">
                                <button type="submit" class="btn btn-outline-light">Logout</button>
                            </form>
                        </div>
                    </div>
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
    <footer th:fragment="footer" class="footer mt-auto py-3 bg-dark text-white">
        <div class="container text-center">
            <p class="mb-0">&copy; 2023 Spring Security Demo. All rights reserved.</p>
            <p class="mb-0">Built with Spring Boot &amp; Spring Security</p>
        </div>
    </footer>
</body>
</html>
```

```html
<!-- src/main/resources/templates/auth/login.html -->
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Spring Security Demo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link th:href="@{/css/styles.css}" rel="stylesheet">
</head>
<body>
    <div th:replace="fragments/header :: header"></div>
    
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header bg-primary text-white">
                        <h4 class="card-title mb-0">Login</h4>
                    </div>
                    <div class="card-body">
                        <!-- Display flash messages -->
                        <div th:if="${param.error}" class="alert alert-danger">
                            Invalid username or password.
                        </div>
                        <div th:if="${param.logout}" class="alert alert-success">
                            You have been logged out.
                        </div>
                        <div th:if="${message}" class="alert alert-success">
                            <span th:text="${message}"></span>
                        </div>
                        
                        <!-- Login form -->
                        <form th:action="@{/auth/login-process}" method="post">
                            <div class="mb-3">
                                <label for="username" class="form-label">Username</label>
                                <input type="text" id="username" name="username" class="form-control" required autofocus>
                            </div>
                            
                            <div class="mb-3">
                                <label for="password" class="form-label">Password</label>
                                <input type="password" id="password" name="password" class="form-control" required>
                            </div>
                            
                            <div class="mb-3 form-check">
                                <input type="checkbox" id="remember-me" name="remember-me" class="form-check-input">
                                <label class="form-check-label" for="remember-me">Remember me</label>
                            </div>
                            
                            <div class="d-grid gap-2">
                                <button type="submit" class="btn btn-primary">Login</button>
                            </div>
                        </form>
                        
                        <div class="mt-3 text-center">
                            <p>Don't have an account? <a th:href="@{/register}">Register here</a></p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <div th:replace="fragments/footer :: footer"></div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script th:src="@{/js/script.js}"></script>
</body>
</html>
```

```html
<!-- src/main/resources/templates/auth/register.html -->
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - Spring Security Demo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link th:href="@{/css/styles.css}" rel="stylesheet">
</head>
<body>
    <div th:replace="fragments/header :: header"></div>
    
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header bg-primary text-white">
                        <h4 class="card-title mb-0">Register New Account</h4>
                    </div>
                    <div class="card-body">
                        <form th:action="@{/register}" th:object="${user}" method="post" novalidate class="needs-validation">
                            <!-- Username -->
                            <div class="mb-3">
                                <label for="username" class="form-label">Username *</label>
                                <input type="text" th:field="*{username}" class="form-control"
                                       th:classappend="${#fields.hasErrors('username')} ? 'is-invalid' : ''"
                                       id="username" required>
                                <div class="invalid-feedback" th:if="${#fields.hasErrors('username')}"
                                     th:errors="*{username}"></div>
                            </div>
                            
                            <!-- Full Name -->
                            <div class="mb-3">
                                <label for="fullName" class="form-label">Full Name *</label>
                                <input type="text" th:field="*{fullName}" class="form-control"
                                       th:classappend="${#fields.hasErrors('fullName')} ? 'is-invalid' : ''"
                                       id="fullName" required>
                                <div class="invalid-feedback" th:if="${#fields.hasErrors('fullName')}"
                                     th:errors="*{fullName}"></div>
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
                            
                            <!-- Password -->
                            <div class="mb-3">
                                <label for="password" class="form-label">Password *</label>
                                <input type="password" th:field="*{password}" class="form-control"
                                       th:classappend="${#fields.hasErrors('password')} ? 'is-invalid' : ''"
                                       id="password" required>
                                <div class="invalid-feedback" th:if="${#fields.hasErrors('password')}"
                                     th:errors="*{password}"></div>
                                <small class="form-text text-muted">
                                    Password must be at least 8 characters long.
                                </small>
                            </div>
                            
                            <!-- Submit Button -->
                            <div class="d-grid gap-2">
                                <button type="submit" class="btn btn-primary">Register</button>
                                <a th:href="@{/auth/login}" class="btn btn-secondary">Cancel</a>
                            </div>
                        </form>
                        
                        <div class="mt-3 text-center">
                            <p>Already have an account? <a th:href="@{/auth/login}">Login here</a></p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <div th:replace="fragments/footer :: footer"></div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script th:src="@{/js/script.js}"></script>
</body>
</html>
```

```html
<!-- src/main/resources/templates/user/profile.html -->
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/extras/spring-security">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Profile - Spring Security Demo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link th:href="@{/css/styles.css}" rel="stylesheet">
</head>
<body>
    <div th:replace="fragments/header :: header"></div>
    
    <div class="container mt-5">
        <h2>My Profile</h2>
        
        <div class="card mb-4">
            <div class="card-header bg-primary text-white">
                <h5 class="card-title mb-0">User Information</h5>
            </div>
            <div class="card-body">
                <div class="row">
                    <div class="col-md-6">
                        <p><strong>Username:</strong> <span th:text="${user.username}"></span></p>
                        <p><strong>Full Name:</strong> <span th:text="${user.fullName}"></span></p>
                        <p><strong>Email:</strong> <span th:text="${user.email}"></span></p>
                    </div>
                    <div class="col-md-6">
                        <p><strong>Roles:</strong></p>
                        <ul>
                            <li th:each="role : ${user.roles}" th:text="${role.name}"></li>
                        </ul>
                        <p><strong>Account Status:</strong>
                            <span th:if="${user.enabled}" class="badge bg-success">Active</span>
                            <span th:unless="${user.enabled}" class="badge bg-danger">Disabled</span>
                        </p>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="card">
            <div class="card-header bg-primary text-white">
                <h5 class="card-title mb-0">Security Information</h5>
            </div>
            <div class="card-body">
                <p><strong>Current Authentication:</strong></p>
                <p>Authenticated as: <span sec:authentication="name"></span></p>
                <p>Authorities: <span sec:authentication="principal.authorities"></span></p>
                <hr>
                <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                    <form th:action="@{/auth/logout}" method="post">
                        <button type="submit" class="btn btn-warning">Logout</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
    
    <div th:replace="fragments/footer :: footer"></div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script th:src="@{/js/script.js}"></script>
</body>
</html>
```

```html
<!-- src/main/resources/templates/admin/index.html -->
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/extras/spring-security">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - Spring Security Demo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link th:href="@{/css/styles.css}" rel="stylesheet">
</head>
<body>
    <div th:replace="fragments/header :: header"></div>
    
    <div class="container mt-5">
        <h2>Admin Dashboard</h2>
        
        <div class="alert alert-info">
            <p>Welcome to the admin dashboard, <span sec:authentication="name"></span>!</p>
            <p>Here you can manage users and perform administrative tasks.</p>
        </div>
        
        <div class="row mt-4">
            <div class="col-md-4 mb-4">
                <div class="card h-100">
                    <div class="card-body">
                        <h5 class="card-title">User Management</h5>
                        <p class="card-text">View, edit, and manage user accounts.</p>
                        <a th:href="@{/admin/users}" class="btn btn-primary">Manage Users</a>
                    </div>
                </div>
            </div>
            
            <div class="col-md-4 mb-4">
                <div class="card h-100">
                    <div class="card-body">
                        <h5 class="card-title">Security Settings</h5>
                        <p class="card-text">Configure application security settings.</p>
                        <a th:href="@{/admin/security}" class="btn btn-secondary">Security Settings</a>
                    </div>
                </div>
            </div>
            
            <div class="col-md-4 mb-4">
                <div class="card h-100">
                    <div class="card-body">
                        <h5 class="card-title">System Information</h5>
                        <p class="card-text">View system status and information.</p>
                        <a th:href="@{/admin/system}" class="btn btn-info">System Info</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <div th:replace="fragments/footer :: footer"></div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script th:src="@{/js/script.js}"></script>
</body>
</html>
```

```html
<!-- src/main/resources/templates/admin/users.html -->
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/extras/spring-security">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Management - Spring Security Demo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link th:href="@{/css/styles.css}" rel="stylesheet">
</head>
<body>
    <div th:replace="fragments/header :: header"></div>
    
    <div class="container mt-5">
        <h2>User Management</h2>
        
        <!-- Flash messages -->
        <div th:if="${message}" class="alert alert-success alert-dismissible fade show" role="alert">
            <span th:text="${message}"></span>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
        
        <div th:if="${error}" class="alert alert-danger alert-dismissible fade show" role="alert">
            <span th:text="${error}"></span>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
        
        <!-- User Table -->
        <div class="card mt-4">
            <div class="card-header bg-primary text-white">
                <h5 class="card-title mb-0">User List</h5>
            </div>
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-striped table-hover">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Username</th>
                                <th>Full Name</th>
                                <th>Email</th>
                                <th>Roles</th>
                                <th>Status</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr th:if="${users.empty}">
                                <td colspan="7" class="text-center">No users found</td>
                            </tr>
                            <tr th:each="user : ${users}">
                                <td th:text="${user.id}"></td>
                                <td th:text="${user.username}"></td>
                                <td th:text="${user.fullName}"></td>
                                <td th:text="${user.email}"></td>
                                <td>
                                    <span th:each="role, iterStat : ${user.roles}">
                                        <span class="badge bg-secondary" th:text="${role.name}"></span>
                                        <span th:if="${!iterStat.last}">, </span>
                                    </span>
                                </td>
                                <td>
                                    <span th:if="${user.enabled}" class="badge bg-success">Active</span>
                                    <span th:unless="${user.enabled}" class="badge bg-danger">Disabled</span>
                                </td>
                                <td>
                                    <div class="btn-group btn-group-sm" role="group">
                                        <a th:href="@{/admin/users/{id}(id=${user.id})}" class="btn btn-info">View</a>
                                        <form th:action="@{/admin/users/{id}/toggle-status(id=${user.id})}" method="post" 
                                              onsubmit="return confirm('Are you sure you want to change user status?')">
                                            <button type="submit" class="btn btn-warning">
                                                <span th:if="${user.enabled}">Disable</span>
                                                <span th:unless="${user.enabled}">Enable</span>
                                            </button>
                                        </form>
                                    </div>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    
    <div th:replace="fragments/footer :: footer"></div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script th:src="@{/js/script.js}"></script>
</body>
</html>
```

```html
<!-- src/main/resources/templates/error/403.html -->
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Access Denied - Spring Security Demo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link th:href="@{/css/styles.css}" rel="stylesheet">
</head>
<body>
    <div th:replace="fragments/header :: header"></div>
    
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card border-danger">
                    <div class="card-header bg-danger text-white">
                        <h4 class="card-title mb-0">Access Denied</h4>
                    </div>
                    <div class="card-body text-center">
                        <h1 class="display-1">403</h1>
                        <p class="lead">Sorry, you do not have permission to access this page.</p>
                        <p>Please contact your administrator if you believe this is an error.</p>
                        <div class="mt-4">
                            <a th:href="@{/}" class="btn btn-primary">Go to Home</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <div th:replace="fragments/footer :: footer"></div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script th:src="@{/js/script.js}"></script>
</body>
</html>
```

### Step 12: Adding CSS and JavaScript

```css
/* src/main/resources/static/css/styles.css */
body {
    min-height: 100vh;
    display: flex;
    flex-direction: column;
}

.footer {
    margin-top: auto;
}

.jumbotron {
    padding: 2rem;
    background-color: #e9ecef;
    border-radius: .3rem;
    margin-bottom: 2rem;
}

.card {
    margin-bottom: 1rem;
}

.btn-group form {
    display: inline-block;
}
```

```javascript
/* src/main/resources/static/js/script.js */
// Enable Bootstrap tooltips
document.addEventListener('DOMContentLoaded', function() {
    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
    var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl)
    });
    
    // Bootstrap validation
    var forms = document.querySelectorAll('.needs-validation');
    Array.prototype.slice.call(forms).forEach(function (form) {
        form.addEventListener('submit', function (event) {
            if (!form.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
            }
            form.classList.add('was-validated');
        }, false);
    });
});
```

### Step 13: Setting Up Initial Data

Create a data.sql file to add initial roles and an admin user:

```sql
-- src/main/resources/data.sql

-- Insert roles
INSERT INTO roles (name) VALUES ('ADMIN');
INSERT INTO roles (name) VALUES ('USER');

-- Insert admin user (password is 'admin123' bcrypt encoded)
INSERT INTO users (username, password, email, full_name, enabled, account_non_expired, account_non_locked, credentials_non_expired, created_at, updated_at)
VALUES ('admin', '$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.', 'admin@example.com', 'System Administrator', true, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert regular user (password is 'user123' bcrypt encoded)
INSERT INTO users (username, password, email, full_name, enabled, account_non_expired, account_non_locked, credentials_non_expired, created_at, updated_at)
VALUES ('user', '$2a$10$9tWe6YsZulZcKzj3WK8eaeO6ORcy1On9K.ZG8NeUBJxVpbK2Xvwmy', 'user@example.com', 'Regular User', true, true, true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Assign roles to users
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1); -- admin has ADMIN role
INSERT INTO user_roles (user_id, role_id) VALUES (2, 2); -- user has USER role
```

### Step 14: Testing the Application

Create a simple test for the HomeController:

```java
package com.example.securitydemo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTests {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    public void testHomePageAccessible() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }
    
    @Test
    public void testLoginPageAccessible() throws Exception {
        mockMvc.perform(get("/auth/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/login"));
    }
    
    @Test
    @WithMockUser(roles = "USER")
    public void testUserProfileAccessibleByUser() throws Exception {
        mockMvc.perform(get("/user/profile"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/profile"));
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    public void testAdminDashboardAccessibleByAdmin() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/index"));
    }
    
    @Test
    @WithMockUser(roles = "USER")
    public void testAdminDashboardNotAccessibleByUser() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isForbidden());
    }
}
```

**Key Points:**
- `@WithMockUser` simulates an authenticated user with specific roles
- The tests verify that the pages are accessible based on roles

### Step 15: Running the Application

Run the application the same way as before:

```bash
mvn spring-boot:run
```

After starting, you can access the application at http://localhost:8080/

You can log in with:
- Admin user: username=admin, password=admin123
- Regular user: username=user, password=user123

## Spring Security Concepts Explained

### 1. Authentication vs. Authorization

- **Authentication**: Verifies who the user is (identity)
- **Authorization**: Determines what the user can do (permissions)

Spring Security handles both through separate mechanisms:
- Authentication providers verify credentials
- Access decision voters determine authorization

### 2. SecurityContext and SecurityContextHolder

The `SecurityContextHolder` is where Spring Security stores authentication details:

```java
// Getting the current authenticated user
Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
String username = authentication.getName();
```

### 3. UserDetails and UserDetailsService

`UserDetails` represents a user in Spring Security:
- Contains username, password, authorities, and account status
- `UserDetailsService` loads `UserDetails` by username

```java
@Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    
    return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            user.isEnabled(),
            user.isAccountNonExpired(),
            user.isCredentialsNonExpired(),
            user.isAccountNonLocked(),
            getAuthorities(user));
}
```

### 4. GrantedAuthority

`GrantedAuthority` represents a permission or role:
- Typically prefixed with "ROLE_" for roles
- Used by authorization mechanisms

```java
private Collection<? extends GrantedAuthority> getAuthorities(User user) {
    Set<GrantedAuthority> authorities = new HashSet<>();
    
    for (Role role : user.getRoles()) {
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
    }
    
    return authorities;
}
```

### 5. Password Encoding

Spring Security uses `PasswordEncoder` to hash passwords:
- BCrypt is a common choice for secure password storage
- Passwords should never be stored in plain text

```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}

// Encoding a password
String encodedPassword = passwordEncoder.encode(rawPassword);

// Verifying a password
boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);
```

### 6. Authentication Flow

1. User submits credentials
2. `AuthenticationManager` processes the authentication request
3. `AuthenticationProvider` validates credentials
4. If valid, an `Authentication` object is created and stored in the `SecurityContext`
5. The user is redirected based on authentication success or failure

### 7. Remember-Me Authentication

Remember-me functionality allows users to stay authenticated between browser sessions:
- Token-based remember-me stores a token in a cookie
- Persistent remember-me stores tokens in a database

```java
.rememberMe()
    .key("uniqueAndSecretKey")
    .tokenValiditySeconds(86400) // 1 day
    .rememberMeParameter("remember-me")
```

### 8. CSRF Protection

Cross-Site Request Forgery (CSRF) protection prevents attackers from making unauthorized requests:
- Spring Security includes CSRF protection by default
- CSRF tokens are included in forms and verified on submission

```html
<!-- Thymeleaf automatically adds CSRF token -->
<form th:action="@{/login}" method="post">
    <!-- form fields -->
</form>
```

## Challenges to Try

1. **Implement Email Verification**: Add email verification for new user registration
2. **Add Password Reset**: Create a "forgot password" flow
3. **Implement OAuth2 Login**: Allow users to log in with Google, Facebook, etc.
4. **Add Account Locking**: Lock accounts after multiple failed login attempts
5. **Implement Role Management**: Create a UI for admins to manage roles
6. **Add User Profile Management**: Allow users to update their profile information
7. **Implement Account Deletion**: Allow users to delete their accounts
8. **Add Activity Logging**: Log user actions and logins

## Next Steps

In this project, you've learned how to:
- Set up Spring Security in a Spring Boot application
- Implement authentication with a custom UserDetailsService
- Configure authorization with role-based access control
- Create login and registration flows
- Secure endpoints based on user roles
- Use Thymeleaf's security integration

In [Project 6: Complete RESTful Application](../06-complete-rest-app/README.md), we'll build a complete RESTful API with advanced features, integrating all the concepts we've learned so far.

## Additional Resources

- [Spring Security Reference](https://docs.spring.io/spring-security/reference/index.html)
- [Spring Security Architecture](https://spring.io/guides/topicals/spring-security-architecture)
- [OWASP Top Ten Security Risks](https://owasp.org/www-project-top-ten/)
- [Password Storage Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/Password_Storage_Cheat_Sheet.html)