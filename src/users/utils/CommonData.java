package users.utils;

import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import users.*;
import utils.*;

public class CommonData {

    public static List<String> collectCommonData(Role role) {
        List<String> commonData = new ArrayList<>();

        String roleType = switch (role) {
            case STUDENT -> "S T U D E N T";
            case TEACHER -> "T E A C H E R";
            case COORDINATOR -> "C O O R D I N A T O R";
        };

        System.out.println(String.format("\n ADDING %s ", roleType));

        String firstName = Ask.forString("the first name");
        String lastName = Ask.forString("the last name");
        String birthYear = Ask.forString("the birth year");
        String address = Ask.forString("the address");

        FederativeEntity state = chooseState();
        String stateString = state.toString();

        String city = Ask.forString("the city");
        String username = generateUsername();
        String password = Ask.forString("the password");

        String gender = String.valueOf(chooseGender());

        String birthDate = askForDate("birth date");
        String phoneNumber = generatePhoneNumber();

        commonData.addAll(Arrays.asList(firstName, lastName, birthDate, address, stateString, city, username, password, gender, phoneNumber));

        return commonData;
    }

    public static FederativeEntity validState(String input) {
        return switch (input.toUpperCase()) {
            case "AGUASCALIENTES" -> FederativeEntity.AGUASCALIENTES;
            case "BAJA CALIFORNIA" -> FederativeEntity.BAJA_CALIFORNIA;
            case "BAJA CALIFORNIA SUR" -> FederativeEntity.BAJA_CALIFORNIA_SUR;
            case "CAMPECHE" -> FederativeEntity.CAMPECHE;
            case "CHIAPAS" -> FederativeEntity.CHIAPAS;
            case "CHIHUAHUA" -> FederativeEntity.CHIHUAHUA;
            case "CIUDAD DE MÉXICO" -> FederativeEntity.CDMX;
            case "COAHUILA" -> FederativeEntity.COAHUILA;
            case "COLIMA" -> FederativeEntity.COLIMA;
            case "DURANGO" -> FederativeEntity.DURANGO;
            case "ESTADO DE MÉXICO" -> FederativeEntity.ESTADO_DE_MEXICO;
            case "GUANAJUATO" -> FederativeEntity.GUANAJUATO;
            case "GUERRERO" -> FederativeEntity.GUERRERO;
            case "HIDALGO" -> FederativeEntity.HIDALGO;
            case "JALISCO" -> FederativeEntity.JALISCO;
            case "MICHOACÁN" -> FederativeEntity.MICHOACAN;
            case "MORELOS" -> FederativeEntity.MORELOS;
            case "NAYARIT" -> FederativeEntity.NAYARIT;
            case "NUEVO LEÓN" -> FederativeEntity.NUEVO_LEON;
            case "OAXACA" -> FederativeEntity.OAXACA;
            case "PUEBLA" -> FederativeEntity.PUEBLA;
            case "QUERÉTARO" -> FederativeEntity.QUERETARO;
            case "QUINTANA ROO" -> FederativeEntity.QUINTANA_ROO;
            case "SAN LUIS POTOSÍ" -> FederativeEntity.SAN_LUIS_POTOSI;
            case "SINALOA" -> FederativeEntity.SINALOA;
            case "SONORA" -> FederativeEntity.SONORA;
            case "TABASCO" -> FederativeEntity.TABASCO;
            case "TAMAULIPAS" -> FederativeEntity.TAMAULIPAS;
            case "TLAXCALA" -> FederativeEntity.TLAXCALA;
            case "VERACRUZ" -> FederativeEntity.VERACRUZ;
            case "YUCATÁN" -> FederativeEntity.YUCATAN;
            case "ZACATECAS" -> FederativeEntity.ZACATECAS;
            default -> FederativeEntity.EXTRANJERO;
        };
    }

    public static String generateUsername() {
        String username = "";
        while (true) {
            boolean isValid = true;
            username = Ask.forString("username");
            // Check if the username already exists in the system
            if (!isValid) {
                System.out.println("This username is already taken");
            } else {
                return username;
            }
        }
    }

