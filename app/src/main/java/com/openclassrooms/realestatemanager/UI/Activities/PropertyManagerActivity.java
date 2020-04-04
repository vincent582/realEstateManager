package com.openclassrooms.realestatemanager.UI.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.UI.Fragment.DetailsProperty.DetailsPropertyFragment;
import com.openclassrooms.realestatemanager.UI.Fragment.PropertyManager.PropertyManagerFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PropertyManagerActivity extends AppCompatActivity {

    @BindView(R.id.activity_main_toolbar)
    Toolbar mToolbar;

    private PropertyManagerFragment mPropertyManagerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_manager);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        configureAndShowPropertyManagerFragment();
    }

    private void configureAndShowPropertyManagerFragment() {
        mPropertyManagerFragment = (PropertyManagerFragment) getSupportFragmentManager().findFragmentById(R.id.property_manager_activity_frame_layout);

        if (mPropertyManagerFragment == null){
            mPropertyManagerFragment = new PropertyManagerFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.property_manager_activity_frame_layout,mPropertyManagerFragment)
                    .commit();
        }
    }
}
