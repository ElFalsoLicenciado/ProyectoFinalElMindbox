package utils;

import academicinfo.CareerType;

public class CurrentCareer {

    private static CurrentCareer instance;
    private CareerType currentCareer;

    private CurrentCareer(){}

    public static CurrentCareer getInstance() {
        if (instance == null) {
            instance = new CurrentCareer();
        }
        return instance;
    }

    public CareerType getCurrentCareer() {
        return currentCareer;
    }

    public void setCareer(CareerType career) {
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
