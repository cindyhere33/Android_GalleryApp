package com.sindhura.samsunggallery.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.sindhura.samsunggallery.R;
import com.sindhura.samsunggallery.adapters.PreviewAdapter;
import com.sindhura.samsunggallery.utils.PhotoUtils;

/**
 * Created by Sindhura on 3/29/2017.
 */

//Transparent activity to show preview of images

public class PreviewActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        //Get the album name
        if (getIntent().hasExtra(getResources().getString(R.string.KEY_ALBUM))) {
            String albumName = getIntent().getExtras().getString(getResources().getString(R.string.KEY_ALBUM), null);
            if (albumName != null) {
                //Initialize viewPager to swipe through photos
                ViewPager mPager = (ViewPager) findViewById(R.id.pager);
                //Set size of each photo
                PhotoUtils.adjustSize(mPager, 0);
                //Populate the viewpager with the album photos from the given file path
                mPager.setAdapter(new PreviewAdapter(this, albumName, PhotoUtils.getAssetFiles(this, getResources().getString(R.string.KEY_FOLDER) + "/" + albumName)));
            }
        }
    }
}
