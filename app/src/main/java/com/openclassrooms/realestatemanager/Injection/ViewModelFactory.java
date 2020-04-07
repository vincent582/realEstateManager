package com.openclassrooms.realestatemanager.Injection;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.Repository.AddressRepository;
import com.openclassrooms.realestatemanager.Repository.PropertiesRepository;
import com.openclassrooms.realestatemanager.UI.ViewModels.AddressViewModel;
import com.openclassrooms.realestatemanager.UI.ViewModels.PropertiesViewModel;

import java.util.concurrent.Executor;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final PropertiesRepository mPropertiesRepository;
    private final AddressRepository mAddressRepository;
    private final Executor mExecutor;

    public ViewModelFactory(PropertiesRepository propertiesRepository, AddressRepository addressRepository, Executor executor) {
        this.mPropertiesRepository = propertiesRepository;
        this.mAddressRepository = addressRepository;
        this.mExecutor = executor;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PropertiesViewModel.class)) {
            return (T) new PropertiesViewModel(mPropertiesRepository,mExecutor);
        }
        if (modelClass.isAssignableFrom(AddressViewModel.class)){
            return (T) new AddressViewModel(mAddressRepository,mExecutor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
