package com.mymac.myapp.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mymac.myapp.Helpers.FileSystemHelper;
import com.mymac.myapp.Helpers.OrganizeMediaHelper;
import com.mymac.myapp.adapter.ArrayMusicAdapter;
import com.mymac.myapp.models.Media;
import com.mymac.myapp.models.Song;
import com.mymac.myapp.player.PlayListManager;
import com.mymac.myapp.player.PlayerManager;

import java.io.File;
import java.util.Random;

import android.os.Handler;


public class AlbumActivity extends ActionBarActivity {

    ListView listView;
    ImageButton playerButton;
    ImageButton nextButton;
    ImageButton previousButton;
    ImageButton suffleButton;
    ImageButton repeatModeButton;
    ImageButton backwardButton;
    ImageButton forwardButton;

    ///Controllers
    boolean activateSuffleMode = true; // go to  the playListManager


    SeekBar seekBar;
    final static int EVERY_SECOND = 100;
    Handler handler;
    Runnable onEverySecond;

    private int currentMediaIndex;

    //MediaPlayer mp;
   // private static PlayerManager player;
    private static PlayListManager playListManager;

    private LinearLayout playerContainerView;

    TextView leftTimeView;
    TextView durationTextView;

   // private void play(Media media){
//        PlayerManager.setMedia(media);
//        PlayerManager.play();
   private void play(int position){
        //TO-DO:
        PlayListManager.play(position);


        //Timers
        leftTimeView = (TextView) findViewById(R.id.lefTime);
        durationTextView = (TextView) findViewById(R.id.durationText);
        durationTextView.setText(setTime(PlayerManager.getDuration()));

        seekBar.setMax(PlayListManager.getDuration());
        seekBar.setProgress(0);
        handler.postDelayed(onEverySecond, EVERY_SECOND);
    }

    private void pauseMediaEvent(){
        PlayerManager.pause();
    }
    private void stop() {}

    private void resumeMediaEvent(){
//        PlayerManager.resume();
        PlayListManager.resume();
    }

    private String setTime(long millis){


        long second = (millis / 1000) % 60;
        long minute = (millis / (1000 * 60)) % 60;
        long hour = (millis / (1000 * 60 * 60)) % 24;

        //String time = String.format("%02d:%02d:%02d:%d", hour, minute, second, millis);
        String time = " ";
        if (hour != 0) {
            time = String.format("%02d:%02d:%02d", hour, minute, second);
        } else {
            time = String.format("%02d:%02d", minute, second);
        }

        return time;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        Intent intent = getIntent();
        String albumPath =  intent.getStringExtra("albumPath");
        String albumName =  intent.getStringExtra("albumName");


        getSupportActionBar().setTitle(albumName);
        Log.d("SharedPreference", "####SharedPreference +STR: " + albumPath);



        File[] files =  FileSystemHelper.getAlbum(albumPath);
        listView = (ListView) findViewById(R.id.list);

        OrganizeMediaHelper organize = new OrganizeMediaHelper(files);
        Media[] medias = organize.getMedias();

        PlayListManager.setPlayList(medias);


        ArrayMusicAdapter adapter = new ArrayMusicAdapter(this, R.layout.list_view_row_music_item, medias);

        // Assign adapter to ListView
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // ListView Clicked item index
                currentMediaIndex = position;


                // ListView Clicked item value
                Media  media = (Media) listView.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getApplicationContext(), "Position :" + currentMediaIndex + "  ListItem : " + media.getTitle(), Toast.LENGTH_LONG).show();

