package gson.deserializers;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import mindbox.Sys;
import mindbox.Minbox;
import academicinfo.CareerType;
import users.User;
import users.utils.Role;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserDeserializer {

    public static void deserialize() {
        Gson gson = new Gson();
        try (BufferedReader reader = new BufferedReader(new FileReader("users.json"))) {
            Type userTypeMap = new TypeToken<HashMap<CareerType, Map<Role, ArrayList<User>>>>() {}.getType();
            Map<CareerType, Map<Role, ArrayList<User>>> data = gson.fromJson(reader, userTypeMap);

            Sys.getInstance().getCareers().clear();
            data.forEach((careerType, usersMap) -> {
                Sys.getInstance().getCareers().put(careerType, new Minbox(careerType));
            });

        } catch (IOException | JsonParseException e) {
            System.out.println("Error while loading users: " + e.getMessage());
        }
    }
}
