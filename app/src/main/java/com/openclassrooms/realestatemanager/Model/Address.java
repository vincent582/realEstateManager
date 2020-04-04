package com.openclassrooms.realestatemanager.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Address implements Parcelable {

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

    protected Address(Parcel in) {
        number = in.readInt();
        street = in.readString();
        complement_street = in.readString();
        district = in.readString();
        state = in.readString();
        postCode = in.readInt();
        country = in.readString();
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };

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
        return number+" "+street+" "+complement_street+" "+district+" "+state+" "+postCode+" "+country;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(number);
        dest.writeString(street);
        dest.writeString(complement_street);
        dest.writeString(district);
        dest.writeString(state);
        dest.writeInt(postCode);
        dest.writeString(country);
    }
}
