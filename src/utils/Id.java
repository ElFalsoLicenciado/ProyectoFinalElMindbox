package utils;

import mindbox.utils.CareerType;
import users.utils.Role;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Id {
    private static final Map<CareerType, Integer> studentIndices = new HashMap<>();
    private static final Map<CareerType, Integer> teacherIndices = new HashMap<>();
    private static final Map<CareerType, Integer> coordinatorIndices = new HashMap<>();

    public static String generateStudentId(){

        String year = String.valueOf(LocalDate.now().getYear()).substring(2);
        int index = studentIndices.getOrDefault(career, 0);

    }

    public static String generateNumCon(String name, String year, CareerType careerType, Role role){
        String numCon = "";
        switch (role){
            case STUDENT ->  numCon = "I";
            case TEACHER -> numCon = "M";
            case COORDINATOR -> numCon = "C";
        }
        return numCon = String.format("%s%s%s%s",numCon,name.substring(0,1).toUpperCase(),year.substring(2,4),careerType.toString());
    }

}
