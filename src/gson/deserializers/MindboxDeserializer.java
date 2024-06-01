package gson.deserializers;

import Models.MindboxModel;
import academicinfo.*;
import mindbox.*;
import users.*;
import users.utils.*;
import utils.*;

import java.util.*;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class MindboxDeserializer {
    public static void deserialize() {
        Gson gson = new Gson();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("mindbox.json"));
            MindboxModel mindboxModel = gson.fromJson(reader, MindboxModel.class);
            Mindbox.semesters = mindboxModel.getSemesters();
            Mindbox.graduates = mindboxModel.getGraduates();
            System.out.println("Static data loaded from mindbox.json");
        } catch (FileNotFoundException e) {
            Mindbox.mindboxFirstRun();
            Mindbox.usersFirstRun();
        } catch (IOException e) {
            System.out.println(e);
            Mindbox.mindboxFirstRun();
            Mindbox.usersFirstRun();
        } catch (JsonSyntaxException e) {
            Mindbox.mindboxFirstRun();
            Mindbox.usersFirstRun();
        } catch (JsonParseException e) {
            Mindbox.mindboxFirstRun();
            Mindbox.usersFirstRun();
        } catch (Exception e) {
            Mindbox.mindboxFirstRun();
            Mindbox.usersFirstRun();
        }
    }
}
