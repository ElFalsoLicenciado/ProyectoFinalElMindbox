package academicinfo;

import java.util.List;
import users.Coordinator;

public class Career {
    private String id;
    private String name;
    private int numberOfGroups;
    private int numberOfStudents;
    private int numberOfSubjects;
    private String creationDate;
    private Coordinator coordinator;
    private List<Group> groups;

    public Career(String id, String name, int numberOfGroups, int numberOfStudents, int numberOfSubjects, String creationDate, Coordinator coordinator, List<Group> groups) {
        this.id = id;
        this.name = name;
        this.numberOfGroups = numberOfGroups;
        this.numberOfStudents = numberOfStudents;
        this.numberOfSubjects = numberOfSubjects;
        this.creationDate = creationDate;
        this.coordinator = coordinator;
        this.groups = groups;
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
}
