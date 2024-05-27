package utils;

import mindbox.utils.Career;

public class CurrentCareer {

    private static CurrentCareer instance;
    private Career currentCareer;

    private CurrentCareer(){}

    public static CurrentCareer getInstance() {
        if (instance == null) {
            instance = new CurrentCareer();
        }
        return instance;
    }

    public Career getCurrentCareer() {
        return currentCareer;
    }

    public void setCareer(Career career) {
        this.currentCareer = career;
    }

    public boolean thereIsCurrentUser() {
        return currentCareer != null;
    }

    public void closeCareer() {
        instance = null;
        currentCareer = null;
    }


}
