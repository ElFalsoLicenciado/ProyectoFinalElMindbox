package users;

import academicinfo.Careers;
import academicinfo.Group;
import academicinfo.Semester;
import academicinfo.Subject;
import mindbox.Mindbox;
import users.utils.CommonData;
import users.utils.Role;
import utils.DialogHelper;
import utils.UserInSession;

import javax.tools.Diagnostic;
import java.time.LocalDate;
import java.util.*;

public class Coordinator extends Worker {
    private int coord;

    public Coordinator(String name, String lastName1,String lastName2, String birthDate, String city, String state, String curp, String address, Careers career, String controlNumber,String username, String password, String RFC, int salary) {
        super(name, lastName1,lastName2, birthDate, city, state, curp, address, career, controlNumber, username,password, Role.COORDINATOR, RFC, salary);
        this.coord = 0;
    }

    public static void assignTeacherToSubject() {
        int semesterNumber = 0;
        String groupType = "";
        Careers career = UserInSession.getInstance().getCurrentUser().getCareer();
        switch (DialogHelper.optionD("Select a semester",new String[]{"1°","2°","3°"})) {
            case 0 -> semesterNumber = 1;
            case 1 -> semesterNumber = 2;
            case 2 -> semesterNumber = 3;
        }
        Semester semester = Semester.getSemesterByNumber(semesterNumber);
        if (semester != null) {
            switch (DialogHelper.optionD("Select a group", new String[]{"A", "B"})) {
                case 0 -> groupType = "A";
                case 1 -> groupType = "B";
            }
            Group group = Group.getGroup(groupType, semester);
            if (group != null) {
                Subject subject = Subject.getSubject(semester.getId(), group.getGroupId());
                if (subject != null) {
                    Worker teacher = Worker.getWorker();
                    if (teacher != null) {
                        Worker checkTeacher = Mindbox.getWorker(subject.getTeacher());
                        if (checkTeacher == null) {
                            subject.setTeacher(teacher.getControlNumber());
                            teacher.getSubjects().add(subject.getId());
                        } else
                            DialogHelper.error("The subject already has a teacher assigned, you cannot assign another.");
                    }
                }
            } else {
                DialogHelper.error("Group not found.");
            }
        } else {
            DialogHelper.error("Semester not found.");
        }
    }

    public static void removeTeacherFromSubject() {
        int semesterNumber = 0;
        String groupType = "";
        Careers career = UserInSession.getInstance().getCurrentUser().getCareer();
        switch (DialogHelper.optionD("From which semester do you want to remove a teacher?",new String[]{"1°","2°","3°","Return"})) {
            case 0 -> semesterNumber = 1;
            case 1 -> semesterNumber = 2;
            case 2 -> semesterNumber = 3;
            case 3 -> DialogHelper.returnD();
        }


        if (semesterNumber != 3) {
            Semester semester = Semester.getSemesterByNumber(semesterNumber);
            switch (DialogHelper.optionD("Which group do you want?", new String[]{"A","B"})) {
                case 0 -> groupType = "A";
                case 1 -> groupType = "B";
            }
            Group group = Group.getGroup(groupType, semester);
            if (group != null) {
                Subject subject = Subject.getSubject(semester.getId(), group.getGroupId());
                if (subject != null) {
                    Worker checkTeacher = Mindbox.getWorker(subject.getTeacher());
                    if (checkTeacher != null) {
                        DialogHelper.info(checkTeacher.toString());
                        subject.removeTeacher();
                        checkTeacher.getSubjects().remove(subject.getId());
                    } else DialogHelper.error("The subject has no teacher, you cannot remove anything.");
                }
            } else DialogHelper.error("Group not found.");
        } else DialogHelper.error("Semester not found.");
    }

    public static void showAllCoordinators() {
        ArrayList<String> aux = new ArrayList<>();
        ArrayList<User> coordinatorUsers = Mindbox.users.get(Role.COORDINATOR);
        aux.add("Coordinators of Mindbox");
        for (User user : coordinatorUsers) {
            if (user.getCareer() == UserInSession.getInstance().getCurrentUser().getCareer()) {
                Coordinator coordinator = (Coordinator) user;
                aux.add(coordinator.toString());
            }
        }
        DialogHelper.info(Arrays.toString(aux.toArray()).replace("[","").replace("]",""));
    }

