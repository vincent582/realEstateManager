package com.openclassrooms.realestatemanager.Repository;

import androidx.lifecycle.MutableLiveData;

import com.openclassrooms.realestatemanager.Dummy.Dummy;
import com.openclassrooms.realestatemanager.Model.Property;

import java.util.List;

public class PropertiesRepository {

    private static PropertiesRepository sPropertiesRepository;

    private List<Property> mPropertyList = Dummy.generateListProperties();

    public static PropertiesRepository getInstance(){
        if (sPropertiesRepository == null){
            sPropertiesRepository = new PropertiesRepository();
        }
        return sPropertiesRepository;
    }

    public MutableLiveData<List<Property>> getProperties(){
        MutableLiveData<List<Property>> listMutableLiveData = new MutableLiveData<>();
        listMutableLiveData.setValue(mPropertyList);
        return listMutableLiveData;
    }

    public Property getPropertyById(int id){
        Property propertyToReturn = null;
        for (Property property :mPropertyList) {
            if (property.getId() == id){
                propertyToReturn = property;
            }
        }
        return propertyToReturn;
    }

    public void addProperty(Property property) {
        mPropertyList.add(property);
    }
}
