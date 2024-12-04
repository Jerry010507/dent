package com.kye.dent;

public class UserDTO {
    private String name;
    private String birthDate;   // PW
    private String phoneNumber; // ID


    // Constructor
    public UserDTO(String name, String birthDate, String phoneNumber) {
        this.name = name;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;

    }

    public UserDTO() {

    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "UserDTO [Name=" + name + ", PhoneNumber=" + phoneNumber + ", BirthDate=" + birthDate + "]";
    }
}
