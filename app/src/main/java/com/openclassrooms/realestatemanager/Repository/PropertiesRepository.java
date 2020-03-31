package com.openclassrooms.realestatemanager.Repository;

import androidx.lifecycle.MutableLiveData;

import com.openclassrooms.realestatemanager.Dummy.Dummy;
import com.openclassrooms.realestatemanager.Model.Property;

import java.util.List;

public class PropertiesRepository {

    private static PropertiesRepository sPropertiesRepository;

    public static PropertiesRepository getInstance(){
        if (sPropertiesRepository == null){
            sPropertiesRepository = new PropertiesRepository();
        }
        return sPropertiesRepository;
    }

    public MutableLiveData<List<Property>> getProperties(){
        MutableLiveData<List<Property>> listMutableLiveData = new MutableLiveData<>();
        listMutableLiveData.setValue(Dummy.sPropertyList);
        return listMutableLiveData;
    }

    public Property getPropertyById(int id){
        return Dummy.sPropertyList.get(id - 1);
    }
}
