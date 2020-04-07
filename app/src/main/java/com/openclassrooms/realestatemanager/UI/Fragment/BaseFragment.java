package com.openclassrooms.realestatemanager.UI.Fragment;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.Injection.Injection;
import com.openclassrooms.realestatemanager.Injection.ViewModelFactory;
import com.openclassrooms.realestatemanager.UI.ViewModels.AddressViewModel;
import com.openclassrooms.realestatemanager.UI.ViewModels.PropertiesViewModel;

public class BaseFragment extends Fragment {

    private ViewModelFactory modelFactory;
    protected PropertiesViewModel mPropertiesViewModel;
    protected AddressViewModel mAddressViewModel;

    protected void configureViewModels(Context context) {
        modelFactory = Injection.provideViewModelFactory(context);
        mPropertiesViewModel = new ViewModelProvider(this, modelFactory).get(PropertiesViewModel.class);
        mPropertiesViewModel.init();
        mAddressViewModel = new ViewModelProvider(this,modelFactory).get(AddressViewModel.class);
    }
}
