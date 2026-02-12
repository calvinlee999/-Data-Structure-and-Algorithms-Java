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
A functional interface is any interface with exactly one abstract method. It acts as the type for a function.
- `Predicate<T>`: Takes an input, returns a boolean (e.g., is this transaction valid?)
- `Function<T, R>`: Takes type `T`, returns type `R` (e.g., convert currency to USD)
- `Consumer<T>`: Takes input, returns nothing (e.g., log this event)
- `Supplier<T>`: Takes nothing, returns a value (e.g., generate a UUID)

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
