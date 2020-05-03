package com.openclassrooms.realestatemanager.Database.DAO;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.openclassrooms.realestatemanager.Model.FullProperty;
import com.openclassrooms.realestatemanager.Model.Property;

import java.util.List;

@Dao
public interface PropertyDAO {

    @Query("SELECT * FROM Property")
    LiveData<List<Property>> getAllProperty();

    @Transaction @Query("SELECT * FROM Property WHERE id = :id")
    LiveData<FullProperty> getPropertyById(Integer id);

    @Insert
    long insertProperty(Property property);

    @Update
    int updateProperty(Property property);

    @Transaction @Query("SELECT * FROM property")
    LiveData<List<FullProperty>> getFullProperties();

    @Query("SELECT * FROM property")
    Cursor getPropertiesWithCursor();

    @Transaction @Query("SELECT * FROM property " +
        "INNER JOIN address ON address.propertyId = property.id " +
        //"INNER JOIN picture ON picture.propertyId = property.id" +
        "WHERE (property.surface BETWEEN :surfaceMin AND :surfaceMax)" +
        "AND (property.price BETWEEN :priceMin AND :priceMax)" +
        "AND property.facilities IN(:facilities) " +
        "AND (property.addedDate BETWEEN :addedDateMin AND :addedDateMax)" +
        "AND (property.dateOfSale BETWEEN :soldDateMin AND :soldDateMax)" +
        "")
    LiveData<List<FullProperty>> searchPropertiesWithParameters(int surfaceMin, int surfaceMax, int priceMin,
                                                                int priceMax , List<String> facilities, Long addedDateMin,
                                                                Long addedDateMax, Long soldDateMin, Long soldDateMax);
}
