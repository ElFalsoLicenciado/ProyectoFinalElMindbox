package gson.serializers;

import java.time.LocalDate;
import java.util.*;

import Models.UserModel;
import academicinfo.*;
import mindbox.*;
import users.*;
import users.utils.*;
import utils.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class UserSerializer {
    public static void serialize() {
        if (Mindbox.users == null || !Mindbox.users.containsKey(Role.STUDENT) ||
                !Mindbox.users.containsKey(Role.COORDINATOR) ||
                !Mindbox.users.containsKey(Role.TEACHER)) {
            System.out.println("Error: Mindbox.users is not properly initialized");
            return;
        }

        // Create the user model
        UserModel userModel = new UserModel();

        // Filter and assign Students
        ArrayList<Student> students = new ArrayList<>();
        for (User user : Mindbox.users.get(Role.STUDENT)) {
            if (user instanceof Student) {
                students.add((Student) user);
            }
        }
        userModel.setStudent(students);

        // Filter and assign Coordinators
        ArrayList<Coordinator> coordinators = new ArrayList<>();
        for (User user : Mindbox.users.get(Role.COORDINATOR)) {
            if (user instanceof Coordinator) {
                coordinators.add((Coordinator) user);
            }
        }
        userModel.setCoordinator(coordinators);

        // Filter and assign Teachers
        ArrayList<Teacher> teachers = new ArrayList<>();
        for (User user : Mindbox.users.get(Role.TEACHER)) {
            if (user instanceof Teacher && !user.getRole().equals(Role.COORDINATOR)) {
                teachers.add((Teacher) user);
            }
        }
        userModel.setTeacher(teachers);

        // Create an instance of Gson
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

        // Serialize the model to a JSON file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.json"))) {
            gson.toJson(userModel, writer);
            System.out.println("Users serialized successfully.");
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}
