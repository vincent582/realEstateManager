package com.openclassrooms.realestatemanager.UI.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;

public class PropertiesRecyclerViewAdapter extends RecyclerView.Adapter<PropertiesViewHolder> {

    private boolean mTwoPanes;
    private Context mContext;

    public PropertiesRecyclerViewAdapter(Context context, boolean twoPanes) {
        mTwoPanes = twoPanes;
        mContext = context;
    }

    @NonNull
    @Override
    public PropertiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_property,parent,false);
        return new PropertiesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PropertiesViewHolder holder, int position) {
        holder.updateView(mContext,mTwoPanes,position);
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
