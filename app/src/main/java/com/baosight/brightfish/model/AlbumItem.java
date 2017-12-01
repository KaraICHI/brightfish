package com.baosight.brightfish.model;


import android.graphics.Bitmap;

/**
 * Created by saitama on 2017/11/24.
 */
public class AlbumItem {
    Bitmap ablumPhoto;

    public AlbumItem() {
    }

    public AlbumItem(Bitmap ablumPhoto) {
        this.ablumPhoto = ablumPhoto;
    }

    public Bitmap getAblumPhoto() {
        return ablumPhoto;
    }

    public void setAblumPhoto(Bitmap ablumPhoto) {
        this.ablumPhoto = ablumPhoto;
    }


}
