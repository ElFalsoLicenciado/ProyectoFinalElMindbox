package users;

import academicinfo.CareerType;
import academicinfo.Grade;
import academicinfo.Group;
import academicinfo.Semester;
import mindbox.Sys;
import mindbox.utils.CommonData;
import users.utils.*;
import utils.CurrentCareer;
import utils.DialogHelper;
import utils.Id;
import utils.UserInSession;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Student extends User {
    private CareerType career;
    private Semester semester;
    private Group.GroupId groupId;
    private double gradeAverage;
    private List<Grade> grades;

    public Student(String firstName, String paternalLastName, String maternalLastName, String birthDate, Gender gender,
                   String city, Country country, String curp, String rfc, String address, String registrationDate,
                   String username, String password, String controlNumber, CareerType career, Semester semester,
                   Group.GroupId groupId, double gradeAverage, List<Grade> grades) {
        super(firstName, paternalLastName, maternalLastName, birthDate, gender, city, country, curp, rfc, address,
                registrationDate, username, password, controlNumber, Role.STUDENT);
        this.career = career;
        this.semester = semester;
        this.groupId = groupId;
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

    public Group.GroupId getGroupId() {
        return groupId;
    }

    public void setGroupId(Group.GroupId groupId) {
        this.groupId = groupId;
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
        System.out.println("You have a new notification");
    }

    public static void consultPersonalInfo() {
        User currentUser = UserInSession.getInstance().getCurrentUser();
        if (currentUser instanceof Student student) {
            System.out.println("Personal Information: ");
            System.out.println("Name: " + student.getFirstName());
            System.out.println("Last Name: " + student.paternalLastName + " " + student.maternalLastName);
            System.out.println("Birth Date: " + student.getBirthDate());
            System.out.println("Gender: " + student.getGender());
            System.out.println("City: " + student.getCity());
            System.out.println("Country: " + student.getCountry());
            System.out.println("CURP: " + student.getCurp());
            System.out.println("RFC: " + student.getRfc());
            System.out.println("Address: " + student.getAddress());
            System.out.println("Registration Date: " + student.getRegistrationDate());
            System.out.println("Username: " + student.getUsername());
            System.out.println("Password: " + student.getPassword());
            System.out.println("Control Number: " + student.getControlNumber());
            System.out.println("Career: " + student.getCareer().name());
            System.out.println("Semester: " + student.getSemester().name());
            System.out.println("Group: " + student.getGroupId().name());
            System.out.println("Grade Average: " + student.getGradeAverage());
        } else {
            System.out.println("No student information available.");
        }
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
            Sys.getInstance().getCareers().get(student.getCareer()).getGroups().stream()
                    .filter(group -> group.getId() == student.getGroupId())
                    .findFirst()
                    .ifPresent(group -> group.getSubjects().forEach(subject -> System.out.println(subject.getSubjectName())));
        }
    }

    public static void consultGrades() {
        User currentUser = UserInSession.getInstance().getCurrentUser();
        if (currentUser instanceof Student) {
            Student student = (Student) currentUser;
            System.out.println("Current grades for " + student.getFirstName() + " " + student.getPaternalLastName() + ":");
            student.getGrades().forEach(grade -> System.out.println(grade.getSubject().getSubjectName() + ": " + grade.getValue()));
        }
    }

    public static void consultTeachers() {
        User currentUser = UserInSession.getInstance().getCurrentUser();
        if (currentUser instanceof Student) {
            Student student = (Student) currentUser;
            System.out.println("Current teachers for " + student.getFirstName() + " " + student.getPaternalLastName() + ":");
            Sys.getInstance().getCareers().get(student.getCareer()).getGroups().stream()
                    .filter(group -> group.getId() == student.getGroupId())
                    .findFirst()
                    .ifPresent(group -> group.getSubjects().forEach(subject -> System.out.println(subject.getTeacherName())));
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
                            DialogHelper.info("Semester: " + group.getSemester());
                            group.getSubjects().forEach(subject -> {
                                Optional<Grade> grade = s.getGrades().stream().filter(g -> g.getSubject().getId().equals(subject.getId())).findFirst();
                                grade.ifPresent(value -> DialogHelper.info(String.format("Subject: %s Grade: %s Teacher: %s", subject.getSubjectName(), value.getValue(), subject.getTeacherName())));
                            });
                            DialogHelper.info("Average grade: " + s.getGradeAverage());
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

        Semester semester = Semester.FIRST;
        Group.GroupId groupId = Group.GroupId.A; // Simplified for example, adjust as needed
        double gradeAverage = 0.0;
        List<Grade> grades = new ArrayList<>();

        Student student = new Student(firstName, paternalLastName, maternalLastName, birthDate, gender1, city, country1, curp, rfc, address, LocalDate.now().toString(), username, password, controlNumber, career, semester, groupId, gradeAverage, grades);

        Sys.getInstance().getCareers().get(career).getGroups().stream()
                .filter(group -> group.getId() == groupId)
                .findFirst()
                .ifPresent(group -> group.getStudents().add(student)); // Add student to the specified group
        Sys.saveData(); // Save data to JSON
        System.out.println("\n----------------STUDENT REGISTERED----------------\n");
    }

    public static void remove() {
        User currentUser = UserInSession.getInstance().getCurrentUser();
        if (currentUser instanceof Student) {
            Student student = (Student) currentUser;
            CareerType career = student.getCareer();
            Sys.getInstance().getCareers().get(career).getGroups().stream()
                    .filter(group -> group.getStudents().contains(student))
                    .findFirst()
                    .ifPresent(group -> group.getStudents().remove(student));
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
            System.out.println("Semester: " + student.getSemester().name());
            System.out.println("Group: " + student.getGroupId().name());
            System.out.println("Grade Average: " + student.getGradeAverage());
        }
    }

    public static void viewAll() {
        CareerType career = CurrentCareer.getInstance().getCurrentCareer();
        System.out.println("All students in " + career.name() + ":");
        Sys.getInstance().getCareers().get(career).getGroups().forEach(group -> group.getStudents().forEach(student -> System.out.println(student.getFullName())));
    }
}
