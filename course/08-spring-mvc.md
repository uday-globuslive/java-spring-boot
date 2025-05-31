# 8. Spring MVC & Web

This chapter is your complete guide to building web applications with Spring MVC and Thymeleaf. You’ll learn the MVC pattern, how to create controllers and views, and build a real web page.

## 8.1 Key Concepts
- **Model-View-Controller (MVC)**: Separates application into Model, View, and Controller
- **Controllers & Views**: Controllers handle requests, Views render output
- **Templates (Thymeleaf)**: Template engine for rendering HTML

## 8.2 Example: Controller with View
```java
@Controller
public class HomeController {
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message", "Welcome to Spring MVC!");
        return "home"; // home.html (Thymeleaf)
    }
}
```

## 8.3 Mini-Project: Web Page with Thymeleaf
1. Add Thymeleaf dependency in `pom.xml`:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```
2. Create a controller (see above)
3. Create a simple HTML page in `src/main/resources/templates/home.html`:
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Home</title>
</head>
<body>
    <h1 th:text="${message}">Welcome!</h1>
</body>
</html>
```

## 8.4 Spring MVC Quick Reference
- Use `@Controller` for web controllers
- Use `Model` to pass data to views
- Place HTML templates in `src/main/resources/templates/`

## 8.5 Challenge
- Add a form to the page to submit a new user (name, email)
- Display the list of users on the page

---
[⬅️ Back](./07-project2-crud-app.md) | [Next ➡️](./09-project3-web-app.md)
