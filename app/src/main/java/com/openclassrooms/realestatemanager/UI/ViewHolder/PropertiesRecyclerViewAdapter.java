package com.openclassrooms.realestatemanager.UI.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.Model.Property;
import com.openclassrooms.realestatemanager.R;

import java.util.List;

public class PropertiesRecyclerViewAdapter extends RecyclerView.Adapter<PropertiesViewHolder> {

    private List<Property> mListProperties;
    private boolean mTwoPanes;
    private Context mContext;

    public PropertiesRecyclerViewAdapter(Context context, boolean twoPanes, List<Property> listProperties) {
        mTwoPanes = twoPanes;
        mContext = context;
        mListProperties = listProperties;
    }

    @NonNull
    @Override
    public PropertiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_property,parent,false);
        return new PropertiesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PropertiesViewHolder holder, int position) {
        holder.updateView(mContext,mTwoPanes,mListProperties.get(position));
    }

    @Override
    public int getItemCount() {
        if (mListProperties == null){
            return 0;
        }else{
            return mListProperties.size();
        }
    }

    public void updateUi(Context context, boolean twoPanes, List<Property> properties) {
        mContext = context;
        mTwoPanes = twoPanes;
        mListProperties = properties;
        notifyDataSetChanged();
    }
}
