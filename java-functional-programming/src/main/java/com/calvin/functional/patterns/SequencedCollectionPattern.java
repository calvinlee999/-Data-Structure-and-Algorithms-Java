package com.calvin.functional.patterns;

import java.util.*;

/**
 * SEQUENCED COLLECTIONS PATTERN (Java 21+)
 * 
 * Think of sequenced collections like a line of people!
 * You can easily access first person, last person, or reverse the line!
 * Old way: complicated code. New way: simple methods!
 * 
 * Real-world analogy: Like a queue at a coffee shop - you can see who's first,
 * who's last, and imagine the line in reverse order!
 * 
 * @author FinTech Principal Software Engineer
 * @since Java 21
 */
public class SequencedCollectionPattern {

    /**
     * PATTERN 1: Basic Sequenced Operations
     * Access first/last elements easily
     */
    static class BasicSequencedExample {
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 1: Basic Sequenced Operations ===");
            System.out.println("Goal: Easy access to first/last elements\n");
            
            List<String> transactions = new ArrayList<>(List.of("TX001", "TX002", "TX003", "TX004", "TX005"));
            
            System.out.println("❌ OLD WAY:");
            System.out.println("  First: transactions.get(0)");
            System.out.println("  Last: transactions.get(transactions.size() - 1)");
            
            System.out.println("\n✅ NEW WAY:");
            System.out.println("  First: " + transactions.getFirst());
            System.out.println("  Last: " + transactions.getLast());
            
            // Add to first/last
            transactions.addFirst("TX000");  // Add at beginning
            transactions.addLast("TX006");   // Add at end
            
            System.out.println("\nAfter addFirst/addLast:");
            System.out.println("  " + transactions);
            
            // Remove first/last
            String removedFirst = transactions.removeFirst();
            String removedLast = transactions.removeLast();
            
            System.out.println("\nRemoved: " + removedFirst + ", " + removedLast);
            System.out.println("  Remaining: " + transactions);
            
