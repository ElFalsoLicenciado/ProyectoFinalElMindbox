package users;

import academicinfo.*;
import mindbox.*;
import users.*;
import users.utils.*;
import utils.*;

import java.time.LocalDate;
import java.util.*;

public class Coordinator extends Worker {
    private int coord = 5;

    public Coordinator(String name, String lastName, String birthDate, String city, String state, String curp, String address, Careers career, String controlNumber, String password, String RFC, float salary) {
        super(name, lastName, birthDate, city, state, curp, address, career, controlNumber, password, Role.COORDINATOR, RFC, salary);
        this.coord = 0;
    }

    public static void assignTeacherToSubject() {
        Scanner sc = new Scanner(System.in);
        int semesterNumber = 0;
        String groupType = "";
        boolean validSemester = false, validGroup = false;
        Careers career = UserInSession.getInstance().getCurrentUser().getCareer();
        do {
            System.out.println("Which semester do you want to assign a teacher to [1], [2], [3] ?");
            String option = sc.nextLine().trim();
            switch (option) {
                case "1":
                    semesterNumber = 1;
                    validSemester = true;
                    break;
                case "2":
                    semesterNumber = 2;
                    validSemester = true;
                    break;
                case "3":
                    semesterNumber = 3;
                    validSemester = true;
                    break;
                default:
                    System.out.println("INVALID OPTION.");
            }
        } while (!validSemester);
        Semester semester = Semester.getSemesterByNumber(semesterNumber);
        if (semester != null) {
            do {
                System.out.println("Which group do you want?");
                System.out.println("[1] A");
                System.out.println("[2] B");
                String option = sc.nextLine();
                switch (option) {
                    case "1":
                        groupType = "A";
                        validGroup = true;
                        break;
                    case "2":
                        groupType = "B";
                        validGroup = true;
                        break;
                    default:
                        System.out.println("INVALID OPTION.");
                }
            } while (!validGroup);
            Group group = Group.getGroup(groupType, semester);
            if (group != null) {
                Subject subject = Subject.getSubject(semester.getId(), group.getGroupId());
                if (subject != null) {
                    Worker teacher = Worker.getWorker();
                    if (teacher != null) {
                        Worker checkTeacher = Mindbox.getWorker(subject.getTeacher());
                        if (checkTeacher == null) {
                            subject.setTeacher(teacher.getControlNumber());
                            teacher.getSubjects().add(subject.getId());
                            System.out.println("Teacher successfully assigned.");
                        } else {
                            System.out.println("The subject already has a teacher assigned, you cannot assign another.");
                        }
                    }
                }
            } else {
                System.out.println("Group not found.");
            }
        } else {
            System.out.println("Semester not found.");
        }
    }

    public static void removeTeacherFromSubject() {
        Scanner sc = new Scanner(System.in);
        int semesterNumber = 0;
        String groupType = "";
        boolean validSemester = false, validGroup = false;
        Careers career = UserInSession.getInstance().getCurrentUser().getCareer();
        do {
            System.out.println("From which semester do you want to remove a teacher [1], [2], [3] ?");
            String option = sc.nextLine();
            switch (option) {
                case "1":
                    semesterNumber = 1;
                    validSemester = true;
                    break;
                case "2":
                    semesterNumber = 2;
                    validSemester = true;
                    break;
                case "3":
                    semesterNumber = 3;
                    validSemester = true;
                    break;
                default:
                    System.out.println("INVALID OPTION.");
            }
        } while (!validSemester);
        Semester semester = Semester.getSemesterByNumber(semesterNumber);
        if (semester != null) {
            do {
                System.out.println("Which group do you want?");
                System.out.println("[1] A");
                System.out.println("[2] B");
                String option = sc.nextLine();
                switch (option) {
                    case "1":
                        groupType = "A";
                        validGroup = true;
                        break;
                    case "2":
                        groupType = "B";
                        validGroup = true;
                        break;
                    default:
                        System.out.println("INVALID OPTION.");
                }
            } while (!validGroup);
            Group group = Group.getGroup(groupType, semester);
            if (group != null) {
                Subject subject = Subject.getSubject(semester.getId(), group.getGroupId());
                if (subject != null) {
                    Worker checkTeacher = Mindbox.getWorker(subject.getTeacher());
                    if (checkTeacher != null) {
                        System.out.println(checkTeacher.toString());
                        subject.removeTeacher();
                        checkTeacher.getSubjects().remove(subject.getId());
                        System.out.println("Teacher successfully removed.");
                    } else {
                        System.out.println("The subject has no teacher, you cannot remove anything.");
                    }
                }
            } else {
                System.out.println("Group not found.");
            }
        } else {
            System.out.println("Semester not found.");
        }
    }

