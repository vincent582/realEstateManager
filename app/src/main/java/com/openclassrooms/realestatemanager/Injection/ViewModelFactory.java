package com.openclassrooms.realestatemanager.Injection;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.Model.Picture;
import com.openclassrooms.realestatemanager.Repository.AddressRepository;
import com.openclassrooms.realestatemanager.Repository.PictureRepository;
import com.openclassrooms.realestatemanager.Repository.PropertiesRepository;
import com.openclassrooms.realestatemanager.Repository.UserRepository;
import com.openclassrooms.realestatemanager.UI.ViewModels.AddressViewModel;
import com.openclassrooms.realestatemanager.UI.ViewModels.PictureViewModel;
import com.openclassrooms.realestatemanager.UI.ViewModels.PropertiesViewModel;
import com.openclassrooms.realestatemanager.UI.ViewModels.UserViewModel;

import java.util.concurrent.Executor;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final PropertiesRepository mPropertiesRepository;
    private final AddressRepository mAddressRepository;
    private final UserRepository mUserRepository;
    private final PictureRepository mPictureRepository;
    private final Executor mExecutor;

    public ViewModelFactory(PropertiesRepository propertiesRepository, AddressRepository addressRepository, UserRepository userRepository, PictureRepository pictureRepository, Executor executor) {
        this.mPropertiesRepository = propertiesRepository;
        this.mAddressRepository = addressRepository;
        this.mUserRepository = userRepository;
        this.mPictureRepository = pictureRepository;
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
        if (modelClass.isAssignableFrom(UserViewModel.class)){
            return (T) new UserViewModel(mUserRepository,mExecutor);
        }
        if (modelClass.isAssignableFrom(PictureViewModel.class)){
            return (T) new PictureViewModel(mPictureRepository,mExecutor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
