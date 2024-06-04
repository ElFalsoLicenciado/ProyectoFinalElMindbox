package academicinfo;

import java.util.ArrayList;
import java.util.HashMap;

import mindbox.*;
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
        StringBuilder subjectsInfo = new StringBuilder();
        for (Subject subject : subjects) {
            subjectsInfo.append(subject.toString()).append("\n");
        }
        DialogHelper.info(subjectsInfo.toString());
        return "";
    }


    public static void showAllGroups() {
        String option = "";
        do {
            switch (DialogHelper.optionD("How do you want to see the groups?", new String[]{"With their subjects", "With their students", "Exit this menu"})) {
                case 0 -> {
                    StringBuilder groupsInfo = new StringBuilder();
                    ArrayList<Semester> semesters = Mindbox.semesters.get(UserInSession.getInstance().getCurrentUser().getCareer());
                    for (Semester semester : semesters) {
                        ArrayList<Group> groups = semester.getGroups();
                        for (Group group : groups) {
                            groupsInfo.append(group.toString()).append("\n");
                            ArrayList<Subject> subjects = group.getSubjects();
                            groupsInfo.append("Subjects of the group: ").append(group.getGroupId()).append("\n");
                            for (Subject subject : subjects) {
                                groupsInfo.append(subject.toString()).append("\n");
                            }
                            groupsInfo.append("----------------------------\n");
                        }
                    }
                    DialogHelper.info(groupsInfo.toString());
                }
                case 1 -> {
                    StringBuilder groupsInfo = new StringBuilder();
                    ArrayList<Semester> semesters = Mindbox.semesters.get(UserInSession.getInstance().getCurrentUser().getCareer());
                    for (Semester semester : semesters) {
                        ArrayList<Group> groups = semester.getGroups();
                        for (Group group : groups) {
                            groupsInfo.append(group.toString()).append("\n");
                            ArrayList<String> students = group.getStudentList();
                            groupsInfo.append("Students of the group: ").append(group.getGroupId()).append("\n");
                            for (String student : students) {
                                groupsInfo.append(Mindbox.getStudent(student).toString()).append("\n");
                            }
                            groupsInfo.append("----------------------------\n");
                        }
                    }
                    DialogHelper.info(groupsInfo.toString());
                }
                case 2 -> option = "E";
            }
        } while (!option.equalsIgnoreCase("E"));
    }

    public static Group getGroup(String c, Semester semester) {
        Careers career = UserInSession.getInstance().getCurrentUser().getCareer();
        for (Group group : semester.getGroups()) {
            if (group.getGroupType().equals(c)) {
                return group;
            }
        }
        return null;
    }

    public String showStudentList() {
        StringBuilder studentsInfo = new StringBuilder("All students in this group:\n");
        if (studentList == null) {
            studentsInfo.append("No students in the group.\n");
        } else {
            for (String student : studentList) {
                studentsInfo.append(Mindbox.getStudent(student).toString()).append("\n");
            }
        }
        DialogHelper.info(studentsInfo.toString());
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

