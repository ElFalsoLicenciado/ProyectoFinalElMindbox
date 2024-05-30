package users;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import academicinfo.*;
import mindbox.Sys;
import mindbox.utils.CommonData;
import users.utils.*;
import utils.Ask;
import utils.CurrentCareer;
import utils.Id;
import utils.UserInSession;

public class Teacher extends User {
    private double salary;
    private List<Subject> subjects;
    private List<Student> managedStudents;
    private List<Group> groups;

    public Teacher(String firstName, String paternalLastName, String maternalLastName, String birthDate, Gender gender,
                   String city, Country country, String curp, String rfc, String address, String registrationDate,
                   String username, String password, String controlNumber, double salary, List<Subject> subjects,
                   List<Student> managedStudents, List<Group> groups) {
        super(firstName, paternalLastName, maternalLastName, birthDate, gender, city, country, curp, rfc, address,
                registrationDate, username, password, controlNumber, Role.TEACHER);
        this.salary = salary;
        this.subjects = subjects;
        this.managedStudents = managedStudents;
        this.groups = groups;
    }

    // Getters and Setters
    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
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

    // Methods
    public static void register() {
        List<String> data = CommonData.getCommonData(Role.TEACHER);
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

        String curp = Curp.generate(firstName, paternalLastName, maternalLastName, birthDate, gender1, country1);
        String rfc = Rfc.generate(paternalLastName, maternalLastName, firstName, birthDate);
        CareerType careerType = CurrentCareer.getInstance().getCurrentCareer();
        String controlNumber = Id.generateControlNumber(firstName, birthDate, careerType, Role.TEACHER);

        double salary = Ask.forDouble("the salary");
        List<Subject> subjects = new ArrayList<>();
        List<Student> managedStudents = new ArrayList<>();
        List<Group> groups = new ArrayList<>();

        Teacher teacher = new Teacher(firstName, paternalLastName, maternalLastName, birthDate, gender1, city, country1, curp, rfc, address, LocalDate.now().toString(), username, password, controlNumber, salary, subjects, managedStudents, groups);

        Sys.getInstance().getCareers().get(careerType).getGroups().forEach(group -> {
            group.getStudents().forEach(student -> student.getGrades().forEach(grade -> {
                if (grade.getSubject().getTeacherName().equals(teacher.getFullName())) {
                    managedStudents.add(student);
                }
            }));

            group.getSubjects().forEach(subject -> {
                if (subject.getTeacherName().equals(teacher.getFullName())) {
                    subjects.add(subject);
                }
            });

            if (group.getSubjects().stream().anyMatch(subject -> subject.getTeacherName().equals(teacher.getFullName()))) {
                groups.add(group);
            }
        });

        Sys.getInstance().getCareers().get(careerType).getUserList().get(Role.TEACHER).add(teacher); // Add teacher to the list of teachers
        Sys.saveData(); // Save data to JSON
        System.out.println("\n----------------TEACHER REGISTERED----------------\n");
    }

    public static void remove() {
        User currentUser = UserInSession.getInstance().getCurrentUser();
        if (currentUser instanceof Teacher) {
            Teacher teacher = (Teacher) currentUser;
            CareerType careerType = CurrentCareer.getInstance().getCurrentCareer();
            Sys.getInstance().getCareers().get(careerType).getGroups().forEach(group -> {
                group.getSubjects().removeIf(subject -> subject.getTeacherName().equals(teacher.getFullName()));
                group.getStudents().forEach(student -> student.getGrades().removeIf(grade -> grade.getSubject().getTeacherName().equals(teacher.getFullName())));
            });
            Sys.getInstance().getCareers().get(careerType).getUserList().get(Role.TEACHER).remove(teacher);
            Sys.saveData(); // Save data to JSON
            System.out.println("Teacher removed successfully.");
        }
    }

    public static void modify() {
        User currentUser = UserInSession.getInstance().getCurrentUser();
        if (currentUser instanceof Teacher) {
            Teacher teacher = (Teacher) currentUser;
            List<String> data = CommonData.getCommonData(Role.TEACHER);
            teacher.setFirstName(data.get(0));
            teacher.setPaternalLastName(data.get(1));
            teacher.setMaternalLastName(data.get(2));
            teacher.setBirthDate(data.get(3));
            teacher.setAddress(data.get(4));
            teacher.setCountry(CommonData.validCountry(data.get(5)));
            teacher.setCity(data.get(6));
            teacher.setUsername(data.get(7));
            teacher.setPassword(data.get(8));
            teacher.setGender(CommonData.validGender(data.get(9)));

            Sys.saveData(); // Save data to JSON
            System.out.println("Teacher modified successfully.");
        }
    }

