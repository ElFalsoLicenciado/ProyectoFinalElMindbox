package gson.deserializers;

import com.google.gson.*;
import users.Coordinator;
import users.Student;
import users.Teacher;
import users.User;
import users.utils.Role;

import java.lang.reflect.Type;

public class UserDeserializer implements JsonDeserializer<User> {
    @Override
    public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        Role role = Role.valueOf(jsonObject.get("role").getAsString());

        switch (role) {
            case STUDENT:
                return context.deserialize(jsonObject, Student.class);
            case TEACHER:
                return context.deserialize(jsonObject, Teacher.class);
            case COORDINATOR:
                return context.deserialize(jsonObject, Coordinator.class);
            default:
                throw new IllegalArgumentException("Unknown role: " + role);
        }
    }
}
