# 2. Java Refresher

This chapter is your complete, practical guide to Java fundamentals. If you’re new to Java or need a refresher, work through this section before moving on. All code is ready to run and explained in detail.

---

## 2.1 Java Basics
### Variables and Data Types
Java is a statically-typed language, which means you must declare the type of every variable.
```java
int age = 25; // integer
String name = "Alice"; // string
boolean isActive = true; // boolean
char grade = 'A'; // character
double salary = 50000.5; // floating point
```

### Control Flow
Control flow lets you make decisions and repeat actions.
```java
if (age > 18) {
    System.out.println("Adult");
} else {
    System.out.println("Minor");
}

for (int i = 0; i < 5; i++) {
    System.out.println(i); // prints 0 to 4
}
```

### Methods
Methods are reusable blocks of code. They can take parameters and return values.
```java
public int add(int a, int b) {
    return a + b;
}
```

### Classes & Objects
Java is object-oriented. You define classes and create objects from them.
```java
public class Person {
    private String name;
    public Person(String name) { this.name = name; }
    public String getName() { return name; }
}
// Creating an object:
Person p = new Person("Bob");
System.out.println(p.getName());
```

### Inheritance & Interfaces
Inheritance lets you create new classes based on existing ones. Interfaces define contracts for classes.
```java
interface Animal { void speak(); }
class Dog implements Animal {
    public void speak() { System.out.println("Woof"); }
}
```

### Collections
Collections are used to store groups of objects.
```java
List<String> names = new ArrayList<>();
names.add("Alice");
names.add("Bob");
for (String n : names) System.out.println(n);
```

### Exception Handling
Exceptions are errors that can be caught and handled.
```java
try {
    int x = 10 / 0;
} catch (ArithmeticException e) {
    System.out.println("Error: " + e.getMessage());
}
```

---

## 2.2 Mini-Project: Command-Line Calculator
Write a Java program that:
- Asks the user for two numbers and an operator (+, -, *, /)
- Performs the calculation and prints the result
- Handles division by zero and invalid input

```java
import java.util.Scanner;
public class Calculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter first number: ");
        double a = sc.nextDouble();
        System.out.print("Enter operator (+, -, *, /): ");
        char op = sc.next().charAt(0);
        System.out.print("Enter second number: ");
        double b = sc.nextDouble();
        double result = 0;
        boolean valid = true;
        switch (op) {
            case '+': result = a + b; break;
            case '-': result = a - b; break;
            case '*': result = a * b; break;
            case '/':
                if (b != 0) result = a / b;
                else {
                    System.out.println("Cannot divide by zero");
                    valid = false;
                }
                break;
            default:
                System.out.println("Invalid operator");
                valid = false;
        }
        if (valid) System.out.println("Result: " + result);
    }
}
```

---

## 2.3 Java OOP Concepts in Practice
- **Encapsulation**: Use private fields and public getters/setters to protect data.
- **Inheritance**: Extend classes for code reuse.
- **Polymorphism**: Use interfaces and method overriding to allow different behaviors.
- **Abstraction**: Hide details with abstract classes/interfaces.

### Example: Encapsulation
```java
public class BankAccount {
    private double balance;
    public void deposit(double amount) { balance += amount; }
    public double getBalance() { return balance; }
}
```

### Example: Polymorphism
```java
Animal a = new Dog();
a.speak(); // prints "Woof"
```

---

## 2.4 Java Quick Reference
- Data types: int, double, boolean, char, String
- Control flow: if, else, for, while, switch
- Methods: public/private, return type, parameters
- Classes/Objects: new, constructors, fields, methods
- Collections: List, Map, Set
- Exception handling: try, catch, finally

---
[⬅️ Back](./01-getting-started.md) | [Next ➡️](./03-spring-core-basics.md)
