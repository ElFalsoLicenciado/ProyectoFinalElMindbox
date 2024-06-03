package users;
import java.time.LocalDate;
import java.util.*;

import academicinfo.*;
import mindbox.*;
import users.*;
import users.utils.*;
import utils.*;

import static users.Coordinator.showAllCoordinators;

public class Worker extends User {

    protected ArrayList<String> subjects;
    protected String RFC;
    protected int salary;

    public Worker(String firstName, String lastName1,String lastName2, String birthDate, String city, String state, String curp, String address, Careers career, String controlNumber,String username, String password, Role role, String RFC, int salary) {
        super(firstName, lastName1,lastName2, birthDate, city, state, curp, address, career, controlNumber,username, password, role);
        this.RFC = RFC;
        this.salary = salary;
        this.subjects = new ArrayList<>();
    }

    public String toString() {
        return String.format(
                "Name: %s, Last Name: %s, Control Number: %s, Birth Date: %s, City: %s, State: %s, CURP: %s, Address: %s, Registration Date: %s, Career: %s, RFC: %s, Salary: %.2f",
                getFirstName(), getLastName(), getControlNumber(), getBirthDate(), getCity(), getState(), getCurp(), getAddress(), getRegistrationDate(), getCareer(), RFC, salary
        );
    }

