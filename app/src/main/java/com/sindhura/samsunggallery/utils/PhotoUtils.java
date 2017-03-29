package com.sindhura.samsunggallery.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sxk159231 on 3/29/2017.
 */

public class PhotoUtils {

    public static List<String> getAssetFiles(Context context, String path) {
        try {
            String[] albumPics = context.getAssets().list(path);
            return Arrays.asList(albumPics);
        } catch (IOException e) {
            Log.e(context.getClass().getName(), "Error loading pictures from album");
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static Drawable getPhotoDrawable(Context context, String path) {
        try {
            return Drawable.createFromStream(context.getAssets().open(path), null);
        } catch (IOException e) {
            Log.e(context.getClass().getName(), "Error getting drawable from path " + path);
            e.printStackTrace();
        }
        return null;
    }

    public static String getDisplayName(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    public static Display getDisplayMetrics(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay();
    }

    public static int adjustSize(View v, Integer adjustment) {
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) v.getContext()).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int size = metrics.widthPixels - adjustment;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.height = size;
        params.width = size;
        v.setLayoutParams(params);
        return size;
    }


    public static Bitmap decodeSampledBitmapFromResource(InputStream is, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, new Rect(reqHeight, reqHeight, reqHeight, reqHeight), options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(is, new Rect(reqHeight, reqHeight, reqHeight, reqHeight), options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
