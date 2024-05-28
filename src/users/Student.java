package users;

import mindbox.utils.CareerType;
import users.utils.*;
import academicinfo.Grade;
import academicinfo.Group;
import academicinfo.Semester;
import academicinfo.Subject;
import mindbox.Sys;
import mindbox.utils.CommonData;
import utils.CurrentCareer;
import utils.UserInSession;
import utils.Id;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Student extends User {
    private CareerType career;
    private Semester semester;
    private Group group;
    private double gradeAverage;
    private List<Grade> grades;

    public Student(String firstName, String paternalLastName, String maternalLastName, String birthDate, Gender gender, String city, Country country, String curp, String rfc, String address, String registrationDate, String username, String password, String controlNumber, CareerType career, Semester semester, Group group, double gradeAverage, List<Grade> grades) {
        super(firstName, paternalLastName, maternalLastName, birthDate, gender, city, country, curp, rfc, address, registrationDate, username, password, controlNumber, Role.STUDENT);
        this.career = career;
        this.semester = semester;
        this.group = group;
        this.gradeAverage = gradeAverage;
        this.grades = grades;
    }

    // Getters and Setters
    public CareerType getCareer() {
        return career;
    }

    public void setCareer(CareerType career) {
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

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    // Methods
    public static void showNotification() {
        System.out.println("You have new notifications.");
    }

    public static void modifyPersonalInfo() {
        User currentUser = UserInSession.getInstance().getCurrentUser();
        if (currentUser instanceof Student) {
            Student student = (Student) currentUser;
            List<String> data = CommonData.getCommonData(Role.STUDENT);
            student.setFirstName(data.get(0));
            student.setPaternalLastName(data.get(1));
            student.setMaternalLastName(data.get(2));
            student.setBirthDate(data.get(3));
            student.setAddress(data.get(4));
            student.setCountry(CommonData.validCountry(data.get(5)));
            student.setCity(data.get(6));
            student.setUsername(data.get(7));
            student.setPassword(data.get(8));
            student.setGender(CommonData.validGender(data.get(9)));

            Sys.saveData(); // Save data to JSON
            System.out.println("Personal information updated successfully.");
        }
    }

    public static void consultSubjects() {
        User currentUser = UserInSession.getInstance().getCurrentUser();
        if (currentUser instanceof Student) {
            Student student = (Student) currentUser;
            System.out.println("Ongoing subjects for " + student.getFirstName() + " " + student.getPaternalLastName() + ":");
            student.getGroup().getSubjects().forEach(subject -> System.out.println(subject.getName()));
        }
    }

    public static void consultGrades() {
        User currentUser = UserInSession.getInstance().getCurrentUser();
        if (currentUser instanceof Student) {
            Student student = (Student) currentUser;
            System.out.println("Current grades for " + student.getFirstName() + " " + student.getPaternalLastName() + ":");
            student.getGrades().forEach(grade -> System.out.println(grade.getSubject().getName() + ": " + grade.getValue()));
        }
    }

    public static void consultTeachers() {
        User currentUser = UserInSession.getInstance().getCurrentUser();
        if (currentUser instanceof Student) {
            Student student = (Student) currentUser;
            System.out.println("Current teachers for " + student.getFirstName() + " " + student.getPaternalLastName() + ":");
            student.getGroup().getSubjects().forEach(subject -> System.out.println(subject.getTeacher().getFullName()));
        }
    }

    public static void consultGradeAverage() {
        User currentUser = UserInSession.getInstance().getCurrentUser();
        if (currentUser instanceof Student) {
            Student student = (Student) currentUser;
            System.out.println("Current grade average for " + student.getFirstName() + " " + student.getPaternalLastName() + ": " + student.getGradeAverage());
        }
    }

    public static void consultHistory() {
        User currentUser = UserInSession.getInstance().getCurrentUser();
        if (currentUser instanceof Student) {
            Student student = (Student) currentUser;
            System.out.println("History for " + student.getFirstName() + " " + student.getPaternalLastName() + ":");

            Sys.getInstance().getCareers().values().forEach(career -> {
                career.getGroups().forEach(group -> {
                    group.getStudents().forEach(s -> {
                        if (s.getControlNumber().equals(student.getControlNumber())) {
                            System.out.println("Semester: " + group.getSemester().getNumber());
                            group.getSubjects().forEach(subject -> {
                                Optional<Grade> grade = s.getGrades().stream().filter(g -> g.getSubject().getId().equals(subject.getId())).findFirst();
                                if (grade.isPresent()) {
                                    System.out.println("Subject: " + subject.getName());
                                    System.out.println("Grade: " + grade.get().getValue());
                                    System.out.println("Teacher: " + subject.getTeacher().getFullName());
                                }
                            });
                            System.out.println("Average grade: " + s.getGradeAverage());
                            System.out.println("------------");
                        }
                    });
                });
            });
        }
    }

    public static void register() {
        List<String> data = CommonData.getCommonData(Role.STUDENT);
        String firstName = data.get(0);
        String paternalLastName = data.get(1);
        String maternalLastName = data.get(2);
        String birthDate = data.get(3);
        String address = data.get(4);
        String country = data.get(5);
        String city = data.get(6);
        String username = data.get(7);
        String password = data.get(8);
        String gender = data.get(9);

        Gender gender1 = CommonData.validGender(gender);
        Country country1 = CommonData.validCountry(country);

        String curp = Curp.generate(paternalLastName, maternalLastName, firstName, birthDate, gender1, country1);
        String rfc = Rfc.generate(paternalLastName, maternalLastName, firstName, birthDate);
        CareerType career = CurrentCareer.getInstance().getCurrentCareer();
        String controlNumber = Id.generateControlNumber(firstName, birthDate, career, Role.STUDENT);

        Semester semester = new Semester("1", 1, career, new ArrayList<>());
        Group group = new Group("A", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), career, semester);
        double gradeAverage = 0.0;
        List<Grade> grades = new ArrayList<>();

        Student student = new Student(firstName, paternalLastName, maternalLastName, birthDate, gender1, city, country1, curp, rfc, address, LocalDate.now().toString(), username, password, controlNumber, career, semester, group, gradeAverage, grades);

        Sys.getInstance().getCareers().get(career).getGroups().get(0).getStudents().add(student); // Add student to the first group for simplicity
        Sys.saveData(); // Save data to JSON
        System.out.println("\n----------------STUDENT REGISTERED----------------\n");
    }

    public static void remove() {
        User currentUser = UserInSession.getInstance().getCurrentUser();
        if (currentUser instanceof Student) {
            Student student = (Student) currentUser;
            CareerType career = student.getCareer();
            Optional<Group> groupOptional = Sys.getInstance().getCareers().get(career).getGroups().stream().filter(g -> g.getStudents().contains(student)).findFirst();
            groupOptional.ifPresent(group -> group.getStudents().remove(student));
            Sys.saveData(); // Save data to JSON
            System.out.println("Student removed successfully.");
        }
    }

    public static void modify() {
        User currentUser = UserInSession.getInstance().getCurrentUser();
        if (currentUser instanceof Student) {
            Student student = (Student) currentUser;
            List<String> data = CommonData.getCommonData(Role.STUDENT);
            student.setFirstName(data.get(0));
            student.setPaternalLastName(data.get(1));
            student.setMaternalLastName(data.get(2));
            student.setBirthDate(data.get(3));
            student.setAddress(data.get(4));
            student.setCountry(CommonData.validCountry(data.get(5)));
            student.setCity(data.get(6));
            student.setUsername(data.get(7));
            student.setPassword(data.get(8));
            student.setGender(CommonData.validGender(data.get(9)));

            Sys.saveData(); // Save data to JSON
            System.out.println("Student modified successfully.");
        }
    }

    public static void viewInfo() {
        User currentUser = UserInSession.getInstance().getCurrentUser();
        if (currentUser instanceof Student) {
            Student student = (Student) currentUser;
            System.out.println("Student Information:");
            System.out.println("Name: " + student.getFullName());
            System.out.println("Birth Date: " + student.getBirthDate());
            System.out.println("Address: " + student.getAddress());
            System.out.println("City: " + student.getCity());
            System.out.println("Country: " + student.getCountry());
            System.out.println("Career: " + student.getCareer().name());
            System.out.println("Semester: " + student.getSemester().getNumber());
            System.out.println("Group: " + student.getGroup().getId());
            System.out.println("Grade Average: " + student.getGradeAverage());
        }
    }

    public static void viewAll() {
        CareerType career = CurrentCareer.getInstance().getCurrentCareer();
        System.out.println("All students in " + career.name() + ":");
        Sys.getInstance().getCareers().get(career).getGroups().forEach(group -> group.getStudents().forEach(student -> System.out.println(student.getFullName())));
    }
}
