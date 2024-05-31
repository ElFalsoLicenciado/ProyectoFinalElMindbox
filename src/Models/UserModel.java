package Models;

import java.time.LocalDate;
import java.util.*;

import academicinfo.*;
import mindbox.*;
import users.*;
import users.utils.*;
import utils.*;

public class UserModel {
    private ArrayList<Student> student;

    private ArrayList<Coordinator> coordinator;

    private ArrayList<Teacher> teacher;

    public ArrayList<Student> getStudent() {
        return student;
    }

    public void setStudent(ArrayList<Student> student) {
        this.student = student;
    }

    public ArrayList<Coordinator> getCoordinator() {
        return coordinator;
    }

    public void setCoordinator(ArrayList<Coordinator> coordinator) {
        this.coordinator = coordinator;
    }

    public ArrayList<Teacher> getTeacher() {
        return teacher;
    }

    public void setTeacher(ArrayList<Teacher> teacher) {
        this.teacher = teacher;
    }
}

