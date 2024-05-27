package users;

import academicinfo.Career;
import academicinfo.Group;
import academicinfo.Subject;
import mindbox.Sys;
import mindbox.utils.CommonData;
import users.utils.Country;
import users.utils.Gender;
import users.utils.Role;
import utils.Ask;
import utils.CurrentCareer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Coordinator extends Teacher {

    public Coordinator(String firstName, String paternalLastName, String maternalLastName, LocalDate birthDate, Gender gender, String city, Country country, String curp, String rfc, String address, LocalDate registrationDate, String username, String password, String controlNumber, double salary, List<Subject> subjects, List<Student> managedStudents, List<Group> groups) {
        super(firstName, paternalLastName, maternalLastName, birthDate, gender, city, country, curp, rfc, address, registrationDate, username, password, controlNumber, Role.COORDINATOR, salary, subjects, managedStudents, groups);
    }

    public static void register() {
        List<String> data = CommonData.getCommonData(Role.COORDINATOR);
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

        String curp = CommonData.generateCURP(firstName, paternalLastName, maternalLastName, birthDate, gender, country);
        String rfc = CommonData.generateRFC(firstName, paternalLastName, maternalLastName, birthDate);

        LocalDate registrationDate = LocalDate.now();
        double salary = Ask.forDouble("the salary");
        int controlNumber = Ask.forInt("the control number");

        Coordinator coordinator = new Coordinator(firstName, paternalLastName, maternalLastName, birthDate, gender, city, country, curp, rfc, address, registrationDate, username, password, salary, controlNumber, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        Career currentCareer = Sys.getInstance().getCareers().get(CurrentCareer.getInstance().getCurrentCareer().getId());
        currentCareer.setCoordinator(coordinator);

        Sys.getInstance().saveData();
        System.out.println("\n----------------COORDINATOR REGISTERED----------------\n");
    }

    public static void remove() {
        Career currentCareer = Sys.getInstance().getCareers().get(CurrentCareer.getInstance().getCurrentCareer().getId());
        Coordinator coordinator = currentCareer.getCoordinator();
        if (coordinator != null) {
            currentCareer.setCoordinator(null);
            Sys.getInstance().saveData();
            System.out.println("\n----------------COORDINATOR REMOVED----------------\n");
        } else {
            System.out.println("No coordinator found to remove.");
        }
    }

    public static void modify() {
        Career currentCareer = Sys.getInstance().getCareers().get(CurrentCareer.getInstance().getCurrentCareer().getId());
        Coordinator coordinator = currentCareer.getCoordinator();
        if (coordinator != null) {
            CommonData.modifyUser(coordinator);
            Sys.getInstance().saveData();
            System.out.println("\n----------------COORDINATOR MODIFIED----------------\n");
        } else {
            System.out.println("No coordinator found to modify.");
        }
    }

    public static void viewInfo() {
        Career currentCareer = Sys.getInstance().getCareers().get(CurrentCareer.getInstance().getCurrentCareer().getId());
        Coordinator coordinator = currentCareer.getCoordinator();
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
        Career currentCareer = Sys.getInstance().getCareers().get(CurrentCareer.getInstance().getCurrentCareer().getId());
        currentCareer.getGroups().forEach(group -> {
            boolean canPromote = true;
            for (Student student : group.getStudents()) {
                if (student.getGrades().stream().anyMatch(grade -> grade.getValue() < 70)) {
                    canPromote = false;
                    break;
                }
            }
            if (canPromote) {
                group.setSemester(group.getSemester().next());
                group.getStudents().forEach(student -> student.setSemester(group.getSemester()));
                Sys.getInstance().saveData();
                System.out.println("Group " + group.getId() + " promoted to semester " + group.getSemester().getNumber());
            } else {
                System.out.println("Group " + group.getId() + " cannot be promoted due to failing grades.");
            }
        });
    }
}
