# 13. Project 5: Test-Driven App

This chapter is a complete, practical project: building an application using Test-Driven Development (TDD) with Spring Boot. You’ll write tests first, then implement features.

## 13.1 Project Overview
- Write tests first, then implement features
- Example: To-Do List API

## 13.2 Step-by-Step Guide
1. **Write a test for a new feature**
   ```java
   @Test
   void testAddTodo() {
       Todo todo = new Todo("Learn Spring");
       todoService.addTodo(todo);
       assertTrue(todoService.getTodos().contains(todo));
   }
   ```
2. **Implement the feature**
   ```java
   public class TodoService {
       private List<Todo> todos = new ArrayList<>();
       public void addTodo(Todo todo) { todos.add(todo); }
       public List<Todo> getTodos() { return todos; }
   }
   ```
3. **Refactor and repeat**

## 13.3 Concepts Covered
- TDD cycle (Red-Green-Refactor)
- Writing effective tests
- Refactoring code for testability

## 13.4 Challenge
- Achieve 90%+ test coverage
- Add integration tests for database
- Add REST endpoints for the To-Do app

---
[⬅️ Back](./12-testing.md) | [Next ➡️](./14-advanced-topics.md)
