package com.openclassrooms.realestatemanager.UI.Fragment.PropertyManager;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.Model.Picture;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.Utils.StorageUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.openclassrooms.realestatemanager.UI.Fragment.PropertyManager.PropertyManagerFragment.FOLDERNAME;

public class PicturesViewHolder extends RecyclerView.ViewHolder {

    private final Context mContext;
    @BindView(R.id.property_picture_item)
    ImageView mPropertyPictureImageView;
    @BindView(R.id.property_picture_description)
    TextView mPropertyPictureDescriptionTextView;

    public PicturesViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.mContext = context;
        ButterKnife.bind(this, itemView);
    }

    public void updateView(Picture picture) {
        Bitmap bitmap = StorageUtils.getBitmapFromStorage(mContext.getFilesDir(),mContext,picture.getFile(),FOLDERNAME);
        mPropertyPictureImageView.setImageBitmap(bitmap);
        mPropertyPictureDescriptionTextView.setText(picture.getDescription());
    }
}
