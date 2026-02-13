package com.calvin.functional.patterns;

/**
 * RECORD PATTERN MATCHING (Java 19+ Preview, Java 21 Standard)
 * 
 * Think of record patterns like opening a gift box!
 * Instead of opening it slowly to get each item, you unpack everything at once!
 * 
 * Real-world analogy: Like unpacking a burger - you see bun, patty, cheese,
 * lettuce all at once, not item by item!
 * 
 * @author FinTech Principal Software Engineer
 * @since Java 19 (Preview), Java 21 (Standard)
 */
public class RecordPatternPattern {

    /**
     * PATTERN 1: Simple Record Deconstruction
     * Extract record fields directly in pattern matching
     */
    static class SimpleDeconstructionExample {
        
        record Point(int x, int y) {}
        
        static String describe(Object obj) {
            return switch (obj) {
                case Point(int x, int y) -> "Point at (" + x + ", " + y + ")";
                case String s -> "String: " + s;
                case Integer i -> "Integer: " + i;
                default -> "Unknown";
            };
        }
        
        static boolean isOrigin(Object obj) {
            return switch (obj) {
                case Point(int x, int y) when x == 0 && y == 0 -> true;
                default -> false;
            };
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 1: Simple Record Deconstruction ===");
            System.out.println("Goal: Extract record fields directly\n");
            
            System.out.println("❌ OLD WAY (Manual field access):");
            Point p = new Point(5, 10);
            System.out.println("  if (obj instanceof Point) {");
            System.out.println("      Point point = (Point) obj;");
            System.out.println("      int x = point.x();");
            System.out.println("      int y = point.y();");
            System.out.println("  }");
            
            System.out.println("\n✅ NEW WAY (Pattern matching):");
            System.out.println("  case Point(int x, int y) -> ...");
            
            Object[] objects = { new Point(5, 10), new Point(0, 0), "Hello", 42 };
            System.out.println("\nTesting objects:");
            for (Object obj : objects) {
                System.out.println("  " + describe(obj) + " | Origin: " + isOrigin(obj));
            }
            
            System.out.println("\n  Benefits: Concise field extraction!");
        }
    }

    /**
     * PATTERN 2: Nested Record Patterns
     * Deconstruct nested records in one pattern
     */
    static class NestedPatternsExample {
        
        record Address(String street, String city, String zipCode) {}
        record Customer(String name, Address address, double creditLimit) {}
        record Transaction(String id, Customer customer, double amount) {}
        
        static boolean isHighValueTransaction(Transaction tx) {
            return switch (tx) {
                case Transaction(
                    String id, 
                    Customer(String name, Address(String street, String city, String zip), double limit),
                    double amount
                ) when amount > limit -> true;
                default -> false;
            };
        }
        
        static String getCityOfTransaction(Transaction tx) {
            return switch (tx) {
                case Transaction(var id, Customer(var name, Address(var street, String city, var zip), var limit), var amount) 
                    -> city;
            };
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 2: Nested Record Patterns ===");
            System.out.println("Goal: Deconstruct nested records directly\n");
            
            Transaction tx = new Transaction(
                "TX001",
                new Customer(
                    "Alice Johnson",
                    new Address("123 Main St", "New York", "10001"),
                    5000.0
                ),
                6000.0
            );
            
            System.out.println("Transaction: " + tx.id);
            System.out.println("Customer: " + tx.customer.name);
            System.out.println("City: " + getCityOfTransaction(tx));
            System.out.println("Amount: $" + tx.amount);
            System.out.println("Credit limit: $" + tx.customer.creditLimit);
            System.out.println("Over limit: " + isHighValueTransaction(tx));
            
            System.out.println("\n  Benefits: Deep nesting extraction in one pattern!");
        }
    }

    /**
     * PATTERN 3: Payment Processing with Patterns
     * Deconstruct payment data for validation
     */
    static class PaymentProcessingExample {
        
        record CreditCard(String number, String cvv, String expiry) {}
        record BillingInfo(String name, String email, CreditCard card) {}
        record PaymentRequest(String orderId, BillingInfo billing, double amount) {}
        
