# Java Functional Programming

This folder contains a comprehensive implementation guide for modern Java functional programming, organized by topic with runnable examples for each strategic principle.

## Overview

Functional programming in Java treats functions as first-class citizens, enabling cleaner, more testable, and more parallelizable code. This module covers the five strategic principles, the three-legged stool architecture, and practical patterns for production Java systems.

**See also:** [FUNCTIONAL_PROGRAMMING.md](../FUNCTIONAL_PROGRAMMING.md) for the executive summary, strategic alignment, and architectural deep dive.

## The 5 Strategic Principles

| Principle | What it means | Example |
| --- | --- | --- |
| **1. Immutability** | Data structures do not change after creation | [ImmutabilityExample.java](src/main/java/com/calvin/functional/interfaces/ImmutabilityExample.java) |
| **2. Statelessness** | Pure functions only depend on inputs, produce no side effects | [StatelessnessExample.java](src/main/java/com/calvin/functional/interfaces/StatelessnessExample.java) |
| **3. Declarative pipelines** | Describe what to do, not how to do it, using Streams | [DeclarativePipelinesExample.java](src/main/java/com/calvin/functional/streams/DeclarativePipelinesExample.java) |
| **4. Concurrency** | Functional code is naturally thread-safe and parallelizable | [ConcurrencyExample.java](src/main/java/com/calvin/functional/streams/ConcurrencyExample.java) |
| **5. Continuous security** | Security checks embedded as pure functions in pipelines | [SecurityAsFunctionExample.java](src/main/java/com/calvin/functional/interfaces/SecurityAsFunctionExample.java) |

## The Three-Legged Stool: Functional Interfaces, Lambdas, Streams

Functional programming in Java rests on three pillars:

### 1. Functional Interfaces (The Blueprint)
A functional interface defines the contract for a single-method function. Common types:
- `Predicate<T>` – Boolean test (is this transaction valid?)
- `Function<T, R>` – Transformation (convert currency)
- `Consumer<T>` – Side effect (log this message)
- `Supplier<T>` – Value generation (generate a UUID)

See: [LambdaExpressionsExample.java](src/main/java/com/calvin/functional/lambdas/LambdaExpressionsExample.java) for detailed patterns.

### 2. Lambda Expressions (The Implementation)
Anonymous functions that implement functional interfaces without boilerplate.

```java
Predicate<String> isValid = s -> s != null && !s.isEmpty();
Function<Integer, Integer> square = n -> n * n;
```

See: [LambdaExpressionsExample.java](src/main/java/com/calvin/functional/lambdas/LambdaExpressionsExample.java).

### 3. The Stream API (The Pipeline)
Functional transformations on collections using `filter()`, `map()`, `reduce()`.

```java
List<Integer> result = transactions.stream()
    .filter(n -> n > 25)
    .map(n -> n * 2)
    .collect(Collectors.toList());
```

See: [StreamBasicsExample.java](src/main/java/com/calvin/functional/streams/StreamBasicsExample.java).

## The Optional Pattern: Null Safety

Instead of returning `null`, return `Optional<T>` to force explicit handling of missing values.

- Basics: [OptionalBasicsExample.java](src/main/java/com/calvin/functional/optionals/OptionalBasicsExample.java)
- Chaining: [OptionalChainingExample.java](src/main/java/com/calvin/functional/optionals/OptionalChainingExample.java)
- Advanced: [OptionalAdvancedExample.java](src/main/java/com/calvin/functional/optionals/OptionalAdvancedExample.java)

## The Combinator Pattern: Building Complex Validators

Use combinators to build complex validations from simple, reusable functions.

See: [CustomerValidationExample.java](src/main/java/com/calvin/functional/combinators/CustomerValidationExample.java) for a complete validation combinator system.

## Proactive Actions (Mitigating Common Risks)

