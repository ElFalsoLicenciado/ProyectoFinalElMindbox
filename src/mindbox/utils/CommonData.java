package mindbox.utils;

import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

import mindbox.Minbox;
import mindbox.Sys;
import users.User;
import users.utils.Country;
import users.utils.FederationEntity;
import users.utils.Gender;
import users.utils.Role;
import utils.Ask;
import utils.Date;

public class CommonData {

    public static List<String> getCommonData(Role role) {
        List<String> commonData = new ArrayList<>();

        String roleType = switch (role) {
            case STUDENT -> "S T U D E N T";
            case TEACHER -> "T E A C H E R";
            case COORDINATOR -> "C O O R D I N A T O R";
        };

        System.out.println(String.format("\n ADD %s ", roleType));

        String firstName = Ask.forString("the first name");
        String paternalLastName = Ask.forString("the paternal last name");
        String maternalLastName = Ask.forString("the maternal last name");
        String address = Ask.forString("the address");

        Country country = CommonData.chooseCountry();
        String countryString = FederationEntity.getStringCountry(country);

        String city = Ask.forString("the city");
        String username = CommonData.getUsername();
        String password = Ask.forString("the password");

        String gender = String.valueOf(chooseGender());

        String birthDate = Date.askForDate("birth date");

        commonData.addAll(Arrays.asList(firstName, paternalLastName, maternalLastName, birthDate, address, countryString, city, username, password, gender));

        return commonData;
    }

    public static Country validCountry(String input) {
        return switch (input.toUpperCase()) {
            case "AGUASCALIENTES" -> Country.Aguascalientes;
            case "BAJA CALIFORNIA" -> Country.Baja_California;
            case "BAJA CALIFORNIA SUR" -> Country.Baja_California_Sur;
            case "CAMPECHE" -> Country.Campeche;
            case "CHIAPAS" -> Country.Chiapas;
            case "CHIHUAHUA" -> Country.Chihuahua;
            case "CIUDAD DE MÉXICO" -> Country.CDMX;
            case "COAHUILA" -> Country.Coahuila;
            case "COLIMA" -> Country.Colima;
            case "DURANGO" -> Country.Durango;
            case "ESTADO DE MÉXICO" -> Country.Estado_de_Mexico;
            case "GUANAJUATO" -> Country.Guanajuato;
            case "GUERRERO" -> Country.Guerrero;
            case "HIDALGO" -> Country.Hidalgo;
            case "JALISCO" -> Country.Jalisco;
            case "MICHOACÁN" -> Country.Michoacan;
            case "MORELOS" -> Country.Morelos;
            case "NAYARIT" -> Country.Nayarit;
            case "NUEVO LEÓN" -> Country.Nuevo_Leon;
            case "OAXACA" -> Country.Oaxaca;
            case "PUEBLA" -> Country.Puebla;
            case "QUERÉTARO" -> Country.Queretaro;
            case "QUINTANA ROO" -> Country.Quintana_Roo;
            case "SAN LUIS POTOSÍ" -> Country.San_Luis_Potosi;
            case "SINALOA" -> Country.Sinaloa;
            case "SONORA" -> Country.Sonora;
            case "TABASCO" -> Country.Tabasco;
            case "TAMAULIPAS" -> Country.Tamaulipas;
            case "TLAXCALA" -> Country.Tlaxcala;
            case "VERACRUZ" -> Country.Veracruz;
            case "YUCATÁN" -> Country.Yucatan;
            case "ZACATECAS" -> Country.Zacatecas;
            default -> Country.Nacido_En_Extranjero;
        };
    }

    public static String getUsername() {
        String username = "";
        while (true) {
            boolean validUsername = true;
            username = Ask.forString("username");
            for (Minbox minbox : Sys.getInstance().getCareers().values()) {
                for (User user : minbox.getUserList().get(Role.STUDENT)) {
                    if (username.equals(user.getUsername())) {
                        validUsername = false;
                        break;
                    }
                }
                if (!validUsername) break;

                for (User user : minbox.getUserList().get(Role.TEACHER)) {
                    if (username.equals(user.getUsername())) {
                        validUsername = false;
                        break;
                    }
                }
                if (!validUsername) break;

                for (User user : minbox.getUserList().get(Role.COORDINATOR)) {
                    if (username.equals(user.getUsername())) {
                        validUsername = false;
                        break;
                    }
                }
            }
            if (!validUsername)
                System.out.println("This username is already taken, please try another.");
            else
                return username;
        }
    }

    public static String formatDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formatter);
    }

    public static String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return dateTime.format(formatter);
    }

    public static Country chooseCountry() {
        Country state = null;
        while (state == null) {
            System.out.println(
                    "1. Aguascalientes\n2. Baja California\n3. Baja California Sur\n4. Campeche\n5. Chiapas\n6. Chihuahua\n7. Ciudad de México\n8. Coahuila\n9. Colima\n10. Durango\n11. Estado de México\n12. Guanajuato\n13. Guerrero\n14. Hidalgo\n15. Jalisco\n16. Michoacán\n17. Morelos\n18. Nayarit\n19. Nuevo León\n20. Oaxaca\n21. Puebla\n22. Querétaro\n23. Quintana Roo\n24. San Luis Potosí\n25. Sinaloa\n26. Sonora\n27. Tabasco\n28. Tamaulipas\n29. Tlaxcala\n30. Veracruz\n31. Yucatán\n32. Zacatecas\n33. Extranjero\n");
            int option = Ask.forInt("the number of the state");

            switch (option) {
                case 1 -> state = Country.Aguascalientes;
                case 2 -> state = Country.Baja_California;
                case 3 -> state = Country.Baja_California_Sur;
                case 4 -> state = Country.Campeche;
                case 5 -> state = Country.Chiapas;
                case 6 -> state = Country.Chihuahua;
                case 7 -> state = Country.CDMX;
                case 8 -> state = Country.Coahuila;
                case 9 -> state = Country.Colima;
                case 10 -> state = Country.Durango;
                case 11 -> state = Country.Estado_de_Mexico;
                case 12 -> state = Country.Guanajuato;
                case 13 -> state = Country.Guerrero;
                case 14 -> state = Country.Hidalgo;
                case 15 -> state = Country.Jalisco;
                case 16 -> state = Country.Michoacan;
                case 17 -> state = Country.Morelos;
                case 18 -> state = Country.Nayarit;
                case 19 -> state = Country.Nuevo_Leon;
                case 20 -> state = Country.Oaxaca;
                case 21 -> state = Country.Puebla;
                case 22 -> state = Country.Queretaro;
                case 23 -> state = Country.Quintana_Roo;
                case 24 -> state = Country.San_Luis_Potosi;
                case 25 -> state = Country.Sinaloa;
                case 26 -> state = Country.Sonora;
                case 27 -> state = Country.Tabasco;
                case 28 -> state = Country.Tamaulipas;
                case 29 -> state = Country.Tlaxcala;
                case 30 -> state = Country.Veracruz;
                case 31 -> state = Country.Yucatan;
                case 32 -> state = Country.Zacatecas;
                case 33 -> state = Country.Nacido_En_Extranjero;
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
