package users;
import java.util.*;

import academicinfo.*;
import mindbox.*;
import users.utils.*;
import utils.*;

import static users.Coordinator.showAllCoordinators;

public class Worker extends User {

    protected ArrayList<String> subjects;
    protected String RFC;
    protected float salary;

    public Worker(String firstName, String lastName, String birthDate, String city, String state, String curp, String address, Careers career, String controlNumber, String password, Role role, String RFC, float salary) {
        super(firstName, lastName, birthDate, city, state, curp, address, career, controlNumber, password, role);
        this.RFC = RFC;
        this.salary = salary;
        this.subjects = new ArrayList<>();
    }

    public String toString() {
        return String.format(
                "Name: %s, Last Name: %s, Control Number: %s, Birth Date: %s, City: %s, State: %s, CURP: %s, Address: %s, Registration Date: %s, Career: %s, RFC: %s, Salary: %.2f",
                getFirstName(), getLastName(), getControlNumber(), getBirthDate(), getCity(), getState(), getCurp(), getAddress(), getRegistrationDate(), getCareer(), RFC, salary
        );
    }

    public void filterBySemester() {
        DialogHelper.info("Filter by Semester");
        int semesterNumber = 0;
        switch (DialogHelper.optionD("Which semester grades do you want to view?",new String[]{"1°","2°","3°"})) {
            case 0 -> semesterNumber = 1;
            case 1 -> semesterNumber = 2;
            case 2 -> semesterNumber = 3;
            case 3 -> semesterNumber = 4;
        }

        if (semesterNumber != 4) {
            Semester semester = Semester.getSemesterByNumber(semesterNumber);
            boolean subjectsFound = false;
            ArrayList<String> passed = new ArrayList<>();
            ArrayList<String> failed = new ArrayList<>();
            ArrayList<String> all = new ArrayList<>();
            assert semester != null;
            for (Group group : semester.getGroups()) {
                ArrayList<Subject> subjects = group.getSubjects();
                for (Subject subject : subjects) {
                    if (subject.getTeacher() != null) {
                        if (Mindbox.getWorker(subject.getTeacher()).equals(UserInSession.getInstance().getCurrentUser())) {
                            subjectsFound = true;
                            ArrayList<String> checkPassed = subject.getApprovedStudents(semester.getId());
                            for (String student : checkPassed) {
                                if (!passed.contains(student)) {
                                    passed.add(student);
                                }
                            }
                            ArrayList<String> checkFailed = subject.getFailedStudents(semester.getId());
                            for (String student : checkFailed) {
                                if (!failed.contains(student)) {
                                    failed.add(student);
                                }
                            }
                            ArrayList<String> checkAll = subject.getStudentsInSubject();
                            for (String student : checkAll) {
                                if (!all.contains(student)) {
                                    all.add(student);
                                }
                            }
                        }
                    }
                }
            }
            if (subjectsFound) {
                boolean flag = true;
                do {
                    switch (DialogHelper.optionD("What type of students do you want to see?",new String[]{"Passed","Failed","All","Return","XD"})) {
                        case 0 -> {
                            StringBuilder passedStudents = new StringBuilder("All passed students:\n");
                            for (String student : passed) {
                                passedStudents.append(Objects.requireNonNull(Mindbox.getStudent(student))).append("\n");
                                passedStudents.append("Group: ").append(Objects.requireNonNull(Mindbox.getStudent(student)).getGroup()).append("\n\n");
                            }
                            DialogHelper.info(passedStudents.toString());
                        }
                        case 1 -> {
                            StringBuilder failedStudents = new StringBuilder("All failed students:\n");
                            for (String student : failed) {
                                failedStudents.append(Objects.requireNonNull(Mindbox.getStudent(student)).toString()).append("\n");
                                failedStudents.append("Group: ").append(Objects.requireNonNull(Mindbox.getStudent(student)).getGroup()).append("\n\n");
                            }
                            DialogHelper.info(failedStudents.toString());
                        }
                        case 2 -> {
                            StringBuilder allStudents = new StringBuilder("All students:\n");
                            for (String student : all) {
                                allStudents.append(Objects.requireNonNull(Mindbox.getStudent(student)).toString()).append("\n");
                                allStudents.append("Group: ").append(Objects.requireNonNull(Mindbox.getStudent(student)).getGroup()).append("\n\n");
                            }
                            DialogHelper.info(allStudents.toString());
                        }
                        case 3 -> {
                            DialogHelper.returnD();
                            flag = false;
                        }
                    }
                } while (flag);
            } else DialogHelper.warning("There are no subjects you are teaching in that semester.");
        } else DialogHelper.warning("Semester not found.");
    }

