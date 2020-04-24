package com.openclassrooms.realestatemanager.Repository;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.Database.DAO.PropertyDAO;
import com.openclassrooms.realestatemanager.Model.FullProperty;
import com.openclassrooms.realestatemanager.Model.Property;

import java.util.List;

public class PropertiesRepository {

    private PropertyDAO mPropertyDAO;

    //CONSTRUCTOR
    public PropertiesRepository(PropertyDAO mPropertyDAO) {
        this.mPropertyDAO = mPropertyDAO;
    }

    /**
     * Get Property by is id
     * @param id
     * @return LiveData FullProperty
     */
    public LiveData<FullProperty> getPropertyById(int id){ return mPropertyDAO.getPropertyById(id); }

    /**
     * Add property to the database
     * @param property
     * @return
     */
    public long createProperty(Property property) {
        return mPropertyDAO.insertProperty(property);
    }

    /**
     * Update the property with new info in database
     * @param property
     */
    public void updateProperty(Property property) {
        mPropertyDAO.updateProerty(property);
    }

    /**
     * Get list of all of properties
     * @return
     */
    public LiveData<List<FullProperty>> getFullProperties(){ return mPropertyDAO.getFullProperties(); }
}
