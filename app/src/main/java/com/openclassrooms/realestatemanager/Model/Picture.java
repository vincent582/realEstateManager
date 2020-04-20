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
    private String file;
    private String description;

    public Picture(String file, String description) {
        this.file = file;
        this.description = description;
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

    public String getFile() { return file; }

    public void setFile(String file) { this.file = file; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
