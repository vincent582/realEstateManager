package com.openclassrooms.realestatemanager.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.openclassrooms.realestatemanager.Injection.Injection;
import com.openclassrooms.realestatemanager.Injection.ViewModelFactory;
import com.openclassrooms.realestatemanager.Model.User;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.UI.Fragment.DetailsProperty.DetailsPropertyFragment;
import com.openclassrooms.realestatemanager.UI.Fragment.ListProperties.ListPropertiesFragment;
import com.openclassrooms.realestatemanager.UI.Fragment.Map.MapFragment;
import com.openclassrooms.realestatemanager.UI.ViewModels.UserViewModel;
import com.openclassrooms.realestatemanager.Utils.DialogAuthentication;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT;
import static com.openclassrooms.realestatemanager.UI.Activities.DetailsPropertyActivity.PROPERTY_ID_EXTRA_FOR_PROPERTY_MANAGER;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ListPropertiesFragment.sendPropertyIdToMainActivityOnClickListener , DialogAuthentication.DialogAuthenticationListener {

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
    private Integer mPropertyId;

    private UserViewModel mUserViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ViewModelFactory modelFactory = Injection.provideViewModelFactory(this);
        mUserViewModel = new ViewModelProvider(this, modelFactory).get(UserViewModel.class);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,R.string.drawer_layout_open,R.string.drawer_layout_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.primaryTextColor));
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
            mListPropertiesFragment = new ListPropertiesFragment(twoPanes,this);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_main_host_frame_layout, mListPropertiesFragment)
                    .commit();
        }else{
            mListPropertiesFragment.updateTwoPanesAndListenerToFragment(twoPanes,this);
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
                    if (mListPropertiesFragment == null) mListPropertiesFragment = new ListPropertiesFragment(twoPanes,this);
                    this.startTransactionFragment(mListPropertiesFragment);
                break;
            case R.id.activity_main_drawer_map:
                    MapFragment mapFragment = new MapFragment();
                    this.startTransactionFragment(mapFragment);
                break;
            case R.id.activity_main_drawer_connection:
                    configureSignIn();
                break;
            default:
                break;
        }
        this.mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Configure sign-in
     */
    private void configureSignIn() {
        DialogAuthentication dialog = new DialogAuthentication();
        dialog.show(getSupportFragmentManager(),"DialogAuthentication");
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
        //currentUser
        boolean test = true;
        if (test) {
            if (twoPanes) {
                getMenuInflater().inflate(R.menu.activity_main_menu_two_panes_toolbar, menu);
            } else {
                getMenuInflater().inflate(R.menu.main_activity_menu_toolbar, menu);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_bar_add_item:
                    mPropertyId = null;
                    startPropertyManagerActivity();
                break;
            case R.id.app_bar_edit_item:
                    if (mPropertyId != null){
                        startPropertyManagerActivity();
                    }else{
                        Snackbar.make(mDetailsPropertyFragment.getView(),"You have to select an item to update it!",Snackbar.LENGTH_SHORT).show();
                    }
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void startPropertyManagerActivity(){
        Intent intent = new Intent(this,PropertyManagerActivity.class);
        if (mPropertyId != null) {
            intent.putExtra(PROPERTY_ID_EXTRA_FOR_PROPERTY_MANAGER, mPropertyId);
        }
        startActivity(intent);
    }

    @Override
    public void callbackPropertyId(int propertyId) {
        this.mPropertyId = propertyId;
    }

    /**
     * Get Dialog answer to Sign-in request.
     * @param dialogAuthentication
     */
    @Override
    public void onDialogAuthenticationSignInClick(DialogAuthentication dialogAuthentication) {
        EditText editTextUserName = dialogAuthentication.getDialog().findViewById(R.id.name_user_editText);
        String userName = editTextUserName.getText().toString();
        EditText editTextPassword = dialogAuthentication.getDialog().findViewById(R.id.password_editText);
        String password = editTextPassword.getText().toString();

        mUserViewModel.getUser(userName,password).observe(this,this::getuser);
        }

    private void getuser(User user) {
        if (user != null){
            Snackbar.make(getCurrentFocus(),"Successful authentication", LENGTH_SHORT).show();
        }else{
            Snackbar.make(getCurrentFocus(),"Authentication failed", LENGTH_SHORT).show();
        }
    }

    /**
     * Get Dialog answer to register request.
     * @param dialogAuthentication
     */
    @Override
    public void onDialogAuthenticationRegisterClick(DialogAuthentication dialogAuthentication) {
        EditText editTextUserName = dialogAuthentication.getDialog().findViewById(R.id.name_user_editText);
        String userName = editTextUserName.getText().toString();
        EditText editTextPassword = dialogAuthentication.getDialog().findViewById(R.id.password_editText);
        String password = editTextPassword.getText().toString();
        if (userName.isEmpty() || password.isEmpty()){
            Snackbar.make(getCurrentFocus(),"Registration failed", LENGTH_SHORT).show();
        }else {
            User user = new User(userName, password);
            mUserViewModel.createUser(user);
            //TODO create user in db and conect it
            Snackbar.make(getCurrentFocus(), "Successful registration", LENGTH_SHORT).show();
        }
    }
}
