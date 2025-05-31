# 12. Testing in Spring

This chapter is your complete guide to testing Spring applications. You’ll learn unit testing, integration testing, and how to test REST APIs with MockMvc.

## 12.1 Key Concepts
- **Unit Testing**: Test individual classes/methods (JUnit, Mockito)
- **Integration Testing**: Test how components work together (Spring Boot Test)
- **MockMvc**: Test web layer without starting a server

## 12.2 Example: Simple Test
```java
@SpringBootTest
public class HelloServiceTest {
    @Autowired
    private HelloService helloService;

    @Test
    void testSayHello() {
        assertEquals("Hello from Spring!", helloService.sayHello());
    }
}
```

## 12.3 Mini-Project: Test Your REST API
1. Write tests for your controllers and services
2. Use MockMvc for REST endpoints
```java
@Autowired
private MockMvc mockMvc;

@Test
void testHelloEndpoint() throws Exception {
    mockMvc.perform(get("/hello"))
        .andExpect(status().isOk())
        .andExpect(content().string("Hello, Spring Boot REST!"));
}
```

## 12.4 Spring Testing Quick Reference
- Use `@SpringBootTest` for integration tests
- Use `@WebMvcTest` for controller tests
- Use `MockMvc` for REST API testing

## 12.5 Challenge
- Achieve 90%+ test coverage
- Add integration tests for database

---
[⬅️ Back](./11-project4-secure-api.md) | [Next ➡️](./13-project5-tdd.md)
