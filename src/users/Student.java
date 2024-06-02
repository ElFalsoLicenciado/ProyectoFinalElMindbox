package users;

import academicinfo.*;
import mindbox.*;
import users.*;
import users.utils.*;
import utils.*;
import java.util.*;

public class Student extends User {

    private String semester;
    private String group;
    private double average;
    private String groupType;

    // Semester, subject
    private HashMap<String, HashMap<String, Integer>> grades;

    public Student(String firstName, String lastName, String birthDate, String city, String state, String curp, String address, Careers career, String controlNumber, String password) {
        super(firstName, lastName, birthDate, city, state, curp, address, career, controlNumber, password, Role.STUDENT);
        this.grades = new HashMap<>();
    }

    public String toString() {
        return String.format(
                "Name: %s, Last Name: %s, Control Number: %s, Birth Date: %s, City: %s, State: %s, CURP: %s, Address: %s, Registration Date: %s, Career: %s",
                getFirstName(), getLastName(), getControlNumber(), getBirthDate(), getCity(), getState(), getCurp(), getAddress(), getRegistrationDate(), getCareer()
        );
    }

    public static void registerStudent() {
        System.out.println("You have selected the option to register a student.");
        ArrayList<String> commonData = CommonData.getCommonData(Role.STUDENT);

        String firstName = commonData.get(0);
        String lastName = commonData.get(1);
        String birthDate = commonData.get(2);
        String city = commonData.get(3);
        String state = commonData.get(4);
        String curp = commonData.get(5);
        String address = commonData.get(6);
        String password = commonData.get(7);
        String controlNumber = commonData.get(8);
        Careers career = commonData.get(9).equals("Sistemas") ? Careers.Systems : commonData.get(9).equals("Materiales") ? Careers.Materials : Careers.Electronics;

        Student student = new Student(firstName, lastName, birthDate, city, state, curp, address, career, controlNumber, password);
        Semester semester = Semester.getSemesterByNumber(1);
        if (semester == null) {
            System.out.println("Could not find the specified semester.");
            return;
        }
        student.setSemester(semester.getId());
        String groupType = getGroupType();
        semester.addStudentToGroup(student, groupType);

        for (Group group : semester.getGroups()) {
            if (group.getGroupType().equals(groupType)) {
                student.setGroupType(group.getGroupType());
                student.setGroup(group.getGroupId());
                student.setNullGrades(semester, group);
            }
        }
        Mindbox.users.get(Role.STUDENT).add(student);
        System.out.println("Student registered successfully.");
    }

    public void setNullGrades(Semester semester, Group group) {
        grades.put(semester.getId(), new HashMap<>());
        for (Subject subject : group.getSubjects()) {
            grades.get(semester.getId()).put(subject.getId(), null);
        }
    }

    public static String getGroupType() {
        Scanner sc = new Scanner(System.in);
        String option;
        do {
            System.out.println("Enter the group type to register [A/B]: ");
            option = sc.nextLine();
            if (option.equals("A")) {
                return "A";
            } else if (option.equals("B")) {
                return "B";
            } else {
                System.out.println("Invalid option.");
            }
        } while (true);
    }

    public static Student getStudent() {
        Scanner sc = new Scanner(System.in);
        ArrayList<User> students = Mindbox.users.get(Role.STUDENT);
        String controlNumber = "";
        boolean found = false;
        Student student = null;
        do {
            showAllStudents();
            System.out.println("Enter the control number of the student you want: ");
            controlNumber = sc.nextLine();
            for (User user : students) {
                if (user.getControlNumber().equals(controlNumber)) {
                    found = true;
                    student = (Student) user;
                    break;
                }
            }
        } while (!found);
        return student;
    }

    public static void showAllStudents() {
        ArrayList<User> studentUsers = Mindbox.users.get(Role.STUDENT);
        System.out.println("Students of Mindbox Institute");
        for (User user : studentUsers) {
            if (user.getCareer().equals(UserInSession.getCurrentUser().getCareer())) {
                Student student = (Student) user;
                System.out.println(student.toString());
            }
        }
    }

