package users;
import users.utils.Country;
import users.utils.Gender;
import users.utils.Role;

import java.util.List;
import academicinfo.Career;
import academicinfo.Grade;
import academicinfo.Group;
import academicinfo.Semester;

import java.time.LocalDate;

public class Student extends User{
    private Career career;
    private Semester semester;
    private Group group;
    private double gradeAverage;
    private int controlNumber;
    private List<Grade> grades;


    public Student(String firstName, String paternalLastName, String maternalLastName, LocalDate birthDate, Gender gender, String city, Country country, String curp, String rfc, String address, LocalDate registrationDate, String username, String password, Role role, Career career, Semester semester, Group group, double gradeAverage, int controlNumber, List<Grade> grades) {
        super(firstName, paternalLastName, maternalLastName, birthDate, gender, city, country, curp, rfc, address, registrationDate, username, password, Role.STUDENT);
        this.career = career;
        this.semester = semester;
        this.group = group;
        this.gradeAverage = gradeAverage;
        this.controlNumber = controlNumber;
        this.grades = grades;
    }


    public Career getCareer() {
        return career;
    }

    public void setCareer(Career career) {
        this.career = career;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public double getGradeAverage() {
        return gradeAverage;
    }

    public void setGradeAverage(double gradeAverage) {
        this.gradeAverage = gradeAverage;
    }

    public int getControlNumber() {
        return controlNumber;
    }

    public void setControlNumber(int controlNumber) {
        this.controlNumber = controlNumber;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }
}
