package users.utils;

import java.util.Random;

public class Rfc {

    private static Random rd = new Random();

    public static String generate(String paternalLastname, String maternalLastName, String firstName, String birthDate) {
        String date = birthDate;
        String lastNameP = paternalLastname.substring(0, 2);
        String lastNameM = maternalLastName.substring(0, 1);
        String name = String.valueOf(firstName.charAt(0));
        String year = date.substring(2, 4);
        String month = date.substring(5, 7);
        String day = date.substring(8, 10);

        String homoclave = generateHomoclave();

        return lastNameP + lastNameM + name + year + month + day + homoclave;
    }

    private static String generateHomoclave() {
        String homoclave;
        String alphabet = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
        int i = rd.nextInt(2);  // Randomly select 0 or 1
        // First character of the homoclave
        String char1;
        if (i == 0) {
            int digit = rd.nextInt(10);  // Randomly select a digit between 0 and 9
            char1 = String.valueOf(digit);
        } else {
            int c = rd.nextInt(27);  // Randomly select an alphabet character
            char1 = String.valueOf(alphabet.charAt(c));
        }

        // Second character of the homoclave
        int c = rd.nextInt(27);
        String char2 = String.valueOf(alphabet.charAt(c));
        while (char2.equals(char1)) {
            c = rd.nextInt(27);
            char2 = String.valueOf(alphabet.charAt(c));
        }

        // Third character of the homoclave
        int digit = rd.nextInt(10);
        String char3 = String.valueOf(digit);
        while (char3.equals(char1)) {
            digit = rd.nextInt(10);
            char3 = String.valueOf(digit);
        }

        homoclave = char1 + char2 + char3;

        return homoclave;
    }
}
