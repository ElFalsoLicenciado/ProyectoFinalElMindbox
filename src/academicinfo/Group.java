package academicinfo;

import academicinfo.CareerType;
import users.*;

import java.util.List;

public class Group {

    private String id;
    private List<Teacher> teachers;
    private List<Student> students;
    private List<Subject> subjects;
    private CareerType career;
    private Semester semester;

    public Group(String id, List<Teacher> teachers, List<Student> students, List<Subject> subjects, CareerType career, Semester semester) {
        this.id = id;
        this.teachers = teachers;
        this.students = students;
        this.subjects = subjects;
        this.career = career;
        this.semester = semester;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CareerType getCareer() {
        return career;
    }

    public void setCareer(CareerType career) {
        this.career = career;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }


    public enum GroupId {
        A,
        B
    }
}



