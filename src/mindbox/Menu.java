package mindbox;

import academicinfo.Career;
import academicinfo.Group;
import users.Coordinator;
import users.Student;
import users.Teacher;
import users.utils.Country;
import users.utils.Gender;
import users.utils.Role;
import utils.Ask;
import utils.DialogHelper;
import utils.UserInSession;

import com.google.gson.Gson;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Menu {

    private static final Gson gson = new Gson();
    private static final Map<String, Career> careers = new HashMap<>();

    public static void initializeCareers() {
        // Dummy data for the initial setup
        Coordinator coordinator = new Coordinator("John", "Doe", "Smith", LocalDate.now(), Gender.MALE, "City", Country.Aguascalientes, "CURP123", "RFC123", "Address", LocalDate.now(), "username", "password",  );
        List<Group> dummyGroups = new ArrayList<>();

        careers.put("ISC", new Career("ISC", "Engineering in Computer Systems", 2, 50, 6, LocalDate.now().toString(), coordinator, dummyGroups));
        careers.put("IMAT", new Career("IMAT", "Engineering in Materials", 2, 30, 6, LocalDate.now().toString(), coordinator, dummyGroups));
        careers.put("ELC", new Career("ELC", "Engineering in Electronics", 2, 40, 6, LocalDate.now().toString(), coordinator, dummyGroups));
        // Load from JSON if exists
    }

    public static Career askCareer() {
        while (true) {
            int option = DialogHelper.intIn("***************WELCOME TO THE PROGRAM***************\n1. ISC \n2. IMAT \n3. ELC\n4. EXIT",0);
            if (option == 1)
                return careers.get("ISC");
            else if (option == 2)
                return careers.get("IMAT");
            else if (option == 3)
                return careers.get("ELC");
            else if (option == 4)
                return null;
            else
                System.out.println("Invalid option, please try again");
        }
    }

    public static void selectMenu() {
        switch (UserInSession.getInstance().getCurrentUser().getRole()) {
            case STUDENT -> executeStudentMenu();
            case TEACHER -> executeTeacherMenu();
            case COORDINATOR -> executeCoordinatorMenu();
        }
    }

    private static void greetingUser() {
        System.out.printf("\n---------------WELCOME TO THE SYSTEM %s---------------\n",
                UserInSession.getInstance().getCurrentUser().getFullName());
    }

    private static Role requestUserType() {
        Role role = null;
        while (role == null) {
            System.out.println("Enter the user type");
            System.out.println("1. Student");
            System.out.println("2. Teacher");
            System.out.println("3. Coordinator");
            int answer = Ask.forInt("the option number");

            switch (answer) {
                case 1 -> role = Role.STUDENT;
                case 2 -> role = Role.TEACHER;
                case 3 -> role = Role.COORDINATOR;
                default -> System.out.println("Invalid option, please try again");
            }
        }
        return role;
    }

    private static void executeStudentMenu() {
        boolean flag = true;
        int counter = 0;
        greetingUser();
        while (flag) {
            if (counter == 0) Student.showNotification();
            System.out.println("\n What do you want to do?");
            System.out.println("1. Consult personal information");
            System.out.println("2. Modify personal information");
            System.out.println("3. Consult ongoing subjects");
            System.out.println("4. Consult current grades");
            System.out.println("5. Consult current teachers");
            System.out.println("6. Consult current grade average");
            System.out.println("7. Consult history");
            System.out.println("8. LogOut");
            counter++;
            int option = Ask.forInt("the option number");

            switch (option) {
                case 1 -> {
                    System.out.println("----------------MY PERSONAL INFORMATION-----------------------");
                    System.out.println(UserInSession.getInstance().getCurrentUser());
                }
                case 2 -> Student.modifyPersonalInfo();
                case 3 -> Student.consultSubjects();
                case 4 -> Student.consultGrades();
                case 5 -> Student.consultTeachers();
                case 6 -> Student.consultGradeAverage();
                case 7 -> Student.consultHistory();
                case 8 -> {
                    UserInSession.getInstance().closeSession();
                    flag = false;
                }
                default -> System.out.println("Invalid option, please try again");
            }
        }
    }

    private static void executeTeacherMenu() {
        boolean flag = true;
        greetingUser();
        while (flag) {
            System.out.println("\n What do you want to do?");
            System.out.println("1. Consult personal information");
            System.out.println("2. Modify personal information");
            System.out.println("3. Consult groups");
            System.out.println("4. Assign grades");
            System.out.println("5. Modify grades");
            System.out.println("6. Consult current students");
            System.out.println("7. LogOut");
            int option = Ask.forInt("the option number");

            switch (option) {
                case 1 -> {
                    System.out.println("----------------MY PERSONAL INFORMATION-----------------------");
                    System.out.println(UserInSession.getInstance().getCurrentUser());
                }
                case 2 -> Teacher.modifyPersonalInfo();
                case 3 -> Teacher.consultGroups();
                case 4 -> Teacher.assignGrades();
                case 5 -> Teacher.modifyGrades();
                case 6 -> Teacher.consultCurrentStudents();
                case 7 -> {
                    UserInSession.getInstance().closeSession();
                    flag = false;
                }
                default -> System.out.println("Invalid option, please try again");
            }
        }
    }

    private static void executeCoordinatorMenu() {
        boolean flag = true;
        greetingUser();
        while (flag) {
            System.out.println("\nWhat would you like to do?");
            System.out.println("1. Add a student");
            System.out.println("2. Remove a student");
            System.out.println("3. Modify a student");
            System.out.println("4. View a student's information");
            System.out.println("5. View all students");
            System.out.println("6. Add a teacher");
            System.out.println("7. Remove a teacher");
            System.out.println("8. Modify a teacher");
            System.out.println("9. View a teacher's information");
            System.out.println("10. View all teachers");
            System.out.println("11. Promote a group");
            System.out.println("12. Logout");
            int option = Ask.forInt("the option number");

            switch (option) {
                case 1 -> Student.register();
                case 2 -> Student.remove();
                case 3 -> Student.modify();
                case 4 -> Student.viewInfo();
                case 5 -> Student.viewAll();
                case 6 -> Teacher.register();
                case 7 -> Teacher.remove();
                case 8 -> Teacher.modify();
                case 9 -> Teacher.viewInfo();
                case 10 -> Teacher.viewAll();
                case 11 -> promoteGroup();
                case 12 -> {
                    UserInSession.getInstance().closeSession();
                    flag = false;
                }
                default -> System.out.println("Invalid option, please try again");
            }
        }
    }

    private static void promoteGroup() {
        System.out.println("Promoting group...");
        // Implementation of promoting a group
    }
}
