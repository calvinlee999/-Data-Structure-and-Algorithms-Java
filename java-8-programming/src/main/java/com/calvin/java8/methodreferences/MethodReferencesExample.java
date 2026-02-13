package com.calvin.java8.methodreferences;

import com.calvin.java8.models.Payment;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import java.util.function.*;

/**
 * Method References - Shorthand for Lambdas
 * 4 Types: Static, Instance (bound), Instance (unbound), Constructor
 * 
 * @author Calvin Lee - FinTech Principal Software Engineer
 */
public class MethodReferencesExample {

    public static void main(String[] args) {
        System.out.println("=".repeat(80));
        System.out.println("JAVA 8 METHOD REFERENCES - LAMBDA SHORTHAND");
        System.out.println("=".repeat(80));
        System.out.println();

        List<Payment> payments = createPayments();

        // Type 1: Static Method Reference
        System.out.println("1. STATIC METHOD REFERENCE (ClassName::staticMethod)");
        System.out.println("   Lambda:    payments.stream().map(p -> Math.abs(p.getAmount().intValue()))");
        System.out.println("   Reference: payments.stream().map(Payment::getAmount).map(BigDecimal::intValue)");
        System.out.println();

        // Type 2: Instance Method Reference (bound)
        System.out.println("2. INSTANCE METHOD REFERENCE - BOUND (instance::method)");
        payments.forEach(System.out::println);  // Cleaner than p -> System.out.println(p)
        System.out.println();

        // Type 3: Instance Method Reference (unbound)
        System.out.println("3. INSTANCE METHOD REFERENCE - UNBOUND (Class::instanceMethod)");
        long approved = payments.stream().filter(Payment::isApproved).count();
        System.out.println("   Approved payments: " + approved);
        System.out.println();

        // Type 4: Constructor Reference
        System.out.println("4. CONSTRUCTOR REFERENCE (Class::new)");
        Supplier<List<Payment>> listFactory = ArrayList::new;
        List<Payment> newList = listFactory.get();
        System.out.println("   Created new list: " + newList.getClass().getSimpleName());
        System.out.println();
    }

    private static List<Payment> createPayments() {
        return Arrays.asList(
            new Payment("P1", "C1", new BigDecimal("1000"), "USD", Payment.PaymentStatus.APPROVED, Instant.now(), "M1"),
            new Payment("P2", "C2", new BigDecimal("2000"), "USD", Payment.PaymentStatus.PENDING, Instant.now(), "M2")
        );
    }
}
