package com.openclassrooms.realestatemanager.UI.Fragment;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.Injection.Injection;
import com.openclassrooms.realestatemanager.Injection.ViewModelFactory;
import com.openclassrooms.realestatemanager.UI.ViewModels.AddressViewModel;
import com.openclassrooms.realestatemanager.UI.ViewModels.PictureViewModel;
import com.openclassrooms.realestatemanager.UI.ViewModels.PropertiesViewModel;
import com.openclassrooms.realestatemanager.UI.ViewModels.UserViewModel;

public class BaseFragment extends Fragment {

    private ViewModelFactory modelFactory;
    protected PropertiesViewModel mPropertiesViewModel;
    protected AddressViewModel mAddressViewModel;
    protected PictureViewModel mPictureViewModel;
    protected UserViewModel mUserViewModel;

    protected void configureViewModels(Context context) {
        modelFactory = Injection.provideViewModelFactory(context);
        mPropertiesViewModel = new ViewModelProvider(getActivity(), modelFactory).get(PropertiesViewModel.class);
        mPropertiesViewModel.init();
        mAddressViewModel = new ViewModelProvider(getActivity(),modelFactory).get(AddressViewModel.class);
        mPictureViewModel = new ViewModelProvider(getActivity(),modelFactory).get(PictureViewModel.class);
        mUserViewModel = new ViewModelProvider(getActivity(),modelFactory).get(UserViewModel.class);
    }
}
