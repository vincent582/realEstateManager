package com.openclassrooms.realestatemanager.Model;

public class Address {

    private int number;
    private String street;
    private String complement_street;
    private String district;
    private String state;
    private int postCode;
    private String country;

    public Address(int number, String street, String district, String state, int postCode, String country) {
        this.number = number;
        this.street = street;
        this.district = district;
        this.state = state;
        this.postCode = postCode;
        this.country = country;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getComplement_street() {
        return complement_street;
    }

    public void setComplement_street(String complement_street) {
        this.complement_street = complement_street;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getPostCode() {
        return postCode;
    }

    public void setPostCode(int postCode) {
        this.postCode = postCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
