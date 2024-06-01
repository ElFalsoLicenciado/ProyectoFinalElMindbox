package mindbox;

import academicinfo.*;
import gson.serializers.*;
import gson.deserializers.*;
import mindbox.*;
import users.*;
import users.utils.*;
import utils.*;
import java.util.*;

public class Menu {

    static Scanner reader = new Scanner(System.in);

    public static void logIn() {
        boolean correctData = false;
        int attempts = 5;

        UserInSession.getInstance().logout();

        do {
            System.out.println("WELCOME TO THE MINDBOX SYSTEM.");
            System.out.println("Type [EXIT] in both fields if you want to exit the program directly.");
            System.out.printf("Log in to continue, you have %d attempts: \n", attempts);

            System.out.println("Enter your control number: ");
            String controlNumber = reader.nextLine();

            System.out.println("Enter your password: ");
            String password = reader.nextLine();

            if (controlNumber.equals("EXIT") && password.equals("EXIT")) {
                System.out.println("Ending the program.");
                UserSerializer.serialize();
                MindboxSerializer.serialize();
                correctData = true;
            } else {
                User currentUser = Mindbox.verifyLogin(controlNumber, password);

                if (currentUser != null && attempts > 0) {
                    UserInSession.getInstance().setUser(currentUser);
                    selectMenu();
                    attempts = 5;
                } else if (attempts == 1) {
                    System.out.println("ATTEMPTS EXHAUSTED, ENDING THE PROGRAM.");
                    UserSerializer.serialize();
                    MindboxSerializer.serialize();
                    correctData = true;
                } else {
                    System.out.println("INCORRECT DATA.");
                    attempts--;
                }
            }

        } while (!correctData);
    }

    private static void selectMenu() {
        switch (UserInSession.getInstance().getCurrentUser().getRole()) {
            case STUDENT -> executeStudentMenu();
            case COORDINATOR -> executeCoordinatorMenu();
            case TEACHER -> executeTeacherMenu();
        }
    }

    private static void executeStudentMenu() {
        String option = "";
        Student student = (Student) UserInSession.getInstance().getCurrentUser();
        System.out.println("Welcome Student " + student.getFirstName() + " " + student.getLastName());
        do {
            System.out.println("1. View grades");
            System.out.println("2. Show current subjects");
            System.out.println("3. View history");
            System.out.println("4. Show my information.");
            System.out.println("E. Log out");
            System.out.print("Enter an option: ");
            System.out.println();
            option = reader.nextLine();
            switch (option) {
                case "1":
                    student.showGrades();
                    System.out.print("-------------------------------------------------\n");
                    break;
                case "2":
                    student.showCurrentClasses();
                    System.out.print("-------------------------------------------------\n");
                    break;
                case "3":
                    student.showHistory();
                    System.out.print("-------------------------------------------------\n");
                    break;
                case "4":
                    System.out.println(student.toString());
                    System.out.print("-------------------------------------------------\n");
                    break;
                case "E":
                    System.out.println("Logging out...");
                    System.out.print("-------------------------------------------------\n");
                    break;
                default:
                    if (!option.equalsIgnoreCase("E")) {
                        System.out.println("Invalid Option");
                    }
                    System.out.print("-------------------------------------------------\n");
                    break;
            }
        } while (!option.equalsIgnoreCase("E"));
        UserInSession.getInstance().logout();
    }

