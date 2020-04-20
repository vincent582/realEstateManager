package com.openclassrooms.realestatemanager.UI.Fragment.ListProperties;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.realestatemanager.Database.RealEstateManagerDataBase;
import com.openclassrooms.realestatemanager.Injection.Injection;
import com.openclassrooms.realestatemanager.Injection.ViewModelFactory;
import com.openclassrooms.realestatemanager.Model.Address;
import com.openclassrooms.realestatemanager.Model.FullProperty;
import com.openclassrooms.realestatemanager.Model.Property;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.UI.Activities.DetailsPropertyActivity;
import com.openclassrooms.realestatemanager.UI.Fragment.BaseFragment;
import com.openclassrooms.realestatemanager.UI.Fragment.DetailsProperty.DetailsPropertyFragment;

import java.util.List;
import java.util.concurrent.Executor;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.openclassrooms.realestatemanager.UI.Activities.DetailsPropertyActivity.PROPERTY_ID_EXTRA;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListPropertiesFragment extends BaseFragment implements PropertiesViewHolder.OnPropertyListener {

    @BindView(R.id.list_properties_recycler_view)
    RecyclerView mRecyclerView;

    public interface sendPropertyIdToMainActivityOnClickListener{
        void callbackPropertyId(long propertyId);
    }

    private sendPropertyIdToMainActivityOnClickListener mListener;

    private PropertiesRecyclerViewAdapter mAdapter;
    private List<FullProperty> mListProperties;
    private boolean twoPanes;

    public ListPropertiesFragment(){}

    public ListPropertiesFragment(boolean twoPanes,sendPropertyIdToMainActivityOnClickListener listener) {
        this.twoPanes = twoPanes;
        this.mListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this,view);

        configureViewModels(getContext());
        mPropertiesViewModel.getFullProperties().observe(getViewLifecycleOwner(),this::updateListProperties);

        return view;
    }

    private void updateListProperties(List<FullProperty> properties) {
        mListProperties = properties;
        configureRecyclerView();
    }

    private void configureRecyclerView() {
        mAdapter = new PropertiesRecyclerViewAdapter(mListProperties,this,getContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onPropertyClick(int propertyId) {
        if (twoPanes){
            mListener.callbackPropertyId(propertyId);
            FragmentManager fragmentManager =  getParentFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.details_activity_frame_layout,new DetailsPropertyFragment(propertyId))
                    .commit();
        }else{
            Intent intent = new Intent(getContext(), DetailsPropertyActivity.class);
            intent.putExtra(PROPERTY_ID_EXTRA, propertyId);
            startActivity(intent);
        }
        mAdapter.updateBackgroundColor(propertyId);
    }

    public void updateTwoPanesAndListenerToFragment(boolean twoPanes, sendPropertyIdToMainActivityOnClickListener listener) {
        this.twoPanes = twoPanes;
        this.mListener = listener;
    }
}
