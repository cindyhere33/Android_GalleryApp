package com.sindhura.samsunggallery.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sindhura.samsunggallery.R;
import com.sindhura.samsunggallery.adapters.AlbumAdapter;
import com.sindhura.samsunggallery.utils.PhotoUtils;

/**
 * Created by Sindhura on 3/29/2017.
 */


public class MainActivity extends AppCompatActivity {

    private final String TAG = getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums);
        PhotoUtils.updateScreenWidth(this);

        //Recycler view with LinearLayout Manager to show albums
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Populate album names from assets ( folder name in resources )
        recyclerView.setAdapter(new AlbumAdapter(PhotoUtils.getAssetFiles(this, getResources().getString(R.string.KEY_FOLDER))));


    }

}
