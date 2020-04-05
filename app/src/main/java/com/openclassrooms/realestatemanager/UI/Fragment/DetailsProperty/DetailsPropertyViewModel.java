package com.openclassrooms.realestatemanager.UI.Fragment.DetailsProperty;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.Model.Property;
import com.openclassrooms.realestatemanager.Repository.PropertiesRepository;

public class DetailsPropertyViewModel extends ViewModel {

    private PropertiesRepository mPropertiesRepository;

    public void init(){
        mPropertiesRepository = PropertiesRepository.getInstance();
    }

    public MutableLiveData<Property> getProperty(int id) {
            return mPropertiesRepository.getPropertyById(id);
    }

    public void updateProperty(Property property) {
        mPropertiesRepository.updateProperty(property);
    }
}
