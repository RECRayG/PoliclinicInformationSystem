package Users;

import java.sql.Date;

public class Patient
{
    private String secondName;
    private String firstName;
    private String thirdName;
    private String gender;
    private java.sql.Date birthday;
    private java.sql.Date dayOfCreateCard;
    private String numberOfPassport;
    private String numberOfPhone;
    private String numberOfInsurancePolicy;
    private String city;
    private String street;
    private String building;
    private String apartment;

    public Patient(){}

    public Patient(String secondName, String firstName, String thirdName,
                   String gender, Date birthday, Date dayOfCreateCard,
                   String numberOfPassport, String numberOfPhone, String numberOfInsurancePolicy,
                   String city, String street, String building, String apartment)
    {
        this.secondName = secondName;
        this.firstName = firstName;
        this.thirdName = thirdName;
        this.gender = gender;
        this.birthday = birthday;
        this.dayOfCreateCard = dayOfCreateCard;
        this.numberOfPassport = numberOfPassport;
        this.numberOfPhone = numberOfPhone;
        this.numberOfInsurancePolicy = numberOfInsurancePolicy;
        this.city = city;
        this.street = street;
        this.building = building;
        this.apartment = apartment;
    }

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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getDayOfCreateCard() {
        return dayOfCreateCard;
    }

    public void setDayOfCreateCard(Date dayOfCreateCard) {
        this.dayOfCreateCard = dayOfCreateCard;
    }

    public String getNumberOfPassport() {
        return numberOfPassport;
    }

    public void setNumberOfPassport(String numberOfPassport) {
        this.numberOfPassport = numberOfPassport;
    }

    public String getNumberOfPhone() {
        return numberOfPhone;
    }

    public void setNumberOfPhone(String numberOfPhone) {
        this.numberOfPhone = numberOfPhone;
    }

    public String getNumberOfInsurancePolicy() {
        return numberOfInsurancePolicy;
    }

    public void setNumberOfInsurancePolicy(String numberOfInsurancePolicy) {
        this.numberOfInsurancePolicy = numberOfInsurancePolicy;
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
