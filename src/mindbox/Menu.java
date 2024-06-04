package mindbox;

import academicinfo.*;
import users.*;
import utils.*;

public class Menu {

    public static void showMenuFirstTime() {

        while (true) {
            Mindbox.loadJson();

            DialogHelper.info("WELCOME TO THE MINDBOX SYSTEM OF ITM.");

            int choice = DialogHelper.optionD("Select an option:", new String[]{"Log In", "Exit"});

            switch (choice) {
                case 0:
                    logIn();
                    break;
                case 1:
                    Mindbox.saveJson();
                    System.exit(0);
                    break;
            }
        }
    }


    public static void showMenu() {

        while (true) {
            int choice = DialogHelper.optionD("Select an option:", new String[]{"Log In", "Exit"});

            switch (choice) {
                case 0:
                    logIn();
                    break;
                case 1:
                    Mindbox.saveJson();
                    System.exit(0);
                    break;
            }
        }
    }


    public static void logIn() {
        int attempts = 5;

        UserInSession.getInstance().logout();

        do {
            DialogHelper.info(String.format("Log in to continue, you have %s attempts",attempts));

            String controlNumber = DialogHelper.stringIn("Enter your control number:\n 0 To cancel");
            if(controlNumber.equals("0")) break;
            String password = DialogHelper.stringIn("Enter your password: ");

            User currentUser = Mindbox.verifyLogin(controlNumber, password);

            if (currentUser != null && attempts > 0) {
                UserInSession.getInstance().setUser(currentUser);
                selectMenu();
                attempts = 5;
            } else if (attempts == 1) {
                DialogHelper.error("ATTEMPTS EXHAUSTED, ENDING THE PROGRAM.");
                Mindbox.saveJson();
                break;
            } else {
                DialogHelper.error("INCORRECT DATA.");
                attempts--;
            }
        } while (true);
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
                    student.showCurrentSubjects();
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
        showMenu();
    }

    private static void executeCoordinatorMenu() {
        Coordinator coordinator = (Coordinator) UserInSession.getInstance().getCurrentUser();
        DialogHelper.info("Welcome Coordinator " + coordinator.getFirstName() + " " + coordinator.getLastName());
        boolean flag = true;
        do {
            switch (DialogHelper.optionD("What do you wanna do?",
                    new String[]{"Assign grades", "View all my current subjects", "Observe students by filter",
                            "Promote Group", "Student Options", "Teacher Options", "Show all Groups", "Show Graduated Students",
                            "Show my information", "Exit"})) {
                case 0 -> {
                    DialogHelper.info("Selected: Assign grades");
                    coordinator.assignGrade();
                }
                case 1 -> {
                    DialogHelper.info("Showing all your current subjects.");
                    coordinator.showMySubjects();
                }
                case 2 -> {
                    if (coordinator.getSubjects().isEmpty()) {
                        DialogHelper.info("You have no subjects assigned.");
                    } else {
                        boolean exit = true;
                        do {
                            switch (DialogHelper.optionD("Observe grades by filter",
                                    new String[]{"By semester", "By group", "By subject", "Exit"})) {
                                case 0:
                                    coordinator.filterBySemester();
                                    break;
                                case 1:
                                    coordinator.filterByGroup();
                                    break;
                                case 2:
                                    coordinator.filterBySubject();
                                    break;
                                case 3:
                                    exit = false;
                                    break;
                            }
                        } while (exit);
                    }
                }
                case 3 -> {
                    DialogHelper.info("Selected: Promote Group");
                    Coordinator.promoteGroup();
                }
                case 4 -> {
                    boolean aux = true;
                    do {
                        switch (DialogHelper.optionD("Student Options",
                                new String[]{"Register Student", "Modify Student", "Delete Student", "Show Students", "Exit"})) {
                            case 0:
                                Mindbox.registerStudent();
                                break;
                            case 1:
                                Mindbox.updateStudent();
                                break;
                            case 2:
                                Mindbox.expelStudent();
                                break;
                            case 3:
                                Mindbox.showAllStudents();
                                break;
                            case 4:
                                DialogHelper.returnD();
                                aux = false;
                        }
                    } while (aux);
                }
                case 5 -> {
                boolean aux = true;
                do {
                    switch (DialogHelper.optionD("Teacher Options", new String[]{"Register Teacher", "Modify Teacher", "Delete Teacher", "Show Teachers", "Add teacher to subject", "Remove teacher from subject", "Exit"})) {
                        case 0:
                            Mindbox.registerTeacher();
                            break;
                        case 1:
                            Mindbox.updateTeacher();
                            break;
                        case 2:
                            Mindbox.deleteTeacher();
                            break;
                        case 3:
                            Mindbox.showAllTeachers();
                            break;
                        case 4:
                            Mindbox.assignTeacherToSubject();
                            break;
                        case 5:
                            Mindbox.removeTeacherFromSubject();
                            break;
                        case 6:
                            aux = false;
                            DialogHelper.returnD();
                    }
                } while (aux);
            }
                case 6 -> Group.showAllGroups();
                case 7 -> Graduates.showGraduates();
                case 8 -> DialogHelper.info(coordinator.toString());
                case 9 -> {
                    DialogHelper.returnD();
                    flag = false;
                }
            }
        } while (flag);
        UserInSession.getInstance().logout();
        DialogHelper.info("Logging Out...");
        showMenu();
    }


    private static void executeTeacherMenu() {
        Worker teacher = (Worker) UserInSession.getInstance().getCurrentUser();
        DialogHelper.info("Welcome Teacher " + teacher.getFirstName() + " " + teacher.getLastName());
        boolean flag = true;
        do {
            switch (DialogHelper.optionD("What do you wanna do?",
                    new String[]{"View all my current subjects", "Observe students by filter", "Assign Grades",
                            "Show my information", "Exit"})) {
                case 0 -> {
                    DialogHelper.info("Showing all your current subjects.");
                    teacher.showMySubjects();
                }
                case 1 -> {
                    if (teacher.getSubjects().isEmpty()) {
                        DialogHelper.error("No filters available, you have no groups assigned.");
                    } else {
                        boolean exit = false;
                        do {
                            switch (DialogHelper.optionD("Observe grades by filter",
                                    new String[]{"By semester", "By group", "By subject", "Exit"})) {
                                case 0 -> teacher.filterBySemester();
                                case 1 -> teacher.filterByGroup();
                                case 2 -> teacher.filterBySubject();
                                case 3 -> {
                                    DialogHelper.returnD();
                                    exit = true;
                                }
                            }
                        } while (!exit);
                    }
                }
                case 2 -> {
                    DialogHelper.info("Selected: Assign grades");
                    teacher.assignGrade();
                }
                case 3 ->{
                    DialogHelper.info(teacher.toString());
                }
                case 4 -> {
                    DialogHelper.info("Logging out...");
                    UserInSession.getInstance().logout();
                    showMenu();
                    flag = false;
                }

            }
        }while (flag);
    }

}
