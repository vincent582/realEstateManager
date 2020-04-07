package com.openclassrooms.realestatemanager.Model;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Property {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String type;
    private int price;
    private int surface;
    private int nbrOfRooms;
    private String description;
    private Boolean sold;
    @Embedded
    private Date addedDate;
    @Embedded
    private Date dateOfSale;
    @Embedded
    private User agentInCharge;

    public Property(String type, int price, int surface, int nbrOfRooms, String description, Boolean sold, Date addedDate, Date dateOfSale, User agentInCharge) {
        this.type = type;
        this.price = price;
        this.surface = surface;
        this.nbrOfRooms = nbrOfRooms;
        this.description = description;
        this.sold = sold;
        this.addedDate = addedDate;
        this.dateOfSale = dateOfSale;
        this.agentInCharge = agentInCharge;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSurface() {
        return surface;
    }

    public void setSurface(int surface) {
        this.surface = surface;
    }

    public int getNbrOfRooms() {
        return nbrOfRooms;
    }

    public void setNbrOfRooms(int nbrOfRooms) {
        this.nbrOfRooms = nbrOfRooms;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getSold() {
        return sold;
    }

    public void setSold(Boolean sold) {
        this.sold = sold;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public Date getDateOfSale() {
        return dateOfSale;
    }

    public void setDateOfSale(Date dateOfSale) {
        this.dateOfSale = dateOfSale;
    }

    public User getAgentInCharge() {
        return agentInCharge;
    }

    public void setAgentInCharge(User agentInCharge) {
        this.agentInCharge = agentInCharge;
    }
}
