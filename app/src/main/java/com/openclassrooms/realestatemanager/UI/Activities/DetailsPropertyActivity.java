package com.openclassrooms.realestatemanager.UI.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.UI.Fragment.DetailsPropertyFragment;

public class DetailsPropertyActivity extends AppCompatActivity {

    DetailsPropertyFragment mDetailsPropertyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_property);
        configureAndShowDetailsFragment();
    }

    private void configureAndShowDetailsFragment() {
        mDetailsPropertyFragment = (DetailsPropertyFragment) getSupportFragmentManager().findFragmentById(R.id.details_activity_frame_layout);

        if (mDetailsPropertyFragment == null){
            mDetailsPropertyFragment = new DetailsPropertyFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.details_activity_frame_layout,mDetailsPropertyFragment)
                    .commit();
        }
    }


}
