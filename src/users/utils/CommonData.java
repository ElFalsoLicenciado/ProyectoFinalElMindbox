package users.utils;

import java.time.LocalDate;
import java.util.*;

import academicinfo.*;
import mindbox.*;
import users.*;
import users.utils.*;
import utils.*;

public class CommonData {

    private static final HashMap<String, String> federalEntities = new HashMap<>();

    private static Scanner read = new Scanner(System.in);

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
        federalEntities.put("NACIDO EN EL EXTRANJERO", "NE");
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

    public static User getCommonData(Role role) {
        String firstName, middleName, parentalLastName, maternalLastName,username, password, address;
        String city, state, curp, controlNumber, gender; Careers career;

        String currentRole = role == Role.STUDENT ? "Student" : role == Role.COORDINATOR ? "Coordinator" : "Teacher";
        DialogHelper.info("ROLE: " + currentRole);

        do {
            firstName = DialogHelper.stringIn("Enter your first name: ");;
            if (emptyOrNum(firstName)){
                DialogHelper.error("Name with numbers/empty/characters/spaces is not valid, please enter another. ");
            } else break;
        } while (true);


        if (DialogHelper.confirmD("Do you have a middle name?")) {
            do {
                middleName = DialogHelper.stringIn("Enter your middle name: ");
                if (emptyOrNum(middleName))
                    DialogHelper.error("Name with numbers/empty is not valid, please enter another. ");
                else {
                    firstName = firstName + " " + middleName;
                    break;
                }
            } while (true);
        }

        do {
            parentalLastName = DialogHelper.stringIn("Enter your parental last name: ");
            if (emptyOrNum(parentalLastName))
                DialogHelper.error("Last name with numbers/empty/characters/spaces is not valid, please enter another. ");
            else break;
        } while (true);

        do {
            maternalLastName = DialogHelper.stringIn("Enter your maternal last name: ");
            if (emptyOrNum(maternalLastName))
                DialogHelper.error("Last name with numbers/empty/characters/spaces is not valid, please enter another. ");
            else break;
        } while (true);

        boolean invalidState;
        do {
            state = DialogHelper.stringIn("Enter your state: (if born abroad, write \"NACIDO EN EL EXTRANJERO\")").toUpperCase();
            try {
                federalEntities.get(state);
                if (!federalEntities.containsKey(state)) throw new Exception("");
                invalidState = false;
            } catch (Exception e) {
                invalidState = true;
            }
        } while (invalidState);

        do {
            address = DialogHelper.stringIn("Enter your address: ");
            if (emptyOrNum(address))
                DialogHelper.error("Address with numbers/empty/characters/spaces is not valid, please enter another.");
            else break;
        } while (true);

        String birthDate = TimeFunctions.getDate();


        gender = DialogHelper.optionD("Enter your gender: ",new String[]{"Male","Female"}) ==0 ? "MALE": "FEMALE";
        DialogHelper.info(gender);

        curp = generateCURP(firstName, parentalLastName, maternalLastName, birthDate, gender, state);

        do {
            city = DialogHelper.stringIn("Enter your city: ");
            if (emptyOrNum(city))
                DialogHelper.error("City with numbers/empty/characters/spaces is not valid, please enter another. ");
            else break;
        } while (true);

        career = UserInSession.getInstance().getCurrentUser().getCareer();

        controlNumber = generateControlNumber(role, firstName, String.valueOf(career));
        DialogHelper.info("You will be assigned to the career: " + career+"\nYour Control Number/User ID is: " + controlNumber);

        username = verifyUsername();

        password = DialogHelper.stringIn("Enter your password: ");



        return new User(firstName,parentalLastName,maternalLastName,birthDate,city,state,curp,address,career,controlNumber,username,password,role);
    }

    public static String verifyUsername(){
        String availableUsername;
        boolean nonExistent;
        do {
            nonExistent = false;
            availableUsername = DialogHelper.stringIn("User's username");
            for (Map.Entry<Role, ArrayList<User>> entry : Mindbox.users.entrySet() ) {
                ArrayList<User> userList = entry.getValue();
                for (User current : userList) {
                    if (current.getUsername().equals(availableUsername)) {
                        nonExistent = true;
                        DialogHelper.warning("Repeated username.");
                        break;
                    }
                }
            }
        }while (nonExistent);
        return availableUsername;
    }

