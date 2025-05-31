# 4. Spring Boot Introduction

This chapter is your complete guide to Spring Boot, the fastest way to build modern Java applications. Every concept and code example is explained for beginners.

---

## 4.1 What is Spring Boot?
Spring Boot is a framework that makes it easy to create stand-alone, production-grade Spring applications with minimal configuration.

### Why Use Spring Boot?
- No boilerplate XML configuration
- Embedded server (Tomcat, Jetty) so you don’t need to install anything extra
- Auto-configuration based on dependencies
- Production-ready features (metrics, health checks)
- Rapid development with starter dependencies

---

## 4.2 Key Concepts
- **Starter Dependencies**: Predefined sets of dependencies for common tasks (e.g., web, data, security)
- **Auto-Configuration**: Spring Boot configures your app automatically based on what’s on the classpath
- **Spring Boot CLI**: Command-line tool to quickly run Spring Boot apps

---

## 4.3 Example: Main Class (Explained)
This is the entry point for any Spring Boot application. The `@SpringBootApplication` annotation tells Spring Boot to start everything for you.
```java
@SpringBootApplication // Marks this as a Spring Boot application
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args); // Starts the Spring Boot app
    }
}
```
- This file is usually named `DemoApplication.java` and placed in the `src/main/java/your/package/` directory.
- When you run this, Spring Boot starts an embedded web server and your app is ready to use.

---

## 4.4 Mini-Project: Your First Spring Boot App
1. **Create a new Spring Boot project** (use Spring Initializr or your IDE)
2. **Add a REST controller:**
```java
@RestController // Tells Spring this class handles HTTP requests
public class HelloController {
    @GetMapping("/hello") // Maps GET requests to /hello
    public String sayHello() {
        return "Hello, Spring Boot!";
    }
}
```
- `@RestController` combines `@Controller` and `@ResponseBody`.
- `@GetMapping` maps HTTP GET requests to the method.

3. **Run the application and visit `http://localhost:8080/hello`**

---

## 4.5 Spring Boot Quick Reference
### Creating a Spring Boot Project
- Use Spring Initializr (https://start.spring.io/) or your IDE’s wizard
- Add dependencies: Spring Web, Spring Boot DevTools, etc.

### Application Properties Example
```properties
server.port=8081 // Changes the default port
spring.application.name=demo-app // Sets the app name
```

---

## 4.6 Challenge
- Add another endpoint `/greet/{name}` that returns `Hello, {name}!`
- Try changing the port in `application.properties` and see what happens

---
[⬅️ Back](./03-spring-core-basics.md) | [Next ➡️](./05-project1-simple-rest-api.md)
