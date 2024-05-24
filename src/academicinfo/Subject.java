package academicinfo;

import users.Teacher;

public class Subject {
    private String id;
    private String name;
    private Career career;
    private Group group;
    private Teacher teacher;


    public Subject(String id, String name, Career career, Group group, Teacher teacher) {
        this.id = id;
        this.name = name;
        this.career = career;
        this.group = group;
        this.teacher = teacher;
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

    public Career getCareer() {
        return career;
    }

    public void setCareer(Career career) {
        this.career = career;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
