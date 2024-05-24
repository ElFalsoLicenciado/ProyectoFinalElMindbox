package users;

import academicinfo.Group;
import academicinfo.Subject;
import users.utils.Country;
import users.utils.Gender;
import users.utils.Role;

import java.time.LocalDate;
import java.util.List;

public class Coordinator extends Teacher{

    public Coordinator(String firstName, String paternalLastName, String maternalLastName, LocalDate birthDate, Gender gender, String city, Country country, String curp, String rfc, String address, LocalDate registrationDate, String username, String password, Role role, double salary, int controlNumber, List<Subject> subjects, List<Student> managedStudents, List<Group> groups) {
        super(firstName, paternalLastName, maternalLastName, birthDate, gender, city, country, curp, rfc, address, registrationDate, username, password, Role.COORDINATOR, salary, controlNumber, subjects, managedStudents, groups);
    }


}
