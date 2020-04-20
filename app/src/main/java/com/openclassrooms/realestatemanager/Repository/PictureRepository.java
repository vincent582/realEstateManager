package com.openclassrooms.realestatemanager.Repository;

import com.openclassrooms.realestatemanager.Database.DAO.PictureDAO;
import com.openclassrooms.realestatemanager.Model.Picture;

public class PictureRepository {

    private PictureDAO mPictureDAO;

    public PictureRepository(PictureDAO mPictureDAO) {
        this.mPictureDAO = mPictureDAO;
    }

    public void createPicture(Picture picture){
        mPictureDAO.insertPicture(picture);
    }
}
