# Java Functional Programming

## Executive Summary: Functional Programming in Modern Java
Modern Java has evolved from a strictly imperative language (telling the computer how to do things) to a declarative and functional one (describing what to do). This shift, which accelerated with Java 8, treats functions as first-class citizens, enabling code that is more concise, easier to parallelize, and more robust against state-related bugs.

## Strategic Alignment: The 5 Principles
1. Immutability: Functional Java discourages changing data; instead, it creates new versions (SSOT).
2. Statelessness: Pure functions do not rely on or change external variables, enhancing resiliency.
3. Declarative pipelines: Using the Stream API moves logic into clear, readable action steps.
4. Concurrency: Functional code is safer for multi-core and parallel execution because it avoids mutable shared state.
5. Continuous security and compliance: Security checks are embedded as pure functions in pipelines.

## Technical Deep Dive (The How)
Functional programming in Java stands on a three-legged stool: functional interfaces, lambda expressions, and the Stream API.

### 1. Functional interfaces: The blueprint

#### What is a Functional Interface?
A **functional interface** is an interface that contains exactly one abstract method. Think of it as a contract that says: _"I will do one job, and here's what that job looks like."_

**Key Characteristics:**
- **Single Abstract Method (SAM):** One and only one abstract method. It can have default methods and static methods too.
- **@FunctionalInterface Annotation:** Optional but recommended. Tells the compiler to enforce the one-method rule.
- **Enables Lambdas:** When you have a functional interface, you can use lambda expressions (compact code) instead of clunky anonymous classes.

```java
// Example: A simple functional interface
@FunctionalInterface
public interface IsEven {
    // This is the ONE abstract method
    boolean check(int number);
}

// Old way (before Java 8): verbose anonymous class
IsEven isEven = new IsEven() {
    @Override
    public boolean check(int number) {
        return number % 2 == 0;
    }
};

// New way (Java 8+): clean lambda expression
IsEven isEven = n -> n % 2 == 0;

// Use it
System.out.println(isEven.check(4));  // true
System.out.println(isEven.check(5));  // false
```

#### The Built-in Functional Interfaces

Java provides a toolbox of standard functional interfaces in the `java.util.function` package. These were introduced in Java 8 and have remained stable through Java 21. Here are the four primary types:

| Interface | Purpose | Abstract Method | Real-world analogy | Example |
| --- | --- | --- | --- | --- |
| `Predicate<T>` | Ask a yes/no question | `boolean test(T t)` | A security guard checking IDs | `n -> n > 100` (is this amount large?) |
| `Consumer<T>` | Do something with a value (side effect) | `void accept(T t)` | A printer printing a page | `System.out::println` (print the value) |
| `Supplier<T>` | Generate or provide a value | `T get()` | A factory producing items | `() -> new Random().nextInt()` (give me a random number) |
| `Function<T, R>` | Transform one thing into another | `R apply(T t)` | A currency converter | `currency -> currency * exchangeRate` |

#### Code Examples: The Four Primary Types

**1. Predicate<T> – Testing a condition**
```java
import java.util.function.Predicate;
import java.util.List;

// At 8th grade level: A Predicate is like a quiz question that returns yes/no
Predicate<Integer> isPositive = n -> n > 0;  // Question: Is this number positive?

System.out.println(isPositive.test(5));   // Output: true (yes, it is!)
System.out.println(isPositive.test(-3));  // Output: false (no, it's not)

// Real use: Filter a list of transactions for large amounts
List<Integer> transactions = List.of(10, 50, 100, 20, 200);
Predicate<Integer> isLarge = amt -> amt > 75;

transactions.stream()
    .filter(isLarge)  // Keep only the large transactions
    .forEach(System.out::println);  // Output: 100, 200
```

**2. Consumer<T> – Do something with a value**
```java
import java.util.function.Consumer;
import java.util.List;

// A Consumer is like giving instructions: "Take this, and do this with it."
Consumer<String> greet = name -> System.out.println("Hello, " + name + "!");

greet.accept("Alice");  // Output: Hello, Alice!
greet.accept("Bob");    // Output: Hello, Bob!

// Real use: Log or process each item in a list
List<String> userNames = List.of("Alice", "Bob", "Charlie");
Consumer<String> logger = name -> System.out.println("Processing: " + name);

userNames.forEach(logger);  // Output: Processing: Alice (then Bob, then Charlie)
```

**3. Supplier<T> – Generate or provide a value**
```java
import java.util.function.Supplier;
import java.util.UUID;

// A Supplier is like a magic box: "Give me something from inside without telling me what."
Supplier<Integer> randomNumber = () -> (int) (Math.random() * 100);

System.out.println(randomNumber.get());  // Output: Random number like 42
System.out.println(randomNumber.get());  // Output: Different random number like 87

// Real use: Lazy generation of expensive objects
Supplier<String> sessionId = () -> UUID.randomUUID().toString();
String id1 = sessionId.get();  // Generate when needed, not before
String id2 = sessionId.get();  // Each time, a new unique ID
```

