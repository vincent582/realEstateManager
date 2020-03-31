package com.openclassrooms.realestatemanager.Repository;

import com.openclassrooms.realestatemanager.Dummy.Dummy;
import com.openclassrooms.realestatemanager.Model.Property;

import java.util.List;

public class PropertiesRepository {

    public List<Property> getProperties(){
        return Dummy.sPropertyList;
    }
}
