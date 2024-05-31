package utils;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class Date {

    public static String askForDate(String desc) {
        System.out.printf("\nEnter the date for %s\n", desc);
        int year = getYear();
        int month = getMonth();
        int dayOfMonth = getDay(month);
        LocalDate date = LocalDate.of(year, month, dayOfMonth);
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(pattern);
    }

    public static String askForDate(int year, int month, int dayOfMonth) {
        LocalDate date = LocalDate.of(year, month, dayOfMonth);
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(pattern);
    }

    private static int getYear() {
        int year = Ask.forInt("the year");
        while (!validYear(year)) {
            System.out.println("Invalid year entered, please try again.");
            year = Ask.forInt("the year");
        }
        return year;
    }

    private static boolean validYear(int year) {
        return year > 1950 && year <= 2024;
    }

    private static int getMonth() {
        int month = Ask.forInt("the month");
        while (!validMonth(month)) {
            System.out.println("Invalid month entered, please try again.");
            month = Ask.forInt("the month");
        }
        return month;
    }

    private static boolean validMonth(int month) {
        return month > 0 && month <= 12;
    }

    public static int getDay(int month) {
        int day;
        while (true) {
            day = Ask.forInt("the day");
            if (validDay(month, day))
                return day;
            else
                System.out.println("Invalid day entered, please try again.");
        }
    }

    private static boolean validDay(int month, int day) {
        if (day > 0 && day <= 31) {
            if (day <= Month.of(month).maxLength())
                return true;
            else {
                System.out.println("The entered day is valid, but not within the month's range.");
                System.out.printf("\nThe maximum day for the month %s is: %d", Month.of(month), Month.of(month).maxLength());
            }
        } else
            System.out.println("Invalid day number entered.");
        return false;
    }
}
