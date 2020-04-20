package com.openclassrooms.realestatemanager.UI.Fragment.PropertyManager;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;
import com.openclassrooms.realestatemanager.Dummy.Dummy;
import com.openclassrooms.realestatemanager.Model.Address;
import com.openclassrooms.realestatemanager.Model.FullProperty;
import com.openclassrooms.realestatemanager.Model.Picture;
import com.openclassrooms.realestatemanager.Model.Property;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.UI.Fragment.BaseFragment;
import com.openclassrooms.realestatemanager.Utils.DialogDeleteImage;
import com.openclassrooms.realestatemanager.Utils.DialogImagePreview;
import com.openclassrooms.realestatemanager.Utils.DialogSelectPictureFrom;
import com.openclassrooms.realestatemanager.Utils.StorageUtils;

import java.io.IOException;
import java.util.AbstractList;
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
public class PropertyManagerFragment extends BaseFragment implements DialogImagePreview.DialogImagePreviewListener, PicturesViewHolder.ListenerPictureClick , DialogDeleteImage.DialogDeleteListener , DialogSelectPictureFrom.DialogSelectListener {

    @BindView(R.id.recycler_view_pictures) RecyclerView mPicturesRecyclerView;
    @BindView(R.id.add_picture_iv) ImageView mAddPictureImageView;
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

    //If PropertyId get FullProperty
    private Integer mPropertyId;
    private FullProperty mFullProperty;

    //Values of editText
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

    //Folder to Stock image on internalStorage
    public static final String FOLDERNAME = "RealEstateManager_images";
    private static final String PERMS = Manifest.permission.READ_EXTERNAL_STORAGE;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int RC_IMAGE_PERMS = 200;
    private static final int RC_CHOOSE_PHOTO = 300;
    private PicturesRecyclerViewAdapter mAdapter;
    private Bitmap bitmap;
    private List<Picture> mListPictures = new ArrayList<>();
    private Picture pictureTodelete;
    private List<Picture> mListPicturesToDelete = new ArrayList<>();

