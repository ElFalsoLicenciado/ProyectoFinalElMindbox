package mindbox;

import gson.deserializers.*;
import gson.serializers.*;
import academicinfo.CareerType;
import users.User;
import utils.Ask;
import utils.CurrentCareer;
import utils.UserInSession;

import java.util.HashMap;
import java.util.Map;

public class Sys {
    private static Sys instance;
    private Map<CareerType, Minbox> careers;

    public Sys() {
        careers = new HashMap<>();
        loadData(); // Load data on startup
        if (careers.isEmpty()) {
            careers.put(CareerType.ISC, new Minbox(CareerType.ISC));
            careers.put(CareerType.IMAT, new Minbox(CareerType.IMAT));
            careers.put(CareerType.ELC, new Minbox(CareerType.ELC));
        }
    }

    public void executeSystem() {
        CareerType career = null;
        boolean flag = true;
        do {
            if (CurrentCareer.getInstance().getCurrentCareer() == null) {
                career = Menu.askCareer();
                if (career == null) {
                    System.out.println("Thanks for using our program.");
                    break;
                } else {
                    CurrentCareer.getInstance().setCareer(career);
                }
            }
            System.out.printf("\n****************WELCOME TO THE %s CAREER****************",
                    CurrentCareer.getInstance().getCurrentCareer());
            logIn();

            System.out.printf("\nYou are currently in the career: %s",
                    CurrentCareer.getInstance().getCurrentCareer().toString());
            System.out.println("\nWhat do you want to do?");
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
            String username = Ask.forString("username");
            String password = Ask.forString("password");

            User user = Sys.getInstance().getCareers().get(CurrentCareer.getInstance().getCurrentCareer())
                    .verifyLogIn(username, password);
            if (user != null) {
                UserInSession.getInstance().setCurrentUser(user);
                correctData = true;
                Menu.selectMenu();
            } else {
                counter++;
                if (counter == 4) {
                    System.out.println("You have exceeded the number of attempts. Returning to the previous menu...");
                }
                System.out.println("Incorrect username or password. Please try again.");
            }
        } while (!correctData && counter != 4);
    }

    public Map<CareerType, Minbox> getCareers() {
        return careers;
    }

    public static Sys getInstance() {
        if (instance == null) {
            instance = new Sys();
        }
        return instance;
    }

    public static void saveData() {
        UserSerializer.serialize();
    }

    public static void loadData() {
        UserDeserializer.deserialize();
    }
}
