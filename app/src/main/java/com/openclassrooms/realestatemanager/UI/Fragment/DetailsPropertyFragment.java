package com.openclassrooms.realestatemanager.UI.Fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.openclassrooms.realestatemanager.Model.Property;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsPropertyFragment extends Fragment implements OnMapReadyCallback {

    @BindView(R.id.details_layout_fragment)
    ScrollView mDetailsLayout;

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
    private GoogleMap mGoogleMap;

    public DetailsPropertyFragment() {
        // Required empty public constructor
    }

    public DetailsPropertyFragment(Property property) {
        mProperty = property;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details_property, container, false);
        ButterKnife.bind(this,view);

        mMapView.onCreate(savedInstanceState);

        if (mProperty != null){
            mDescription.setText(mProperty.getDescription());
            mSurface.setText(String.valueOf(mProperty.getSurface()));
            mNbrOfRooms.setText(String.valueOf(mProperty.getNbrOfRooms()));
            mAddressStreet.setText(mProperty.getAddress().getStreet());
            mAddressStreetComplement.setText(mProperty.getAddress().getComplement_street());
            mAddressDistrict.setText(mProperty.getAddress().getDistrict());
            mAddressStateAndPostCode.setText(mProperty.getAddress().getState()+" "+ mProperty.getAddress().getPostCode());
            mAddressCountry.setText(mProperty.getAddress().getCountry());

            mMapView.getMapAsync(this);
        }else{
            mDetailsLayout.setVisibility(View.GONE);
        }

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        String formattedAddress = mProperty.getAddress().getFormatedAddress();
        LatLng position = Utils.getLocationFromAddress(getContext(), formattedAddress);

        mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setMapToolbarEnabled(false);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(position));

        mGoogleMap.addMarker(new MarkerOptions()
                .position(position)
        );
    }
}
