package com.calvin.functional.patterns;

/**
 * SEALED INTERFACE PATTERN (Java 17+)
 * 
 * Think of sealed interfaces like a royal family tree!
 * Only family members (permitted classes) can inherit the throne - no outsiders allowed!
 * The compiler knows ALL possible children, enabling exhaustive checks!
 * 
 * Real-world analogy: Like a strict club membership - only invited members allowed.
 * The bouncer (compiler) has the complete list and checks everyone!
 * 
 * @author FinTech Principal Software Engineer
 * @since Java 17 (Sealed Classes standard feature)
 */
public class SealedInterfacePattern {

    /**
     * PATTERN 1: Payment Method ADT (Algebraic Data Type)
     * Define all possible payment types exhaustively
     */
    static class PaymentMethodExample {
        
        // Sealed interface - ONLY these implementations allowed!
        sealed interface PaymentMethod permits CreditCard, DebitCard, BankTransfer, DigitalWallet {}
        
        record CreditCard(String cardNumber, String cvv, String expiry) implements PaymentMethod {}
        record DebitCard(String cardNumber, String pin) implements PaymentMethod {}
        record BankTransfer(String accountNumber, String routingNumber) implements PaymentMethod {}
        record DigitalWallet(String walletId, String provider) implements PaymentMethod {}
        
        // Exhaustive pattern matching - compiler ensures all cases covered!
        static double getProcessingFee(PaymentMethod method) {
            return switch (method) {
                case CreditCard cc -> 2.9 + (0.3);  // $2.90 + 0.3%
                case DebitCard dc -> 1.5;           // Flat $1.50
                case BankTransfer bt -> 0.50;       // Cheap!
                case DigitalWallet dw -> 2.0;       // $2.00
                // No default needed - compiler knows all cases!
            };
        }
        
        static String getProviderName(PaymentMethod method) {
            return switch (method) {
                case CreditCard cc -> "Visa/Mastercard";
                case DebitCard dc -> "PIN Debit Network";
                case BankTransfer bt -> "ACH Network";
                case DigitalWallet dw -> dw.provider;
            };
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 1: Payment Method ADT ===");
            System.out.println("Goal: Define all possible payment types\n");
            
            System.out.println("OLD WAY: Interface + instanceof chains:");
            System.out.println("  Problem: Anyone can implement interface, no exhaustiveness!");
            
            System.out.println("\n✅ NEW WAY: Sealed interface + pattern matching:");
            
            PaymentMethod[] methods = {
                new CreditCard("4111-1111-1111-1111", "123", "12/25"),
                new DebitCard("5555-5555-5555-4444", "1234"),
                new BankTransfer("999999999", "021000021"),
                new DigitalWallet("user@paypal.com", "PayPal")
            };
            
            for (PaymentMethod method : methods) {
                double fee = getProcessingFee(method);
                String provider = getProviderName(method);
                System.out.println(method.getClass().getSimpleName() + ":");
                System.out.println("  Provider: " + provider);
                System.out.println("  Fee: $" + fee);
            }
            
            System.out.println("\n  Benefits: Compiler-checked exhaustiveness!");
        }
    }

    /**
     * PATTERN 2: Transaction Result ADT
     * Model success/failure states exhaustively
     */
    static class TransactionResultExample {
        
        sealed interface TransactionResult permits Success, Failure {}
        
        record Success(String transactionId, double amount, long timestamp) 
            implements TransactionResult {}
        
        sealed interface Failure extends TransactionResult 
            permits InsufficientFunds, InvalidCard, NetworkError, FraudDetected {}
        
        record InsufficientFunds(double available, double required) implements Failure {}
        record InvalidCard(String reason) implements Failure {}
        record NetworkError(String message, int retryCount) implements Failure {}
        record FraudDetected(String riskScore, String reason) implements Failure {}
        
        static String formatResult(TransactionResult result) {
            return switch (result) {
                case Success s -> 
                    "✅ Success: " + s.transactionId + " ($" + s.amount + ")";
                    
                case InsufficientFunds isf -> 
                    "❌ Insufficient funds: Need $" + isf.required + ", have $" + isf.available;
                    
                case InvalidCard ic -> 
                    "❌ Invalid card: " + ic.reason;
                    
                case NetworkError ne -> 
                    "⚠️ Network error: " + ne.message + " (retries: " + ne.retryCount + ")";
                    
                case FraudDetected fd -> 
                    "🚨 Fraud detected: " + fd.reason + " (risk: " + fd.riskScore + ")";
            };
        }
        
