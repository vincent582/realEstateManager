package com.openclassrooms.realestatemanager.UI.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.Model.User;
import com.openclassrooms.realestatemanager.Repository.UserRepository;

import java.util.concurrent.Executor;

public class UserViewModel extends ViewModel {

    private final UserRepository mUserRepository;
    private final Executor mExecutor;
    private LiveData<User> mCurrentUser;

    public UserViewModel(UserRepository userRepository, Executor executor) {
        mUserRepository = userRepository;
        mExecutor = executor;
    }

    public LiveData<User> getCurrentUser(){
        if (mCurrentUser == null){
            return new MutableLiveData<>();
        }
        return mCurrentUser;
    }

    public LiveData<User> getUser(String name, String password){
        mCurrentUser = mUserRepository.getUser(name,password);
        return mCurrentUser;
    }

    public LiveData<User> getUserById(long id){
        mCurrentUser = mUserRepository.getUser(id);
        return mCurrentUser;
    }

    public void createUser(User user){
        mExecutor.execute(()-> {
             mUserRepository.createUser(user);
        });
    }
}
