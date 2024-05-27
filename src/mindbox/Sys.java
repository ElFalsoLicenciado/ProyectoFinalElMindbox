package mindbox;

import mindbox.utils.Career;
import users.User;
import utils.Ask;
import utils.CurrentCareer;
import utils.UserInSession;

import java.util.HashMap;

public class Sys {
    private static Sys instance;
    private static HashMap<Career, Minbox> careers = new HashMap<>();

    public Sys(){
        careers.put(Career.ISC, new Minbox(Career.ISC));
        careers.put(Career.IMAT, new Minbox(Career.IMAT));
        careers.put(Career.ELC, new Minbox(Career.ELC));

    }

    public void executeSystem() {
        Career career = null;
        boolean flag = true;
        do {
            if (CurrentCareer.getInstance().getCurrentCareer() == null) {
                career = Menu.askCareer();
                if (career == null){
                    System.out.println("Gracias por utilizar nuestro programa \3");
                    break;
                }
                else
                    CurrentCareer.getInstance().setCareer(career);
            }
            System.out.printf("\n****************BIENVENIDO A LA SUCURSAL %s****************",
                    CurrentCareer.getInstance().getCurrentCareer());
            logIn();

            System.out.printf("\nSe encuentra actualmente en la sucursal: %s",
                    CurrentCareer.getInstance().getCurrentCareer().toString());
            System.out.println("\n¿Qué desea hacer?");
            System.out.println("\n1. Mantenerse en esta sucursal");
            System.out.println("2. Volver al menú principal");
            int answer = Ask.forInt("el número de opción");
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
            String username = Ask.forString("el nombre de usuario");
            String password = Ask.forString("la contraseña");

            User user = Sys.getInstance().getCareers().get(CurrentCareer.getInstance().getCurrentCareer())
                    .verifyLogIn(username, password);
            if (user != null) {
                UserInSession.getInstance().setCurrentUser(user);
                correctData = true;
                Menu.selectMenu();
            } else {
                counter++;
                if (counter == 4) {
                    System.out.println("Ha excedido el número de intentos. Volviendo al menú anterior...");
                }
                System.out.println("Nombre de usuario o contraseña incorrectos. Intente de nuevo");
            }
        } while (!correctData && counter != 4);
    }


    public HashMap<Career, Minbox> getCareers() {
        return careers;
    }

    public static Sys getInstance() {
        if (instance == null) {
            instance = new Sys();
        }
        return instance;
    }
}
