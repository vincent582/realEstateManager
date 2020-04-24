package com.openclassrooms.realestatemanager.UI.Fragment.Map;

import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.openclassrooms.realestatemanager.Model.FullProperty;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.UI.Activities.DetailsPropertyActivity;
import com.openclassrooms.realestatemanager.UI.Fragment.BaseFragment;
import com.openclassrooms.realestatemanager.UI.Fragment.DetailsProperty.DetailsPropertyFragment;
import com.openclassrooms.realestatemanager.Utils.Utils;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.openclassrooms.realestatemanager.UI.Activities.DetailsPropertyActivity.PROPERTY_ID_EXTRA;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends BaseFragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private static final String PERMS = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final int RC_LOCATION_PERMS = 100;

    private final boolean twoPanes;
    private final boolean isUserConnected;
    private View mView;
    private FusedLocationProviderClient fusedLocationClient;
    private Location mCurrentUserLocation;
    private GoogleMap mMap;
    private List<FullProperty> mListProperties;

    //CONSTRUCTOR
    public MapFragment(boolean twoPanes, boolean currentUser){
        this.twoPanes = twoPanes;
        this.isUserConnected = currentUser;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_map, container, false);
        configureViewModels(getActivity());
        mPropertiesViewModel.getFullProperties().observe(getActivity(),this::getAllProperties);
        checkPermission();
        return mView;
    }

    /**
     * get list of all properties
     * @param fullProperties
     */
    private void getAllProperties(List<FullProperty> fullProperties) {
        mListProperties = fullProperties;
    }

    /**
     * Check if permission to access location are granted
     * then get map fragment and move camera to user location
     */
    @AfterPermissionGranted(RC_LOCATION_PERMS)
    public void checkPermission() {
        if (!EasyPermissions.hasPermissions(getActivity(),PERMS)) {
            EasyPermissions.requestPermissions(this, "We need your permission to access to your location.", RC_LOCATION_PERMS, PERMS);
            return;
        }else {
            if (isUserConnected) {
                configureMapFragment();
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
                fusedLocationClient.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        mCurrentUserLocation = location;
                        movePositionOnMap();
                    }
                });
            }else{
                Snackbar.make(mView,"No user connected.", Snackbar.LENGTH_LONG).show();
            }
        }
    }

    /**
     * get Google map fragment
     */
    public void configureMapFragment(){
        if (mMap != null) {
            return;
        }else {
            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
    }

    /**
     * Move map camera to user location
     */
    private void movePositionOnMap() {
        if (mCurrentUserLocation != null) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(mCurrentUserLocation.getLatitude(),
                    mCurrentUserLocation.getLongitude()), 12));
            putMarkersOnMap();
        }
    }

    /**
     * Put markers on map foreach properties
     */
    private void putMarkersOnMap() {
        if (!mListProperties.isEmpty()){
            for (FullProperty property: mListProperties) {
                LatLng latLng = Utils.getLocationFromAddress(getContext(), property.getAddress().getFormatedAddress());
                if (latLng != null) {
                    Marker marker = mMap.addMarker(new MarkerOptions().position(latLng));
                    marker.setTag(property.getAddress().getPropertyId());
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        checkPermission();
    }

    /**
     * Manage click on marker to start Details Activity/Fragment
     * @param marker
     * @return
     */
    @Override
    public boolean onMarkerClick(Marker marker) {
        int propertyId = (int) marker.getTag();
        if (twoPanes){
            FragmentManager fragmentManager =  getParentFragmentManager();
            fragmentManager.beginTransaction()
                .replace(R.id.details_activity_frame_layout,new DetailsPropertyFragment(propertyId))
                .commit();
        }else{
            Intent intent = new Intent(getContext(), DetailsPropertyActivity.class);
            intent.putExtra(PROPERTY_ID_EXTRA, propertyId);
            startActivity(intent);
        }
        return false;
    }
}
