package com.coursera.gfil.dailyselfie;

import android.graphics.Bitmap;
import android.net.Uri;

public class SelfieImageItem {

    private String mImageName = new String();
    private Bitmap mThumbnailImage;
    private Uri mFileUri;

    SelfieImageItem(String imageName, Bitmap thumbnailImage, Uri uri) {
        this.mImageName = imageName;
        this.mThumbnailImage = thumbnailImage;
        this.mFileUri = uri;
    }

    public String getImageName() {
        return mImageName;
    }

    public Bitmap getThumbnailImage() {
        return mThumbnailImage;
    }

    public Uri getImageUri() {
        return mFileUri;
    }
}
