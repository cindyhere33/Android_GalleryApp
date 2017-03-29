package com.sindhura.samsunggallery.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sindhura.samsunggallery.R;
import com.sindhura.samsunggallery.activities.PicturesActivity;
import com.sindhura.samsunggallery.activities.PreviewActivity;
import com.sindhura.samsunggallery.utils.PhotoUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Sindhura on 3/29/2017.
 */

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {

    private List<String> albums = new ArrayList<>();
    private String TAG = getClass().getName();

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPic;
        TextView tvTitle;

        ViewHolder(View v) {
            super(v);
            ivPic = (ImageView) v.findViewById(R.id.ivImage);
            tvTitle = (TextView) v.findViewById(R.id.tvImage);
        }
    }

    public AlbumAdapter(List<String> albums) {
        this.albums = albums;
    }

    @Override
    public AlbumAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Context context = holder.ivPic.getContext();
        List<String> photos = PhotoUtils.getAssetFiles(context, context.getResources().getString(R.string.KEY_FOLDER) + "/" + albums.get(position));
        if(photos.size()>0){
            Drawable photo = PhotoUtils.getPhotoDrawable(context, context.getResources().getString(R.string.KEY_FOLDER) + "/" + albums.get(position) + "/" + photos.get(0));
            holder.ivPic.setImageDrawable(photo);
        }
        holder.tvTitle.setText(PhotoUtils.getDisplayName(albums.get(position)));
        holder.ivPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), PicturesActivity.class);
                intent.putExtra(view.getContext().getResources().getString(R.string.KEY_ALBUM), albums.get(position));
                view.getContext().startActivity(intent);
            }
        });
        holder.ivPic.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(view.getContext(), PreviewActivity.class);
                intent.putExtra(view.getContext().getResources().getString(R.string.KEY_ALBUM), albums.get(position));
                view.getContext().startActivity(intent);
                return true;
            }
        });
    }


    @Override
    public int getItemCount() {
        return albums.size();
    }
}