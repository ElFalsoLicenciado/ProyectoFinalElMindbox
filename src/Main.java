import academicinfo.Group;
import academicinfo.Semester;
import mindbox.Menu;
import mindbox.Minbox;
import mindbox.Sys;
import services.CareerService;
import academicinfo.CareerType;
import users.Coordinator;
import users.Student;
import users.Teacher;
import users.utils.Country;
import users.utils.Gender;
import users.utils.Role;
import utils.CurrentCareer;
import utils.UserInSession;

import java.time.LocalDate;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        // Initialize data for the first time if necessary
        CareerService careerService = CareerService.getInstance();

        // Initialize careers in the menu
        Menu.initializeCareers();

        // Create a new Menu object and display the career selection menu
        CareerType career = Menu.askCareer();
        if (career != null) {
            CurrentCareer.getInstance().setCareer(career);

            // Log in the user
            Sys.getInstance().executeSystem();
        }
    }
}
