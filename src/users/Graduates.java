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
        StringBuilder graduatesInfo = new StringBuilder();
        for (String student : graduatedStudents) {
            graduatesInfo.append(Mindbox.getStudent(student).toString()).append("\n");
        }
        DialogHelper.info(graduatesInfo.toString());
    }

    public static void showGraduates() {
        StringBuilder graduatesInfo = new StringBuilder("Graduated students of Mindbox Institute\n");
        for (Graduates graduates : Mindbox.graduates) {
            if (graduates.careers == UserInSession.getInstance().getCurrentUser().getCareer()) {
                graduatesInfo.append(graduates.toString()).append("\nStudents:\n");
                graduates.printGraduates();
            }
        }
        DialogHelper.info(graduatesInfo.toString());
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

