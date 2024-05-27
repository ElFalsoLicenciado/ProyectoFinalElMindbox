package users.utils;

import java.util.Random;

public class Curp {
    private static Random ran = new Random();

    public static String generate(String paternalLastName, String maternalLastName, String firstName, String birthDate,
                                  Gender gender, Country country) {
        String date = birthDate;
        String pLastName = paternalLastName.substring(0, 2);
        String mLastName = maternalLastName.substring(0, 1);
        String name = String.valueOf(firstName.charAt(0));
        String year = date.substring(8, 10);
        String month = date.substring(0, 2);
        String day = date.substring(3, 5);

        String sex = "";
        if (gender == Gender.MALE)
            sex = "H";
        else
            sex = "M";

        String consonantes = "";
        for (int i = 0; i < paternalLastName.length(); i++) {
            char c = paternalLastName.charAt(i);
            if (c != 'A' && c != 'E' && c != 'I' && c != 'O' && c != 'U') {
                consonantes += String.valueOf(c);
                break;
            }
        }
        for (int i = 0; i < maternalLastName.length(); i++) {
            char c = maternalLastName.charAt(i);
            if (c != 'A' && c != 'E' && c != 'I' && c != 'O' && c != 'U') {
                consonantes += String.valueOf(c);
                break;
            }
        }
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (c != 'A' && c != 'E' && c != 'I' && c != 'O' && c != 'U') {
                consonantes += String.valueOf(c);
                break;
            }
        }

        String federationEntity = FederationEntity.getFederationEntity(country);

        int n1 = ran.nextInt(10);
        int n2 = ran.nextInt(10);
        return pLastName + mLastName + name + year + month + day + sex + federationEntity + consonantes + n1 + n2;
    }
}
