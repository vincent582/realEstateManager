package com.openclassrooms.realestatemanager.UI.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.Model.FullProperty;
import com.openclassrooms.realestatemanager.Model.Property;
import com.openclassrooms.realestatemanager.Repository.PropertiesRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class PropertiesViewModel extends ViewModel {

    private PropertiesRepository mPropertiesRepository;
    private Executor mExecutor;

    private LiveData<List<FullProperty>> mPropertyList;
    private MutableLiveData<Integer> idCreatedProperty = new MutableLiveData<>();

    public PropertiesViewModel(PropertiesRepository propertiesRepository, Executor executor) {
        mPropertiesRepository = propertiesRepository;
        mExecutor = executor;
    }

    public void init(){
        if (mPropertyList == null){
            mPropertyList = mPropertiesRepository.getFullProperties();
        }
        return;
    }

    public LiveData<List<FullProperty>> getFullProperties(){
        return mPropertyList;
    }

    public LiveData<FullProperty> getPropertyById(int id) { return mPropertiesRepository.getPropertyById(id); }

    public MutableLiveData<Integer> createProperty(Property property){
        mExecutor.execute(()-> {
            long idReturn = mPropertiesRepository.createProperty(property);
            this.idCreatedProperty.postValue((int) idReturn);
        });
        return idCreatedProperty;
    }

    public void updateProperty(Property property) {
        mExecutor.execute(()-> mPropertiesRepository.updateProperty(property));
    }

    public LiveData<List<FullProperty>> searchProperties(int surfaceMin, int surfaceMax, int priceMin,
                                                         int priceMax , List<String> facilities, Long addedDateMin,
                                                         Long addedDateMax, Long soldDateMin, Long soldDateMax){
        return mPropertiesRepository.searchProperties(surfaceMin,surfaceMax,priceMin,priceMax,facilities,addedDateMin,addedDateMax,soldDateMin,soldDateMax);
    }
}
