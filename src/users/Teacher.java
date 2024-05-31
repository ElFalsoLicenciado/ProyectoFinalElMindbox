package Usuarios.Trabajadores;

import Tec.Tec;
import Usuarios.Usuario;
import Usuarios.Utils.Carreras;
import Usuarios.Utils.Rol;
import Usuarios.Utils.Utils;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import Utils.UsuarioEnSesion;
import users.Worker;

public class Teacher extends Worker {
    public Profesor(String nombre, String apellido, String fechaNacimiento, String ciudad, String estado, String curp, String direccion, Carreras carrera, String nControl, String contrasena, String RFC, float salario) {
        super(nombre, apellido, fechaNacimiento, ciudad, estado, curp, direccion, carrera, nControl, contrasena, Rol.Profesor, RFC, salario);
    }

    public static void registrarProfesor(){
        System.out.println("Has seleccionado la opcion de registrar a un Profesor. ");
        ArrayList<String> datosComun = Utils.obtenerDatosComun(Rol.Profesor);
        Scanner sc = new Scanner(System.in);

        String nombre = datosComun.get(0);
        String apellido = datosComun.get(1);
        String fechaNacimiento = datosComun.get(2);
        String ciudad = datosComun.get(3);
        String estado = datosComun.get(4);
        String curp = datosComun.get(5);
        String direccion = datosComun.get(6);
        String contrasena = datosComun.get(7);
        String nControl = datosComun.get(8);
        Carreras carrera = datosComun.get(9) == "Sistemas" ? Carreras.Sistemas: datosComun.get(9) == "Materiales" ? Carreras.Materiales : Carreras.Electronica;
        String rfc = datosComun.get(10);
        float sueldo = 0;
        do {
            try {
                System.out.println("Digite el sueldo del Profesor");
                sueldo = sc.nextFloat();
                if (sueldo <= 0) {
                    sueldo = 0;
                    throw new Exception("");
                }
            } catch (InputMismatchException e) {
                System.out.println("Valor debe ser un numero.");
                sc.nextLine();
            } catch (Exception e) {
                System.out.println("Valor debe ser mayor a 0.");
                sc.nextLine();
            }
        } while (sueldo == 0);

        Profesor profesor = new Profesor(nombre, apellido, fechaNacimiento, ciudad, estado, curp, direccion, carrera, nControl, contrasena,rfc, sueldo);
        Tec.usuarios.get(Rol.Profesor).add(profesor);
        System.out.println("Profesor registrado con exito.");
    }

    public static void mostrarAllProfesores(){
        ArrayList<Usuario> usuariosProfesores = Tec.usuarios.get(Rol.Profesor);
        System.out.println("Profesores del TEC de MORELIA");
        for (Usuario usuarios : usuariosProfesores) {
            if (usuarios.getCarrera().equals(UsuarioEnSesion.getUsuarioActual().getCarrera())){
                Profesor profesores = (Profesor) usuarios;
                System.out.println(profesores.aString());
            }

        }
    }

    public static Profesor obtenerProfesor(){
        Scanner sc = new Scanner(System.in);
        ArrayList<Usuario> profes = Tec.usuarios.get(Rol.Profesor);
        String nControl = "";
        boolean band = false;
        Profesor maestro = null;
        do {
            mostrarAllProfesores();
            System.out.println("Ingresa el numero de control del profesor que deseas: ");
            nControl = sc.nextLine();
            for (Usuario usuario : profes) {
                if (usuario.getnControl().equals(nControl)){
                    band = true;
                    maestro = (Profesor) usuario;
                    break;
                }
            }
        } while (!band);
        return maestro;
    }

    public static void eliminarProfesor(){
        if (Tec.usuarios.get(Rol.Profesor).isEmpty()){
            System.out.println("No hay maestros a eliminar. ");
            return;
        }
        System.out.println("Has seleccionado la opcion eliminar Profesor.");
        Profesor profesor = obtenerProfesor();
        if(profesor.getMaterias().isEmpty()){
            Tec.usuarios.get(Rol.Profesor).remove(profesor);
            System.out.println("Se ha elimando el profesor "+profesor.getNombre()+", con numero de control "+profesor.getnControl());
        }
        else{
            System.out.println("No se ha eliminado el profesor, ya que cuenta con materias asignadas");
        }
    }

    public static void actualizarDatosComunes(){
        System.out.println("Has seleccionado la opcion Actualizar Profesor.");
        mostrarAllProfesores();
        Profesor profesor = obtenerProfesor();
        Utils.actualizarInformacion(profesor);
    }
}
