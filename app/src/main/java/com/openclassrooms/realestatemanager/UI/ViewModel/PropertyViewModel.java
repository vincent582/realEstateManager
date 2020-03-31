package com.openclassrooms.realestatemanager.UI.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.Model.Property;
import com.openclassrooms.realestatemanager.Repository.PropertiesRepository;

import java.util.List;

public class PropertyViewModel extends ViewModel {

    private PropertiesRepository mPropertiesRepository;
    private MutableLiveData<List<Property>> mPropertyList;

    public void init(){
        if (mPropertyList != null) {
            return;
        }
        mPropertiesRepository = PropertiesRepository.getInstance();
        mPropertyList = mPropertiesRepository.getProperties();
    }

    public MutableLiveData<List<Property>> getProperties(){
        return mPropertyList;
    }

    public Property getPropertyById(int id){
        return mPropertiesRepository.getPropertyById(id);
    }

}
