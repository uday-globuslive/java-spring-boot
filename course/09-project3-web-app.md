# 9. Project 3: Web Application

This chapter is a complete, practical project: a web application using Spring Boot, Spring MVC, and Thymeleaf. You’ll build a user management web app with forms and dynamic pages.

## 9.1 Project Overview
- Display a list of users on a web page
- Add new users via a form
- Edit and delete users from the web page

## 9.2 Step-by-Step Guide
1. **Add Thymeleaf dependency** (see previous chapter)
2. **Create Controller**: Handles web requests
   ```java
   @Controller
   @RequestMapping("/users")
   public class UserWebController {
       @Autowired
       private UserRepository userRepository;

       @GetMapping
       public String listUsers(Model model) {
           model.addAttribute("users", userRepository.findAll());
           return "user-list";
       }

       @GetMapping("/add")
       public String showAddForm(Model model) {
           model.addAttribute("user", new User());
           return "user-form";
       }

       @PostMapping
       public String addUser(@ModelAttribute User user) {
           userRepository.save(user);
           return "redirect:/users";
       }

       @GetMapping("/edit/{id}")
       public String showEditForm(@PathVariable Long id, Model model) {
           model.addAttribute("user", userRepository.findById(id).orElse(new User()));
           return "user-form";
       }

       @PostMapping("/update/{id}")
       public String updateUser(@PathVariable Long id, @ModelAttribute User user) {
           user.setId(id);
           userRepository.save(user);
           return "redirect:/users";
       }

       @GetMapping("/delete/{id}")
       public String deleteUser(@PathVariable Long id) {
           userRepository.deleteById(id);
           return "redirect:/users";
       }
   }
   ```
3. **Create HTML templates**:
   - `user-list.html`: List all users
   - `user-form.html`: Add/edit user form
4. **Connect to User entity and repository**

## 9.3 Concepts Covered
- MVC pattern
- Thymeleaf templates
- Form handling
- CRUD from the web UI

## 9.4 Challenge
- Add validation to the form
- Add a search box to filter users by name

---
[⬅️ Back](./08-spring-mvc.md) | [Next ➡️](./10-spring-security.md)
