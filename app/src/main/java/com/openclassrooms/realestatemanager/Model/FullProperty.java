package com.openclassrooms.realestatemanager.Model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class FullProperty {

    @Embedded
    public Property mProperty;

    @Relation(parentColumn = "id", entityColumn = "propertyId", entity = Address.class)
    public Address mAddress;

    @Relation(parentColumn = "id", entityColumn = "propertyId", entity = Picture.class)
    public List<Picture> mPictureList;

    public FullProperty(Property property, Address address, List<Picture> pictureList) {
        mProperty = property;
        mAddress = address;
        mPictureList = pictureList;
    }

    public Property getProperty() {
        return mProperty;
    }

    public void setProperty(Property property) {
        mProperty = property;
    }

    public Address getAddress() {
        return mAddress;
    }

    public void setAddress(Address address) {
        mAddress = address;
    }

    public List<Picture> getPictureList() {
        return mPictureList;
    }

    public void setPictureList(List<Picture> pictureList) {
        mPictureList = pictureList;
    }
}
