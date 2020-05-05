package com.openclassrooms.realestatemanager.Database.DAO;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.openclassrooms.realestatemanager.Model.Property;

import java.util.List;

@Dao
public interface PropertyDAO {

    @Query("SELECT * FROM Property")
    LiveData<List<Property>> getAllProperty();

    @Query("SELECT * FROM Property WHERE id = :id")
    LiveData<Property> getPropertyById(Integer id);

    @Insert
    void insertProperty(Property property);

    @Update
    void updateProperty(Property property);

    //For ContentProvider
    @Query("SELECT * FROM property")
    Cursor getPropertiesWithCursor();


    @Query("SELECT * FROM property " +
        //Manage surface option
        "WHERE ((property.surface >= :surfaceMin AND property.surface <= :surfaceMax)" +
        "OR (property.surface <= :surfaceMax AND :surfaceMin is null) " +
        "OR (property.surface >= :surfaceMin AND :surfaceMax is null) " +
        "OR (:surfaceMin is null AND :surfaceMax is null))"+
        //Manage price option
        "AND ((property.price >= :priceMin AND property.price <= :priceMax) " +
        "OR (property.price <= :priceMax AND :priceMin is null) " +
        "OR (property.price >= :priceMin AND :priceMax is null) " +
        "OR (:priceMin is null AND :priceMax is null))"+
        //Manage number of room option
        "AND ((property.nbrOfRooms >= :nbrRoomMin AND property.nbrOfRooms <= :nbrRoomMax) " +
        "OR (property.nbrOfRooms <= :nbrRoomMax AND :nbrRoomMin is null) " +
        "OR (property.nbrOfRooms >= :nbrRoomMin AND :nbrRoomMax is null) " +
        "OR (:nbrRoomMin is null AND :nbrRoomMax is null))"+
        //Manage facilities option
        "AND (property.facilities IN(:listFacilities))"

        /*
        "AND (property.addedDate BETWEEN :addedDateMin AND :addedDateMax)" +
        "AND (property.dateOfSale BETWEEN :soldDateMin AND :soldDateMax)" +
        ""
         */
    )
    LiveData<List<Property>> searchPropertiesWithParameters(Integer surfaceMin, Integer surfaceMax, Integer priceMin,
                                                            Integer priceMax, Integer nbrRoomMin, Integer nbrRoomMax, List<String> listFacilities);
        //, List<String> facilities, Long addedDateMin,Long addedDateMax, Long soldDateMin, Long soldDateMax);
}
