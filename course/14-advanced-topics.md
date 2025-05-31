# 14. Advanced Topics

This chapter covers advanced Spring Boot features to take your skills to the next level. Each topic includes code samples and practical advice.

## 14.1 Spring Boot Actuator
- Add monitoring and management endpoints
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```
- Enable endpoints in `application.properties`:
```properties
management.endpoints.web.exposure.include=*
```

## 14.2 Custom Exception Handling
```java
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAll(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
}
```

## 14.3 Asynchronous Programming
```java
@Async
public void doAsyncTask() {
    // ...
}
```

## 14.4 Scheduling Tasks
```java
@EnableScheduling
@SpringBootApplication
public class App {}

@Scheduled(fixedRate = 5000)
public void scheduledTask() {
    System.out.println("Task runs every 5 seconds");
}
```

## 14.5 Spring Events
```java
@Component
public class MyListener {
    @EventListener
    public void handleEvent(SomeEvent event) {
        // ...
    }
}
```

## 14.6 External Configuration
- Use `application.properties` or `application.yml` for config
- Use `@Value` or `@ConfigurationProperties` to inject values

## 14.7 Profiles & Environments
- Use `@Profile` for environment-specific beans
- Set active profile in `application.properties`:
```properties
spring.profiles.active=dev
```

## 14.8 REST API Versioning
- Use URI versioning: `/api/v1/resource`
- Or header versioning

## 14.9 File Upload/Download
```java
@PostMapping("/upload")
public String upload(@RequestParam MultipartFile file) { /* ... */ }
```

## 14.10 Consuming External APIs
```java
@Autowired
private RestTemplate restTemplate;

String result = restTemplate.getForObject("https://api.example.com/data", String.class);
```

## 14.11 Challenge
- Add actuator endpoints to your app
- Implement global exception handling
- Add a scheduled task

---
[⬅️ Back](./13-project5-tdd.md) | [Next ➡️](./15-project6-enterprise-app.md)
