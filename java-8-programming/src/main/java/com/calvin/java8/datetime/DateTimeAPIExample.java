package com.calvin.java8.datetime;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

/**
 * Date-Time API (java.time) - Modern, Thread-Safe Date Handling
 * 
 * Replaces broken java.util.Date and Calendar:
 * - Immutable (thread-safe)
 * - Clear API (LocalDate, LocalDateTime, ZonedDateTime)
 * - Timezone-aware
 * - Fluent API
 * 
 * @author Calvin Lee - FinTech Principal Software Engineer
 */
public class DateTimeAPIExample {

    public static void main(String[] args) {
        System.out.println("=".repeat(80));
        System.out.println("JAVA 8 DATE-TIME API - MODERN DATE HANDLING");
        System.out.println("=".repeat(80));
        System.out.println();

        demonstrateLocalDate();
        demonstrateZonedDateTime();
        demonstrateBusinessLogic();
    }

    private static void demonstrateLocalDate() {
        System.out.println("1. LOCAL DATE (No Timezone)");
        System.out.println("-".repeat(80));

        LocalDate today = LocalDate.now();
        LocalDate specificDate = LocalDate.of(2026, 2, 13);
        
        System.out.println("   Today: " + today);
        System.out.println("   Specific date: " + specificDate);
        System.out.println("   Tomorrow: " + today.plusDays(1));
        System.out.println("   Last month: " + today.minusMonths(1));
        System.out.println("   Is leap year: " + today.isLeapYear());
        System.out.println();
    }

    private static void demonstrateZonedDateTime() {
        System.out.println("2. ZONED DATE TIME (Timezone-Aware)");
        System.out.println("-".repeat(80));

        // Transaction timestamp in UTC
        Instant txnTime = Instant.now();
        System.out.println("   Transaction time (UTC): " + txnTime);

        // Convert to different timezones
        ZonedDateTime nyTime = txnTime.atZone(ZoneId.of("America/New_York"));
        ZonedDateTime londonTime = txnTime.atZone(ZoneId.of("Europe/London"));
        ZonedDateTime tokyoTime = txnTime.atZone(ZoneId.of("Asia/Tokyo"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
        System.out.println("   New York: " + nyTime.format(formatter));
        System.out.println("   London: " + londonTime.format(formatter));
        System.out.println("   Tokyo: " + tokyoTime.format(formatter));
        System.out.println();
    }

    private static void demonstrateBusinessLogic() {
        System.out.println("3. BUSINESS LOGIC (Transaction Date Calculations)");
        System.out.println("-".repeat(80));

        // Find transactions in last 30 days
        LocalDate today = LocalDate.now();
        LocalDate thirtyDaysAgo = today.minusDays(30);
        System.out.println("   Transaction window: " + thirtyDaysAgo + " to " + today);

        // Calculate business days (exclude weekends)
        LocalDate startDate = LocalDate.of(2026, 2, 1);
        LocalDate endDate = LocalDate.of(2026, 2, 28);
        
        long businessDays = Stream.iterate(startDate, date -> date.plusDays(1))
                .limit(ChronoUnit.DAYS.between(startDate, endDate))
                .filter(date -> date.getDayOfWeek() != DayOfWeek.SATURDAY && 
                                date.getDayOfWeek() != DayOfWeek.SUNDAY)
                .count();
        
        System.out.println("   Business days in Feb 2026: " + businessDays);
        System.out.println();
    }
}