    private static void executeCoordinatorMenu() {
        String option = "";
        Coordinator coordinator = (Coordinator) UserInSession.getInstance().getCurrentUser();
        System.out.println("Welcome Coordinator " + coordinator.getFirstName() + " " + coordinator.getLastName());
        do {
            System.out.println("1. Assign grades");
            System.out.println("2. View all my current subjects.");
            System.out.println("3. Observe students by filter.");
            System.out.println("4. Promote Group");
            System.out.println("5. Student Options");
            System.out.println("6. Teacher Options");
            System.out.println("7. Show all Groups.");
            System.out.println("8. Show Graduated Students");
            System.out.println("9. Show my information.");
            System.out.println("E. Exit the system");
            System.out.print("Enter an option:");
            System.out.println();
            option = reader.nextLine();
            switch (option) {
                case "1":
                    System.out.println("Selected: Assign grades");
                    coordinator.assignGrade();
                    System.out.print("-------------------------------------------------\n");
                    break;
                case "2":
                    System.out.println("Showing all your current subjects.");
                    coordinator.showMySubjects();
                    System.out.print("-------------------------------------------------\n");
                    break;
                case "3":
                    if (coordinator.getSubjects().isEmpty()) {
                        System.out.println("You have no subjects assigned.");
                        System.out.print("-------------------------------------------------\n");
                    } else {
                        System.out.println("Observe grades by filter.");
                        String filterOption;
                        boolean exit = false;
                        do {
                            System.out.println("Which filter do you want?");
                            System.out.println("[1]. By semester.");
                            System.out.println("[2]. By group.");
                            System.out.println("[3]. By subject.");
                            System.out.println("[E]. Exit the menu");
                            filterOption = reader.nextLine();
                            switch (filterOption) {
                                case "1":
                                    coordinator.filterBySemester();
                                    System.out.print("-------------------------------------------------\n");
                                    break;
                                case "2":
                                    coordinator.filterByGroup();
                                    System.out.print("-------------------------------------------------\n");
                                    break;
                                case "3":
                                    coordinator.filterBySubject();
                                    System.out.print("-------------------------------------------------\n");
                                    break;
                                case "E":
                                    exit = true;
                                    System.out.print("-------------------------------------------------\n");
                                    break;
                                default:
                                    System.out.println("Invalid option.");
                                    System.out.print("-------------------------------------------------\n");
                                    break;
                            }
                        } while (!exit);
                    }
                    break;
                case "4":
                    System.out.println("Selected: Promote Group");
                    Coordinator.promoteGroup();
                    System.out.print("-------------------------------------------------\n");
                    break;
                case "5":
                    do {
                        System.out.println("1. Register Student");
                        System.out.println("2. Modify Student");
                        System.out.println("3. Delete Student");
                        System.out.println("4. Show Students");
                        System.out.println("E. Exit the menu");
                        System.out.print("Enter an option:");
                        option = reader.nextLine();
                        switch (option) {
                            case "1":
                                Mindbox.registerStudent();
                                System.out.print("-------------------------------------------------\n");
                                break;
                            case "2":
                                Mindbox.updateStudent();
                                System.out.print("-------------------------------------------------\n");
                                break;
                            case "3":
                                Mindbox.expelStudent();
                                System.out.print("-------------------------------------------------\n");
                                break;
                            case "4":
                                Mindbox.showAllStudents();
                                System.out.print("-------------------------------------------------\n");
                                break;
                            case "E":
                                System.out.print("-------------------------------------------------\n");
                                break;
                            default:
                                System.out.println("Invalid Option");
                                System.out.print("-------------------------------------------------\n");
                                break;
                        }
                    } while (!option.equalsIgnoreCase("E"));
                    option = "";
                    break;
                case "6":
                    do {
                        System.out.println("1. Register Teacher");
                        System.out.println("2. Modify Teacher");
                        System.out.println("3. Delete Teacher");
                        System.out.println("4. Show Teachers");
                        System.out.println("5. Add a teacher to a subject.");
                        System.out.println("6. Remove a teacher from a subject.");
                        System.out.println("E. Exit the menu");
                        System.out.print("Enter an option:");
                        option = reader.nextLine();
                        switch (option) {
                            case "1":
                                Mindbox.registerTeacher();
                                System.out.print("-------------------------------------------------\n");
                                break;
                            case "2":
                                Mindbox.updateTeacher();
                                System.out.print("-------------------------------------------------\n");
                                break;
                            case "3":
                                Mindbox.deleteTeacher();
                                System.out.print("-------------------------------------------------\n");
                                break;
                            case "4":
                                Mindbox.showAllTeachers();
                                System.out.print("-------------------------------------------------\n");
                                break;
                            case "5":
                                Mindbox.assignTeacherToSubject();
                                System.out.print("-------------------------------------------------\n");
                                break;
                            case "6":
                                Mindbox.removeTeacherFromSubject();
                                System.out.print("-------------------------------------------------\n");
                                break;
                            case "E":
                                System.out.print("-------------------------------------------------\n");
                                break;
                            default:
                                System.out.println("Invalid Option");
                                System.out.print("-------------------------------------------------\n");
                                break;
                        }
                    } while (!option.equalsIgnoreCase("E"));
                    option = "";
                    break;
                case "7":
                    Group.showAllGroups();
                    System.out.print("-------------------------------------------------\n");
                    break;
                case "8":
                    Graduates.showGraduates();
                    System.out.print("-------------------------------------------------\n");
                    break;
                case "9":
                    System.out.println(coordinator.toString());
                    System.out.print("-------------------------------------------------\n");
                    break;
                default:
                    if (!option.equalsIgnoreCase("E")) {
                        System.out.println("Invalid Option");
                    }
                    System.out.print("-------------------------------------------------\n");
                    break;
            }
        } while (!option.equalsIgnoreCase("E"));
        UserInSession.getInstance().logout();
        System.out.println("Logging Out...");
        System.out.print("-------------------------------------------------\n");
    }

