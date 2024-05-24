package services;

import academicinfo.Career;
import utils.GsonUtil;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CareerService {
    private static CareerService instance;
    private Map<String, Career> careers;

    private CareerService() {
        careers = new HashMap<>();
        // Load careers from JSON or initialize if first execution
    }

    public static CareerService getInstance() {
        if (instance == null) {
            instance = new CareerService();
        }
        return instance;
    }

    public void addCareer(Career career) {
        careers.put(career.getId(), career);
    }

    public Career getCareer(String id) {
        return careers.get(id);
    }

    public void saveCareers(String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            GsonUtil.getGsonInstance().toJson(careers, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Methods for managing careers
}
