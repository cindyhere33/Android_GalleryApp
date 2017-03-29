package com.sindhura.samsunggallery.models;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sindhura on 3/29/2017.
 */

public class Album {

    List<Drawable> pictures = new ArrayList<>();
    String title = "";
    Drawable picture = null;

    public Album(List<Drawable> pictures, String title, Drawable picture) {
        this.pictures = pictures;
        this.title = title;
        this.picture = picture;
    }

    public List<Drawable> getPictures() {
        return pictures;
    }

    public void setPictures(List<Drawable> pictures) {
        this.pictures = pictures;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Drawable getPicture() {
        return picture;
    }

    public void setPicture(Drawable picture) {
        this.picture = picture;
    }
}