    public void filterByGroup() {
        DialogHelper.info("Filter by Group");
        int semesterNumber = 0;
        String groupType = "";
        switch (DialogHelper.optionD("Which semester grades do you want to view?",new String[]{"1°","2°","3°"})) {
            case 0 -> semesterNumber = 1;
            case 1 -> semesterNumber = 2;
            case 2 -> semesterNumber = 3;
            case 3 -> semesterNumber = 4;
        }
        if (semesterNumber != 4) {
            Semester semester = Semester.getSemesterByNumber(semesterNumber);

            switch (DialogHelper.optionD("Which group do you want?",new String[]{"A","B"})){
                case 0 -> groupType = "A";
                case 1 -> groupType = "B";
            }
            assert semester != null;
            Group group = Group.getGroup(groupType,semester);
            if (group != null) {
                ArrayList<Subject> subjects = group.getSubjects();
                ArrayList<String> passed = new ArrayList<>();
                ArrayList<String> failed = new ArrayList<>();
                ArrayList<String> all = new ArrayList<>();
                boolean subjectsFound = false;
                for (Subject subject : subjects) {
                    if (subject.getTeacher() != null) {
                        if (Objects.equals(Mindbox.getWorker(subject.getTeacher()), UserInSession.getInstance().getCurrentUser())) {
                            subjectsFound = true;
                            ArrayList<String> checkPassed = subject.getApprovedStudents(semester.getId());
                            for (String student : checkPassed) {
                                if (!passed.contains(student)) {
                                    passed.add(student);
                                }
                            }
                            ArrayList<String> checkFailed = subject.getFailedStudents(semester.getId());
                            for (String student : checkFailed) {
                                if (!failed.contains(student)) {
                                    failed.add(student);
                                }
                            }
                            ArrayList<String> checkAll = subject.getStudentsInSubject();
                            for (String student : checkAll) {
                                if (!all.contains(student)) {
                                    all.add(student);
                                }
                            }
                        }
                    }
                }
                if (subjectsFound) {
                    boolean exit = false;
                    do {
                        switch (DialogHelper.optionD("What type of students do you want to see?",new String[]{"Passed","Failed","All","Return"})) {
                            case 0 -> {
                                StringBuilder passedStudents = new StringBuilder("All passed students:\n");
                                for (String student : passed) {
                                    passedStudents.append(Mindbox.getStudent(student).toString()).append("\n");
                                    passedStudents.append("Group: ").append(Mindbox.getStudent(student).getGroup()).append("\n\n");
                                }
                                DialogHelper.info(passedStudents.toString());
                            }
                            case 1 -> {
                                StringBuilder failedStudents = new StringBuilder("All failed students:\n");
                                for (String student : failed) {
                                    failedStudents.append(Mindbox.getStudent(student).toString()).append("\n");
                                    failedStudents.append("Group: ").append(Mindbox.getStudent(student).getGroup()).append("\n\n");
                                }
                                DialogHelper.info(failedStudents.toString());
                            }
                            case 2 -> {
                                StringBuilder allStudents = new StringBuilder("All students:\n");
                                for (String student : all) {
                                    allStudents.append(Mindbox.getStudent(student).toString()).append("\n");
                                    allStudents.append("Group: ").append(Mindbox.getStudent(student).getGroup()).append("\n\n");
                                }
                                DialogHelper.info(allStudents.toString());
                            }
                            case 3 -> {
                                DialogHelper.returnD();
                                exit = true;
                            }

                        }
                    } while (!exit);
                }
            } else {
                DialogHelper.warning("Group not found.");
            }
        } else {
            DialogHelper.warning("Semester not found.");
        }
    }

