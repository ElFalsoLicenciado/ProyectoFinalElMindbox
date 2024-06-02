package gson.deserializers;

import Models.UserModel;
import com.google.gson.*;
import users.Coordinator;
import users.Student;
import users.Teacher;
import users.User;
import users.utils.Role;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import mindbox.Mindbox;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class UserDeserializer{

    public static void deserialize() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (BufferedReader reader = new BufferedReader(new FileReader("users.json"))) {
            // Define the correct type for UserModel
            Type userModelType = new TypeToken<UserModel>() {}.getType();
            UserModel userModel = gson.fromJson(reader, userModelType);

            // Clear existing lists in Mindbox.users
            Mindbox.users.put(Role.STUDENT, new ArrayList<>());
            Mindbox.users.put(Role.TEACHER, new ArrayList<>());
            Mindbox.users.put(Role.COORDINATOR, new ArrayList<>());

            // Add deserialized users to Mindbox.users
            if (userModel.getStudent() != null) {
                Mindbox.users.get(Role.STUDENT).addAll(userModel.getStudent());
            }
            if (userModel.getTeacher() != null) {
                Mindbox.users.get(Role.TEACHER).addAll(userModel.getTeacher());
            }
            if (userModel.getCoordinator() != null) {
                Mindbox.users.get(Role.COORDINATOR).addAll(userModel.getCoordinator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