            System.out.println("\n  Benefits: Cleaner, more readable code!");
        }
    }

    /**
     * PATTERN 2: Reversed Views
     * Get reversed view without copying
     */
    static class ReversedViewExample {
        
        record Transaction(String id, double amount) {}
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 2: Reversed Views ===");
            System.out.println("Goal: Reverse iteration without copying\n");
            
            List<Transaction> transactions = new ArrayList<>(List.of(
                new Transaction("TX001", 100.0),
                new Transaction("TX002", 200.0),
                new Transaction("TX003", 300.0),
                new Transaction("TX004", 400.0)
            ));
            
            System.out.println("Original order:");
            transactions.forEach(tx -> System.out.println("  " + tx.id + ": $" + tx.amount));
            
            System.out.println("\n❌ OLD WAY (Copy and reverse):");
            System.out.println("  List<T> reversed = new ArrayList<>(list);");
            System.out.println("  Collections.reverse(reversed);");
            
            System.out.println("\n✅ NEW WAY (Reversed view - no copy!):");
            List<Transaction> reversed = transactions.reversed();
            reversed.forEach(tx -> System.out.println("  " + tx.id + ": $" + tx.amount));
            
            System.out.println("\nModifying reversed view affects original:");
            reversed.addFirst(new Transaction("TX005", 500.0)); // Adds to END of original!
            System.out.println("  Original: " + transactions.getFirst().id + " to " + transactions.getLast().id);
            System.out.println("  Original last transaction: " + transactions.getLast().id + " ($" + transactions.getLast().amount + ")");
            
            System.out.println("\n  Benefits: Memory efficient, no copying!");
        }
    }

    /**
     * PATTERN 3: Transaction Queue (FIFO)
     * Process transactions first-in, first-out
     */
    static class TransactionQueueExample {
        
        static class TransactionQueue {
            private final List<String> queue = new ArrayList<>();
            
            public void enqueue(String transactionId) {
                queue.addLast(transactionId);  // Add to end
                System.out.println("  Enqueued: " + transactionId);
            }
            
            public String dequeue() {
                if (queue.isEmpty()) {
                    throw new NoSuchElementException("Queue empty");
                }
                String tx = queue.removeFirst();  // Remove from front
                System.out.println("  Dequeued: " + tx);
                return tx;
            }
            
            public String peekFirst() {
                return queue.getFirst();
            }
            
            public String peekLast() {
                return queue.getLast();
            }
            
            public boolean isEmpty() {
                return queue.isEmpty();
            }
            
            public int size() {
                return queue.size();
            }
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 3: Transaction Queue (FIFO) ===");
            System.out.println("Goal: First-In-First-Out processing\n");
            
            TransactionQueue queue = new TransactionQueue();
            
            System.out.println("Enqueueing transactions:");
            queue.enqueue("TX001");
            queue.enqueue("TX002");
            queue.enqueue("TX003");
            
            System.out.println("\nQueue state:");
            System.out.println("  First: " + queue.peekFirst());
            System.out.println("  Last: " + queue.peekLast());
            System.out.println("  Size: " + queue.size());
            
            System.out.println("\nProcessing queue:");
            while (!queue.isEmpty()) {
                queue.dequeue();
            }
            
            System.out.println("\n  Benefits: Clean FIFO implementation!");
        }
    }

    /**
     * PATTERN 4: Transaction Stack (LIFO)
     * Process transactions last-in, first-out
     */
    static class TransactionStackExample {
        
        static class TransactionStack {
            private final List<String> stack = new ArrayList<>();
            
            public void push(String transactionId) {
                stack.addLast(transactionId);  // Add to end
                System.out.println("  Pushed: " + transactionId);
            }
            
            public String pop() {
                if (stack.isEmpty()) {
                    throw new NoSuchElementException("Stack empty");
                }
                String tx = stack.removeLast();  // Remove from end (LIFO!)
                System.out.println("  Popped: " + tx);
                return tx;
            }
            
            public String peek() {
                return stack.getLast();
            }
            
            public boolean isEmpty() {
                return stack.isEmpty();
            }
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 4: Transaction Stack (LIFO) ===");
            System.out.println("Goal: Last-In-First-Out processing\n");
            
            TransactionStack stack = new TransactionStack();
            
            System.out.println("Pushing transactions:");
            stack.push("TX001");
            stack.push("TX002");
            stack.push("TX003");
            
            System.out.println("\nTop of stack: " + stack.peek());
            
            System.out.println("\nPopping (LIFO order):");
            while (!stack.isEmpty()) {
                stack.pop();
            }
            
            System.out.println("\n  Benefits: Natural stack operations!");
        }
    }

    /**
     * PATTERN 5: Circular Buffer Pattern
     * Fixed-size buffer with rotation
     */
    static class CircularBufferExample {
        
        record Transaction(String id, double amount) {}
        
        static class CircularBuffer {
            private final List<Transaction> buffer;
            private final int capacity;
            
            public CircularBuffer(int capacity) {
                this.capacity = capacity;
                this.buffer = new ArrayList<>(capacity);
            }
            
            public void add(Transaction tx) {
                if (buffer.size() >= capacity) {
                    buffer.removeFirst();  // Remove oldest
                }
                buffer.addLast(tx);      // Add newest
            }
            
            public Transaction getOldest() {
                return buffer.getFirst();
            }
            
            public Transaction getNewest() {
                return buffer.getLast();
            }
            
            public List<Transaction> getAll() {
                return List.copyOf(buffer);
            }
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 5: Circular Buffer ===");
            System.out.println("Goal: Fixed-size recent history\n");
            
            CircularBuffer recentTransactions = new CircularBuffer(3);
            
            System.out.println("Adding to circular buffer (capacity: 3):");
            for (int i = 1; i <= 5; i++) {
                Transaction tx = new Transaction("TX00" + i, i * 100.0);
                recentTransactions.add(tx);
                System.out.println("  Added: " + tx.id);
                
                List<Transaction> current = recentTransactions.getAll();
                System.out.println("    Buffer: " + current.stream()
                    .map(Transaction::id)
                    .toList());
            }
            
            System.out.println("\nFinal buffer:");
            System.out.println("  Oldest: " + recentTransactions.getOldest().id);
            System.out.println("  Newest: " + recentTransactions.getNewest().id);
            
            System.out.println("\n  Benefits: Automatic rotation of old data!");
        }
    }

    /**
     * PATTERN 6: Sliding Window Pattern
     * Process data in windows
     */
    static class SlidingWindowExample {
        
        record Price(double value, long timestamp) {}
        
        static double calculateMovingAverage(List<Price> prices, int windowSize) {
            if (prices.size() < windowSize) {
                return 0.0;
            }
            
            // Get last N prices
            List<Price> window = prices.reversed()
                .stream()
                .limit(windowSize)
                .toList()
                .reversed();  // Revert to chronological order
            
            return window.stream()
                .mapToDouble(Price::value)
                .average()
                .orElse(0.0);
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 6: Sliding Window ===");
            System.out.println("Goal: Moving average calculation\n");
            
            List<Price> prices = new ArrayList<>(List.of(
                new Price(100.0, 1000),
                new Price(102.0, 2000),
                new Price(98.0, 3000),
                new Price(105.0, 4000),
                new Price(103.0, 5000)
            ));
            
            int windowSize = 3;
            System.out.println("Calculating " + windowSize + "-period moving average:");
            
            for (int i = 0; i < prices.size(); i++) {
                List<Price> subset = prices.subList(0, i + 1);
                double avg = calculateMovingAverage(subset, windowSize);
                
                Price current = prices.get(i);
                System.out.println("  Price: $" + current.value + " | " +
                    "MA(" + windowSize + "): $" + String.format("%.2f", avg));
            }
            
            System.out.println("\n  Benefits: Easy window-based calculations!");
        }
    }

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║       SEQUENCED COLLECTIONS PATTERN (Java 21+)                ║");
        System.out.println("║  Easy access to first/last elements and reversed views        ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        
        BasicSequencedExample.demonstrate();
        ReversedViewExample.demonstrate();
        TransactionQueueExample.demonstrate();
        TransactionStackExample.demonstrate();
        CircularBufferExample.demonstrate();
        SlidingWindowExample.demonstrate();
        
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  KEY TAKEAWAY:                                                 ║");
        System.out.println("║  • getFirst()/getLast(): Access end elements                   ║");
        System.out.println("║  • addFirst()/addLast(): Insert at ends                        ║");
        System.out.println("║  • removeFirst()/removeLast(): Remove from ends                ║");  
        System.out.println("║  • reversed(): Get reversed view (no copy!)                    ║");
        System.out.println("║  • Use case: Queues, stacks, circular buffers, time series     ║");
        System.out.println("║  • Applies to: List, Deque, LinkedHashSet                      ║");
        System.out.println("║  • Introduced in: Java 21                                      ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
    }
}
