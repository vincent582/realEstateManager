package com.openclassrooms.realestatemanager.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Property.class,
        parentColumns = "id",
        childColumns = "propertyId"))
public class Address {

    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "propertyId")
    private int propertyId;
    private int number;
    private String street;
    private String complement_street;
    private String district;
    private String state;
    private int postCode;
    private String country;

    public Address(int propertyId, int number, String street, String complement_street, String district, String state, int postCode, String country) {
        this.propertyId = propertyId;
        this.number = number;
        this.street = street;
        this.complement_street = complement_street;
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

    public String getFormatedAddress(){
        String formatedAddress = number + ", " +street+", ";
        if (complement_street!= null){
            formatedAddress += complement_street+", ";
        }
        formatedAddress += district+", "+state+", "+postCode+", "+country;
        return formatedAddress;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }
}
