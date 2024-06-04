package users.utils;

import java.time.LocalDate;
import java.util.*;

import mindbox.*;
import users.*;
import utils.*;

public class CommonData {

    private static final HashMap<String, String> federalEntities = new HashMap<>();


    static {
        federalEntities.put("AGUASCALIENTES", "AS");
        federalEntities.put("BAJA CALIFORNIA", "BC");
        federalEntities.put("BAJA CALIFORNIA SUR", "BS");
        federalEntities.put("CAMPECHE", "CC");
        federalEntities.put("COAHUILA DE ZARAGOZA", "CL");
        federalEntities.put("COLIMA", "CM");
        federalEntities.put("CHIAPAS", "CS");
        federalEntities.put("CHIHUAHUA", "CH");
        federalEntities.put("CIUDAD DE MEXICO", "DF");
        federalEntities.put("DURANGO", "DG");
        federalEntities.put("GUANAJUATO", "GT");
        federalEntities.put("GUERRERO", "GR");
        federalEntities.put("HIDALGO", "HG");
        federalEntities.put("JALISCO", "JC");
        federalEntities.put("MEXICO", "MC");
        federalEntities.put("MICHOACAN", "MN");
        federalEntities.put("MORELOS", "MS");
        federalEntities.put("NAYARIT", "NT");
        federalEntities.put("NUEVO LEON", "NL");
        federalEntities.put("OAXACA", "OC");
        federalEntities.put("PUEBLA", "PL");
        federalEntities.put("QUERETARO", "QT");
        federalEntities.put("QUINTANA ROO", "QR");
        federalEntities.put("SAN LUIS POTOSI", "SP");
        federalEntities.put("SINALOA", "SL");
        federalEntities.put("SONORA", "SR");
        federalEntities.put("TABASCO", "TC");
        federalEntities.put("TAMAULIPAS", "TS");
        federalEntities.put("TLAXCALA", "TL");
        federalEntities.put("VERACRUZ", "VZ");
        federalEntities.put("YUCATAN", "YN");
        federalEntities.put("ZACATECAS", "ZS");
        federalEntities.put("FOREIGN BORN", "NE");
    }

    public static boolean isEmpty(String var){
        if (var.isEmpty()){
            return true;
        }
        for (int i = 0; i < var.length(); i++){
            char c = var.charAt(i);
            if (String.valueOf(c).equals(" ")){
                return true;
            }
        }
        return false;
    }

    public static boolean emptyOrNum(String var) {
        if (var.isEmpty()){
            return true;
        }
        if (var.length() < 3){
            return true;
        }
        for (int i = 0; i < var.length(); i++) {
            char c = var.charAt(i);
            if (!Character.isLetter(c) || String.valueOf(c).equals(" ")) {
                return true;
            }
        }
        return false;
    }

