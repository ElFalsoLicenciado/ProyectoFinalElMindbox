package utils;

import users.User;

public class UserInSession {
    private static UserInSession instance;
    private User currentUser;

    private UserInSession() {}

    public static UserInSession getInstance() {
        if (instance == null) {
            instance = new UserInSession();
        }
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
}
