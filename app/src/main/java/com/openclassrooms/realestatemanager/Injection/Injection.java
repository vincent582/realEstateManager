package com.openclassrooms.realestatemanager.Injection;

import android.content.Context;

import com.openclassrooms.realestatemanager.Database.RealEstateManagerDataBase;
import com.openclassrooms.realestatemanager.Repository.AddressRepository;
import com.openclassrooms.realestatemanager.Repository.PropertiesRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

    public static PropertiesRepository providePropertyDataSource(Context context){
        RealEstateManagerDataBase dataBase = RealEstateManagerDataBase.getInstance(context);
        return new PropertiesRepository(dataBase.mPropertyDAO());
    }

    public static AddressRepository provideAddressDataSource(Context context){
        RealEstateManagerDataBase dataBase = RealEstateManagerDataBase.getInstance(context);
        return new AddressRepository(dataBase.mAddressDAO());
    }

    public static Executor provideExecutor(){
        return Executors.newSingleThreadExecutor();
    }

    public static ViewModelFactory provideViewModelFactory(Context context){
        PropertiesRepository propertiesRepository = providePropertyDataSource(context);
        AddressRepository addressRepository = provideAddressDataSource(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(propertiesRepository, addressRepository, executor);
    }
}