    //CONSTRUCTOR
    public PropertyManagerFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_property_manager, container, false);
        ButterKnife.bind(this,view);
        configureViewModels(getContext());
        configureRecyclerView();
        checkIfArguments();
        configurePropertyTypeSpinner();
        setAddPictureClickListener();
        setAddPropertyButtonOnclickListener();
        return view;
    }

    private void configureRecyclerView() {
        mAdapter = new PicturesRecyclerViewAdapter(getContext(),this);
        mPicturesRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mPicturesRecyclerView.setLayoutManager(layoutManager);
    }

    /**
     * Check if the Fragment received arguments
     * Get The PropertyById and populate editText
     */
    private void checkIfArguments() {
        Bundle args = getArguments();
        if (!args.isEmpty()){
            mPropertyId = getArguments().getInt(PROPERTY_ID_EXTRA_FOR_PROPERTY_MANAGER);
            if (mPropertyId != null){
                mPropertiesViewModel.getPropertyById(mPropertyId).observe(getActivity(),this::populateEditItemWithPropertyValues);
            }
        }
    }

    /**
     * Populate all Edit text with value of Property
     * @param fullProperty
     */
    private void populateEditItemWithPropertyValues(FullProperty fullProperty) {
        this.mFullProperty = fullProperty;

        mListPictures = fullProperty.getPictureList();
        mAdapter.updateListPictures(mListPictures);

        //TODO set defaultvalue of spinner
        mPropertyTypeSpinner.setSelection(Dummy.propertyType.indexOf(fullProperty.getProperty().getType()));
        mPropertyPrice.setText(String.valueOf(fullProperty.getProperty().getPrice()));
        mPropertyDescription.setText(fullProperty.getProperty().getDescription());
        mPropertySurface.setText(String.valueOf(fullProperty.getProperty().getSurface()));
        mPropertyNbrOfRooms.setText(String.valueOf(fullProperty.getProperty().getNbrOfRooms()));
        //TODO add facilities

        mPropertyAddressStreetNumber.setText(String.valueOf(mFullProperty.getAddress().getNumber()));
        mPropertyAddressStreet.setText(mFullProperty.getAddress().getStreet());
        mPropertyAddressDistrict.setText(mFullProperty.getAddress().getDistrict());
        mPropertyAddressState.setText(mFullProperty.getAddress().getState());
        mPropertyAddressPostCode.setText(String.valueOf(mFullProperty.getAddress().getPostCode()));
        mPropertyAddressCountry.setText(mFullProperty.getAddress().getCountry());

        //TODO add isSold boolean
        mAddPropertyButton.setText("Update Property");
    }

    /**
     * Populate Spinner to choose type of Property From List
     */
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

    /**
     * Manage Click on add picture Button
     */
    private void setAddPictureClickListener() {
        mAddPictureImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogToChoosePicture();
            }
        });
    }

    private void showDialogToChoosePicture() {
        DialogSelectPictureFrom dialog = new DialogSelectPictureFrom();
        dialog.setTargetFragment(this,1);
        dialog.show(getParentFragmentManager(),"DialogSelectPictureFrom");
    }

    @Override
    public void onDialogSelectedClick(DialogSelectPictureFrom dialog) {
        RadioButton albumCheckBox = dialog.getDialog().findViewById(R.id.from_album);
        RadioButton cameraCheckBox = dialog.getDialog().findViewById(R.id.from_camera);
        if (albumCheckBox.isChecked()){
            dispachChoosePictureFromAlbum();
        }
        if (cameraCheckBox.isChecked()){
            dispatchTakePictureIntent();
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    /**
     * Check if we have permission to have access to pictures
     * Start Activity to pick up one picture from the phone
     */
    @AfterPermissionGranted(RC_IMAGE_PERMS)
    public void dispachChoosePictureFromAlbum() {
        if (!EasyPermissions.hasPermissions(getContext(), PERMS)) {
            EasyPermissions.requestPermissions(this, "We need permission to access to your pictures", RC_IMAGE_PERMS, PERMS);
            return;
        }
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RC_CHOOSE_PHOTO);
    }

    /**
     * On Result success to get Picture from phone
     * show bitmap file in Dialog preview
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_CHOOSE_PHOTO) {
            if (resultCode == RESULT_OK) {
                Uri uriImageSelected = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uriImageSelected);
                    showDialogImagePreview(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");
            showDialogImagePreview(bitmap);
        }
    }

    /**
     * Showing DialogPreview with a bitmap
     * @param bitmap
     */
    private void showDialogImagePreview(Bitmap bitmap) {
        DialogImagePreview dialog = new DialogImagePreview(bitmap);
        dialog.setTargetFragment(this,1);
        dialog.show(getParentFragmentManager(),"DialogImagePreview");
    }

    /**
     * Manage Click on Save button of the DialogPreview
     * Get EditText value , generate file name of picture
     * add new picture to list
     * Display pictures in RecyclerView
     * Then save picture on storage
     * @param dialogImagePreview
     */
    @Override
    public void onDialogImagePreviewSave(DialogImagePreview dialogImagePreview) {
        EditText imageDescription = dialogImagePreview.getDialog().findViewById(R.id.dialog_picture_description_et);
        String description = imageDescription.getText().toString();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String imageName = "image_"+ n +".jpg";
        Picture picture = new Picture(imageName,description);
        mListPictures.add(picture);
        mAdapter.updateListPictures(mListPictures);
        StorageUtils.setBitmapInStorage(getActivity().getFilesDir(),getContext(),imageName,FOLDERNAME,bitmap);
    }

    /**
     * Manage click on add/update property
     */
    private void setAddPropertyButtonOnclickListener() {
        mAddPropertyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValuesOfInput()){
                    if (mFullProperty != null){
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

    /**
     * Check if the values of input are not null
     * @return
     */
    private Boolean checkValuesOfInput() {
        Boolean values = true;
        if (mListPictures.isEmpty()){
            Snackbar.make(getView(),"Vous devez ajouter au moins une image",Snackbar.LENGTH_LONG).show();
            values = false;
        }
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

    /**
     * Update property Entity in RoomDatabase
     */
    private void setValuesOfPropertyAndUpdate() {
        Property property = new Property();
        property.setId(mFullProperty.mProperty.getId());
        property.setType(mType);
        property.setPrice(mPrice);
        property.setSurface(mSurface);
        property.setNbrOfRooms(mNbrOfRoom);
        property.setDescription(mDescription);
        //mProperty.setListFacilities(new ArrayList<>());
        //TODO getValue of checkbox sold
        property.setSold(false);
        property.setDateOfSale(new Date());

        mPropertiesViewModel.updateProperty(property);
        createPicturesForProperty(mFullProperty.getProperty().getId());
    }

    /**
     * get Address and update Address Entity in RoomDatabase
     */
    private void setValuesOfAddressAndUpdate() {
        mAddressViewModel.getAddressOfProperty(mFullProperty.getProperty().getId()).observe(this,this::updateAddress);
    }

    private void updateAddress(Address address) {
        address.setNumber(mAddressStreetNbr);
        address.setStreet(mAddressStreet);
        address.setDistrict(mAddressDistrict);
        address.setState(mAddressState);
        address.setPostCode(mAddressPostCode);
        address.setCountry(mAddressCountry);
        mAddressViewModel.updateAddress(address);

        deletePictureInDatabase();

        //showConfirmationMessage("updated");
    }

    /**
     * On save delete in database all the picture the user choose to delete
     */
    private void deletePictureInDatabase() {
        if (!mListPicturesToDelete.isEmpty()) {
            for (Picture pictureToDelete : mListPicturesToDelete) {
                mPictureViewModel.deletePicture(pictureToDelete);
            }
        }
    }

    /**
     * Create new Property and address related
     */
    private void createNewPropertyAndAddress() {
        Property property = new Property(mType, mPrice, mSurface, mNbrOfRoom, mDescription, false, new Date(), new Date());
        mPropertiesViewModel.createProperty(property).observe(getViewLifecycleOwner(),this::createAddress);
    }

    private void createAddress(Integer idProperty) {
        Address address = new Address(idProperty, mAddressStreetNbr, mAddressStreet, null, mAddressDistrict,
                mAddressState, mAddressPostCode, mAddressCountry);
        mAddressViewModel.createAddress(address);

        createPicturesForProperty(idProperty);
    }

    /**
     * Create Pictures in database with propertyId relation
     * @param idProperty
     */
    private void createPicturesForProperty(Integer idProperty) {
        for (Picture picture: mListPictures) {
            picture.setPropertyId(idProperty);
            mPictureViewModel.createPicture(picture);
        }
        //showConfirmationMessage("added");
    }

    /**
     * Manage click on picture
     * @param picture
     */
    @Override
    public void onClickPicture(Picture picture) {
        pictureTodelete = picture;
        Bitmap bitmap = StorageUtils.getBitmapFromStorage(getActivity().getFilesDir(),getContext(),picture.getFile(),FOLDERNAME);
        DialogDeleteImage dialog = new DialogDeleteImage(bitmap);
        dialog.setTargetFragment(this,1);
        dialog.show(getParentFragmentManager(),"DialogImagePreview");
    }

    /**
     * Manage click to delete picture from list to show
     * And add to list we will delete in database on save property
     * @param dialogDeleteImage
     */
    @Override
    public void onDialogDeleteClick(DialogDeleteImage dialogDeleteImage) {
        mListPictures.remove(pictureTodelete);
        mListPicturesToDelete.add(pictureTodelete);
        mAdapter.updateListPictures(mListPictures);
    }


    /**
     * Showing Confirmation message in notification
     * @param message
     */
    /*
    private void showConfirmationMessage(String message){
        String messageText = "The property "+ mFullProperty.getProperty().getType()+" at the "+ mFullProperty.getAddress().getFormatedAddress() +" was successfully "+message+"!";
        NotificationService notificationService = new NotificationService(getContext());
        notificationService.sendNotification(1,messageText);
    }
    */

}