    public void filterBySubject() {
        DialogHelper.info("Filter by Subject");
        boolean exit = false;
        String subjectId;
        do {
            showMySubjects();
            subjectId = DialogHelper.stringIn("Enter the name of the subject you want to filter:");
            if (subjects.contains(subjectId)) exit = true;
            else DialogHelper.warning("Invalid subject.");
        } while (!exit);
        exit = false;
        Semester semester = Mindbox.getSemester(Mindbox.getGroup(Mindbox.getSubject(subjectId).getGroup()).getSemester());
        do {
            switch (DialogHelper.optionD("What type of students do you want to see?\n1. Passed.\n2. Failed.\n3. All.\nE. Exit the menu.",new String[]{"Passed","Failed","All","Exit"})) {
                case 0 -> {
                    StringBuilder passedStudents = new StringBuilder("Passed students in the subject " + Mindbox.getSubject(subjectId).getId() + "\n");
                    ArrayList<String> checkPassed = Mindbox.getSubject(subjectId).getApprovedStudents(semester.getId());
                    for (String student : checkPassed) {
                        passedStudents.append(Mindbox.getStudent(student).toString()).append("\n");
                        passedStudents.append("Group: ").append(Mindbox.getStudent(student).getGroup()).append("\n");
                        String grade = Mindbox.getStudent(student).getGrades().get(semester.getId()).get(Mindbox.getSubject(subjectId).getId()) == null ? "Not assigned." : String.valueOf(Mindbox.getStudent(student).getGrades().get(semester.getId()).get(Mindbox.getSubject(subjectId).getId()));
                        passedStudents.append("Grade: ").append(grade).append("\n\n");
                    }
                    DialogHelper.info(passedStudents.toString());
                }
                case 1 -> {
                    StringBuilder failedStudents = new StringBuilder("Failed students in the subject " + Mindbox.getSubject(subjectId).getId() + "\n");
                    ArrayList<String> checkFailed = Mindbox.getSubject(subjectId).getFailedStudents(semester.getId());
                    for (String student : checkFailed) {
                        failedStudents.append(Mindbox.getStudent(student).toString()).append("\n");
                        failedStudents.append("Group: ").append(Mindbox.getStudent(student).getGroup()).append("\n");
                        String grade = Mindbox.getStudent(student).getGrades().get(semester.getId()).get(Mindbox.getSubject(subjectId).getId()) == null ? "Not assigned." : String.valueOf(Mindbox.getStudent(student).getGrades().get(semester.getId()).get(Mindbox.getSubject(subjectId).getId()));
                        failedStudents.append("Grade: ").append(grade).append("\n\n");
                    }
                    DialogHelper.info(failedStudents.toString());
                }
                case 2 -> {
                    StringBuilder allStudents = new StringBuilder("All students in the subject " + Mindbox.getSubject(subjectId).getId() + "\n");
                    ArrayList<String> checkAll = Mindbox.getSubject(subjectId).getStudentsInSubject();
                    for (String student : checkAll) {
                        allStudents.append(Mindbox.getStudent(student).toString()).append("\n");
                        allStudents.append("Group: ").append(Mindbox.getStudent(student).getGroup()).append("\n");
                        String grade = Mindbox.getStudent(student).getGrades().get(semester.getId()).get(Mindbox.getSubject(subjectId).getId()) == null ? "Not assigned." : String.valueOf(Mindbox.getStudent(student).getGrades().get(semester.getId()).get(Mindbox.getSubject(subjectId).getId()));
                        allStudents.append("Grade: ").append(grade).append("\n\n");
                    }
                    DialogHelper.info(allStudents.toString());
                }
                case 3 -> {
                    DialogHelper.returnD();
                    exit = true;
                }
            }
        } while (!exit);
    }

    public void showMySubjects() {
        if (subjects.isEmpty()) {
            DialogHelper.error("You currently have no subjects assigned.");
        } else {
            StringBuilder subjectsInfo = new StringBuilder();
            for (String subject : subjects) {
                subjectsInfo.append(Mindbox.getSubject(subject).toString()).append("\n");
            }
            DialogHelper.info(subjectsInfo.toString());
        }
    }

