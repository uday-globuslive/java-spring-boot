# 10. Spring Security

This chapter is your complete guide to securing your applications with Spring Security. You’ll learn authentication, authorization, and how to protect your REST APIs and web apps.

## 10.1 Key Concepts
- **Authentication**: Verifying user identity
- **Authorization**: Controlling access to resources
- **Security Filters**: Intercept and process security logic for requests

## 10.2 Example: Basic Auth Configuration
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .anyRequest().authenticated()
            .and()
            .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("user").password("password").roles("USER");
    }
}
```

## 10.3 Mini-Project: Secure an Endpoint
1. Add Spring Security dependency in `pom.xml`:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```
2. Secure your REST endpoints with basic authentication
3. Test with Postman (add Authorization header)

## 10.4 Spring Security Quick Reference
- Use `@EnableWebSecurity` to enable security
- Use `httpBasic()` for basic authentication
- Use `inMemoryAuthentication()` for demo users

## 10.5 Challenge
- Add role-based access (ADMIN, USER)
- Secure only specific endpoints

---
[⬅️ Back](./09-project3-web-app.md) | [Next ➡️](./11-project4-secure-api.md)
