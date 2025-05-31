# Spring Core Concepts

This guide provides a comprehensive overview of Spring Core concepts, which form the foundation of the Spring Framework and all its related projects. Understanding these core concepts is essential for effective Spring development.

## Table of Contents

1. [Introduction to Spring Framework](#introduction-to-spring-framework)
2. [Inversion of Control (IoC)](#inversion-of-control-ioc)
3. [Dependency Injection (DI)](#dependency-injection-di)
4. [Spring Container and Beans](#spring-container-and-beans)
5. [Bean Lifecycle](#bean-lifecycle)
6. [Bean Scopes](#bean-scopes)
7. [Configuration Approaches](#configuration-approaches)
8. [Annotation-Based Configuration](#annotation-based-configuration)
9. [Java-Based Configuration](#java-based-configuration)
10. [XML-Based Configuration](#xml-based-configuration)
11. [Property Injection and Externalization](#property-injection-and-externalization)
12. [Profiles](#profiles)
13. [Aspect-Oriented Programming (AOP)](#aspect-oriented-programming-aop)
14. [Spring Expression Language (SpEL)](#spring-expression-language-spel)
15. [Event Handling](#event-handling)
16. [Resources](#resources)
17. [Validation, Data Binding, and Type Conversion](#validation-data-binding-and-type-conversion)
18. [SpEL (Spring Expression Language)](#spel-spring-expression-language)
19. [Testing](#testing)

## Introduction to Spring Framework

Spring Framework is a comprehensive, modular framework for Java-based enterprise applications. It was created to address the complexity of enterprise application development and offers infrastructure support at the application level.

**Key features of Spring Framework:**

- **Lightweight**: Spring is lightweight in terms of size and overhead
- **Inversion of Control (IoC)**: Spring manages object creation and lifecycle
- **Aspect-Oriented Programming (AOP)**: Enables separation of cross-cutting concerns
- **Container**: Manages the configuration and lifecycle of application objects
- **Framework of frameworks**: Integrates with various frameworks like Hibernate, JPA, etc.
- **Modular**: Spring consists of various modules for different functionalities

**Spring Framework Architecture:**

Spring Framework consists of several modules, organized into the following layers:

1. **Core Container**: IoC container, Beans, Core, Context, SpEL
2. **Data Access/Integration**: JDBC, ORM, OXM, JMS, Transactions
3. **Web**: Web, Servlet, Portlet, WebSocket
4. **AOP, Aspects, Instrumentation, and Messaging**
5. **Test**: Support for testing Spring components

## Inversion of Control (IoC)

Inversion of Control (IoC) is a design principle where the control of object creation and lifecycle is transferred from the application code to a framework or container.

**Traditional Approach vs. IoC:**

Traditional:
```java
// The application code creates and manages dependencies
public class TraditionalService {
    private DependencyClass dependency;
    
    public TraditionalService() {
        // Application creates the dependency
        this.dependency = new DependencyClass();
    }
}
```

With IoC:
```java
// The container provides the dependency
public class IoCService {
    private DependencyInterface dependency;
    
    // Application receives the dependency
    public IoCService(DependencyInterface dependency) {
        this.dependency = dependency;
    }
}
```

**Benefits of IoC:**

1. **Reduced coupling**: Components are loosely coupled, making the code more modular
2. **Easier testing**: Dependencies can be mocked or stubbed
3. **Increased modularity**: Components focus on their specific functionality
4. **Simplified configuration**: Central configuration for component wiring
5. **Enhanced maintainability**: Easier to modify and extend

## Dependency Injection (DI)

Dependency Injection is a specific implementation of IoC where dependencies are "injected" into a component rather than the component creating or finding them.

### Types of Dependency Injection

**1. Constructor Injection**

Dependencies are provided through a constructor:

```java
@Component
public class UserService {
    private final UserRepository userRepository;
    
    // Dependencies injected via constructor
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
```

**2. Setter Injection**

Dependencies are provided through setter methods:

```java
@Component
public class UserService {
    private UserRepository userRepository;
    
    // Dependency injected via setter
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
```

**3. Field Injection**

Dependencies are injected directly into fields:

```java
@Component
public class UserService {
    // Dependency injected directly into field
    @Autowired
    private UserRepository userRepository;
}
```

**Recommended Approach:** Constructor injection is generally preferred because:
- It ensures that required dependencies are not null (immutability)
- It makes testing easier
- It clearly communicates dependencies

## Spring Container and Beans

The Spring container is the core of the Spring Framework. It creates objects, wires them together, configures them, and manages their lifecycle.

### Bean Definition

A "bean" is an object that is instantiated, assembled, and managed by the Spring IoC container.

```java
// A simple Spring bean
@Component
public class SimpleBean {
    private String message = "Hello, Spring!";
    
    public String getMessage() {
        return message;
    }
}
```

### Types of Spring Containers

**1. BeanFactory**

The simplest container, providing basic DI and bean management:

```java
// Using BeanFactory
Resource resource = new ClassPathResource("applicationContext.xml");
BeanFactory factory = new XmlBeanFactory(resource);
SimpleBean bean = (SimpleBean) factory.getBean("simpleBean");
```

**2. ApplicationContext**

An advanced container that builds on BeanFactory and adds enterprise-specific features:

```java
// Using ApplicationContext
ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
SimpleBean bean = context.getBean("simpleBean", SimpleBean.class);
```

**Key Features of ApplicationContext:**

- Bean factory methods for accessing application components
- Ability to load file resources
- Ability to publish events to registered listeners
- Internationalization support
- Environment-specific contexts (e.g., for web applications)

### Common ApplicationContext Implementations

- **ClassPathXmlApplicationContext**: Loads context definition from XML file in classpath
- **FileSystemXmlApplicationContext**: Loads context definition from XML file in file system
- **AnnotationConfigApplicationContext**: Loads context definition from annotated classes
- **WebApplicationContext**: ApplicationContext for web applications

## Bean Lifecycle

Spring manages the lifecycle of beans from instantiation to destruction:

### Bean Lifecycle Phases

1. **Instantiation**: Spring instantiates the bean
2. **Populate Properties**: Dependencies and properties are set
3. **BeanNameAware's setBeanName()**: If the bean implements BeanNameAware
4. **BeanFactoryAware's setBeanFactory()**: If the bean implements BeanFactoryAware
5. **ApplicationContextAware's setApplicationContext()**: If bean implements ApplicationContextAware
6. **Pre-initialization BeanPostProcessors**: preProcessBeforeInitialization methods
7. **InitializingBean's afterPropertiesSet()**: If the bean implements InitializingBean
8. **Custom init-method**: If specified
9. **Post-initialization BeanPostProcessors**: postProcessAfterInitialization methods
10. **Bean is ready to use**
11. **DisposableBean's destroy()**: If the bean implements DisposableBean
12. **Custom destroy-method**: If specified

### Implementing Lifecycle Callbacks

**Using interfaces:**

```java
@Component
public class LifecycleBean implements InitializingBean, DisposableBean {
    
    @Override
    public void afterPropertiesSet() throws Exception {
        // Initialization logic
        System.out.println("Bean is being initialized");
    }
    
    @Override
    public void destroy() throws Exception {
        // Cleanup logic
        System.out.println("Bean is being destroyed");
    }
}
```

**Using annotations:**

```java
@Component
public class AnnotatedLifecycleBean {
    
    @PostConstruct
    public void init() {
        // Initialization logic
        System.out.println("Bean is being initialized");
    }
    
    @PreDestroy
    public void cleanup() {
        // Cleanup logic
        System.out.println("Bean is being destroyed");
    }
}
```

**Using configuration:**

```java
@Configuration
public class AppConfig {
    
    @Bean(initMethod = "init", destroyMethod = "cleanup")
    public ExampleBean exampleBean() {
        return new ExampleBean();
    }
}

public class ExampleBean {
    
    public void init() {
        // Initialization logic
    }
    
    public void cleanup() {
        // Cleanup logic
    }
}
```

## Bean Scopes

Spring beans can be defined to be deployed in one of several scopes:

### Standard Bean Scopes

1. **singleton (default)**: One instance per Spring container
2. **prototype**: New instance each time requested
3. **request**: One instance per HTTP request (for web applications)
4. **session**: One instance per HTTP session (for web applications)
5. **application**: One instance per ServletContext (for web applications)
6. **websocket**: One instance per WebSocket (for web applications)

```java
// Singleton scope (default)
@Component
public class SingletonBean {
    // ...
}

// Prototype scope
@Component
@Scope("prototype")
public class PrototypeBean {
    // ...
}

// Request scope
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RequestScopedBean {
    // ...
}

// Session scope
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionScopedBean {
    // ...
}
```

### Scoped Beans as Dependencies

When a short-lived bean (e.g., prototype) is injected into a long-lived bean (e.g., singleton), special considerations apply:

```java
@Component
public class SingletonService {
    
    // Without proxy, this will always be the same prototype instance
    @Autowired
    private PrototypeBean prototypeBean;
    
    // With proxy, a new prototype instance is provided each time
    @Autowired
    @Lookup
    public PrototypeBean getPrototypeBean() {
        // Spring overrides this method
        return null;
    }
}
```

**ScopedProxyMode Options:**

- **DEFAULT**: No scoped proxy
- **NO**: No scoped proxy
- **INTERFACES**: JDK dynamic proxy implementing interfaces
- **TARGET_CLASS**: CGLIB-based proxy implementing class

## Configuration Approaches

Spring supports multiple configuration approaches, which can be used independently or in combination:

### Annotation-Based Configuration

Uses annotations to define beans and dependencies:

```java
@Component
public class UserService {
    
    private final UserRepository userRepository;
    
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
```

Key annotations:
- `@Component`, `@Service`, `@Repository`, `@Controller`
- `@Autowired`, `@Qualifier`, `@Value`
- `@PostConstruct`, `@PreDestroy`
- `@Configuration`, `@Bean`

To enable component scanning:

```java
@Configuration
@ComponentScan(basePackages = "com.example")
public class AppConfig {
    // ...
}
```

### Java-Based Configuration

Uses Java classes and methods to define beans:

```java
@Configuration
public class AppConfig {
    
    @Bean
    public UserRepository userRepository() {
        return new JpaUserRepository();
    }
    
    @Bean
    public UserService userService(UserRepository userRepository) {
        return new UserService(userRepository);
    }
}
```

Key annotations:
- `@Configuration`
- `@Bean`
- `@Import`
- `@PropertySource`

### XML-Based Configuration

Uses XML files to define beans:

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                          http://www.springframework.org/schema/beans/spring-beans.xsd">
    
    <bean id="userRepository" class="com.example.JpaUserRepository"/>
    
    <bean id="userService" class="com.example.UserService">
        <constructor-arg ref="userRepository"/>
    </bean>
</beans>
```

### Mixing Configuration Approaches

Spring allows mixing different configuration approaches:

```java
@Configuration
@ImportResource("classpath:applicationContext.xml")
public class AppConfig {
    
    @Bean
    public ServiceA serviceA() {
        return new ServiceAImpl();
    }
}
```

## Annotation-Based Configuration

Annotation-based configuration relies on annotations to define beans and dependencies.

### Component Scanning

Component scanning automatically detects and registers beans:

```java
@Configuration
@ComponentScan(basePackages = "com.example")
public class AppConfig {
    // ...
}
```

### Stereotype Annotations

Stereotype annotations define the role of components:

- **@Component**: Generic Spring-managed component
- **@Service**: Business service component
- **@Repository**: Data access component (with exception translation)
- **@Controller**: Web controller component
- **@RestController**: REST API controller (@Controller + @ResponseBody)

```java
@Service
public class UserServiceImpl implements UserService {
    // ...
}

@Repository
public class JpaUserRepository implements UserRepository {
    // ...
}

@Controller
public class UserController {
    // ...
}
```

### Dependency Injection Annotations

- **@Autowired**: Injects dependencies (can be used on constructors, methods, or fields)
- **@Qualifier**: Specifies which bean to inject when multiple candidates exist
- **@Value**: Injects property values, including SpEL expressions
- **@Required**: Indicates a required property (deprecated as of Spring 5.1)

```java
@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final EmailService emailService;
    
    @Autowired
    public UserService(UserRepository userRepository, 
                      @Qualifier("primaryEmailService") EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }
    
    @Value("${app.feature.enabled}")
    private boolean featureEnabled;
    
    @Value("#{systemProperties['user.region']}")
    private String region;
}
```

### JSR-330 Annotations

Spring also supports JSR-330 standard annotations:

- **@Inject** (equivalent to @Autowired)
- **@Named** (equivalent to @Component and @Qualifier)
- **@Singleton** (equivalent to @Scope("singleton"))

```java
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Named
@Singleton
public class UserService {
    
    private final UserRepository userRepository;
    
    @Inject
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
```

## Java-Based Configuration

Java-based configuration uses Java classes and methods to define beans.

### @Configuration and @Bean

```java
@Configuration
public class AppConfig {
    
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:testdb");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }
    
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
    
    @Bean
    public UserRepository userRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcUserRepository(jdbcTemplate);
    }
    
    @Bean
    public UserService userService(UserRepository userRepository) {
        return new UserService(userRepository);
    }
}
```

### Bean Dependencies

Dependencies can be specified in several ways:

```java
@Configuration
public class AppConfig {
    
    // Method parameter injection
    @Bean
    public UserService userService(UserRepository userRepository) {
        return new UserService(userRepository);
    }
    
    // Direct method call
    @Bean
    public UserService userService2() {
        return new UserService(userRepository());
    }
    
    @Bean
    public UserRepository userRepository() {
        return new JpaUserRepository();
    }
}
```

### Bean Lifecycle and Scope

```java
@Configuration
public class AppConfig {
    
    @Bean(initMethod = "init", destroyMethod = "cleanup")
    public ExampleBean exampleBean() {
        return new ExampleBean();
    }
    
    @Bean
    @Scope("prototype")
    public PrototypeBean prototypeBean() {
        return new PrototypeBean();
    }
}
```

### Importing Other Configurations

```java
@Configuration
@Import({DataSourceConfig.class, SecurityConfig.class})
public class AppConfig {
    // ...
}

@Configuration
class DataSourceConfig {
    // ...
}

@Configuration
class SecurityConfig {
    // ...
}
```

### Environment and PropertySource

```java
@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {
    
    @Autowired
    private Environment env;
    
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
        dataSource.setUrl(env.getProperty("jdbc.url"));
        dataSource.setUsername(env.getProperty("jdbc.username"));
        dataSource.setPassword(env.getProperty("jdbc.password"));
        return dataSource;
    }
}
```

## XML-Based Configuration

XML-based configuration uses XML files to define beans.

### Basic Bean Definition

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                          http://www.springframework.org/schema/beans/spring-beans.xsd">
    
    <bean id="userRepository" class="com.example.JpaUserRepository"/>
    
    <bean id="userService" class="com.example.UserService">
        <constructor-arg ref="userRepository"/>
    </bean>
</beans>
```

### Dependency Injection

**Constructor Injection:**

```xml
<bean id="userService" class="com.example.UserService">
    <constructor-arg ref="userRepository"/>
    <constructor-arg value="defaultValue"/>
    <constructor-arg>
        <list>
            <value>value1</value>
            <value>value2</value>
        </list>
    </constructor-arg>
</bean>
```

**Setter Injection:**

```xml
<bean id="userService" class="com.example.UserService">
    <property name="userRepository" ref="userRepository"/>
    <property name="maxRetries" value="3"/>
    <property name="adminEmails">
        <list>
            <value>admin@example.com</value>
            <value>support@example.com</value>
        </list>
    </property>
</bean>
```

### Collections and Complex Types

```xml
<bean id="complexBean" class="com.example.ComplexBean">
    <!-- List -->
    <property name="stringList">
        <list>
            <value>value1</value>
            <value>value2</value>
        </list>
    </property>
    
    <!-- Set -->
    <property name="integerSet">
        <set>
            <value>1</value>
            <value>2</value>
        </set>
    </property>
    
    <!-- Map -->
    <property name="stringMap">
        <map>
            <entry key="key1" value="value1"/>
            <entry key="key2" value="value2"/>
        </map>
    </property>
    
    <!-- Properties -->
    <property name="properties">
        <props>
            <prop key="prop1">value1</prop>
            <prop key="prop2">value2</prop>
        </props>
    </property>
</bean>
```

### Bean Lifecycle and Scope

```xml
<bean id="exampleBean" class="com.example.ExampleBean"
      init-method="init" destroy-method="cleanup"
      scope="singleton"/>
```

### Import and Namespaces

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                          http://www.springframework.org/schema/beans/spring-beans.xsd
                          http://www.springframework.org/schema/context
                          http://www.springframework.org/schema/context/spring-context.xsd">
    
    <!-- Import other XML configuration files -->
    <import resource="datasource-config.xml"/>
    
    <!-- Enable component scanning -->
    <context:component-scan base-package="com.example"/>
    
    <!-- Enable annotation-based configuration -->
    <context:annotation-config/>
    
    <!-- Property placeholder configurer -->
    <context:property-placeholder location="classpath:application.properties"/>
</beans>
```

## Property Injection and Externalization

Spring provides robust support for externalizing configuration and injecting properties.

### @Value Annotation

```java
@Component
public class AppConfig {
    
    @Value("${database.url}")
    private String databaseUrl;
    
    @Value("${database.username}")
    private String username;
    
    @Value("${database.password}")
    private String password;
    
    @Value("${app.maxThreads:10}") // Default value is 10
    private int maxThreads;
    
    @Value("#{systemProperties['user.region']}")
    private String region;
    
    @Value("#{new java.util.Random().nextInt(100)}")
    private int randomNumber;
}
```

### PropertySource

```java
@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {
    
    @Autowired
    private Environment env;
    
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(env.getProperty("database.url"));
        dataSource.setUsername(env.getProperty("database.username"));
        dataSource.setPassword(env.getProperty("database.password"));
        return dataSource;
    }
}
```

### Environment Abstraction

```java
@Component
public class EnvironmentService {
    
    @Autowired
    private Environment env;
    
    public String getDatabaseUrl() {
        return env.getProperty("database.url");
    }
    
    public int getServerPort() {
        return env.getProperty("server.port", Integer.class, 8080);
    }
    
    public boolean isProductionMode() {
        return env.getProperty("app.production", Boolean.class, false);
    }
    
    public String getRequiredProperty() {
        return env.getRequiredProperty("app.required.property");
    }
}
```

### Property Binding with @ConfigurationProperties

With Spring Boot, you can bind entire prefixed property sets to Java objects:

```java
@Component
@ConfigurationProperties(prefix = "app.mail")
public class MailProperties {
    
    private String host;
    private int port = 25; // Default value
    private String username;
    private String password;
    private boolean ssl = false;
    private List<String> recipients = new ArrayList<>();
    
    // Getters and setters
}
```

Properties in application.properties:
```properties
app.mail.host=smtp.example.com
app.mail.port=587
app.mail.username=user
app.mail.password=secret
app.mail.ssl=true
app.mail.recipients[0]=admin@example.com
app.mail.recipients[1]=support@example.com
```

## Profiles

Profiles allow for conditional bean registration based on the active environment.

### Defining Profiles

```java
@Configuration
@Profile("development")
public class DevelopmentConfig {
    
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }
}

@Configuration
@Profile("production")
public class ProductionConfig {
    
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:mysql://production-server:3306/mydb");
        // Set other properties
        return dataSource;
    }
}
```

```java
@Component
@Profile("development")
public class DevelopmentService implements EnvironmentService {
    // ...
}

@Component
@Profile("production")
public class ProductionService implements EnvironmentService {
    // ...
}
```

### Profile Activation

Profiles can be activated in several ways:

1. **Programmatically:**

```java
AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
context.getEnvironment().setActiveProfiles("development");
context.register(AppConfig.class);
context.refresh();
```

2. **JVM System Property:**

```bash
java -Dspring.profiles.active=development -jar app.jar
```

3. **Environment Variable:**

```bash
export SPRING_PROFILES_ACTIVE=development
```

4. **Web.xml (for web applications):**

```xml
<context-param>
    <param-name>spring.profiles.active</param-name>
    <param-value>development</param-value>
</context-param>
```

5. **Application.properties/yml (Spring Boot):**

```properties
spring.profiles.active=development
```

### Default Profile

Beans without a profile are always created. You can also define a default profile:

```java
@Configuration
@Profile("default")
public class DefaultConfig {
    // ...
}
```

### Profile Groups (Spring Boot 2.4+)

Profile groups allow you to group related profiles:

```properties
spring.profiles.group.production=prod-db,prod-mq
spring.profiles.group.development=dev-db,dev-mq
```

## Aspect-Oriented Programming (AOP)

AOP complements OOP by providing another way of thinking about program structure. While OOP modularizes applications using classes, AOP modularizes them using aspects for cross-cutting concerns.

### AOP Concepts

- **Aspect**: Modularization of a cross-cutting concern (e.g., logging, security)
- **Join Point**: Point in the program execution where an aspect can be applied
- **Advice**: Action taken by an aspect at a join point
- **Pointcut**: Expression that matches join points
- **Target Object**: Object being advised by aspects
- **Proxy**: Object created after applying advice to the target object
- **Weaving**: Process of linking aspects with other application types

### Types of Advice

- **Before**: Runs before the method execution
- **After Returning**: Runs after successful method execution
- **After Throwing**: Runs after method throws an exception
- **After (finally)**: Runs after the method execution (regardless of success or failure)
- **Around**: Surrounds the method execution, providing control over if/when the method executes

### Spring AOP Implementation

Spring AOP uses runtime proxies:
- JDK dynamic proxies for interfaces
- CGLIB proxies for classes

Example implementation:

```java
// 1. Enable AOP
@Configuration
@EnableAspectJAutoProxy
public class AppConfig {
    // ...
}

// 2. Define an aspect
@Aspect
@Component
public class LoggingAspect {
    
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    
    // Pointcut for all service methods
    @Pointcut("execution(* com.example.service.*.*(..))")
    public void serviceMethods() {}
    
    // Before advice
    @Before("serviceMethods()")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Before executing: " + joinPoint.getSignature().getName());
    }
    
    // After returning advice
    @AfterReturning(pointcut = "serviceMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("Method returned: " + result);
    }
    
    // After throwing advice
    @AfterThrowing(pointcut = "serviceMethods()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Exception exception) {
        logger.error("Exception in: " + joinPoint.getSignature().getName(), exception);
    }
    
    // After (finally) advice
    @After("serviceMethods()")
    public void logAfter(JoinPoint joinPoint) {
        logger.info("After executing: " + joinPoint.getSignature().getName());
    }
    
    // Around advice
    @Around("execution(* com.example.service.UserService.find*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Before method: " + joinPoint.getSignature().getName());
        try {
            Object result = joinPoint.proceed();
            logger.info("After method: " + joinPoint.getSignature().getName());
            return result;
        } catch (Exception e) {
            logger.error("Error in method: " + joinPoint.getSignature().getName(), e);
            throw e;
        }
    }
}
```

### Pointcut Expressions

Spring AOP uses AspectJ's pointcut expression language:

```java
// Match any method in the service package
@Pointcut("execution(* com.example.service.*.*(..))")
public void serviceMethods() {}

// Match methods with specific parameters
@Pointcut("execution(* com.example.service.UserService.findUser(Long))")
public void findUserById() {}

// Match annotated methods
@Pointcut("@annotation(com.example.annotation.Loggable)")
public void loggableMethods() {}

// Match methods in classes with specific annotations
@Pointcut("@within(org.springframework.stereotype.Service)")
public void serviceClassMethods() {}

// Combine pointcuts
@Pointcut("serviceMethods() && !findUserById()")
public void serviceMethodsExceptFindUserById() {}
```

## Spring Expression Language (SpEL)

SpEL is a powerful expression language that supports querying and manipulating object graphs at runtime.

### Basic Expressions

```java
ExpressionParser parser = new SpelExpressionParser();

// Literal expressions
Expression exp = parser.parseExpression("'Hello World'");
String message = (String) exp.getValue();

// Method invocation
exp = parser.parseExpression("'Hello World'.concat('!')");
message = (String) exp.getValue();

// Mathematical operators
exp = parser.parseExpression("10 * 2 + 5");
int result = (Integer) exp.getValue();

// Boolean operators
exp = parser.parseExpression("true and false");
boolean value = (Boolean) exp.getValue();

// Relational operators
exp = parser.parseExpression("10 > 5");
value = (Boolean) exp.getValue();
```

### SpEL in Spring Configuration

```java
@Component
public class SpELDemo {
    
    @Value("#{systemProperties['user.region']}")
    private String region;
    
    @Value("#{T(java.lang.Math).random() * 100.0}")
    private double randomNumber;
    
    @Value("#{userService.findAll().size()}")
    private int userCount;
    
    @Value("#{environment['app.max.threads'] ?: 10}")
    private int maxThreads;
    
    @Value("#{userService.admin.email}")
    private String adminEmail;
    
    @Value("#{userService.findById(1)?.email}")
    private String userEmail; // Safe navigation
}
```

### SpEL Operators and Functions

```java
// Collection selection
@Value("#{users.?[active == true]}")
private List<User> activeUsers; // List of active users

// Collection projection
@Value("#{users.![name]}")
private List<String> userNames; // List of all user names

// Collection aggregation
@Value("#{users.?[age > 30].![name]}")
private List<String> namesOfUsersOlderThan30;

// First/last elements
@Value("#{users.^[active == true]}")
private User firstActiveUser;

@Value("#{users.$[active == true]}")
private User lastActiveUser;
```

## Event Handling

Spring provides an event handling mechanism based on the Observer pattern.

### Standard Spring Events

Spring provides several standard events:

- `ContextRefreshedEvent`: Published when the ApplicationContext is initialized or refreshed
- `ContextStartedEvent`: Published when the ApplicationContext is started
- `ContextStoppedEvent`: Published when the ApplicationContext is stopped
- `ContextClosedEvent`: Published when the ApplicationContext is closed
- `RequestHandledEvent`: Published when an HTTP request is handled (in web applications)

### Custom Events

You can create and publish custom events:

```java
// 1. Define a custom event
public class UserCreatedEvent extends ApplicationEvent {
    
    private final User user;
    
    public UserCreatedEvent(Object source, User user) {
        super(source);
        this.user = user;
    }
    
    public User getUser() {
        return user;
    }
}

// 2. Publish the event
@Service
public class UserService {
    
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    
    public User createUser(User user) {
        // Save user logic
        User savedUser = userRepository.save(user);
        
        // Publish event
        eventPublisher.publishEvent(new UserCreatedEvent(this, savedUser));
        
        return savedUser;
    }
}

// 3. Listen for the event
@Component
public class UserEventListener {
    
    private static final Logger logger = LoggerFactory.getLogger(UserEventListener.class);
    
    @EventListener
    public void handleUserCreatedEvent(UserCreatedEvent event) {
        logger.info("User created: " + event.getUser().getUsername());
        // Send welcome email, notifications, etc.
    }
    
    @EventListener
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public void handleAllEvents(ApplicationEvent event) {
        logger.info("Application event: " + event);
    }
}
```

### Asynchronous Events

Events can be processed asynchronously:

```java
@Configuration
@EnableAsync
public class AsyncConfig {
    // ...
}

@Component
public class AsyncEventListener {
    
    @Async
    @EventListener
    public void handleUserCreatedEvent(UserCreatedEvent event) {
        // This method runs in a separate thread
    }
}
```

### Transaction-Bound Events

Events can be bound to transaction phases:

```java
@Component
public class TransactionalEventListener {
    
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleUserCreatedEvent(UserCreatedEvent event) {
        // This runs after transaction commits
    }
    
    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void handleFailedEvent(UserCreatedEvent event) {
        // This runs if transaction rolls back
    }
}
```

## Resources

Spring provides a resource abstraction that simplifies working with resources regardless of their location.

### Resource Interface

The `Resource` interface provides methods for working with resources:

```java
public interface Resource extends InputStreamSource {
    boolean exists();
    boolean isReadable();
    boolean isOpen();
    boolean isFile();
    URL getURL() throws IOException;
    URI getURI() throws IOException;
    File getFile() throws IOException;
    long contentLength() throws IOException;
    long lastModified() throws IOException;
    Resource createRelative(String relativePath) throws IOException;
    String getFilename();
    String getDescription();
}
```

### Resource Implementations

Spring provides several `Resource` implementations:

- **UrlResource**: For resources accessed via URL (http:, https:, ftp:, file:, etc.)
- **ClassPathResource**: For resources in the classpath
- **FileSystemResource**: For resources in the file system
- **ServletContextResource**: For resources in the web application root
- **InputStreamResource**: For resources from an input stream
- **ByteArrayResource**: For resources from a byte array

### ResourceLoader

The `ResourceLoader` interface is used to load resources:

```java
public interface ResourceLoader {
    Resource getResource(String location);
}
```

ApplicationContext implements ResourceLoader, so you can load resources directly:

```java
@Autowired
private ApplicationContext context;

public void loadResource() {
    Resource resource = context.getResource("classpath:data.txt");
    // Use resource
}
```

### ResourceLoaderAware

Implement `ResourceLoaderAware` to get a ResourceLoader injected:

```java
@Component
public class ResourceService implements ResourceLoaderAware {
    
    private ResourceLoader resourceLoader;
    
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
    
    public String loadTextFile(String location) throws IOException {
        Resource resource = resourceLoader.getResource(location);
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource.getInputStream()))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }
}
```

### Resource Patterns

ResourcePatternResolver extends ResourceLoader to support patterns:

```java
@Autowired
private ResourcePatternResolver resourcePatternResolver;

public List<Resource> findAllProperties() throws IOException {
    Resource[] resources = resourcePatternResolver.getResources("classpath*:*.properties");
    return Arrays.asList(resources);
}
```

## Validation, Data Binding, and Type Conversion

Spring provides robust support for validation, data binding, and type conversion.

### Validation

Spring integrates with JSR-303/JSR-349 Bean Validation:

```java
public class User {
    
    @NotBlank
    @Size(min = 2, max = 30)
    private String username;
    
    @NotBlank
    @Email
    private String email;
    
    @NotNull
    @Past
    private LocalDate dateOfBirth;
    
    @NotBlank
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
    private String password;
    
    // Getters and setters
}
```

Using validation in a controller:

```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user, 
                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            return ResponseEntity.badRequest().build();
        }
        
        // Process valid user
        return ResponseEntity.ok(user);
    }
}
```

### Custom Validation

You can create custom validation constraints:

```java
// 1. Define the annotation
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueUsernameValidator.class)
public @interface UniqueUsername {
    String message() default "Username already exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

// 2. Implement the validator
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (username == null) {
            return true; // Let @NotNull handle null values
        }
        
        return !userRepository.existsByUsername(username);
    }
}

// 3. Use the annotation
public class User {
    
    @NotBlank
    @UniqueUsername
    private String username;
    
    // Other fields
}
```

### Data Binding

Spring's data binding converts property values to bean properties:

```java
// Create a target object
User user = new User();

// Create a DataBinder
DataBinder binder = new DataBinder(user);

// Create property values
MutablePropertyValues pvs = new MutablePropertyValues();
pvs.add("username", "johndoe");
pvs.add("email", "john@example.com");
pvs.add("dateOfBirth", "1990-01-01");

// Bind properties
binder.bind(pvs);

// Validate
binder.validate();
BindingResult results = binder.getBindingResult();
```

### Type Conversion

Spring provides a type conversion system:

```java
// Using ConversionService
@Autowired
private ConversionService conversionService;

public void convert() {
    String dateString = "2023-01-15";
    LocalDate date = conversionService.convert(dateString, LocalDate.class);
    
    Integer number = 42;
    String numberString = conversionService.convert(number, String.class);
}

// Custom Converter
@Component
public class StringToLocalDateConverter implements Converter<String, LocalDate> {
    
    @Override
    public LocalDate convert(String source) {
        return LocalDate.parse(source);
    }
}

// Registering custom converters
@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToLocalDateConverter());
    }
}
```

### Formatter API

Formatters are converters specialized for String conversions:

```java
@Component
public class LocalDateFormatter implements Formatter<LocalDate> {
    
    @Override
    public LocalDate parse(String text, Locale locale) {
        return LocalDate.parse(text);
    }
    
    @Override
    public String print(LocalDate object, Locale locale) {
        return object.toString();
    }
}
```

## Testing

Spring provides excellent support for testing application components.

### Unit Testing with Spring

```java
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AppConfig.class})
public class UserServiceTest {
    
