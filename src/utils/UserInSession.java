package utils;

import users.User;

public class UserInSession {
    private static UserInSession instance;
    private static User user;

    private UserInSession() {}

    public static UserInSession getInstance() {
        if (instance == null) {
            instance = new UserInSession();
        }
        return instance;
    }

    public User getCurrentUser() {
        return user;
    }

    public void setUser(User user) {
        UserInSession.user = user;
    }

    public boolean hasUser() {
        return user != null;
    }

    public void logout() {
        user = null;
        instance = null;
    }
}
