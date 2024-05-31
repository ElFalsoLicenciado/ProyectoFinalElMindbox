package academicinfo;

public enum Careers {
    Systems("ISC", "Computer Systems Engineering"),
    Materials("IMAT", "Materials Engineering"),
    Electronics("ELC", "Electronic Engineering");

    private String code;
    private String fullName;

    Careers(String code, String fullName) {
        this.code = code;
        this.fullName = fullName;
    }

    public String getCode() {
        return code;
    }

    public String getFullName() {
        return fullName;
    }

    public static Careers fromString(String code) {
        for (Careers career : Careers.values()) {
            if (career.code.equals(code)) {
                return career;
            }
        }
        throw new IllegalArgumentException("No enum constant with code: " + code);
    }

}
