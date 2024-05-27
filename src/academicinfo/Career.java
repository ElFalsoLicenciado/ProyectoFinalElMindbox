package academicinfo;

import users.Coordinator;
import users.User;
import users.utils.Role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Career {
    private String id;
    private String name;
    private int numberOfGroups;
    private int numberOfStudents;
    private int numberOfSubjects;
    private String creationDate;
    private Coordinator coordinator;
    private List<Group> groups;
    private Map<Role, List<User>> userList;

    public Career(String id, String name, int numberOfGroups, int numberOfStudents, int numberOfSubjects, String creationDate, Coordinator coordinator, List<Group> groups) {
        this.id = id;
        this.name = name;
        this.numberOfGroups = numberOfGroups;
        this.numberOfStudents = numberOfStudents;
        this.numberOfSubjects = numberOfSubjects;
        this.creationDate = creationDate;
        this.coordinator = coordinator;
        this.groups = groups;
        this.userList = new HashMap<>();
        this.userList.put(Role.STUDENT, new ArrayList<>());
        this.userList.put(Role.TEACHER, new ArrayList<>());
        this.userList.put(Role.COORDINATOR, new ArrayList<>());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfGroups() {
        return numberOfGroups;
    }

    public void setNumberOfGroups(int numberOfGroups) {
        this.numberOfGroups = numberOfGroups;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public int getNumberOfSubjects() {
        return numberOfSubjects;
    }

    public void setNumberOfSubjects(int numberOfSubjects) {
        this.numberOfSubjects = numberOfSubjects;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public Coordinator getCoordinator() {
        return coordinator;
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

    public Map<Role, List<User>> getUserList() {
        return userList;
    }

    public void addUser(User user) {
        this.userList.get(user.getRole()).add(user);
    }

    public void removeUser(User user) {
        this.userList.get(user.getRole()).remove(user);
    }

    public List<User> getUsersByRole(Role role) {
        return this.userList.get(role);
    }
}
