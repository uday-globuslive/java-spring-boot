# 5. Project 1: Simple REST API

This chapter is your first real Spring Boot project. You’ll build a REST API, see how controllers work, and understand the basics of HTTP endpoints.

## 5.1 Project Overview
- Expose a REST endpoint `/hello` that returns a greeting message
- Add a dynamic endpoint `/greet/{name}`

## 5.2 Step-by-Step Guide
1. **Create a Spring Boot project** (use Spring Initializr or your IDE)
2. **Add a Controller**
   ```java
   @RestController
   public class HelloController {
       @GetMapping("/hello")
       public String sayHello() {
           return "Hello, Spring Boot REST!";
       }

       @GetMapping("/greet/{name}")
       public String greet(@PathVariable String name) {
           return "Hello, " + name + "!";
       }
   }
   ```
3. **Run the application**
4. **Test the endpoints**: Open browser or use Postman: `http://localhost:8080/hello` and `http://localhost:8080/greet/YourName`

## 5.3 Concepts Covered
- REST Controller: Annotated with `@RestController`, handles HTTP requests
- Request Mapping: Use `@GetMapping`, `@PathVariable`, etc.
- Running a Spring Boot app: Run the main class with `@SpringBootApplication`

## 5.4 REST API Quick Reference
- Use `@RestController` for REST APIs
- Use `@GetMapping`, `@PostMapping`, etc. for endpoints
- Use `@PathVariable` and `@RequestParam` for dynamic data

## 5.5 Challenge
- Add a new endpoint `/bye` that returns "Goodbye!"

---
[⬅️ Back](./04-spring-boot-intro.md) | [Next ➡️](./06-spring-data-jpa.md)
