package academicinfo;

import mindbox.utils.CareerType;
import java.util.List;

public class Semester {
    private String id;
    private int number;
    private CareerType career;
    private List<Subject> subjects;

    public Semester(String id, int number, CareerType career, List<Subject> subjects) {
        this.id = id;
        this.number = number;
        this.career = career;
        this.subjects = subjects;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public CareerType getCareer() {
        return career;
    }

    public void setCareer(CareerType career) {
        this.career = career;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    // Method to get the next semester
    public Semester next() {
        return new Semester(this.id, this.number + 1, this.career, this.subjects);
    }
}
