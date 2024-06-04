package academicinfo;

import java.time.LocalDate;
import java.util.*;

import academicinfo.*;
import mindbox.*;
import users.*;
import users.utils.*;
import utils.*;

public class Subject {

    private String id;
    private Careers career;
    private String group;
    private String teacher;

    public Subject(String id, Careers career, String group) {
        this.id = id;
        this.career = career;
        this.group = group;
    }

    public String toString() {
        if (getTeacher() == null) {
            return String.format(
                    "Id: %s, Career: %s, Group: %s, Teacher: Not assigned",
                    getId(), getCareer(), Mindbox.getGroup(getGroup()).getGroupId()
            );
        } else {
            return String.format(
                    "Id: %s, Career: %s, Group: %s, Teacher: %s",
                    getId(), getCareer(), Mindbox.getGroup(getGroup()).getGroupId(), getTeacher()
            );
        }
    }

    public ArrayList<String> getStudentsInSubject() {
        Group group1 = Mindbox.getGroup(group);
        return group1.getStudentList();
    }

    public ArrayList<String> getApprovedStudents(String semester) {
        ArrayList<String> students = Mindbox.getGroup(getGroup()).getStudentList();
        ArrayList<String> approved = new ArrayList<>();

        for (String student : students) {
            Integer grade = Mindbox.getStudent(student).getGrades()
                    .get(Mindbox.getSemester(semester).getId())
                    .get(id);
            if (grade != null && grade >= 70) {
                approved.add(student);
            }
        }
        return approved;
    }

    public ArrayList<String> getFailedStudents(String semester) {
        ArrayList<String> students = Mindbox.getGroup(getGroup()).getStudentList();
        ArrayList<String> failed = new ArrayList<>();

        for (String student : students) {
            Integer grade = Mindbox.getStudent(student).getGrades()
                    .get(Mindbox.getSemester(semester).getId())
                    .get(id);
            if (grade != null && grade < 70) {
                failed.add(student);
            }
        }
        return failed;
    }

    public static Subject getSubject(String semester, String group) {
        Subject subject1 = null;
        boolean found = false;
        ArrayList<Subject> subjects = Mindbox.getGroup(group).getSubjects();
        do {
            DialogHelper.info("All subjects of this group.");
            Mindbox.getGroup(group).showSubjects();
            String name = DialogHelper.stringIn("Enter the name of the subject:");
            for (Subject subject : subjects) {
                if (subject.getId().equals(name)) {
                    found = true;
                    subject1 = subject;
                    break;
                }
            }
        } while (!found);
        return subject1;
    }

    public static void showAllSubjects(Group group) {
        ArrayList<String> aux = new ArrayList<>();
        Careers career = UserInSession.getInstance().getCurrentUser().getCareer();
        ArrayList<Subject> subjects = Mindbox.getGroup(group.getGroupId()).getSubjects();
        if (subjects.isEmpty()) {
            DialogHelper.info("You don't have any subjects.");
            return;
        }
        for (Subject subject : subjects) {
            aux.add("Name: "+subject.getId());
            aux.add("\nCareer: "+subject.getCareer());
            aux.add("\nGroup: "+subject.getGroup());
        }
        DialogHelper.info(aux.toString().replace("[","").replace("]",""));
    }

    public String getStudent(String subjectId) {
        ArrayList<String> studentList = Mindbox.getGroup(group).getStudentList();
        boolean isValid = true;
        String selectedStudent = null;
        do {
            StringBuilder studentInfo = new StringBuilder();
            for (String student : studentList) {
                HashMap<String, Integer> semesterGrades = Mindbox.getStudent(student).getGrades().get(Mindbox.getStudent(student).getSemester());
                if (semesterGrades != null && semesterGrades.containsKey(subjectId)) {
                    Integer grade = semesterGrades.get(subjectId);
                    studentInfo.append("[ ")
                            .append(studentList.indexOf(student))
                            .append(" ] Name: ")
                            .append(Mindbox.getStudent(student).getFirstName())
                            .append(" ")
                            .append(Mindbox.getStudent(student).getLastName())
                            .append(" - Grade: ")
                            .append(grade == null ? "Not assigned" : grade.toString())
                            .append("\n");
                } else {
                    studentInfo.append("Does not have the subject.\n");
                }
            }
            DialogHelper.info(studentInfo.toString());
            int option = DialogHelper.intIn("Enter the index of the student:", 0);
            try {
                selectedStudent = studentList.get(option);
                isValid = false;
            } catch (IndexOutOfBoundsException e) {
                DialogHelper.warning("Enter a valid index.");
            }
        } while (isValid);

        return selectedStudent;
    }

    public void removeTeacher() {
        this.teacher = null;
    }

    // Getters
    public String getId() {
        return id;
    }

    public Careers getCareer() {
        return career;
    }

    public String getGroup() {
        return group;
    }

    public String getTeacher() {
        return teacher;
    }

    // Setters
    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCareer(Careers career) {
        this.career = career;
    }
}
