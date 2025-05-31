# 7. Project 2: CRUD App with Database

This chapter is a complete, practical project: a CRUD (Create, Read, Update, Delete) application using Spring Boot and Spring Data JPA. You’ll build a user management system with a REST API and in-memory database.

## 7.1 Project Overview
- Manage a list of users (name, email)
- Use H2 in-memory database

## 7.2 Step-by-Step Guide
1. **Create Entity**: `User` (id, name, email)
   ```java
   @Entity
   public class User {
       @Id
       @GeneratedValue
       private Long id;
       private String name;
       @Email
       private String email;
       // getters and setters
   }
   ```
2. **Create Repository**: `UserRepository extends JpaRepository<User, Long>`
   ```java
   public interface UserRepository extends JpaRepository<User, Long> {
       List<User> findByName(String name);
   }
   ```
3. **Create Controller**: REST endpoints for CRUD operations
   ```java
   @RestController
   @RequestMapping("/users")
   public class UserController {
       @Autowired
       private UserRepository userRepository;

       @GetMapping
       public List<User> getAllUsers() {
           return userRepository.findAll();
       }

       @PostMapping
       public User createUser(@RequestBody User user) {
           return userRepository.save(user);
       }

       @GetMapping("/{id}")
       public User getUser(@PathVariable Long id) {
           return userRepository.findById(id).orElse(null);
       }

       @PutMapping("/{id}")
       public User updateUser(@PathVariable Long id, @RequestBody User userDetails) {
           User user = userRepository.findById(id).orElse(null);
           if (user != null) {
               user.setName(userDetails.getName());
               user.setEmail(userDetails.getEmail());
               return userRepository.save(user);
           }
           return null;
       }

       @DeleteMapping("/{id}")
       public void deleteUser(@PathVariable Long id) {
           userRepository.deleteById(id);
       }

       @GetMapping("/search")
       public List<User> searchByName(@RequestParam String name) {
           return userRepository.findByName(name);
       }
   }
   ```
4. **Test with Postman or browser**

## 7.3 Concepts Covered
- Entity, Repository, Service, Controller layers
- CRUD operations
- Using H2 database
- Validation with `@Email`
- Search by name endpoint

## 7.4 Challenge
- Add a field for `age` and implement search by age
- Add error handling for not found users

## 7.5 CRUD App Quick Reference
- All code and setup for this project is included above. No external links required.

---
[⬅️ Back](./06-spring-data-jpa.md) | [Next ➡️](./08-spring-mvc.md)
