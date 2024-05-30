package utils;

import academicinfo.CareerType;
import academicinfo.Group;
import academicinfo.Semester;
import com.google.gson.reflect.TypeToken;
import mindbox.Minbox;
import services.CareerService;
import users.Coordinator;
import users.Student;
import users.Teacher;
import users.utils.Country;
import users.utils.Gender;
import users.utils.Role;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataLoader {

    private static final String DATA_FILE = "data.json";

    public static void initializeData() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            // Initialize careers and coordinators
            CareerService careerService = CareerService.getInstance();

            // ISC Coordinator, Student, and Teacher
            Coordinator iscCoordinator = new Coordinator(
                    "John", "Doe", "Smith", "1980-01-01", Gender.MALE, "City", Country.Michoacan,
                    "CURP_ISC", "RFC_ISC", "Address", LocalDate.now().toString(), "ISC_C", "password",
                    "CISC0", 50000, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

            Student iscStudent = new Student(
                    "Luis", "Roman", "Sanchez", "2000-04-01", Gender.MALE,
                    "Morelia", Country.Michoacan, "CURP_LUIS", "RFC_LUIS", "Address",
                    LocalDate.now().toString(), "Lars", "Pass", "ILISC0", CareerType.ISC,
                    Semester.FIRST, new Group("A", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), CareerType.ISC, Semester.FIRST),
                    0.0, new ArrayList<>());

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

            // IMAT Coordinator, Student, and Teacher
            Coordinator imatCoordinator = new Coordinator(
                    "Jane", "Doe", "Smith", "1980-01-01", Gender.FEMALE, "City", Country.Michoacan,
                    "CURP_IMAT", "RFC_IMAT", "Address", LocalDate.now().toString(), "IMAT_C", "password",
                    "CIMAT0", 50000, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

            Student imatStudent = new Student(
                    "Ana", "Garcia", "Lopez", "2001-02-01", Gender.FEMALE,
                    "Morelia", Country.Michoacan, "CURP_ANA", "RFC_ANA", "Address",
                    LocalDate.now().toString(), "AnaG", "Pass", "IMAT0", CareerType.IMAT,
                    Semester.FIRST, new Group("A", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), CareerType.IMAT, Semester.FIRST),
                    0.0, new ArrayList<>());

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

            // ELC Coordinator, Student, and Teacher
            Coordinator elcCoordinator = new Coordinator(
                    "Jim", "Doe", "Smith", "1980-01-01", Gender.MALE, "City", Country.Michoacan,
                    "CURP_ELC", "RFC_ELC", "Address", LocalDate.now().toString(), "ELC_C", "password",
                    "CELC0", 50000, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

            Student elcStudent = new Student(
                    "Mario", "Hernandez", "Ruiz", "1999-03-10", Gender.MALE,
                    "Morelia", Country.Michoacan, "CURP_MARIO", "RFC_MARIO", "Address",
                    LocalDate.now().toString(), "MarioH", "Pass", "IELC0", CareerType.ELC,
                    Semester.FIRST, new Group("A", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), CareerType.ELC, Semester.FIRST),
                    0.0, new ArrayList<>());

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
            saveData();
        } else {
            loadData();
        }
    }

    public static void saveData() {
        CareerService careerService = CareerService.getInstance();
        try (FileWriter writer = new FileWriter(DATA_FILE)) {
            GsonUtil.getGsonInstance().toJson(careerService.getAllCareers(), writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadData() {
        CareerService careerService = CareerService.getInstance();
        try (FileReader reader = new FileReader(DATA_FILE)) {
            Type careerMapType = new TypeToken<Map<CareerType, Minbox>>() {}.getType();
            Map<CareerType, Minbox> careers = GsonUtil.getGsonInstance().fromJson(reader, careerMapType);
            careerService.setAllCareers(careers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
