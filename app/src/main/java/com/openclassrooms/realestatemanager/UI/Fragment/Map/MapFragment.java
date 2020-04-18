package com.openclassrooms.realestatemanager.UI.Fragment.Map;

import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.openclassrooms.realestatemanager.Injection.Injection;
import com.openclassrooms.realestatemanager.Injection.ViewModelFactory;
import com.openclassrooms.realestatemanager.Model.Address;
import com.openclassrooms.realestatemanager.Model.Property;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.UI.Activities.DetailsPropertyActivity;
import com.openclassrooms.realestatemanager.UI.Fragment.DetailsProperty.DetailsPropertyFragment;
import com.openclassrooms.realestatemanager.UI.ViewModels.AddressViewModel;
import com.openclassrooms.realestatemanager.UI.ViewModels.PropertiesViewModel;
import com.openclassrooms.realestatemanager.Utils.Utils;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT;
import static com.openclassrooms.realestatemanager.UI.Activities.DetailsPropertyActivity.PROPERTY_ID_EXTRA;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private static final String PERMS = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final int RC_LOCATION_PERMS = 100;
    private final boolean twoPanes;
    private final boolean isUserConnected;

    private View mView;
    private FusedLocationProviderClient fusedLocationClient;
    private Location mCurrentUserLocation;
    private GoogleMap mMap;

    private PropertiesViewModel mPropertiesViewModel;
    protected AddressViewModel mAddressViewModel;
    private List<Property> mListProperties;

    public MapFragment(boolean twoPanes, boolean currentUser){
        this.twoPanes = twoPanes;
        this.isUserConnected = currentUser;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_map, container, false);
        configureViewModel();
        checkPermission();
        return mView;
    }

    private void configureViewModel() {
        ViewModelFactory modelFactory = Injection.provideViewModelFactory(getActivity());
        mPropertiesViewModel = new ViewModelProvider(getActivity(),modelFactory).get(PropertiesViewModel.class);
        mAddressViewModel = new ViewModelProvider(getActivity(),modelFactory).get(AddressViewModel.class);
        mPropertiesViewModel.getProperties().observe(getActivity(),this::getAllProperties);
    }

    private void getAllProperties(List<Property> properties) {
        mListProperties = properties;
    }

    public void getMapFragment(){
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

    @AfterPermissionGranted(RC_LOCATION_PERMS)
    public void checkPermission() {
        if (!EasyPermissions.hasPermissions(getActivity(),PERMS)) {
            EasyPermissions.requestPermissions(this, "We need your permission to access to your location.", RC_LOCATION_PERMS, PERMS);
            return;
        }else {
            if (isUserConnected) {
                getMapFragment();
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

    private void movePositionOnMap() {
        if (mCurrentUserLocation != null) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(mCurrentUserLocation.getLatitude(),
                    mCurrentUserLocation.getLongitude()), 12));
            getAddressForProperties();
        }
    }

    private void getAddressForProperties() {
        if (!mListProperties.isEmpty()){
            for (Property property: mListProperties) {
                mAddressViewModel.getAddressOfProperty(property.getId()).observe(this,this::putMarkerOnMap);
            }
        }
    }

    private void putMarkerOnMap(Address address) {
        LatLng latLng = Utils.getLocationFromAddress(getContext(), address.getFormatedAddress());
        if (latLng != null) {
            Marker marker = mMap.addMarker(new MarkerOptions().position(latLng));
            marker.setTag(address.getPropertyId());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        checkPermission();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        long propertyId = (long) marker.getTag();
        if (twoPanes){
            FragmentManager fragmentManager =  getParentFragmentManager();
            fragmentManager.beginTransaction()
                .replace(R.id.details_activity_frame_layout,new DetailsPropertyFragment((int) propertyId))
                .commit();
        }else{
            Intent intent = new Intent(getContext(), DetailsPropertyActivity.class);
            intent.putExtra(PROPERTY_ID_EXTRA,(int) propertyId);
            startActivity(intent);
        }
        return false;
    }
}