    public static Worker getWorker() {
        String controlNumber;
        boolean found = false;
        Worker teacher = null;
        do {
            Teacher.showAllTeachers();
            showAllCoordinators();
            controlNumber = DialogHelper.stringIn("Enter the control number of the teacher you want:");
            for (User user : Mindbox.users.get(Role.COORDINATOR)) {
                if (user.getControlNumber().equals(controlNumber)) {
                    found = true;
                    teacher = (Worker) user;
                    break;
                }
            }
            for (User user : Mindbox.users.get(Role.TEACHER)) {
                if (user.getControlNumber().equals(controlNumber)) {
                    found = true;
                    teacher = (Worker) user;
                    break;
                }
            }
        } while (!found);
        return teacher;
    }

    public String getSubject() {
        String id = "";
        boolean valid = true;
        if (subjects.isEmpty()) {
            DialogHelper.error("You have no subjects assigned.");
            return null;
        }
        do {
            StringBuilder subjectsList = new StringBuilder();
            for (String subjectId : subjects) {
                Subject subject = Mindbox.getSubject(subjectId);
                subjectsList.append("[ ").append(subjects.indexOf(subject.getId())).append(" ] Subject: ").append(subject.getId()).append(" Career: ").append(subject.getCareer().getFullName()).append(" Semester: ").append(Mindbox.getSemester(Mindbox.getGroup(subject.getGroup()).getSemester()).getId()).append(" Group: ").append(Mindbox.getGroup(subject.getGroup()).getGroupType()).append("\n");
            }
            try {
                id = subjects.get(DialogHelper.intIn(subjectsList.toString(),subjects.size())-1);
                valid = false;
            } catch (IndexOutOfBoundsException e) {
                DialogHelper.warning("Enter a valid index.");
            }
        } while (valid);
        return id;
    }

    public void assignGrade() {
        int grade = 0;
        boolean valid = true, continueAssigning = true;
        Subject subject = Mindbox.getSubject(getSubject());
        if (subject == null) {
            return;
        }
        Group group = Mindbox.getGroup(subject.getGroup());
        if (group.getStudentList().isEmpty()) {
            DialogHelper.info("There are no students in this subject for now.");
            return;
        }
        if (group.getStudentList().size() < 3) {
            DialogHelper.info("The group does not have at least 3 students.");
            return;
        }
        do {
            Student student = Mindbox.getStudent(subject.getStudent(subject.getId()));
            HashMap<String, Integer> semesterGrades = student.getGrades().get(student.getSemester());
            if (semesterGrades.get(subject.getId()) != null) {
                int existingGrade = semesterGrades.get(subject.getId());
                do {
                    String response = DialogHelper.stringIn("The student already has an assigned grade: " + existingGrade + "\nDo you want to modify the grade? [Y]/[N]");
                    if (response.equalsIgnoreCase("n")) {
                        return;
                    } else if (response.equalsIgnoreCase("y")) {
                        break;
                    } else {
                        DialogHelper.warning("Invalid option.");
                    }
                } while (true);
            }
            do {
                try {
                    grade = Integer.parseInt(DialogHelper.stringIn("Enter the grade to assign:"));
                    if (grade < 0 || grade > 100) {
                        throw new Exception("");
                    }
                    valid = false;
                } catch (NumberFormatException e) {
                    DialogHelper.warning("The value must be an integer.");
                } catch (Exception e) {
                    DialogHelper.warning("The value must be between 0 and 100.");
                }
            } while (valid);
            student.getGrades().get(student.getSemester()).remove(subject.getId());
            student.getGrades().get(student.getSemester()).put(subject.getId(), grade);
            DialogHelper.info("Grade changed successfully.");
        } while (DialogHelper.confirmD("Do you want to continue assigning grades?"));
    }
    // Getters
    public ArrayList<String> getSubjects() {
        return subjects;
    }

    public String getRFC() {
        return RFC;
    }

    public float getSalary() {
        return salary;
    }

    // Setters
    public void setSubjects(ArrayList<String> subjects) {
        this.subjects = subjects;
    }

    public void setRFC(String RFC) {
        this.RFC = RFC;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }
}

