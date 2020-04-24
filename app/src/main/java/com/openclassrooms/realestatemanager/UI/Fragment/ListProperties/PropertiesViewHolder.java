package com.openclassrooms.realestatemanager.UI.Fragment.ListProperties;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.Model.FullProperty;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Utils.StorageUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.openclassrooms.realestatemanager.UI.Fragment.PropertyManager.PropertyManagerFragment.FOLDERNAME;

public class PropertiesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.property_list_picture_iv)
    ImageView mPictureImageView;
    @BindView(R.id.item_property_type_text_view)
    TextView mPropertyType;
    @BindView(R.id.item_property_price_text_view)
    TextView mPropertyPrice;
    @BindView(R.id.item_property_district_text_view)
    TextView mPropertyDistrict;
    @BindView(R.id.item_list_property)
    LinearLayout mItemView;

    private final Context mContext;
    private FullProperty mProperty;

    private OnPropertyListener mOnPropertyListener;

    public interface OnPropertyListener{
        void onPropertyClick(int propertyId);
    }

    //CONSTRUCTOR
    public PropertiesViewHolder(@NonNull View itemView, Context context, OnPropertyListener propertyListener) {
        super(itemView);
        this.mContext = context;
        this.mOnPropertyListener = propertyListener;
        ButterKnife.bind(this, itemView);
    }

    /**
     * Update view with property values
     * @param property
     * @param index
     */
    public void updateView(FullProperty property, int index) {
        this.mProperty = property;
        //get first image of property image list if not empty
        if (!mProperty.getPictureList().isEmpty()) {
            Bitmap bitmap = StorageUtils.getBitmapFromStorage(mContext.getFilesDir(), mContext, property.getPictureList().get(0).getFile(), FOLDERNAME);
            mPictureImageView.setImageBitmap(bitmap);
        }
        mPropertyType.setText(property.getProperty().getType());
        mPropertyPrice.setText("$"+ String.format("%,d", property.getProperty().getPrice()));
        mPropertyDistrict.setText(property.getAddress().getDistrict());
        mItemView.setOnClickListener(this);
        changeBackgroundColor(index);
    }

    private void changeBackgroundColor(int index) {
        if(index == mProperty.getProperty().getId()){
            mItemView.setBackgroundResource(R.color.primaryLightColor);
            mPropertyPrice.setTextColor(ContextCompat.getColor(mContext,R.color.secondaryLightColor));
            mPropertyType.setTextColor(ContextCompat.getColor(mContext,R.color.primaryTextColor));
            mPropertyDistrict.setTextColor(ContextCompat.getColor(mContext,R.color.primaryTextColor));
        }else{
            mItemView.setBackgroundResource(R.color.primaryTextColor);
            mPropertyPrice.setTextColor(ContextCompat.getColor(mContext,R.color.secondaryDarkColor));
            mPropertyType.setTextColor(ContextCompat.getColor(mContext,R.color.primaryDarkColor));
            mPropertyDistrict.setTextColor(ContextCompat.getColor(mContext,R.color.primaryDarkColor));
        }
    }

    @Override
    public void onClick(View v) {
        mOnPropertyListener.onPropertyClick(mProperty.getProperty().getId());
    }
}
