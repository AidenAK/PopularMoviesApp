package com.doelay.android.popularmoviesapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.doelay.android.popularmoviesapp.R;

/**
 * Created by doelay on 6/20/2017.
 */

public class FavoriteMovieActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        // TODO: 6/20/2017 load favorite movies from content provider
    }
}
