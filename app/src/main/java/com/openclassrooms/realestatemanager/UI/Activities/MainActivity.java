package com.openclassrooms.realestatemanager.UI.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.navigation.NavigationView;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.UI.Fragment.DetailsProperty.DetailsPropertyFragment;
import com.openclassrooms.realestatemanager.UI.Fragment.ListProperties.ListPropertiesFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.activity_main_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.activity_main_drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.activity_main_host_frame_layout)
    FrameLayout mFrameLayout;
    @BindView(R.id.activity_main_navigation_view)
    NavigationView mNavigationView;

    private ListPropertiesFragment mListPropertiesFragment;
    private DetailsPropertyFragment mDetailsPropertyFragment;
    private boolean twoPanes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,R.string.drawer_layout_open,R.string.drawer_layout_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);

        if (findViewById(R.id.details_activity_frame_layout) != null) twoPanes = true;

        configureAndShowListFragment();
        configureAndShowDetailsFragment();
    }

    /**
     * Showing the list of properties with ListPropertiesFragment.
     */
    private void configureAndShowListFragment() {
        mListPropertiesFragment = (ListPropertiesFragment) getSupportFragmentManager().findFragmentById(R.id.activity_main_host_frame_layout);
        if (mListPropertiesFragment == null){
            mListPropertiesFragment = new ListPropertiesFragment(twoPanes);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_main_host_frame_layout, mListPropertiesFragment)
                    .commit();
        }else{
            mListPropertiesFragment.updateTwoPanesToFragment(twoPanes);
        }
    }

    /**
     * Display the DetailsPropertyFragment on tab device.
     */
    private void configureAndShowDetailsFragment() {
        mDetailsPropertyFragment = (DetailsPropertyFragment) getSupportFragmentManager().findFragmentById(R.id.details_activity_frame_layout);
        if (mDetailsPropertyFragment == null && findViewById(R.id.details_activity_frame_layout) != null){
            mDetailsPropertyFragment = new DetailsPropertyFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.details_activity_frame_layout,mDetailsPropertyFragment)
                    .commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.activity_main_drawer_list:
                    if (mListPropertiesFragment == null) mListPropertiesFragment = new ListPropertiesFragment(twoPanes);
                    this.startTransactionFragment(mListPropertiesFragment);
                break;
            case R.id.activity_main_drawer_settings:
                break;
            default:
                break;
        }
        this.mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    public void startTransactionFragment(Fragment fragment){
        if(!fragment.isVisible()){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_main_host_frame_layout,fragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu_toolbar,menu);
        return true;
    }
}
