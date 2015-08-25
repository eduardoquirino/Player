package com.mymac.myapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageButton;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mymac.myapp.Helpers.FileSystemHelper;
import com.mymac.myapp.Helpers.OrganizeMediaHelper;
import com.mymac.myapp.models.Media;
import com.mymac.myapp.models.Song;
import com.mymac.myapp.preferences.Preferences;
import com.mymac.myapp.player.PlayerManager;

import com.mymac.myapp.adapter.ArrayMusicAdapter;

import java.io.File;


public class MenuActivity extends ActionBarActivity {
    ListView listView;
    ImageButton playerButton;
    //MediaPlayer mp;
    PlayerManager player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Preferences preferences = new Preferences(this);

        SharedPreferences sharePrf = preferences.getSharedPreferences();
        String  pathToMusicFiles = sharePrf.getString("music_folder", " ");

        Log.d("SharedPreference", "####SharedPreference +STR: " + pathToMusicFiles);

        File[] files =  FileSystemHelper.getFolder("/storage/extSdCard");

        listView = (ListView) findViewById(R.id.list);

        OrganizeMediaHelper organize = new OrganizeMediaHelper(files);
        Media[] medias = organize.getMedias();

        ArrayMusicAdapter adapter = new ArrayMusicAdapter(this, R.layout.list_view_row_music_item, medias);

        // Assign adapter to ListView
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                Media  media = (Media) listView.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getApplicationContext(), "Position :" + itemPosition + "  ListItem : " + media.getTitle(), Toast.LENGTH_LONG).show();

                System.out.println("MusicPath::"+media.getPath());
                if (media instanceof Song) {
                    PlayerManager.setMedia(media);
                    PlayerManager.play();


                    playerButton = (ImageButton) findViewById(R.id.btnPlay);
                    playerButton.setImageResource(R.drawable.btn_pause);
                    playerButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            playerButton.setImageResource(R.drawable.btn_play);
                            PlayerManager.pause();
                        }
                    });
                } else {

                    Intent intent = new Intent(Activities.ALBUM_ACTIVITY);
                    intent.putExtra("albumPath", media.getPath() );
                    startActivity(intent);
                }
            }

        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();
        if (id == R.id.action_overflow) {
            return true;
        }

        if (id == R.id.action_settings) {
            startActivity(new Intent(Activities.SETTINGS_ACTIVITY));
        }

        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed (){

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        System.exit(0);
    }
}
