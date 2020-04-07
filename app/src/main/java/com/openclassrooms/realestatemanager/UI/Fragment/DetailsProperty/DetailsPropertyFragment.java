package com.openclassrooms.realestatemanager.UI.Fragment.DetailsProperty;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.openclassrooms.realestatemanager.Model.Address;
import com.openclassrooms.realestatemanager.Model.Property;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.UI.Fragment.BaseFragment;
import com.openclassrooms.realestatemanager.Utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsPropertyFragment extends BaseFragment implements OnMapReadyCallback {

    private static final String INSTANCE_STATE = "INSTANCE_STATE";
    private static final String BUNDLE_INSTANCE_STATE = "BUNDLE_INSTANCE_STATE";

    @BindView(R.id.no_property_selected_details_fragment)
    LinearLayout mNoSelectedProperty;
    @BindView(R.id.scroll_view_details_property)
    ScrollView mScrollViewDetailsProperty;
    @BindView(R.id.details_description_text_view)
    TextView mDescription;
    @BindView(R.id.details_surface_value_text_view)
    TextView mSurface;
    @BindView(R.id.details_nbr_of_rooms_value_text_view)
    TextView mNbrOfRooms;
    @BindView(R.id.details_address_street_text_view)
    TextView mAddressStreet;
    @BindView(R.id.details_address_complement_street_text_view)
    TextView mAddressStreetComplement;
    @BindView(R.id.details_address_district_text_view)
    TextView mAddressDistrict;
    @BindView(R.id.details_address_state_and_postcode_text_view)
    TextView mAddressStateAndPostCode;
    @BindView(R.id.details_address_country_text_view)
    TextView mAddressCountry;
    @BindView(R.id.mapView)
    MapView mMapView;

    private Property mProperty;
    private Address mAddressProperty;
    private GoogleMap mGoogleMap;
    private Integer mPropertyId;

    public DetailsPropertyFragment(){}

    public DetailsPropertyFragment(int propertyId) {
        this.mPropertyId = propertyId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details_property, container, false);
        ButterKnife.bind(this,view);

        configureViewModels(getContext());

        if (savedInstanceState != null){
            if (savedInstanceState.getBundle(BUNDLE_INSTANCE_STATE) != null) {
                mPropertyId = savedInstanceState.getBundle(BUNDLE_INSTANCE_STATE).getInt(INSTANCE_STATE);
            }
        }

        if (mPropertyId != null) {
            mPropertiesViewModel.getPropertyById(mPropertyId).observe(getActivity(),this::updateProperty);
            mScrollViewDetailsProperty.setVisibility(View.VISIBLE);
            mNoSelectedProperty.setVisibility(View.GONE);
        }else{
            mScrollViewDetailsProperty.setVisibility(View.GONE);
            mNoSelectedProperty.setVisibility(View.VISIBLE);
        }

        mMapView.onCreate(savedInstanceState);

        return view;
    }

    private void updateProperty(Property property) {
        this.mProperty = property;
        if (mProperty != null){
            mDescription.setText(mProperty.getDescription());
            mSurface.setText(String.valueOf(mProperty.getSurface()));
            mNbrOfRooms.setText(String.valueOf(mProperty.getNbrOfRooms()));
            
            mAddressViewModel.getAddressOfProperty(property.getId()).observe(this,this::getAddressOfProperty);
        }
    }

    private void getAddressOfProperty(Address address) {
        this.mAddressProperty = address;
        mAddressStreet.setText(address.getStreet());
        mAddressStreetComplement.setText(address.getComplement_street());
        mAddressDistrict.setText(address.getDistrict());
        mAddressStateAndPostCode.setText(address.getState()+" "+ address.getPostCode());
        mAddressCountry.setText(address.getCountry());
        mMapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setMapToolbarEnabled(false);

        String formattedAddress = mAddressProperty.getFormatedAddress();
        LatLng position = Utils.getLocationFromAddress(getContext(), formattedAddress);

        if (position != null){
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(position));
            mGoogleMap.addMarker(new MarkerOptions()
                    .position(position)
            );
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mPropertyId != null) {
            Bundle bundle = new Bundle();
            bundle.putInt(BUNDLE_INSTANCE_STATE,mPropertyId);
            outState.putBundle(INSTANCE_STATE, bundle);
        }
    }
}