    public static ArrayList<String> getCommonData(Role role) {
        String firstName, middleName, parentalLastName, maternalLastName, password, address;
        String city, state, curp, controlNumber, gender, career;

        String currentRole = role == Role.STUDENT ? "Student" : role == Role.COORDINATOR ? "Coordinator" : "Teacher";
        DialogHelper.info("ROLE: " + currentRole);

        do {
            firstName = DialogHelper.stringIn("Enter your first name:");
            if (emptyOrNum(firstName)){
                DialogHelper.warning("Name with numbers or empty is not valid, please enter another.");
            } else {
                break;
            }
        } while (true);

        do {
            String option = DialogHelper.stringIn("Do you have a middle name? [Y]/[N]");
            if (option.equalsIgnoreCase("Y")){
                do {
                    middleName = DialogHelper.stringIn("Enter your middle name:");
                    if (emptyOrNum(middleName))
                        DialogHelper.warning("Name with numbers or empty is not valid, please enter another.");
                    else {
                        firstName = firstName + " " + middleName;
                        break;
                    }
                } while (true);
                break;
            } else if (option.equalsIgnoreCase("N")){
                break;
            } else DialogHelper.warning("Invalid option.");
        } while (true);

        do {
            parentalLastName = DialogHelper.stringIn("Enter your parental last name:");
            if (emptyOrNum(parentalLastName)){
                DialogHelper.warning("Last name with numbers or empty is not valid, please enter another.");
            } else {
                break;
            }
        } while (true);

        do {
            maternalLastName = DialogHelper.stringIn("Enter your maternal last name:");
            if (emptyOrNum(maternalLastName)){
                DialogHelper.warning("Last name with numbers/empty/characters/spaces is not valid, please enter another.");
            } else break;
        } while (true);

        boolean invalidState;
        do {
            state = DialogHelper.stringIn("Enter your state: (if born abroad, write \"FOREIGN BORN\")");
            state = state.toUpperCase();
            try {
                federalEntities.get(state);
                if (!federalEntities.containsKey(state)) throw new Exception("");
                invalidState = false;
            } catch (Exception e) {
                invalidState = true;
            }
        } while (invalidState);

        do {
            city = DialogHelper.stringIn("Enter your city:");
            if (emptyOrNum(city))
                DialogHelper.warning("City with numbers or empty is not valid, please enter another.");
            else {
                DialogHelper.info("City registered successfully.");
                break;
            }
        } while (true);

        LocalDate birthDate = getBirthDate();

        gender = (DialogHelper.optionD("Enter your gender:",new String[]{"Male","Female"})==0 ?"MALE": "FEMALE");

        curp = generateCURP(firstName, parentalLastName, maternalLastName, birthDate, gender, state);

        String rfc = generateRFC(firstName, parentalLastName, maternalLastName, birthDate);

        String fullLastName = parentalLastName + " " + maternalLastName;

        career = UserInSession.getInstance().getCurrentUser().getCareer().getFullName();

        DialogHelper.info("You will be assigned to the career: " + career);
        controlNumber = generateControlNumber(role, firstName, career);
        DialogHelper.info("Your Control Number is: " + controlNumber);

        password = DialogHelper.stringIn("Enter your password:");
        address = DialogHelper.stringIn( "Enter your address: ");

        // Yooo ya termine de arrglar student y teacher, le sigo con coordinador?, SI por eso, ya puse la interfaz con swingx y dialogue helper, vavavavavava

        return new ArrayList<>(Arrays.asList(firstName, fullLastName, birthDate.toString(), city, state, curp, address, password, controlNumber, career, rfc));
    }

    public static LocalDate getBirthDate() {
        int year = DialogHelper.int3In("Type a year",1900,2024);
        int month = DialogHelper.int3In("Type a month by its number",1,12);
        int day = 0;
        switch (month) {
            case 1, 3, 5, 7, 8, 10, 12 -> day = DialogHelper.int3In("Type a day",1,31);
            case 2 -> {
                if ((year%4)==0){ day = DialogHelper.int3In("Type a day",1,29);
                }
                else { day = DialogHelper.int3In("Type a day", 1, 28);}
            }
            case 4,6,9,11 -> day = DialogHelper.int3In("Type a day",1,30);
        }
        return LocalDate.of(year ,month,day);
    }

    private static String generateRandomCharacters() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder(4);
        Random random = new Random();

        for (int i = 0; i < 4; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        return sb.toString();
    }

    private static String getFirstInternalVowel(String str) {
        for (int i = 1; i < str.length(); i++) {
            char c = str.charAt(i);
            if ("AEIOUaeiou".indexOf(c) >= 0) {
                return String.valueOf(c).toUpperCase();
            }
        }
        return "X";
    }

    public static String generateCURP(String firstName, String parentalLastName, String maternalLastName, LocalDate birthDate, String gender, String state) {
        String curpBase = parentalLastName.substring(0, 1).toUpperCase() +
                getFirstInternalVowel(parentalLastName) +
                maternalLastName.substring(0, 1).toUpperCase() +
                firstName.substring(0, 1).toUpperCase() +
                birthDate.toString().substring(2, 4) +
                String.format("%02d", birthDate.getMonthValue()) +
                String.format("%02d", birthDate.getDayOfMonth()) +
                gender.toUpperCase().charAt(0) +
                federalEntities.getOrDefault(state, "NE");

        String randomCharacters = generateRandomCharacters();

        return curpBase + randomCharacters;
    }

