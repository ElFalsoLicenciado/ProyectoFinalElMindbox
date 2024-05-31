package users;

import academicinfo.*;
import mindbox.*;
import users.*;
import users.utils.*;
import utils.*;
import java.util.*;

import java.time.LocalDate;
import java.util.Scanner;

public class User {

    protected String firstName;
    protected String lastName;
    protected String birthDate;
    protected String city;
    protected String state;
    protected String curp;
    protected String address;
    protected String registrationDate;
    protected Careers career;
    protected String controlNumber;
    protected String password;
    protected Role role;

    public User(String firstName, String lastName, String birthDate, String city, String state, String curp, String address, Careers career, String controlNumber, String password, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.city = city;
        this.state = state;
        this.curp = curp;
        this.address = address;
        this.registrationDate = LocalDate.now().toString();
        this.career = career;
        this.controlNumber = controlNumber;
        this.password = password;
        this.role = role;
    }

    protected void showUser() {
        System.out.println("First Name: " + firstName);
        System.out.println("Last Name: " + lastName);
        System.out.println("Birth Date: " + birthDate);
        System.out.println("City: " + city);
        System.out.println("State: " + state);
        System.out.println("CURP: " + curp);
        System.out.println("Address: " + address);
        System.out.println("Registration Date: " + registrationDate);
        System.out.println("Role: " + role);
    }

    public String toString() {
        return String.format(
                "First Name: %s, Last Name: %s, Control Number: %s, Birth Date: %s, City: %s, State: %s, CURP: %s, Address: %s, Registration Date: %s, Career: %s ",
                getFirstName(), getLastName(), getControlNumber(), getBirthDate(), getCity(), getState(), getCurp(), getAddress(), getRegistrationDate(), getCareer()
        );
    }

    public static void updateInformation(User user) {
        Scanner read = new Scanner(System.in);
        String option = "";
        Role role = user.getRole();
        do {
            System.out.println("Which characteristics do you want to change for the selected user?");
            System.out.println("1. City of residence.");
            System.out.println("2. State of residence.");
            System.out.println("3. Address.");
            System.out.println("4. Password.");
            System.out.println("E. EXIT THIS MENU.");
            option = read.nextLine();

            if (option.equals("1")) {
                do {
                    System.out.println("Enter the new city of residence:");
                    String city = read.nextLine();
                    if (CommonData.emptyOrNum(city)) {
                        System.out.println("City with numbers/empty is not valid, please enter another.");
                    } else {
                        user.setCity(city);
                        break;
                    }
                } while (true);
                System.out.println("City changed successfully.");
            } else if (option.equals("2")) {
                do {
                    System.out.println("Enter the new state of residence:");
                    String state = read.nextLine();
                    if (CommonData.emptyOrNum(state)) {
                        System.out.println("State with numbers/empty is not valid, please enter another.");
                    } else {
                        user.setState(state);
                        break;
                    }
                } while (true);
                System.out.println("State changed successfully.");
            } else if (option.equals("3")) {
                System.out.println("Enter the new address:");
                String address = read.nextLine();
                user.setAddress(address);
                System.out.println("Address changed successfully.");
            } else if (option.equals("4")) {
                System.out.println("Enter the new password:");
                String password = read.nextLine();
                user.setPassword(password);
                System.out.println("Password changed successfully.");
            } else if (option.equals("E")) {
                System.out.println("EXITING THE MENU.");
                break;
            } else {
                System.out.println("INVALID OPTION.");
            }

        } while (true);
    }

    // Setters
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setCareer(Careers career) {
        this.career = career;
    }

    public void setControlNumber(String controlNumber) {
        this.controlNumber = controlNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    // Getters
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCurp() {
        return curp;
    }

    public String getAddress() {
        return address;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public Careers getCareer() {
        return career;
    }

    public String getControlNumber() {
        return controlNumber;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }
}