| Risk (The Lemon) | Mitigation | Example |
| --- | --- | --- |
| **Side effects** | Use pure functions, no external state modification | [StatelessnessExample.java](src/main/java/com/calvin/functional/interfaces/StatelessnessExample.java) |
| **NullPointerExceptions** | Use `Optional<T>` for optional values | [OptionalBasicsExample.java](src/main/java/com/calvin/functional/optionals/OptionalBasicsExample.java) |
| **State pollution** | Favor immutability, use `List.of()` | [ImmutabilityExample.java](src/main/java/com/calvin/functional/interfaces/ImmutabilityExample.java) |
| **Silent failures** | Use `.peek()` for logging, Result objects for errors | [DeclarativePipelinesExample.java](src/main/java/com/calvin/functional/streams/DeclarativePipelinesExample.java) |
| **Concurrency bugs** | Avoid mutable shared state, use functional transforms | [ConcurrencyExample.java](src/main/java/com/calvin/functional/streams/ConcurrencyExample.java) |

## Structure
```
java-functional-programming/
├── src/
│   ├── main/java/com/calvin/functional/
│   │   ├── lambdas/              # Basic lambda expressions and method references
│   │   ├── streams/              # Stream API, advanced pipelines, concurrency
│   │   ├── interfaces/           # Functional interfaces, principles 1, 2, 5
│   │   ├── optionals/            # Optional patterns for null safety
│   │   └── combinators/          # Combinator pattern for validation
│   └── test/java/com/calvin/functional/
│       └── StreamEfficiencyTest.java  # Performance and efficiency tests
└── README.md
```

## Organized examples by package

### lambdas/
**Topic:** Functional interfaces, lambda syntax, method references
- [LambdaExpressionsExample.java](src/main/java/com/calvin/functional/lambdas/LambdaExpressionsExample.java)
  - Lambda syntax variations, functional interface types, method references
  - Scope and variable capture rules

### streams/
**Topic:** Stream API, declarative pipelines, concurrency, performance
- [StreamBasicsExample.java](src/main/java/com/calvin/functional/streams/StreamBasicsExample.java)
  - filter, map, reduce fundamentals
  - Collectors and terminal operations
- [DeclarativePipelinesExample.java](src/main/java/com/calvin/functional/streams/DeclarativePipelinesExample.java)
  - Imperative vs declarative comparison
  - Stream chaining and complex pipelines
- [ConcurrencyExample.java](src/main/java/com/calvin/functional/streams/ConcurrencyExample.java)
  - Sequential vs parallel stream performance
  - Thread safety with immutability
  - Race condition prevention

### interfaces/
**Topic:** Core principles (immutability, statelessness, security)
- [ImmutabilityExample.java](src/main/java/com/calvin/functional/interfaces/ImmutabilityExample.java)
  - Mutable vs immutable class design
  - Immutable collections (`List.of()`, `Collections.unmodifiableList()`)
  - Functional transformations without mutation
- [StatelessnessExample.java](src/main/java/com/calvin/functional/interfaces/StatelessnessExample.java)
  - Stateful vs stateless calculator
  - Pure functions and predictability
  - Testing and thread safety benefits
- [SecurityAsFunctionExample.java](src/main/java/com/calvin/functional/interfaces/SecurityAsFunctionExample.java)
  - Security as embedded pipeline guardrails
  - Zero-trust architecture patterns
  - Compliance validation in data flow

### optionals/
**Topic:** Null safety using Optional
- [OptionalBasicsExample.java](src/main/java/com/calvin/functional/optionals/OptionalBasicsExample.java)
  - Creation: `Optional.of()`, `Optional.ofNullable()`, `Optional.empty()`
  - Operations: `isPresent()`, `isEmpty()`, `ifPresent()`, `ifPresentOrElse()`
- [OptionalChainingExample.java](src/main/java/com/calvin/functional/optionals/OptionalChainingExample.java)
  - `map()` for transformations
  - `flatMap()` for nested optionals
  - `filter()` for conditional operations
