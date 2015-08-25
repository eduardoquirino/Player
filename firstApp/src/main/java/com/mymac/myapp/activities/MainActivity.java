package com.mymac.myapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.mymac.myapp.Helpers.FileSystemHelper;
import com.mymac.myapp.preferences.Preferences;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Preferences preferences = new Preferences(this);
//
//        SharedPreferences sharePrf = preferences.getSharedPreferences();
//        String  str = sharePrf.getString("music_folder", " ");
//
//        Log.d("SharedPreference", "####SharedPreference +STR: " + str);
//
////
//       // Log.d("SharedPreferences", "####SharedPreferences: " + sharePrf.getAll().toString());
//
//        FileSystemHelper.getFolder("/storage/extSdCard");


        startActivity(new Intent(Activities.MENU_ACTIVITY));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_overflow) {
            return true;
        }
        if (id == R.id.action_settings) {

            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }
}
