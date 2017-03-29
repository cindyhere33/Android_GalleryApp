package com.sindhura.samsunggallery;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
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
            ivPic = (ImageView)v.findViewById(R.id.ivImage);
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
        AssetManager assetManager = holder.ivPic.getContext().getAssets();
        try {
            String[] albumPics = assetManager.list("Photos/" + albums.get(position));
            if (albumPics.length > 0) {
                Drawable pic = Drawable.createFromStream(assetManager.open("Photos/" + albums.get(position) + "/" + albumPics[0]), null);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    holder.ivPic.setBackground(pic);
                }
                else holder.ivPic.setBackgroundDrawable(pic);
            }
        } catch (IOException e) {
            Log.e(TAG, "Error loading pictures in album");
            e.printStackTrace();
        }
        holder.tvTitle.setText(getPrintableName(albums.get(position)));
        holder.ivPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), PicturesActivity.class);
                intent.putExtra(view.getContext().getResources().getString(R.string.KEY_ALBUM), albums.get(position));
                view.getContext().startActivity(intent);
            }
        });
    }

    private String getPrintableName(String s) {
        return s.substring(0,1).toUpperCase() + s.substring(1).toLowerCase();
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }
}