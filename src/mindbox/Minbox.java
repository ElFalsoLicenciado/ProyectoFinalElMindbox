package mindbox;

import mindbox.utils.*;
import users.Coordinator;
import users.User;
import users.utils.Country;
import users.utils.Gender;
import users.utils.Role;
import utils.CurrentCareer;
import utils.Date;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Minbox {

    Coordinator coordinator;

    private HashMap<Role, ArrayList<User>> userList = new HashMap<>();


    public Minbox(Career career) {
        userList.put(Role.STUDENT, new ArrayList<User>());
        userList.put(Role.TEACHER, new ArrayList<User>());
        userList.put(Role.COORDINATOR, new ArrayList<User>());

        switch (career) {

            case ISC -> this.coordinator = new Coordinator("CISC", "C", "C", Date.askForDate(2000, 10, 10),
                    Gender.MALE, "Morelia", Country.Michoacan, "CURPISC", "RFCISC", "Address", LocalDate.now(), "ISC_C",
                    "123456", Role.COORDINATOR, 25000, "123456", "");
            case IMAT ->

            case ELC ->

        }
    }

    public User verifyLogIn(String username, String password) {
        if (username.equals(coordinator.getUsername()) && password.equals(coordinator.getPassword())) {
            return coordinator;
        }
        for (ArrayList<User> list : userList.values()) {
            for (User user : list) {
                if (username.equals(user.getUsername())) {
                    if (password.equals(user.getPassword())) {
                        return user;
                    }
                }
            }
        }
        return null;
    }

    public static void showMindboxInfo(){
        System.out.printf("\n------------------------INFORMACIÓN DE LA SUCURSAL %s------------------------------", SucursalActual.getInstancia().getSucursalActual().toString());
        ArrayList<Integer> datos = calcularNumeroUsuarios();
        System.out.printf("\n | SALDO ACTUAL DEL BANCO: $%.2f | ", Sys.getInstance().getSucursales().get(SucursalActual.getInstancia().getSucursalActual()).getSaldoBanco());
        System.out.printf("\n | NÚMERO TOTAL DE MOVIMIENTOS BANCARIOS: %d | NÚMERO TOTAL DE SOLICITUDES: %d | ", Sys.getInstance().getSucursales().get(SucursalActual.getInstancia().getSucursalActual()).getListaMovimientoBancarios().size(), Sys.getInstance().getSucursales().get(SucursalActual.getInstancia().getSucursalActual()).getListaSolicitudes().size());
        System.out.println("\n--------------NÚMERO TOTAL DE USUARIOS EN EL SISTEMA------------------");
        System.out.printf("\n | CLIENTES: %d | \n |  EJECUTIVOS DE CUENTA : %d | | \n CAPTURISTAS: %d | \n | INVERSIONISTAS: %d |   ", datos.get(0), datos.get(1), datos.get(2), datos.get(3));
        System.out.printf("\n | NÚMERO TOTAL DE USUARIOS: %d |", datos.get(4));

        System.out.println("\n------------------INFORMACIÓN DEL GERENTE A CARGO DEL BANCO------------------");
        System.out.println(Sys.getInstance().getSucursales().get(SucursalActual.getInstancia().getSucursalActual()).getGerente());

    }


    private static ArrayList<Integer> calculateNumberOfUsers(){
        ArrayList<Integer> data = new ArrayList<>();
        HashMap<Role, ArrayList<User>> map = Sys.getInstance().getCareers().get(CurrentCareer.getInstance().getCurrentCareer()).getUserList();
        data.add(map.get(Role.STUDENT).size());
        data.add(map.get(Role.TEACHER).size());
        data.add(data.get(0) + data.get(1) + data.get(2) + data.get(3));
        return data;
    }

    public HashMap<Role, ArrayList<User>> getUserList() {
        return userList;
    }

    public Coordinator getCoordinator() {
        return this.coordinator;
    }


}
