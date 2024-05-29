package mindbox;

import academicinfo.Group;
import academicinfo.CareerType;
import users.Coordinator;
import users.User;
import users.utils.Country;
import users.utils.Gender;
import users.utils.Role;
import utils.CurrentCareer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Minbox {

    private Coordinator coordinator;
    private HashMap<Role, ArrayList<User>> userList = new HashMap<>();
    private List<Group> groups = new ArrayList<>();

    public Minbox(CareerType careerType) {
        userList.put(Role.STUDENT, new ArrayList<>());
        userList.put(Role.TEACHER, new ArrayList<>());
        userList.put(Role.COORDINATOR, new ArrayList<>());

        switch (careerType) {
            case ISC -> this.coordinator = new Coordinator("CISC", "C", "C", LocalDate.of(2000, 10, 10).toString(),
                    Gender.MALE, "Morelia", Country.Michoacan, "CURPISC", "RFCISC", "Address", LocalDate.now().toString(), "ISC_C",
                    "123456", "CC24ISC0", 20000, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            case IMAT -> this.coordinator = new Coordinator("CIMAT", "C", "C", LocalDate.of(2000, 10, 10).toString(),
                    Gender.MALE, "Morelia", Country.Michoacan, "CURPIMAT", "RFCIMAT", "Address", LocalDate.now().toString(), "IMAT_C",
                    "123456", "CC24IMAT0", 20000, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            case ELC -> this.coordinator = new Coordinator("CELC", "C", "C", LocalDate.of(2000, 10, 10).toString(),
                    Gender.MALE, "Morelia", Country.Michoacan, "CURPELC", "RFCELC", "Address", LocalDate.now().toString(), "ELC_C",
                    "123456", "CC24ELC0", 20000, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        }
    }

    public User verifyLogIn(String username, String password) {
        if (username.equals(coordinator.getUsername()) && password.equals(coordinator.getPassword())) {
            return coordinator;
        }
        for (ArrayList<User> list : userList.values()) {
            for (User user : list) {
                if (username.equals(user.getUsername()) && password.equals(user.getPassword())) {
                    return user;
                }
            }
        }
        return null;
    }

    public static void showMindboxInfo() {
        System.out.printf("\n------------------------CAREER INFORMATION------------------------------");
        ArrayList<Integer> data = calculateNumberOfUsers();
        System.out.printf("\n | TOTAL STUDENTS: %d | \n | TOTAL TEACHERS: %d | \n | TOTAL COORDINATORS: %d | \n | TOTAL USERS: %d |",
                data.get(0), data.get(1), data.get(2), data.get(3));
        System.out.println("\n------------------COORDINATOR INFORMATION------------------");
        CareerType careerType = CurrentCareer.getInstance().getCurrentCareer();
        System.out.println(Sys.getInstance().getCareers().get(careerType).getCoordinator());
    }

    private static ArrayList<Integer> calculateNumberOfUsers() {
        ArrayList<Integer> data = new ArrayList<>();
        CareerType currentCareer = CurrentCareer.getInstance().getCurrentCareer();
        HashMap<Role, ArrayList<User>> map = Sys.getInstance().getCareers().get(currentCareer).getUserList();
        data.add(map.get(Role.STUDENT).size());
        data.add(map.get(Role.TEACHER).size());
        data.add(map.get(Role.COORDINATOR).size());
        data.add(map.get(Role.STUDENT).size() + map.get(Role.TEACHER).size() + map.get(Role.COORDINATOR).size());
        return data;
    }

    public HashMap<Role, ArrayList<User>> getUserList() {
        return userList;
    }

    public Coordinator getCoordinator() {
        return this.coordinator;
    }

    public void setCoordinator(Coordinator coordinator) {
        this.coordinator = coordinator;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
