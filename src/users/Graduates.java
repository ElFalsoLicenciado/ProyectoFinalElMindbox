package users;

import java.time.LocalDate;
import java.util.*;

import academicinfo.*;
import mindbox.*;
import users.*;
import users.utils.*;
import utils.*;

public class Graduates {

    private String graduationDate;
    private Careers careers;
    private ArrayList<String> graduatedStudents;
    private String graduationGeneration;

    public Graduates(String graduationDate, Careers careers, ArrayList<String> graduatedStudents, String graduationGeneration) {
        this.graduationDate = graduationDate;
        this.careers = careers;
        this.graduatedStudents = graduatedStudents;
        this.graduationGeneration = graduationGeneration;
    }

    public static void registerGraduates(Graduates graduates) {
        Mindbox.graduates.add(graduates);
    }

    public String toString() {
        return "Graduation Date: " + graduationDate + "\nGraduation Generation: " + graduationGeneration;
    }

    private void printGraduates() {
        for (String student : graduatedStudents) {
            System.out.println(Mindbox.getStudent(student).toString());
        }
    }

    public static void showGraduates() {
        System.out.println("Graduated students of Mindbox Institute");
        for (Graduates graduates : Mindbox.graduates) {
            if (graduates.careers == UserInSession.getCurrentUser().getCareer()) {
                System.out.println(graduates.toString());
                System.out.println("Students:");
                graduates.printGraduates();
            }
        }
    }

    // Getters
    public String getGraduationDate() {
        return graduationDate;
    }

    public Careers getCareers() {
        return careers;
    }

    public ArrayList<String> getGraduatedStudents() {
        return graduatedStudents;
    }

    public String getGraduationGeneration() {
        return graduationGeneration;
    }

    // Setters
    public void setGraduationDate(String graduationDate) {
        this.graduationDate = graduationDate;
    }

    public void setCareers(Careers careers) {
        this.careers = careers;
    }

    public void setGraduatedStudents(ArrayList<String> graduatedStudents) {
        this.graduatedStudents = graduatedStudents;
    }

    public void setGraduationGeneration(String graduationGeneration) {
        this.graduationGeneration = graduationGeneration;
    }
}