                if (media instanceof Song) {

                    play(currentMediaIndex);

                    playerButton.setImageResource(R.drawable.btn_pause);

                    playerContainerView = (LinearLayout) findViewById(R.id.playerContainerView);
                    playerContainerView.setEnabled(true);
                    playerContainerView.setVisibility(View.VISIBLE);




                } else {
                    Intent intent = new Intent(Activities.ALBUM_ACTIVITY);
                    intent.putExtra("albumPath", media.getPath());
                    intent.putExtra("albumName", media.getTitle());
                    startActivity(intent);
                }
            }

        });


        playerButton = (ImageButton) findViewById(R.id.btnPlay);
        playerButton.setImageResource(R.drawable.btn_pause);

        playerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(PlayListManager.isPlaying()) {
                    playerButton.setImageResource(R.drawable.btn_play);
                    pauseMediaEvent();
                } else {
                    playerButton.setImageResource(R.drawable.btn_pause);
                    resumeMediaEvent();
                }
            }
        });

        nextButton = (ImageButton) findViewById(R.id.btnNext);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //new Handler().postDelayed(new Runnable() {
                  //  public void run() {
                        nextButton.setImageResource(R.drawable.btn_next_selection);
                //    }
                //}, 50L);

                PlayListManager.playNextMedia();

                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        nextButton.setImageResource(R.drawable.btn_next);
                    }
                }, 100L);
            }
        });

        previousButton = (ImageButton) findViewById(R.id.btnPrevious);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  new Handler().postDelayed(new Runnable() {
                //    public void run() {
                        previousButton.setImageResource(R.drawable.btn_previous_selection);
               //     }
               // }, 50L);


                PlayListManager.playPreviousMedia();

                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        previousButton.setImageResource(R.drawable.btn_previous);
                    }
                }, 100L);
            }
        });


        suffleButton = (ImageButton) findViewById(R.id.btnSuffle);
        suffleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Handler().postDelayed(new Runnable() {
                    public void run() {

                        activateSuffleMode = !activateSuffleMode;



                        //Media media = (Media) listView.getItemAtPosition(position);
                        //play(media);

                        //To-Do:
                        //PlayListManager.play();

                        suffleButton.setImageResource(R.drawable.btn_no_shuffle);

                    }
                }, 50L);


                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        suffleButton.setImageResource(R.drawable.btn_shuffle_black);
                    }
                }, 100L);
            }
        });

        repeatModeButton = (ImageButton) findViewById(R.id.btnRepeatMode);
        repeatModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        repeatModeButton.setImageResource(R.drawable.btn_repeat_black_one);
                    }
                }, 50L);

                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        repeatModeButton.setImageResource(R.drawable.btn_repeat_black_all);
                    }
                }, 100L);
            }
        });


//        backwardButton = (ImageButton) findViewById(R.id.btnBackward);
//        backwardButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                backwardButton.setImageResource(R.drawable.btn_backward_selection);
//
//                int currentPosition = PlayerManager.getCurrentPosition();
//                Log.i("AlbumActivity", "backwardButton####currentPositionMedia : "+currentPosition);
//                PlayerManager.seekTo(PlayerManager.getDuration()-10000);
//                PlayerManager.seekTo(0);
//
//
//                new Handler().postDelayed(new Runnable() {
//                    public void run() {
//                        backwardButton.setImageResource(R.drawable.btn_backward);
//                    }
//                }, 100L);
//            }
//        });
//
//
//        forwardButton = (ImageButton) findViewById(R.id.btnForward);
//        forwardButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                forwardButton.setImageResource(R.drawable.btn_forward_selection);
//
//                int currentPosition = PlayerManager.getCurrentPosition();
//
//                Log.i("AlbumActivity", "forwardButton####currentPositionMedia : " + currentPosition);
//                PlayerManager.seekTo(PlayerManager.getDuration()-10000);
//
//                new Handler().postDelayed(new Runnable() {
//                    public void run() {
//                        forwardButton.setImageResource(R.drawable.btn_forward);
//                    }
//                }, 100L);
//            }
//        });

        seekBar = (SeekBar) findViewById(R.id.seekBar);

        onEverySecond = new Runnable() {
            @Override
            public void run() {
                if(PlayListManager.isPlaying()){
                    if(seekBar!= null && handler != null) {

                        int position = PlayListManager.getCurrentPosition();

                        seekBar.setProgress(position);
                        handler.postDelayed(onEverySecond, EVERY_SECOND);
                        String timeSTR = setTime(position);
                        leftTimeView.setText(timeSTR);
                    }
                }
            }
        };
        handler = new Handler();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            private int currentProgress = 0;
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                PlayListManager.seekTo(currentProgress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentProgress = progress;
            }
        });

    }

    private boolean checkExtension( String fileName ) {
        String ext = getFileExtension(fileName);
        if ( ext == null) return false;
        try {
            if ( SupportedFileFormat.valueOf(ext.toUpperCase()) != null ) {
                return true;
            }
        } catch(IllegalArgumentException e) {
            return false;
        }
        return false;
    }

    public String getFileExtension( String fileName ) {
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            return fileName.substring(i+1);
        } else
            return null;
    }
    public enum SupportedFileFormat
    {
        _3GP("3gp"),
        MP4("mp4"),
        M4A("m4a"),
        AAC("aac"),
        TS("ts"),
        FLAC("flac"),
        MP3("mp3"),
        MID("mid"),
        XMF("xmf"),
        MXMF("mxmf"),
        RTTTL("rtttl"),
        RTX("rtx"),
        OTA("ota"),
        IMY("imy"),
        OGG("ogg"),
        MKV("mkv"),
        WAV("wav");

        private String filesuffix;

        SupportedFileFormat( String filesuffix ) {
            this.filesuffix = filesuffix;
        }

        public String getFilesuffix() {
            return filesuffix;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.album, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
