package com.openclassrooms.realestatemanager.UI.Fragment.ListProperties;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.Model.Property;
import com.openclassrooms.realestatemanager.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PropertiesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.item_property_type_text_view)
    TextView mPropertyType;
    @BindView(R.id.item_property_price_text_view)
    TextView mPropertyPrice;
    @BindView(R.id.item_property_district_text_view)
    TextView mPropertyDistrict;
    @BindView(R.id.item_list_property)
    LinearLayout mItemView;

    private final Context mContext;
    private Property mProperty;

    private OnPropertyListener mOnPropertyListener;

    public interface OnPropertyListener{
        void onPropertyClick(int propertyId);
    }

    public PropertiesViewHolder(@NonNull View itemView, Context context, OnPropertyListener propertyListener) {
        super(itemView);
        this.mContext = context;
        this.mOnPropertyListener = propertyListener;
        ButterKnife.bind(this, itemView);
    }

    public void updateView(Property property, int index) {
        this.mProperty = property;
        mPropertyType.setText(property.getType());
        mPropertyPrice.setText("$"+ property.getPrice());
        mPropertyDistrict.setText(property.getAddress().getDistrict());

        mItemView.setOnClickListener(this);

        changeBackgroundColor(index);
    }

    private void changeBackgroundColor(int index) {
        if(index == mProperty.getId()){
            mItemView.setBackgroundResource(R.color.colorAccent);
            mPropertyPrice.setTextColor(ContextCompat.getColor(mContext,R.color.colorWhite));
        }else{
            mItemView.setBackgroundResource(R.color.transparent);
            mPropertyPrice.setTextColor(ContextCompat.getColor(mContext,R.color.colorAccent));
        }
    }

    @Override
    public void onClick(View v) {
        mOnPropertyListener.onPropertyClick(mProperty.getId());
    }
}
