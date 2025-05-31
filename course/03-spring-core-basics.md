# 3. Spring Core Basics

This chapter is your complete introduction to the Spring Framework’s core concepts. You’ll learn what makes Spring powerful, how to use it in real projects, and every code example is explained for beginners.

---

## 3.1 What is Spring?
Spring is a Java framework for building robust, maintainable applications. It helps you:
- Manage your objects (beans) automatically
- Inject dependencies (other objects) into your classes
- Write modular, testable code

### Why Use Spring?
- Reduces boilerplate code
- Makes testing and maintenance easier
- Used by most modern Java projects

---

## 3.2 Key Concepts
### Inversion of Control (IoC)
Spring manages the creation and lifecycle of your objects (beans), not you. This is called "Inversion of Control" because you give up control to the framework.

### Dependency Injection (DI)
Spring injects dependencies into your classes, making code more modular and testable. Instead of creating objects yourself, you ask Spring to provide them.

### Beans & ApplicationContext
- **Bean**: An object managed by Spring
- **ApplicationContext**: The container that manages beans

---

## 3.3 Example: Simple Spring Bean (Explained)
```java
@Component // Tells Spring to manage this class as a bean
public class HelloService {
    public String sayHello() {
        return "Hello from Spring!";
    }
}
```
- `@Component` tells Spring to create and manage an instance of `HelloService`.

---

## 3.4 Mini-Project: Hello Spring
1. **Create a Maven project and add Spring Context dependency**
2. **Define a bean** (see above)
3. **Create a main class to load the context and use the bean:
```java
public class Main {
    public static void main(String[] args) {
        // Loads the Spring context (container)
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        // Gets the HelloService bean from the context
        HelloService hello = ctx.getBean(HelloService.class);
        System.out.println(hello.sayHello());
    }
}
```
- `ApplicationContext` is the Spring container. It creates and manages beans.
- `getBean()` fetches your bean from the container.

---

## 3.5 Spring Core Quick Reference
### Creating a Bean
```java
@Component
public class MyBean {}
```

### Injecting a Bean
```java
@Autowired // Tells Spring to inject the bean here
private MyBean myBean;
```

### ApplicationContext Example
```java
ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
MyBean bean = context.getBean(MyBean.class);
```

---

## 3.6 Challenge
- Create a bean for a `CalculatorService` with add/subtract methods
- Inject and use it in your main class
- Try changing the bean to return a different greeting

---
[⬅️ Back](./02-java-refresher.md) | [Next ➡️](./04-spring-boot-intro.md)
