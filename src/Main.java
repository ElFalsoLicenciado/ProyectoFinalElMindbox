import mindbox.Menu;

public class Main {

    public static void main(String[] args) {
        Menu.initializeCareers();
        Menu menu = new Menu();
        Menu.selectMenu();
    }
    
}