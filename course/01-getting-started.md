# 1. Getting Started

Welcome to your Java Spring & Spring Boot journey! This course is designed as a complete, hands-on book. You’ll learn by building real projects, with every concept explained and demonstrated step by step. No external resources are needed—everything is here.

## What You'll Learn in This Course
- Java basics and OOP refresher
- Spring Core (IoC, DI, Beans)
- Spring Boot (auto-configuration, starters, CLI)
- REST APIs with Spring Boot
- Spring Data JPA and database integration
- Web development with Spring MVC and Thymeleaf
- Security with Spring Security
- Testing (unit, integration, MockMvc)
- Advanced topics (Actuator, async, scheduling, error handling)
- Building, running, and deploying Spring Boot apps

## Who This Course Is For
- Beginners with basic Java knowledge
- Developers new to Spring or Spring Boot
- Anyone who prefers learning by building real projects

## How to Use This Book
- Follow the chapters in order for a smooth learning curve
- Each chapter contains:
  - **Concepts**: Clear explanations
  - **Mini-Projects**: Step-by-step guides
  - **Code Examples**: Ready to use
  - **Challenges**: For deeper understanding
- All code is self-contained and explained
- Use the [Appendix](./16-appendix.md) for quick reference

## What You'll Need
- **Java JDK 17+** (OpenJDK or Oracle JDK)
- **IDE**: IntelliJ IDEA (Community), Eclipse, or VS Code with Java extensions
- **Maven** or **Gradle** (build tools)
- **Git** (for version control)
- **Postman** or similar tool for API testing (optional but recommended)

## Setup Steps
1. **Install Java JDK**
   - Download and install from your preferred JDK provider (e.g., OpenJDK, Oracle JDK)
   - Verify installation: `java -version`
2. **Install an IDE**
   - IntelliJ IDEA (Community): Free, highly recommended for Spring
   - Eclipse: Popular, open-source
   - VS Code: Lightweight, use with Java Pack Extension
3. **Install Maven**
   - Download from https://maven.apache.org/download.cgi
   - Add to PATH, verify: `mvn -version`
4. **Install Git**
   - Download from https://git-scm.com/
   - Verify: `git --version`
5. **(Optional) Install Postman**
   - Download from https://www.postman.com/downloads/

## Understanding the Tools
- **Java JDK**: The core language and runtime. You write Java code, and the JDK compiles and runs it.
- **IDE**: Your coding environment. It helps you write, run, debug, and manage your code. IntelliJ IDEA, Eclipse, and VS Code are all good choices.
- **Maven/Gradle**: These tools manage your project’s dependencies (libraries), build your code, and help you run tests and package your app.
- **Git**: Version control. It lets you track changes, back up your code, and collaborate with others.
- **Postman**: A tool to test your REST APIs by sending HTTP requests and viewing responses.

## Course Structure
- **Start with Java basics** if you need a refresher ([Java Refresher](./02-java-refresher.md)).
- **Move to Spring Core** to understand the foundation.
- **Progress to Spring Boot** for rapid application development.
- **Build REST APIs, connect to databases, create web apps, secure your apps, and test everything.**
- **Tackle advanced topics and a complex project at the end.**

## Example: Your First Java Program (Explained)
This is a simple Java program that prints "Hello, World!" to the console. Every Java program starts with a class and a `main` method.
```java
public class HelloWorld { // This defines a class named HelloWorld
    public static void main(String[] args) { // This is the entry point of the program
        System.out.println("Hello, World!"); // This prints text to the console
    }
}
```
- Save this code in a file named `HelloWorld.java`.
- Compile it with `javac HelloWorld.java` and run it with `java HelloWorld`.

## Example: Your First Spring Boot Main Class (Explained)
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

## What is a "Project" in This Course?
A project is a folder containing all the code, configuration, and resources for a working application. You’ll create new projects for each major chapter, and each project will have its own step-by-step instructions.

## How to Read and Use Code Examples
- Every code example is explained line by line or with comments.
- If you see something unfamiliar, check the explanation above or below the code.
- Try copying the code into your IDE and running it yourself—experimenting is the best way to learn!

## Next Step
Go to [2. Java Refresher](./02-java-refresher.md) if you need a quick Java basics review, or jump to [3. Spring Core Basics](./03-spring-core-basics.md) if you're comfortable with Java.

---

[⬅️ Back to Index](../README.md) | [Next ➡️](./02-java-refresher.md)
