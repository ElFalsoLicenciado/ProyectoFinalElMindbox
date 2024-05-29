package academicinfo;

import java.util.List;
import users.Student;

public class Group {
    private GroupId id;
    private Semester semester;
    private List<Student> students;
    private List<Subject> subjects; // Add this field

    public Group(GroupId id, Semester semester, List<Student> students, List<Subject> subjects) {
        this.id = id;
        this.semester = semester;
        this.students = students;
        this.subjects = subjects; // Initialize the subjects
    }

    public GroupId getId() {
        return id;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Subject> getSubjects() { // Add this method
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public enum GroupId {
        A,
        B
    }
}
