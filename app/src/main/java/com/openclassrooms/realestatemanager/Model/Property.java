package com.openclassrooms.realestatemanager.Model;

import java.util.Date;
import java.util.List;

public class Property {

    private int id;
    private String type;
    private int price;
    private int surface;
    private int nbrOfRooms;
    private String description;
    private List<Photo> mPhotoList;
    private Address mAddress;
    private List<String> listFacilities;
    private String status;
    private Date addedDate;
    private Date dateOfSale;
    private User agentInCharge;

    public Property(int id, String type, int price, int surface, int nbrOfRooms, String description,Address address, List<String> listFacilities, String status, Date addedDate, Date dateOfSale, User agentInCharge) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.surface = surface;
        this.nbrOfRooms = nbrOfRooms;
        this.description = description;
        this.mAddress = address;
        this.listFacilities = listFacilities;
        this.status = status;
        this.addedDate = addedDate;
        this.dateOfSale = dateOfSale;
        this.agentInCharge = agentInCharge;
    }

    public int getId() { return id; }

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

    public List<Photo> getPhotoList() {
        return mPhotoList;
    }

    public void setPhotoList(List<Photo> photoList) {
        mPhotoList = photoList;
    }

    public Address getAddress() {
        return mAddress;
    }

    public void setAddress(Address address) {
        mAddress = address;
    }

    public List<String> getListFacilities() {
        return listFacilities;
    }

    public void setListFacilities(List<String> listFacilities) {
        this.listFacilities = listFacilities;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
