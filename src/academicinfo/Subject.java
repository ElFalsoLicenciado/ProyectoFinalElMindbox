package academicinfo;

import java.util.EnumSet;

public enum Subject {
    PROGRAMMING_1_ISC("Programming 1", CareerType.ISC, Semester.FIRST, "Teacher A"),
    PROBABILITY_1_ISC("Probability 1", CareerType.ISC, Semester.FIRST, "Teacher B"),
    CALCULUS_1_ISC("Calculus 1", CareerType.ISC, Semester.FIRST, "Teacher C"),
    PROGRAMMING_2_ISC("Programming 2", CareerType.ISC, Semester.SECOND, "Teacher A"),
    PROBABILITY_2_ISC("Probability 2", CareerType.ISC, Semester.SECOND, "Teacher B"),
    CALCULUS_2_ISC("Calculus 2", CareerType.ISC, Semester.SECOND, "Teacher C"),
    PROGRAMMING_3_ISC("Programming 3", CareerType.ISC, Semester.THIRD, "Teacher A"),
    PROBABILITY_3_ISC("Probability 3", CareerType.ISC, Semester.THIRD, "Teacher B"),
    CALCULUS_3_ISC("Calculus 3", CareerType.ISC, Semester.THIRD, "Teacher C"),

    STATISTICS_1_IMAT("Statistics 1", CareerType.IMAT, Semester.FIRST, "Teacher D"),
    ACCOUNTING_1_IMAT("Accounting 1", CareerType.IMAT, Semester.FIRST, "Teacher E"),
    CALCULUS_1_IMAT("Calculus 1", CareerType.IMAT, Semester.FIRST, "Teacher F"),
    STATISTICS_2_IMAT("Statistics 2", CareerType.IMAT, Semester.SECOND, "Teacher D"),
    ACCOUNTING_2_IMAT("Accounting 2", CareerType.IMAT, Semester.SECOND, "Teacher E"),
    CALCULUS_2_IMAT("Calculus 2", CareerType.IMAT, Semester.SECOND, "Teacher F"),
    STATISTICS_3_IMAT("Statistics 3", CareerType.IMAT, Semester.THIRD, "Teacher D"),
    ACCOUNTING_3_IMAT("Accounting 3", CareerType.IMAT, Semester.THIRD, "Teacher E"),
    CALCULUS_3_IMAT("Calculus 3", CareerType.IMAT, Semester.THIRD, "Teacher F"),

    NETWORKS_1_ELC("Networks 1", CareerType.ELC, Semester.FIRST, "Teacher G"),
    CIRCUITS_1_ELC("Circuits 1", CareerType.ELC, Semester.FIRST, "Teacher H"),
    CALCULUS_1_ELC("Calculus 1", CareerType.ELC, Semester.FIRST, "Teacher I"),
    NETWORKS_2_ELC("Networks 2", CareerType.ELC, Semester.SECOND, "Teacher G"),
    CIRCUITS_2_ELC("Circuits 2", CareerType.ELC, Semester.SECOND, "Teacher H"),
    CALCULUS_2_ELC("Calculus 2", CareerType.ELC, Semester.SECOND, "Teacher I"),
    NETWORKS_3_ELC("Networks 3", CareerType.ELC, Semester.THIRD, "Teacher G"),
    CIRCUITS_3_ELC("Circuits 3", CareerType.ELC, Semester.THIRD, "Teacher H"),
    CALCULUS_3_ELC("Calculus 3", CareerType.ELC, Semester.THIRD, "Teacher I");

    private final String subjectName;
    private final CareerType career;
    private final Semester semester;
    private final String teacherName;

    Subject(String subjectName, CareerType career, Semester semester, String teacherName) {
        this.subjectName = subjectName;
        this.career = career;
        this.semester = semester;
        this.teacherName = teacherName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public CareerType getCareer() {
        return career;
    }

    public Semester getSemester() {
        return semester;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getId() {
        return name();
    }

    public static EnumSet<Subject> getSubjectsByCareerAndSemester(CareerType career, Semester semester) {
        EnumSet<Subject> subjects = EnumSet.noneOf(Subject.class);
        for (Subject subject : Subject.values()) {
            if (subject.getCareer() == career && subject.getSemester() == semester) {
                subjects.add(subject);
            }
        }
        return subjects;
    }

    public static void main(String[] args) {
        CareerType career = CareerType.ISC;
        Semester semester = Semester.FIRST;
        EnumSet<Subject> subjects = Subject.getSubjectsByCareerAndSemester(career, semester);

        System.out.println("Subjects for " + career + " in " + semester + ":");
        for (Subject subject : subjects) {
            System.out.println(subject.getSubjectName());
        }
    }
}
