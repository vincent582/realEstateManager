package com.openclassrooms.realestatemanager.UI.Fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.openclassrooms.realestatemanager.Model.Property;
import com.openclassrooms.realestatemanager.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsPropertyFragment extends Fragment {

    @BindView(R.id.details_layout_fragment)
    ScrollView mDetailsLayout;

    @BindView(R.id.textView)
    TextView mTextView;

    private Property mProperty;

    public DetailsPropertyFragment() {
        // Required empty public constructor
    }

    public DetailsPropertyFragment(Property property) {
        mProperty = property;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details_property, container, false);
        ButterKnife.bind(this,view);

        if (mProperty != null){
            mTextView.setText(mProperty.getType());
        }else{
            mDetailsLayout.setVisibility(View.GONE);
        }

        return view;
    }

}
