package com.openclassrooms.realestatemanager.UI.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.Model.Address;
import com.openclassrooms.realestatemanager.Repository.AddressRepository;

import java.util.concurrent.Executor;

public class AddressViewModel extends ViewModel {

    private AddressRepository mAddressRepository;
    private Executor mExecutor;

    public AddressViewModel(AddressRepository addressRepository, Executor executor) {
        this.mAddressRepository = addressRepository;
        this.mExecutor = executor;
    }

    public LiveData<Address> getAddressOfProperty(int propertyId){
        return this.mAddressRepository.getAddressOfProperty(propertyId);
    }

    public void createAddress(Address address){
        mExecutor.execute(() -> {
            this.mAddressRepository.createAddress(address);
        });
    }

    public void updateAddress(Address address){
        mExecutor.execute(() -> {
            this.mAddressRepository.updateAddress(address);
        });
    }
}