        static boolean shouldRetry(TransactionResult result) {
            return switch (result) {
                case NetworkError ne -> ne.retryCount < 3;
                default -> false;
            };
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 2: Transaction Result ADT ===");
            System.out.println("Goal: Model all possible outcomes\n");
            
            TransactionResult[] results = {
                new Success("TXN001", 1000.0, System.currentTimeMillis()),
                new InsufficientFunds(50.0, 100.0),
                new InvalidCard("Expired card"),
                new NetworkError("Gateway timeout", 1),
                new FraudDetected("HIGH", "Unusual location")
            };
            
            System.out.println("Processing transaction results:");
            for (TransactionResult result : results) {
                System.out.println(formatResult(result));
                if (shouldRetry(result)) {
                    System.out.println("  → Will retry");
                }
            }
            
            System.out.println("\n  Benefits: Type-safe error handling!");
        }
    }

    /**
     * PATTERN 3: Account Type Hierarchy
     * Model account types with different behaviors
     */
    static class AccountTypeExample {
        
        sealed interface Account permits CheckingAccount, SavingsAccount, InvestmentAccount {}
        
        record CheckingAccount(String id, double balance, double overdraftLimit) 
            implements Account {
            public boolean canWithdraw(double amount) {
                return balance + overdraftLimit >= amount;
            }
        }
        
        record SavingsAccount(String id, double balance, double interestRate) 
            implements Account {
            public double calculateMonthlyInterest() {
                return balance * (interestRate / 12);
            }
        }
        
        record InvestmentAccount(String id, double balance, String portfolioType) 
            implements Account {
            public double getRiskLevel() {
                return switch (portfolioType) {
                    case "Conservative" -> 0.2;
                    case "Moderate" -> 0.5;
                    case "Aggressive" -> 0.8;
                    default -> 0.0;
                };
            }
        }
        
        static String getAccountSummary(Account account) {
            return switch (account) {
                case CheckingAccount ca ->
                    "Checking: $" + ca.balance + " (overdraft: $" + ca.overdraftLimit + ")";
                case SavingsAccount sa ->
                    "Savings: $" + sa.balance + " (interest: " + (sa.interestRate * 100) + "%)";
                case InvestmentAccount ia ->
                    "Investment: $" + ia.balance + " (" + ia.portfolioType + " - risk: " + 
                    (ia.getRiskLevel() * 100) + "%)";
            };
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 3: Account Type Hierarchy ===");
            System.out.println("Goal: Different account types with unique behaviors\n");
            
            Account[] accounts = {
                new CheckingAccount("CHK001", 1000.0, 500.0),
                new SavingsAccount("SAV001", 5000.0, 0.02),
                new InvestmentAccount("INV001", 10000.0, "Moderate")
            };
            
            System.out.println("Account summaries:");
            for (Account account : accounts) {
                System.out.println("  " + getAccountSummary(account));
            }
            
            System.out.println("\n  Benefits: Type-specific behavior with exhaustive matching!");
        }
    }

    /**
     * PATTERN 4: Order State Machine
     * Model order lifecycle states
     */
    static class OrderStateExample {
        
        sealed interface OrderState permits Pending, Processing, Shipped, Delivered, Cancelled {}
        
        record Pending(long createdAt) implements OrderState {}
        record Processing(long startedAt, String warehouse) implements OrderState {}
        record Shipped(long shippedAt, String trackingNumber) implements OrderState {}
        record Delivered(long deliveredAt, String signedBy) implements OrderState {}
        record Cancelled(long cancelledAt, String reason) implements OrderState {}
        
        static boolean canCancel(OrderState state) {
            return switch (state) {
                case Pending p -> true;
                case Processing pr -> true;
                case Shipped s -> false;  // Too late!
                case Delivered d -> false;
                case Cancelled c -> false;
            };
        }
        
        static String getStatus(OrderState state) {
            return switch (state) {
                case Pending p -> "⏳ Pending (created at " + p.createdAt + ")";
                case Processing pr -> "📦 Processing at " + pr.warehouse;
                case Shipped s -> "🚚 Shipped (tracking: " + s.trackingNumber + ")";
                case Delivered d -> "✅ Delivered (signed: " + d.signedBy + ")";
                case Cancelled c -> "❌ Cancelled: " + c.reason;
            };
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 4: Order State Machine ===");
            System.out.println("Goal: Model order lifecycle\n");
            
            OrderState[] states = {
                new Pending(1000L),
                new Processing(2000L, "Warehouse A"),
                new Shipped(3000L, "TRACK123456"),
                new Delivered(4000L, "John Doe"),
                new Cancelled(5000L, "Customer request")
            };
            
            System.out.println("Order states:");
            for (OrderState state : states) {
                System.out.println(getStatus(state));
                System.out.println("  Can cancel: " + canCancel(state));
            }
            
            System.out.println("\n  Benefits: Clear state transitions!");
        }
    }

