import academicinfo.Group;
import academicinfo.Semester;
import mindbox.Menu;
import mindbox.Minbox;
import mindbox.Sys;
import services.CareerService;
import academicinfo.CareerType;
import users.Coordinator;
import users.Student;
import users.Teacher;
import users.utils.Country;
import users.utils.Gender;
import users.utils.Role;
import utils.CurrentCareer;
import utils.UserInSession;

import java.time.LocalDate;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        // Initialize data for the first time
        initializeInitialData();

        // Initialize careers in the menu
        Menu.initializeCareers();

        // Create a new Menu object and display the career selection menu
        CareerType career = Menu.askCareer();
        if (career != null) {
            CurrentCareer.getInstance().setCareer(career);

            // Log in the user
            Sys.getInstance().executeSystem();
        }
    }

    private static void initializeInitialData() {
        CareerService careerService = CareerService.getInstance();

        // Initialize ISC career
        Coordinator iscCoordinator = new Coordinator(
                "John", "Doe", "Smith", "1980-01-01", Gender.MALE, "City", Country.Michoacan,
                "CURP_ISC", "RFC_ISC", "Address", LocalDate.now().toString(), "ISC_C", "password",
                "CISC0", 50000, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        Student iscStudent = new Student(
                "Luis", "Roman", "Sanchez", "2000-04-01", Gender.MALE,
                "Morelia", Country.Michoacan, "CURP_LUIS", "RFC_LUIS", "Address",
                LocalDate.now().toString(), "Lars", "Pass", "ILISC0", CareerType.ISC,
                Semester.FIRST, Group.GroupId.A, 0.0, new ArrayList<>());

        Teacher iscTeacher = new Teacher(
                "Alice", "Brown", "Johnson", "1985-05-15", Gender.FEMALE,
                "Morelia", Country.Michoacan, "CURP_ALICE", "RFC_ALICE", "Address",
                LocalDate.now().toString(), "Alice", "password", "TAISC0", 40000,
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        Minbox iscMinbox = new Minbox(CareerType.ISC);
        iscMinbox.getUserList().get(Role.COORDINATOR).add(iscCoordinator);
        iscMinbox.getUserList().get(Role.STUDENT).add(iscStudent);
        iscMinbox.getUserList().get(Role.TEACHER).add(iscTeacher);
        careerService.addCareer(CareerType.ISC, iscMinbox);

        // Initialize IMAT career
        Coordinator imatCoordinator = new Coordinator(
                "Jane", "Doe", "Smith", "1980-01-01", Gender.FEMALE, "City", Country.Michoacan,
                "CURP_IMAT", "RFC_IMAT", "Address", LocalDate.now().toString(), "IMAT_C", "password",
                "CIMAT0", 50000, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        Student imatStudent = new Student(
                "Ana", "Garcia", "Lopez", "2001-02-01", Gender.FEMALE,
                "Morelia", Country.Michoacan, "CURP_ANA", "RFC_ANA", "Address",
                LocalDate.now().toString(), "AnaG", "Pass", "IMAT0", CareerType.IMAT,
                Semester.FIRST, Group.GroupId.A, 0.0, new ArrayList<>());

        Teacher imatTeacher = new Teacher(
                "Carlos", "Martinez", "Santos", "1986-07-21", Gender.MALE,
                "Morelia", Country.Michoacan, "CURP_CARLOS", "RFC_CARLOS", "Address",
                LocalDate.now().toString(), "CarlosM", "password", "TAIMAT0", 40000,
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        Minbox imatMinbox = new Minbox(CareerType.IMAT);
        imatMinbox.getUserList().get(Role.COORDINATOR).add(imatCoordinator);
        imatMinbox.getUserList().get(Role.STUDENT).add(imatStudent);
        imatMinbox.getUserList().get(Role.TEACHER).add(imatTeacher);
        careerService.addCareer(CareerType.IMAT, imatMinbox);

        // Initialize ELC career
        Coordinator elcCoordinator = new Coordinator(
                "Jim", "Doe", "Smith", "1980-01-01", Gender.MALE, "City", Country.Michoacan,
                "CURP_ELC", "RFC_ELC", "Address", LocalDate.now().toString(), "ELC_C", "password",
                "CELC0", 50000, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        Student elcStudent = new Student(
                "Mario", "Hernandez", "Ruiz", "1999-03-10", Gender.MALE,
                "Morelia", Country.Michoacan, "CURP_MARIO", "RFC_MARIO", "Address",
                LocalDate.now().toString(), "MarioH", "Pass", "IELC0", CareerType.ELC,
                Semester.FIRST, Group.GroupId.A, 0.0, new ArrayList<>());

        Teacher elcTeacher = new Teacher(
                "Laura", "Gonzalez", "Vega", "1984-08-11", Gender.FEMALE,
                "Morelia", Country.Michoacan, "CURP_LAURA", "RFC_LAURA", "Address",
                LocalDate.now().toString(), "LauraG", "password", "TAELC0", 40000,
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        Minbox elcMinbox = new Minbox(CareerType.ELC);
        elcMinbox.getUserList().get(Role.COORDINATOR).add(elcCoordinator);
        elcMinbox.getUserList().get(Role.STUDENT).add(elcStudent);
        elcMinbox.getUserList().get(Role.TEACHER).add(elcTeacher);
        careerService.addCareer(CareerType.ELC, elcMinbox);

        // Save initial data to JSON
        Sys.saveData();
    }
}
