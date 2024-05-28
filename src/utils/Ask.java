package utils;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Ask {

    static Scanner sc = new Scanner(System.in);

    public static String forString(String prompt) {
        String answer = "";
        boolean correctData = false;
        while (!correctData) {
            try {
                print(prompt);
                answer = sc.next();
                correctData = true;
            } catch (Exception error) {
                System.out.println("An error occurred, please try again.");
                sc.nextLine();
            }
        }
        return answer;
    }

    public static double forDouble(String prompt) {
        double answer = 0;
        boolean correctData = false;
        while (!correctData) {
            try {
                print(prompt);
                answer = sc.nextDouble();
                correctData = true;
            } catch (Exception error) {
                System.out.println("An error occurred, please try again.");
                sc.nextLine();
            }
        }
        return answer;
    }

    public static int forInt(String prompt) {
        int answer = 0;
        boolean correctData = false;
        while (!correctData) {
            try {
                print(prompt);
                answer = sc.nextInt();
                correctData = true;
            } catch (Exception error) {
                System.out.println("An error occurred, please try again.");
                sc.nextLine();
            }
        }
        return answer;
    }

    public static long forLong(String prompt) {
        long answer = 0;
        boolean correctData = false;
        while (!correctData) {
            try {
                print(prompt);
                answer = sc.nextLong();
                correctData = true;
            } catch (Exception error) {
                System.out.println("An error occurred, please try again.");
                sc.nextLine();
            }
        }
        return answer;
    }

    public static boolean forBoolean(String prompt) {
        int answer = 0;
        boolean correctData = false;
        while (!correctData) {
            try {
                System.out.printf("IS %s?%n", prompt);
                System.out.println("1. Yes");
                System.out.println("2. No");
                answer = sc.nextInt();
                correctData = true;
            } catch (Exception error) {
                System.out.println("An error occurred, please try again.");
                sc.nextLine();
            }
        }
        return answer == 1;
    }

    private static void print(String prompt) {
        System.out.printf("%nEnter %s: ", prompt);
    }
}
