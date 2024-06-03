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


    public static void showMenu() {
        boolean flag = true;
        do {
            switch (DialogHelper.optionD("Welcome to the Mindbox.", new String[]{"Log in", "Exit"})) {
                case 0 -> {
                    logIn();
                }
                case 1 -> {
                    Mindbox.saveJson();
                    flag = false;
                }
            }
        } while (flag);
    }

    public static void logIn() {
        boolean correctData = false;
        int attempts = 5;


        UserInSession.getInstance().logout();

        if(DialogHelper.optionD("Select log in method",new String[]{"Username","Control number"})==0) {
            do {
                DialogHelper.info(String.format("Log in to continue, you have %s attempts",attempts));

                String controlNumber = DialogHelper.stringIn("Enter your control number:\n 0 To cancel");
                if(controlNumber.equals("0")) break;
                String password = DialogHelper.stringIn("Enter your password: ");

                User currentUser = Mindbox.verifyLogin(controlNumber, password,true);

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
        }else{
            do {
                DialogHelper.info("Log in to continue, you have %d attempts: "+attempts);

                String username = DialogHelper.stringIn("Enter your username:\n0 To cancel ");
                if(username.equals("0")) break;
                String password = DialogHelper.stringIn("Enter your password: ");

                User currentUser = Mindbox.verifyLogin(username, password,false);

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
        boolean flag = true;
        Coordinator coordinator = (Coordinator) UserInSession.getInstance().getCurrentUser();
        DialogHelper.info("Welcome Coordinator " + coordinator.getFirstName() + " " + coordinator.getLastName());
        do {
            switch (DialogHelper.optionD("1. Assign grades\n2. View all my current subjects\n3. Observer students by filter\n4. Promote Group\n5. Student Options\n6. Teacher Options\n7. Show all Groups\n8. Show Graduated Students\n9. Show my info",new String[]{"1","2","3","4","5","6","7","8","9","Exit"})) {
                case 0 -> {
                    DialogHelper.info("Selected: Assign grades");
                    coordinator.assignGrade();
                }
                case 1 -> {
                    DialogHelper.info("Showing all your current subjects.");
                    coordinator.showMySubjects();
                }
                case 2-> {
                    if (coordinator.getSubjects().isEmpty()) {
                        DialogHelper.error("You have no subjects assigned.");
                    } else {
                        DialogHelper.info("Observe grades by filter.");
                        boolean exit = false;
                        do {
                            switch (DialogHelper.optionD("Which filter do you want", new String[]{"Semester", "Group", "Subject", "Return"})) {
                                case 0 -> coordinator.filterBySemester();
                                case 1 -> coordinator.filterByGroup();
                                case 2 -> coordinator.filterBySubject();

                                case 3 -> {
                                    DialogHelper.returnD();
                                    exit = true;
                                }
                            }  //Como que no se actualiza?
                        } while (!exit);
                        break;
                    }
                }case 3 -> Coordinator.promoteGroup();
                case 4 -> {
                    boolean flag2 = true;
                    do {
                        switch (DialogHelper.optionD("Student options menu", new String[]{"Register", "Modify", "Delete", "View", "Return"})) {
                            case 0 -> Mindbox.registerStudent();
                            case 1 -> Mindbox.updateStudent();
                            case 2 -> Mindbox.expelStudent();
                            case 3 -> Mindbox.showAllStudents();
                            case 4 -> {
                                DialogHelper.returnD();
                                flag2 = false;
                            }
                        }
                    }while (flag2);
                }case 5 -> {
                    boolean flag2 = true;
                    do {
                        System.out.println("1. Register Teacher");
                        System.out.println("2. Modify Teacher");
                        System.out.println("3. Delete Teacher");
                        System.out.println("4. Show Teachers");
                        System.out.println("5. Add a teacher to a subject.");
                        System.out.println("6. Remove a teacher from a subject.");
                        System.out.println("E. Exit the menu");
                        System.out.print("Enter an option:");
                        switch (DialogHelper.optionD("Teacher options menu",new String[]{"Register","Modify","Delete","Show","Add to","Remove from","Exit"})) {
                            case 0 -> Mindbox.registerTeacher();
                            case 1 -> Mindbox.updateTeacher();
                            case 2 -> Mindbox.deleteTeacher();
                            case 3 -> Mindbox.showAllTeachers();
                            case 4 -> Mindbox.assignTeacherToSubject();
                            case 5 -> Mindbox.removeTeacherFromSubject();
                            case 6 -> {
                                DialogHelper.returnD();
                                flag2 = false;
                            }
                        }
                    } while (flag2);
                }case 6 -> Group.showAllGroups();
                case 7 -> Graduates.showGraduates();
                case 8 -> DialogHelper.info(coordinator.toString());
                case 9 -> {DialogHelper.warning("Logging out..."); flag = false; ;}
            }
        }while (flag);
        UserInSession.getInstance().logout();
    }

    private static void executeTeacherMenu() {
        boolean flag = true;
        Worker teacher = (Worker) UserInSession.getInstance().getCurrentUser();
        DialogHelper.info("Welcome Teacher " + teacher.getFirstName() + " " + teacher.getLastName());
        do {
            switch (DialogHelper.optionD("What do you wanna do?\n1. View all my current subjects\n2. Filter students\n3. Assign grades\n4. Show my information",new String[]{"1","2","3","4","Log out"})) {
                case 0 -> teacher.showMySubjects();
                case 1 -> {
                    if (teacher.getSubjects().isEmpty()) {
                        DialogHelper.error("No filters available, you have no groups assigned.");
                    } else {
                        boolean exit = false;
                        do {
                            switch (DialogHelper.optionD("Filter selection",new String[]{"Semester","Group","Subject","Return"})) {
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
                }case 2 -> teacher.assignGrade();
                case 3 -> DialogHelper.info(teacher.toString());
                case 4 -> {
                    DialogHelper.warning("Logging out...");
                    UserInSession.getInstance().logout();
                    flag = false;
                }
            }
        } while (flag);
    }
}
