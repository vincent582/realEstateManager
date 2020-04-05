package com.openclassrooms.realestatemanager.UI.Fragment.ListProperties;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.Model.Property;
import com.openclassrooms.realestatemanager.Repository.PropertiesRepository;

import java.util.List;

public class PropertiesViewModel extends ViewModel {

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

    public void addPropertyToList(Property property){
        mPropertiesRepository.addProperty(property);
    }

}
