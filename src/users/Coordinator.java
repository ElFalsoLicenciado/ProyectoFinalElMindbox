package users;

import academicinfo.*;
import mindbox.*;
import users.utils.*;
import utils.*;

import java.time.LocalDate;
import java.util.*;

public class Coordinator extends Worker {
    private int coord = 5;

    public Coordinator(String name, String lastName, String birthDate, String city, String state, String curp, String address, Careers career, String controlNumber, String password, String RFC, float salary) {
        super(name, lastName, birthDate, city, state, curp, address, career, controlNumber, password, Role.COORDINATOR, RFC, salary);
        this.coord = 0;
    }

    public static void assignTeacherToSubject() {
        int semesterNumber = 0;
        String groupType = "";
        Careers career = UserInSession.getInstance().getCurrentUser().getCareer();

        switch (DialogHelper.optionD("Which semester do you want to assign a teacher to?", new String[]{"1", "2", "3"})) {
            case 0 -> semesterNumber = 1;
            case 1 -> semesterNumber = 2;
            case 2 -> semesterNumber = 3;
        }
        Semester semester = Semester.getSemesterByNumber(semesterNumber);

        if (semester != null) {
            switch (DialogHelper.optionD("Which group do you want?", new String[]{"A", "B"})) {
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
                            DialogHelper.info("Teacher successfully assigned.");
                        } else {
                            DialogHelper.warning("The subject already has a teacher assigned, you cannot assign another.");
                        }
                    }
                }
            } else {
                DialogHelper.warning("Group not found.");
            }
        } else {
            DialogHelper.warning("Semester not found.");
        }
    }

    public static void removeTeacherFromSubject() {
        int semesterNumber = 0;
        String groupType = "";
        Careers career = UserInSession.getInstance().getCurrentUser().getCareer();

        switch (DialogHelper.optionD("From which semester do you want to remove a teacher?", new String[]{"1", "2", "3"})) {
            case 0 -> semesterNumber = 1;
            case 1 -> semesterNumber = 2;
            case 2 -> semesterNumber = 3;
        }
        Semester semester = Semester.getSemesterByNumber(semesterNumber);

        if (semester != null) {
            switch (DialogHelper.optionD("Which group do you want?", new String[]{"A", "B"})) {
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
                        DialogHelper.info("Teacher successfully removed.");
                    } else DialogHelper.warning("The subject has no teacher, you cannot remove anything.");
                }
            } else DialogHelper.warning("Group not found.");
        } else DialogHelper.warning("Semester not found.");
    }

    public static void showAllCoordinators() {
        ArrayList<User> coordinatorUsers = Mindbox.users.get(Role.COORDINATOR);
        StringBuilder coordinatorsInfo = new StringBuilder("Coordinators of Mindbox\n");
        for (User user : coordinatorUsers) {
            if (user.getCareer() == UserInSession.getInstance().getCurrentUser().getCareer()) {
                Coordinator coordinator = (Coordinator) user;
                coordinatorsInfo.append(coordinator.toString()).append("\n");
            }
        }
        DialogHelper.info(coordinatorsInfo.toString());
    }

    public static Coordinator getCoordinator() {
        ArrayList<User> coordinators = Mindbox.users.get(Role.COORDINATOR);
        String controlNumber;
        boolean found = false;
        Coordinator selectedCoordinator = null;
        do {
            controlNumber = DialogHelper.stringIn("Enter the control number of the coordinator you want:");
            for (User user : coordinators) {
                if (user.getControlNumber().equals(controlNumber)) {
                    found = true;
                    selectedCoordinator = (Coordinator) user;
                    break;
                }
            }
        } while (!found);
        return selectedCoordinator;
    }

    public void updateCommonData() {
        showAllCoordinators();
        Coordinator coordinator = getCoordinator();
        CommonData.updateInformation(coordinator);
    }

    public static void promoteGroup() {
        int semesterNumber = 0;
        String groupType = "";
        Careers career = UserInSession.getInstance().getCurrentUser().getCareer();
        Semester semester;

        switch (DialogHelper.optionD("From which semester do you want to promote the group?", new String[]{"1", "2", "3"})) {
            case 0 -> semesterNumber = 1;
            case 1 -> semesterNumber = 2;
            case 2 -> semesterNumber = 3;
            default -> {
                DialogHelper.warning("Invalid option.");
                return;
            }
        }

        semester = Semester.getSemesterByNumber(semesterNumber);

        switch (DialogHelper.optionD("Enter the group you want to promote", new String[]{"A", "B"})) {
            case 0 -> groupType = "A";
            case 1 -> groupType = "B";
            default -> {
                DialogHelper.warning("Invalid option.");
                return;
            }
        }

        Group selectedGroup = null;

        for (Group group : semester.getGroups()) {
            if (group.getGroupType().equals(groupType)) {
                selectedGroup = group;
                break;
            }
        }

        if (selectedGroup.getStudentList().isEmpty()) {
            DialogHelper.warning("The group is empty.");
            return;
        }

        if (!selectedGroup.checkGrades()) {
            DialogHelper.warning("Not all students have grades in all subjects.");
            return;
        }
        int newSemesterNumber = semesterNumber + 1;

        ArrayList<String> failedStudents = selectedGroup.getFailedStudents();
        ArrayList<String> passedStudents = selectedGroup.getApprovedStudents();

        if (newSemesterNumber == 4) {
            DialogHelper.info("The group graduates.");
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
                if (group.getStudentList().isEmpty() && group.getGroupType().equals(groupType)) {
                    Iterator<String> iterator = selectedGroup.getStudentList().iterator();
                    while (iterator.hasNext()) {
                        String student = iterator.next();
                        if (passedStudents.contains(student)) {
                            iterator.remove();
                        }
                    }
                    for (String student : passedStudents) {
                        if (!group.getStudentList().contains(student)) {
                            group.getStudentList().add(student);
                            Student studentObj = Mindbox.getStudent(student);
                            studentObj.setSemester(group.getSemester());
                            studentObj.setGroup(group.getGroupId());
                            studentObj.setNullGrades(Mindbox.getSemester(group.getSemester()), group);
                        }
                    }
                    DialogHelper.info("The students of the group have been successfully promoted.");
                    return;
                }
            }
            DialogHelper.warning("The group cannot be moved because it still contains students.");
        }

        for (String student : failedStudents) {
            HashMap<String, Integer> failedGrades = new HashMap<>();
            for (Subject subject : selectedGroup.getSubjects()) {
                failedGrades.put(subject.getId(), null);
            }
            Mindbox.getStudent(student).getGrades().put(semester.getId(), failedGrades);
        }
    }

}
