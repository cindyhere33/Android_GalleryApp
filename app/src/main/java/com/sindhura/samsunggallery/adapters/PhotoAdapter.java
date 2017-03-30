package com.sindhura.samsunggallery.adapters;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sindhura.samsunggallery.R;
import com.sindhura.samsunggallery.utils.PhotoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sindhura on 3/29/2017.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    //Names of all the photos
    private List<String> photoFileNames = new ArrayList<>();

    //Album name
    private String albumName = "";
    private String TAG = getClass().getName();

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPic;
        TextView tvTitle;

        ViewHolder(View v) {
            super(v);
            ivPic = (ImageView) v.findViewById(R.id.ivPic);
            tvTitle = (TextView) v.findViewById(R.id.tvName);
        }
    }

    public PhotoAdapter(String albumName, List<String> photoFileNames) {
        this.photoFileNames = photoFileNames;
        this.albumName = albumName;
    }

    @Override
    public PhotoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout for photos
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_photos, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //Get compressed version of each photo from the asset folder based on album name
        Bitmap photo = PhotoUtils.getPhotoDrawable(holder.ivPic.getContext(), holder.ivPic.getContext().getResources().getString(R.string.KEY_FOLDER) + "/" + albumName + "/" + photoFileNames.get(position));

        //Set the size of the pic
        PhotoUtils.adjustSize(holder.ivPic, 350);
        if (photo != null) {
            //Set the imageview to show the photo
            holder.ivPic.setImageBitmap(photo);
        }
        //TextView displays the name of the photo
        holder.tvTitle.setText(PhotoUtils.getDisplayName(photoFileNames.get(position)));
    }


    //Get the number of photos in the album
    @Override
    public int getItemCount() {
        return photoFileNames.size();
    }

}
