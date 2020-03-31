package com.openclassrooms.realestatemanager.UI.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.openclassrooms.realestatemanager.Model.Property;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.UI.Fragment.DetailsPropertyFragment;
import com.openclassrooms.realestatemanager.UI.ViewModel.PropertyViewModel;

public class DetailsPropertyActivity extends AppCompatActivity {

    public static String PROPERTY_ID = "PROPERTY_ID";
    private DetailsPropertyFragment mDetailsPropertyFragment;
    private int mProperty_id;
    private PropertyViewModel mPropertyViewModel;
    private Property mProperty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_property);

        if (getIntent().hasExtra(PROPERTY_ID)){
            mProperty_id = getIntent().getIntExtra(PROPERTY_ID,0);
        }

        mPropertyViewModel = new ViewModelProvider(this).get(PropertyViewModel.class);
        mPropertyViewModel.init();
        mProperty = mPropertyViewModel.getPropertyById(mProperty_id);

        configureAndShowDetailsFragment();
    }

    private void configureAndShowDetailsFragment() {
        mDetailsPropertyFragment = (DetailsPropertyFragment) getSupportFragmentManager().findFragmentById(R.id.details_activity_frame_layout);

        if (mDetailsPropertyFragment == null){
            mDetailsPropertyFragment = new DetailsPropertyFragment(mProperty);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.details_activity_frame_layout,mDetailsPropertyFragment)
                    .commit();
        }
    }


}
