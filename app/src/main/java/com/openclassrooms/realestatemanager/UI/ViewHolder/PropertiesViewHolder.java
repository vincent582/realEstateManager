package com.openclassrooms.realestatemanager.UI.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.UI.Activities.DetailsPropertyActivity;
import com.openclassrooms.realestatemanager.UI.Fragment.DetailsPropertyFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PropertiesViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_list_property)
    LinearLayout mItemView;

    public PropertiesViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateView(Context context, boolean twoPanes, int position) {
        mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (twoPanes){
                    FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.details_activity_frame_layout,new DetailsPropertyFragment(position))
                            .commit();
                }else{
                    Intent intent = new Intent(v.getContext(), DetailsPropertyActivity.class);
                    v.getContext().startActivity(intent);
                }
            }
        });
    }
}