- [OptionalAdvancedExample.java](src/main/java/com/calvin/functional/optionals/OptionalAdvancedExample.java)
  - Anti-patterns to avoid (don't use `get()` without checking, use as fields, etc.)
  - Stream integration with `flatMap()`
  - `or()` method for alternative handling

### combinators/
**Topic:** Combinator pattern for building complex validators
- [CustomerValidationExample.java](src/main/java/com/calvin/functional/combinators/CustomerValidationExample.java)
  - Primitive validators (email, phone, age, name)
  - AND combinator, OR combinator, NOT combinator
  - Real-world validation pipeline example

## Big O Complexity Notes

Stream operations have predictable complexity:
- `filter(predicate)` – $O(n)$ (checks every element)
- `map(function)` – $O(n)$ (transforms every element)
- `flatMap(function)` – $O(n)$ (flattens nested structures)
- `sorted()` – $O(n \log n)$ (comparison-based sort)
- `reduce(...)` – $O(n)$ (aggregates all elements)
- `collect(Collectors.toList())` – $O(n)$ (collects to list)

**Parallel streams:** Add overhead from thread management. Use `.parallelStream()` only when:
- Data size is large (typically >1000 elements)
- Per-element operation is expensive
- The stream source supports efficient splitting

## How to run the examples

**Compile:**
```bash
cd java-functional-programming
javac -d out src/main/java/com/calvin/functional/lambdas/LambdaExpressionsExample.java
```

**Run:**
```bash
java -cp out com.calvin.functional.lambdas.LambdaExpressionsExample
```

**Run all examples in a package:**
```bash
javac -d out src/main/java/com/calvin/functional/streams/*.java
java -cp out com.calvin.functional.streams.StreamBasicsExample
java -cp out com.calvin.functional.streams.DeclarativePipelinesExample
java -cp out com.calvin.functional.streams.ConcurrencyExample
```

**With JUnit (test module):**
```bash
javac -cp junit-jupiter-api-5.x.x.jar:. -d out src/test/java/com/calvin/functional/StreamEfficiencyTest.java
junit ConsoleLauncher --scan-classpath
```

## Tests
`StreamEfficiencyTest` is a stub for performance and efficiency testing. Extend it with:
- Sequential vs parallel stream benchmarks
- Memory usage comparisons
- Garbage collection impact analysis

## Learning Path

1. **Start here:** [FUNCTIONAL_PROGRAMMING.md](../FUNCTIONAL_PROGRAMMING.md) for strategic overview and executive summary
2. **Lambdas:** [LambdaExpressionsExample.java](src/main/java/com/calvin/functional/lambdas/LambdaExpressionsExample.java) – understand the syntax and functional interfaces
3. **Streams basics:** [StreamBasicsExample.java](src/main/java/com/calvin/functional/streams/StreamBasicsExample.java) – filter, map, reduce
4. **Principles:** [ImmutabilityExample.java](src/main/java/com/calvin/functional/interfaces/ImmutabilityExample.java), [StatelessnessExample.java](src/main/java/com/calvin/functional/interfaces/StatelessnessExample.java) – understand the "why"
5. **Optionals:** [OptionalBasicsExample.java](src/main/java/com/calvin/functional/optionals/OptionalBasicsExample.java) – null-safe patterns
6. **Advanced:** [DeclarativePipelinesExample.java](src/main/java/com/calvin/functional/streams/DeclarativePipelinesExample.java), [ConcurrencyExample.java](src/main/java/com/calvin/functional/streams/ConcurrencyExample.java), [CustomerValidationExample.java](src/main/java/com/calvin/functional/combinators/CustomerValidationExample.java)

## Strategic Takeaways

- **Immutability** prevents bugs in multi-threaded code
- **Statelessness** makes functions predictable and testable
- **Declarative pipelines** improve readability and maintenance
- **Concurrency** is easier with functional patterns
- **Security as code** embeds compliance into data flow

## Related Resources

- Main hub: [README.md](../README.md)
- Data structures and algorithms: [README_FINAL.md](../README_FINAL.md)
- Functional programming strategy: [FUNCTIONAL_PROGRAMMING.md](../FUNCTIONAL_PROGRAMMING.md)

---

**Last updated:** 2026-02-12
