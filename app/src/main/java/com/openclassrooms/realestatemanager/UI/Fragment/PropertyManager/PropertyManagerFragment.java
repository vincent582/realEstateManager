package com.openclassrooms.realestatemanager.UI.Fragment.PropertyManager;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.openclassrooms.realestatemanager.Dummy.Dummy;
import com.openclassrooms.realestatemanager.Model.Address;
import com.openclassrooms.realestatemanager.Model.Property;
import com.openclassrooms.realestatemanager.Model.User;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.UI.Fragment.DetailsProperty.DetailsPropertyViewModel;
import com.openclassrooms.realestatemanager.UI.Fragment.ListProperties.PropertiesViewModel;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.openclassrooms.realestatemanager.UI.Activities.DetailsPropertyActivity.PROPERTY_ID_EXTRA_FOR_PROPERTY_MANAGER;

/**
 * A simple {@link Fragment} subclass.
 */
public class PropertyManagerFragment extends Fragment {

    @BindView(R.id.manager_layout_type_spinner) Spinner mPropertyTypeSpinner;
    @BindView(R.id.manager_layout_price_editText) EditText mPropertyPrice;
    @BindView(R.id.manager_layout_description_editText) EditText mPropertyDescription;
    @BindView(R.id.manager_layout_surface_editText) EditText mPropertySurface;
    @BindView(R.id.manager_layout_nbr_of_room_editText) EditText mPropertyNbrOfRooms;
    @BindView(R.id.manager_layout_address_nbr_of_street_editText) EditText mPropertyAddressStreetNumber;
    @BindView(R.id.manager_layout_address_street_editText) EditText mPropertyAddressStreet;
    @BindView(R.id.manager_layout_address_district_editText) EditText mPropertyAddressDistrict;
    @BindView(R.id.manager_layout_address_state_editText) EditText mPropertyAddressState;
    @BindView(R.id.manager_layout_address_post_code_editText) EditText mPropertyAddressPostCode;
    @BindView(R.id.manager_layout_address_country_editText) EditText mPropertyAddressCountry;

    @BindView(R.id.manager_layout_btn_add_property) Button mAddPropertyButton;

    private PropertiesViewModel mPropertiesViewModel;
    private DetailsPropertyViewModel mDetailsPropertyViewModel;

    private String mType;
    private Integer mPrice;
    private String mDescription;
    private Integer mSurface;
    private Integer mNbrOfRoom;

    private Integer mAddressStreetNbr;
    private String mAddressStreet;
    private String mAddressDistrict;
    private String mAddressState;
    private Integer mAddressPostCode;
    private String mAddressCountry;

    private Integer mPropertyId;
    private Property mProperty;