    public static void showAllCoordinators() {
        ArrayList<User> coordinatorUsers = Mindbox.users.get(Role.COORDINATOR);
        System.out.println("Coordinators of Mindbox");
        for (User user : coordinatorUsers) {
            if (user.getCareer() == UserInSession.getInstance().getCurrentUser().getCareer()) {
                Coordinator coordinator = (Coordinator) user;
                System.out.println(coordinator.toString());
            }
        }
    }

    public static Coordinator getCoordinator() {
        Scanner sc = new Scanner(System.in);
        ArrayList<User> coordinators = Mindbox.users.get(Role.COORDINATOR);
        String controlNumber = "";
        boolean found = false;
        Coordinator selectedCoordinator = null;
        do {
            System.out.println("Enter the control number of the coordinator you want:");
            controlNumber = sc.nextLine();
            for (User user : coordinators) {
                if (user.getControlNumber().equals(controlNumber)) {
                    found = true;
                    selectedCoordinator = (Coordinator) user;
                    break;
                }
            }
        } while (!found);
        return selectedCoordinator;
    }

    public void updateCommonData() {
        showAllCoordinators();
        Coordinator coordinator = getCoordinator();
        CommonData.updateInformation(coordinator);
    }

    public static void promoteGroup() {
        Scanner sc = new Scanner(System.in);
        int semesterNumber = 0;
        String groupType;
        Careers career = UserInSession.getInstance().getCurrentUser().getCareer();
        Semester semester;

        do {
            try {
                System.out.println("From which semester do you want to promote the group: [1], [2], [3] ?");
                semesterNumber = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("The value must be an integer.");
                sc.nextLine();
            }
            if (semesterNumber == 1 || semesterNumber == 2 || semesterNumber == 3) {
                semester = Semester.getSemesterByNumber(semesterNumber);
                break;
            } else {
                System.out.println("Invalid option.");
            }
        } while (true);

        do {
            System.out.println("Enter the group you want to promote [A], [B]:");
            String input = sc.next();

            if (input.length() == 1) {
                groupType = String.valueOf(input.charAt(0));

                if (groupType.equals("A") || groupType.equals("B")) {
                    break;
                }
            }

            System.out.println("Invalid option. Please enter 'A' or 'B'.");
        } while (true);

        Group selectedGroup = null;

        for (Group group : semester.getGroups()) {
            if (group.getGroupType().equals(groupType)) {
                selectedGroup = group;
                break;
            }
        }

        if (selectedGroup.getStudentList().isEmpty()) {
            System.out.println("The group is empty.");
            return;
        }

        if (!selectedGroup.checkGrades()) {
            System.out.println("Not all students have grades or do not have grades in all subjects.");
            System.out.println();
            return;
        }
        int newSemesterNumber = semesterNumber + 1;

        ArrayList<String> failedStudents = selectedGroup.getFailedStudents();
        ArrayList<String> passedStudents = selectedGroup.getApprovedStudents();

        if (newSemesterNumber == 4) {
            System.out.println("The group graduates.");
            Graduates.registerGraduates(new Graduates(LocalDate.now().toString(), career, passedStudents, String.valueOf(LocalDate.now().getYear())));
            Iterator<String> iterator = selectedGroup.getStudentList().iterator();
            while (iterator.hasNext()) {
                String student = iterator.next();
                Mindbox.getStudent(student).setSemester(null);
                if (passedStudents.contains(student)) {
                    iterator.remove();
                }
            }
        } else {
            Semester newSemester = Semester.getSemesterByNumber(newSemesterNumber);

            for (Group group : newSemester.getGroups()) {
                if (group.getStudentList().isEmpty()) {
                    if (group.getGroupType().equals(groupType)) {

                        // Remove students from the previous group
                        Iterator<String> iterator = selectedGroup.getStudentList().iterator();
                        while (iterator.hasNext()) {
                            String student = iterator.next();
                            if (passedStudents.contains(student)) {
                                iterator.remove();
                            }
                        }

                        // Add passed students to the new group
                        for (String student : passedStudents) {
                            if (!group.getStudentList().contains(student)) {
                                group.getStudentList().add(student);
                                Mindbox.getStudent(student).setSemester(group.getSemester());
                                Mindbox.getStudent(student).setGroup(group.getGroupId());
                                Mindbox.getStudent(student).setNullGrades(Mindbox.getSemester(group.getSemester()), group);
                            }
                        }

                        System.out.println("The students of the group have been successfully promoted.");
                    }
                } else {
                    System.out.println("The group cannot be moved because it still contains students.");
                }
            }
        }
        for (String student : failedStudents) {
            HashMap<String, Integer> failedGrades = new HashMap<>();
            for (Subject subject : selectedGroup.getSubjects()) {
                failedGrades.put(subject.getId(), null);
            }
            Mindbox.getStudent(student).getGrades().put(semester.getId(), failedGrades);
        }
    }

    public int getCoord() {
        return coord;
    }

    public void setCoord(int coord) {
        this.coord = coord;
    }
}
