# Java Functional Programming

This folder follows a standard src-based Java layout with focused packages for each functional programming topic.

## Structure
```
java-functional-programming/
├── src/
│   ├── main/java/com/calvin/functional/
│   │   ├── lambdas/
│   │   ├── streams/
│   │   ├── interfaces/
│   │   ├── optionals/
│   │   └── combinators/
│   └── test/java/com/calvin/functional/
│       └── StreamEfficiencyTest.java
└── README.md
```

## Examples by area
- Lambdas
  - [src/main/java/com/calvin/functional/lambdas/LambdaExpressionsExample.java](src/main/java/com/calvin/functional/lambdas/LambdaExpressionsExample.java)
- Streams
  - [src/main/java/com/calvin/functional/streams/StreamBasicsExample.java](src/main/java/com/calvin/functional/streams/StreamBasicsExample.java)
  - [src/main/java/com/calvin/functional/streams/DeclarativePipelinesExample.java](src/main/java/com/calvin/functional/streams/DeclarativePipelinesExample.java)
  - [src/main/java/com/calvin/functional/streams/ConcurrencyExample.java](src/main/java/com/calvin/functional/streams/ConcurrencyExample.java)
- Functional interfaces and core principles
  - [src/main/java/com/calvin/functional/interfaces/ImmutabilityExample.java](src/main/java/com/calvin/functional/interfaces/ImmutabilityExample.java)
  - [src/main/java/com/calvin/functional/interfaces/StatelessnessExample.java](src/main/java/com/calvin/functional/interfaces/StatelessnessExample.java)
  - [src/main/java/com/calvin/functional/interfaces/SecurityAsFunctionExample.java](src/main/java/com/calvin/functional/interfaces/SecurityAsFunctionExample.java)
- Optionals
  - [src/main/java/com/calvin/functional/optionals/OptionalBasicsExample.java](src/main/java/com/calvin/functional/optionals/OptionalBasicsExample.java)
  - [src/main/java/com/calvin/functional/optionals/OptionalChainingExample.java](src/main/java/com/calvin/functional/optionals/OptionalChainingExample.java)
  - [src/main/java/com/calvin/functional/optionals/OptionalAdvancedExample.java](src/main/java/com/calvin/functional/optionals/OptionalAdvancedExample.java)
- Combinators
  - [src/main/java/com/calvin/functional/combinators/CustomerValidationExample.java](src/main/java/com/calvin/functional/combinators/CustomerValidationExample.java)

## Big O notes (functional patterns)
- `map`, `filter`, `flatMap`, and `forEach` are typically $O(n)$ for $n$ elements.
- `sorted` is typically $O(n \log n)$.
- `collect(toList())` is typically $O(n)$.
- Parallel streams add overhead; use them when per-element work is significant.

## How to run the examples
```bash
cd java-functional-programming
javac -d out src/main/java/com/calvin/functional/lambdas/LambdaExpressionsExample.java
java -cp out com.calvin.functional.lambdas.LambdaExpressionsExample
```

## Tests
`StreamEfficiencyTest` is a stub that assumes JUnit 5 is on the classpath.

## Related pages
- Main hub: [README.md](../README.md)
- Data structures guide: [README_FINAL.md](../README_FINAL.md)
- Functional programming overview: [FUNCTIONAL_PROGRAMMING.md](../FUNCTIONAL_PROGRAMMING.md)
