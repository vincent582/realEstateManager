package com.openclassrooms.realestatemanager.UI.ViewModel;

import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.Model.Property;
import com.openclassrooms.realestatemanager.Repository.PropertiesRepository;

import java.util.List;

public class PropertyViewModel extends ViewModel {

    private PropertiesRepository mPropertiesRepository = new PropertiesRepository();

    public List<Property> getProperties(){
        return mPropertiesRepository.getProperties();
    }

}
