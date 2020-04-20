package com.openclassrooms.realestatemanager.Repository;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.Database.DAO.PictureDAO;
import com.openclassrooms.realestatemanager.Model.Picture;

import java.util.List;

public class PictureRepository {

    private PictureDAO mPictureDAO;

    public PictureRepository(PictureDAO mPictureDAO) {
        this.mPictureDAO = mPictureDAO;
    }

    public void createPicture(Picture picture){
        mPictureDAO.insertPicture(picture);
    }

    public LiveData<List<Picture>> getPictureOfPropertyById(Integer id){
        return mPictureDAO.getPropertyById(id);
    }

    public void deletePicture(Picture picture){
        mPictureDAO.deletePicture(picture);
    }
}
