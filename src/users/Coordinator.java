package users;

import academicinfo.*;
import mindbox.Sys;
import mindbox.utils.CommonData;
import academicinfo.CareerType;
import users.utils.*;
import utils.Ask;
import utils.CurrentCareer;
import utils.Id;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Coordinator extends Teacher {

    public Coordinator(String firstName, String paternalLastName, String maternalLastName, String birthDate, Gender gender, String city, Country country, String curp, String rfc, String address, String registrationDate, String username, String password, String controlNumber, double salary, List<Subject> subjects, List<Student> managedStudents, List<Group> groups) {
        super(firstName, paternalLastName, maternalLastName, birthDate, gender, city, country, curp, rfc, address, registrationDate, username, password, controlNumber, salary, subjects, managedStudents, groups);
    }

    public static void register() {
        List<String> data = CommonData.getCommonData(Role.COORDINATOR);
        String firstName = data.get(0);
        String paternalLastName = data.get(1);
        String maternalLastName = data.get(2);
        String birthDate = data.get(3);
        String address = data.get(4);
        Country country = CommonData.validCountry(data.get(5));
        String city = data.get(6);
        String username = data.get(7);
        String password = data.get(8);
        Gender gender = CommonData.validGender(data.get(9));

        String curp = Curp.generate(paternalLastName, maternalLastName, firstName, birthDate, gender, country);
        String rfc = Rfc.generate(paternalLastName, maternalLastName, firstName, birthDate);

        String registrationDate = LocalDate.now().toString();
        double salary = Ask.forDouble("the salary");
        CareerType currentCareer = CurrentCareer.getInstance().getCurrentCareer();
        String controlNumber = Id.generateControlNumber(firstName, registrationDate, currentCareer, Role.COORDINATOR);

        Coordinator coordinator = new Coordinator(firstName, paternalLastName, maternalLastName, birthDate, gender, city, country, curp, rfc, address, registrationDate, username, password, controlNumber, salary, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        Sys.getInstance().getCareers().get(currentCareer).setCoordinator(coordinator);

        Sys.saveData();
        System.out.println("\n----------------COORDINATOR REGISTERED----------------\n");
    }

    public static void remove() {
        CareerType currentCareer = CurrentCareer.getInstance().getCurrentCareer();
        Coordinator coordinator = Sys.getInstance().getCareers().get(currentCareer).getCoordinator();
        if (coordinator != null) {
            Sys.getInstance().getCareers().get(currentCareer).setCoordinator(null);
            Sys.saveData();
            System.out.println("\n----------------COORDINATOR REMOVED----------------\n");
        } else {
            System.out.println("No coordinator found to remove.");
        }
    }

    public static void modify() {
        CareerType currentCareer = CurrentCareer.getInstance().getCurrentCareer();
        Coordinator coordinator = Sys.getInstance().getCareers().get(currentCareer).getCoordinator();
        if (coordinator != null) {
            List<String> data = CommonData.getCommonData(Role.COORDINATOR);
            coordinator.setFirstName(data.get(0));
            coordinator.setPaternalLastName(data.get(1));
            coordinator.setMaternalLastName(data.get(2));
            coordinator.setBirthDate(data.get(3));
            coordinator.setAddress(data.get(4));
            coordinator.setCountry(CommonData.validCountry(data.get(5)));
            coordinator.setCity(data.get(6));
            coordinator.setUsername(data.get(7));
            coordinator.setPassword(data.get(8));
            coordinator.setGender(CommonData.validGender(data.get(9)));

            Sys.saveData();
            System.out.println("\n----------------COORDINATOR MODIFIED----------------\n");
        } else {
            System.out.println("No coordinator found to modify.");
        }
    }

    public static void viewInfo() {
        CareerType currentCareer = CurrentCareer.getInstance().getCurrentCareer();
        Coordinator coordinator = Sys.getInstance().getCareers().get(currentCareer).getCoordinator();
        if (coordinator != null) {
            System.out.println(coordinator);
        } else {
            System.out.println("No coordinator found.");
        }
    }

    public static void viewAll() {
        Sys.getInstance().getCareers().values().forEach(career -> {
            Coordinator coordinator = career.getCoordinator();
            if (coordinator != null) {
                System.out.println(coordinator);
            }
        });
    }

    public static void promoteGroup() {
        CareerType currentCareer = CurrentCareer.getInstance().getCurrentCareer();
        List<Group> groups = Sys.getInstance().getCareers().get(currentCareer).getGroups();

        for (Group group : groups) {
            boolean canPromote = true;

            for (Student student : group.getStudents()) {
                if (student.getGrades().stream().anyMatch(grade -> grade.getValue() < 70)) {
                    canPromote = false;
                    break;
                }
            }

            if (canPromote) {
                // Promote the group to the next semester
                Semester currentSemester = group.getSemester();
                Semester nextSemester = getNextSemester(currentSemester);
                if (nextSemester != null) {
                    group.setSemester(nextSemester);
                    group.getStudents().forEach(student -> student.setSemester(nextSemester));
                    Sys.saveData();
                    System.out.println("Group " + group.getId() + " promoted to semester " + nextSemester);
                } else {
                    System.out.println("Group " + group.getId() + " is already in the highest semester.");
                }
            } else {
                System.out.println("Group " + group.getId() + " cannot be promoted due to failing grades.");
            }
        }
    }

    private static Semester getNextSemester(Semester currentSemester) {
        switch (currentSemester) {
            case FIRST:
                return Semester.SECOND;
            case SECOND:
                return Semester.THIRD;
            case THIRD:
                return Semester.GRADUATE;
            case GRADUATE:
                return null; // Already in the highest semester
            default:
                throw new IllegalArgumentException("Unknown semester: " + currentSemester);
        }
    }

}
