# 11. Project 4: Secure REST API

This chapter is a complete, practical project: securing your REST API using Spring Security. You’ll add authentication and authorization to your existing endpoints.

## 11.1 Project Overview
- Add authentication to your REST API
- Only authenticated users can access endpoints
- Add role-based access control

## 11.2 Step-by-Step Guide
1. **Add Spring Security dependency** (see previous chapter)
2. **Configure Basic Auth** (see previous chapter)
3. **Add roles to users**
   ```java
   @Override
   protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth.inMemoryAuthentication()
           .withUser("user").password("password").roles("USER")
           .and()
           .withUser("admin").password("adminpass").roles("ADMIN");
   }
   ```
4. **Secure endpoints by role**
   ```java
   @Override
   protected void configure(HttpSecurity http) throws Exception {
       http.authorizeRequests()
           .antMatchers("/admin/**").hasRole("ADMIN")
           .antMatchers("/users/**").hasAnyRole("USER", "ADMIN")
           .anyRequest().authenticated()
           .and()
           .httpBasic();
   }
   ```
5. **Test with Postman (add Authorization header)**

## 11.3 Concepts Covered
- Security configuration
- Authentication & Authorization
- Role-based access control

## 11.4 Challenge
- Secure only specific endpoints
- Add custom error messages for unauthorized access

---
[⬅️ Back](./10-spring-security.md) | [Next ➡️](./12-testing.md)
