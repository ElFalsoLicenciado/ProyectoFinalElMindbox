package services;

import academicinfo.CareerType;
import users.Coordinator;
import users.Student;
import users.Teacher;

import java.util.ArrayList;
import java.util.List;

public class Career {
    private CareerType careerType;
    private Coordinator coordinator;
    private List<Student> students;
    private List<Teacher> teachers;

    public Career(CareerType careerType, Coordinator coordinator) {
        this.careerType = careerType;
        this.coordinator = coordinator;
        this.students = new ArrayList<>();
        this.teachers = new ArrayList<>();
    }

    public CareerType getCareerType() {
        return careerType;
    }

    public void setCareerType(CareerType careerType) {
        this.careerType = careerType;
    }

    public Coordinator getCoordinator() {
        return coordinator;
    }

    public void setCoordinator(Coordinator coordinator) {
        this.coordinator = coordinator;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }
}
