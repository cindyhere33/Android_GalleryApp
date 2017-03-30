package com.sindhura.samsunggallery.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Window;

import com.sindhura.samsunggallery.R;
import com.sindhura.samsunggallery.adapters.PreviewAdapter;
import com.sindhura.samsunggallery.utils.PhotoUtils;

/**
 * Created by Sindhura on 3/29/2017.
 */

public class PreviewDialog extends Dialog {

    private final String TAG = getClass().getName();
    ViewPager mPager;

    public PreviewDialog(@NonNull Context context, String albumName) {
        super(context);
        init(albumName);
    }

    public PreviewDialog(@NonNull Context context, String albumName, @StyleRes int themeResId) {
        super(context, themeResId);
        init(albumName);
    }

    protected PreviewDialog(@NonNull Context context, String albumName, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(albumName);
    }

    private void init(String albumName) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_preview);
        mPager = (ViewPager) findViewById(R.id.pager);
        //Set size of each photo
        PhotoUtils.adjustSizeToScreenWidth(mPager, 0);
        //Populate the viewpager with the album photos from the given file path
        mPager.setAdapter(new PreviewAdapter(getContext(), albumName, PhotoUtils.getAssetFiles(getContext(), getContext().getResources().getString(R.string.KEY_FOLDER) + "/" + albumName)));
    }

    //update albumName
    public void setAlbumName(String albumName) {
        mPager.setAdapter(new PreviewAdapter(getContext(), albumName, PhotoUtils.getAssetFiles(getContext(), getContext().getResources().getString(R.string.KEY_FOLDER) + "/" + albumName)));
    }

    //view next photo
    public void goToNextItem() {
        if (mPager != null)
            mPager.setCurrentItem(mPager.getCurrentItem() + 1);
    }

    //view previous photo
    public void goToPreviousItem() {
        if (mPager != null)
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
    }

    @Override
    public void show() {
        try {
            if (!isShowing()) super.show();
        } catch (Exception e) {
            Log.e(TAG, "Error while displaying preview");
            e.printStackTrace();
        }
    }


    //Dismiss the preview
    @Override
    public void dismiss() {
        try {
            if (isShowing()) super.dismiss();
        } catch (Exception e) {
            Log.e(TAG, "Error while dismissing preview");
            e.printStackTrace();
        }
    }
}
