package users;

import users.utils.Country;
import users.utils.Gender;
import users.utils.Role;

import java.util.ArrayList;
import java.util.List;
import academicinfo.Career;
import academicinfo.Grade;
import academicinfo.Group;
import academicinfo.Semester;
import mindbox.Sys;
import mindbox.utils.CommonData;
import utils.Ask;
import utils.CurrentCareer;

import java.time.LocalDate;

public class Student extends User {
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

    public static void register() {
        ArrayList<String> data = CommonData.obtainCommonData(Role.STUDENT);
        String firstName = data.get(0);
        String paternalLastName = data.get(1);
        String maternalLastName = data.get(2);
        LocalDate birthDate = LocalDate.parse(data.get(3));
        String address = data.get(4);
        Country country = CommonData.validCountry(data.get(5));
        String city = data.get(6);
        String username = data.get(7);
        String password = data.get(8);
        Gender gender = CommonData.validGender(data.get(9));
        LocalDate registrationDate = LocalDate.now();
        String curp = CommonData.generateCURP(firstName, paternalLastName, maternalLastName, birthDate.toString(), gender, country);
        String rfc = CommonData.generateRFC(paternalLastName, maternalLastName, firstName, birthDate.toString());

        Career career = CurrentCareer.getInstance().getCurrentCareer();
        Semester semester = new Semester(1, career);
        Group group = new Group(career, "A", semester);
        double gradeAverage = 0.0;
        int controlNumber = Integer.parseInt(CommonData.generateControlNumber(Role.STUDENT));
        List<Grade> grades = new ArrayList<>();

        Student student = new Student(firstName, paternalLastName, maternalLastName, birthDate, gender, city, country, curp, rfc, address, registrationDate, username, password, Role.STUDENT, career, semester, group, gradeAverage, controlNumber, grades);

        Sys.getInstance().getCareers().get(career.getId()).addUser(student);
        System.out.println("\n----------------STUDENT REGISTERED----------------\n");
    }

    public static void showNotification() {
        System.out.println("Showing notifications for Student...");
        // Implementation of showing notifications
    }

    public static void modifyPersonalInfo() {
        System.out.println("Modifying personal information...");
        // Implementation of modifying personal information
    }

    public static void consultSubjects() {
        System.out.println("Consulting subjects...");
        // Implementation of consulting subjects
    }

    public static void consultGrades() {
        System.out.println("Consulting grades...");
        // Implementation of consulting grades
    }

    public static void consultTeachers() {
        System.out.println("Consulting teachers...");
        // Implementation of consulting teachers
    }

    public static void consultGradeAverage() {
        System.out.println("Consulting grade average...");
        // Implementation of consulting grade average
    }

    public static void consultHistory() {
        System.out.println("Consulting history...");
        // Implementation of consulting history
    }

    public static void remove() {
        System.out.println("Removing student...");
        // Implementation of removing a student
    }

    public static void modify() {
        System.out.println("Modifying student...");
        // Implementation of modifying a student
    }

    public static void viewInfo() {
        System.out.println("Viewing student information...");
        // Implementation of viewing a student's information
    }

    public static void viewAll() {
        System.out.println("Viewing all students...");
        // Implementation of viewing all students
    }
}
