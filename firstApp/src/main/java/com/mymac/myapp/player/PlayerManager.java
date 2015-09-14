package com.mymac.myapp.player;

import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;

import com.mymac.myapp.models.Media;

import java.io.IOException;

/**
 * Created by eduardo on 30/07/2015.
 */
public class PlayerManager {

    private static Media _media;
    private static MediaPlayer mp;

    public static void setMedia(Media media){

        if (mp != null) {
            if (isPlaying()) {
                stop();
            }
        }

        mp = new MediaPlayer();

        _media = media;

        setDataSource();
    }

    private static void setDataSource() {
        try {
            if (mp != null) {
                Log.d("event Media", "Media Path:" + _media.getPath());
                mp.setDataSource(_media.getPath());
            }
        } catch (java.lang.NullPointerException e){
            Log.e("PlayerManager", " setDataSource():::NullPointerException ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void play () {
        try {
            if (isPlaying()) {
                stop();
            }

            mp.prepare();
            mp.start();

        } catch (java.lang.NullPointerException e){
            Log.e("PlayerManager", " play():::NullPointerException: call setMedia before call play ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void stop () {
        try {
            mp.stop();
            mp = null;
        } catch (java.lang.NullPointerException e){
            Log.e("PlayerManager", " stop():::NullPointerException: Call setMedia method before call stop");
        }
    }

    public static void resume() {
        try {
            mp.start();
        } catch (java.lang.NullPointerException e) {
            Log.e("PlayerManager", " resume()::: NullPointerException:  Call setMedia method before call resume");
        }
    }
    public static void pause() {
        try {
            mp.pause();
        } catch (java.lang.NullPointerException e){
            Log.e("PlayerManager", " pause()::: NullPointerException: Call setMedia method before call pause");
        }
    }

    public static void reset() {

        try {
            mp.reset();
        } catch (java.lang.NullPointerException e){
            Log.e("PlayerManager", " reset()::: NullPointerException: Call setMedia method before call reset");
        }
    }

    public static boolean isPlaying() {
        boolean isPlaying = false;

        if(mp != null) {
            isPlaying =  mp.isPlaying();
        }

        return isPlaying;
    }

    public static int getDuration() {
        int duration = 0;

        try {
            duration =  mp.getDuration();
        } catch (java.lang.NullPointerException e){
            Log.e("PlayerManager", " reset()::: NullPointerException: Call setMedia method before call getDuration");
        }

        return duration;
    }

    public static void seekTo(int msec) {

        try {
            mp.pause();
            mp.seekTo(msec);
            mp.start();
        } catch (java.lang.NullPointerException e){
            Log.e("PlayerManager", " reset()::: NullPointerException: Call setMedia method before call seekTo");
        }

    }

    public static int getCurrentPosition(){
        int currentPosition = 0;

        try {
            currentPosition = mp.getCurrentPosition();
        } catch (java.lang.NullPointerException e){
            Log.e("PlayerManager", " reset()::: NullPointerException: Call setMedia method before call currentPosition()");
        }

        return currentPosition;
    }

    public static void setLooping(boolean isLooping){
        mp.setLooping(isLooping);
    }

    public static MediaPlayer getMediaPlayer(){
        return mp;
    }
    //TODO: implementar setOnCompletionListener
//    public static void completion(Runnable runnable){
//        mp.OnCompletionListener(new){
//            new Handler().postDelayed()
//        }
//    }
}
