package com.openclassrooms.realestatemanager.Repository;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.Database.DAO.AddressDAO;
import com.openclassrooms.realestatemanager.Model.Address;

public class AddressRepository {

    private AddressDAO mAddressDAO;

    public AddressRepository(AddressDAO mAddressDAO) {
        this.mAddressDAO = mAddressDAO;
    }

    public LiveData<Address> getAddressOfProperty(int propertyId){
        return this.mAddressDAO.getAddressOfProperty(propertyId);
    }

    public void createAddress(Address address){
        this.mAddressDAO.createAddress(address);
    }

    public void updateAddress(Address address){
        this.mAddressDAO.updateAddress(address);
    }
}
