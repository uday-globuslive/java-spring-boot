# Java Refresher for Spring Developers

This guide provides a concise refresher on core Java concepts that are essential for working with Spring Framework. Whether you're new to Java or just need a quick review before diving into Spring, this guide covers the foundational knowledge you'll need.

## Table of Contents

1. [Java Basics](#java-basics)
2. [Object-Oriented Programming](#object-oriented-programming)
3. [Collections Framework](#collections-framework)
4. [Exception Handling](#exception-handling)
5. [Generics](#generics)
6. [Lambda Expressions and Functional Interfaces](#lambda-expressions-and-functional-interfaces)
7. [Streams API](#streams-api)
8. [Annotations](#annotations)
9. [Reflection](#reflection)
10. [I/O and NIO](#io-and-nio)
11. [Multithreading and Concurrency](#multithreading-and-concurrency)

## Java Basics

### Variables and Data Types

Java is a statically-typed language with the following primitive data types:

| Type | Size | Range | Default |
|------|------|-------|---------|
| `byte` | 8 bits | -128 to 127 | 0 |
| `short` | 16 bits | -32,768 to 32,767 | 0 |
| `int` | 32 bits | -2^31 to 2^31-1 | 0 |
| `long` | 64 bits | -2^63 to 2^63-1 | 0L |
| `float` | 32 bits | ±3.4e-38 to ±3.4e+38 | 0.0f |
| `double` | 64 bits | ±1.7e-308 to ±1.7e+308 | 0.0d |
| `char` | 16 bits | 0 to 65,535 | '\u0000' |
| `boolean` | 1 bit | true or false | false |

Reference types include classes, interfaces, arrays, and enums. The default value for reference types is `null`.

```java
// Primitive data types
int count = 10;
double price = 99.99;
char grade = 'A';
boolean isActive = true;

// Reference types
String name = "John";
Date currentDate = new Date();
Integer wrappedInt = Integer.valueOf(10);
```

### Operators

Java supports various operators:

- Arithmetic: `+`, `-`, `*`, `/`, `%`
- Assignment: `=`, `+=`, `-=`, `*=`, `/=`, `%=`
- Comparison: `==`, `!=`, `>`, `<`, `>=`, `<=`
- Logical: `&&`, `||`, `!`
- Bitwise: `&`, `|`, `^`, `~`, `<<`, `>>`, `>>>`
- Ternary: `condition ? expr1 : expr2`

### Control Flow

Java includes standard control flow structures:

```java
// If-else statement
if (condition) {
    // code block
} else if (anotherCondition) {
    // code block
} else {
    // code block
}

// Switch statement
switch (variable) {
    case value1:
        // code block
        break;
    case value2:
        // code block
        break;
    default:
        // code block
}

// For loop
for (int i = 0; i < 10; i++) {
    // code block
}

// Enhanced for loop (for-each)
for (String item : collection) {
    // code block
}

// While loop
while (condition) {
    // code block
}

// Do-while loop
do {
    // code block
} while (condition);
```

### Methods

Methods in Java are defined with a return type (or `void`), name, and parameter list:

```java
public int add(int a, int b) {
    return a + b;
}

public void printMessage(String message) {
    System.out.println(message);
}

// Variable arguments (varargs)
public double average(double... numbers) {
    double sum = 0;
    for (double num : numbers) {
        sum += num;
    }
    return numbers.length > 0 ? sum / numbers.length : 0;
}
```

### Access Modifiers

Java has four access levels controlled by access modifiers:

| Modifier | Class | Package | Subclass | World |
|----------|-------|---------|----------|-------|
| `public` | Yes | Yes | Yes | Yes |
| `protected` | Yes | Yes | Yes | No |
| `default` (no modifier) | Yes | Yes | No | No |
| `private` | Yes | No | No | No |

## Object-Oriented Programming

Java is a fully object-oriented language with support for the following OOP principles:

### Classes and Objects

```java
// Class definition
public class Person {
    // Fields (attributes)
    private String name;
    private int age;
    
    // Constructor
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    // Methods (behaviors)
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    public void greet() {
        System.out.println("Hello, my name is " + name);
    }
}

// Creating objects
Person person = new Person("John", 30);
person.greet();
```

### Inheritance

Inheritance allows classes to inherit fields and methods from other classes:

```java
// Parent class
public class Animal {
    protected String name;
    
    public Animal(String name) {
        this.name = name;
    }
    
    public void eat() {
        System.out.println(name + " is eating");
    }
    
    public void sleep() {
        System.out.println(name + " is sleeping");
    }
}

// Child class
public class Dog extends Animal {
    private String breed;
    
    public Dog(String name, String breed) {
        super(name); // Call parent constructor
        this.breed = breed;
    }
    
    public void bark() {
        System.out.println(name + " is barking");
    }
    
    // Method overriding
    @Override
    public void eat() {
        System.out.println(name + " the " + breed + " is eating dog food");
    }
}
```

### Polymorphism

Polymorphism allows objects to be treated as instances of their parent class:

```java
// Using polymorphism
Animal animal1 = new Animal("Generic Animal");
Animal animal2 = new Dog("Rex", "German Shepherd");

animal1.eat(); // Calls Animal's eat method
animal2.eat(); // Calls Dog's overridden eat method

// Runtime type checking
if (animal2 instanceof Dog) {
    Dog dog = (Dog) animal2;
    dog.bark();
}
```

### Abstraction

Abstraction allows you to hide implementation details and show only functionality:

```java
// Abstract class
public abstract class Shape {
    // Abstract method (no implementation)
    public abstract double calculateArea();
    
    // Concrete method
    public void display() {
        System.out.println("This is a shape with area: " + calculateArea());
    }
}

// Concrete class implementing abstract class
public class Circle extends Shape {
    private double radius;
    
    public Circle(double radius) {
        this.radius = radius;
    }
    
    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
}

// Interface
public interface Drawable {
    void draw(); // Abstract method
    
    // Default method (Java 8+)
    default void display() {
        System.out.println("Displaying drawable object");
    }
    
    // Static method (Java 8+)
    static void info() {
        System.out.println("This is the Drawable interface");
    }
}

// Class implementing interface
public class Rectangle extends Shape implements Drawable {
    private double width;
    private double height;
    
    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }
    
    @Override
    public double calculateArea() {
        return width * height;
    }
    
    @Override
    public void draw() {
        System.out.println("Drawing a rectangle");
    }
}
```

### Encapsulation

Encapsulation is the bundling of data and methods that operate on that data within a single unit (class), and restricting access to some of the object's components:

```java
public class BankAccount {
    private String accountNumber;
    private double balance;
    
    public BankAccount(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }
    
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public double getBalance() {
        return balance;
    }
    
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }
    
    public boolean withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }
}
```

## Collections Framework

Java Collections Framework provides interfaces and classes for storing and manipulating groups of objects.

### Main Interfaces

- `Collection`: The root interface with basic methods like `add()`, `remove()`, `contains()`
- `List`: An ordered collection that allows duplicate elements
- `Set`: A collection that doesn't allow duplicate elements
- `Map`: A collection of key-value pairs where keys are unique
- `Queue`: A collection designed for holding elements prior to processing

### Common Implementations

```java
// List implementations
List<String> arrayList = new ArrayList<>();       // Dynamic array
List<String> linkedList = new LinkedList<>();     // Doubly-linked list
List<String> vector = new Vector<>();             // Thread-safe dynamic array
List<String> stack = new Stack<>();               // LIFO stack

// Set implementations
Set<String> hashSet = new HashSet<>();            // Uses HashMap, no order
Set<String> linkedHashSet = new LinkedHashSet<>(); // Maintains insertion order
Set<String> treeSet = new TreeSet<>();            // Sorted set

// Map implementations
Map<String, Integer> hashMap = new HashMap<>();           // No order
Map<String, Integer> linkedHashMap = new LinkedHashMap<>(); // Maintains insertion order
Map<String, Integer> treeMap = new TreeMap<>();           // Sorted by keys
Map<String, Integer> hashtable = new Hashtable<>();       // Thread-safe, no nulls

// Queue implementations
Queue<String> linkedQueue = new LinkedList<>();           // Standard queue
Queue<String> priorityQueue = new PriorityQueue<>();      // Elements processed by priority
Deque<String> arrayDeque = new ArrayDeque<>();            // Double-ended queue
```

### Collections Operations

```java
// Adding elements
list.add("element");
list.addAll(otherCollection);

// Removing elements
list.remove("element");
list.removeAll(otherCollection);
list.clear();

// Checking contents
boolean contains = list.contains("element");
boolean isEmpty = list.isEmpty();
int size = list.size();

// Iterating
for (String element : list) {
    System.out.println(element);
}

Iterator<String> iterator = list.iterator();
while (iterator.hasNext()) {
    String element = iterator.next();
    System.out.println(element);
    // Can use iterator.remove() to safely remove during iteration
}

// Converting to array
String[] array = list.toArray(new String[0]);

// Sorting
Collections.sort(list);
Collections.sort(list, Comparator.reverseOrder());

// Map operations
map.put("key", 100);
Integer value = map.get("key");
map.remove("key");
boolean containsKey = map.containsKey("key");
Set<String> keys = map.keySet();
Collection<Integer> values = map.values();
Set<Map.Entry<String, Integer>> entries = map.entrySet();

// Iterating over a map
for (Map.Entry<String, Integer> entry : map.entrySet()) {
    System.out.println(entry.getKey() + ": " + entry.getValue());
}
```

## Exception Handling

Exception handling in Java allows you to handle runtime errors gracefully.

### Exception Hierarchy

- `Throwable`: The superclass of all errors and exceptions
  - `Error`: Serious problems that a reasonable application should not try to catch
  - `Exception`: Problems that a reasonable application might want to catch
    - `RuntimeException`: Exceptions that can be avoided by proper coding (unchecked)
    - Other exceptions (checked exceptions)

### Try-Catch-Finally

```java
try {
    // Code that might throw an exception
    int result = 10 / 0; // ArithmeticException
} catch (ArithmeticException e) {
    // Handle ArithmeticException
    System.out.println("Cannot divide by zero: " + e.getMessage());
} catch (Exception e) {
    // Handle any other exception
    System.out.println("Error: " + e.getMessage());
} finally {
    // Code that always executes, regardless of exception
    System.out.println("This always executes");
}
```

### Try-with-Resources (Java 7+)

```java
try (FileReader reader = new FileReader("file.txt");
     BufferedReader bufferedReader = new BufferedReader(reader)) {
    String line;
    while ((line = bufferedReader.readLine()) != null) {
        System.out.println(line);
    }
} catch (IOException e) {
    System.out.println("Error reading file: " + e.getMessage());
}
// Resources are automatically closed
```

### Throwing Exceptions

```java
public void validateAge(int age) throws IllegalArgumentException {
    if (age < 0) {
        throw new IllegalArgumentException("Age cannot be negative");
    }
    if (age > 120) {
        throw new IllegalArgumentException("Age is too high");
    }
}
```

### Custom Exceptions

```java
// Custom checked exception
public class BusinessException extends Exception {
    public BusinessException(String message) {
        super(message);
    }
    
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}

// Custom unchecked exception
public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
    
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
```

## Generics

Generics enable types (classes and interfaces) to be parameters when defining classes, interfaces, and methods.

### Generic Classes

```java
// Generic class with one type parameter
public class Box<T> {
    private T content;
    
    public Box(T content) {
        this.content = content;
    }
    
    public T getContent() {
        return content;
    }
    
    public void setContent(T content) {
        this.content = content;
    }
}

// Using generic class
Box<String> stringBox = new Box<>("Hello");
String content = stringBox.getContent();

Box<Integer> intBox = new Box<>(123);
Integer number = intBox.getContent();
```

### Generic Methods

```java
public class Utilities {
    // Generic method
    public <T> T findFirst(List<T> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
    // Generic method with multiple type parameters
    public <K, V> Map<K, V> zip(List<K> keys, List<V> values) {
        Map<K, V> map = new HashMap<>();
        int size = Math.min(keys.size(), values.size());
        
        for (int i = 0; i < size; i++) {
            map.put(keys.get(i), values.get(i));
        }
        
        return map;
    }
}

// Using generic methods
Utilities utils = new Utilities();
String first = utils.findFirst(Arrays.asList("a", "b", "c"));
Map<String, Integer> map = utils.zip(
    Arrays.asList("one", "two", "three"),
    Arrays.asList(1, 2, 3)
);
```

### Bounded Type Parameters

```java
// Upper bound: T must be Number or a subclass of Number
public class MathBox<T extends Number> {
    private T number;
    
    public MathBox(T number) {
        this.number = number;
    }
    
    public double sqrt() {
        return Math.sqrt(number.doubleValue());
    }
}

// Multiple bounds: T must implement both Comparable and Serializable
public <T extends Comparable<T> & Serializable> T max(T a, T b) {
    return a.compareTo(b) > 0 ? a : b;
}
```

### Wildcards

```java
// Unbounded wildcard: any type
public void printList(List<?> list) {
    for (Object item : list) {
        System.out.println(item);
    }
}

// Upper bounded wildcard: Number or any subclass of Number
public double sum(List<? extends Number> list) {
    double sum = 0;
    for (Number n : list) {
        sum += n.doubleValue();
    }
    return sum;
}

// Lower bounded wildcard: Integer or any superclass of Integer
public void addIntegers(List<? super Integer> list) {
    list.add(1);
    list.add(2);
    list.add(3);
}
```

## Lambda Expressions and Functional Interfaces

Java 8 introduced lambda expressions and functional interfaces to support functional programming.

### Functional Interfaces

A functional interface has exactly one abstract method. Examples include:

- `Runnable`: `void run()`
- `Callable<V>`: `V call()`
- `Comparator<T>`: `int compare(T o1, T o2)`
- `Consumer<T>`: `void accept(T t)`
- `Supplier<T>`: `T get()`
- `Function<T, R>`: `R apply(T t)`
- `Predicate<T>`: `boolean test(T t)`

```java
// Custom functional interface
@FunctionalInterface
public interface Calculator {
    int calculate(int a, int b);
    
    // Can have default and static methods
    default void printInfo() {
        System.out.println("Calculator interface");
    }
}
```

### Lambda Expressions

```java
// Old way (anonymous class)
Runnable runnable1 = new Runnable() {
    @Override
    public void run() {
        System.out.println("Hello World!");
    }
};

// Lambda expression
Runnable runnable2 = () -> System.out.println("Hello World!");

// Lambda with multiple statements
Runnable runnable3 = () -> {
    System.out.println("Hello");
    System.out.println("World!");
};

// Lambda with parameters
Calculator addition = (a, b) -> a + b;
Calculator subtraction = (a, b) -> a - b;
Calculator multiplication = (a, b) -> a * b;

// Using lambdas with collections
List<String> names = Arrays.asList("John", "Jane", "Adam", "Eve");

// Sort using a lambda
names.sort((s1, s2) -> s1.compareTo(s2));

// Filter using a lambda
List<String> filteredNames = names.stream()
        .filter(name -> name.startsWith("J"))
        .collect(Collectors.toList());

// Transform using a lambda
List<Integer> nameLengths = names.stream()
        .map(name -> name.length())
        .collect(Collectors.toList());
```

### Method References

Method references provide a shorthand for lambdas that simply call a method:

```java
// Static method reference
Function<String, Integer> parseInt = Integer::parseInt;

// Instance method reference on a particular object
String prefix = "Mr. ";
Function<String, String> addPrefix = prefix::concat;

// Instance method reference on an arbitrary object of a particular type
Function<String, String> toUpperCase = String::toUpperCase;

// Constructor reference
Supplier<List<String>> listSupplier = ArrayList::new;
```

## Streams API

Java 8 introduced the Stream API for processing sequences of elements.

### Creating Streams

```java
// From a collection
List<String> list = Arrays.asList("a", "b", "c");
Stream<String> streamFromList = list.stream();

// From an array
String[] array = {"a", "b", "c"};
Stream<String> streamFromArray = Arrays.stream(array);

// From values
Stream<String> streamFromValues = Stream.of("a", "b", "c");

// Empty stream
Stream<String> emptyStream = Stream.empty();

// Infinite streams
Stream<Integer> infiniteStream1 = Stream.iterate(0, n -> n + 2); // 0, 2, 4, 6, ...
Stream<Double> infiniteStream2 = Stream.generate(Math::random);
```

### Intermediate Operations

Intermediate operations return a new stream and are lazy (not executed until a terminal operation is invoked):

```java
Stream<String> stream = list.stream()
    .filter(s -> s.startsWith("a"))     // Filter elements
    .map(s -> s.toUpperCase())          // Transform elements
    .distinct()                         // Remove duplicates
    .sorted()                           // Sort elements
    .peek(System.out::println)          // Process elements (for debugging)
    .limit(10)                          // Limit to 10 elements
    .skip(2);                           // Skip first 2 elements
```

### Terminal Operations

Terminal operations trigger the processing of the stream and produce a result:

```java
// Collection operations
List<String> resultList = stream.collect(Collectors.toList());
Set<String> resultSet = stream.collect(Collectors.toSet());
String joined = stream.collect(Collectors.joining(", "));

// Reduction operations
Optional<String> min = stream.min(Comparator.naturalOrder());
Optional<String> max = stream.max(Comparator.naturalOrder());
Optional<String> first = stream.findFirst();
Optional<String> any = stream.findAny();
boolean allMatch = stream.allMatch(s -> s.length() > 0);
boolean anyMatch = stream.anyMatch(s -> s.contains("a"));
boolean noneMatch = stream.noneMatch(s -> s.contains("z"));
long count = stream.count();
Optional<String> reduced = stream.reduce((s1, s2) -> s1 + s2);

// Side-effect operations
stream.forEach(System.out::println);
```

### Parallel Streams

```java
// Convert a sequential stream to a parallel one
Stream<String> parallelStream = list.parallelStream();

// Process elements in parallel
long count = parallelStream
    .filter(s -> s.length() > 3)
    .count();
```

## Annotations

Annotations provide metadata about code.

### Built-in Annotations

```java
// For classes, methods, and fields
@Override           // Indicates a method overrides a superclass method
@Deprecated         // Marks code as deprecated
@SuppressWarnings   // Suppresses compiler warnings
@FunctionalInterface // Indicates an interface is a functional interface (Java 8+)

// For other annotations
@Retention          // Specifies how long the annotation should be retained
@Target             // Specifies where the annotation can be applied
@Documented         // Indicates that the annotation should be documented by javadoc
@Inherited          // Indicates that the annotation is inherited by subclasses
@Repeatable         // Indicates that the annotation can be applied multiple times (Java 8+)
```

### Custom Annotations

```java
// Define a custom annotation
@Retention(RetentionPolicy.RUNTIME) // Retained at runtime
@Target({ElementType.METHOD, ElementType.TYPE}) // Can be applied to methods and classes
public @interface Author {
    String name();
    String date() default "Unknown";
    int revision() default 1;
}

// Using the custom annotation
@Author(name = "John Doe", date = "2023-01-15", revision = 2)
public class MyClass {
    
    @Author(name = "Jane Doe")
    public void myMethod() {
        // Method implementation
    }
}

// Processing annotations at runtime using reflection
Author authorAnnotation = MyClass.class.getAnnotation(Author.class);
if (authorAnnotation != null) {
    System.out.println("Author: " + authorAnnotation.name());
    System.out.println("Date: " + authorAnnotation.date());
    System.out.println("Revision: " + authorAnnotation.revision());
}
```

## Reflection

Reflection allows you to inspect and manipulate classes, interfaces, constructors, methods, and fields at runtime.

### Class Information

```java
// Get Class object
Class<?> clazz1 = MyClass.class;                      // Using class literal
Class<?> clazz2 = object.getClass();                  // From object instance
Class<?> clazz3 = Class.forName("com.example.MyClass"); // By fully qualified name

// Get information about the class
String className = clazz1.getName();
String simpleName = clazz1.getSimpleName();
Package pkg = clazz1.getPackage();
Class<?> superClass = clazz1.getSuperclass();
Class<?>[] interfaces = clazz1.getInterfaces();
int modifiers = clazz1.getModifiers();
boolean isPublic = Modifier.isPublic(modifiers);
```

### Constructors, Methods, and Fields

```java
// Constructors
Constructor<?>[] constructors = clazz.getConstructors();            // Public only
Constructor<?>[] allConstructors = clazz.getDeclaredConstructors(); // All constructors
Constructor<?> constructor = clazz.getConstructor(String.class, int.class);

// Create an instance using reflection
Object instance = constructor.newInstance("John", 30);

// Methods
Method[] methods = clazz.getMethods();                // Public methods (including inherited)
Method[] allMethods = clazz.getDeclaredMethods();     // All methods (excluding inherited)
Method method = clazz.getMethod("myMethod", String.class);

// Invoke a method
Object result = method.invoke(instance, "parameter");

// Fields
Field[] fields = clazz.getFields();                   // Public fields (including inherited)
Field[] allFields = clazz.getDeclaredFields();        // All fields (excluding inherited)
Field field = clazz.getDeclaredField("myField");

// Get and set field values
field.setAccessible(true); // Bypass access restrictions
Object value = field.get(instance);
field.set(instance, newValue);
```

### Annotations

```java
// Get annotations from a class, method, or field
Annotation[] annotations = element.getAnnotations();
Annotation[] declaredAnnotations = element.getDeclaredAnnotations();
Author authorAnnotation = element.getAnnotation(Author.class);
```

## I/O and NIO

Java provides two API sets for I/O operations: the original I/O (java.io) and the newer NIO (java.nio).

### File I/O (java.io)

```java
// Reading a file character by character
try (FileReader reader = new FileReader("file.txt")) {
    int character;
    while ((character = reader.read()) != -1) {
        System.out.print((char) character);
    }
}

// Reading a file line by line
try (BufferedReader reader = new BufferedReader(new FileReader("file.txt"))) {
    String line;
    while ((line = reader.readLine()) != null) {
        System.out.println(line);
    }
}

// Writing to a file
try (FileWriter writer = new FileWriter("output.txt")) {
    writer.write("Hello, World!");
}

// Buffered writing
try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {
    writer.write("Hello, World!");
    writer.newLine();
    writer.write("Another line");
}

// Reading/writing binary data
try (FileInputStream in = new FileInputStream("input.bin");
     FileOutputStream out = new FileOutputStream("output.bin")) {
    byte[] buffer = new byte[1024];
    int bytesRead;
    while ((bytesRead = in.read(buffer)) != -1) {
        out.write(buffer, 0, bytesRead);
    }
}
```

### NIO (java.nio)

```java
// Reading a file (all at once)
Path path = Paths.get("file.txt");
List<String> lines = Files.readAllLines(path);
String content = Files.readString(path); // Java 11+

// Reading a file (streaming)
try (Stream<String> stream = Files.lines(path)) {
    stream.forEach(System.out::println);
}

// Writing to a file
Files.write(Paths.get("output.txt"), "Hello, World!".getBytes());
Files.writeString(Paths.get("output.txt"), "Hello, World!"); // Java 11+

// Appending to a file
Files.write(Paths.get("output.txt"), "More content".getBytes(), 
    StandardOpenOption.APPEND);

// File operations
Path source = Paths.get("source.txt");
Path target = Paths.get("target.txt");

Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
Files.delete(path);
boolean exists = Files.exists(path);
boolean isDirectory = Files.isDirectory(path);

// Directory operations
Path dir = Paths.get("directory");
Files.createDirectory(dir);
Files.createDirectories(Paths.get("path/to/directory"));

// Listing directory contents
try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
    for (Path entry : stream) {
        System.out.println(entry.getFileName());
    }
}

// Walking a directory tree
try (Stream<Path> stream = Files.walk(dir)) {
    stream.filter(Files::isRegularFile)
          .forEach(System.out::println);
}
```

## Multithreading and Concurrency

Java provides robust support for multithreaded programming.

### Creating and Starting Threads

```java
// Extending Thread
class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("Thread running: " + Thread.currentThread().getName());
    }
}

// Implementing Runnable
class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("Runnable running: " + Thread.currentThread().getName());
    }
}

// Starting threads
Thread thread1 = new MyThread();
thread1.start();

Thread thread2 = new Thread(new MyRunnable());
thread2.start();

Thread thread3 = new Thread(() -> {
    System.out.println("Lambda running: " + Thread.currentThread().getName());
});
thread3.start();
```

### Thread Lifecycle and Control

```java
// Thread states: NEW, RUNNABLE, BLOCKED, WAITING, TIMED_WAITING, TERMINATED

// Sleeping
Thread.sleep(1000); // Sleep for 1 second

// Joining (wait for thread to complete)
thread.join();
thread.join(1000); // Wait for 1 second max

// Interrupting
thread.interrupt();

// Checking if interrupted
if (Thread.interrupted()) {
    // Thread was interrupted
}

// Thread priority (1-10, default is 5)
thread.setPriority(Thread.MAX_PRIORITY); // 10
thread.setPriority(Thread.NORM_PRIORITY); // 5
thread.setPriority(Thread.MIN_PRIORITY); // 1
```

### Synchronization

```java
// Synchronized method
public synchronized void synchronizedMethod() {
    // Only one thread can execute this method on the same object at a time
}

// Synchronized block
public void method() {
    synchronized (this) {
        // Only one thread can execute this block on the same object at a time
    }
}

// Static synchronized method
public static synchronized void staticSynchronizedMethod() {
    // Only one thread can execute this method at a time across all instances
}

// Static synchronized block
public static void method() {
    synchronized (MyClass.class) {
        // Only one thread can execute this block at a time across all instances
    }
}
```

### Volatile and Final

```java
// Volatile ensures visibility across threads
private volatile boolean flag = false;

// Final variables cannot be changed after initialization
private final int value = 10;
```

### Concurrent Collections (java.util.concurrent)

```java
// Thread-safe collections
ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>();

// Adding elements (blocks if queue is full)
blockingQueue.put("element");

// Retrieving elements (blocks if queue is empty)
String element = blockingQueue.take();

// Non-blocking operations
blockingQueue.offer("element");
String element = blockingQueue.poll();
```

### Executors and Thread Pools

```java
// Creating an executor service with a fixed thread pool
ExecutorService executor = Executors.newFixedThreadPool(10);

// Submitting tasks
executor.execute(() -> {
    System.out.println("Task executed by " + Thread.currentThread().getName());
});

Future<String> future = executor.submit(() -> {
    return "Task result";
});

// Getting result from Future
try {
    String result = future.get(); // Blocks until the task completes
    String resultWithTimeout = future.get(1, TimeUnit.SECONDS); // With timeout
} catch (InterruptedException | ExecutionException | TimeoutException e) {
    e.printStackTrace();
}

// Shutting down the executor
executor.shutdown();
boolean terminated = executor.awaitTermination(10, TimeUnit.SECONDS);
```

### Atomic Variables

```java
// Atomic variables for lock-free thread-safe operations
AtomicInteger counter = new AtomicInteger(0);

int value = counter.get();
counter.set(10);
int oldValue = counter.getAndIncrement(); // Atomic increment, returns old value
int newValue = counter.incrementAndGet(); // Atomic increment, returns new value
boolean updated = counter.compareAndSet(10, 20); // Atomic compare-and-set
```

### Locks

```java
// ReentrantLock
Lock lock = new ReentrantLock();
try {
    lock.lock(); // Acquire the lock
    // Critical section
} finally {
    lock.unlock(); // Release the lock in a finally block
}

// Trying to acquire a lock with timeout
if (lock.tryLock(1, TimeUnit.SECONDS)) {
    try {
        // Critical section
    } finally {
        lock.unlock();
    }
} else {
    // Could not acquire lock
}

// ReadWriteLock
ReadWriteLock rwLock = new ReentrantReadWriteLock();
Lock readLock = rwLock.readLock();
Lock writeLock = rwLock.writeLock();

// Multiple threads can acquire the read lock simultaneously
readLock.lock();
try {
    // Read operations
} finally {
    readLock.unlock();
}

// Only one thread can acquire the write lock, and no read locks can be held
writeLock.lock();
try {
    // Write operations
} finally {
    writeLock.unlock();
}
```

### Concurrent Synchronizers

```java
// CountDownLatch: Wait until a set of operations completes
CountDownLatch latch = new CountDownLatch(3);

// In worker threads
latch.countDown(); // Decrements the count

// In coordinating thread
latch.await(); // Blocks until count reaches zero
latch.await(10, TimeUnit.SECONDS); // With timeout

// CyclicBarrier: Wait until all threads reach a common barrier point
CyclicBarrier barrier = new CyclicBarrier(3);

// In each thread
barrier.await(); // Blocks until all threads call await()

// Semaphore: Restrict the number of threads that can access a resource
Semaphore semaphore = new Semaphore(5);

semaphore.acquire(); // Acquires a permit, blocks if none available
try {
    // Access the protected resource
} finally {
    semaphore.release(); // Releases the permit
}

// Phaser: Synchronize threads working through multiple phases
Phaser phaser = new Phaser(3); // 3 parties

// In each thread
phaser.arriveAndAwaitAdvance(); // Wait for all parties to arrive

// Exchanger: Allow two threads to exchange objects at a synchronization point
Exchanger<String> exchanger = new Exchanger<>();

// In thread 1
String result1 = exchanger.exchange("data from thread 1");

// In thread 2
String result2 = exchanger.exchange("data from thread 2");
```

### CompletableFuture (Java 8+)

```java
// Create and complete manually
CompletableFuture<String> future1 = new CompletableFuture<>();
future1.complete("Result");
future1.completeExceptionally(new RuntimeException("Error"));

// Run asynchronously
CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
    System.out.println("Running asynchronously");
});

// Supply asynchronously
CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
    return "Result from async task";
});

// Chain operations
CompletableFuture<String> future4 = future3
    .thenApply(result -> result.toUpperCase())
    .thenCompose(result -> CompletableFuture.supplyAsync(() -> result + "!"))
    .thenCombine(future1, (result1, result2) -> result1 + " + " + result2)
    .exceptionally(ex -> "Error: " + ex.getMessage())
    .whenComplete((result, ex) -> {
        if (ex != null) {
            System.err.println("Error: " + ex.getMessage());
        } else {
            System.out.println("Result: " + result);
        }
    });

// Wait for multiple futures
CompletableFuture<Void> allFutures = CompletableFuture.allOf(future1, future3, future4);
CompletableFuture<Object> anyFuture = CompletableFuture.anyOf(future1, future3, future4);
```

## Conclusion

This guide covers the essential Java concepts that are most relevant for Spring developers. Understanding these fundamentals will help you work more effectively with Spring Framework and build robust Java applications.

For more detailed information, refer to the official Java documentation and tutorials:

- [Java Documentation](https://docs.oracle.com/en/java/)
- [Java Tutorials](https://docs.oracle.com/javase/tutorial/)

Now that you have refreshed your Java knowledge, you're ready to dive into Spring Framework with a solid foundation!