    public static Coordinator getCoordinator() {
        ArrayList<User> coordinators = Mindbox.users.get(Role.COORDINATOR);
        String controlNumber;
        boolean found = false;
        Coordinator selectedCoordinator = null;
        do {
            controlNumber = DialogHelper.stringIn("Enter the control number of the coordinator you want:");
            for (User user : coordinators) {
                selectedCoordinator = (Coordinator) user;
                if (user.getControlNumber().equals(controlNumber)) {
                    found = true;
                    break;
                }
            }
        } while (found);
        return selectedCoordinator;
    }

    public void updateCommonData() {
        showAllCoordinators();
        Coordinator coordinator = getCoordinator();
        CommonData.updateInformation(coordinator);
    }

    public static void promoteGroup() {
        int semesterNumber;
        String groupType;
        Careers career = UserInSession.getInstance().getCurrentUser().getCareer();
        Semester semester;

        do {
            semesterNumber = DialogHelper.optionD("From which semester do you want to promote the group:",new String[]{"1°","2°","3°","Return"});

            if (semesterNumber == 0 || semesterNumber == 1 || semesterNumber == 2) {
                semester = Semester.getSemesterByNumber(semesterNumber);

                groupType = DialogHelper.optionD("Enter the group you want to promote :",new String[]{"A","B"}) == 0 ? "A": "B";
                DialogHelper.info(groupType);

                Group selectedGroup = null;

                for (Group group : semester.getGroups()) {
                    selectedGroup = group;
                    if (group.getGroupType().equals(groupType)) {
                        break;
                    }
                }

                assert selectedGroup != null;
                if (selectedGroup.getStudentList().isEmpty()) {
                    DialogHelper.error("The group is empty.");
                    break;
                }

                if (!selectedGroup.checkGrades()) {
                    DialogHelper.error("Not all students have grades or do not have grades in all subjects.");
                    break;
                }
                int newSemesterNumber = semesterNumber + 1;

                ArrayList<String> failedStudents = selectedGroup.getFailedStudents();
                ArrayList<String> passedStudents = selectedGroup.getApprovedStudents();

                if (newSemesterNumber == 3) {
                    DialogHelper.info("The graduate's group.");
                    Graduates.registerGraduates(new Graduates(LocalDate.now().toString(), career, passedStudents, String.valueOf(LocalDate.now().getYear())));
                    Iterator<String> iterator = selectedGroup.getStudentList().iterator();
                    while (iterator.hasNext()) {
                        String student = iterator.next();
                        Mindbox.getStudent(student).setSemester(null);
                        if (passedStudents.contains(student)) {
                            iterator.remove();
                        }
                    }
                } else {
                    Semester newSemester = Semester.getSemesterByNumber(newSemesterNumber);

                    for (Group group : newSemester.getGroups()) {
                        if (group.getStudentList().isEmpty()) {
                            if (group.getGroupType().equals(groupType)) {

                                // Remove students from the previous group
                                selectedGroup.getStudentList().removeIf(passedStudents::contains);

                                // Add passed students to the new group
                                for (String student : passedStudents) {
                                    if (!group.getStudentList().contains(student)) {
                                        group.getStudentList().add(student);
                                        Mindbox.getStudent(student).setSemester(group.getSemester());
                                        Mindbox.getStudent(student).setGroup(group.getGroupId());
                                        Mindbox.getStudent(student).setNullGrades(Mindbox.getSemester(group.getSemester()), group);
                                    }
                                }

                                DialogHelper.info("The students of the group have been successfully promoted.");
                            }
                        } else {
                            DialogHelper.error("The group cannot be moved because it still contains students.");
                        }
                    }
                }
                for (String student : failedStudents) {
                    HashMap<String, Integer> failedGrades = new HashMap<>();
                    for (Subject subject : selectedGroup.getSubjects()) {
                        failedGrades.put(subject.getId(), null);
                    }
                    Objects.requireNonNull(Mindbox.getStudent(student)).getGrades().put(semester.getId(), failedGrades);
                }
            } else {
                DialogHelper.returnD();
                break;
            }
        } while (true);


    }

    public int getCoord() {
        return coord;
    }

    public void setCoord(int coord) {
        this.coord = coord;
    }
}
