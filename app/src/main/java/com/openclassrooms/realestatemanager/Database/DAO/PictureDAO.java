package com.openclassrooms.realestatemanager.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.openclassrooms.realestatemanager.Model.Picture;

@Dao
public interface PictureDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertPicture(Picture picture);
}
