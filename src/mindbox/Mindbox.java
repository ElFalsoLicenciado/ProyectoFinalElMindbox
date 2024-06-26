package mindbox;

import java.util.*;

import academicinfo.*;
import gson.deserializers.MindboxDeserializer;
import gson.deserializers.UserDeserializer;
import gson.serializers.MindboxSerializer;
import gson.serializers.UserSerializer;
import users.*;
import users.utils.*;
import utils.*;

public class Mindbox {

    public static HashMap<Role, ArrayList<User>> users = new HashMap<>(); // the role is the key, the arraylist is the value assigned to each key

    public static HashMap<Careers, ArrayList<Semester>> semesters = new HashMap<>();

    public static ArrayList<Graduates> graduates = new ArrayList<>();

    public static Semester getSemester(String sem) {
        ArrayList<Semester> semesters = Mindbox.semesters.get(UserInSession.getInstance().getCurrentUser().getCareer());
        for (Semester semester : semesters) {
            if (semester.getId().equals(sem)) {
                return semester;
            }
        }
        return null;
    }

    public static Group getGroup(String gru) {
        ArrayList<Semester> semesters = Mindbox.semesters.get(UserInSession.getInstance().getCurrentUser().getCareer());
        for (Semester semester : semesters) {
            for (Group group : semester.getGroups()) {
                if (group.getGroupId().equals(gru)) {
                    return group;
                }
            }
        }
        return null;
    }

    public static Subject getSubject(String mat) {
        ArrayList<Semester> semesters = Mindbox.semesters.get(UserInSession.getInstance().getCurrentUser().getCareer());
        for (Semester semester : semesters) {
            for (Group group : semester.getGroups()) {
                for (Subject subject : group.getSubjects()) {
                    if (subject.getId().equals(mat)) {
                        return subject;
                    }
                }
            }
        }
        return null;
    }

    public static Student getStudent(String alu) {
        for (User user : users.get(Role.STUDENT)) {
            Student student = (Student) user;
            if (student.getControlNumber().equals(alu)) {
                return student;
            }
        }
        return null;
    }

    public static Coordinator getCoordinator(String coo) {
        for (User user : users.get(Role.COORDINATOR)) {
            if (user.getRole().equals(Role.COORDINATOR)) {
                Coordinator coordinator = (Coordinator) user;
                if (coordinator.getControlNumber().equals(coo)) {
                    return coordinator;
                }
            } else {
                getTeacher(coo);
            }
        }
        return null;
    }

    public static Teacher getTeacher(String tea) {
        for (User user : users.get(Role.TEACHER)) {
            if (user.getRole().equals(Role.TEACHER)) {
                Teacher teacher = (Teacher) user;
                if (teacher.getControlNumber().equals(tea)) {
                    return teacher;
                }
            } else {
                getCoordinator(tea);
            }
        }
        return null;
    }

    public static Worker getWorker(String tea) {
        for (User user : users.get(Role.TEACHER)) {
            Worker teacher = (Worker) user;
            if (teacher.getControlNumber().equals(tea)) {
                return teacher;
            }
        }
        for (User user : users.get(Role.COORDINATOR)) {
            Worker teacher = (Worker) user;
            if (teacher.getControlNumber().equals(tea)) {
                return teacher;
            }
        }
        return null;
    }

    public static void usersFirstRun() {
        users.put(Role.COORDINATOR, new ArrayList<>());
        users.put(Role.STUDENT, new ArrayList<>());
        users.put(Role.TEACHER, new ArrayList<>());

        Mindbox.registerCoordinators();
        Mindbox.registerDefined();
    }

    public static void mindboxFirstRun() {
        Mindbox.registerSemesters();
    }

