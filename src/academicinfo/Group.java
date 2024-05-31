package academicinfo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import academicinfo.*;
import mindbox.*;
import users.*;
import users.utils.*;
import utils.*;

public class Group {

    private Careers career;
    private ArrayList<String> studentList;
    private ArrayList<Subject> subjects;
    private String semester;
    private String groupType;
    private String groupId;

    public Group(Careers career, String semester, String groupType, String groupId) {
        this.career = career;
        this.semester = semester;
        this.groupType = groupType;
        this.groupId = groupId;
        this.studentList = new ArrayList<>();
        this.subjects = new ArrayList<>();
    }

    public String toString() {
        return String.format(
                "ID: %s, Group Type: %s, Career: %s, Semester: %d", getGroupId(), getGroupType(), getCareer(), Mindbox.getSemester(getSemester()).getSemesterNumber()
        );
    }

    public String showSubjects() {
        if (subjects == null) {
            return "";
        }
        for (Subject subject : subjects) {
            System.out.println(subject.toString());
            System.out.println();
        }
        return "";
    }

    public static void showAllGroups() {
        Scanner sc = new Scanner(System.in);
        String option;
        do {
            System.out.println("How do you want to see the groups?");
            System.out.println("[1]. With their subjects.");
            System.out.println("[2]. With their students.");
            System.out.println("[E]. Exit this menu.");
            option = sc.nextLine();
            if (option.equalsIgnoreCase("1")) {
                ArrayList<Semester> semesters = Mindbox.semesters.get(UserInSession.getCurrentUser().getCareer());
                for (Semester semester : semesters) {
                    ArrayList<Group> groups = semester.getGroups();
                    for (Group group : groups) {
                        System.out.println(group.toString());
                        ArrayList<Subject> subjects = group.getSubjects();
                        System.out.println("Subjects of the group: " + group.getGroupId());
                        for (Subject subject : subjects) {
                            System.out.println(subject.toString());
                        }
                        System.out.println("----------------------------");
                    }
                }
            } else if (option.equalsIgnoreCase("2")) {
                ArrayList<Semester> semesters = Mindbox.semesters.get(UserInSession.getCurrentUser().getCareer());
                for (Semester semester : semesters) {
                    ArrayList<Group> groups = semester.getGroups();
                    for (Group group : groups) {
                        System.out.println(group.toString());
                        ArrayList<String> students = group.getStudentList();
                        System.out.println("Students of the group: " + group.getGroupId());
                        for (String student : students) {
                            System.out.println(Mindbox.getStudent(student).toString());
                        }
                        System.out.println("----------------------------");
                    }
                }
            }
        } while (!option.equalsIgnoreCase("E"));
    }

    public static Group getGroup(String c, Semester semester) {
        Careers career = UserInSession.getCurrentUser().getCareer();
        for (Group group : semester.getGroups()) {
            if (group.getGroupType().equals(c)) {
                return group;
            }
        }
        return null;
    }

    public String showStudentList() {
        System.out.println("All students in this group:");
        if (studentList == null) {
            System.out.println("No students in the group.");
            return "";
        }
        for (String student : studentList) {
            System.out.println(Mindbox.getStudent(student).toString());
        }
        return "";
    }

    public ArrayList<String> getApprovedStudents() {
        ArrayList<String> approvedList = new ArrayList<>();
        for (String student : studentList) {
            HashMap<String, HashMap<String, Integer>> grades = Mindbox.getStudent(student).getGrades();
            for (Integer grade : grades.get(Mindbox.getSemester(Mindbox.getStudent(student).getSemester()).getId()).values()) {
                if (grade >= 70) {
                    if (!approvedList.contains(student)) {
                        approvedList.add(student);
                    }
                }
            }
        }
        return approvedList;
    }

    public ArrayList<String> getFailedStudents() {
        ArrayList<String> failedList = new ArrayList<>();
        for (String student : studentList) {
            HashMap<String, HashMap<String, Integer>> grades = Mindbox.getStudent(student).getGrades();
            for (Integer grade : grades.get(Mindbox.getSemester(Mindbox.getStudent(student).getSemester()).getId()).values()) {
                if (grade < 70) {
                    if (!failedList.contains(student)) {
                        failedList.add(student);
                    }
                }
            }
        }
        return failedList;
    }

    public boolean checkGrades() {
        for (String student : studentList) {
            HashMap<String, HashMap<String, Integer>> grades = Mindbox.getStudent(student).getGrades();
            for (Integer grade : grades.get(Mindbox.getSemester(Mindbox.getStudent(student).getSemester()).getId()).values()) {
                if (grade == null) {
                    return false;
                }
            }
        }
        return true;
    }

    // Getters
    public Careers getCareer() {
        return career;
    }

    public ArrayList<String> getStudentList() {
        return studentList;
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public String getSemester() {
        return semester;
    }

    public String getGroupType() {
        return groupType.equals("A") ? "A" : "B";
    }

    public String getGroupId() {
        return groupId;
    }

    // Setters
    public void setCareer(Careers career) {
        this.career = career;
    }

    public void setStudentList(ArrayList<String> studentList) {
        this.studentList = studentList;
    }

    public void setSubjects(ArrayList<Subject> subjects) {
        this.subjects = subjects;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}