    public static String generatePhoneNumber() {
        String phoneNumber;
        while (true) {
            boolean isValid = true;
            phoneNumber = Ask.forString("phone number");
            // Check if the phone number already exists in the system
            if (!isValid) {
                System.out.println("This phone number is already taken");
            } else {
                return phoneNumber;
            }
        }
    }

    public static String askForDate(String dateType) {
        LocalDate date = Ask.forLocalDate(dateType);
        return formatDate(date);
    }

    public static String formatDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formatter);
    }

    public static String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return dateTime.format(formatter);
    }

    public static FederativeEntity chooseState() {
        FederativeEntity state = null;
        while (state == null) {
            System.out.println(
                    "1. Aguascalientes\n2. Baja California\n3. Baja California Sur\n4. Campeche\n5. Chiapas\n6. Chihuahua\n7. Ciudad de México\n8. Coahuila\n9. Colima\n10. Durango\n11. Estado de México\n12. Guanajuato\n13. Guerrero\n14. Hidalgo\n15. Jalisco\n16. Michoacán\n17. Morelos\n18. Nayarit\n19. Nuevo León\n20. Oaxaca\n21. Puebla\n22. Querétaro\n23. Quintana Roo\n24. San Luis Potosí\n25. Sinaloa\n26. Sonora\n27. Tabasco\n28. Tamaulipas\n29. Tlaxcala\n30. Veracruz\n31. Yucatán\n32. Zacatecas\n33. Extranjero\n");
            int option = Ask.forInt("the number of the state");

            switch (option) {
                case 1 -> state = FederativeEntity.AGUASCALIENTES;
                case 2 -> state = FederativeEntity.BAJA_CALIFORNIA;
                case 3 -> state = FederativeEntity.BAJA_CALIFORNIA_SUR;
                case 4 -> state = FederativeEntity.CAMPECHE;
                case 5 -> state = FederativeEntity.CHIAPAS;
                case 6 -> state = FederativeEntity.CHIHUAHUA;
                case 7 -> state = FederativeEntity.CDMX;
                case 8 -> state = FederativeEntity.COAHUILA;
                case 9 -> state = FederativeEntity.COLIMA;
                case 10 -> state = FederativeEntity.DURANGO;
                case 11 -> state = FederativeEntity.ESTADO_DE_MEXICO;
                case 12 -> state = FederativeEntity.GUANAJUATO;
                case 13 -> state = FederativeEntity.GUERRERO;
                case 14 -> state = FederativeEntity.HIDALGO;
                case 15 -> state = FederativeEntity.JALISCO;
                case 16 -> state = FederativeEntity.MICHOACAN;
                case 17 -> state = FederativeEntity.MORELOS;
                case 18 -> state = FederativeEntity.NAYARIT;
                case 19 -> state = FederativeEntity.NUEVO_LEON;
                case 20 -> state = FederativeEntity.OAXACA;
                case 21 -> state = FederativeEntity.PUEBLA;
                case 22 -> state = FederativeEntity.QUERETARO;
                case 23 -> state = FederativeEntity.QUINTANA_ROO;
                case 24 -> state = FederativeEntity.SAN_LUIS_POTOSI;
                case 25 -> state = FederativeEntity.SINALOA;
                case 26 -> state = FederativeEntity.SONORA;
                case 27 -> state = FederativeEntity.TABASCO;
                case 28 -> state = FederativeEntity.TAMAULIPAS;
                case 29 -> state = FederativeEntity.TLAXCALA;
                case 30 -> state = FederativeEntity.VERACRUZ;
                case 31 -> state = FederativeEntity.YUCATAN;
                case 32 -> state = FederativeEntity.ZACATECAS;
                case 33 -> state = FederativeEntity.EXTRANJERO;
                default -> System.out.println("\nInvalid option, please try again\n");
            }
        }
        return state;
    }

    public static Gender chooseGender() {
        Gender gender = null;
        while (gender == null) {
            System.out.println("1. Female\n2. Male");
            int option = Ask.forInt("gender");

            switch (option) {
                case 1 -> gender = Gender.FEMALE;
                case 2 -> gender = Gender.MALE;
                default -> System.out.println("\nInvalid option, please try again\n");
            }
        }
        return gender;
    }

    public static Gender validGender(String input) {
        return input.equalsIgnoreCase("FEMALE") ? Gender.FEMALE : Gender.MALE;
    }
}
