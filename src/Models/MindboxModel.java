package Models;

import java.time.LocalDate;
import java.util.*;

import academicinfo.*;
import mindbox.*;
import users.*;
import users.utils.*;
import utils.*;

public class MindboxModel {

    private HashMap<Careers, ArrayList<Semester>> semesters = new HashMap<>();

    private ArrayList<Graduates> graduates;

    // Getters y setters

    public HashMap<Careers, ArrayList<Semester>> getSemesters() {
        return semesters;
    }

    public void setSemesters (HashMap<Careers, ArrayList<Semester>> semesters) {
        this.semesters = semesters;
    }

    public ArrayList<Graduates> getGraduates() {
        return graduates;
    }

    public void setGraduates(ArrayList<Graduates> graduates) {
        this.graduates = graduates;
    }



}