    public static void registerDefined() {
        Semester semester = null;
        Group group = null;
        for (Semester semester1 : Mindbox.semesters.get(Careers.Systems)) {
            if (semester1.getSemesterNumber() == 1) {
                semester = semester1;
            }
        }

        for (Group group1 : semester.getGroups()) {
            if (group1.getGroupType().equals("A")) {
                group = group1;
            }
        }

        Student student1 = new Student("Luis Arturo", "Roman Sanchez", "04-01-2005", "Morelia", "Michoacan", "ROSL050104HMNMNSA3", "Address 123", Careers.Systems, "IL24ISC00", "pass");
        student1.setSemester(semester.getId());
        student1.setGroup(group.getGroupId());
        student1.setNullGrades(semester, group);
        student1.setGroupType(group.getGroupType());
        group.getStudentList().add(student1.getControlNumber());
        Student student2 = new Student("Alberto", "Montoya Arriaga", "04-01-2005", "Morelia", "Michoacan", "MOAA050104HMNMNTA9", "Address 123", Careers.Systems, "IA24ISC00", "123");
        student2.setSemester(semester.getId());
        student2.setGroup(group.getGroupId());
        student2.setNullGrades(semester, group);
        student2.setGroupType(group.getGroupType());
        group.getStudentList().add(student2.getControlNumber());
        Student student3 = new Student("Rene Antonio", "Olvera Estrada", "04-01-2005", "Morelia", "Michoacan", "OERR050104HMNLNR09", "Address 123", Careers.Systems, "IR24ISC00", "123");
        student3.setSemester(semester.getId());
        student3.setGroup(group.getGroupId());
        student3.setNullGrades(semester, group);
        student3.setGroupType(group.getGroupType());
        group.getStudentList().add(student3.getControlNumber());
        Mindbox.users.get(Role.STUDENT).add(student1);
        Mindbox.users.get(Role.STUDENT).add(student2);
        Mindbox.users.get(Role.STUDENT).add(student3);

        Teacher teacher1 = new Teacher("Jose Alfredo", "Jimenez Murillo", "15-06-1965", "Morelia", "Michoacan", "JIMM650615HMLMRJF7", "Address 123", Careers.Systems, "MM24ISC00", "123", "JIMM650615KP8", 20000.0f);
        Mindbox.users.get(Role.TEACHER).add(teacher1);
        Teacher teacher2 = new Teacher("Eder", "Rivera Cisneros", "12-06-1998", "Morelia", "Michoacan", "RICN980612HMLVDRP8", "Address 123", Careers.Systems, "MM24ISC00", "123", "RICN980612FJ2", 20000.0f);
        Mindbox.users.get(Role.TEACHER).add(teacher2);
        Teacher teacher3 = new Teacher("Ruben", "Vega Cano", "10-06-1985", "Morelia", "Michoacan", "VECR850610HMLGNBR4", "Address 123", Careers.Systems, "MM24ISC00", "123", "VECR850610DA6", 20000.0f);
        Mindbox.users.get(Role.TEACHER).add(teacher3);
    }

    public static void registerCoordinators() {
        Coordinator coordinator1 = new Coordinator("Laura Nelly", "Alvarado Zamora", "10-4-1974", "Morelia", "Michoacan", "AAZL740410MMLRNRD7", "Address 123", Careers.Systems, "CN24ISC00", "123", "AAZL740410GK5", 65000.0f);
        Coordinator coordinator2 = new Coordinator("Eduardo Saenz", "Cabezon Irrigaray", "12-3-1972", "Morelia", "Michoacan", "SACE720312HMLBNDF3", "Address 123", Careers.Electronics, "CE24ELC00", "123", "SACE720312DI9", 65000.0f);
        Coordinator coordinator3 = new Coordinator("Derek Alexander", "Muller", "7-9-1982", "Morelia", "Michoacan", "MUAD820907HMLLRRK9", "Address 123", Careers.Materials, "CD24IMAT00", "123", "MUAD820907PU7", 65000.0f);
        users.get(Role.COORDINATOR).add(coordinator1);
        users.get(Role.COORDINATOR).add(coordinator2);
        users.get(Role.COORDINATOR).add(coordinator3);
    }

    public static User verifyLogin(String controlNumber, String password) {
        for (Map.Entry<Role, ArrayList<User>> entry : users.entrySet()) {
            ArrayList<User> userList = entry.getValue();
            for (User currentUser : userList) {
                if (currentUser.getControlNumber().equals(controlNumber) && currentUser.getPassword().equals(password)) {
                    return currentUser;
                }
            }
        }
        return null;
    }

    public static void registerSemesters() {
        for (Careers career : Careers.values()) {
            Mindbox.semesters.put(career, new ArrayList<>());

            for (int i = 1; i <= 3; i++) {
                String id = "S" + i + career.getCode();
                Semester semester = new Semester(id, i, career);

                Group groupA = new Group(career, id, "A", "G" + i + "A" + career.getCode());
                Group groupB = new Group(career, id, "B", "G" + i + "B" + career.getCode());

                semester.getGroups().add(groupA);
                semester.getGroups().add(groupB);

                if (career.getCode().equals("ISC")) {
                    initializeSubjectsISC(semester, groupA);
                    initializeSubjectsISC(semester, groupB);
                } else if (career.getCode().equals("IMAT")) {
                    initializeSubjectsIMAT(semester, groupA);
                    initializeSubjectsIMAT(semester, groupB);
                } else {
                    initializeSubjectsELC(semester, groupA);
                    initializeSubjectsELC(semester, groupB);
                }
                Mindbox.semesters.get(career).add(semester);
            }
        }
    }

