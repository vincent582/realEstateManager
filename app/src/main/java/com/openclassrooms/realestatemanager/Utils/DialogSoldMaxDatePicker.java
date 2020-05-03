package com.openclassrooms.realestatemanager.Utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.openclassrooms.realestatemanager.R;

public class DialogSoldMaxDatePicker extends DialogFragment {

    public interface DialogSoldMaxDatePickerListener {
        public void onDialogSoldMaxDatePikerValidateClick(DialogSoldMaxDatePicker dialogSoldMaxDatePickerFragment);
    }

    DialogSoldMaxDatePickerListener mListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_date_picker, null))
            .setMessage("Date of entry in marker")
            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mListener.onDialogSoldMaxDatePikerValidateClick(DialogSoldMaxDatePicker.this);
                }
            })
            .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (DialogSoldMaxDatePickerListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement DialogSoldMaxDatePickerListener");
        }
    }
}
