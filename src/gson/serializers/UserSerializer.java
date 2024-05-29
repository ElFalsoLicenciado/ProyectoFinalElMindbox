package gson.serializers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import mindbox.Sys;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

public class UserSerializer {

    public static void serialize() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.json"))) {
            Map<String, Object> data = new HashMap<>();
            data.put("careers", Sys.getInstance().getCareers());
            gson.toJson(data, writer);
        } catch (IOException e) {
            System.out.println("Error while saving data: " + e.getMessage());
        }
    }
}
