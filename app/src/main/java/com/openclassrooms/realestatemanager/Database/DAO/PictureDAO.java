package com.openclassrooms.realestatemanager.Database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.openclassrooms.realestatemanager.Model.FullProperty;
import com.openclassrooms.realestatemanager.Model.Picture;

import java.util.List;

@Dao
public interface PictureDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertPicture(Picture picture);

    @Query("SELECT * FROM Picture WHERE propertyId = :id")
    LiveData<List<Picture>> getPropertyById(Integer id);

    @Delete
    void deletePicture(Picture... pictures);
}
