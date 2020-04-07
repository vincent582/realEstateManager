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

public class DialogAuthentication extends DialogFragment {

    public interface DialogAuthenticationListener{
        void onDialogAuthenticationSignInClick(DialogAuthentication dialogAuthenticationEmail);
        void onDialogAuthenticationRegisterClick(DialogAuthentication dialogAuthenticationEmail);
    }

    DialogAuthenticationListener mListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_authentication,null))
                .setPositiveButton("Sign In", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onDialogAuthenticationSignInClick(DialogAuthentication.this);
                    }
                })
                .setNegativeButton("create account", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onDialogAuthenticationRegisterClick(DialogAuthentication.this);
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mListener = (DialogAuthenticationListener) getActivity();
        }catch (ClassCastException e){
            throw new ClassCastException(getActivity().toString()+" must implement DialogAuthenticationListener");
        }
    }
}
