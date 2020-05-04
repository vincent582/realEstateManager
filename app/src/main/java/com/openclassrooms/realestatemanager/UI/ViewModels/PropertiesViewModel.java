package com.openclassrooms.realestatemanager.UI.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.Model.Property;
import com.openclassrooms.realestatemanager.Repository.PropertiesRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class PropertiesViewModel extends ViewModel {

    private PropertiesRepository mPropertiesRepository;
    private Executor mExecutor;

    public PropertiesViewModel(PropertiesRepository propertiesRepository, Executor executor) {
        mPropertiesRepository = propertiesRepository;
        mExecutor = executor;
    }

    public LiveData<List<Property>> getAllProperties(){
        return mPropertiesRepository.getAllProperties();
    }

    public LiveData<Property> getPropertyById(int id) { return mPropertiesRepository.getPropertyById(id); }

    public void createProperty(Property property){
        mExecutor.execute(()-> { mPropertiesRepository.createProperty(property); });
    }

    public void updateProperty(Property property) {
        mExecutor.execute(()-> mPropertiesRepository.updateProperty(property));
    }

    public LiveData<List<Property>> searchProperties(int surfaceMin, int surfaceMax, int priceMin,
                                                         int priceMax , List<String> facilities, Long addedDateMin,
                                                         Long addedDateMax, Long soldDateMin, Long soldDateMax){
        return mPropertiesRepository.searchProperties(surfaceMin,surfaceMax,priceMin,priceMax,facilities,addedDateMin,addedDateMax,soldDateMin,soldDateMax);
    }
}