    private static void initializeSubjectsISC(Semester semester, Group group) {
        ArrayList<Subject> subjects = new ArrayList<>();
        switch (semester.getSemesterNumber()) {
            case 1:
                subjects.add(new Subject("Programming 1" + group.getGroupType(), semester.getCareer(), group.getGroupId()));
                subjects.add(new Subject("Probability 1" + group.getGroupType(), semester.getCareer(), group.getGroupId()));
                subjects.add(new Subject("Calculus 1" + group.getGroupType(), semester.getCareer(), group.getGroupId()));
                group.getSubjects().addAll(subjects);
                break;
            case 2:
                subjects.add(new Subject("Programming 2" + group.getGroupType(), semester.getCareer(), group.getGroupId()));
                subjects.add(new Subject("Probability 2" + group.getGroupType(), semester.getCareer(), group.getGroupId()));
                subjects.add(new Subject("Calculus 2" + group.getGroupType(), semester.getCareer(), group.getGroupId()));
                group.getSubjects().addAll(subjects);
                break;
            case 3:
                subjects.add(new Subject("Programming 3" + group.getGroupType(), semester.getCareer(), group.getGroupId()));
                subjects.add(new Subject("Probability 3" + group.getGroupType(), semester.getCareer(), group.getGroupId()));
                subjects.add(new Subject("Calculus 3" + group.getGroupType(), semester.getCareer(), group.getGroupId()));
                group.getSubjects().addAll(subjects);
                break;
        }
    }

    private static void initializeSubjectsIMAT(Semester semester, Group group) {
        ArrayList<Subject> subjects = new ArrayList<>();
        switch (semester.getSemesterNumber()) {
            case 1:
                subjects.add(new Subject("Statistics 1" + group.getGroupType(), semester.getCareer(), group.getGroupId()));
                subjects.add(new Subject("Accounting 1" + group.getGroupType(), semester.getCareer(), group.getGroupId()));
                subjects.add(new Subject("Calculus 1" + group.getGroupType(), semester.getCareer(), group.getGroupId()));
                group.getSubjects().addAll(subjects);
                break;
            case 2:
                subjects.add(new Subject("Statistics 2" + group.getGroupType(), semester.getCareer(), group.getGroupId()));
                subjects.add(new Subject("Accounting 2" + group.getGroupType(), semester.getCareer(), group.getGroupId()));
                subjects.add(new Subject("Calculus 2" + group.getGroupType(), semester.getCareer(), group.getGroupId()));
                group.getSubjects().addAll(subjects);
                break;
            case 3:
                subjects.add(new Subject("Statistics 3" + group.getGroupType(), semester.getCareer(), group.getGroupId()));
                subjects.add(new Subject("Accounting 3" + group.getGroupType(), semester.getCareer(), group.getGroupId()));
                subjects.add(new Subject("Calculus 3" + group.getGroupType(), semester.getCareer(), group.getGroupId()));
                group.getSubjects().addAll(subjects);
                break;
        }
    }

    private static void initializeSubjectsELC(Semester semester, Group group) {
        ArrayList<Subject> subjects = new ArrayList<>();
        switch (semester.getSemesterNumber()) {
            case 1:
                subjects.add(new Subject("Networks 1" + group.getGroupType(), semester.getCareer(), group.getGroupId()));
                subjects.add(new Subject("Circuits 1" + group.getGroupType(), semester.getCareer(), group.getGroupId()));
                subjects.add(new Subject("Calculus 1" + group.getGroupType(), semester.getCareer(), group.getGroupId()));
                group.getSubjects().addAll(subjects);
                break;
            case 2:
                subjects.add(new Subject("Networks 2" + group.getGroupType(), semester.getCareer(), group.getGroupId()));
                subjects.add(new Subject("Circuits 2" + group.getGroupType(), semester.getCareer(), group.getGroupId()));
                subjects.add(new Subject("Calculus 2" + group.getGroupType(), semester.getCareer(), group.getGroupId()));
                group.getSubjects().addAll(subjects);
                break;
            case 3:
                subjects.add(new Subject("Networks 3" + group.getGroupType(), semester.getCareer(), group.getGroupId()));
                subjects.add(new Subject("Circuits 3" + group.getGroupType(), semester.getCareer(), group.getGroupId()));
                subjects.add(new Subject("Calculus 3" + group.getGroupType(), semester.getCareer(), group.getGroupId()));
                group.getSubjects().addAll(subjects);
                break;
        }
    }

    public static void assignTeacherToSubject() {
        Coordinator.assignTeacherToSubject();
    }

    public static void removeTeacherFromSubject() {
        Coordinator.removeTeacherFromSubject();
    }

    // Teacher
    public static void registerTeacher() {
        Teacher.registerTeacher();
    }

    public static void updateTeacher() {
        Teacher.updateCommonData();
    }

    public static void showAllTeachers() {
        Teacher.showAllTeachers();
    }

    public static void deleteTeacher() {
        Teacher.deleteTeacher();
    }

    // Students
    public static void registerStudent() {
        Student.registerStudent();
    }

    public static void updateStudent() {
        Student.updateCommonData();
    }

    public static void showAllStudents() {
        Student.showAllStudents();
    }

    public static void expelStudent() {
        Student.expelStudent();
    }

    public static void saveJson(){
        MindboxSerializer.serialize();
        UserSerializer.serialize();
    }

    public static void loadJson(){
        MindboxDeserializer.deserialize();
        UserDeserializer.deserialize();
    }
}

