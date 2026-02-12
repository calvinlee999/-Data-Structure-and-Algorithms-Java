# Data Structures, Algorithms & Java - Comprehensive Learning Hub

> **Enterprise-Grade Java Education Repository**  
> From fundamentals to production-ready code  
> *Principal Engineer Track - FinTech & Enterprise Systems*

---

## 🎯 Quick Navigation

This repository is organized into three major learning tracks. Choose your path:

| Track | Purpose | Level | Time |
|-------|---------|-------|------|
| [📊 Data Structures & Algorithms](./DATA_STRUCTURES.md) | Master fundamental CS concepts | Beginner → Advanced | 6-12 weeks |
| [🚀 Java Functional Programming](./FUNCTIONAL_PROGRAMMING_ENHANCED.md) | Modern Java development patterns | Intermediate → Expert | 2-4 weeks |
| [🌱 Spring Framework](./SPRING.md) | Enterprise application development | Intermediate → Advanced | 4-8 weeks |

---

## 🚀 Quick Start

### Prerequisites

Ensure you have installed:
- **Java 17+** - [Download](https://www.oracle.com/java/technologies/downloads/)
- **Maven 3.6+** (optional) - [Download](https://maven.apache.org/)
- **Git** - [Download](https://git-scm.com/)

### Verify Installation

```bash
java -version          # Should show Java 17+
javac -version        # Should match java version
```

### Clone the Repository

```bash
git clone https://github.com/calvinlee999/-Data-Structure-and-Algorithms-Java.git
cd Data-Structure-and-Algorithms-Java
```

### Run Your First Example

```bash
# Example 1: Data Structures
cd BigO/src/com/company
javac BigoMain.java
java BigoMain

# Example 2: Functional Programming
cd java-functional-programming/src/main/java/com/calvin/fp/principles
javac FunctionalPrinciples.java
java com.calvin.fp.principles.FunctionalPrinciples

# Example 3: LinkedList
cd LinkedList/LinkedList/src/com/company
javac LinkedListMain.java
java LinkedListMain
```

---

## 📚 Track 1: Data Structures & Algorithms

### What You'll Learn

Fundamental data structures that power every system:

- **Linear Structures**: Arrays, Linked Lists, Stacks, Queues
- **Hierarchical Structures**: Binary Trees, Heaps, Hash Tables
- **Algorithms**: 10+ sorting algorithms, Big O analysis
- **Real-world Applications**: When and why to use each structure
- **Interview Problem Solving**: Practice challenges included

### Folder Structure

```
BigO/                 # Complexity analysis (O(n), O(log n), etc.)
LinkedList/           # Singly, doubly linked lists + challenges
Stacks/              # Stack implementations + challenge
Queue/               # Queues, Priority Queues + challenge
binarySearchTree/    # BST implementations
maxHeap/            # Heap implementations
Hashtable/          # Hash tables, chaining, challenges
Sort/               # Bubble, Insertion, Merge, Quick, Heap...
binarySearch/       # Binary search variations
```

### Learning Path

**Week 1-2: Foundations**
- [ ] Arrays and Big O
- [ ] Linked Lists
- [ ] Stacks & Queues

**Week 3-4: Sorting**
- [ ] Bubble, Selection, Insertion
- [ ] Merge Sort, Quick Sort
- [ ] Counting Sort, Radix Sort

**Week 5-6: Advanced Structures**
- [ ] Binary Search Trees
- [ ] Heaps
- [ ] Hash Tables

**Week 7-8: Challenges & Optimization**
- [ ] Solve all challenge problems
- [ ] Optimize for time/space
- [ ] System design applications

### Key Resources

- [📖 Full Data Structures Guide](./DATA_STRUCTURES.md)
- Each folder contains `README.md` with detailed explanations
- All implementations include complexity analysis
- 5+ challenge problems included

### Quick Facts

| Topic | Files | Complexity Coverage |
|-------|-------|-------------------|
| Linked Lists | 8 | O(1) to O(n) |
| Sorting | 12 | O(n) to O(n²) |
| Hash Tables | 6 | O(1) average, O(n) worst |
| Heaps | 2 | O(log n) operations |
| BST | 2 | O(log n) balanced, O(n) unbalanced |

---

## 🚀 Track 2: Java Functional Programming

### What You'll Learn

Modern Java development patterns used by top tech companies:

- **5 Core Principles**: Immutability, Statelessness, Declarative, Concurrency-safe, Secure
- **40+ Functional Interfaces**: Predicate, Consumer, Supplier, Function, etc.
- **Lambda Expressions**: Concise anonymous functions
- **Stream API**: Filter-map-reduce pipelines
- **Combinator Pattern**: Building complex from simple functions
- **Real-world Applications**: Transaction processing, reporting, reactive code

### Why Functional Programming?

In the era of multi-core processors and distributed systems:
- 🔒 **Thread-safe by default** (no mutable shared state)
- 🎯 **Clear intent** (declarative vs. imperative)
- 🧪 **Easier to test** (pure functions, no side effects)
- ⚡ **Better performance** (lazy evaluation, parallelization)
- 📱 **Scalable** (safe for 1M+ concurrent operations)

### Runnable Examples

All examples are production-ready and extensively commented:

```
java-functional-programming/src/main/java/com/calvin/fp/
├── principles/
│   └── FunctionalPrinciples.java         # The 5 core principles
├── interfaces/
│   └── FunctionalInterfacesGuide.java    # Predicate, Consumer, Function, etc.
├── lambdas/
│   └── LambdaExpressions.java            # Lambdas, method references, streams
├── combinators/
│   └── CombinatorPattern.java            # Real-world validator examples
└── streams/
    └── AdvancedStreamPatterns.java       # Filter-map-reduce, partition, groupby
```

### Quick Example: Processing Financial Data

```java
// Imperative: Tell the computer HOW
List<Transaction> valid = new ArrayList<>();
for (Transaction t : transactions) {
    if (t.getAmount() > 1000 && !t.isSanctioned()) {
        valid.add(t);
    }
}

// Functional: Tell the computer WHAT
List<Transaction> valid = transactions.stream()
    .filter(t -> t.getAmount() > 1000)
    .filter(t -> !t.isSanctioned())
    .collect(Collectors.toList());
```

The functional version is:
- More readable (clear intent)
- Thread-safe (no shared mutable state)
- Optimizable (lazy evaluation)
- Parallelizable (add `.parallel()` for multi-core)

### Run Examples

```bash
cd java-functional-programming/src/main/java

# Run all principles
javac -d ../../../../target com/calvin/fp/principles/FunctionalPrinciples.java
java -cp ../../../../target com.calvin.fp.principles.FunctionalPrinciples

# Run interfaces guide
javac -d ../../../../target com/calvin/fp/interfaces/FunctionalInterfacesGuide.java
java -cp ../../../../target com.calvin.fp.interfaces.FunctionalInterfacesGuide

# (Similar for lambda, combinators, streams)
```

### Learning Path

**Week 1: Foundations**
- [ ] The 5 principles
- [ ] 8 core functional interfaces
- [ ] Lambda expressions basics

**Week 2: Streams**
- [ ] Filter-Map-Reduce pattern
- [ ] Terminal vs intermediate operations
- [ ] Lazy evaluation

**Week 3: Advanced**
- [ ] Combinators
- [ ] Function composition
- [ ] Parallel streams
- [ ] Real-world applications

### Key Resources

- [📖 Full Functional Programming Guide](./FUNCTIONAL_PROGRAMMING_ENHANCED.md)
- 5 production-ready example classes
- 40+ code snippets with explanations
- Real FinTech scenarios

### Production Use Cases

| Scenario | Pattern | Benefit |
|----------|---------|---------|
| Transaction filtering | Streams + Predicates | Declarative, efficient |
| Compliance checks | Combinators | Modular, testable |
| Data transformation | Map + FlatMap | No mutations, clear intent |
| Parallel processing | parallelStream | Thread-safe scaling |
| Error handling | Optional | No more NPE surprises |

---

## 🌱 Track 3: Spring Framework

### What You'll Learn

Building enterprise applications with Spring:

- **Spring Core**: Dependency Injection, Beans, AOP
- **Spring Boot**: Rapid application development
- **Spring Data**: Database access patterns
- **Spring Web**: REST APIs, Controllers, Routing
- **Spring Security**: Authentication and authorization
- **Reactive Programming**: Async/await with Project Reactor

### Why Spring?

Spring is the de facto standard for enterprise Java:
- 🏢 Used by 99% of enterprise Java shops
- 🚀 Rapid development (convention over configuration)
- 🔌 Integrates everything (DB, REST, Security, Messaging)
- 📖 Massive ecosystem and community
- 🧪 First-class testing support

### See Full Details

[📖 Go to Spring Framework Guide](./SPRING.md)

---

## 🎓 Recommended Learning Sequence

### For Interview Preparation
1. **Week 1-2**: Data Structures (arrays, linked lists, stacks, queues)
2. **Week 3-4**: Sorting algorithms (understand all 5 major sorters)
3. **Week 5-6**: Trees & Hash Tables
4. **Week 7**: Challenge problems
5. **Week 8**: System design with functional programming

### For Career as Java Developer
1. **Week 1-2**: Data Structures + Big O
2. **Parallel**: Functional Programming (modern Java essential skill)
3. **Week 3-4**: Spring Fundamentals
4. **Week 5-6**: Spring Boot + Spring Data
5. **Week 7-8**: Building small project with Spring

### For FinTech/High-Frequency Systems
1. **Week 1-2**: Big O + Hash Tables (instant lookups!)
2. **Week 3**: Functional Programming (immutability = correctness)
3. **Week 4**: Data Structures for special domains (segement trees, etc.)
4. **Week 5-6**: Reactive streams + Spring WebFlux
5. **Week 7-8**: Performance optimization with profiling

---

## 🛠️ Project Structure

```
Data-Structure-and-Algorithms-Java/
│
├── README.md                          # ← You are here
├── DATA_STRUCTURES.md                 # Track 1: Data structures & algorithms
├── FUNCTIONAL_PROGRAMMING_ENHANCED.md # Track 2: Modern Java patterns
├── SPRING.md                          # Track 3: Enterprise framework
│
├── BigO/                              # Complexity analysis
├── LinkedList/                        # Arrays of implementations
├── Stacks/                           # Stack + challenges
├── Queue/                            # Queues + challenges
├── binarySearchTree/                 # BST implementations
├── maxHeap/                          # Heap based data structures
├── Hashtable/                        # Hash tables + challenges
├── Sort/                             # 10+ sorting algorithms
├── binarySearch/                     # Binary search variations
│
└── java-functional-programming/      # ✨ New: Production examples
    └── src/main/java/com/calvin/fp/
        ├── principles/               # The 5 principles
        ├── interfaces/               # 40+ interfaces
        ├── lambdas/                 # Lambda expressions
        ├── combinators/             # Combinator pattern
        └── streams/                 # Advanced streams
```

---

## 📊 Algorithm Complexity Reference

### Time Complexities

| Algorithm | Best | Average | Worst | Space |
|-----------|------|---------|-------|-------|
| Linear Search | O(1) | O(n) | O(n) | O(1) |
| Binary Search | O(1) | O(log n) | O(log n) | O(log n) |
| Bubble Sort | O(n) | O(n²) | O(n²) | O(1) |
| Merge Sort | O(n log n) | O(n log n) | O(n log n) | O(n) |
| Quick Sort | O(n log n) | O(n log n) | O(n²) | O(log n) |
| Hash Table | O(1) | O(1) | O(n) | O(n) |
| BST | O(log n) | O(log n) | O(n) | O(n) |

### Data Structure Operations

| Structure | Insert | Delete | Search | Space |
|-----------|--------|--------|--------|-------|
| Array | O(n) | O(n) | O(n) | O(n) |
| LinkedList | O(1)* | O(1)* | O(n) | O(n) |
| Stack | O(1) | O(1) | O(n) | O(n) |
| Queue | O(1) | O(1) | O(n) | O(n) |
| BST | O(log n)** | O(log n)** | O(log n)** | O(n) |
| Hash Table | O(1)*** | O(1)*** | O(1)*** | O(n) |

*\*At known position  
\*\*If balanced  
\*\*\*Average case*

---

## 🚀 Next Steps

### Right Now
1. ✅ Clone the repo
2. ✅ Run first example
3. ✅ Choose your track

### This Week
- [ ] Complete fundamentals in your chosen track
- [ ] Run all example programs
- [ ] Take notes (handwriting = better retention)
- [ ] Implement one data structure from scratch

### This Month
- [ ] Complete all 3 tracks OR go deep in 1 track
- [ ] Solve all challenge problems
- [ ] Build a small project integrating concepts
- [ ] Explain one concept to someone else (teaches you)

### This Quarter
- [ ] Technical interview preparation (if needed)
- [ ] System design projects
- [ ] Contributing improvements to this repo
- [ ] Mentoring others

---

## 🤝 Contributing

Have improvements? Found a bug? Want to add examples?

1. Fork this repository
2. Create a feature branch (`git checkout -b feature/new-example`)
3. Add your code with comprehensive comments
4. Include complexity analysis in docstrings
5. Submit a pull request

### Contribution Guidelines

- Code must follow Java conventions
- Include inline comments explaining logic
- Add complexity analysis in comments
- All implementations must be tested
- Professional, readable code (imagine Principal reviewing it)

---

## 📖 Book Recommendations

- **"Introduction to Algorithms"** - Cormen, Leiserson, Rivest, Stein (The Bible)
- **"Java Concurrency in Practice"** - Goetz, et al. (Master threading + functional)
- **"Effective Java"** - Joshua Bloch (Best practices)
- **"Clean Code"** - Robert C. Martin (Readability matters)
- **"The Pragmatic Programmer"** - Hunt & Thomas (Think like an expert)

---

## 🎓 Certification Paths

This repo prepares you for:
- ✅ FAANG Technical Interviews
- ✅ Oracle Java Associate Programmer (OCA)
- ✅ Spring Certification Basics
- ✅ System Design Interviews
- ✅ Production Code Readiness

---

## 📞 Support & Questions

- GitHub Issues: Report bugs or ask questions
- Code Review: Submit PR for feedback
- Self-Learning: Each folder has detailed README files

---

## 📜 License

This repository is open-source and available for educational purposes.

---

## 🎯 Your Success

This repository is designed around **one principle**:

> **"Understanding > Memorization"**

Don't memorize Big O. Understand why O(n log n) exists.  
Don't memorize sorting algorithms. Understand divide-and-conquer.  
Don't memorize functional interfaces. Understand why they matter.

When you understand the WHY, you can solve any problem.

---

## ✨ Highlights

What makes this repository special:

- ✅ **Comprehensive**: All major data structures covered
- ✅ **Production-Ready**: Code you can use in real systems
- ✅ **Well-Commented**: Explain-it-like-I'm-8 level clarity
- ✅ **Runnable Examples**: Not just theory, executable code
- ✅ **Challenge Problems**: Test your understanding
- ✅ **Real-World Applications**: See where it's used
- ✅ **Modern Java**: Functional programming included
- ✅ **Enterprise Focus**: Spring integration for real jobs

---

## 🔗 Quick Links

| Resource | Link |
|----------|------|
| Data Structures | [📖 See Guide](./DATA_STRUCTURES.md) |
| Functional Programming | [🚀 See Guide](./FUNCTIONAL_PROGRAMMING_ENHANCED.md) |
| Spring Framework | [🌱 See Guide](./SPRING.md) |
| GitHub Repository | [🐙 Visit Repo](https://github.com/calvinlee999/-Data-Structure-and-Algorithms-Java) |
| Java Documentation | [📚 Oracle Docs](https://docs.oracle.com/en/java/) |

---

**Version:**  2.0 - Comprehensive Edition  
**Last Updated:** February 2026  
**Maintained By:** @calvinlee999  
**Status:** ✅ Production Ready

---

### 🙌 Thank You

Thank you for investing time in learning. Every expert was once a beginner.

**Now go build amazing things!** 🚀

