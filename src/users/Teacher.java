package users;

import java.util.ArrayList;
import java.util.List;
import academicinfo.Subject;
import academicinfo.Group;
import mindbox.Sys;
import mindbox.utils.CommonData;
import users.utils.Country;
import users.utils.Gender;
import users.utils.Role;
import utils.Ask;
import utils.CurrentCareer;

import java.time.LocalDate;

public class Teacher extends User {
    private double salary;
    private int controlNumber;
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

    public static void register() {
        ArrayList<String> data = CommonData.obtainCommonData(Role.TEACHER);
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

        double salary = Ask.forDouble("the salary");
        int controlNumber = Integer.parseInt(CommonData.generateControlNumber(Role.TEACHER));
        List<Subject> subjects = new ArrayList<>();
        List<Student> managedStudents = new ArrayList<>();
        List<Group> groups = new ArrayList<>();

        Teacher teacher = new Teacher(firstName, paternalLastName, maternalLastName, birthDate, gender, city, country, curp, rfc, address, registrationDate, username, password, Role.TEACHER, salary, controlNumber, subjects, managedStudents, groups);

        Sys.getInstance().getCareers().get(CurrentCareer.getInstance().getCurrentCareer().getId()).addUser(teacher);
        System.out.println("\n----------------TEACHER REGISTERED----------------\n");
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

    // Placeholder methods for other Teacher functionalities
    public static void modifyPersonalInfo() {
        System.out.println("Modifying personal information...");
        // Implementation of modifying personal information
    }

    public static void consultGroups() {
        System.out.println("Consulting groups...");
        // Implementation of consulting groups
    }

    public static void assignGrades() {
        System.out.println("Assigning grades...");
        // Implementation of assigning grades
    }

    public static void modifyGrades() {
        System.out.println("Modifying grades...");
        // Implementation of modifying grades
    }

    public static void consultCurrentStudents() {
        System.out.println("Consulting current students...");
        // Implementation of consulting current students
    }

    public static void remove() {
        System.out.println("Removing teacher...");
        // Implementation of removing a teacher
    }

    public static void modify() {
        System.out.println("Modifying teacher...");
        // Implementation of modifying a teacher
    }

    public static void viewInfo() {
        System.out.println("Viewing teacher information...");
        // Implementation of viewing a teacher's information
    }

    public static void viewAll() {
        System.out.println("Viewing all teachers...");
        // Implementation of viewing all teachers
    }
}
