package com.openclassrooms.realestatemanager.Model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Property.class,
        parentColumns = "id",
        childColumns = "propertyId"))
public class Picture {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int propertyId;
    private String fileName;

    public Picture(int propertyId, String fileName) {
        this.propertyId = propertyId;
        this.fileName = fileName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }
}
