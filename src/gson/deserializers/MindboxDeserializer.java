package gson.deserializers;

import Models.MindboxModel;
import com.google.gson.Gson;
import mindbox.Mindbox;

import java.io.BufferedReader;
import java.io.FileReader;


public class MindboxDeserializer {
    public static void deserialize() {
        Gson gson = new Gson();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("mindbox.json"));
            MindboxModel mindboxModel = gson.fromJson(reader, MindboxModel.class);
            Mindbox.semesters = mindboxModel.getSemesters();
            Mindbox.graduates = mindboxModel.getGraduates();
            System.out.println("Static data loaded from mindbox.json");
        } catch (Exception e) {
            Mindbox.mindboxFirstRun();
            Mindbox.usersFirstRun();
        }
    }
}
