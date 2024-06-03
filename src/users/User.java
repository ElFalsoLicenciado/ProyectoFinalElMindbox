package users;

import academicinfo.*;
import users.utils.*;
import utils.*;

import java.time.LocalDate;

public class User {

    protected String firstName;
    protected String lastName1;
    protected String lastName2;
    protected String birthDate;
    protected String city;
    protected String state;
    protected String curp;
    protected String address;
    protected String registrationDate;
    protected Careers career;
    protected String controlNumber;
    protected String username;
    protected String password;
    protected Role role;

    public User(String firstName, String lastName1,String lastName2, String birthDate, String city, String state, String curp, String address, Careers career, String controlNumber,String username, String password, Role role) {
        this.firstName = firstName;
        this.lastName1 = lastName1;
        this.lastName2 = lastName2;
        this.birthDate = birthDate;
        this.city = city;
        this.state = state;
        this.curp = curp;
        this.address = address;
        this.registrationDate = LocalDate.now().toString();
        this.career = career;
        this.controlNumber = controlNumber;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    protected void showUser() {
        DialogHelper.info("Full name: " + firstName+" " + lastName1+" " + lastName2+"\nBirth Date: " + birthDate+"\nCity: " + city+ " State: " + state+"\nCURP: " + curp+"\nAddress: " + address+"\nRegistration Date: " + registrationDate+"\nRole: " + role);
    }

    public String toString() {
        return String.format(
                "First Name: %s, Last Name: %s, Control Number: %s, Birth Date: %s, City: %s, State: %s, CURP: %s, Address: %s, Registration Date: %s, Career: %s ",
                getFirstName(), getLastName(), getControlNumber(), getBirthDate(), getCity(), getState(), getCurp(), getAddress(), getRegistrationDate(), getCareer()
        );
    }

    public static void updateInformation(User user) {
        boolean flag = false;
        do {
            switch (DialogHelper.optionD("Which characteristics do you want to change for the selected user?", new String[]{"City", "State", "Address", "Username", "Password", "Exit"})) {
                case 0 -> {
                    do {
                        String city = DialogHelper.stringIn("Enter the new city of residence:");
                        if (CommonData.emptyOrNum(city))
                            DialogHelper.warning("City with numbers/empty is not valid, please enter another.");
                        else {
                            user.setCity(city);
                            break;
                        }
                    } while (true);
                }
                case 1 -> {
                    do {
                        String state = DialogHelper.stringIn("Enter the new state of residence:");
                        if (CommonData.emptyOrNum(state)) {
                            DialogHelper.error("State with numbers/empty is not valid, please enter another.");
                        } else {
                            user.setState(state);
                            break;
                        }
                    } while (true);
                } case 2 -> {
                    String address = DialogHelper.stringIn("Enter the new address: ");
                    user.setAddress(address);
                } case 3 -> {
                    String username = CommonData.verifyUsername();
                    user.setUsername(username);
                } case 4 ->{
                    String password = DialogHelper.stringIn("Enter the new username: ");
                    user.setPassword(password);
                }
                case 5 -> {
                    DialogHelper.warning("EXITING THE MENU.");
                    flag = true;
                }
            }
        }while (!flag);
        }

        // Setters
        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public void setLastName1(String lastName1) {
            this.lastName1 = lastName1;
        }

        public void setLastName2(String lastName2) {
            this.lastName2 = lastName2;
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

        public void setUsername(String username) {
            this.username = username;
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
            return String.format("%s %s",lastName1,lastName2);
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

        public String getUsername(){
            return username;
        }

        public String getPassword() {
            return password;
        }

        public Role getRole() {
            return role;
        }
    }
