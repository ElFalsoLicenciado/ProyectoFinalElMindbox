package utils;

import academicinfo.CareerType;
import users.utils.Role;

import java.util.HashMap;
import java.util.Map;

public class Id {
    private static final Map<CareerType, Integer> studentIndices = new HashMap<>();
    private static final Map<CareerType, Integer> teacherIndices = new HashMap<>();
    private static final Map<CareerType, Integer> coordinatorIndices = new HashMap<>();

    public static String generateControlNumber(String name, String birthDate, CareerType careerType, Role role) {
        String year = birthDate.substring(2, 4);
        int index = 0;
        String prefix = "";

        switch (role) {
            case STUDENT -> {
                index = studentIndices.getOrDefault(careerType, 0) + 1;
                studentIndices.put(careerType, index);
                prefix = "l";
            }
            case TEACHER -> {
                index = teacherIndices.getOrDefault(careerType, 0) + 1;
                teacherIndices.put(careerType, index);
                prefix = "M";
            }
            case COORDINATOR -> {
                index = coordinatorIndices.getOrDefault(careerType, 0) + 1;
                coordinatorIndices.put(careerType, index);
                prefix = "C";
            }
        }

        return String.format("%s%s%s%s%d", prefix, name.substring(0, 1).toUpperCase(), year, careerType, index);
    }
}
