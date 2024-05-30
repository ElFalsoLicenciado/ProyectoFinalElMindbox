package academicinfo;

public enum Semester {
    FIRST(1),
    SECOND(2),
    THIRD(3),
    GRADUATE(4);

    private final int number;

    Semester(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public Semester next() {
        switch (this) {
            case FIRST:
                return SECOND;
            case SECOND:
                return THIRD;
            case THIRD:
                return GRADUATE;
            default:
                return GRADUATE; // or throw an exception if there's no next semester after GRADUATE
        }
    }
}