    public void filterBySemester() {
        System.out.println("Filter by Semester");
        Scanner sc = new Scanner(System.in);
        int semesterNumber = 0;
        boolean valid = false;
        do {
            System.out.println("Which semester grades do you want to view?");
            System.out.println("[1] First semester");
            System.out.println("[2] Second semester");
            System.out.println("[3] Third semester");
            String option = sc.nextLine();
            switch (option) {
                case "1":
                    semesterNumber = 1;
                    valid = true;
                    break;
                case "2":
                    semesterNumber = 2;
                    valid = true;
                    break;
                case "3":
                    semesterNumber = 3;
                    valid = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        } while (!valid);

        Semester semester = Semester.getSemesterByNumber(semesterNumber);

        if (semester != null) {
            boolean subjectsFound = false;
            ArrayList<String> passed = new ArrayList<>();
            ArrayList<String> failed = new ArrayList<>();
            ArrayList<String> all = new ArrayList<>();
            for (Group group : semester.getGroups()) {
                ArrayList<Subject> subjects = group.getSubjects();
                for (Subject subject : subjects) {
                    if (subject.getTeacher() != null) {
                        if (Objects.equals(Mindbox.getWorker(subject.getTeacher()), UserInSession.getInstance().getCurrentUser())) {
                            subjectsFound = true;
                            ArrayList<String> checkPassed = subject.getApprovedStudents(semester.getId());
                            for (String student : checkPassed) {
                                if (!passed.contains(student)) {
                                    passed.add(student);
                                }
                            }
                            ArrayList<String> checkFailed = subject.getFailedStudents(semester.getId());
                            for (String student : checkFailed) {
                                if (!failed.contains(student)) {
                                    failed.add(student);
                                }
                            }
                            ArrayList<String> checkAll = subject.getStudentsInSubject();
                            for (String student : checkAll) {
                                if (!all.contains(student)) {
                                    all.add(student);
                                }
                            }
                        }
                    }
                }
            }
            if (subjectsFound) {
                boolean exit = false;
                do {
                    System.out.println("What type of students do you want to see?");
                    System.out.println("1. Passed.");
                    System.out.println("2. Failed.");
                    System.out.println("3. All.");
                    System.out.println("E. Exit the menu.");
                    String option = sc.nextLine();
                    switch (option) {
                        case "1":
                            System.out.println("All passed students:");
                            for (String student : passed) {
                                System.out.println(Objects.requireNonNull(Mindbox.getStudent(student)).toString());
                                System.out.println("Group: " + Objects.requireNonNull(Mindbox.getStudent(student)).getGroup());
                                System.out.println();
                            }
                            break;
                        case "2":
                            System.out.println("All failed students:");
                            for (String student : failed) {
                                System.out.println(Objects.requireNonNull(Mindbox.getStudent(student)).toString());
                                System.out.println("Group: " + Objects.requireNonNull(Mindbox.getStudent(student)).getGroup());
                                System.out.println();
                            }
                            break;
                        case "3":
                            System.out.println("All students:");
                            for (String student : all) {
                                System.out.println(Objects.requireNonNull(Mindbox.getStudent(student)).toString());
                                System.out.println("Group: " + Objects.requireNonNull(Mindbox.getStudent(student)).getGroup());
                                System.out.println();
                            }
                            break;
                        case "E":
                            exit = true;
                            break;
                        default:
                            System.out.println("Invalid option.");
                            break;
                    }
                } while (!exit);
            } else {
                System.out.println("There are no subjects you are teaching in that semester.");
            }
        } else {
            System.out.println("Semester not found.");
        }
    }

    public void filterByGroup() {
        System.out.println("Filter by Group");
        Scanner sc = new Scanner(System.in);
        int semesterNumber = 0;
        String groupType = "";
        boolean valid = false, validGroup = false;
        do {
            System.out.println("Which semester do you want to get the group from?");
            System.out.println("[1] First semester");
            System.out.println("[2] Second semester");
            System.out.println("[3] Third semester");
            String option = sc.nextLine();
            switch (option) {
                case "1":
                    semesterNumber = 1;
                    valid = true;
                    break;
                case "2":
                    semesterNumber = 2;
                    valid = true;
                    break;
                case "3":
                    semesterNumber = 3;
                    valid = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        } while (!valid);
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
                        System.out.println("Invalid option.");
                }
            } while (!validGroup);
            Group group = Group.getGroup(groupType, semester);
            if (group != null) {
                ArrayList<Subject> subjects = group.getSubjects();
                ArrayList<String> passed = new ArrayList<>();
                ArrayList<String> failed = new ArrayList<>();
                ArrayList<String> all = new ArrayList<>();
                boolean subjectsFound = false;
                for (Subject subject : subjects) {
                    if (subject.getTeacher() != null) {
                        if (Objects.equals(Mindbox.getWorker(subject.getTeacher()), UserInSession.getInstance().getCurrentUser())) {
                            subjectsFound = true;
                            ArrayList<String> checkPassed = subject.getApprovedStudents(semester.getId());
                            for (String student : checkPassed) {
                                if (!passed.contains(student)) {
                                    passed.add(student);
                                }
                            }
                            ArrayList<String> checkFailed = subject.getFailedStudents(semester.getId());
                            for (String student : checkFailed) {
                                if (!failed.contains(student)) {
                                    failed.add(student);
                                }
                            }
                            ArrayList<String> checkAll = subject.getStudentsInSubject();
                            for (String student : checkAll) {
                                if (!all.contains(student)) {
                                    all.add(student);
                                }
                            }
                        }
                    }
                }
                if (subjectsFound) {
                    boolean exit = false;
                    do {
                        System.out.println("What type of students do you want to see?");
                        System.out.println("1. Passed.");
                        System.out.println("2. Failed.");
                        System.out.println("3. All.");
                        System.out.println("E. Exit the menu.");
                        String option = sc.nextLine();
                        switch (option) {
                            case "1":
                                System.out.println("All passed students:");
                                for (String student : passed) {
                                    System.out.println(Objects.requireNonNull(Mindbox.getStudent(student)).toString());
                                    System.out.println("Group: " + Objects.requireNonNull(Mindbox.getStudent(student)).getGroup());
                                    System.out.println();
                                }
                                break;
                            case "2":
                                System.out.println("All failed students:");
                                for (String student : failed) {
                                    System.out.println(Objects.requireNonNull(Mindbox.getStudent(student)).toString());
                                    System.out.println("Group: " + Objects.requireNonNull(Mindbox.getStudent(student)).getGroup());
                                    System.out.println();
                                }
                                break;
                            case "3":
                                System.out.println("All students:");
                                for (String student : all) {
                                    System.out.println(Objects.requireNonNull(Mindbox.getStudent(student)).toString());
                                    System.out.println("Group: " + Objects.requireNonNull(Mindbox.getStudent(student)).getGroup());
                                    System.out.println();
                                }
                                break;
                            case "E":
                                exit = true;
                                break;
                            default:
                                System.out.println("Invalid option.");
                                break;
                        }
                    } while (!exit);
                }
            } else {
                System.out.println("Group not found.");
            }
        } else {
            System.out.println("Semester not found.");
        }
    }

    public void filterBySubject() {
        System.out.println("Filter by Subject");
        Scanner sc = new Scanner(System.in);
        boolean exit = false;
        String subjectId = null;
        do {
            showMySubjects();
            System.out.println("Enter the name of the subject you want to filter:");
            String option = sc.nextLine();
            for (String subject : subjects) {
                if (subject.equals(option)) {
                    subjectId = subject;
                    exit = true;
                    break;
                }
            }
        } while (!exit);
        exit = false;
        Semester semester = Mindbox.getSemester(Mindbox.getGroup(Mindbox.getSubject(subjectId).getGroup()).getSemester());
        do {
            System.out.println("What type of students do you want to see?");
            System.out.println("1. Passed.");
            System.out.println("2. Failed.");
            System.out.println("3. All.");
            System.out.println("E. Exit the menu.");
            String option = sc.nextLine();
            switch (option) {
                case "1":
                    System.out.println("Passed students in the subject " + Mindbox.getSubject(subjectId).getId());
                    ArrayList<String> checkPassed = Mindbox.getSubject(subjectId).getApprovedStudents(semester.getId());
                    for (String student : checkPassed) {
                        System.out.println(Mindbox.getStudent(student).toString());
                        System.out.println("Group: " + Mindbox.getStudent(student).getGroup());
                        String grade = "";
                        if (Mindbox.getStudent(student).getGrades().get(semester.getId()).get(Mindbox.getSubject(subjectId).getId()) == null) {
                            grade = "Not assigned.";
                        } else {
                            grade = String.valueOf(Mindbox.getStudent(student).getGrades().get(semester.getId()).get(Mindbox.getSubject(subjectId).getId()));
                        }
                        System.out.println("Grade: " + grade);
                        System.out.println();
                    }
                    break;
                case "2":
                    System.out.println("Failed students in the subject " + Mindbox.getSubject(subjectId).getId());
                    ArrayList<String> checkFailed = Mindbox.getSubject(subjectId).getFailedStudents(semester.getId());
                    for (String student : checkFailed) {
                        System.out.println(Mindbox.getStudent(student).toString());
                        System.out.println("Group: " + Mindbox.getStudent(student).getGroup());
                        String grade = "";
                        if (Mindbox.getStudent(student).getGrades().get(semester.getId()).get(Mindbox.getSubject(subjectId).getId()) == null) {
                            grade = "Not assigned.";
                        } else {
                            grade = String.valueOf(Mindbox.getStudent(student).getGrades().get(semester.getId()).get(Mindbox.getSubject(subjectId).getId()));
                        }
                        System.out.println("Grade: " + grade);
                        System.out.println();
                    }
                    break;
                case "3":
                    System.out.println("All students in the subject " + Mindbox.getSubject(subjectId).getId());
                    ArrayList<String> checkAll = Mindbox.getSubject(subjectId).getStudentsInSubject();
                    for (String student : checkAll) {
                        System.out.println(Mindbox.getStudent(student).toString());
                        System.out.println("Group: " + Mindbox.getStudent(student).getGroup());
                        String grade = "";
                        if (Mindbox.getStudent(student).getGrades().get(semester.getId()).get(Mindbox.getSubject(subjectId).getId()) == null) {
                            grade = "Not assigned.";
                        } else {
                            grade = String.valueOf(Mindbox.getStudent(student).getGrades().get(semester.getId()).get(Mindbox.getSubject(subjectId).getId()));
                        }
                        System.out.println("Grade: " + grade);
                        System.out.println();
                    }
                    break;
                case "E":
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        } while (!exit);
    }

    public void showMySubjects() {
        if (subjects.isEmpty()) {
            System.out.println("You currently have no subjects assigned.");
            System.out.println();
        } else {
            for (String subject : subjects) {
                System.out.println(Mindbox.getSubject(subject).toString());
            }
        }
    }

    public static Worker getWorker() {
        Scanner sc = new Scanner(System.in);
        String controlNumber = "";
        boolean found = false;
        Worker teacher = null;
        do {
            Teacher.showAllTeachers();
            showAllCoordinators();
            System.out.println("Enter the control number of the teacher you want:");
            controlNumber = sc.nextLine();
            for (User user : Mindbox.users.get(Role.COORDINATOR)) {
                if (user.getControlNumber().equals(controlNumber)) {
                    found = true;
                    teacher = (Worker) user;
                    break;
                }
            }
            for (User user : Mindbox.users.get(Role.TEACHER)) {
                if (user.getControlNumber().equals(controlNumber)) {
                    found = true;
                    teacher = (Worker) user;
                    break;
                }
            }
        } while (!found);
        return teacher;
    }

    public String getSubject() {
        Scanner sc = new Scanner(System.in);
        int option = 0;
        boolean valid = true;
        String id = "";
        if (subjects.isEmpty()) {
            System.out.println("You have no subjects assigned.");
            return null;
        }
        do {
            for (String subjectId : subjects) {
                Subject subject = Mindbox.getSubject(subjectId);
                System.out.println("[ " + subjects.indexOf(subject.getId()) + " ]" + " Subject: " + subject.getId() + " Career: " + subject.getCareer().getFullName() + " Semester: " + Mindbox.getSemester(Mindbox.getGroup(subject.getGroup()).getSemester()).getId() + " Group: " + Mindbox.getGroup(subject.getGroup()).getGroupType());
            }
            try {
                option = sc.nextInt();
                id = subjects.get(option);
                valid = false;
            } catch (InputMismatchException e) {
                System.out.println("The value must be an integer.");
                sc.nextLine();
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Enter a valid index.");
                sc.nextLine();
            }
        } while (valid);
        return id;
    }

    public void assignGrade() {
        Scanner sc = new Scanner(System.in);
        int grade = 0;
        boolean valid = true, continueAssigning = true;
        Subject subject = Mindbox.getSubject(getSubject());
        if (subject == null) {
            return;
        }
        Group group = Mindbox.getGroup(subject.getGroup());
        if (group.getStudentList().isEmpty()) {
            DialogHelper.error("There are no students in this subject for now.");
            return;
        }
        if (group.getStudentList().size() < 3) {
            DialogHelper.error("The group does not have at least 3 students.");
            return;
        }
        do {
            Student student = Mindbox.getStudent(subject.getStudent(subject.getId()));
            assert student != null;
            HashMap<String, Integer> semesterGrades = student.getGrades().get(student.getSemester());
            if (semesterGrades.get(subject.getId()) != null) {
                int existingGrade = semesterGrades.get(subject.getId());
                do {
                    if (DialogHelper.confirmD("\"The student already has an assigned grade: " + existingGrade+ "\nDo you want to modify the grade?")) {
                    } else {
                        break;
                    }
                } while (true);
            }
            do {
                System.out.println("Enter the grade to assign:");
                try {
                    grade = sc.nextInt();
                    if (grade < 0 || grade > 100) {
                        throw new Exception("");
                    }
                    valid = false;
                } catch (InputMismatchException e) {
                    System.out.println("The value must be an integer.");
                    sc.nextLine();
                } catch (Exception e) {
                    System.out.println("The value must be between 0 and 100.");
                    sc.nextLine();
                }
            } while (valid);
            student.getGrades().get(student.getSemester()).remove(subject.getId());
            student.getGrades().get(student.getSemester()).put(subject.getId(), grade);
            System.out.println("Grade changed successfully.");
            sc.nextLine();
            System.out.println("Do you want to continue assigning grades? [Y]/[N]");
            String response = "";

            do {
                response = sc.nextLine();
                if (response.equalsIgnoreCase("n")) {
                    continueAssigning = false;
                    break;
                } else if (!response.equalsIgnoreCase("y")) {
                    System.out.println("Invalid option. Please enter [Y] or [N].");
                }
            } while (!response.equalsIgnoreCase("y") && !response.equalsIgnoreCase("n"));
        } while (continueAssigning);
    }

    // Getters
    public ArrayList<String> getSubjects() {
        return subjects;
    }

    public String getRFC() {
        return RFC;
    }

    public float getSalary() {
        return salary;
    }

    // Setters
    public void setSubjects(ArrayList<String> subjects) {
        this.subjects = subjects;
    }

    public void setRFC(String RFC) {
        this.RFC = RFC;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}

