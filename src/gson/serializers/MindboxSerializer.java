package gson.serializers;

import Models.MindboxModel;
import academicinfo.*;
import mindbox.*;
import users.*;
import users.utils.*;
import utils.*;

import java.io.*;
import java.util.*;
import com.google.gson.GsonBuilder;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;


public class MindboxSerializer {
    public static void serialize() {
        MindboxModel mindboxModel = new MindboxModel();
        mindboxModel.setSemesters(Mindbox.semesters);
        mindboxModel.setGraduates(Mindbox.graduates);
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("mindbox.json"));
            gson.toJson(mindboxModel, writer);
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
