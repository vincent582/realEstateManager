package com.openclassrooms.realestatemanager.UI.ViewModels;

import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.Model.Picture;
import com.openclassrooms.realestatemanager.Repository.PictureRepository;

import java.util.concurrent.Executor;

public class PictureViewModel extends ViewModel {

    private PictureRepository mPictureRepository;
    private Executor mExecutor;

    public PictureViewModel(PictureRepository pictureRepository, Executor executor) {
        mPictureRepository = pictureRepository;
        mExecutor = executor;
    }

    public void createPicture(Picture picture){
        mExecutor.execute(() -> {
            this.mPictureRepository.createPicture(picture);
        });
    }
}
