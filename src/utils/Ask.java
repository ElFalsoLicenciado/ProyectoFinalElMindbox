package utils;

import java.util.Scanner;

public class Ask {
    private static final Scanner scanner = new Scanner(System.in);

    public static int forInt(String message) {
        System.out.print("Enter " + message + ": \n");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next(); // clear invalid input
            System.out.print("Enter " + message + ": ");
        }
        int result = scanner.nextInt();
        scanner.nextLine(); // consume the newline
        return result;
    }

    public static double forDouble(String message) {
        System.out.print("Enter " + message + ": ");
        while (!scanner.hasNextDouble()) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.next(); // clear invalid input
            System.out.print("Enter " + message + ": \n");
        }
        double result = scanner.nextDouble();
        scanner.nextLine(); // consume the newline
        return result;
    }

    public static String forString(String message) {
        System.out.print("Enter " + message + ": \n");
        String result = scanner.nextLine();
        return result;
    }
}
