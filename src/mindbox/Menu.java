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

    public static Scanner reader = new Scanner(System.in);

    public static void showMenu(){

        while (true) {
            Mindbox.loadJson();

            System.out.println("WELCOME TO THE MINDBOX SYSTEM.");
            System.out.println("1. LogIn");
            System.out.println("E. Exit");
            System.out.println("Elige una opción: ");

            String option = reader.nextLine();

            switch (option.toUpperCase()) {
                case "1":
                    logIn();
                    break;
                case "E":
                    Mindbox.saveJson();
                    System.exit(0);
                    break;
                default:
                    System.out.println("INCORRECT OPTION");
                    break;
            }
        }

    }

    public static void logIn() {
        boolean correctData = false;
        int attempts = 5;

        UserInSession.getInstance().logout();

        if(DialogHelper.optionD("Select log in method",new String[]{"Username","Control number"})==0) {
            do {
                DialogHelper.info("Log in to continue, you have %d attempts: "+attempts);

                String controlNumber = DialogHelper.stringIn("Enter your control number: ");

                String password = DialogHelper.stringIn("Enter your password: ");

                User currentUser = Mindbox.verifyLogin(controlNumber, password,true);

                if (currentUser != null && attempts > 0) {
                    UserInSession.getInstance().setUser(currentUser);
                    selectMenu();
                    attempts = 5;
                } else if (attempts == 1) {
                    DialogHelper.error("ATTEMPTS EXHAUSTED, ENDING THE PROGRAM.");
                    Mindbox.saveJson();
                    correctData = true;
                } else {
                    DialogHelper.error("INCORRECT DATA.");
                    attempts--;
                }

            } while (!correctData);
        }else{
            do {
                DialogHelper.info("Log in to continue, you have %d attempts: "+attempts);

                String username = DialogHelper.stringIn("Enter your username: ");

                String password = DialogHelper.stringIn("Enter your password: ");

                User currentUser = Mindbox.verifyLogin(username, password,false);

                if (currentUser != null && attempts > 0) {
                    UserInSession.getInstance().setUser(currentUser);
                    selectMenu();
                    attempts = 5;
                } else if (attempts == 1) {
                    DialogHelper.error("ATTEMPTS EXHAUSTED, ENDING THE PROGRAM.");
                    Mindbox.saveJson();
                    correctData = true;
                } else {
                    DialogHelper.error("INCORRECT DATA.");
                    attempts--;
                }

            } while (!correctData);
        }
    }

    private static void selectMenu() {
        switch (UserInSession.getInstance().getCurrentUser().getRole()) {
            case STUDENT -> executeStudentMenu();
            case COORDINATOR -> executeCoordinatorMenu();
            case TEACHER -> executeTeacherMenu();
        }
    }

    private static void executeStudentMenu() {
        Student student = (Student) UserInSession.getInstance().getCurrentUser();
        DialogHelper.info("Welcome Student " + student.getFirstName() + " " + student.getLastName());
        boolean flag = false;

        do {
            switch (DialogHelper.optionD("What do you wanna do?", "1. View grades\n2. Show current subjects\n3.View history\n4. Show my info", new String[]{"1", "2", "3", "4", "Exit"})) {
                case 0:
                    student.showGrades();
                case 1:
                    student.showCurrentClasses();
                case 2:
                    student.showHistory();
                case 3:
                    DialogHelper.info(student.toString());
                    break;
                case 4:
                    DialogHelper.info("Logging out...");
                    flag = true;
            }
        }while (!flag) ;
        UserInSession.getInstance().logout();
    }

    private static void executeCoordinatorMenu() {
        Coordinator coordinator = (Coordinator) UserInSession.getInstance().getCurrentUser();
        DialogHelper.info("Welcome Coordinator " + coordinator.getFirstName() + " " + coordinator.getLastName());
        do {
            switch (DialogHelper.optionD("1. Assign grades\n2. View all my current subjects\n3. Observer students by filter\n4. Promote Group\n5. Student Options\n6. Teacher Options\n7. Show all Groups\n8. Show Graduated Students\n9. Show my info",new String[]{"1","2","3","4","5","6","7","8","9","Exit"})) {
                case 0:
                    DialogHelper.info("Selected: Assign grades");
                    coordinator.assignGrade();
                    break;
                case 1:
                    DialogHelper.info("Showing all your current subjects.");
                    coordinator.showMySubjects();
                    System.out.print("-------------------------------------------------\n");
                    break;
                case 2:
                    if (coordinator.getSubjects().isEmpty()) {
                        DialogHelper.error("You have no subjects assigned.");
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
                            switch (filterOption.toUpperCase()) {
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
                case 3:
                    System.out.println("Selected: Promote Group");
                    Coordinator.promoteGroup();
                    System.out.print("-------------------------------------------------\n");
                    break;
                case 4:
                    do {
                        System.out.println("1. Register Student");
                        System.out.println("2. Modify Student");
                        System.out.println("3. Delete Student");
                        System.out.println("4. Show Students");
                        System.out.println("E. Exit the menu");
                        System.out.print("Enter an option:");
                        option = reader.nextLine();
                        switch (option.toUpperCase()) {
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
                case 5:
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
                        switch (option.toUpperCase()) {
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
                case 6:
                    Group.showAllGroups();
                    System.out.print("-------------------------------------------------\n");
                    break;
                case 7:
                    Graduates.showGraduates();
                    System.out.print("-------------------------------------------------\n");
                    break;
                case 8:
                    System.out.println(coordinator.toString());
                    System.out.print("-------------------------------------------------\n");
                    break;
                case 9: DialogHelper.returnD();
            }
            }while (!option.equalsIgnoreCase("E"));
            UserInSession.getInstance().logout();
            System.out.println("Logging Out...");
            System.out.print("-------------------------------------------------\n");
        }

        private static void executeTeacherMenu() {
            String option;
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
                                switch (filterOption.toUpperCase()) {
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