    public static void viewInfo() {
        User currentUser = UserInSession.getInstance().getCurrentUser();
        if (currentUser instanceof Teacher) {
            Teacher teacher = (Teacher) currentUser;
            System.out.println("Teacher Information:");
            System.out.println("Name: " + teacher.getFullName());
            System.out.println("Birth Date: " + teacher.getBirthDate());
            System.out.println("Address: " + teacher.getAddress());
            System.out.println("City: " + teacher.getCity());
            System.out.println("Country: " + teacher.getCountry());
            System.out.println("Salary: " + teacher.getSalary());
            System.out.println("Control Number: " + teacher.getControlNumber());
            System.out.println("Subjects: ");
            teacher.getSubjects().forEach(subject -> System.out.println(subject.getSubjectName()));
        }
    }

    public static void viewAll() {
        CareerType careerType = CurrentCareer.getInstance().getCurrentCareer();
        System.out.println("All teachers in " + careerType + ":");
        Sys.getInstance().getCareers().get(careerType).getGroups().forEach(group -> group.getSubjects().forEach(subject -> {
            if (subject.getTeacherName().equals(UserInSession.getInstance().getCurrentUser().getFullName())) {
                System.out.println(subject.getTeacherName());
            }
        }));
    }

    public static void modifyPersonalInfo() {
        User currentUser = UserInSession.getInstance().getCurrentUser();
        if (currentUser instanceof Teacher) {
            Teacher teacher = (Teacher) currentUser;
            List<String> data = CommonData.getCommonData(Role.TEACHER);
            teacher.setFirstName(data.get(0));
            teacher.setPaternalLastName(data.get(1));
            teacher.setMaternalLastName(data.get(2));
            teacher.setBirthDate(data.get(3));
            teacher.setAddress(data.get(4));
            teacher.setCountry(CommonData.validCountry(data.get(5)));
            teacher.setCity(data.get(6));
            teacher.setUsername(data.get(7));
            teacher.setPassword(data.get(8));
            teacher.setGender(CommonData.validGender(data.get(9)));

            Sys.saveData(); // Save data to JSON
            System.out.println("Personal information updated successfully.");
        }
    }

    public static void consultGroups() {
        User currentUser = UserInSession.getInstance().getCurrentUser();
        if (currentUser instanceof Teacher) {
            Teacher teacher = (Teacher) currentUser;
            System.out.println("Groups managed by " + teacher.getFirstName() + " " + teacher.getPaternalLastName() + ":");
            teacher.getGroups().forEach(group -> System.out.println(group.getId()));
        }
    }

    public static void assignGrades() {
        User currentUser = UserInSession.getInstance().getCurrentUser();
        if (currentUser instanceof Teacher) {
            Teacher teacher = (Teacher) currentUser;
            teacher.getGroups().forEach(group -> group.getStudents().forEach(student -> {
                group.getSubjects().forEach(subject -> {
                    if (subject.getTeacherName().equals(teacher.getFullName())) {
                        double grade = Ask.forDouble("the grade for " + student.getFullName() + " in " + subject.getSubjectName());
                        Grade studentGrade = new Grade(subject, grade);
                        student.getGrades().add(studentGrade);
                        Sys.saveData(); // Save data to JSON
                        System.out.println("Grade assigned successfully.");
                    }
                });
            }));
        }
    }

    public static void modifyGrades() {
        User currentUser = UserInSession.getInstance().getCurrentUser();
        if (currentUser instanceof Teacher) {
            Teacher teacher = (Teacher) currentUser;
            teacher.getGroups().forEach(group -> group.getStudents().forEach(student -> {
                group.getSubjects().forEach(subject -> {
                    if (subject.getTeacherName().equals(teacher.getFullName())) {
                        Optional<Grade> gradeOptional = student.getGrades().stream().filter(g -> g.getSubject().equals(subject)).findFirst();
                        gradeOptional.ifPresent(grade -> {
                            double newGrade = Ask.forDouble("the new grade for " + student.getFullName() + " in " + subject.getSubjectName());
                            grade.setValue(newGrade);
                            Sys.saveData(); // Save data to JSON
                            System.out.println("Grade modified successfully.");
                        });
                    }
                });
            }));
        }
    }

    public static void consultCurrentStudents() {
        User currentUser = UserInSession.getInstance().getCurrentUser();
        if (currentUser instanceof Teacher) {
            Teacher teacher = (Teacher) currentUser;
            System.out.println("Current students managed by " + teacher.getFirstName() + " " + teacher.getPaternalLastName() + ":");
            teacher.getManagedStudents().forEach(student -> System.out.println(student.getFullName()));
        }
    }
}
