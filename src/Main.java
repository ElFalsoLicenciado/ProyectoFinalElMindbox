import academicinfo.CareerType;
import academicinfo.Grade;
import academicinfo.Group;
import academicinfo.Semester;
import mindbox.Menu;
import users.Student;
import users.utils.Country;
import users.utils.Gender;
import utils.UserInSession;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Creating a list of grades (initially empty for the new student)
        List<Grade> grades = new ArrayList<>();

        // Creating a new Student object
        Student student = new Student(
                "Luis", "Roman", "Sanchez", "2000-04-01", Gender.MALE,
                "Morelia", Country.Michoacan, "ROSL", "RFC", "Address",
                "2024-05-23", "Lars", "Pass", "ILISC0", CareerType.ISC,
                Semester.FIRST, Group.GroupId.A, 0.0, grades
        );

        // Setting the current user in the session
        UserInSession.getInstance().setCurrentUser(student);

        // Initializing careers in the menu
        Menu.initializeCareers();

        // Creating a new Menu object
        Menu menu = new Menu();

        // Displaying the career selection menu
        Menu.askCareer();

        // Displaying the user selection menu
        Menu.requestUserType();

        // Displaying the main menu
        Menu.selectMenu();
    }
}