    private static String generateRandomCharacters(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();

        for (int i = 0; i < length; i++) {
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

    public static String generateCURP(String firstName, String parentalLastName, String maternalLastName, String birthDate, String gender, String state) {
        String curpBase = parentalLastName.substring(0, 1).toUpperCase() +
                getFirstInternalVowel(parentalLastName) +
                maternalLastName.substring(0, 1).toUpperCase() +
                firstName.substring(0, 1).toUpperCase() +
                birthDate.substring(2, 4) +
                birthDate.substring(5, 7) +
                birthDate.substring(8, 10) +
                gender.toUpperCase().charAt(0) +
                federalEntities.getOrDefault(state, "NE");

        String randomCharacters = generateRandomCharacters(4);

        return curpBase + randomCharacters;
    }

    public static String generateControlNumber(Role role, String firstName, String career){
        boolean exists = false;
        String prefix = "l", firstLetter;
        String controlNumber, yearSuffix, careerCode;
        int suffix = 0;

        firstLetter = String.valueOf(firstName.charAt(0)).toUpperCase();

        int year = LocalDate.now().getYear();
        yearSuffix = String.valueOf(year).substring(2);

        careerCode = career.substring(0, 3).toUpperCase();

        ArrayList<User> teachers = Mindbox.users.get(Role.TEACHER);
        ArrayList<User> students = Mindbox.users.get(Role.STUDENT);
        ArrayList<User> coordinators = Mindbox.users.get(Role.COORDINATOR);
        User foundUser = null;
        controlNumber = prefix + firstLetter + yearSuffix + careerCode + suffix;
        for (User user : teachers) {
            if (user.getControlNumber().equals(controlNumber)){
                exists = true;
                foundUser = user;
                break;
            }
        }
        for (User user : students) {
            if (user.getControlNumber().equals(controlNumber)){
                exists = true;
                foundUser = user;
                break;
            }
        }
        for (User user : coordinators) {
            if (user.getControlNumber().equals(controlNumber)){
                exists = true;
                foundUser = user;
                break;
            }
        }

        String finalControlNumber = "";
        if (exists){
            String existingControlNumber = foundUser.getControlNumber();
            int number = Integer.parseInt(String.valueOf(existingControlNumber.charAt(existingControlNumber.length() - 1)));
            number++;
            finalControlNumber = prefix + firstLetter + yearSuffix + careerCode + number;
        } else {
            finalControlNumber = controlNumber;
        }

        return finalControlNumber;
    }

    public static String generateRFC(String firstName, String parentalLastName, String maternalLastName, String birthDate) {
        Random random = new Random();
        maternalLastName = maternalLastName.toUpperCase();
        parentalLastName = parentalLastName.toUpperCase();
        int randomDigit1 = random.nextInt(10);
        int randomDigit2 = random.nextInt(10);
        char randomLetter = (char)(random.nextInt(27) + 65);
        return parentalLastName.toUpperCase().charAt(0) + String.valueOf(parentalLastName.toUpperCase().charAt(1)) +
                maternalLastName.toUpperCase().charAt(0) + firstName.toUpperCase().charAt(0) +
                birthDate.substring(2,4) + birthDate.substring(5,7) + birthDate.substring(8,10) +
                randomDigit1 + randomLetter + randomDigit2;
    }

    public static void updateInformation(User user){
        Scanner read = new Scanner(System.in);
        String option = "";
        Role role = user.getRole();
        do {
            System.out.println("Which characteristics do you want to change for the selected user?");
            System.out.println("1. City of residence.");
            System.out.println("2. State of residence.");
            System.out.println("3. Address.");
            System.out.println("4. Password.");
            System.out.println("5. EXIT THIS MENU.");
            option = read.nextLine();

            if (option.equals("1")) {
                do {
                    System.out.println("Enter the new city of residence:");
                    String city = read.nextLine();
                    if (emptyOrNum(city)){
                        System.out.println("City with numbers/empty/characters/spaces is not valid, please enter another.");
                    } else {
                        user.setCity(city);
                        System.out.println("City changed successfully.");
                        break;
                    }
                } while (true);
            } else if (option.equals("2")) {

                do {
                    boolean invalidState = false;
                    System.out.println("Enter the new state: (if born abroad, write \"NACIDO EN EL EXTRANJERO\")");
                    String state = read.nextLine();
                    state = state.toUpperCase();
                    try {
                        federalEntities.get(state);
                        if (!federalEntities.keySet().contains(state)){
                            throw new Exception("");
                        }
                    } catch (Exception e) {
                        invalidState = true;
                    }
                    if (emptyOrNum(state) || invalidState){
                        System.out.println("State with numbers/empty/characters/spaces is not valid or does not exist, please enter another.");
                    } else {
                        user.setState(state);
                        System.out.println("State changed successfully.");
                        break;
                    }
                } while (true);
            } else if (option.equals("3")) {
                do {
                    System.out.println("Enter the new address:");
                    String address = read.nextLine();
                    if (emptyOrNum(address)){
                        System.out.println("Address with numbers/empty/characters/spaces is not valid, please enter another.");
                    } else {
                        user.setAddress(address);
                        System.out.println("Address changed successfully.");
                        break;
                    }
                } while (true);
            } else if (option.equals("4")) {
                System.out.println("Enter the new password:");
                String password = read.nextLine();
                user.setPassword(password);
                System.out.println("Password changed successfully.");
            } else if (option.equals("5")) {
                System.out.println("EXITING THE MENU.");
                break;
            } else  {
                System.out.println("INVALID OPTION.");
            }

        } while (true);
    }
}
