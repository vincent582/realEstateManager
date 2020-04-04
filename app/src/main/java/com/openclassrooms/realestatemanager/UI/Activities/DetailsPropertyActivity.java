package com.openclassrooms.realestatemanager.UI.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.UI.Fragment.DetailsProperty.DetailsPropertyFragment;

public class DetailsPropertyActivity extends AppCompatActivity {


    public static String PROPERTY_ID_EXTRA = "PROPERTY_ID_EXTRA";

    private DetailsPropertyFragment mDetailsPropertyFragment;
    private int mPropertyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_property);

        if (getIntent().hasExtra(PROPERTY_ID_EXTRA)){
            mPropertyId = getIntent().getIntExtra(PROPERTY_ID_EXTRA,0);
        }

        configureAndShowDetailsFragment();
    }

    private void configureAndShowDetailsFragment() {
        mDetailsPropertyFragment = (DetailsPropertyFragment) getSupportFragmentManager().findFragmentById(R.id.details_activity_frame_layout);

        if (mDetailsPropertyFragment == null){
            mDetailsPropertyFragment = new DetailsPropertyFragment(mPropertyId);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.details_activity_frame_layout,mDetailsPropertyFragment)
                    .commit();
        }
    }
}
