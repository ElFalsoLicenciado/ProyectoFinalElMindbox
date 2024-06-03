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
        Scanner sc = new Scanner(System.in);
        Subject subject1 = null;
        String name = "";
        boolean found = false;
        Careers career = UserInSession.getInstance().getCurrentUser().getCareer();
        Semester semester1 = Mindbox.getSemester(semester);
        ArrayList<Subject> subjects = Objects.requireNonNull(Mindbox.getGroup(group)).getSubjects();
        do {
            System.out.println("All subjects of this group.");
            Mindbox.getGroup(group).showSubjects();
            System.out.println("Enter the name of the subject:");
            name = sc.nextLine();
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
        Careers career = UserInSession.getInstance().getCurrentUser().getCareer();
        ArrayList<Subject> subjects = Mindbox.getGroup(group.getGroupId()).getSubjects();
        if (subjects.isEmpty()) {
            System.out.println("You don't have any subjects.");
            return;
        }
        for (Subject subject : subjects) {
            System.out.println("Name: " + subject.getId());
            System.out.println("Career: " + subject.getCareer());
            System.out.println("Group: " + subject.getGroup());
            System.out.println();
        }
    }

    public String getStudent(String subjectId) {
        Scanner sc = new Scanner(System.in);
        int option = 0;
        boolean isValid = true;
        ArrayList<String> studentList = Mindbox.getGroup(group).getStudentList();

        do {
            for (String student : studentList) {
                HashMap<String, Integer> semesterGrades = Mindbox.getStudent(student).getGrades().get(Mindbox.getStudent(student).getSemester());
                if (semesterGrades != null && semesterGrades.containsKey(subjectId)) {
                    Integer grade = semesterGrades.get(subjectId);
                    if (grade == null) {
                        System.out.println("[ " + studentList.indexOf(student) + " ] Name: " + Mindbox.getStudent(student).getFirstName() + " " + Mindbox.getStudent(student).getLastName() + " - Grade: Not assigned");
                    } else {
                        System.out.println("[ " + studentList.indexOf(student) + " ] Name: " + Mindbox.getStudent(student).getFirstName() + " " + Mindbox.getStudent(student).getLastName() + " - Grade: " + grade);
                    }
                } else {
                    System.out.println("Does not have the subject.");
                }
            }

            try {
                option = sc.nextInt();
                studentList.get(option);
                isValid = false;
            } catch (InputMismatchException e) {
                System.out.println("The value must be an integer.");
                sc.nextLine();
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Enter a valid index.");
                sc.nextLine();
            }
        } while (isValid);

        return studentList.get(option);
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
