package com.openclassrooms.realestatemanager.UI.Fragment.PropertyManager;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.openclassrooms.realestatemanager.Dummy.Dummy;
import com.openclassrooms.realestatemanager.Model.Address;
import com.openclassrooms.realestatemanager.Model.Property;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.UI.Fragment.BaseFragment;
import com.openclassrooms.realestatemanager.Utils.NotificationService;
import com.openclassrooms.realestatemanager.Utils.StorageUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;
import static com.openclassrooms.realestatemanager.UI.Activities.DetailsPropertyActivity.PROPERTY_ID_EXTRA_FOR_PROPERTY_MANAGER;

/**
 * A simple {@link Fragment} subclass.
 */
public class PropertyManagerFragment extends BaseFragment{

    private static final String FOLDERNAME = "RealEstateManager_images";

    @BindView(R.id.display_picture_linear_layout) LinearLayout mDisplayPicturesLinearLayout;
    @BindView(R.id.add_picture_iv) ImageView mPictureImageView;
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

    //For Property
    private Integer mPropertyId;
    private Property mProperty;
    private String mType;
    private Integer mPrice;
    private String mDescription;
    private Integer mSurface;
    private Integer mNbrOfRoom;
    //For Property Address
    private Address mPropertyAddress;
    private Integer mAddressStreetNbr;
    private String mAddressStreet;
    private String mAddressDistrict;
    private String mAddressState;
    private Integer mAddressPostCode;
    private String mAddressCountry;

    private static final String PERMS = Manifest.permission.READ_EXTERNAL_STORAGE;
    private static final int RC_IMAGE_PERMS = 200;
    private Uri uriImageSelected;
    private static final int RC_CHOOSE_PHOTO = 300;

    //CONSTRUCTOR
    public PropertyManagerFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_property_manager, container, false);
        ButterKnife.bind(this,view);
        checkIfArguments();
        configureViewModels(getContext());
        if (mPropertyId != null){
            mPropertiesViewModel.getPropertyById(mPropertyId).observe(getActivity(),this::updateProperty);
        }
        configurePropertyTypeSpinner();
        setAddPropertyButtonOnclickListener();
        setAddPictureClickListener();

        Bitmap bitmap = StorageUtils.getBitmapFromStorage(getActivity().getFilesDir(),getContext(),"image_8964.jpg",FOLDERNAME);
        displayPropertyPictures(bitmap);

        return view;
    }


    private void setAddPictureClickListener() {
        mPictureImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAddFile();
            }
        });
    }

    @AfterPermissionGranted(RC_IMAGE_PERMS)
    public void onClickAddFile() {
        if (!EasyPermissions.hasPermissions(getContext(), PERMS)) {
            EasyPermissions.requestPermissions(this, "We need permission to access to your pictures", RC_IMAGE_PERMS, PERMS);
            return;
        }
        // 3 - Launch an "Selection Image" Activity
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RC_CHOOSE_PHOTO);
    }


    /**
     * Check if the Fragment received arguments
     */
    private void checkIfArguments() {
        Bundle args = getArguments();
        if (!args.isEmpty()){
            mPropertyId = getArguments().getInt(PROPERTY_ID_EXTRA_FOR_PROPERTY_MANAGER);
        }
    }

    private void updateProperty(Property property) {
        this.mProperty = property;
        mAddressViewModel.getAddressOfProperty(mProperty.getId()).observe(getActivity(),this::getAddressOfProperty);
    }

    private void getAddressOfProperty(Address address) {
        this.mPropertyAddress = address;
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

        if (mPropertyAddress != null) {
            mPropertyAddressStreetNumber.setText(String.valueOf(mPropertyAddress.getNumber()));
            mPropertyAddressStreet.setText(mPropertyAddress.getStreet());
            mPropertyAddressDistrict.setText(mPropertyAddress.getDistrict());
            mPropertyAddressState.setText(mPropertyAddress.getState());
            mPropertyAddressPostCode.setText(String.valueOf(mPropertyAddress.getPostCode()));
            mPropertyAddressCountry.setText(mPropertyAddress.getCountry());
        }
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
                        setValuesOfPropertyAndUpdate();
                        setValuesOfAddressAndUpdate();
                    }else {
                        createNewPropertyAndAddress();
                    }
                    getActivity().finish();
                }
            }
        });
    }

    private void createNewPropertyAndAddress() {
        Property property = new Property(mType, mPrice, mSurface, mNbrOfRoom,
                mDescription, false, new Date(), new Date());
        mPropertiesViewModel.createProperty(property).observe(getViewLifecycleOwner(),this::createAddress);
    }

    private void createAddress(Integer idProperty) {
        Address address = new Address(idProperty, mAddressStreetNbr, mAddressStreet, null, mAddressDistrict,
                mAddressState, mAddressPostCode, mAddressCountry);
        mAddressViewModel.createAddress(address);
        showConfirmationMessage("added");
    }

    private void setValuesOfAddressAndUpdate() {
        mPropertyAddress.setNumber(mAddressStreetNbr);
        mPropertyAddress.setStreet(mAddressStreet);
        mPropertyAddress.setDistrict(mAddressDistrict);
        mPropertyAddress.setState(mAddressState);
        mPropertyAddress.setPostCode(mAddressPostCode);
        mPropertyAddress.setCountry(mAddressCountry);

        mAddressViewModel.updateAddress(mPropertyAddress);
        showConfirmationMessage("updated");
    }

    private void setValuesOfPropertyAndUpdate() {
        mProperty.setType(mType);
        mProperty.setPrice(mPrice);
        mProperty.setSurface(mSurface);
        mProperty.setNbrOfRooms(mNbrOfRoom);
        mProperty.setDescription(mDescription);
        //mProperty.setListFacilities(new ArrayList<>());
        //TODO getValue of checkbox sold
        mProperty.setSold(false);
        mProperty.setDateOfSale(new Date());

        mPropertiesViewModel.updateProperty(mProperty);
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

    private void showConfirmationMessage(String message){
        String messageText = "The property "+mProperty.getType()+" at the "+ mPropertyAddress.getFormatedAddress() +" was successfully "+message+"!";
        NotificationService notificationService = new NotificationService(getContext());
        notificationService.sendNotification(1,messageText);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_CHOOSE_PHOTO) {
            if (resultCode == RESULT_OK) { //SUCCESS
                this.uriImageSelected = data.getData();

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uriImageSelected);
                    List<Bitmap> picturesList = new ArrayList<>();
                    picturesList.add(bitmap);
                    displayPropertyPictures(bitmap);
                    SaveImage(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void displayPropertyPictures(Bitmap bitmap) {
        ImageView image = new ImageView(getContext());
        image.setImageBitmap(bitmap);
        image.setLayoutParams(new RelativeLayout.LayoutParams((int) getResources().getDimension(R.dimen.item_property_image_size), (int) getResources().getDimension(R.dimen.item_property_image_size)));
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(image.getLayoutParams());
        lp.setMargins(10, 0, 10, 0);
        image.setLayoutParams(lp);
        image.setScaleType(ImageView.ScaleType.FIT_XY);
        mDisplayPicturesLinearLayout.addView(image);
    }

    private void SaveImage(Bitmap finalBitmap) {
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String imageName = "image_"+ n +".jpg";

        StorageUtils.setBitmapInStorage(getActivity().getFilesDir(),getContext(),imageName,FOLDERNAME,finalBitmap);
    }
}