**4. Function<T, R> – Transform one thing into another**
```java
import java.util.function.Function;
import java.util.List;
import java.util.stream.Collectors;

// A Function is like a recipe: "Give me ingredient T, I'll give you ingredient R."
Function<Integer, Integer> square = n -> n * n;

System.out.println(square.apply(5));   // Output: 25
System.out.println(square.apply(10));  // Output: 100

// Real use: Transform a list of prices into a list of discounted prices
List<Integer> prices = List.of(100, 50, 200);
Function<Integer, Integer> applyDiscount = price -> (int) (price * 0.9);  // 10% discount

List<Integer> discountedPrices = prices.stream()
    .map(applyDiscount)  // Transform each price
    .collect(Collectors.toList());
// Output: [90, 45, 180]
```

#### Evolution Across Java Versions

Java's functional programming support has evolved smoothly across versions. The core functional interfaces remain the same, but language features and performance have improved:

| Version | Year | Key Addition | Impact |
| --- | --- | --- | --- |
| **Java 8** | 2014 | Introduced `@FunctionalInterface`, lambda expressions, `java.util.function` package | Foundation of functional programming in Java. Changed the language forever. |
| **Java 9** | 2017 | Enhanced Stream API with new methods like `takeWhile()`, `dropWhile()` | Better stream filtering without custom predicates. |
| **Java 11** | 2018 | Stable LTE (Long Term Support). Local variable type inference (`var`) | Cleaner lambda code. `var s = () -> "hello"` is now valid. |
| **Java 17** | 2021 | Stable LTE. Sealed Classes, Records as standard | Better modeling of domain objects. Functional patterns now standard. |
| **Java 21** | 2023 | Stable LTE. Virtual Threads, Pattern Matching | High-concurrency functional code becomes practical. Lambdas + virtual threads = scalable architecture. |

**Note:** Functional interfaces themselves have stayed constant since Java 8. The improvements are in the surrounding ecosystem and language features that make using them easier and more powerful.

#### Other Common Functional Interfaces

Beyond the four primary types, Java provides specialized interfaces:

| Interface | Method | Purpose |
| --- | --- | --- |
| `BiFunction<T, U, R>` | `R apply(T t, U u)` | Function that takes TWO inputs |
| `UnaryOperator<T>` | `T apply(T t)` | Function where input and output are the SAME type |
| `BinaryOperator<T>` | `T apply(T t1, T t2)` | Combines TWO values of the SAME type |
| `Comparator<T>` | `int compare(T o1, T o2)` | Compare two objects (returns negative, 0, or positive) |
| `Runnable` | `void run()` | Execute code with NO input and NO output |
| `Callable<V>` | `V call()` | Execute code that RETURNS a value (can throw exceptions) |

