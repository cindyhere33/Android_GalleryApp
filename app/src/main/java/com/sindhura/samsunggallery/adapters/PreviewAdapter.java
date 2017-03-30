package com.sindhura.samsunggallery.adapters;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sindhura.samsunggallery.R;
import com.sindhura.samsunggallery.utils.PhotoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sindhura on 3/29/2017.
 */

public class PreviewAdapter extends PagerAdapter {

    private Context context;
    //Album name
    private String albumName = "";

    //Names of all photos in the album
    private List<String> photoFileNames = new ArrayList<>();

    public PreviewAdapter(Context context, String albumname,  List<String> photoFileNames) {
        this.context = context;
        this.albumName = albumname;
        this.photoFileNames = photoFileNames;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);

        //Inflate the layout into the view
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.adapter_preview, collection, false);

        //Set the compressed image to show
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ((ImageView) layout.findViewById(R.id.ivPreview)).setBackground(PhotoUtils.getBackgroundDrawable(context.getResources(), PhotoUtils.getPhotoDrawable(context, context.getResources().getString(R.string.KEY_FOLDER) + "/" + albumName + "/" + photoFileNames.get(position))));
        }
        else{
            ((ImageView) layout.findViewById(R.id.ivPreview)).setBackgroundDrawable(PhotoUtils.getBackgroundDrawable(context.getResources(), PhotoUtils.getPhotoDrawable(context, context.getResources().getString(R.string.KEY_FOLDER) + "/" + albumName + "/" + photoFileNames.get(position))));
        }
        //Set the size of the image displayed
        PhotoUtils.adjustSize(layout, 0);
        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    //Get count of all the photos in the album
    @Override
    public int getCount() {
        return photoFileNames.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }



}