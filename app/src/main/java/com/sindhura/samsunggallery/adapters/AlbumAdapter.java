package com.sindhura.samsunggallery.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sindhura.samsunggallery.R;
import com.sindhura.samsunggallery.activities.PicturesActivity;
import com.sindhura.samsunggallery.dialogs.PreviewDialog;
import com.sindhura.samsunggallery.utils.PhotoUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Sindhura on 3/29/2017.
 */

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {

    //List of all album names
    private List<String> albums = new ArrayList<>();

    //Dialog to show pictures in album on LongClick
    PreviewDialog dialog;

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
        //Inflate view and initialize viewholder
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Context context = holder.ivPic.getContext();
        //Get all photo names under the album
        List<String> photos = PhotoUtils.getAssetFiles(context, context.getResources().getString(R.string.KEY_FOLDER) + "/" + albums.get(position));
        if (photos.size() > 0) {
            //Get a compressed version of first photo and set the imageView to show it
            Bitmap photo = PhotoUtils.getPhotoDrawable(context, context.getResources().getString(R.string.KEY_FOLDER) + "/" + albums.get(position) + "/" + photos.get(0));
            holder.ivPic.setImageBitmap(photo);
        }
        //Display the album name in the textView.
        holder.tvTitle.setText(PhotoUtils.getDisplayName(albums.get(position)));

        //Clicking shall open another activity that shows all the photos in the album
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
                showDialog(holder.ivPic.getContext(), albums.get(position));
                view.setOnTouchListener(listener);
                return true;
            }
        });
    }

    //Show preview dialog
    private void showDialog(Context con, String albumName) {
        if (dialog == null)
            dialog = new PreviewDialog(con, albumName);
        else
            dialog.setAlbumName(albumName);
        if (!dialog.isShowing()) dialog.show();

    }

    //Get total number of albums
    @Override
    public int getItemCount() {
        return albums.size();
    }

    private boolean updateX1 = true;
    private float x1, x2, dx = 0;


    //Dismiss the preview
    private void stopPreview() {
        if (!updateX1) {
            updateX1 = true;
            if (dialog != null) dialog.dismiss();
        }
    }

    //Detect motion of finger and change picture accordingly
    View.OnTouchListener listener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            String direction = "";
            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    if (updateX1) {
                        x1 = event.getX();
                        updateX1 = false;
                    } else {
                        x2 = event.getX();
                        dx = x2 - x1;
                        x1 = x2;
                    }
                    if (Math.abs(dx) > 1.5) {
                        if (dx > 0) {
                            direction = "right " + x1 + " - " + x2;
                            if (dialog != null) dialog.goToNextItem();
                        } else if (dx < 0) {
                            direction = "left" + x1 + " - " + x2;
                            if (dialog != null) dialog.goToPreviousItem();
                        }
                    } else direction = "no movement " + dx;
                    break;
                case MotionEvent.ACTION_DOWN:
                    Log.d(TAG, "Down");
                    stopPreview();
                    break;
                case MotionEvent.ACTION_CANCEL:
                    stopPreview();
                    break;
                case MotionEvent.ACTION_UP:
                    stopPreview();
            }

            Log.d(TAG, "Direction " + direction);
            return true;
        }
    };
}