    @Autowired
    private UserService userService;
    
    @MockBean
    private UserRepository userRepository;
    
    @Test
    public void testFindById() {
        // Arrange
        User expectedUser = new User();
        expectedUser.setId(1L);
        expectedUser.setUsername("johndoe");
        
        when(userRepository.findById(1L)).thenReturn(Optional.of(expectedUser));
        
        // Act
        User user = userService.findById(1L);
        
        // Assert
        assertNotNull(user);
        assertEquals("johndoe", user.getUsername());
        verify(userRepository).findById(1L);
    }
}
```

### Spring Boot Testing

Spring Boot provides more testing features:

```java
@SpringBootTest
class ApplicationTests {
    
    @Autowired
    private UserService userService;
    
    @MockBean
    private UserRepository userRepository;
    
    @Test
    void contextLoads() {
        // Verify context loads correctly
    }
    
    @Test
    void testUserService() {
        // Test userService
    }
}
```

### Testing Web Controllers

```java
@WebMvcTest(UserController.class)
public class UserControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private UserService userService;
    
    @Test
    public void testGetUser() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("johndoe");
        
        when(userService.findById(1L)).thenReturn(user);
        
        mockMvc.perform(get("/api/users/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.username").value("johndoe"));
    }
}
```

### Testing with Slices

Spring Boot provides test slices for different layers:

- **@WebMvcTest**: Test Spring MVC controllers
- **@DataJpaTest**: Test JPA repositories
- **@JdbcTest**: Test JDBC-based components
- **@DataMongoTest**: Test MongoDB repositories
- **@RestClientTest**: Test REST clients
- **@JsonTest**: Test JSON serialization/deserialization

```java
@DataJpaTest
public class UserRepositoryTest {
    
    @Autowired
    private UserRepository userRepository;
    
    @Test
    public void testFindByUsername() {
        // Arrange
        User user = new User();
        user.setUsername("johndoe");
        user.setEmail("john@example.com");
        userRepository.save(user);
        
        // Act
        User foundUser = userRepository.findByUsername("johndoe").orElse(null);
        
        // Assert
        assertNotNull(foundUser);
        assertEquals("john@example.com", foundUser.getEmail());
    }
}
```

## Conclusion

This guide covers the essential concepts of Spring Core. Understanding these fundamentals is crucial for effectively using Spring Framework and its related projects like Spring Boot, Spring MVC, Spring Data, and Spring Security.

For more detailed information, refer to the official Spring documentation:

- [Spring Framework Documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/)
- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)

With these core concepts in mind, you're well-equipped to build robust, scalable, and maintainable applications using the Spring ecosystem.