package com.openclassrooms.realestatemanager.Utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.openclassrooms.realestatemanager.R;

public class DialogSelectPictureFrom extends DialogFragment {

    public interface DialogSelectListener{
        void onDialogSelectedClick(DialogSelectPictureFrom dialogSelectPictureFrom);
    }

    DialogSelectListener mListener;

    public DialogSelectPictureFrom(){}

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_select_picture_from,null);
        builder.setView(view)
            .setPositiveButton("Go", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mListener.onDialogSelectedClick(DialogSelectPictureFrom.this);
                }
            });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mListener = (DialogSelectListener) getTargetFragment();
        }catch (ClassCastException e){
            throw new ClassCastException(getTargetFragment().toString()+" must implement DialogSelectListener");
        }
    }
}
