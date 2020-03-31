package com.openclassrooms.realestatemanager.UI.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.Model.Property;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.UI.Activities.DetailsPropertyActivity;
import com.openclassrooms.realestatemanager.UI.Fragment.DetailsPropertyFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.openclassrooms.realestatemanager.UI.Activities.DetailsPropertyActivity.PROPERTY_ID;

public class PropertiesViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_property_type_text_view)
    TextView mPropertyType;
    @BindView(R.id.item_property_price_text_view)
    TextView mPropertyPrice;
    @BindView(R.id.item_property_district_text_view)
    TextView mPropertyDistrict;
    @BindView(R.id.item_list_property)
    LinearLayout mItemView;

    public PropertiesViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateView(Context context, boolean twoPanes, Property property) {

        mPropertyType.setText(property.getType());
        mPropertyPrice.setText("$"+ property.getPrice());
        mPropertyDistrict.setText(property.getAddress().getDistrict());

        mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (twoPanes){
                    FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.details_activity_frame_layout,new DetailsPropertyFragment(property))
                            .commit();
                }else{
                    Intent intent = new Intent(v.getContext(), DetailsPropertyActivity.class);
                    intent.putExtra(PROPERTY_ID,property.getId());
                    v.getContext().startActivity(intent);
                }
            }
        });
    }
}