    public void showGrades() {
        if (getSemester() == null) {
            System.out.println("You have already graduated.");
            return;
        }
        Semester semester = Mindbox.getSemester(getSemester());
        System.out.println("Semester: " + semester.getSemesterNumber());
        HashMap<String, Integer> subjects = grades.get(semester.getId());
        for (String subject : subjects.keySet()) {
            Subject subject1 = Mindbox.getSubject(subject);
            if (grades.get(semester.getId()).get(subject1.getId()) == null) {
                System.out.println("Subject: " + subject1.getId() + " - Grade: Not assigned");
            } else {
                System.out.println("Subject: " + subject1.getId() + " - Grade: " + grades.get(semester.getId()).get(subject1.getId()));
            }
        }
    }

    public void showCurrentClasses() {
        if (getSemester() == null) {
            System.out.println("You have already graduated.");
            return;
        }
        System.out.println("Current Classes");

        Group group1 = Mindbox.getGroup(group);
        for (int i = 0; i < group1.getSubjects().size(); i++) {
            System.out.println(group1.getSubjects().get(i).getId());
        }
    }

    public void showHistory() {
        if (semester == null) {
            System.out.println("History of the graduated student:");
            double totalGrade = 0;
            for (String semester : grades.keySet()) {
                Semester semester1 = Mindbox.getSemester(semester);
                System.out.println("Semester: " + semester1.getSemesterNumber());
                double semesterGrade = 0;
                for (String subject1 : grades.get(semester).keySet()) {
                    Subject subject = Mindbox.getSubject(subject1);
                    System.out.println("Subject: " + subject.getId() + " - Grade: " + grades.get(semester).get(subject1));
                    semesterGrade += grades.get(semester).get(subject1);
                    totalGrade += grades.get(semester).get(subject1);
                }
                System.out.println("Semester average: " + semesterGrade / 3);
            }
            this.average = totalGrade / 9;
            System.out.println("Final career average: " + totalGrade / 9);
        } else {
            System.out.println("Student history up to now.");
            for (String semester : grades.keySet()) {
                Semester semester1 = Mindbox.getSemester(semester);
                System.out.println("Semester: " + semester1.getSemesterNumber());
                double semesterGrade = 0;
                for (String subject1 : grades.get(semester).keySet()) {
                    Subject subject = Mindbox.getSubject(subject1);
                    if (grades.get(semester).get(subject1) == null) {
                        System.out.println("Subject: " + subject.getId() + " - Grade: Not assigned");
                    } else {
                        System.out.println("Subject: " + subject.getId() + " - Grade: " + grades.get(semester).get(subject1));
                        semesterGrade += grades.get(semester).get(subject1);
                    }
                }
                System.out.println("Semester average: " + semesterGrade / 3);
            }
        }
    }

    public static void expelStudent() {
        if (Mindbox.users.get(Role.STUDENT).isEmpty()) {
            System.out.println("No students to expel.");
            return;
        }
        Scanner sc = new Scanner(System.in);
        Student student = getStudent();
        if (student.getSemester() == null) {
            System.out.println("The student has already graduated.");
            return;
        }
        do {
            System.out.println("Are you sure you want to expel the student " + student.getFirstName() + ", with control number " + student.getControlNumber() + " [Y] / [N]");
            String confirmation = sc.nextLine();
            if (confirmation.equalsIgnoreCase("Y")) {
                Mindbox.users.get(Role.STUDENT).remove(student);
                Mindbox.getGroup(student.getGroup()).getStudentList().remove(student.getControlNumber());
                System.out.println("The student has been expelled successfully");
                break;
            } else if (confirmation.equalsIgnoreCase("N")) {
                System.out.println("Student not expelled");
                break;
            } else {
                System.out.println("Invalid option.");
            }
        } while (true);
    }

    public static void updateCommonData() {
        System.out.println("You have selected the option to update Student.");
        Student student = getStudent();
        CommonData.updateInformation(student);
    }

    // Getters
    public String getSemester() {
        return semester;
    }

    public String getGroup() {
        return group;
    }

    public double getAverage() {
        return average;
    }

    public HashMap<String, HashMap<String, Integer>> getGrades() {
        return grades;
    }

    // Setters
    public void setSemester(String semester) {
        this.semester = semester;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public void setGrades(HashMap<String, HashMap<String, Integer>> grades) {
        this.grades = grades;
    }
}
