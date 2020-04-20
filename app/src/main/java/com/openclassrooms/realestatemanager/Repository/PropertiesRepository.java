package com.openclassrooms.realestatemanager.Repository;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.Database.DAO.PropertyDAO;
import com.openclassrooms.realestatemanager.Model.FullProperty;
import com.openclassrooms.realestatemanager.Model.Property;

import java.util.List;

public class PropertiesRepository {

    private PropertyDAO mPropertyDAO;

    public PropertiesRepository(PropertyDAO mPropertyDAO) {
        this.mPropertyDAO = mPropertyDAO;
    }

    public LiveData<List<Property>> getProperties(){
        return mPropertyDAO.getAllProperty();
    }

    public LiveData<FullProperty> getPropertyById(int id){ return mPropertyDAO.getPropertyById(id); }

    public long createProperty(Property property) {
        return mPropertyDAO.insertProperty(property);
    }

    public void updateProperty(Property property) {
        mPropertyDAO.updateProerty(property);
    }

    public LiveData<List<FullProperty>> getFullProperties(){
        return mPropertyDAO.getFullProperties();
    }
}