    private static void executeTeacherMenu() {
        String option = "";
        Worker teacher = (Worker) UserInSession.getInstance().getCurrentUser();
        System.out.println("Welcome Teacher " + teacher.getFirstName() + " " + teacher.getLastName());
        do {
            System.out.println("1. View all my current subjects.");
            System.out.println("2. Observe students by filter.");
            System.out.println("3. Assign Grades");
            System.out.println("4. Show my information.");
            System.out.println("E. Log Out");
            System.out.print("Enter an option:");
            System.out.println();
            option = reader.nextLine();
            switch (option) {
                case "1":
                    System.out.println("Showing all your current subjects.");
                    teacher.showMySubjects();
                    System.out.print("-------------------------------------------------\n");
                    break;
                case "2":
                    if (teacher.getSubjects().isEmpty()) {
                        System.out.println("No filters available, you have no groups assigned.");
                    } else {
                        System.out.println("Observe grades by filter.");
                        String filterOption;
                        boolean exit = false;
                        do {
                            System.out.println("Which filter do you want?");
                            System.out.println("[1]. By semester.");
                            System.out.println("[2]. By group.");
                            System.out.println("[3]. By subject.");
                            System.out.println("[E]. Exit the menu");
                            filterOption = reader.nextLine();
                            switch (filterOption) {
                                case "1":
                                    teacher.filterBySemester();
                                    System.out.print("-------------------------------------------------\n");
                                    break;
                                case "2":
                                    teacher.filterByGroup();
                                    System.out.print("-------------------------------------------------\n");
                                    break;
                                case "3":
                                    teacher.filterBySubject();
                                    System.out.print("-------------------------------------------------\n");
                                    break;
                                case "E":
                                    exit = true;
                                    System.out.print("-------------------------------------------------\n");
                                    break;
                                default:
                                    System.out.println("Invalid option.");
                                    System.out.print("-------------------------------------------------\n");
                                    break;
                            }
                        } while (!exit);
                    }
                    break;
                case "3":
                    System.out.println("Selected: Assign grades");
                    teacher.assignGrade();
                    System.out.print("-------------------------------------------------\n");
                    break;
                case "4":
                    System.out.println(teacher.toString());
                    System.out.print("-------------------------------------------------\n");
                    break;
                case "E":
                case "e":
                    System.out.println("Logging out...");
                    UserInSession.getInstance().logout();
                    System.out.print("-------------------------------------------------\n");
                    break;
                default:
                    if (!option.equalsIgnoreCase("E")) {
                        System.out.println("Invalid Option");
                    }
                    System.out.print("-------------------------------------------------\n");
                    break;
            }
        } while (!option.equalsIgnoreCase("E"));
    }
}
