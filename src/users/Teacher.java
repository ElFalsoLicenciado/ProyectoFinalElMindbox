package users;

import academicinfo.*;
import mindbox.*;
import users.*;
import users.utils.*;
import utils.*;
import java.util.*;

public class Teacher extends Worker {
    public Teacher(String name, String lastName, String birthDate, String city, String state, String curp, String address, Careers career, String controlNumber, String password, String RFC, float salary) {
        super(name, lastName, birthDate, city, state, curp, address, career, controlNumber, password, Role.TEACHER, RFC, salary);
    }

    public static void registerTeacher() {
        System.out.println("You have selected the option to register a Teacher.");
        ArrayList<String> commonData = CommonData.getCommonData(Role.TEACHER);
        Scanner sc = new Scanner(System.in);

        String name = commonData.get(0);
        String lastName = commonData.get(1);
        String birthDate = commonData.get(2);
        String city = commonData.get(3);
        String state = commonData.get(4);
        String curp = commonData.get(5);
        String address = commonData.get(6);
        String password = commonData.get(7);
        String controlNumber = commonData.get(8);
        Careers career = commonData.get(9).equals("Systems") ? Careers.Systems : commonData.get(9).equals("Materials") ? Careers.Materials : Careers.Electronics;
        String rfc = commonData.get(10);
        float salary = 0;
        do {
            try {
                System.out.println("Enter the teacher's salary:");
                salary = sc.nextFloat();
                if (salary <= 0) {
                    salary = 0;
                    throw new Exception("");
                }
            } catch (InputMismatchException e) {
                System.out.println("Value must be a number.");
                sc.nextLine();
            } catch (Exception e) {
                System.out.println("Value must be greater than 0.");
                sc.nextLine();
            }
        } while (salary == 0);

        Teacher teacher = new Teacher(name, lastName, birthDate, city, state, curp, address, career, controlNumber, password, rfc, salary);
        Mindbox.users.get(Role.TEACHER).add(teacher);
        System.out.println("Teacher successfully registered.");
    }

    public static void showAllTeachers() {
        ArrayList<User> teacherUsers = Mindbox.users.get(Role.TEACHER);
        System.out.println("Teachers of Mindbox");
        for (User user : teacherUsers) {
            if (user.getCareer().equals(UserInSession.getInstance().getCurrentUser().getCareer())) {
                Teacher teacher = (Teacher) user;
                System.out.println(teacher.toString());
            }
        }
    }

    public static Teacher getTeacher() {
        Scanner sc = new Scanner(System.in);
        ArrayList<User> teachers = Mindbox.users.get(Role.TEACHER);
        String controlNumber = "";
        boolean found = false;
        Teacher teacher = null;
        do {
            showAllTeachers();
            System.out.println("Enter the control number of the teacher you want:");
            controlNumber = sc.nextLine();
            for (User user : teachers) {
                if (user.getControlNumber().equals(controlNumber)) {
                    found = true;
                    teacher = (Teacher) user;
                    break;
                }
            }
        } while (!found);
        return teacher;
    }

    public static void deleteTeacher() {
        if (Mindbox.users.get(Role.TEACHER).isEmpty()) {
            System.out.println("There are no teachers to delete.");
            return;
        }
        System.out.println("You have selected the option to delete a Teacher.");
        Teacher teacher = getTeacher();
        if (teacher.getSubjects().isEmpty()) {
            Mindbox.users.get(Role.TEACHER).remove(teacher);
            System.out.println("The teacher " + teacher.getFirstName() + " " + teacher.getLastName() + " with control number " + teacher.getControlNumber() + " has been deleted.");
        } else {
            System.out.println("The teacher has not been deleted because they have assigned subjects.");
        }
    }

    public static void updateCommonData() {
        System.out.println("You have selected the option to Update Teacher.");
        showAllTeachers();
        Teacher teacher = getTeacher();
        CommonData.updateInformation(teacher);
    }
}