**Reference:**  
See the official [Java 8 Functional Interfaces documentation](https://docs.oracle.com/javase/8/docs/api/java/util/function/package-summary.html) for the complete list.

### 2. Lambda expressions: The implementation
Lambdas are anonymous methods that provide a concrete implementation for functional interfaces without the boilerplate of anonymous inner classes.

```java
// Logic: (Parameters) -> { Body }
Predicate<String> isValid = s -> s != null && !s.isEmpty();
```

### 3. The Stream API: The pipeline
Streams allow you to process collections in a functional sequence.

```java
List<Integer> transactions = List.of(10, 50, 100, 20);

int sum = transactions.stream()
    .filter(n -> n > 25)           // Filter: action step
    .map(n -> n * 2)               // Map: transformation
    .reduce(0, Integer::sum);      // Reduce: aggregation
```

## Proactive Actions (The Lemons Table)

| Risk (The Lemon) | Functional mitigation strategy |
| --- | --- |
| Side effects | Use pure functions that only depend on inputs and do not modify external data. |
| NullPointerExceptions | Use `Optional<T>` to force explicit handling of missing values. |
| State pollution | Favor immutability. Use `List.of()` or `.toList()` to prevent post-processing mutation. |
| Performance overhead | Streams add overhead for small loops. Use them for complex transformations or large datasets where readability outweighs nanoseconds. |

## Strategic Conclusion: Resiliency and Scale
From a senior principal systems architect perspective, functional programming is a key enabler of scalable infrastructure. By moving away from shared mutable state, we eliminate the primary cause of race conditions in high-scale payment gateways. This declarative style makes the identity and state mesh easier to maintain and allows swapping sequential processing for `.parallelStream()` with minimal code changes.

Would you like me to perform a refactoring analysis of an existing imperative loop into a modern functional stream for your design document?

## Functional Programming in Java Tutorial
This guide provides a focused walkthrough of functional interfaces, streams, and the declarative coding style essential for modern Java development.

## Executive Summary: The Fifth Principle - Continuous Security and Compliance
The fifth strategic principle in the architecture, continuous security and compliance with embedded guardrails, ensures that functional, stateless, and immutable code remains safe in hyper-scale production environments. While functional programming handles the logic flow, this principle safeguards the integrity and safety of the journey.

### Strategic Alignment: The 5th Principle
In a functional context, continuous security treats security checks as pure functions injected directly into data pipelines. Instead of treating security as a final gate, it becomes an embedded step in every stream and map operation.

### Deep Dive: Continuous Security in Functional Java

#### 1. Security as a function (The How)
In a functional architecture, security validation is another transformation in the pipeline. Use predicates to enforce zero trust at the data level.

```java
// Embedding security guardrails directly into the data stream
List<Payment> securePayments = allPayments.stream()
    .filter(Security::isTokenValid)    // Guardrail: AuthN/AuthZ check
    .filter(Security::isWithinLimit)   // Guardrail: compliance and fraud check
    .toList();
```

#### 2. Uninterrupted journeys (The Goal)
By embedding security into code (for example, OIDC or IAM validation within the state mesh), we ensure the customer journey is not interrupted by external, clunky security layers. Compliance is verified in real time as data flows through the service, function, and action-step logic.

#### 3. Automated recovery and chaos engineering
Security is not just about blocking. It is about resiliency. If a security check fails in a serverless function, the architecture should use functional result patterns (such as `Either` or `Result`) to recover or fail gracefully without crashing the entire system.

## The Strategic Architecture Table (v6.0 Summary)

| Principle | Functional implementation | Strategic value |
| --- | --- | --- |
| 1. Customer-centric data | Stream-based transformations | Near real-time value and SSOT |
| 2. Event-driven and DDD | Topics as contracts | Decoupled domain boundaries |
| 3. Stateless and resilient | Pure functions and serverless | High scalability and low debt |
| 4. Identity and state mesh | Distributed caching (Caffeine) | Robust AuthN/AuthZ and OIDC |
| 5. Continuous security | Embedded guardrails | Uninterrupted journeys and safety |

## Proactive Actions (The Lemons Table)

| Risk (The Lemon) | Mitigation strategy |
| --- | --- |
| Silent failures | Functional pipelines can hide errors. Action: use `.peek()` for logging and result objects to capture security violations. |
| Performance drag | Complex security filters can slow streams. Action: use distributed caching (L1/L2) to store results of security lookups. |
| Outdated guardrails | Static checks become obsolete. Action: implement continuous scanning of dependencies and logic as part of CI/CD. |

## Strategic Conclusion: The Holistic View
From a senior principal systems architect perspective, the fifth principle is the shield that protects the first four. You can have elegant, immutable, and stateless code, but if it is not compliant and secure, it cannot operate in global payments. By embedding security into functional Java patterns, you achieve a security-as-code model that scales as fast as the business.

## Start here
- Full guide and examples: [java-functional-programming/README.md](java-functional-programming/README.md)

## Example map
- Core principles (3):
  [java-functional-programming/src/main/java/com/calvin/functional/interfaces/ImmutabilityExample.java](java-functional-programming/src/main/java/com/calvin/functional/interfaces/ImmutabilityExample.java)
  [java-functional-programming/src/main/java/com/calvin/functional/interfaces/StatelessnessExample.java](java-functional-programming/src/main/java/com/calvin/functional/interfaces/StatelessnessExample.java)
  [java-functional-programming/src/main/java/com/calvin/functional/streams/DeclarativePipelinesExample.java](java-functional-programming/src/main/java/com/calvin/functional/streams/DeclarativePipelinesExample.java)
- Functional interfaces, lambdas, and interfaces:
  [java-functional-programming/src/main/java/com/calvin/functional/lambdas/LambdaExpressionsExample.java](java-functional-programming/src/main/java/com/calvin/functional/lambdas/LambdaExpressionsExample.java)
- Optional pattern:
  [java-functional-programming/src/main/java/com/calvin/functional/optionals/OptionalBasicsExample.java](java-functional-programming/src/main/java/com/calvin/functional/optionals/OptionalBasicsExample.java)
  [java-functional-programming/src/main/java/com/calvin/functional/optionals/OptionalChainingExample.java](java-functional-programming/src/main/java/com/calvin/functional/optionals/OptionalChainingExample.java)
  [java-functional-programming/src/main/java/com/calvin/functional/optionals/OptionalAdvancedExample.java](java-functional-programming/src/main/java/com/calvin/functional/optionals/OptionalAdvancedExample.java)
- Combinator pattern:
  [java-functional-programming/src/main/java/com/calvin/functional/combinators/CustomerValidationExample.java](java-functional-programming/src/main/java/com/calvin/functional/combinators/CustomerValidationExample.java)
