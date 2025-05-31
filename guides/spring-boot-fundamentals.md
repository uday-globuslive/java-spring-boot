# Spring Boot Fundamentals

This guide provides a comprehensive overview of Spring Boot fundamentals, focusing on how Spring Boot simplifies Spring application development through convention over configuration, auto-configuration, and production-ready features.

## Table of Contents

1. [Introduction to Spring Boot](#introduction-to-spring-boot)
2. [Spring Boot Architecture](#spring-boot-architecture)
3. [Auto-Configuration](#auto-configuration)
4. [Spring Boot Starters](#spring-boot-starters)
5. [Spring Boot Configuration](#spring-boot-configuration)
6. [Embedded Servers](#embedded-servers)
7. [Spring Boot CLI](#spring-boot-cli)
8. [Spring Boot Actuator](#spring-boot-actuator)
9. [Spring Boot DevTools](#spring-boot-devtools)
10. [Packaging and Deployment](#packaging-and-deployment)
11. [Working with Databases](#working-with-databases)
12. [Testing in Spring Boot](#testing-in-spring-boot)
13. [Profiles in Spring Boot](#profiles-in-spring-boot)
14. [Logging in Spring Boot](#logging-in-spring-boot)
15. [Error Handling](#error-handling)
16. [Spring Boot Security](#spring-boot-security)
17. [Building RESTful Services](#building-restful-services)
18. [Messaging with Spring Boot](#messaging-with-spring-boot)
19. [Spring Boot Actuator](#spring-boot-actuator)
20. [Spring Boot Admin](#spring-boot-admin)

## Introduction to Spring Boot

Spring Boot is a project built on top of the Spring Framework that simplifies the development process through convention over configuration, allowing developers to get started quickly with minimal setup.

### Key Features

- **Auto-configuration**: Automatically configures your application based on the dependencies in the classpath
- **Standalone**: Creates stand-alone applications that "just run"
- **Opinionated defaults**: Provides sensible defaults for configuration
- **Production-ready**: Includes non-functional features like metrics, health checks, and externalized configuration
- **No code generation**: Does not generate code but uses annotations and reflection
- **No XML configuration**: Uses Java-based configuration by default

### Spring Boot vs Spring Framework

Spring Boot is built on top of the Spring Framework and provides the following advantages:

| Feature | Spring Framework | Spring Boot |
|---------|-----------------|-------------|
| Configuration | Requires explicit configuration | Provides auto-configuration |
| Server | Requires external server | Includes embedded server |
| Deployment | WAR files for web applications | Executable JAR files |
| Dependencies | Manual dependency management | Starter dependencies |
| Boilerplate code | Significant | Minimal |
| Production features | Manual implementation | Built-in actuator |

## Spring Boot Architecture

Spring Boot follows a layered architecture that builds upon the Spring Framework.

### Core Components

1. **Spring Boot Autoconfigure**: Provides auto-configuration mechanisms
2. **Spring Boot Starters**: Dependency descriptors for common use cases
3. **Spring Boot CLI**: Command-line tool for quick prototyping
4. **Spring Boot Actuator**: Production-ready features

### Bootstrap Process

When a Spring Boot application starts:

1. SpringApplication is created and run
2. Spring's ApplicationContext is created
3. Auto-configuration classes are processed
4. Embedded server is started (if applicable)
5. ApplicationRunner and CommandLineRunner beans are executed

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}
```

The `@SpringBootApplication` annotation combines:
- `@Configuration`: Tags the class as a source of bean definitions
- `@EnableAutoConfiguration`: Enables Spring Boot's auto-configuration
- `@ComponentScan`: Enables component scanning in the package and sub-packages

## Auto-Configuration

Auto-configuration is a key feature of Spring Boot that automatically configures your Spring application based on the dependencies present in the classpath.

### How Auto-Configuration Works

1. Spring Boot detects the libraries in the classpath
2. Provides default configuration for those libraries
3. Applies the configuration only if needed
4. Allows you to override the defaults when necessary

### Conditional Annotations

Auto-configuration uses various conditional annotations to determine when to apply configuration:

- `@ConditionalOnClass`: Matches when a specified class is on the classpath
- `@ConditionalOnMissingClass`: Matches when a specified class is not on the classpath
- `@ConditionalOnBean`: Matches when a specified bean exists
- `@ConditionalOnMissingBean`: Matches when a specified bean doesn't exist
- `@ConditionalOnProperty`: Matches when a specified property has a specific value
- `@ConditionalOnResource`: Matches when a specified resource is available
- `@ConditionalOnWebApplication`: Matches when the application is a web application
- `@ConditionalOnNotWebApplication`: Matches when the application is not a web application

### Example Auto-Configuration Class

```java
@Configuration
@ConditionalOnClass(DataSource.class)
@ConditionalOnMissingBean(DataSource.class)
@EnableConfigurationProperties(DataSourceProperties.class)
public class DataSourceAutoConfiguration {

    @Bean
    @ConditionalOnProperty(name = "spring.datasource.type")
    public DataSource dataSource(DataSourceProperties properties) {
        // Create and configure a DataSource based on properties
        return DataSourceBuilder.create()
                .url(properties.getUrl())
                .username(properties.getUsername())
                .password(properties.getPassword())
                .build();
    }
}
```

### Disabling Auto-Configuration

You can disable specific auto-configuration classes:

```java
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}
```

Or using properties:

```properties
spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
```

### Viewing Auto-Configuration Report

You can see which auto-configurations are being applied by enabling debug logging:

```properties
debug=true
```

Or by using the Actuator endpoint:

```
GET /actuator/conditions
```

## Spring Boot Starters

Spring Boot Starters are a set of convenient dependency descriptors that you can include in your application to get the required dependencies for a specific functionality.

### Core Starters

- **spring-boot-starter**: Core starter with auto-configuration, logging, and YAML support
- **spring-boot-starter-web**: Starter for building web applications (including RESTful) using Spring MVC
- **spring-boot-starter-data-jpa**: Starter for using Spring Data JPA with Hibernate
- **spring-boot-starter-security**: Starter for using Spring Security
- **spring-boot-starter-test**: Starter for testing Spring Boot applications
- **spring-boot-starter-actuator**: Starter for using Spring Boot Actuator
- **spring-boot-starter-jdbc**: Starter for using JDBC with the HikariCP connection pool
- **spring-boot-starter-validation**: Starter for using Java Bean Validation
- **spring-boot-starter-cache**: Starter for using Spring Framework's caching support
- **spring-boot-starter-mail**: Starter for using Java Mail and Spring Framework's email sending support

### Technology-Specific Starters

- **spring-boot-starter-data-mongodb**: Starter for using MongoDB document-oriented database
- **spring-boot-starter-data-redis**: Starter for using Redis key-value data store
- **spring-boot-starter-data-elasticsearch**: Starter for using Elasticsearch search engine
- **spring-boot-starter-websocket**: Starter for building WebSocket applications
- **spring-boot-starter-batch**: Starter for using Spring Batch

### Using Starters

To use a starter, simply add it as a dependency in your build file:

Maven:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

Gradle:
```groovy
implementation 'org.springframework.boot:spring-boot-starter-web'
```

### Creating Custom Starters

You can create custom starters for your organization's common libraries:

1. Create an auto-configuration module (e.g., `acme-spring-boot-autoconfigure`)
2. Create a starter module (e.g., `acme-spring-boot-starter`)
3. Include the auto-configuration module in the starter

Example structure for `acme-spring-boot-autoconfigure`:

```java
@Configuration
@ConditionalOnClass(AcmeService.class)
@EnableConfigurationProperties(AcmeProperties.class)
public class AcmeAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public AcmeService acmeService(AcmeProperties properties) {
        return new AcmeServiceImpl(properties.getApiKey());
    }
}
```

And register it in `META-INF/spring.factories`:

```properties
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
com.acme.spring.boot.autoconfigure.AcmeAutoConfiguration
```

## Spring Boot Configuration

Spring Boot offers flexible ways to configure your application.

### Configuration Files

Spring Boot supports various configuration file formats:

- **application.properties**: Property file format
- **application.yml**: YAML format

These files can be placed in various locations, with the following precedence (highest to lowest):

1. Config files in the current directory (`./config/`)
2. Config files in the current directory (`./`)
3. Config files in the classpath (`/config` package)
4. Config files in the classpath root

### Configuration Properties

Spring Boot provides type-safe configuration properties:

```java
@ConfigurationProperties(prefix = "app.service")
@Component
public class ServiceProperties {
    
    private String apiUrl;
    private String apiKey;
    private int timeout = 30; // Default value
    private List<String> supportedTypes = new ArrayList<>();
    
    // Getters and setters
}
```

And in `application.properties`:
```properties
app.service.api-url=https://api.example.com
app.service.api-key=abcd1234
app.service.timeout=60
app.service.supported-types[0]=TYPE_A
app.service.supported-types[1]=TYPE_B
```

Or in `application.yml`:
```yaml
app:
  service:
    api-url: https://api.example.com
    api-key: abcd1234
    timeout: 60
    supported-types:
      - TYPE_A
      - TYPE_B
```

### Configuration Property Validation

You can add validation to configuration properties:

```java
@ConfigurationProperties(prefix = "app.service")
@Validated
@Component
public class ServiceProperties {
    
    @NotBlank
    private String apiUrl;
    
    @NotBlank
    private String apiKey;
    
    @Min(0)
    @Max(120)
    private int timeout = 30;
    
    @NotEmpty
    private List<String> supportedTypes = new ArrayList<>();
    
    // Getters and setters
}
```

### Profile-Specific Configuration

You can provide profile-specific configurations:

- `application-dev.properties` or `application-dev.yml` for the "dev" profile
- `application-prod.properties` or `application-prod.yml` for the "prod" profile

```properties
# application.properties (common properties)
app.name=My Application

# application-dev.properties (dev-specific properties)
app.service.api-url=https://dev-api.example.com

# application-prod.properties (prod-specific properties)
app.service.api-url=https://api.example.com
```

To activate a profile:

```properties
spring.profiles.active=dev
```

### Externalized Configuration

Spring Boot also supports externalized configuration in various forms:

1. Command line arguments: `java -jar app.jar --server.port=8081`
2. Java System properties: `java -Dserver.port=8081 -jar app.jar`
3. OS environment variables: `SERVER_PORT=8081 java -jar app.jar`
4. Configuration files outside the packaged application

The precedence order (from highest to lowest) is:

1. Command line arguments
2. Java System properties
3. OS environment variables
4. Profile-specific application properties outside the packaged jar
5. Profile-specific application properties inside the packaged jar
6. Application properties outside the packaged jar
7. Application properties inside the packaged jar

### Accessing Configuration Properties

You can access configuration properties in several ways:

```java
// Using @Value
@Component
public class MyComponent {
    
    @Value("${app.service.api-url}")
    private String apiUrl;
    
    @Value("${app.service.timeout:30}")
    private int timeout; // With default value
}

// Using Environment
@Component
public class EnvironmentExample {
    
    @Autowired
    private Environment env;
    
    public void doSomething() {
        String apiUrl = env.getProperty("app.service.api-url");
        int timeout = env.getProperty("app.service.timeout", Integer.class, 30);
    }
}

// Using @ConfigurationProperties (type-safe)
@Component
@ConfigurationProperties(prefix = "app.service")
public class ServiceProperties {
    
    private String apiUrl;
    private int timeout = 30;
    
    // Getters and setters
}
```

## Embedded Servers

Spring Boot includes support for embedded servers, eliminating the need to deploy applications to external servers.

### Supported Servers

- **Tomcat**: Default embedded server
- **Jetty**: Alternative server with a smaller footprint
- **Undertow**: High-performance server with a small memory footprint
- **Netty**: For reactive web applications with Spring WebFlux

### Server Configuration

You can configure the embedded server using properties:

```properties
# Server port
server.port=8080

# Context path
server.servlet.context-path=/myapp

# Server timeout
server.connection-timeout=5s

# Max HTTP header size
server.max-http-header-size=8KB

# Enable HTTP/2 support
server.http2.enabled=true

# SSL configuration
server.ssl.key-store=classpath:keystore.jks
server.ssl.key-store-password=secret
server.ssl.key-password=password
```

### Changing the Default Server

To use a different server, exclude the default Tomcat starter and include your preferred server:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
        </exclusion>
    </exclusions>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jetty</artifactId>
</dependency>
```

### Programmatic Configuration

You can configure the server programmatically:

```java
@Bean
public WebServerFactoryCustomizer<TomcatServletWebServerFactory> customizer() {
    return factory -> {
        factory.setPort(8080);
        factory.setContextPath("/myapp");
        factory.addConnectorCustomizers(connector -> {
            connector.setMaxPostSize(10 * 1024 * 1024); // 10 MB
        });
    };
}
```

### Reactive Web Applications

For reactive applications, Spring Boot uses Netty by default:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webflux</artifactId>
</dependency>
```

## Spring Boot CLI

Spring Boot CLI is a command-line tool that allows you to quickly prototype with Spring Boot.

### Installation

You can install Spring Boot CLI using various package managers:

- SDKMAN: `sdk install springboot`
- Homebrew (macOS): `brew install spring-boot`
- Scoop (Windows): `scoop install springboot`
- Manual download from Spring's website

### Basic Commands

- `spring --version`: Display CLI version
- `spring help`: Display help information
- `spring run app.groovy`: Run a Groovy script
- `spring init`: Initialize a new project
- `spring shell`: Start an interactive shell
- `spring jar`: Create a self-contained executable jar file
- `spring test`: Run tests

### Creating a New Project

```bash
spring init --name=my-project --dependencies=web,data-jpa my-project
```

Options:
- `--name`: Project name
- `--dependencies`: List of dependencies
- `--build`: Build system (maven or gradle)
- `--packaging`: Packaging type (jar or war)
- `--java-version`: Java version
- `--language`: Programming language (java, kotlin, groovy)

### Groovy Scripts

Spring Boot CLI can run Groovy scripts with simplified syntax:

```groovy
// app.groovy
@RestController
class HelloController {
    @GetMapping("/")
    String home() {
        "Hello, World!"
    }
}
```

Run with: `spring run app.groovy`

## Spring Boot DevTools

Spring Boot DevTools is a set of tools that enhance the development experience.

### Features

1. **Automatic restart**: Application restarts when files change
2. **LiveReload**: Browser refreshes when resources change
3. **Property defaults**: Development-friendly property defaults
4. **Remote development**: Support for remote application development
5. **Dev-only configuration**: Configuration that only applies in development

### Enabling DevTools

Add the dependency to your project:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
</dependency>
```

### Automatic Restart

DevTools automatically restarts your application when files on the classpath change. By default, files in `/META-INF/maven`, `/META-INF/resources`, `/resources`, `/static`, `/public`, or `/templates` don't trigger a restart.

You can customize this behavior:

```properties
# Additional paths to watch
spring.devtools.restart.additional-paths=src/main/webapp

# Paths to exclude
spring.devtools.restart.exclude=static/**,public/**

# Disable automatic restart
spring.devtools.restart.enabled=false
```

### LiveReload

DevTools includes a LiveReload server that triggers a browser refresh when resources change.

```properties
# Enable or disable LiveReload
spring.devtools.livereload.enabled=true

# Change the LiveReload server port
spring.devtools.livereload.port=35729
```

### Property Defaults

DevTools applies development-friendly property defaults:

- Disables template caching
- Enables debug logging for web groups
- Shows H2 console if H2 is in the classpath

### Remote Development

DevTools can support remote application development:

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <configuration>
                <excludeDevtools>false</excludeDevtools>
            </configuration>
        </plugin>
    </plugins>
</build>
```

Enable remote support:

```properties
spring.devtools.remote.secret=mysecret
```

Connect using: `spring remote update --url=https://example.com:8080 --secret=mysecret`

## Packaging and Deployment

Spring Boot offers various options for packaging and deploying applications.

### Executable JAR

Spring Boot creates executable JAR files with embedded servers:

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>
```

Build with: `mvn package`

Run with: `java -jar target/my-application.jar`

### Traditional WAR

For deployment to external servers, you can create WAR files:

```xml
<packaging>war</packaging>

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-tomcat</artifactId>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

And implement `SpringBootServletInitializer`:

```java
@SpringBootApplication
public class MyApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MyApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}
```

### Docker Containers

Spring Boot applications can be containerized:

```dockerfile
FROM openjdk:11-jre-slim
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

Build with: `docker build -t myapp .`

Run with: `docker run -p 8080:8080 myapp`

### Spring Boot Layered JARs

Spring Boot 2.3+ supports layered JARs for more efficient Docker images:

```xml
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
    <configuration>
        <layers>
            <enabled>true</enabled>
        </layers>
    </configuration>
</plugin>
```

Dockerfile:
```dockerfile
FROM openjdk:11-jre-slim as builder
WORKDIR /app
COPY target/*.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=builder /app/dependencies/ ./
COPY --from=builder /app/spring-boot-loader/ ./
COPY --from=builder /app/snapshot-dependencies/ ./
COPY --from=builder /app/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
```

### Cloud Deployment

Spring Boot applications can be deployed to various cloud platforms:

- **Cloud Foundry**: `cf push myapp -p target/myapp.jar`
- **Heroku**: `git push heroku main`
- **AWS Elastic Beanstalk**: Upload the JAR through AWS console or CLI
- **Azure Spring Apps**: Deploy using Azure CLI or Maven plugin
- **Google App Engine**: Deploy using App Engine Maven/Gradle plugin

## Working with Databases

Spring Boot simplifies database access through auto-configuration and starters.

### Database Configuration

Configure database connection in `application.properties`:

```properties
# H2 Database
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

# MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/mydb
spring.datasource.username=user
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/mydb
spring.datasource.username=user
spring.datasource.password=password
spring.datasource.driver-class-name=org.postgresql.Driver
```

### JPA Configuration

```properties
# Hibernate properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Entity scanning
spring.jpa.properties.hibernate.archive.autodetection=class
```

### Spring Data Repositories

Create repositories by extending Spring Data interfaces:

```java
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String username;
    private String email;
    
    // Getters and setters
}

public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUsername(String username);
    
    List<User> findByEmailContaining(String emailPart);
    
    @Query("SELECT u FROM User u WHERE u.username = :username OR u.email = :email")
    Optional<User> findByUsernameOrEmail(
        @Param("username") String username, 
        @Param("email") String email
    );
}
```

### Using Multiple Datasources

For multiple datasources, define separate configuration classes:

```java
@Configuration
@EnableJpaRepositories(
    basePackages = "com.example.primary.repository",
    entityManagerFactoryRef = "primaryEntityManagerFactory",
    transactionManagerRef = "primaryTransactionManager"
)
public class PrimaryDatabaseConfig {
    
    @Primary
    @Bean
    @ConfigurationProperties("app.datasource.primary")
    public DataSourceProperties primaryDataSourceProperties() {
        return new DataSourceProperties();
    }
    
    @Primary
    @Bean
    public DataSource primaryDataSource() {
        return primaryDataSourceProperties()
            .initializeDataSourceBuilder()
            .build();
    }
    
    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder
            .dataSource(primaryDataSource())
            .packages("com.example.primary.entity")
            .persistenceUnit("primary")
            .build();
    }
    
    @Primary
    @Bean
    public PlatformTransactionManager primaryTransactionManager(
            @Qualifier("primaryEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
```

### Database Migration

Spring Boot supports database migration tools:

**Flyway:**

```xml
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-core</artifactId>
</dependency>
```

Create migration scripts in `src/main/resources/db/migration`:
- `V1__Init_Schema.sql`
- `V2__Add_Users.sql`

```properties
# Flyway configuration
spring.flyway.baseline-on-migrate=true
spring.flyway.locations=classpath:db/migration
```

**Liquibase:**

```xml
<dependency>
    <groupId>org.liquibase</groupId>
    <artifactId>liquibase-core</artifactId>
</dependency>
```

Create changelog in `src/main/resources/db/changelog`:
- `db.changelog-master.xml`
- `db.changelog-1.0.xml`

```properties
# Liquibase configuration
spring.liquibase.change-log=classpath:db/changelog/db.changelog-master.xml
```

## Testing in Spring Boot

Spring Boot provides comprehensive testing support through the `spring-boot-starter-test` dependency.

### Unit Testing

```java
@ExtendWith(SpringExtension.class)
public class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserService userService;
    
    @Test
    public void testFindByUsername() {
        // Arrange
        User expectedUser = new User();
        expectedUser.setUsername("johndoe");
        
        when(userRepository.findByUsername("johndoe")).thenReturn(Optional.of(expectedUser));
        
        // Act
        Optional<User> result = userService.findByUsername("johndoe");
        
        // Assert
        assertTrue(result.isPresent());
        assertEquals("johndoe", result.get().getUsername());
    }
}
```

### Integration Testing

```java
@SpringBootTest
public class UserRepositoryIntegrationTest {
    
    @Autowired
    private UserRepository userRepository;
    
    @Test
    public void testSaveAndFindById() {
        // Arrange
        User user = new User();
        user.setUsername("johndoe");
        user.setEmail("john@example.com");
        
        // Act
        User savedUser = userRepository.save(user);
        Optional<User> foundUser = userRepository.findById(savedUser.getId());
        
        // Assert
        assertTrue(foundUser.isPresent());
        assertEquals("johndoe", foundUser.get().getUsername());
    }
}
```

### Sliced Testing

Spring Boot provides test slices for testing specific layers:

```java
@WebMvcTest(UserController.class)
public class UserControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private UserService userService;
    
    @Test
    public void testGetUserById() throws Exception {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setUsername("johndoe");
        
        when(userService.findById(1L)).thenReturn(Optional.of(user));
        
        // Act & Assert
        mockMvc.perform(get("/api/users/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value("johndoe"));
    }
}
```

### Testing with TestRestTemplate

```java
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTest {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    public void testCreateUser() {
        // Arrange
        UserDto userDto = new UserDto();
        userDto.setUsername("johndoe");
        userDto.setEmail("john@example.com");
        
        // Act
        ResponseEntity<UserDto> response = restTemplate.postForEntity(
            "/api/users", userDto, UserDto.class);
        
        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("johndoe", response.getBody().getUsername());
    }
}
```

### Testing with WebTestClient (WebFlux)

```java
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserControllerWebFluxTest {
    
    @Autowired
    private WebTestClient webTestClient;
    
    @Test
    public void testGetAllUsers() {
        webTestClient.get().uri("/api/users")
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(UserDto.class)
            .hasSize(3); // Assuming 3 users in the database
    }
}
```

## Profiles in Spring Boot

Spring Boot builds on Spring Framework's profiles feature with additional support.

### Defining Profiles

```java
@Configuration
@Profile("dev")
public class DevConfig {
    
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .build();
    }
}

@Configuration
@Profile("prod")
public class ProdConfig {
    
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:mysql://prod-server:3306/mydb");
        dataSource.setUsername("prod-user");
        dataSource.setPassword("prod-password");
        return dataSource;
    }
}
```

### Profile-specific Properties

Create profile-specific properties files:

- `application.properties`: Common properties
- `application-dev.properties`: Development properties
- `application-prod.properties`: Production properties

```properties
# application.properties
app.name=My Application

# application-dev.properties
app.environment=Development
logging.level.com.example=DEBUG
spring.datasource.url=jdbc:h2:mem:devdb

# application-prod.properties
app.environment=Production
logging.level.com.example=INFO
spring.datasource.url=jdbc:mysql://prod-server:3306/mydb
```

### Activating Profiles

Profiles can be activated in multiple ways:

```properties
# In application.properties
spring.profiles.active=dev
```

```java
// Programmatically
SpringApplication app = new SpringApplication(MyApplication.class);
app.setAdditionalProfiles("dev");
app.run(args);
```

Command line:
```bash
java -jar myapp.jar --spring.profiles.active=dev
```

Environment variable:
```bash
export SPRING_PROFILES_ACTIVE=dev
```

### Profile Groups

Spring Boot 2.4+ supports profile groups:

```properties
spring.profiles.group.production=prod,metrics,actuator
spring.profiles.group.development=dev,local,debug
```

Activating a group:
```properties
spring.profiles.active=development
```

### Default Profile

The default profile is activated when no profile is explicitly active:

```properties
spring.profiles.default=dev
```

## Logging in Spring Boot

Spring Boot includes a default logging configuration based on Commons Logging, with default implementation using Logback.

### Default Configuration

Spring Boot configures sensible defaults for logging:

- Console output with color-coded levels
- File output (optional)
- Configurable log levels
- Log rotation

### Log Levels

Configure log levels in `application.properties`:

```properties
# Root log level
logging.level.root=INFO

# Package-specific log levels
logging.level.org.springframework.web=DEBUG
logging.level.org.hibernate=ERROR
logging.level.com.example=DEBUG
```

### File Output

Configure file output:

```properties
# Log file location
logging.file.name=myapp.log

# Or logging directory (creates spring.log)
logging.file.path=/var/log

# Log file max size
logging.file.max-size=10MB

# Max number of archive files
logging.file.max-history=10
```

### Custom Log Pattern

Customize log output format:

```properties
# Console pattern
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n

# File pattern
logging.pattern.file=%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n
```

### Using SLF4J

Spring Boot uses SLF4J as a logging facade:

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class MyComponent {
    
    private static final Logger logger = LoggerFactory.getLogger(MyComponent.class);
    
    public void doSomething() {
        logger.trace("Trace message");
        logger.debug("Debug message");
        logger.info("Info message");
        logger.warn("Warning message");
        logger.error("Error message");
    }
}
```

### Custom Logback Configuration

For advanced configuration, create a `logback-spring.xml` file:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <!-- Include Spring Boot defaults -->
    <include resource="org/springframework/boot/logging/logback/defaults.xml"/>
    
    <!-- Console appender -->
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>${CONSOLE_LOG_PATTERN}</pattern>
            <charset>utf8</charset>
        </encoder>
    </appender>
    
    <!-- File appender -->
    <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>logs/myapp.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
            <fileNamePattern>logs/myapp-%d{yyyy-MM-dd}.%i.log</fileNamePattern>
            <maxFileSize>10MB</maxFileSize>
            <maxHistory>30</maxHistory>
            <totalSizeCap>1GB</totalSizeCap>
        </rollingPolicy>
        <encoder>
            <pattern>${FILE_LOG_PATTERN}</pattern>
            <charset>utf8</charset>
        </encoder>
    </appender>
    
    <!-- Profile-specific configuration -->
    <springProfile name="dev">
        <root level="DEBUG">
            <appender-ref ref="CONSOLE"/>
            <appender-ref ref="FILE"/>
        </root>
    </springProfile>
    
    <springProfile name="prod">
        <root level="INFO">
            <appender-ref ref="CONSOLE"/>
            <appender-ref ref="FILE"/>
        </root>
    </springProfile>
</configuration>
```

## Error Handling

Spring Boot provides several ways to handle errors in web applications.

### Default Error Page

By default, Spring Boot serves an error page with a minimal amount of information. You can customize this behavior by:

1. Adding `error.html` to `/templates/error/` (for Thymeleaf)
2. Adding `error.jsp` to `/error/` (for JSP)
3. Creating a custom error controller

### Custom Error Pages

Create status-specific error pages:

- `/templates/error/404.html`: For HTTP 404 errors
- `/templates/error/500.html`: For HTTP 500 errors
- `/templates/error/5xx.html`: For all 5xx errors

### Custom Error Attributes

Customize error attributes:

```java
@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {
    
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, 
                                              ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);
        
        // Add custom attributes
        errorAttributes.put("app", "My Application");
        errorAttributes.put("timestamp", System.currentTimeMillis());
        
        // Remove sensitive information in production
        if (!options.isIncluded(ErrorAttributeOptions.Include.STACK_TRACE)) {
            errorAttributes.remove("trace");
        }
        
        return errorAttributes;
    }
}
```

### Custom Error Controller

Create a custom error controller:

```java
@Controller
public class CustomErrorController implements ErrorController {
    
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error/404";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "error/500";
            }
        }
        
        return "error/error";
    }
}
```

### Exception Handling

Handle exceptions using `@ExceptionHandler` and `@ControllerAdvice`:

```java
@ControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
            ResourceNotFoundException ex, WebRequest request) {
        
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            ex.getMessage(),
            request.getDescription(false)
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(
            Exception ex, WebRequest request) {
        
        logger.error("Unhandled exception", ex);
        
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "An unexpected error occurred",
            request.getDescription(false)
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

// Error response class
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private int status;
    private String message;
    private String details;
    private ZonedDateTime timestamp = ZonedDateTime.now();
}
```

### Handling Validation Errors

```java
@RestController
@RequestMapping("/api")
public class UserController {
    
    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto, 
                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }
        
        // Process valid user
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }
}

@ControllerAdvice
public class ValidationExceptionHandler {
    
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationException(
            ValidationException ex) {
        
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<String> errorMessages = fieldErrors.stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .collect(Collectors.toList());
        
        ValidationErrorResponse errorResponse = new ValidationErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Validation failed",
            errorMessages
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
```

## Spring Boot Security

Spring Boot simplifies security configuration through the `spring-boot-starter-security` dependency.

### Basic Security Configuration

Add the dependency:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

Default security configuration:
- All endpoints are secured
- A random password is generated at startup
- Basic authentication is enabled

### Custom Security Configuration

Create a custom security configuration:

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/", "/home", "/public/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
            .and()
            .logout()
                .permitAll();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("user")
            .password(passwordEncoder().encode("password"))
            .roles("USER")
            .and()
            .withUser("admin")
            .password(passwordEncoder().encode("admin"))
            .roles("USER", "ADMIN");
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

### Database Authentication

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                // URL-based security rules
            .and()
            .formLogin()
                // Form login configuration
            .and()
            .rememberMe()
                .key("uniqueAndSecret")
                .tokenValiditySeconds(86400); // 1 day
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        
        Set<GrantedAuthority> authorities = user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
            .collect(Collectors.toSet());
        
        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            user.isEnabled(),
            true, // accountNonExpired
            true, // credentialsNonExpired
            true, // accountNonLocked
            authorities
        );
    }
}
```

### OAuth2 and JWT

Spring Boot simplifies OAuth2 and JWT integration:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
</dependency>
```

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/api/public/**").permitAll()
                .anyRequest().authenticated()
            .and()
            .oauth2ResourceServer()
                .jwt();
    }
    
    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri("https://auth-server/.well-known/jwks.json").build();
    }
}
```

## Building RESTful Services

Spring Boot excels at building RESTful services through its web starter and auto-configuration.

### RESTful Controller

```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return userService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto createdUser = userService.create(userDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdUser.getId())
            .toUri();
        return ResponseEntity.created(location).body(createdUser);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable Long id, 
            @Valid @RequestBody UserDto userDto) {
        return userService.update(id, userDto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        return userService.findById(id)
            .map(user -> {
                userService.delete(id);
                return ResponseEntity.noContent().<Void>build();
            })
            .orElse(ResponseEntity.notFound().build());
    }
}
```

### Content Negotiation

Spring Boot supports multiple response formats:

```java
@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
    // Implementation
}
```

Configure default content negotiation:

```properties
# Default format
spring.mvc.contentnegotiation.default-content-type=application/json

# Favor path extension over Accept header
spring.mvc.contentnegotiation.favor-path-extension=true

# Media types
spring.mvc.contentnegotiation.media-types.json=application/json
spring.mvc.contentnegotiation.media-types.xml=application/xml
```

### HATEOAS

Add hypermedia support:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-hateoas</artifactId>
</dependency>
```

```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @GetMapping("/{id}")
    public EntityModel<UserDto> getUserById(@PathVariable Long id) {
        UserDto user = userService.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        EntityModel<UserDto> resource = EntityModel.of(user);
        
        resource.add(
            linkTo(methodOn(UserController.class).getUserById(id)).withSelfRel(),
            linkTo(methodOn(UserController.class).getAllUsers()).withRel("users"),
            linkTo(methodOn(OrderController.class).getOrdersByUserId(id)).withRel("orders")
        );
        
        return resource;
    }
}
```

### Documentation with Springdoc-OpenAPI

Add Swagger/OpenAPI documentation:

```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-ui</artifactId>
    <version>1.6.9</version>
</dependency>
```

Configure OpenAPI:

```java
@Configuration
public class OpenApiConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("User Management API")
                .version("1.0")
                .description("API for managing users")
                .termsOfService("http://example.com/terms")
                .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}
```

Document controllers and models:

```java
@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "User management operations")
public class UserController {
    
    @Operation(summary = "Get a user by ID", description = "Returns a user by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found",
            content = { @Content(mediaType = "application/json",
            schema = @Schema(implementation = UserDto.class)) }),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(
            @Parameter(description = "User ID", required = true) @PathVariable Long id) {
        // Implementation
    }
}
```

Access documentation at: http://localhost:8080/swagger-ui.html

## Messaging with Spring Boot

Spring Boot simplifies integration with messaging systems.

### JMS (Java Message Service)

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-activemq</artifactId>
</dependency>
```

```java
@Service
public class JmsMessageService {
    
    @Autowired
    private JmsTemplate jmsTemplate;
    
    public void sendMessage(String destination, Object message) {
        jmsTemplate.convertAndSend(destination, message);
    }
    
    @JmsListener(destination = "user-queue")
    public void receiveMessage(UserDto user) {
        System.out.println("Received user: " + user.getUsername());
    }
}
```

### RabbitMQ

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```

```java
@Configuration
public class RabbitConfig {
    
    @Bean
    public Queue userQueue() {
        return new Queue("user-queue", true);
    }
    
    @Bean
    public TopicExchange userExchange() {
        return new TopicExchange("user-exchange");
    }
    
    @Bean
    public Binding userBinding(Queue userQueue, TopicExchange userExchange) {
        return BindingBuilder.bind(userQueue).to(userExchange).with("user.#");
    }
}

@Service
public class RabbitMessageService {
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    public void sendMessage(String exchange, String routingKey, Object message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
    
    @RabbitListener(queues = "user-queue")
    public void receiveMessage(UserDto user) {
        System.out.println("Received user: " + user.getUsername());
    }
}
```

### Kafka

```xml
<dependency>
    <groupId>org.springframework.kafka</groupId>
    <artifactId>spring-kafka</artifactId>
</dependency>
```

```java
@Configuration
public class KafkaConfig {
    
    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }
    
    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
    
    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "user-group");
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(configProps);
    }
    
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = 
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}

@Service
public class KafkaMessageService {
    
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    
    public void sendMessage(String topic, Object message) {
        kafkaTemplate.send(topic, message);
    }
    
    @KafkaListener(topics = "user-topic", groupId = "user-group")
    public void receiveMessage(UserDto user) {
        System.out.println("Received user: " + user.getUsername());
    }
}
```

## Spring Boot Actuator

Spring Boot Actuator provides production-ready features to help monitor and manage your application.

### Enabling Actuator

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

### Endpoint Configuration

```properties
# Enable all endpoints
management.endpoints.web.exposure.include=*

# Enable specific endpoints
management.endpoints.web.exposure.include=health,info,metrics,prometheus

# Exclude specific endpoints
management.endpoints.web.exposure.exclude=env,beans

# Base path for endpoints (default is /actuator)
management.endpoints.web.base-path=/management

# CORS configuration
management.endpoints.web.cors.allowed-origins=https://example.com
management.endpoints.web.cors.allowed-methods=GET,POST
```

### Health Endpoint

The health endpoint provides basic application health information:

```java
@Component
public class CustomHealthIndicator implements HealthIndicator {
    
    @Override
    public Health health() {
        // Check dependency, database, etc.
        boolean isHealthy = checkHealth();
        
        if (isHealthy) {
            return Health.up()
                .withDetail("service", "running")
                .withDetail("performance", "good")
                .build();
        } else {
            return Health.down()
                .withDetail("service", "not available")
                .withDetail("error", "connection refused")
                .build();
        }
    }
}
```

Configure health details:

```properties
# Show full health details
management.endpoint.health.show-details=always

# Show components but not details
management.endpoint.health.show-details=when-authorized

# Hide details
management.endpoint.health.show-details=never
```

### Info Endpoint

Customize the info endpoint:

```properties
# Static info
info.app.name=My Application
info.app.version=1.0.0
info.app.description=Spring Boot Demo Application
```

```java
@Component
public class BuildInfoContributor implements InfoContributor {
    
    @Override
    public void contribute(Info.Builder builder) {
        Map<String, Object> buildInfo = new HashMap<>();
        buildInfo.put("build-time", System.currentTimeMillis());
        buildInfo.put("environment", activeProfiles());
        
        builder.withDetail("build", buildInfo);
    }
}
```

### Metrics Endpoint

Spring Boot Actuator integrates with Micrometer to provide metrics:

```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private MeterRegistry meterRegistry;
    
    @GetMapping
    public List<UserDto> getAllUsers() {
        meterRegistry.counter("api.requests", "endpoint", "getAllUsers").increment();
        
        Timer.Sample sample = Timer.start(meterRegistry);
        try {
            return userService.findAll();
        } finally {
            sample.stop(meterRegistry.timer("api.request.duration", "endpoint", "getAllUsers"));
        }
    }
}
```

### Custom Actuator Endpoint

Create custom endpoints:

```java
@Component
@Endpoint(id = "features")
public class FeaturesEndpoint {
    
    private Map<String, Boolean> features = new ConcurrentHashMap<>();
    
    public FeaturesEndpoint() {
        features.put("user-management", true);
        features.put("order-management", false);
        features.put("reporting", false);
    }
    
    @ReadOperation
    public Map<String, Boolean> features() {
        return features;
    }
    
    @ReadOperation
    public Boolean feature(@Selector String name) {
        return features.get(name);
    }
    
    @WriteOperation
    public void configureFeature(@Selector String name, Boolean enabled) {
        features.put(name, enabled);
    }
}
```

## Spring Boot Admin

Spring Boot Admin is a web application that manages and monitors Spring Boot applications.

### Setting Up Admin Server

Create a Spring Boot Admin server:

```xml
<dependency>
    <groupId>de.codecentric</groupId>
    <artifactId>spring-boot-admin-starter-server</artifactId>
    <version>2.6.6</version>
</dependency>
```

```java
@EnableAdminServer
@SpringBootApplication
public class AdminServerApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(AdminServerApplication.class, args);
    }
}
```

### Configuring Client Applications

Configure your applications as clients:

```xml
<dependency>
    <groupId>de.codecentric</groupId>
    <artifactId>spring-boot-admin-starter-client</artifactId>
    <version>2.6.6</version>
</dependency>
```

```properties
# Spring Boot Admin server URL
spring.boot.admin.client.url=http://localhost:8080

# Application name in Admin UI
spring.boot.admin.client.instance.name=My Application

# Enable all Actuator endpoints
management.endpoints.web.exposure.include=*
```

### Securing Admin Server

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

```java
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/assets/**").permitAll()
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated()
            .and()
            .formLogin()
                .loginPage("/login")
            .and()
            .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringAntMatchers("/instances", "/actuator/**");
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("admin")
            .password("{bcrypt}" + new BCryptPasswordEncoder().encode("admin"))
            .roles("ADMIN");
    }
}
```

### Email Notifications

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
</dependency>
```

```java
@Configuration
public class NotifierConfiguration {
    
    @Bean
    public EmailNotifier emailNotifier(JavaMailSender mailSender) {
        EmailNotifier notifier = new EmailNotifier(mailSender);
        notifier.setFrom("admin@example.com");
        notifier.setTo("alerts@example.com");
        notifier.setSubject("#{application.name} is #{to.status}");
        return notifier;
    }
}
```

## Conclusion

Spring Boot significantly simplifies the development of Spring applications by providing auto-configuration, starter dependencies, embedded servers, and production-ready features. By understanding these fundamental concepts, you'll be well-equipped to build robust, scalable applications with minimal boilerplate code.

For more detailed information, refer to the official Spring Boot documentation:

- [Spring Boot Reference Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Boot API Documentation](https://docs.spring.io/spring-boot/docs/current/api/)
- [Spring Boot GitHub Repository](https://github.com/spring-projects/spring-boot)

With these fundamentals in mind, you can leverage Spring Boot to build applications quickly and efficiently while adhering to best practices.