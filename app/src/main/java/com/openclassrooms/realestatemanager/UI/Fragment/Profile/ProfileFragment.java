package com.openclassrooms.realestatemanager.UI.Fragment.Profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.openclassrooms.realestatemanager.Model.User;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.UI.Fragment.BaseFragment;
import com.openclassrooms.realestatemanager.Utils.DialogAuthentication;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT;
import static com.openclassrooms.realestatemanager.UI.Activities.BaseActivity.PREFERENCES_NAME;

public class ProfileFragment extends BaseFragment implements DialogAuthentication.DialogAuthenticationListener{

    public interface ConnectionCallback{
        void onConnectionManagement();
    }

    @BindView(R.id.picture_user_iv) ImageView mUserImageView;
    @BindView(R.id.user_name_tv) TextView mUserTextView;
    @BindView(R.id.connection_btn) Button logInBtn;
    @BindView(R.id.logout_btn) Button logOutBtn;

    public static String CURRENT_USER_ID = "CURRENT_USER_ID";

    private User mCurrentUser;
    private SharedPreferences preferences;

    private ConnectionCallback mCallback;

    public ProfileFragment(){}

    //CONSTRUCTOR
    public ProfileFragment(ConnectionCallback connectionCallback) {
        this.mCallback = connectionCallback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this,view);
        preferences = getActivity().getSharedPreferences(PREFERENCES_NAME,Context.MODE_PRIVATE);
        configureViewModels(getContext());
        checkIfUser();
        return view;
    }

    /**
     * Manage on click SignIn button
     */
    @OnClick(R.id.connection_btn)
    public void submit() {
        configureSignIn();
    }

    /**
     * Manage on click SignOut button
     * Clear SharedPrefrences
     */
    @OnClick(R.id.logout_btn)
    public void logout(){
        preferences.edit().clear().apply();
        mCurrentUser = null;
        onResume();
        Snackbar.make(getView(),"You've been disconnected", LENGTH_SHORT).show();
        mCallback.onConnectionManagement();
    }

    /**
     * Check if user saved in SharedPreferences
     */
    public void checkIfUser(){
        long userId = preferences.getLong(CURRENT_USER_ID,0);
        mUserViewModel.getUserById(userId).observe(getActivity(),user -> {
            mCurrentUser = user;
            updateView();
        });
    }

    /**
     * Update view with userInformation
     */
    private void updateView() {
        if (mCurrentUser != null) {
            mUserImageView.setVisibility(View.VISIBLE);
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

    /**
     * Show dialogAuthentication
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
        mUserViewModel.getUser(userName,password).observe(this,this::getConnectedUser);
    }

    /**
     * After connection edit preferences user_id
     * and update View
     * @param user
     */
    private void getConnectedUser(User user) {
        if (user != null){
            mCurrentUser = user;
            preferences.edit().putLong(CURRENT_USER_ID, user.getUid()).apply();
            onResume();
            mCallback.onConnectionManagement();
            Snackbar.make(getView(),"Successful authentication", LENGTH_SHORT).show();
        }else{
            Snackbar.make(getView(),"Authentication failed", LENGTH_SHORT).show();
        }
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
            Snackbar.make(getView(),"Registration failed, you didn't fill all fields.", LENGTH_SHORT).show();
        }else {
            User user = new User(userName, password);
            mUserViewModel.createUser(user).observe(this,this::getNewUserId);
        }
    }

    /**
     * Get New User_id
     * @param id
     */
    private void getNewUserId(Integer id) {
        mUserViewModel.getUserById(id).observe(this,this::connectNewUser);
    }

    /**
     * After Registration, edit preferences user_id and update View
     * @param user
     */
    private void connectNewUser(User user) {
        Log.e("TAG", "connectNewUser: "+user );
        if (user != null){
            preferences.edit().putLong(CURRENT_USER_ID, user.getUid()).apply();
            mCurrentUser = user;
            mCallback.onConnectionManagement();
            Snackbar.make(getView(),"Successful Registration", LENGTH_SHORT).show();
            onResume();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        checkIfUser();
    }
}
