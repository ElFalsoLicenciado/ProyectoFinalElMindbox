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

    public Student(String firstName, String lastName1,String lastName2, String birthDate, String city, String state, String curp, String address, Careers career, String controlNumber, String username, String password, HashMap<String, HashMap<String, Integer>> grades) {
        super(firstName, lastName1,lastName2, birthDate, city, state, curp, address, career, controlNumber, username, password, Role.STUDENT);
        this.grades = grades;
    }

    public String toString() {
        return String.format(
                "Name: %s, Last Name: %s, Control Number: %s, Birth Date: %s, City: %s, State: %s, CURP: %s, Address: %s, Registration Date: %s, Career: %s",
                getFirstName(), getLastName(), getControlNumber(), getBirthDate(), getCity(), getState(), getCurp(), getAddress(), getRegistrationDate(), getCareer()
        );
    }

    public static void registerStudent() {
        DialogHelper.info("You have selected the option to register a student.");
        User aux = CommonData.getCommonData(Role.STUDENT);

        Student student = new Student(aux.firstName, aux.lastName1,aux.lastName2, aux.birthDate, aux.city, aux.state, aux.curp, aux.address, aux.career, aux.controlNumber,aux.username, aux.password, new HashMap<>());
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

    public static String getGroupType() {
        String option = null;

        switch(DialogHelper.optionD("Select the group type to register:",new String[]{"A","B"})) {
            case 0 -> option = "A";
            case 1 -> option = "B";
        }
        return option;
    }

    public static Student getStudent() {
        ArrayList<User> students = Mindbox.users.get(Role.STUDENT);
        String controlNumber;
        boolean found = false;
        Student student = null;
        do {
            showAllStudents();
            controlNumber = DialogHelper.stringIn("Enter the control number of the student you want: ");
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
        ArrayList<String> aux = new ArrayList<>(); //Que ves Artur
        ArrayList<User> studentUsers = Mindbox.users.get(Role.STUDENT);
        aux.add("Students of Mindbox Institute");
        for (User user : studentUsers) {
            if (user.getCareer().equals(UserInSession.getInstance().getCurrentUser().getCareer())) {
                Student student = (Student) user;
                aux.add(student.toString());
            }
        }
        DialogHelper.info(Arrays.toString(aux.toArray()).replace("[", "").replace("]", ""));
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

    public void showCurrentClasses() {
        if (getSemester() == null) {
            DialogHelper.info("You have already graduated.");
            return;
        }
        ArrayList<String> aux = new ArrayList<>();
        aux.add("Current Classes");

        Group group1 = Mindbox.getGroup(group);
        for (int i = 0; i < Objects.requireNonNull(group1).getSubjects().size(); i++) {
            aux.add(group1.getSubjects().get(i).getId());
        }
        DialogHelper.info(Arrays.toString(aux.toArray()).replace("[", "").replace("]", ""));
    }

    public void showHistory() {
        ArrayList<String> aux = new ArrayList<>();
        if (semester == null) {
            aux.add("History of the graduated student:");
            double totalGrade = 0;
            for (String semester : grades.keySet()) {
                Semester semester1 = Mindbox.getSemester(semester);
                assert semester1 != null;
                aux.add("Semester: " + semester1.getSemesterNumber());
                double semesterGrade = 0;
                for (String subject1 : grades.get(semester).keySet()) {
                    Subject subject = Mindbox.getSubject(subject1);
                    assert subject != null;
                    aux.add("Subject: " + subject.getId() + " - Grade: " + grades.get(semester).get(subject1));
                    semesterGrade += grades.get(semester).get(subject1);
                    totalGrade += grades.get(semester).get(subject1);
                }
                aux.add("Semester average: " + semesterGrade / 3);
            }
            this.average = totalGrade / 9;
            aux.add("Final career average: " + totalGrade / 9);
        } else {
            aux.add("Student history up to now.");
            for (String semester : grades.keySet()) {
                Semester semester1 = Mindbox.getSemester(semester);
                assert semester1 != null;
                aux.add("Semester: " + semester1.getSemesterNumber());
                double semesterGrade = 0;
                for (String subject1 : grades.get(semester).keySet()) {
                    Subject subject = Mindbox.getSubject(subject1);
                    if (grades.get(semester).get(subject1) == null) {
                        assert subject != null;
                        aux.add("Subject: " + subject.getId() + " - Grade: Not assigned");
                    } else {
                        assert subject != null;
                        aux.add("Subject: " + subject.getId() + " - Grade: " + grades.get(semester).get(subject1));
                        semesterGrade += grades.get(semester).get(subject1);
                    }
                }
                aux.add("Semester average: " + semesterGrade / 3);
            }
        }
        DialogHelper.info(Arrays.toString(aux.toArray()).replace("[", "").replace("]", ""));
    }

    public static void expelStudent() {
        if (Mindbox.users.get(Role.STUDENT).isEmpty()) {
            DialogHelper.error("No students to expel.");
            return;
        }
        Student student = getStudent();
        if (student.getSemester() == null) {
            DialogHelper.info("The student has already graduated.");
            return;
        }
        do {
            int confirmation = DialogHelper.optionD(String.format("Are you sure you want to expel the student %s with control number %s", student.getFirstName(), student.getControlNumber()), new String[]{"Yes", "No"});
            if (confirmation == 0) {
                Mindbox.users.get(Role.STUDENT).remove(student);
                Objects.requireNonNull(Mindbox.getGroup(student.getGroup())).getStudentList().remove(student.getControlNumber());
                DialogHelper.info("The student has been expelled successfully");
                break;
            } else if (confirmation == 1) {
                DialogHelper.info("Student not expelled");
                break;
            }
        }while (true);
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