        static String validatePayment(PaymentRequest request) {
            return switch (request) {
                case PaymentRequest(
                    String orderId,
                    BillingInfo(String name, var email, CreditCard(String number, String cvv, String expiry)),
                    double amount
                ) when amount <= 0 -> "❌ Invalid amount";
                
                case PaymentRequest(
                    var id,
                    BillingInfo(String name, var email, CreditCard(String number, String cvv, String expiry)),
                    var amt
                ) when name == null || name.isEmpty() -> "❌ Name required";
                
                case PaymentRequest(
                    var id,
                    BillingInfo(var name, var email, CreditCard(String number, String cvv, String expiry)),
                    var amt
                ) when !expiry.matches("\\d{2}/\\d{2}") -> "❌ Invalid expiry format";
                
                case PaymentRequest(var id, var billing, var amt) -> 
                    "✅ Valid payment: $" + amt;
            };
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 3: Payment Validation ===");
            System.out.println("Goal: Validate nested payment data\n");
            
            PaymentRequest[] requests = {
                new PaymentRequest("ORD001", 
                    new BillingInfo("Alice", "alice@example.com", 
                        new CreditCard("4111-1111-1111-1111", "123", "12/25")), 
                    100.0),
                new PaymentRequest("ORD002", 
                    new BillingInfo("", "bob@example.com", 
                        new CreditCard("5555-5555-5555-4444", "456", "06/24")), 
                    50.0),
                new PaymentRequest("ORD003", 
                    new BillingInfo("Charlie", "charlie@example.com", 
                        new CreditCard("3782-822463-10005", "789", "invalid")), 
                    75.0)
            };
            
            System.out.println("Validating payments:");
            for (PaymentRequest req : requests) {
                System.out.println("  " + req.orderId + ": " + validatePayment(req));
            }
            
            System.out.println("\n  Benefits: Complex validation in pattern matching!");
        }
    }

    /**
     * PATTERN 4: Geometric Shapes with Patterns
     * Calculate properties using pattern matching
     */
    static class GeometryExample {
        
        sealed interface Shape permits Circle, Rectangle, Triangle {}
        record Circle(double radius) implements Shape {}
        record Rectangle(double width, double height) implements Shape {}
        record Triangle(double base, double height) implements Shape {}
        
        static double area(Shape shape) {
            return switch (shape) {
                case Circle(double r) -> Math.PI * r * r;
                case Rectangle(double w, double h) -> w * h;
                case Triangle(double b, double h) -> 0.5 * b * h;
            };
        }
        
        static String classify(Shape shape) {
            return switch (shape) {
                case Circle(double r) when r < 5 -> "Small circle";
                case Circle(double r) when r < 10 -> "Medium circle";
                case Circle(double r) -> "Large circle";
                case Rectangle(double w, double h) when w == h -> "Square";
                case Rectangle(double w, double h) -> "Rectangle";
                case Triangle(double b, double h) -> "Triangle";
            };
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 4: Geometric Shapes ===");
            System.out.println("Goal: Calculate using pattern matching\n");
            
            Shape[] shapes = {
                new Circle(3.0),
                new Circle(7.0),
                new Circle(12.0),
                new Rectangle(5.0, 10.0),
                new Rectangle(6.0, 6.0),
                new Triangle(8.0, 4.0)
            };
            
            System.out.println("Shape analysis:");
            for (Shape shape : shapes) {
                System.out.println("  " + classify(shape) + " - Area: " + 
                    String.format("%.2f", area(shape)));
            }
            
            System.out.println("\n  Benefits: Elegant geometry calculations!");
        }
    }

    /**
     * PATTERN 5: Transaction Analysis
     * Analyze transaction patterns
     */
    static class TransactionAnalysisExample {
        
        record Merchant(String name, String category) {}
        record Transaction(String id, Merchant merchant, double amount, String status) {}
        record TransactionBatch(String batchId, Transaction[] transactions, long timestamp) {}
        
