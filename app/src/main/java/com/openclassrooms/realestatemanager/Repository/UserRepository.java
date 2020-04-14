package com.openclassrooms.realestatemanager.Repository;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.Database.DAO.UserDAO;
import com.openclassrooms.realestatemanager.Model.User;

public class UserRepository {

    private UserDAO mUserDAO;

    public UserRepository(UserDAO userDAO) {
        this.mUserDAO = userDAO;
    }

    public LiveData<User> getUser(String name,String password){
        return mUserDAO.getUser(name,password);
    }

    public long createUser(User user){
        return mUserDAO.createUser(user);
    }

    public LiveData<User> getUser(long id) {
        return mUserDAO.getUserById(id);
    }
}
