package com.openclassrooms.realestatemanager.UI.Fragment.Profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.openclassrooms.realestatemanager.Injection.Injection;
import com.openclassrooms.realestatemanager.Injection.ViewModelFactory;
import com.openclassrooms.realestatemanager.Model.User;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.UI.ViewModels.UserViewModel;
import com.openclassrooms.realestatemanager.Utils.DialogAuthentication;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT;

public class ProfileFragment extends Fragment implements DialogAuthentication.DialogAuthenticationListener{

    @BindView(R.id.picture_user_iv)
    ImageView mUserImageView;
    @BindView(R.id.user_name_tv)
    TextView mUserTextView;
    @BindView(R.id.connection_btn)
    Button logInBtn;
    @BindView(R.id.logout_btn)
    Button logOutBtn;

    private UserViewModel mUserViewModel;
    private ViewModelFactory modelFactory;
    private User mCurrentUser;
    private SharedPreferences preferences;

    public static String CURRENT_USER_ID = "CURRENT_USER_ID";

    public interface ConnectionCallback{
        void onConnectionManagement();
    }

    private ConnectionCallback mCallback;

    public ProfileFragment(ConnectionCallback connectionCallback) {
        this.mCallback = connectionCallback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this,view);
        preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        configureViewModel();
        checkIfUser();
        return view;
    }

    /**
     * Configure ViewModel to get UserViewModel instance with factory
     */
    private void configureViewModel() {
        modelFactory = Injection.provideViewModelFactory(getActivity());
        mUserViewModel = new ViewModelProvider(this, modelFactory).get(UserViewModel.class);
    }

    public void checkIfUser(){
        long userId = preferences.getLong(CURRENT_USER_ID,0);
        mUserViewModel.getUserById(userId).observe(getActivity(),user -> {
            mCurrentUser = user;
            updateView();
        });
    }

    private void updateView() {
        if (mCurrentUser != null) {
            Glide.with(this)
                .load(getResources().getIdentifier("no_picture_user", "drawable", getActivity().getPackageName()))
                .centerCrop()
                .into(mUserImageView);

            mUserTextView.setText(mCurrentUser.getName());
            mUserImageView.setVisibility(View.VISIBLE);
            logInBtn.setVisibility(View.GONE);
            logOutBtn.setVisibility(View.VISIBLE);
        }else {
            mUserImageView.setVisibility(View.GONE);
            mUserTextView.setText("No user connected !!!");
            logInBtn.setVisibility(View.VISIBLE);
            logOutBtn.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.connection_btn)
    public void submit() {
        configureSignIn();
    }

    @OnClick(R.id.logout_btn)
    public void logout(){
        preferences.edit().clear().apply();
        mCurrentUser = null;
        updateView();
        Snackbar.make(getView(),"You've been disconnected", LENGTH_SHORT).show();
        mCallback.onConnectionManagement();
    }

    /**
     * Configure sign-in
     */
    private void configureSignIn() {
        DialogAuthentication dialog = new DialogAuthentication();
        dialog.setTargetFragment(this,1);
        dialog.show(getParentFragmentManager(),"DialogAuthentication");
    }

    /**
     * Get Dialog answer to Sign-in request.
     * @param dialogAuthentication
     */
    @Override
    public void onDialogAuthenticationSignInClick(DialogAuthentication dialogAuthentication) {
        EditText editTextUserName = dialogAuthentication.getDialog().findViewById(R.id.name_user_editText);
        String userName = editTextUserName.getText().toString();
        EditText editTextPassword = dialogAuthentication.getDialog().findViewById(R.id.password_editText);
        String password = editTextPassword.getText().toString();

        mUserViewModel.getUser(userName,password).observe(this,this::getuser);
    }

    /**
     * Get Dialog answer to register request.
     * @param dialogAuthentication
     */
    @Override
    public void onDialogAuthenticationRegisterClick(DialogAuthentication dialogAuthentication) {
        EditText editTextUserName = dialogAuthentication.getDialog().findViewById(R.id.name_user_editText);
        String userName = editTextUserName.getText().toString();
        EditText editTextPassword = dialogAuthentication.getDialog().findViewById(R.id.password_editText);
        String password = editTextPassword.getText().toString();
        if (userName.isEmpty() || password.isEmpty()){
            Snackbar.make(getView(),"Registration failed", LENGTH_SHORT).show();
        }else {
            User user = new User(userName, password);
            mUserViewModel.createUser(user);
            //TODO create user in db and conect it
            Snackbar.make(getView(), "Successful registration", LENGTH_SHORT).show();
        }
    }

    private void getuser(User user) {
        if (user != null){
            mCurrentUser = user;
            preferences.edit().putLong(CURRENT_USER_ID, user.getUid()).apply();
            updateView();
            mCallback.onConnectionManagement();
            Snackbar.make(getView(),"Successful authentication", LENGTH_SHORT).show();
        }else{
            Snackbar.make(getView(),"Authentication failed", LENGTH_SHORT).show();
        }
    }
}
