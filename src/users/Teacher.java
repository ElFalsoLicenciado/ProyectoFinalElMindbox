package users;

import academicinfo.*;
import mindbox.*;
import users.*;
import users.utils.*;
import utils.*;
import java.util.*;


public class Teacher extends Worker {

    public Teacher(String name, String lastName1,String lastName2, String birthDate, String city, String state, String curp, String address, Careers career, String controlNumber,String username, String password, String RFC, int salary) {
        super(name, lastName1,lastName2, birthDate, city, state, curp, address, career, controlNumber, username,password, Role.TEACHER, RFC,salary);
    }

    public static void registerTeacher() {
        DialogHelper.info("Registering a Teacher.");
        User aux = CommonData.getCommonData(Role.TEACHER);
        String rfc = CommonData.generateRFC(aux.firstName, aux.lastName1, aux.lastName2, aux.birthDate);
        int salary = DialogHelper.intIn("Enter the teacher's salary:",0);

        Teacher teacher = new Teacher(aux.firstName, aux.lastName1,aux.lastName2 ,aux.birthDate, aux.city, aux.state, aux.curp, aux.address, aux.career, aux.controlNumber, aux.username,aux.password, rfc, salary);
        Mindbox.users.get(Role.TEACHER).add(teacher);
    }

    public static void showAllTeachers() {
        ArrayList<User> teacherUsers = Mindbox.users.get(Role.TEACHER);
        ArrayList<String> aux = new ArrayList<>();
        aux.add("Teachers of Mindbox");
        for (User user : teacherUsers) {
            if (user.getCareer().equals(UserInSession.getInstance().getCurrentUser().getCareer())) {
                Teacher teacher = (Teacher) user;
                aux.add(teacher.toString());
            }
        }
        DialogHelper.info(Arrays.toString(aux.toArray()).replace("[", "").replace("]", ""));
    }

    public static Teacher getTeacher() {
        ArrayList<User> teachers = Mindbox.users.get(Role.TEACHER);
        String controlNumber = "";
        boolean found = false;
        Teacher teacher = null;
        do {
            showAllTeachers();
            controlNumber = DialogHelper.stringIn("Enter the control number of the teacher you want:");
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
            DialogHelper.error("There are no teachers to delete.");
            return;
        }
        Teacher teacher = getTeacher();
        if (teacher.getSubjects().isEmpty()) {
            Mindbox.users.get(Role.TEACHER).remove(teacher);
            DialogHelper.info("The teacher " + teacher.getFirstName() + " " + teacher.getLastName() + " with control number " + teacher.getControlNumber() + " has been deleted.");
        } else {
            DialogHelper.warning("The teacher has not been deleted because they have assigned subjects.");
        }
    }

    public static void updateCommonData() {
        showAllTeachers();
        Teacher teacher = getTeacher();
        CommonData.updateInformation(teacher);
    }

}

