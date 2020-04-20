package com.openclassrooms.realestatemanager.UI.Fragment.DetailsProperty;


import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.openclassrooms.realestatemanager.Model.FullProperty;
import com.openclassrooms.realestatemanager.Model.Picture;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.UI.Fragment.BaseFragment;
import com.openclassrooms.realestatemanager.UI.Fragment.PropertyManager.PicturesRecyclerViewAdapter;
import com.openclassrooms.realestatemanager.UI.Fragment.PropertyManager.PicturesViewHolder;
import com.openclassrooms.realestatemanager.Utils.DialogShowImage;
import com.openclassrooms.realestatemanager.Utils.StorageUtils;
import com.openclassrooms.realestatemanager.Utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.openclassrooms.realestatemanager.UI.Fragment.PropertyManager.PropertyManagerFragment.FOLDERNAME;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsPropertyFragment extends BaseFragment implements OnMapReadyCallback , PicturesViewHolder.ListenerPictureClick {

    private static final String INSTANCE_STATE = "INSTANCE_STATE";
    private static final String BUNDLE_INSTANCE_STATE = "BUNDLE_INSTANCE_STATE";

    @BindView(R.id.recycler_view_pictures)
    RecyclerView mPicturesRecyclerView;
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

    private FullProperty mFullProperty;
    private GoogleMap mGoogleMap;
    private Integer mPropertyId;
    private PicturesRecyclerViewAdapter mAdapter;

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

        configureRecyclerView();

        return view;
    }

    private void configureRecyclerView() {
        mAdapter = new PicturesRecyclerViewAdapter(getContext(),this);
        mPicturesRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mPicturesRecyclerView.setLayoutManager(layoutManager);
    }

    private void updateProperty(FullProperty property) {
        this.mFullProperty = property;
        mAdapter.updateListPictures(mFullProperty.getPictureList());

        if (mFullProperty != null){
            mDescription.setText(mFullProperty.getProperty().getDescription());
            mSurface.setText(String.valueOf(mFullProperty.getProperty().getSurface()));
            mNbrOfRooms.setText(String.valueOf(mFullProperty.getProperty().getNbrOfRooms()));

            mAddressStreet.setText(mFullProperty.getAddress().getStreet());
            if (mFullProperty.getAddress().getComplement_street() != null) {
                mAddressStreetComplement.setText(mFullProperty.getAddress().getComplement_street());
            }else {
                mAddressStreetComplement.setVisibility(View.GONE);
            }
            mAddressDistrict.setText(mFullProperty.getAddress().getDistrict());
            mAddressStateAndPostCode.setText(mFullProperty.getAddress().getState()+" "+ mFullProperty.getAddress().getPostCode());
            mAddressCountry.setText(mFullProperty.getAddress().getCountry());
            mMapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setMapToolbarEnabled(false);

        String formattedAddress = mFullProperty.getAddress().getFormatedAddress();
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
            bundle.putLong(BUNDLE_INSTANCE_STATE,mPropertyId);
            outState.putBundle(INSTANCE_STATE, bundle);
        }
    }

    @Override
    public void onClickPicture(Picture picture) {
        Bitmap bitmap = StorageUtils.getBitmapFromStorage(getActivity().getFilesDir(),getContext(),picture.getFile(),FOLDERNAME);
        DialogShowImage dialog = new DialogShowImage(bitmap);
        dialog.setTargetFragment(this,1);
        dialog.show(getParentFragmentManager(),"DialogImagePreview");
    }
}
