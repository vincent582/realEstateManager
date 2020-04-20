package com.openclassrooms.realestatemanager.UI.Fragment.ListProperties;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.Model.Address;
import com.openclassrooms.realestatemanager.Model.FullProperty;
import com.openclassrooms.realestatemanager.Model.Property;
import com.openclassrooms.realestatemanager.R;

import java.util.List;

public class PropertiesRecyclerViewAdapter extends RecyclerView.Adapter<PropertiesViewHolder> {

    private final Context mContext;
    private List<FullProperty> mListProperties;
    private PropertiesViewHolder.OnPropertyListener mPropertyListener;
    private int index = 0;

    public PropertiesRecyclerViewAdapter(List<FullProperty> listProperties, PropertiesViewHolder.OnPropertyListener propertyListener, Context context) {
        this.mListProperties = listProperties;
        this.mPropertyListener = propertyListener;
        this.mContext = context;
    }

    @NonNull
    @Override
    public PropertiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_property,parent,false);
        return new PropertiesViewHolder(view,mContext,mPropertyListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PropertiesViewHolder holder, int position) {
        holder.updateView(mListProperties.get(position),index);
    }

    @Override
    public int getItemCount() {
        if (mListProperties == null){
            return 0;
        }else{
            return mListProperties.size();
        }
    }

    public void updateBackgroundColor(int propertyId){
        for (FullProperty property: mListProperties) {
            if (property.getProperty().getId() == propertyId){
                index = propertyId;
                notifyDataSetChanged();
            }
        }
    }
}