        static String analyzeBatch(TransactionBatch batch) {
            StringBuilder analysis = new StringBuilder();
            
            for (Transaction tx : batch.transactions) {
                String result = switch (tx) {
                    case Transaction(var id, Merchant(String name, "FOOD"), double amt, var status) 
                        when amt > 100 -> "🍽️ Large food expense: $" + amt;
                    case Transaction(var id, Merchant(String name, "TRAVEL"), double amt, var status) 
                        -> "✈️ Travel expense: $" + amt;
                    case Transaction(var id, Merchant(String name, var cat), double amt, "FAILED") 
                        -> "❌ Failed: " + name + " ($" + amt + ")";
                    case Transaction(var id, var merchant, double amt, var status) 
                        when amt > 1000 -> "⚠️ High value: $" + amt;
                    default -> "✅ Normal transaction";
                };
                
                analysis.append("\n  ").append(tx.id).append(": ").append(result);
            }
            
            return analysis.toString();
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 5: Transaction Analysis ===");
            System.out.println("Goal: Analyze transaction patterns\n");
            
            TransactionBatch batch = new TransactionBatch("BATCH001", new Transaction[] {
                new Transaction("TX001", new Merchant("Whole Foods", "FOOD"), 150.0, "COMPLETED"),
                new Transaction("TX002", new Merchant("Delta Airlines", "TRAVEL"), 500.0, "COMPLETED"),
                new Transaction("TX003", new Merchant("Amazon", "SHOPPING"), 50.0, "FAILED"),
                new Transaction("TX004", new Merchant("Best Buy", "ELECTRONICS"), 1500.0, "COMPLETED")
            }, System.currentTimeMillis());
            
            System.out.println("Batch analysis: " + batch.batchId);
            System.out.println(analyzeBatch(batch));
            
            System.out.println("\n  Benefits: Flexible transaction categorization!");
        }
    }

    /**
     * PATTERN 6: Optional Unwrapping
     * Deconstruct Optional values
     */
    static class OptionalUnwrappingExample {
        
        record Account(String id, double balance) {}
        
        static String formatAccount(Object obj) {
            return switch (obj) {
                case Account(String id, double bal) when bal > 1000 -> 
                    "💰 Premium: " + id + " ($" + bal + ")";
                case Account(String id, double bal) when bal > 0 -> 
                    "Standard: " + id + " ($" + bal + ")";
                case Account(String id, double bal) -> 
                    "⚠️ Overdrawn: " + id + " ($" + bal + ")";
                case null -> "No account";
                default -> "Unknown: " + obj;
            };
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 6: Null-Safe Patterns ===");
            System.out.println("Goal: Handle null safely in patterns\n");
            
            Object[] accounts = {
                new Account("ACC001", 5000.0),
                new Account("ACC002", 500.0),
                new Account("ACC003", -100.0),
                null,
                "InvalidData"
            };
            
            System.out.println("Account classification:");
            for (Object acc : accounts) {
                System.out.println("  " + formatAccount(acc));
            }
            
            System.out.println("\n  Benefits: Null-safe pattern matching!");
        }
    }

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║      RECORD PATTERN MATCHING (Java 19+/21 Standard)           ║");
        System.out.println("║  Deconstruct records directly in pattern matching             ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        
        SimpleDeconstructionExample.demonstrate();
        NestedPatternsExample.demonstrate();
        PaymentProcessingExample.demonstrate();
        GeometryExample.demonstrate();
        TransactionAnalysisExample.demonstrate();
        OptionalUnwrappingExample.demonstrate();
        
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  KEY TAKEAWAY:                                                 ║");
        System.out.println("║  • Pattern matching: Extract record fields directly           ║");
        System.out.println("║  • Nested patterns: Deconstruct deep hierarchies               ║");
        System.out.println("║  • Guard clauses: when keyword for conditions                  ║");
        System.out.println("║  • Null-safe: Handle null explicitly in patterns               ║");
        System.out.println("║  • Combines with: sealed interfaces, switch expressions        ║");
        System.out.println("║  • Introduced in: Java 19 (Preview), Java 21 (Standard)        ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
    }
}
