package com.openclassrooms.realestatemanager.UI.Fragment.Search;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.openclassrooms.realestatemanager.Model.Property;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.UI.Fragment.BaseFragment;
import com.openclassrooms.realestatemanager.Utils.Converter.DateConverter;
import com.openclassrooms.realestatemanager.Utils.Dialog.DialogEntryDatePicker;
import com.openclassrooms.realestatemanager.Utils.Dialog.DialogEntryMaxDatePicker;
import com.openclassrooms.realestatemanager.Utils.Dialog.DialogSoldDatePiker;
import com.openclassrooms.realestatemanager.Utils.Dialog.DialogSoldMaxDatePicker;
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
    DialogSoldDatePiker.DialogSoldDatePickerListener, DialogEntryMaxDatePicker.DialogEntryMaxDatePickerListener,
    DialogSoldMaxDatePicker.DialogSoldMaxDatePickerListener {

    @BindView(R.id.search_property_price_min_values_et) EditText mPriceMin;
    @BindView(R.id.search_property_price_max_values_et) EditText mPriceMax;
    @BindView(R.id.search_property_room_nbr_min_values_et) EditText mNbrRoomMin;
    @BindView(R.id.search_property_room_nbr_max_values_et) EditText mNbrRoomMax;
    @BindView(R.id.search_property_surface_min_values_et) EditText mSurfaceMin;
    @BindView(R.id.search_property_surface_max_values_et) EditText mSurfaceMax;

    @BindView(R.id.search_property_entry_date_min_values_et) Button mEntryDateMinButton;
    @BindView(R.id.search_property_entry_date_max_values_et) Button mEntryDateMaxButton;
    @BindView(R.id.search_property_sold_date_min_values_et) Button mSoldDateMinButton;
    @BindView(R.id.search_property_sold_date_max_values_et) Button mSoldDateMaxButton;

    @BindView(R.id.checkBox_park) CheckBox mCheckboxPark;
    @BindView(R.id.checkBox_school) CheckBox mCheckboxSchool;
    @BindView(R.id.checkBox_station) CheckBox mCheckboxStation;
    @BindView(R.id.checkBox_store) CheckBox mCheckboxStore;
    @BindView(R.id.search_property_district_sp) Spinner mDistrictSpinner;

    private Integer surfaceMin;
    private Integer surfaceMax;
    private List<String> mListFacilities = new ArrayList<>();
    private Date minDateOfEntry;
    private Date minDateOfSale;
    private Integer priceMin;
    private Integer priceMax;
    private Integer nbrRoomMin;
    private Integer nbrRoomMax;
    private Date maxDateOfEntry;
    private Date maxDateOfSale;

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
        return view;
    }

    @OnClick(R.id.search_property_action_btn)
    public void searchProperties(){
        getSearchValues();
        mPropertiesViewModel.searchProperties(surfaceMin,surfaceMax,priceMin,priceMax,mListFacilities, DateConverter.fromDate(minDateOfEntry)
            ,DateConverter.fromDate(maxDateOfEntry),DateConverter.fromDate(minDateOfSale),DateConverter.fromDate(maxDateOfSale)).observe(this,this::getSearchedProperties);
    }

    private void getSearchedProperties(List<Property> fullProperties) {
        Log.e("TAG", "getSearchedProperties: "+ fullProperties );
    }

    @OnClick(R.id.search_property_entry_date_min_values_et)
    public void openDialogGetAddedDateMin(){
        DialogEntryDatePicker dialog = new DialogEntryDatePicker();
        dialog.setTargetFragment(this,2);
        dialog.show(getParentFragmentManager(),"DialogDatePickerFragment");
    }

    @OnClick(R.id.search_property_entry_date_max_values_et)
    public void openDialogGetAddedDateMax(){
        DialogEntryMaxDatePicker dialog = new DialogEntryMaxDatePicker();
        dialog.setTargetFragment(this,2);
        dialog.show(getParentFragmentManager(),"DialogEntryMaxDatePickerFragment");
    }

    @OnClick(R.id.search_property_sold_date_min_values_et)
    public void openDialogGetSoldDateMin(){
        DialogSoldDatePiker dialog = new DialogSoldDatePiker();
        dialog.setTargetFragment(this,2);
        dialog.show(getParentFragmentManager(),"DialogDatePickerFragment");
    }

    @OnClick(R.id.search_property_sold_date_max_values_et)
    public void openDialogGetSoldDateMax(){
        DialogSoldMaxDatePicker dialog = new DialogSoldMaxDatePicker();
        dialog.setTargetFragment(this,2);
        dialog.show(getParentFragmentManager(),"DialogDatePickerFragment");
    }

    private void getSearchValues() {
        priceMin = Integer.valueOf(mPriceMin.getText().toString());
        priceMax = Integer.valueOf(mPriceMax.getText().toString());
        nbrRoomMin = Integer.valueOf(mNbrRoomMin.getText().toString());
        nbrRoomMax = Integer.valueOf(mNbrRoomMax.getText().toString());
        surfaceMin = Integer.valueOf(mSurfaceMin.getText().toString());
        surfaceMax = Integer.valueOf(mSurfaceMax.getText().toString());

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

    @Override
    public void onDialogEntryMaxDatePikerValidateClick(DialogEntryMaxDatePicker dialog) {
        DatePicker datePicker = dialog.getDialog().findViewById(R.id.entry_date_dp);

        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        maxDateOfEntry = calendar.getTime();
        mEntryDateMaxButton.setText(Utils.formatDate(maxDateOfEntry));
    }

    @Override
    public void onDialogSoldMaxDatePikerValidateClick(DialogSoldMaxDatePicker dialog) {
        DatePicker datePicker = dialog.getDialog().findViewById(R.id.entry_date_dp);

        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        maxDateOfSale = calendar.getTime();
        mSoldDateMaxButton.setText(Utils.formatDate(maxDateOfSale));
    }
}
