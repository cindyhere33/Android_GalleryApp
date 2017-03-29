package com.sindhura.samsunggallery.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sindhura.samsunggallery.R;
import com.sindhura.samsunggallery.utils.PhotoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sindhura on 3/29/2017.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    private List<String> photoFileNames = new ArrayList<>();
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_photos, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Drawable photo = PhotoUtils.getPhotoDrawable(holder.ivPic.getContext(), holder.ivPic.getContext().getResources().getString(R.string.KEY_FOLDER) + "/" + albumName + "/" + photoFileNames.get(position));
        adjustSize(holder.ivPic);
        if (photo != null)
            holder.ivPic.setImageDrawable(photo);
        holder.tvTitle.setText(PhotoUtils.getDisplayName(photoFileNames.get(position)));
    }


    @Override
    public int getItemCount() {
        return photoFileNames.size();
    }

    private void adjustSize(ImageView v) {
        WindowManager wm = (WindowManager) v.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Integer size = display.getWidth() - 350;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.height = size;
        params.width = size;
        v.setLayoutParams(params);
    }
}
