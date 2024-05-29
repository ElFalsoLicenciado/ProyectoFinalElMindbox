package users;

import users.utils.Country;
import users.utils.Gender;
import users.utils.Role;

public class User {
    protected String firstName;
    protected String paternalLastName;
    protected String maternalLastName;
    protected String birthDate;
    protected Gender gender;
    protected String city;
    protected Country country;
    protected String curp;
    protected String rfc;
    protected String address;
    protected String registrationDate;
    protected String username;
    protected String password;
    protected String controlNumber;
    protected Role role;

    public User(String firstName, String paternalLastName, String maternalLastName, String birthDate, Gender gender, String city, Country country, String curp, String rfc, String address, String registrationDate, String username, String password, String controlNumber, Role role) {
        this.firstName = firstName.toUpperCase();
        this.paternalLastName = paternalLastName.toUpperCase();
        this.maternalLastName = maternalLastName.toUpperCase();
        this.birthDate = birthDate;
        this.gender = gender;
        this.city = city;
        this.country = country;
        this.curp = curp;
        this.rfc = rfc;
        this.address = address;
        this.registrationDate = registrationDate;
        this.username = username;
        this.password = password;
        this.controlNumber = controlNumber;
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPaternalLastName() {
        return paternalLastName;
    }

    public void setPaternalLastName(String paternalLastName) {
        this.paternalLastName = paternalLastName;
    }

    public String getMaternalLastName() {
        return maternalLastName;
    }

    public void setMaternalLastName(String maternalLastName) {
        this.maternalLastName = maternalLastName;
    }

    public String getFullName() {
        return String.format("%s %s %s", firstName, paternalLastName, maternalLastName);
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getControlNumber() {
        return controlNumber;
    }

    public void setControlNumber(String controlNumber) {
        this.controlNumber = controlNumber;
    }
}
