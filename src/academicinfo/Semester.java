package academicinfo;

import java.time.LocalDate;
import java.util.*;

import academicinfo.*;
import mindbox.*;
import users.*;
import users.utils.*;
import utils.*;

public class Semester {

    private String id;
    private int semesterNumber;
    private Careers career;
    private ArrayList<Group> groups;

    public Semester(String id, int semesterNumber, Careers career) {
        this.id = id;
        this.semesterNumber = semesterNumber;
        this.career = career;
        this.groups = new ArrayList<>();
    }

    public static Semester getSemesterByNumber(int semesterNumber) {
        Careers career = UserInSession.getInstance().getCurrentUser().getCareer();
        ArrayList<Semester> semesters = Mindbox.semesters.get(career);

        if (semesters != null) {
            for (Semester semester : semesters) {
                if (semesterNumber == semester.getSemesterNumber()) {
                    return semester;
                }
            }
        } else {
            DialogHelper.warning("No registered semesters.");
        }
        return null;
    }

    public Group getGroup(String groupType) {
        for (Group group : groups) {
            if (group.getGroupType().equals(groupType)) {
                return group;
            }
        }
        return null;
    }

    public void addStudentToGroup(Student student, String groupType) {
        if (student == null) {
            DialogHelper.warning("Student cannot be null.");
            return;
        }

        Group group = getGroup(groupType);
        if (group == null) {
            DialogHelper.warning("Could not find a valid group for the specified type.");
            return;
        }
        group.getStudentList().add(student.getControlNumber());
        student.setGroup(group.getGroupId());
    }

    // Getters
    public String getId() {
        return id;
    }

    public int getSemesterNumber() {
        return semesterNumber;
    }

    public Careers getCareer() {
        return career;
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setSemesterNumber(int semesterNumber) {
        this.semesterNumber = semesterNumber;
    }

    public void setCareer(Careers career) {
        this.career = career;
    }

    public void setGroups(ArrayList<Group> groups) {
        this.groups = groups;
    }
}