    /**
     * PATTERN 5: Notification Channel ADT
     * Model different notification methods
     */
    static class NotificationExample {
        
        sealed interface Notification permits EmailNotification, SMSNotification, PushNotification {}
        
        record EmailNotification(String to, String subject, String body) implements Notification {}
        record SMSNotification(String phoneNumber, String message) implements Notification {}
        record PushNotification(String deviceToken, String title, String message) implements Notification {}
        
        static double getCost(Notification notification) {
            return switch (notification) {
                case EmailNotification email -> 0.001;  // Very cheap
                case SMSNotification sms -> 0.05;       // Moderate
                case PushNotification push -> 0.0;      // Free!
            };
        }
        
        static int getExpectedDeliverySeconds(Notification notification) {
            return switch (notification) {
                case EmailNotification email -> 60;     // ~1 minute
                case SMSNotification sms -> 5;          // Very fast
                case PushNotification push -> 1;        // Instant
            };
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 5: Notification Channel ADT ===");
            System.out.println("Goal: Different notification methods\n");
            
            Notification[] notifications = {
                new EmailNotification("user@example.com", "Payment Confirm", "Your payment succeeded"),
                new SMSNotification("+15551234567", "Payment of $100 confirmed"),
                new PushNotification("device-token-abc123", "Payment", "Transaction complete")
            };
            
            System.out.println("Notification comparison:");
            for (Notification notif : notifications) {
                double cost = getCost(notif);
                int delay = getExpectedDeliverySeconds(notif);
                System.out.println(notif.getClass().getSimpleName() + ":");
                System.out.println("  Cost: $" + cost);
                System.out.println("  Delivery time: ~" + delay + "s");
            }
            
            System.out.println("\n  Benefits: Exhaustive channel handling!");
        }
    }

    /**
     * PATTERN 6: Expression ADT (Calculation Engine)
     * Build type-safe expression trees
     */
    static class ExpressionExample {
        
        sealed interface Expr permits Num, Add, Multiply, Divide {}
        
        record Num(double value) implements Expr {}
        record Add(Expr left, Expr right) implements Expr {}
        record Multiply(Expr left, Expr right) implements Expr {}
        record Divide(Expr left, Expr right) implements Expr {}
        
        static double eval(Expr expr) {
            return switch (expr) {
                case Num n -> n.value;
                case Add a -> eval(a.left) + eval(a.right);
                case Multiply m -> eval(m.left) * eval(m.right);
                case Divide d -> eval(d.left) / eval(d.right);
            };
        }
        
        static String prettyPrint(Expr expr) {
            return switch (expr) {
                case Num n -> String.valueOf(n.value);
                case Add a -> "(" + prettyPrint(a.left) + " + " + prettyPrint(a.right) + ")";
                case Multiply m -> "(" + prettyPrint(m.left) + " * " + prettyPrint(m.right) + ")";
                case Divide d -> "(" + prettyPrint(d.left) + " / " + prettyPrint(d.right) + ")";
            };
        }
        
        public static void demonstrate() {
            System.out.println("\n=== PATTERN 6: Expression ADT ===");
            System.out.println("Goal: Type-safe calculation engine\n");
            
            // (10 + 5) * (20 / 4)
            Expr expression = new Multiply(
                new Add(new Num(10), new Num(5)),
                new Divide(new Num(20), new Num(4))
            );
            
            System.out.println("Expression: " + prettyPrint(expression));
            System.out.println("Result: " + eval(expression));
            
            System.out.println("\n  Benefits: Type-safe expression trees!");
        }
    }

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║         SEALED INTERFACE PATTERN (Java 17+)                   ║");
        System.out.println("║  Exhaustive type hierarchies with compiler checking           ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        
        PaymentMethodExample.demonstrate();
        TransactionResultExample.demonstrate();
        AccountTypeExample.demonstrate();
        OrderStateExample.demonstrate();
        NotificationExample.demonstrate();
        ExpressionExample.demonstrate();
        
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  KEY TAKEAWAY:                                                 ║");
        System.out.println("║  • sealed: Only permitted classes can extend/implement         ║");
        System.out.println("║  • Compiler knows ALL possible subtypes                        ║");
        System.out.println("║  • Switch expressions need NO default case                     ║");
        System.out.println("║  • Perfect for: ADTs, state machines, payment types            ║");
        System.out.println("║  • Combines with: Records, pattern matching                    ║");
        System.out.println("║  • Introduced in: Java 17 (standard feature)                   ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
    }
}
