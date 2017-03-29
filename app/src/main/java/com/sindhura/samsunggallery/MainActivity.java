package com.sindhura.samsunggallery;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new AlbumAdapter(getAlbums()));
        //   getAlbums2();
    }

    private List<String> getAlbums() {
        try {
            String[] files = getAssets().list("Photos");
            for (String fileName : files) {
                Log.i(TAG, "File: " + fileName);
            }
            return Arrays.asList(files);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Album> getAlbums2() {
        List<Album> albums = new ArrayList<>();
        List<Drawable> animalDrawables = new ArrayList<>();
        List<Drawable> architectureDrawables = new ArrayList<>();
        List<Drawable> foodDrawables = new ArrayList<>();
        List<Drawable> posterDrawables = new ArrayList<>();
        List<Drawable> sceneryDrawables = new ArrayList<>();
        int res = getResources().getIdentifier("ani*", "drawable", getPackageName());
        Field[] drawablesFields = com.sindhura.samsunggallery.R.drawable.class.getFields();
        for (Field field : drawablesFields) {
            try {
                Drawable drawable = getResources().getDrawable(field.getInt(null));
                if (field.getName().contains("animal")) {
                    animalDrawables.add(drawable);
                } else if (field.getName().contains("Photos/Architecture")) {
                    architectureDrawables.add(drawable);
                } else if (field.getName().contains("food")) {
                    foodDrawables.add(drawable);
                } else if (field.getName().contains("poster")) {
                    posterDrawables.add(drawable);
                } else if (field.getName().contains("scenery")) {
                    sceneryDrawables.add(drawable);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        albums.add(new Album(animalDrawables, "Photos/Animals", ContextCompat.getDrawable(this, R.drawable.animals1)));
        albums.add(new Album(architectureDrawables, "Photos/Architecture", ContextCompat.getDrawable(this, R.drawable.architecture1)));
        albums.add(new Album(foodDrawables, "Food", ContextCompat.getDrawable(this, R.drawable.food1)));
        albums.add(new Album(posterDrawables, "Poster", ContextCompat.getDrawable(this, R.drawable.posters1)));
        albums.add(new Album(sceneryDrawables, "Scenery", ContextCompat.getDrawable(this, R.drawable.scenery1)));
        return albums;
    }

}
