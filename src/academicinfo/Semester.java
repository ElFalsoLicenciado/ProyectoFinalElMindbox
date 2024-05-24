package academicinfo;

import java.util.List;

public class Semester {
    private String id;
    private int semesterNumber;
    private Career career;
    private List<Group> groups;


    public Semester(String id, int semesterNumber, Career career, List<Group> groups) {
        this.id = id;
        this.semesterNumber = semesterNumber;
        this.career = career;
        this.groups = groups;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSemesterNumber() {
        return semesterNumber;
    }

    public void setSemesterNumber(int semesterNumber) {
        this.semesterNumber = semesterNumber;
    }

    public Career getCareer() {
        return career;
    }

    public void setCareer(Career career) {
        this.career = career;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
