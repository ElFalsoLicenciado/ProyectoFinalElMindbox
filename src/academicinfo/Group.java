package academicinfo;

import java.util.List;
import users.Student;

public class Group {
    private String id;
    private Career career;
    private String groupType; // A or B
    private List<Student> students;
    private List<Subject> subjects;
    private Semester semester;


    public Group(String id, Career career, String groupType, List<Student> students, List<Subject> subjects, Semester semester) {
        this.id = id;
        this.career = career;
        this.groupType = groupType;
        this.students = students;
        this.subjects = subjects;
        this.semester = semester;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Career getCareer() {
        return career;
    }

    public void setCareer(Career career) {
        this.career = career;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

}
