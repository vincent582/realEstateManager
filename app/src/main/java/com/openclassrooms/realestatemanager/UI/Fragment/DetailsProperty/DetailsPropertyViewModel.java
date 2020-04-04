package com.openclassrooms.realestatemanager.UI.Fragment.DetailsProperty;

import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.Model.Property;
import com.openclassrooms.realestatemanager.Repository.PropertiesRepository;

public class DetailsPropertyViewModel extends ViewModel {

    private PropertiesRepository mPropertiesRepository;

    public void init(){
        mPropertiesRepository = PropertiesRepository.getInstance();
    }

    public Property getProperty(int id) {
            return mPropertiesRepository.getPropertyById(id);
    }
}
