# 6. Spring Data JPA & Databases

This chapter is your complete guide to integrating databases with Spring Boot using Spring Data JPA. You’ll learn how to map Java objects to database tables, perform CRUD operations, and use an in-memory database for development.

## 6.1 Key Concepts
- **JPA (Java Persistence API)**: Standard for object-relational mapping in Java
- **Entities**: Java classes mapped to database tables
- **Repositories**: Interfaces for CRUD operations
- **CRUD Operations**: Create, Read, Update, Delete

## 6.2 Example: Entity & Repository
```java
@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String email;
    // getters and setters
}

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByName(String name);
}
```

## 6.3 Mini-Project: In-Memory Database
1. Add H2 database dependency in `pom.xml`:
```xml
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
```
2. Create an entity and repository (see above)
3. Save and fetch data using REST endpoints
4. Enable H2 console in `application.properties`:
```properties
spring.h2.console.enabled=true
spring.datasource.url=jdbc:h2:mem:testdb
```

## 6.4 Spring Data JPA Quick Reference
- Use `@Entity` for model classes
- Use `@Id` and `@GeneratedValue` for primary keys
- Extend `JpaRepository` for CRUD
- Use H2 for easy, in-memory development

## 6.5 Challenge
- Add a field for `age` to the `User` entity
- Add a method in the repository to find users by age

---
[⬅️ Back](./05-project1-simple-rest-api.md) | [Next ➡️](./07-project2-crud-app.md)
