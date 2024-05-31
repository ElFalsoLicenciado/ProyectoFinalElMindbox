package mindbox;

import academicinfo.CareerType;
import users.Student;
import users.Teacher;
import users.utils.Role;
import utils.Ask;
import utils.UserInSession;

import java.util.HashMap;
import java.util.Map;

public class Menu {

    private static final Map<CareerType, Minbox> careers = new HashMap<>();

    public static void initializeCareers() {
        careers.put(CareerType.ISC, new Minbox(CareerType.ISC));
        careers.put(CareerType.IMAT, new Minbox(CareerType.IMAT));
        careers.put(CareerType.ELC, new Minbox(CareerType.ELC));
        // Load from JSON if exists
    }

    public static CareerType askCareer() {
        while (true) {
            System.out.println("***************WELCOME TO THE PROGRAM***************");
            System.out.println("1. ISC \n2. IMAT \n3. ELC\n4. EXIT");
            int option = Ask.forInt("the option number");
            switch (option) {
                case 1: return CareerType.ISC;
                case 2: return CareerType.IMAT;
                case 3: return CareerType.ELC;
                case 4: return null;
                default: System.out.println("Invalid option, please try again.");
            }
        }
    }

    public static void selectMenu() {
        if (UserInSession.getInstance().getCurrentUser() == null) {
            System.out.println("No user is currently logged in.");
            return;
        }

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

    public static Role requestUserType() {
        Role role = null;
        while (role == null) {
            System.out.println("Enter the user type:");
            System.out.println("1. Student");
            System.out.println("2. Teacher");
            System.out.println("3. Coordinator");
            int answer = Ask.forInt("the option number");

            switch (answer) {
                case 1 -> role = Role.STUDENT;
                case 2 -> role = Role.TEACHER;
                case 3 -> role = Role.COORDINATOR;
                default -> System.out.println("Invalid option, please try again.");
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
            System.out.println("\nWhat do you want to do?");
            System.out.println("1. Consult personal information");
            System.out.println("2. Modify personal information");
            System.out.println("3. Consult ongoing subjects");
            System.out.println("4. Consult current grades");
            System.out.println("5. Consult current teachers");
            System.out.println("6. Consult current grade average");
            System.out.println("7. Consult history");
            System.out.println("8. Log Out");
            counter++;
            int option = Ask.forInt("the option number");

            switch (option) {
                case 1 -> Student.consultPersonalInfo();
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
                default -> System.out.println("Invalid option, please try again.");
            }
        }
    }

    private static void executeTeacherMenu() {
        boolean flag = true;
        greetingUser();
        while (flag) {
            System.out.println("\nWhat do you want to do?");
            System.out.println("1. Consult personal information");
            System.out.println("2. Modify personal information");
            System.out.println("3. Consult groups");
            System.out.println("4. Assign grades");
            System.out.println("5. Modify grades");
            System.out.println("6. Consult current students");
            System.out.println("7. Log Out");
            int option = Ask.forInt("the option number");

            switch (option) {
                case 1 -> Teacher.viewInfo();
                case 2 -> Teacher.modifyPersonalInfo();
                case 3 -> Teacher.consultGroups();
                case 4 -> Teacher.assignGrades();
                case 5 -> Teacher.modifyGrades();
                case 6 -> Teacher.consultCurrentStudents();
                case 7 -> {
                    UserInSession.getInstance().closeSession();
                    flag = false;
                }
                default -> System.out.println("Invalid option, please try again.");
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
            System.out.println("12. Log Out");
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
                default -> System.out.println("Invalid option, please try again.");
            }
        }
    }

    private static void promoteGroup() {
        System.out.println("Promoting group...");
        // Implementation of promoting a group
    }
}
