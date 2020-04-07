package com.openclassrooms.realestatemanager.Database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.openclassrooms.realestatemanager.Model.Address;

@Dao
public interface AddressDAO {

    @Query("SELECT * FROM Address WHERE propertyId = :id")
    LiveData<Address> getAddressOfProperty(int id);

    @Insert
    long createAddress(Address address);

    @Update
    int updateAddress(Address address);
}
