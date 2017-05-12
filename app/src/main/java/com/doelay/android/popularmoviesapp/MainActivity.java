package com.doelay.android.popularmoviesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemClicked = item.getItemId();
        switch (menuItemClicked) {
            case R.id.action_popularity :
                Toast.makeText(this,"sort by popularity",Toast.LENGTH_LONG).show();
                // TODO: 5/11/2017 Add codes to sort by popularity
                return true;
            case R.id.action_rating :
                Toast.makeText(this,"sort by rating",Toast.LENGTH_LONG).show();
                // TODO: 5/11/2017 Add codes to sort by rating
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
