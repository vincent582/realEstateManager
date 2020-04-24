package com.openclassrooms.realestatemanager.Repository;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.Database.DAO.PictureDAO;
import com.openclassrooms.realestatemanager.Model.Picture;

import java.util.List;

public class PictureRepository {

    private PictureDAO mPictureDAO;

    //CONSTRUCTOR
    public PictureRepository(PictureDAO mPictureDAO) {
        this.mPictureDAO = mPictureDAO;
    }

    /**
     * Add picture in Database
     * @param picture
     */
    public void createPicture(Picture picture){
        mPictureDAO.insertPicture(picture);
    }

    /**
     * Get All pictures associate to a property by is Id
     * @param id
     * @return List of Picture
     */
    public LiveData<List<Picture>> getPictureOfPropertyById(Integer id){
        return mPictureDAO.getPropertyById(id);
    }

    /**
     * Delete the picture passed in parameter
     * @param picture
     */
    public void deletePicture(Picture picture){
        mPictureDAO.deletePicture(picture);
    }
}
