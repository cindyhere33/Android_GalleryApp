package com.sindhura.samsunggallery.activities;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.sindhura.samsunggallery.R;
import com.sindhura.samsunggallery.adapters.PreviewAdapter;
import com.sindhura.samsunggallery.utils.PhotoUtils;

/**
 * Created by sxk159231 on 3/29/2017.
 */

public class PreviewActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (getIntent().hasExtra(getResources().getString(R.string.KEY_ALBUM))) {
            String albumName = getIntent().getExtras().getString(getResources().getString(R.string.KEY_ALBUM), null);
            if (albumName != null) {
                ViewPager mPager = (ViewPager) findViewById(R.id.pager);
                mPager.setAdapter(new PreviewAdapter(this, albumName, PhotoUtils.getAssetFiles(this, getResources().getString(R.string.KEY_FOLDER) + "/"  + albumName)));
            }
        }
    }
}