    public static String generateControlNumber(Role role, String firstName, String career) {
        String prefix = switch (role) {
            case STUDENT -> "l";
            case TEACHER -> "M";
            case COORDINATOR -> "C";
        };

        String firstLetter = String.valueOf(firstName.charAt(0)).toUpperCase();
        int year = LocalDate.now().getYear();
        String yearSuffix = String.valueOf(year).substring(2);
        String careerCode = career.substring(0, 3).toUpperCase();

        int suffix = 0;
        String controlNumber;
        boolean exists;

        do {
            exists = false;
            controlNumber = prefix + firstLetter + yearSuffix + careerCode + suffix;

            ArrayList<User> teachers = Mindbox.users.get(Role.TEACHER);
            ArrayList<User> students = Mindbox.users.get(Role.STUDENT);
            ArrayList<User> coordinators = Mindbox.users.get(Role.COORDINATOR);

            for (User user : teachers) {
                if (user.getControlNumber().equals(controlNumber)) {
                    exists = true;
                    break;
                }
            }
            for (User user : students) {
                if (user.getControlNumber().equals(controlNumber)) {
                    exists = true;
                    break;
                }
            }
            for (User user : coordinators) {
                if (user.getControlNumber().equals(controlNumber)) {
                    exists = true;
                    break;
                }
            }

            if (exists) {
                suffix++;
            }

        } while (exists);

        return controlNumber;
    }

    public static String generateRFC(String firstName, String parentalLastName, String maternalLastName, LocalDate birthDate) {
        Random random = new Random();
        maternalLastName = maternalLastName.toUpperCase();
        parentalLastName = parentalLastName.toUpperCase();
        int randomDigit1 = random.nextInt(10);
        int randomDigit2 = random.nextInt(10);
        char randomLetter = (char)(random.nextInt(27) + 65);
        int year = birthDate.getYear();
        int yearTwoDigits = year % 100;
        String yearFormattedTwoDigits = String.format("%02d", yearTwoDigits);
        return parentalLastName.toUpperCase().charAt(0) + String.valueOf(parentalLastName.toUpperCase().charAt(1)) +
                maternalLastName.toUpperCase().charAt(0) + firstName.toUpperCase().charAt(0) +
                yearFormattedTwoDigits + birthDate.getMonthValue() + birthDate.getDayOfMonth() +
                randomDigit1 + randomLetter + randomDigit2;
    }

    public static void updateInformation(User user){
        Role role = user.getRole();
        boolean flag = true;
        do {
            switch (DialogHelper.optionD("Which characteristics do you want to change?",new String[]{"City","State","Address","Password","Return"})) {
                case 0 -> {
                    String city = DialogHelper.stringIn("Enter the new city of residence:");
                    user.setCity(city);
                    DialogHelper.info("City changed successfully.");
                }
                case 1 -> {
                    do {
                        boolean invalidState = false;
                        String state = DialogHelper.stringIn("Enter the new state: (if born abroad, write \"NACIDO EN EL EXTRANJERO\")").toUpperCase();

                        try {
                            federalEntities.get(state);
                            if (!federalEntities.containsKey(state)) throw new Exception("");
                        } catch (Exception e) {
                            invalidState = true;
                        }
                        if (emptyOrNum(state) || invalidState)
                            DialogHelper.error("State with numbers/empty/characters/spaces is not valid or does not exist, please enter another.");
                        else {
                            user.setState(state);
                            DialogHelper.info("State changed successfully.");
                            break;
                        }
                    } while (true);
                }
                case 2 -> {
                    String address = DialogHelper.stringIn("Enter the new address:");
                    user.setAddress(address);
                    DialogHelper.info("Address changed successfully.");
                }
                case 3 -> {
                    String password = DialogHelper.stringIn("Enter the new password:");
                    user.setPassword(password);
                    DialogHelper.info("Password changed successfully.");
                }
                case 4 -> {
                    DialogHelper.returnD();
                    flag = false;
                }
            }
        } while (flag);
    }
}
