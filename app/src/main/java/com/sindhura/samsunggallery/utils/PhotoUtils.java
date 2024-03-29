package com.sindhura.samsunggallery.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sindhura on 3/29/2017.
 */

public class PhotoUtils {

    private static Integer SCREEN_WIDTH = 0;

    //Set the screen width
    public static void updateScreenWidth(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        SCREEN_WIDTH = metrics.widthPixels;
    }

    //Get a list of all the files under the given folder path in assets folder
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

    //Get a compressed Bitmap of the image with the given path in assets folder
    public static Bitmap getPhotoDrawable(Context context, String path) {
        try {
            return decodeSampledBitmapFromResource(context.getAssets().open(path), context.getAssets().open(path), 500, 500);
        } catch (IOException e) {
            Log.e(context.getClass().getName(), "Error getting drawable from path " + path);
            e.printStackTrace();
        }
        return null;
    }

    //Convert Bitmap into BitmapDrawable
    public static BitmapDrawable getBackgroundDrawable(Resources res, Bitmap bitmap) {
        return new BitmapDrawable(res, bitmap);
    }


    //Capitalize first letter of filename
    public static String getDisplayName(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    //Set view height and width to screen width minus the adjustment
    public static int adjustSize(Context context, View v, Integer adjustment) {
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int size = metrics.widthPixels - adjustment;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.height = size;
        params.width = size;
        v.setLayoutParams(params);
        return size;
    }

    //Set the size of view to screen size
    public static void adjustSizeToScreenWidth(View v, Integer adjustment) {
        if (SCREEN_WIDTH == 0) return;
        Integer size = SCREEN_WIDTH - adjustment;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.height = size;
        params.width = size;
        v.setLayoutParams(params);
    }


    //Image compression
    private static Bitmap decodeSampledBitmapFromResource(InputStream is, InputStream is2, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        Rect rect = new Rect(-1, -1, -1, -1);
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, rect, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(is2, rect, options);
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
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
