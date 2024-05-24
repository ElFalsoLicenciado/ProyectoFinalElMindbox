package users;

import java.util.List;
import academicinfo.Subject;
import academicinfo.Group;
import users.utils.Country;
import users.utils.Gender;
import users.utils.Role;

import java.time.LocalDate;

public class Teacher extends  User{
    private double salary;
    private   int controlNumber;
    private List<Subject> subjects;
    private List<Student> managedStudents;
    private List<Group> groups;


    public Teacher(String firstName, String paternalLastName, String maternalLastName, LocalDate birthDate, Gender gender, String city, Country country, String curp, String rfc, String address, LocalDate registrationDate, String username, String password, Role role, double salary, int controlNumber, List<Subject> subjects, List<Student> managedStudents, List<Group> groups) {
        super(firstName, paternalLastName, maternalLastName, birthDate, gender, city, country, curp, rfc, address, registrationDate, username, password, Role.TEACHER);
        this.salary = salary;
        this.controlNumber = controlNumber;
        this.subjects = subjects;
        this.managedStudents = managedStudents;
        this.groups = groups;
    }


    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getControlNumber() {
        return controlNumber;
    }

    public void setControlNumber(int controlNumber) {
        this.controlNumber = controlNumber;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public List<Student> getManagedStudents() {
        return managedStudents;
    }

    public void setManagedStudents(List<Student> managedStudents) {
        this.managedStudents = managedStudents;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
