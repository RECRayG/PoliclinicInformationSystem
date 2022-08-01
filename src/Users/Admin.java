package Users;

import java.sql.Timestamp;
import java.util.Date;

public class Admin
{
    private String secondName;
    private String firstName;
    private String thirdName;
    private String gender;
    private java.sql.Date birthday;
    private String password;
    private String mailAddress;
    private String city;
    private String street;
    private String building;
    private String apartment;

    public Admin(String secondName, String firstName, String thirdName, String gender, java.sql.Date birthday,
                 String password, String mailAddress, String city, String street, String building, String apartment)
    {
        this.secondName = secondName;
        this.firstName = firstName;
        this.thirdName = thirdName;
        this.gender = gender;
        this.birthday = birthday;
        this.password = password;
        this.mailAddress = mailAddress;
        this.city = city;
        this.street = street;
        this.building = building;
        this.apartment = apartment;
    }

    public Admin() {} // Конструктор заглушка

    public String getSecondName() {
        return secondName;
    }
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getThirdName() {
        return thirdName;
    }
    public void setThirdName(String thirdName) {
        this.thirdName = thirdName;
    }

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public java.sql.Date getBirthday() {
        return birthday;
    }
    public void setBirthday(java.sql.Date birthday) {
        this.birthday = birthday;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getMailAddress() {
        return mailAddress;
    }
    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuilding() {
        return building;
    }
    public void setBuilding(String building) {
        this.building = building;
    }

    public String getApartment() {
        return apartment;
    }
    public void setApartment(String apartment) {
        this.apartment = apartment;
    }
}
