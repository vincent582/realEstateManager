package com.openclassrooms.realestatemanager.UI.Fragment.Search;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.openclassrooms.realestatemanager.Dummy.Dummy;
import com.openclassrooms.realestatemanager.Model.Property;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.UI.Fragment.BaseFragment;
import com.openclassrooms.realestatemanager.UI.Fragment.ListProperties.ListPropertiesFragment;
import com.openclassrooms.realestatemanager.Utils.Dialog.DialogEntryDatePicker;
import com.openclassrooms.realestatemanager.Utils.Dialog.DialogSoldDatePiker;
import com.openclassrooms.realestatemanager.Utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends BaseFragment implements DialogEntryDatePicker.DialogEntryDatePickerListener,
    DialogSoldDatePiker.DialogSoldDatePickerListener{

    @BindView(R.id.search_property_price_min_values_et) EditText mPriceMin;
    @BindView(R.id.search_property_price_max_values_et) EditText mPriceMax;
    @BindView(R.id.search_property_room_nbr_min_values_et) EditText mNbrRoomMin;
    @BindView(R.id.search_property_room_nbr_max_values_et) EditText mNbrRoomMax;
    @BindView(R.id.search_property_surface_min_values_et) EditText mSurfaceMin;
    @BindView(R.id.search_property_surface_max_values_et) EditText mSurfaceMax;

    @BindView(R.id.search_property_entry_date_min_values_et) Button mEntryDateMinButton;
    @BindView(R.id.search_property_sold_date_min_values_et) Button mSoldDateMinButton;

    @BindView(R.id.checkBox_park) CheckBox mCheckboxPark;
    @BindView(R.id.checkBox_school) CheckBox mCheckboxSchool;
    @BindView(R.id.checkBox_station) CheckBox mCheckboxStation;
    @BindView(R.id.checkBox_store) CheckBox mCheckboxStore;
    @BindView(R.id.search_property_district_sp) Spinner mDistrictSpinner;

    private Integer surfaceMin = 0;
    private Integer surfaceMax = null;
    private List<String> mListFacilities = new ArrayList<>();
    private Integer priceMin = 0;
    private Integer priceMax = null;
    private Integer nbrRoomMin = 0;
    private Integer nbrRoomMax = null;
    private Date minDateOfEntry;
    private Date minDateOfSale;
    private String district = null;

    private List<Property> mPropertyList;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this,view);
        configureViewModels(getContext());
        mPropertiesViewModel.getAllProperties().observe(getViewLifecycleOwner(),propertyList ->{
            mPropertyList = propertyList;
            configurePropertyDistrictSpinner();
        });
        return view;
    }

    private void configurePropertyDistrictSpinner() {
        List<String> listDistrict = new ArrayList<>();
        listDistrict.add("None");
        for (Property property: mPropertyList) {
            if (!listDistrict.contains(property.getAddress().getDistrict())){
                listDistrict.add(property.getAddress().getDistrict());
            }
        }
        mDistrictSpinner.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.support_simple_spinner_dropdown_item, listDistrict));
        mDistrictSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                district = (String) mDistrictSpinner.getSelectedItem();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                district = null;
            }
        });
    }

    @OnClick(R.id.search_property_action_btn)
    public void searchProperties(){
        getSearchValues();
        checkPropertiesValues();
    }

    private void checkPropertiesValues() {
        List<Property> propertyList = new ArrayList<>();
        if (mPropertyList != null) {
            for (Property property : mPropertyList) {
                if (isBetweenPriceMinAndMax(property) && isBetweenSurfaceMinAndMax(property) &&
                    isBetweenNbrOfRoomMinAndMax(property) && isAfterMinDateOfEntry(property) &&
                isAfterMinDateOfSold(property) && isInDistrict(property) && isContainsFacilities(property)){
                    propertyList.add(property);
                }
            }
        }
        getSearchedProperties(propertyList);
    }

    public boolean isBetweenPriceMinAndMax(Property property){
        if (property.getPrice() >= priceMin && priceMax == null ||
            property.getPrice() >= priceMin && property.getPrice() <= priceMax) {
            return true;
        }
        return false;
    }

    public boolean isBetweenSurfaceMinAndMax(Property property){
        if (property.getSurface() >= surfaceMin && surfaceMax == null ||
            property.getSurface() >= surfaceMin && property.getSurface() <= surfaceMax){
            return true;
        }
        return false;
    }

    public boolean isBetweenNbrOfRoomMinAndMax(Property property){
        if (property.getNbrOfRooms() >= nbrRoomMin && nbrRoomMax == null ||
            property.getNbrOfRooms() >= nbrRoomMin && property.getNbrOfRooms() <= nbrRoomMax) {
            return true;
        }
        return false;
    }

    public boolean isAfterMinDateOfEntry(Property property){
        if (minDateOfEntry == null || property.getAddedDate().after(minDateOfEntry)){
            return true;
        }
        return false;
    }

    public boolean isAfterMinDateOfSold(Property property){
        if (minDateOfSale == null || property.getDateOfSale() != null && property.getDateOfSale().after(minDateOfSale)) {
            return true;
        }
        return false;
    }

    public boolean isInDistrict(Property property){
        if (property.getAddress().getDistrict().equals(district) || district.equals("None")) {
            return true;
        }
        return false;
    }

    public boolean isContainsFacilities(Property property){
        boolean b = true;
        if (!mListFacilities.isEmpty()) {
            for (String facility : mListFacilities) {
                if (property.getFacilities().contains(facility)){
                    b = true;
                }else {
                    b = false;
                }
            }
        } else {
            b = true;
        }
        return b;
    }

    private void getSearchedProperties(List<Property> propertyList) {
        FragmentManager fragmentManager =  getParentFragmentManager();
        fragmentManager.beginTransaction()
            .replace(R.id.activity_main_host_frame_layout,new ListPropertiesFragment(propertyList,true))
            .commit();
    }

    @OnClick(R.id.search_property_entry_date_min_values_et)
    public void openDialogGetAddedDateMin(){
        DialogEntryDatePicker dialog = new DialogEntryDatePicker();
        dialog.setTargetFragment(this,2);
        dialog.show(getParentFragmentManager(),"DialogDatePickerFragment");
    }

    @OnClick(R.id.search_property_sold_date_min_values_et)
    public void openDialogGetSoldDateMin(){
        DialogSoldDatePiker dialog = new DialogSoldDatePiker();
        dialog.setTargetFragment(this,2);
        dialog.show(getParentFragmentManager(),"DialogDatePickerFragment");
    }

    private void getSearchValues() {
        if (!isEmptyEditText(mPriceMin)){
            priceMin = Integer.parseInt(mPriceMin.getText().toString());
        }
        if (!isEmptyEditText(mPriceMax)){
            priceMax = Integer.parseInt(mPriceMax.getText().toString());
        }
        if (!isEmptyEditText(mSurfaceMin)){
            surfaceMin = Integer.parseInt(mSurfaceMin.getText().toString());
        }
        if (!isEmptyEditText(mSurfaceMax)){
            surfaceMax = Integer.parseInt(mSurfaceMax.getText().toString());
        }
        if (!isEmptyEditText(mNbrRoomMin)){
            nbrRoomMin = Integer.parseInt(mNbrRoomMin.getText().toString());
        }
        if (!isEmptyEditText(mNbrRoomMax)){
            nbrRoomMax = Integer.parseInt(mNbrRoomMax.getText().toString());
        }

        if (mCheckboxPark.isChecked()){
            mListFacilities.add("Park");
        }
        if (mCheckboxSchool.isChecked()){
            mListFacilities.add("School");
        }
        if (mCheckboxStation.isChecked()){
            mListFacilities.add("Station");
        }
        if (mCheckboxStore.isChecked()){
            mListFacilities.add("Store");
        }
    }

    private boolean isEmptyEditText(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;
        return true;
    }

    @Override
    public void onDialogDatePikerValidateClick(DialogEntryDatePicker dialog) {
        DatePicker datePicker = dialog.getDialog().findViewById(R.id.entry_date_dp);

        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        minDateOfEntry = calendar.getTime();
        mEntryDateMinButton.setText(Utils.formatDate(minDateOfEntry));
    }

    @Override
    public void onDialogSoldDatePikerValidateClick(DialogSoldDatePiker dialog) {
        DatePicker datePicker = dialog.getDialog().findViewById(R.id.entry_date_dp);

        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        minDateOfSale = calendar.getTime();
        mSoldDateMinButton.setText(Utils.formatDate(minDateOfSale));
    }
}
