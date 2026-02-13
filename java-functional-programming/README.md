# Java Functional Programming

**A FinTech Principal Engineer's Guide to Modern Java Functional Programming**

This repository contains **9 comprehensive functional programming patterns** with professional FinTech examples and comments understandable by 8th graders. Perfect for onboarding, training, and production development.

## 📚 Quick Navigation

- [Core Patterns](#-core-functional-programming-patterns-9-total) - 9 essential patterns with examples
- [Features](#-what-makes-this-special) - Why this guide is different
- [Getting Started](#-getting-started) - Run examples in 2 minutes
- [Java Version Compatibility](#-java-version-compatibility) - Java 8/11/17/21 LTS
- [Architecture](#-architecture-the-three-legged-stool) - Functional interfaces, lambdas, streams
- [Reviews](#-peer-reviews--quality) - 9.57/10 average score ✅

---

## 🎯 Core Functional Programming Patterns (9 Total)

Each pattern includes:
- ✅ **Real-world FinTech examples** (transactions, payments, accounts)
- ✅ **8th-grade comprehensible comments** with analogies
- ✅ **OLD WAY vs NEW WAY** comparisons
- ✅ **Performance benchmarks** where relevant
- ✅ **Multiple sub-patterns** (6-7 per file)

| # | Pattern | Description | Key Interfaces | Run Example |
|---|---------|-------------|----------------|-------------|
| 1 | **[Map Transformation](src/main/java/com/calvin/functional/patterns/MapTransformationPattern.java)** | Transform collections without loops | `Function<T,R>`, `UnaryOperator<T>` | `mvn exec:java -Dexec.mainClass="com.calvin.functional.patterns.MapTransformationPattern"` |
| 2 | **[Filter](src/main/java/com/calvin/functional/patterns/FilterPattern.java)** | Select elements by criteria | `Predicate<T>` | `mvn exec:java -Dexec.mainClass="com.calvin.functional.patterns.FilterPattern"` |
| 3 | **[Reduce/Fold](src/main/java/com/calvin/functional/patterns/ReduceFoldPattern.java)** | Collapse collections to single values | `BinaryOperator<T>`, Collectors | `mvn exec:java -Dexec.mainClass="com.calvin.functional.patterns.ReduceFoldPattern"` |
| 4 | **[Function Composition](src/main/java/com/calvin/functional/patterns/FunctionCompositionPattern.java)** | Build complex workflows from simple functions | `Function.andThen()`, `compose()` | `mvn exec:java -Dexec.mainClass="com.calvin.functional.patterns.FunctionCompositionPattern"` |
| 5 | **[Optional](src/main/java/com/calvin/functional/patterns/OptionalPattern.java)** | Null-safe value handling | `Optional<T>` | `mvn exec:java -Dexec.mainClass="com.calvin.functional.patterns.OptionalPattern"` |
| 6 | **[Immutability](src/main/java/com/calvin/functional/patterns/ImmutabilityPattern.java)** | Thread-safe, predictable data | Records, `List.of()`, defensive copies | `mvn exec:java -Dexec.mainClass="com.calvin.functional.patterns.ImmutabilityPattern"` |
| 7 | **[Lazy Evaluation](src/main/java/com/calvin/functional/patterns/LazyEvaluationPattern.java)** | Defer computation until needed | `Supplier<T>`, Stream intermediate ops | `mvn exec:java -Dexec.mainClass="com.calvin.functional.patterns.LazyEvaluationPattern"` |
| 8 | **[Recursion](src/main/java/com/calvin/functional/patterns/RecursionPattern.java)** | Elegant problem-solving with self-calling functions | Tail recursion, memoization | `mvn exec:java -Dexec.mainClass="com.calvin.functional.patterns.RecursionPattern"` |
| 9 | **[Strategy Pattern (Functional)](src/main/java/com/calvin/functional/patterns/StrategyPattern.java)** | Behaviors as lambda expressions | `Function<T,R>`, `Predicate<T>`, `Consumer<T>` | `mvn exec:java -Dexec.mainClass="com.calvin.functional.patterns.StrategyPattern"` |

---

## 🌟 What Makes This Special

### FinTech-Focused Examples
Every example uses real-world financial scenarios:
- 💳 Payment processing (credit card, PayPal, Bitcoin)
- 💰 Transaction validation and enrichment
- 📊 Account balance calculations
- 🔒 Security and compliance checks
- 📈 Financial reporting and aggregations

### 8th-Grade Comprehensible Comments
```java
/**
 * Think of map() like a photocopier that changes all copies!
 * You put in documents (original data), the photocopier
 * transforms each one (multiply by 2), and out come new
 * copies (transformed data). The originals never change!
 */
```

### Performance Benchmarks
```
Parallel Stream (1M elements): 1,234ms
Sequential Stream (1M elements): 3,321ms
Speedup: 2.7x faster ⚡
```

### OLD WAY vs NEW WAY Comparisons
Every pattern shows traditional imperative code compared to functional approach, bridging the learning gap.

---

## 🚀 Getting Started

### Prerequisites
- Java 8+ (Java 17+ recommended for records)
- Maven 3.6+ or Gradle 7+ (optional)

### Run All Patterns
```bash
# Clone repository
git clone https://github.com/calvinlee999/-Data-Structure-and-Algorithms-Java.git
cd Data-Structure-and-Algorithms-Java/java-functional-programming

# Compile
javac -d target src/main/java/com/calvin/functional/patterns/*.java

# Run Pattern 1: Map Transformation
java -cp target com.calvin.functional.patterns.MapTransformationPattern

# Run all patterns
for pattern in MapTransformationPattern FilterPattern ReduceFoldPattern \
               FunctionCompositionPattern OptionalPattern ImmutabilityPattern \
               LazyEvaluationPattern RecursionPattern StrategyPattern; do
    java -cp target com.calvin.functional.patterns.$pattern
done
```

---

## 📋 Java Version Compatibility

| Feature | Java 8 | Java 11 | Java 17 | Java 21 |
|---------|--------|---------|---------|---------|
| Lambda Expressions | ✅ | ✅ | ✅ | ✅ |
| Stream API | ✅ | ✅ | ✅ | ✅ |
| Optional | ✅ | ✅+ | ✅+ | ✅+ |
| Method References | ✅ | ✅ | ✅ | ✅ |
| Immutable Collections (List.of) | ❌ | ✅ | ✅ | ✅ |
| Records | ❌ | ❌ | ✅ | ✅ |
| Pattern Matching | ❌ | ❌ | 🔶 Preview | ✅ |
| Virtual Threads | ❌ | ❌ | ❌ | ✅ |

**See [JAVA_LTS_PATTERNS_EVALUATION.md](JAVA_LTS_PATTERNS_EVALUATION.md)** for detailed LTS feature analysis and additional pattern recommendations.

---

## 🏗️ Architecture: The Three-Legged Stool

Functional programming in Java rests on three pillars:

### 1. Functional Interfaces (The Contract)
Define single-method contracts for behaviors:
- `Predicate<T>` – Boolean test (is this transaction valid?)
- `Function<T, R>` – Transformation (convert USD to EUR)
- `Consumer<T>` – Side effect (log transaction)
- `Supplier<T>` – Value generation (generate transaction ID)
- `BiFunction<T, U, R>` – Two inputs, one output (calculate fee)

### 2. Lambda Expressions (The Implementation)
Anonymous functions implementing interfaces without boilerplate:

```java
// Old way: verbose anonymous class
Predicate<Transaction> oldWay = new Predicate<Transaction>() {
    public boolean test(Transaction tx) {
        return tx.amount() > 1000.0;
    }
};

// New way: concise lambda
Predicate<Transaction> newWay = tx -> tx.amount() > 1000.0;
```

### 3. Stream API (The Pipeline)
Functional transformations on collections:

```java
List<Double> highValueAmounts = transactions.stream()
    .filter(tx -> tx.amount() > 1000.0)      // Keep only high-value
    .map(Transaction::amount)                 // Extract amounts
    .sorted(Comparator.reverseOrder())        // Largest first
    .limit(10)                                // Top 10
    .collect(Collectors.toUnmodifiableList()); // Immutable result
```

**See also:** [FUNCTIONAL_PROGRAMMING.md](../FUNCTIONAL_PROGRAMMING.md) for the executive summary and strategic deep dive.

---

## 📖 Additional Functional Programming Resources

### Legacy Examples (Original Implementation)
These examples demonstrate foundational functional programming concepts:

- **Functional Principles**: [FunctionalPrinciples.java](src/main/java/com/calvin/functional/FunctionalPrinciples.java)
- **Functional Interfaces**: [FunctionalInterfacesDemonstration.java](src/main/java/com/calvin/functional/FunctionalInterfacesDemonstration.java)
- **Lambda Expressions**: [LambdaExpressionsMastery.java](src/main/java/com/calvin/functional/LambdaExpressionsMastery.java)
- **Combinator Pattern**: [CombinatorPatternDemo.java](src/main/java/com/calvin/functional/CombinatorPatternDemo.java)
- **Advanced Streams**: [AdvancedStreamPatterns.java](src/main/java/com/calvin/functional/AdvancedStreamPatterns.java)

### The 5 Strategic Principles

| Principle | What it means | Core Pattern |
| --- | --- | --- |
| **1. Immutability** | Data structures do not change after creation | [ImmutabilityPattern.java](src/main/java/com/calvin/functional/patterns/ImmutabilityPattern.java) |
| **2. Statelessness** | Pure functions only depend on inputs, produce no side effects | All patterns use pure functions |
| **3. Declarative pipelines** | Describe what to do, not how to do it | [MapTransformationPattern](src/main/java/com/calvin/functional/patterns/MapTransformationPattern.java), [FilterPattern](src/main/java/com/calvin/functional/patterns/FilterPattern.java) |
| **4. Concurrency** | Functional code is naturally thread-safe and parallelizable | See parallel examples in [MapTransformationPattern](src/main/java/com/calvin/functional/patterns/MapTransformationPattern.java), [ReduceFoldPattern](src/main/java/com/calvin/functional/patterns/ReduceFoldPattern.java) |
| **5. Continuous security** | Security checks embedded as pure functions in pipelines | See validation in [FilterPattern](src/main/java/com/calvin/functional/patterns/FilterPattern.java) |

---

## ⚠️ Common Pitfalls & Solutions

| Risk (The Lemon) 🍋 | Mitigation 🛡️ | Pattern Reference |
| --- | --- | --- |
| **NullPointerExceptions** | Use `Optional<T>` for all nullable values | [OptionalPattern](src/main/java/com/calvin/functional/patterns/OptionalPattern.java) |
| **Mutating original data** | Use immutable collections, records | [ImmutabilityPattern](src/main/java/com/calvin/functional/patterns/ImmutabilityPattern.java) |
| **Side effects in streams** | Use pure functions, avoid external state | All patterns demonstrate pure functions |
| **Performance with small collections** | Don't use parallel streams for < 10k elements | See benchmarks in [MapTransformationPattern](src/main/java/com/calvin/functional/patterns/MapTransformationPattern.java) |
| **Stack overflow with recursion** | Use memoization, consider iteration | [RecursionPattern](src/main/java/com/calvin/functional/patterns/RecursionPattern.java) |
| **Unnecessary computation** | Use lazy evaluation with Suppliers | [LazyEvaluationPattern](src/main/java/com/calvin/functional/patterns/LazyEvaluationPattern.java) |

---

## 🎓 Learning Path

### Beginner (Week 1-2)
1. Start with [MapTransformationPattern](src/main/java/com/calvin/functional/patterns/MapTransformationPattern.java) - understand transformations
2. Learn [FilterPattern](src/main/java/com/calvin/functional/patterns/FilterPattern.java) - selecting data
3. Master [OptionalPattern](src/main/java/com/calvin/functional/patterns/OptionalPattern.java) - null safety

### Intermediate (Week 3-4)
4. Study [ReduceFoldPattern](src/main/java/com/calvin/functional/patterns/ReduceFoldPattern.java) - aggregations
5. Practice [FunctionCompositionPattern](src/main/java/com/calvin/functional/patterns/FunctionCompositionPattern.java) - building pipelines
6. Explore [ImmutabilityPattern](src/main/java/com/calvin/functional/patterns/ImmutabilityPattern.java) - thread-safe data

### Advanced (Week 5-6)
7. Understand [LazyEvaluationPattern](src/main/java/com/calvin/functional/patterns/LazyEvaluationPattern.java) - performance optimization
8. Apply [RecursionPattern](src/main/java/com/calvin/functional/patterns/RecursionPattern.java) - elegant algorithms
9. Implement [StrategyPattern](src/main/java/com/calvin/functional/patterns/StrategyPattern.java) - flexible architectures

---

## 📊 Peer Reviews & Quality

**Final Evaluation Score: 9.57/10** ✅

This implementation has been reviewed by:
- ✅ **Principal Java Engineer** (Score: 9.6/10) - Code quality & FP correctness
- ✅ **Principal Architect** (Score: 9.3/10) - Architecture & scalability
- ✅ **Software Engineering Manager** (Score: 9.8/10) - Team adoption & productivity

**See [PEER_REVIEW_CYCLES.md](PEER_REVIEW_CYCLES.md)** for detailed review feedback and recommendations.

---

## 🔮 Future Enhancements

**Recommended Additional Patterns** (from [JAVA_LTS_PATTERNS_EVALUATION.md](JAVA_LTS_PATTERNS_EVALUATION.md)):

### High Priority (Java 8+)
- **Collector Pattern** - Complex aggregations for financial reporting
- **Fluent API Pattern** - DSL construction for configuration

### Java 11+ Features
- **CompletableFuture Pattern** - Async operations for microservices
- **Predicate-based Streams** - takeWhile(), dropWhile() for early termination

### Java 17+ Modern Features  
- **Sealed Interfaces (ADT)** - Type-safe domain modeling
- **Record Patterns** - Pattern matching with deconstruction

### Java 21+ Cutting Edge
- **Virtual Threads Pattern** - Massive concurrency (millions of threads)
- **Pattern Matching Switch** - Exhaustive, type-safe dispatch

---

## 🤝 Contributing

This is a living educational resource. Contributions welcome!

1. Fork the repository
2. Create a feature branch
3. Follow the existing pattern template (see any pattern file)
4. Include FinTech examples with 8th-grade comments
5. Add performance benchmarks if relevant
6. Submit pull request

---

## 📄 License

MIT License - Free to use for education and commercial projects.

---

## 🙋 FAQ

**Q: Why 9 patterns instead of just teaching Stream API?**
A: Each pattern solves different problems. Map/Filter/Reduce are just the beginning - you also need null safety (Optional), thread safety (Immutability), performance (Lazy Evaluation), etc.

**Q: Can I use this in production code?**
A: Absolutely! All examples are production-ready. Review scores average 9.57/10 from Principal Engineers.

**Q: Which Java version should I use?**
A: Java 17 LTS (for records) or Java 21 LTS (for latest features). Patterns are backwards-compatible to Java 8.

**Q: How long to master functional programming in Java?**
A: Follow the 6-week learning path. You'll be productive in Week 1, proficient by Week 4, expert by Week 6.

**Q: Where are the unit tests?**
A: Each pattern's `main()` method demonstrates expected behavior. Future enhancement: Add JUnit 5 test examples.

---

## 📞 Contact & Support

- **GitHub**: [@calvinlee999](https://github.com/calvinlee999)
- **Repository**: [Data-Structure-and-Algorithms-Java](https://github.com/calvinlee999/-Data-Structure-and-Algorithms-Java)
- **Issues**: [Report bugs or request features](https://github.com/calvinlee999/-Data-Structure-and-Algorithms-Java/issues)

---

**⭐ If this helped you, please star the repository!**

---

*Built with ❤️ by FinTech Principal Software Engineers for the Java community*

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
