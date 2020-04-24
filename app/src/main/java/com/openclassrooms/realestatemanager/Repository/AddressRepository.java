package com.openclassrooms.realestatemanager.Repository;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.Database.DAO.AddressDAO;
import com.openclassrooms.realestatemanager.Model.Address;

public class AddressRepository {

    private AddressDAO mAddressDAO;

    //CONSTRUCTOR
    public AddressRepository(AddressDAO mAddressDAO) {
        this.mAddressDAO = mAddressDAO;
    }

    /**
     * Get an address by propertyId
     * @param propertyId
     * @return the good Address
     */
    public LiveData<Address> getAddressOfProperty(int propertyId){
        return this.mAddressDAO.getAddressOfProperty(propertyId);
    }

    /**
     * Add an Address in database
     * @param address
     */
    public void createAddress(Address address){
        this.mAddressDAO.createAddress(address);
    }

    /**
     * Update the Address in DataBase
     * @param address
     */
    public void updateAddress(Address address){
        this.mAddressDAO.updateAddress(address);
    }
}
