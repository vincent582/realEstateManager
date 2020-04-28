package com.openclassrooms.realestatemanager.Database.DAO;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
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
    int updateProerty(Property property);

    @Transaction @Query("SELECT * FROM property")
    LiveData<List<FullProperty>> getFullProperties();

    @Query("SELECT * FROM property")
    Cursor getPropertiesWithCursor();
}
