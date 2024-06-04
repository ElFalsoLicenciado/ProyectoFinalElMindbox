package users;

import academicinfo.*;
import mindbox.*;
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
        DialogHelper.info("You have selected the option to register a student.");
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
            DialogHelper.error("Could not find the specified semester.");
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
        DialogHelper.info("Student registered successfully.");
    }

    public void setNullGrades(Semester semester, Group group) {
        grades.put(semester.getId(), new HashMap<>());
        for (Subject subject : group.getSubjects()) {
            grades.get(semester.getId()).put(subject.getId(), null);
        }
    }

    private static String getGroupType() {
        String groupType;
        do {
            groupType = String.valueOf(DialogHelper.optionD("Enter the group type to register:", new String[]{"A", "B"}));
            if (groupType.equals("A") || groupType.equals("B")) {
                return groupType;
            } else {
                DialogHelper.error("Invalid group option");
            }
        } while (true);
    }


    public static Student getStudent() {
        ArrayList<User> students = Mindbox.users.get(Role.STUDENT);
        String controlNumber;
        boolean found = false;
        Student student = null;
        do {
            showAllStudents();
            controlNumber = DialogHelper.stringIn("Enter the control number of the student you want:");
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
        DialogHelper.info("Students of Mindbox ITM");
        for (User user : studentUsers) {
            if (user.getCareer().equals(UserInSession.getInstance().getCurrentUser().getCareer())) {
                Student student = (Student) user;
                DialogHelper.info(student.toString());
            }
        }
    }

    public void showGrades() {
        if (getSemester() == null) {
            DialogHelper.info("You have already graduated.");
            return;
        }
        ArrayList<String> aux = new ArrayList<>();
        Semester semester = Mindbox.getSemester(getSemester());
        assert semester != null;
        aux.add("Semester: " + semester.getSemesterNumber());
        HashMap<String, Integer> subjects = grades.get(semester.getId());
        for (String subject : subjects.keySet()) {
            Subject subject1 = Mindbox.getSubject(subject);
            assert subject1 != null;
            if (grades.get(semester.getId()).get(subject1.getId()) == null) {
                aux.add("Subject: " + subject1.getId() + " - Grade: Not assigned");
            } else {
                aux.add("Subject: " + subject1.getId() + " - Grade: " + grades.get(semester.getId()).get(subject1.getId()));
            }
        }
        DialogHelper.info(Arrays.toString(aux.toArray()).replace("[", "").replace("]", ""));
    }

    public void showCurrentSubjects() {
        if (getSemester() == null) {
            DialogHelper.info("You have already graduated.");
            return;
        }
        ArrayList<String> aux = new ArrayList<>();
        aux.add("Current Subjects");

        Group group1 = Mindbox.getGroup(group);
        for (int i = 0; i < Objects.requireNonNull(group1).getSubjects().size(); i++) {
            aux.add(group1.getSubjects().get(i).getId());
        }
        DialogHelper.info(Arrays.toString(aux.toArray()).replace("[", "").replace("]", ""));
    }


    public void showHistory() {
        if (semester == null) {
            DialogHelper.info("History of the graduated student:");
            double totalGrade = 0;
            for (String semester : grades.keySet()) {
                Semester semester1 = Mindbox.getSemester(semester);
                assert semester1 != null;
                DialogHelper.info("Semester: " + semester1.getSemesterNumber());
                double semesterGrade = 0;
                for (String subject1 : grades.get(semester).keySet()) {
                    Subject subject = Mindbox.getSubject(subject1);
                    assert subject != null;
                    DialogHelper.info("Subject: " + subject.getId() + " - Grade: " + grades.get(semester).get(subject1));
                    semesterGrade += grades.get(semester).get(subject1);
                    totalGrade += grades.get(semester).get(subject1);
                }
                DialogHelper.info("Semester average: " + semesterGrade / 3);
            }
            this.average = totalGrade / 9;
            DialogHelper.info("Final career average: " + totalGrade / 9);
        } else {
            DialogHelper.info("Student history up to now.");
            for (String semester : grades.keySet()) {
                Semester semester1 = Mindbox.getSemester(semester);
                assert semester1 != null;
                DialogHelper.info("Semester: " + semester1.getSemesterNumber());
                double semesterGrade = 0;
                for (String subject1 : grades.get(semester).keySet()) {
                    Subject subject = Mindbox.getSubject(subject1);
                    if (grades.get(semester).get(subject1) == null) {
                        assert subject != null;
                        DialogHelper.info("Subject: " + subject.getId() + " - Grade: Not assigned");
                    } else {
                        assert subject != null;
                        DialogHelper.info("Subject: " + subject.getId() + " - Grade: " + grades.get(semester).get(subject1));
                        semesterGrade += grades.get(semester).get(subject1);
                    }
                }
                DialogHelper.info("Semester average: " + semesterGrade / 3);
            }
        }
    }

    public static void expelStudent() {
        if (Mindbox.users.get(Role.STUDENT).isEmpty()) {
            DialogHelper.info("No students to expel.");
            return;
        }
        Student student = getStudent();
        if (student.getSemester() == null) {
            DialogHelper.info("The student has already graduated.");
            return;
        }
        do {
            String confirmation = DialogHelper.stringIn("Are you sure you want to expel the student " + student.getFirstName() + ", with control number " + student.getControlNumber() + " [Y] / [N]");
            if (confirmation.equalsIgnoreCase("Y")) {
                Mindbox.users.get(Role.STUDENT).remove(student);
                Mindbox.getGroup(student.getGroup()).getStudentList().remove(student.getControlNumber());
                DialogHelper.info("The student has been expelled successfully");
                break;
            } else if (confirmation.equalsIgnoreCase("N")) {
                DialogHelper.info("Student not expelled");
                break;
            } else {
                DialogHelper.warning("Invalid option.");
            }
        } while (true);
    }


    public static void updateCommonData() {
        DialogHelper.info("You have selected the option to update Student.");
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
