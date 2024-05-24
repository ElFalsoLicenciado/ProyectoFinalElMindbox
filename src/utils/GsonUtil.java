package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtil {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static Gson getGsonInstance() {
        return gson;
    }
}
