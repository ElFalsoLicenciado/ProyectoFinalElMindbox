package mindbox;

import academicinfo.Career;
import mindbox.utils.CommonData;
import users.User;
import utils.Ask;
import utils.CurrentCareer;
import utils.UserInSession;

import java.util.HashMap;
import java.util.Map;

public class Sys {
    private static Sys instance;
    private static Map<String, Career> careers = new HashMap<>();

    private Sys() {
        initializeCareers();
    }

    public static Sys getInstance() {
        if (instance == null) {
            instance = new Sys();
        }
        return instance;
    }

    private void initializeCareers() {
        // Initialize careers
        careers.put("ISC", new Career("ISC", "Engineering in Computer Systems", 0, 0, 0, CommonData.getCurrentDate(), null, new ArrayList<>()));
        careers.put("IMAT", new Career("IMAT", "Engineering in Materials", 0, 0, 0, CommonData.getCurrentDate(), null, new ArrayList<>()));
        careers.put("ELC", new Career("ELC", "Engineering in Electronics", 0, 0, 0, CommonData.getCurrentDate(), null, new ArrayList<>()));
    }

    public void executeSystem() {
        Career career = null;
        boolean flag = true;
        do {
            if (CurrentCareer.getInstance().getCurrentCareer() == null) {
                career = Menu.askCareer();
                if (career == null) {
                    System.out.println("Thank you for using our program!");
                    break;
                } else {
                    CurrentCareer.getInstance().setCareer(career);
                }
            }
            System.out.printf("\n****************WELCOME TO THE CAREER %s****************", CurrentCareer.getInstance().getCurrentCareer().getName());
            logIn();

            System.out.printf("\nYou are currently in the career: %s", CurrentCareer.getInstance().getCurrentCareer().getName());
            System.out.println("\nWhat would you like to do?");
            System.out.println("\n1. Stay in this career");
            System.out.println("2. Return to the main menu");
            int answer = Ask.forInt("the option number");
            if (answer != 1) {
                CurrentCareer.getInstance().closeCareer();
            }
        } while (flag);
    }

    private static void logIn() {
        int counter = 0;
        boolean correctData = false;
        System.out.print("\n----------------LogIn to continue----------------");
        do {
            String username = Ask.forString("the username");
            String password = Ask.forString("the password");

            User user = Sys.getInstance().getCareers().get(CurrentCareer.getInstance().getCurrentCareer().getId())
                    .verifyLogIn(username, password);
            if (user != null) {
                UserInSession.getInstance().setCurrentUser(user);
                correctData = true;
                Menu.selectMenu();
            } else {
                counter++;
                if (counter == 4) {
                    System.out.println("You have exceeded the number of attempts. Returning to the previous menu...");
                } else {
                    System.out.println("Incorrect username or password. Please try again.");
                }
            }
        } while (!correctData && counter != 4);
    }

    public Map<String, Career> getCareers() {
        return careers;
    }
}
