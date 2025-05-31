# Project 1: Hello Spring Boot

Welcome to your first Spring Boot project! In this project, you'll learn the basics of Spring Boot by creating a simple "Hello World" application. This will establish the foundation for all future projects.

## Learning Objectives

- Understand what Spring Boot is and its advantages
- Set up a Spring Boot project from scratch
- Learn about the basic structure of a Spring Boot application
- Create a simple web endpoint
- Run and test a Spring Boot application

## What is Spring Boot?

Spring Boot is an extension of the Spring Framework that simplifies the initial setup and development of new Spring applications. It takes an opinionated view of the Spring platform and third-party libraries, allowing you to get started with minimum configuration.

**Key benefits of Spring Boot:**

- **Reduced development time** - Auto-configuration and starter dependencies
- **Standalone** - Run applications with embedded servers (no WAR files needed)
- **Production-ready** - Metrics, health checks, and externalized configuration
- **No XML configuration** - Java-based configuration or properties files

## Project Steps

### Step 1: Setting Up the Project

Let's create a simple Spring Boot application. We'll use Maven as our build tool.

1. **Project Structure**

   Create the following directory structure:

   ```
   01-hello-spring-boot/
   ├── src/
   │   ├── main/
   │   │   ├── java/
   │   │   │   └── com/
   │   │   │       └── example/
   │   │   │           └── hellospringboot/
   │   │   │               ├── HelloSpringBootApplication.java
   │   │   │               └── controller/
   │   │   │                   └── HelloController.java
   │   │   └── resources/
   │   │       └── application.properties
   │   └── test/
   │       └── java/
   │           └── com/
   │               └── example/
   │                   └── hellospringboot/
   │                       └── HelloSpringBootApplicationTests.java
   └── pom.xml
   ```

2. **Maven Configuration (pom.xml)**

   The `pom.xml` file is essential for Maven projects. It contains project information and configurations:

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
       <artifactId>hello-spring-boot</artifactId>
       <version>0.0.1-SNAPSHOT</version>
       <name>hello-spring-boot</name>
       <description>First Spring Boot Project</description>
       
       <properties>
           <java.version>11</java.version>
       </properties>
       
       <dependencies>
           <!-- Spring Boot Web Starter -->
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-web</artifactId>
           </dependency>
           
           <!-- Spring Boot DevTools for development convenience -->
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-devtools</artifactId>
               <scope>runtime</scope>
               <optional>true</optional>
           </dependency>
           
           <!-- Spring Boot Test Starter -->
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
               </plugin>
           </plugins>
       </build>
   </project>
   ```

   Let's understand this `pom.xml`:
   - **Parent**: The `spring-boot-starter-parent` provides default configurations for Spring Boot applications
   - **Dependencies**: 
     - `spring-boot-starter-web`: Includes everything needed for building web applications
     - `spring-boot-devtools`: Provides development-time features like automatic restart
     - `spring-boot-starter-test`: Includes testing libraries
   - **Build Plugins**: The Spring Boot Maven plugin packages the application as an executable JAR

### Step 2: Creating the Application Class

The main application class serves as the entry point for a Spring Boot application:

```java
package com.example.hellospringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloSpringBootApplication.class, args);
    }
}
```

**Key Points:**
- `@SpringBootApplication`: This annotation combines several annotations:
  - `@Configuration`: Tags the class as a source of bean definitions
  - `@EnableAutoConfiguration`: Tells Spring Boot to configure beans based on classpath
  - `@ComponentScan`: Tells Spring to look for components in the current package and below

- `SpringApplication.run()`: Bootstraps the application, creating the Spring application context

### Step 3: Creating a Controller

Controllers handle HTTP requests in Spring applications. Let's create a simple controller:

```java
package com.example.hellospringboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String hello() {
        return "Hello, Spring Boot!";
    }
    
    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to Spring Boot learning path!";
    }
}
```

**Key Points:**
- `@RestController`: Combines `@Controller` and `@ResponseBody`, indicating this class handles requests and responses are the return values of methods
- `@GetMapping`: Maps HTTP GET requests to specific handler methods
  - `/` maps to the root URL (e.g., http://localhost:8080/)
  - `/welcome` maps to http://localhost:8080/welcome

### Step 4: Configuration Properties

Spring Boot uses `application.properties` for configuration. For now, we'll keep it simple:

```properties
# Server port (default is 8080)
server.port=8080

# Application name
spring.application.name=hello-spring-boot

# Logging level
logging.level.root=INFO
```

### Step 5: Writing a Test

Testing is a crucial part of Spring development. Let's create a simple test:

```java
package com.example.hellospringboot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HelloSpringBootApplicationTests {

    @LocalServerPort
    private int port;
    
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
        // Basic test to verify context loads
    }
    
    @Test
    void helloEndpointReturnsExpectedMessage() {
        // Test that our endpoint returns the expected message
        String response = restTemplate.getForObject("http://localhost:" + port + "/", String.class);
        assertThat(response).isEqualTo("Hello, Spring Boot!");
    }
}
```

**Key Points:**
- `@SpringBootTest`: Loads the full application context for testing
- `TestRestTemplate`: A convenient way to make HTTP requests in tests
- `@LocalServerPort`: Injects the randomly selected port for testing

### Step 6: Running the Application

There are multiple ways to run a Spring Boot application:

1. **Using Maven**:
   ```bash
   cd 01-hello-spring-boot
   mvn spring-boot:run
   ```

2. **Using the Executable JAR**:
   ```bash
   mvn package
   java -jar target/hello-spring-boot-0.0.1-SNAPSHOT.jar
   ```

3. **From an IDE** like IntelliJ IDEA or Eclipse:
   - Import the Maven project
   - Run the main class `HelloSpringBootApplication`

Once running, you can access:
- http://localhost:8080/ → "Hello, Spring Boot!"
- http://localhost:8080/welcome → "Welcome to Spring Boot learning path!"

## Key Spring Boot Concepts Learned

1. **Starter Dependencies**: Pre-configured dependencies that simplify the build configuration
2. **Auto-configuration**: Spring Boot automatically configures your application based on dependencies
3. **Spring MVC**: The web framework used for creating RESTful endpoints
4. **Embedded Server**: No need to deploy to external servers
5. **Externalized Configuration**: Easy configuration via properties files

## Spring Boot Starters Explained

Spring Boot Starters are dependency descriptors that simplify your build configuration. For example:

- `spring-boot-starter-web`: Everything for web development (Spring MVC, embedded Tomcat, etc.)
- `spring-boot-starter-data-jpa`: Everything for JPA database access
- `spring-boot-starter-security`: Everything for Spring Security

This modular approach lets you include only what you need.

## Challenges to Try

1. **Add a new endpoint**: Create a `/time` endpoint that returns the current date and time
2. **Add a path variable**: Modify the welcome endpoint to accept a name parameter (`/welcome/{name}`)
3. **Return JSON**: Create a new endpoint that returns a JSON object instead of a String

## Next Steps

Now that you've created your first Spring Boot application, you've learned about:
- Basic project structure
- Controllers and endpoints
- Running and testing a Spring Boot application

In [Project 2: Simple REST API](../02-simple-rest-api/README.md), we'll expand on these concepts to build a more robust REST API with various HTTP methods and request handling techniques.

## Additional Resources

- [Official Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Boot Guides](https://spring.io/guides)
- [Spring Initializr](https://start.spring.io/) - For quickly generating Spring Boot projects