    public PropertyManagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_property_manager, container, false);
        ButterKnife.bind(this,view);

        mPropertiesViewModel = new ViewModelProvider(getActivity()).get(PropertiesViewModel.class);
        mPropertiesViewModel.init();
        mDetailsPropertyViewModel = new ViewModelProvider(getActivity()).get(DetailsPropertyViewModel.class);
        mDetailsPropertyViewModel.init();

        mPropertyId = getArguments().getInt(PROPERTY_ID_EXTRA_FOR_PROPERTY_MANAGER);
        if (mPropertyId != null){
            mDetailsPropertyViewModel.getProperty(mPropertyId).observe(getActivity(),this::updateProperty);
        }

        configurePropertyTypeSpinner();
        setAddPropertyButtonOnclickListener();

        return view;
    }

    private void updateProperty(Property property) {
        this.mProperty = property;
        populateEditItemWithPropertyValues(mProperty);
    }

    private void populateEditItemWithPropertyValues(Property property) {
        //TODO set defaultvalue of spinner
        mPropertyTypeSpinner.setSelection(Dummy.propertyType.indexOf(property.getType()));
        mPropertyPrice.setText(String.valueOf(property.getPrice()));
        mPropertyDescription.setText(property.getDescription());
        mPropertySurface.setText(String.valueOf(property.getSurface()));
        mPropertyNbrOfRooms.setText(String.valueOf(property.getNbrOfRooms()));
        //TODO add facilities
        mPropertyAddressStreetNumber.setText(String.valueOf(property.getAddress().getNumber()));
        mPropertyAddressStreet.setText(property.getAddress().getStreet());
        mPropertyAddressDistrict.setText(property.getAddress().getDistrict());
        mPropertyAddressState.setText(property.getAddress().getState());
        mPropertyAddressPostCode.setText(String.valueOf(property.getAddress().getPostCode()));
        mPropertyAddressCountry.setText(property.getAddress().getCountry());
        //TODO add isSold boolean
        mAddPropertyButton.setText("Update Property");
    }

    private void configurePropertyTypeSpinner() {
        mPropertyTypeSpinner.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.support_simple_spinner_dropdown_item, Dummy.propertyType));

        mPropertyTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mType = (String) mPropertyTypeSpinner.getSelectedItem();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mType = String.valueOf(mPropertyTypeSpinner.getItemIdAtPosition(0));
            }
        });
    }

    private void setAddPropertyButtonOnclickListener() {
        mAddPropertyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValuesOfInput()){

                    if (mProperty != null){
                        mProperty.setType(mType);
                        mProperty.setPrice(mPrice);
                        mProperty.setSurface(mSurface);
                        mProperty.setNbrOfRooms(mNbrOfRoom);
                        mProperty.setDescription(mDescription);
                        mProperty.setListFacilities(new ArrayList<>());
                        //TODO getValue of checkbox sold
                        mProperty.setSold(false);
                        mProperty.setDateOfSale(new Date());

                        Address address = mProperty.getAddress();
                        address.setNumber(mAddressStreetNbr);
                        address.setStreet(mAddressStreet);
                        address.setDistrict(mAddressDistrict);
                        address.setState(mAddressState);
                        address.setPostCode(mAddressPostCode);
                        address.setCountry(mAddressCountry);

                        mProperty.setAddress(address);

                        mDetailsPropertyViewModel.updateProperty(mProperty);

                    }else {
                        Address address = new Address(mAddressStreetNbr, mAddressStreet, mAddressDistrict,
                                mAddressState, mAddressPostCode, mAddressCountry);
                        Property property = new Property(5, mType, mPrice, mSurface, mNbrOfRoom,
                                mDescription, address, new ArrayList<>(), false, new Date(), new Date(),
                                new User("toto"));
                        mPropertiesViewModel.addPropertyToList(property);
                    }
                    getActivity().finish();
                }
            }
        });
    }

    private Boolean checkValuesOfInput() {
        Boolean values = true;

        if (!mPropertyPrice.getText().toString().isEmpty()){
            mPrice = Integer.valueOf(mPropertyPrice.getText().toString());
        }else{
            mPropertyPrice.setError("Vous devez renseigner le prix du bien");
            values = false;
        }

        if (!mPropertyDescription.getText().toString().isEmpty()){
            mDescription = mPropertyDescription.getText().toString();
        }else{
            mPropertyDescription.setError("Vous devez renseigner une description");
            values = false;
        }

        if (!mPropertySurface.getText().toString().isEmpty()){
            mSurface = Integer.valueOf(mPropertySurface.getText().toString());
        }else{
            mPropertyDescription.setError("Vous devez renseigner une description");
            values = false;
        }

        if (!mPropertyNbrOfRooms.getText().toString().isEmpty()){
            mNbrOfRoom = Integer.valueOf(mPropertyNbrOfRooms.getText().toString());
        }else{
            mPropertyDescription.setError("Vous devez renseigner une description");
            values = false;
        }

        if (!mPropertyAddressStreetNumber.getText().toString().isEmpty()){
            mAddressStreetNbr = Integer.valueOf(mPropertyAddressStreetNumber.getText().toString());
        }else{
            mPropertyAddressStreetNumber.setError("Vous devez renseigner le numero de rue");
            values = false;
        }

        if (!mPropertyAddressStreet.getText().toString().isEmpty()){
            mAddressStreet = mPropertyAddressStreet.getText().toString();
        }else{
            mPropertyAddressStreet.setError("Vous devez renseigner la rue");
            values = false;
        }

        if (!mPropertyAddressDistrict.getText().toString().isEmpty()){
            mAddressDistrict = mPropertyAddressDistrict.getText().toString();
        }else{
            mPropertyAddressDistrict.setError("Vous devez renseigner le quartier");
            values = false;
        }

        if (!mPropertyAddressState.getText().toString().isEmpty()){
            mAddressState = mPropertyAddressState.getText().toString();
        }else{
            mPropertyAddressState.setError("Vous devez renseigner l'état");
            values = false;
        }

        if (!mPropertyAddressPostCode.getText().toString().isEmpty()){
            mAddressPostCode = Integer.valueOf(mPropertyAddressPostCode.getText().toString());
        }else{
            mPropertyAddressPostCode.setError("Vous devez renseigner le CodePostal");
            values = false;
        }

        if (!mPropertyAddressCountry.getText().toString().isEmpty()){
            mAddressCountry = mPropertyAddressCountry.getText().toString();
        }else{
            mPropertyAddressCountry.setError("Vous devez renseigner le pays");
            values = false;
        }

        return values;
    }

}
