package com.openclassrooms.realestatemanager.UI.Fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.realestatemanager.Model.Property;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.UI.Activities.MainActivity;
import com.openclassrooms.realestatemanager.UI.ViewHolder.PropertiesRecyclerViewAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListPropertiesFragment extends Fragment {

    @BindView(R.id.list_properties_recycler_view)
    RecyclerView mRecyclerView;

    private boolean twoPanes;
    private Context mContext;
    private PropertiesRecyclerViewAdapter mAdapter;
    private List<Property> mListProperties;


    public ListPropertiesFragment() {
        // Required empty public constructor
    }

    public ListPropertiesFragment(Context context, boolean twoPanes, List<Property> properties) {
        this.twoPanes = twoPanes;
        this.mContext = context;
        this.mListProperties = properties;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this,view);
        configureRecyclerView();
        return view;
    }

    private void configureRecyclerView() {
        mAdapter = new PropertiesRecyclerViewAdapter(mContext,twoPanes,mListProperties);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    public void updateTwoPanesAndContext(boolean twoPanes, Context context, List<Property> properties) {
        mAdapter.updateUi(context,twoPanes,properties);
    }
}
