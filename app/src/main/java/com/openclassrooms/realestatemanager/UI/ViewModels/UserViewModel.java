package com.openclassrooms.realestatemanager.UI.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.Model.User;
import com.openclassrooms.realestatemanager.Repository.UserRepository;

import java.util.concurrent.Executor;

public class UserViewModel extends ViewModel {

    private final UserRepository mUserRepository;
    private final Executor mExecutor;

    public UserViewModel(UserRepository userRepository, Executor executor) {
        mUserRepository = userRepository;
        mExecutor = executor;
    }

    public LiveData<User> getUser(String name, String password){
        return mUserRepository.getUser(name,password);
    }

    public void createUser(User user){
        mExecutor.execute(()-> mUserRepository.createUser(user));
    }
}
