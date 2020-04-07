package com.openclassrooms.realestatemanager.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.openclassrooms.realestatemanager.Database.DAO.AddressDAO;
import com.openclassrooms.realestatemanager.Database.DAO.PictureDAO;
import com.openclassrooms.realestatemanager.Database.DAO.PropertyDAO;
import com.openclassrooms.realestatemanager.Database.DAO.UserDAO;
import com.openclassrooms.realestatemanager.Model.Address;
import com.openclassrooms.realestatemanager.Model.Picture;
import com.openclassrooms.realestatemanager.Model.Property;
import com.openclassrooms.realestatemanager.Model.User;

@Database(entities = {Property.class, Address.class, Picture.class, User.class},version = 1,exportSchema = false)
public abstract class RealEstateManagerDataBase extends RoomDatabase {

    public static volatile RealEstateManagerDataBase INSTANCE;

    public abstract PropertyDAO mPropertyDAO();
    public abstract AddressDAO mAddressDAO();
    public abstract PictureDAO mPictureDAO();
    public abstract UserDAO mUserDAO();

    public static RealEstateManagerDataBase getInstance(Context context){
        if (INSTANCE == null){
            synchronized (RealEstateManagerDataBase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RealEstateManagerDataBase.class,"RealEstateManagerDatabase.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
