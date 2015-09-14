package com.mymac.myapp.player;

import android.media.MediaPlayer;

import com.mymac.myapp.models.Media;

import java.util.Random;

/**
 * Created by eduardo on 16/08/2015.
 */
public class PlayListManager {

    private static Media[] playList;

    private static int currentMediaIndex = 0;



    // private static boolean activateSuffleMode = true; //go to the playListManager

    public static enum  RepeatMode  {
        NONE,
        ONE,
        ALL
    };

    //TODO: save current playList

    //TODO: Save and load repeatMode
    private static RepeatMode repeatMode = RepeatMode.NONE;

    //TODO: Save and load suffleMode
    private static boolean isOnSuffleMode = false;

    public static void setPlayList(Media[] medias) {
        playList = medias;
    }

    private static Media getMedia(int index) {
        return playList[index];
    }

    public static void play (int position) {
        currentMediaIndex = position;
        Media media = getMedia(position);

        PlayerManager.setMedia(media);
        PlayerManager.play();

        setMediaCompletionEvent();
    }

    private static int getNextIndex(int index) {

        index++;
        if (index > playList.length - 1)
        {
            index = 0;
        }

        return index;
    }

    private static int getPreviousIndex(int index) {

        --index;
        if (index < 0)
        {
            index = playList.length - 1;
        }

        return index;
    }

    public static void playNextMedia() {
        currentMediaIndex = getNextIndex(currentMediaIndex);
        Media media = getMedia(currentMediaIndex);

        PlayerManager.setMedia(media);
        PlayerManager.play();
        setMediaCompletionEvent();
    }

    public static void playPreviousMedia() {
        currentMediaIndex = getPreviousIndex(currentMediaIndex);
        Media media = getMedia(currentMediaIndex);

        PlayerManager.setMedia(media);
        PlayerManager.play();
        setMediaCompletionEvent();
    }

    public static void seekTo(int msec) {
        PlayerManager.seekTo(msec);
    }

    public static boolean isPlaying() {
        return PlayerManager.isPlaying();
    }

    public static void setRepeatMode (RepeatMode _repeatMode) {
        repeatMode = _repeatMode;
    }

    public static RepeatMode changeRepeatMode (RepeatMode mode) {

        switch(mode) {
            case NONE:
                repeatMode = RepeatMode.ONE ;
                break;
            case  ONE:
                repeatMode = RepeatMode.ALL ;
                break;
            case  ALL:
                repeatMode = RepeatMode.NONE ;
                break;
        }

       return repeatMode;
    }

    public static RepeatMode getRepeatMode () {
        if(repeatMode == null){
            repeatMode = RepeatMode.NONE;
        }

        return repeatMode;
    }

    public static void setLooping(boolean isLooping){
        PlayerManager.setLooping(isLooping);
    }

    public static void setMediaCompletionEvent() {

        PlayerManager.getMediaPlayer().setOnCompletionListener(new MediaPlayer.OnCompletionListener(){

            @Override
            public void onCompletion(MediaPlayer mp) {

                if (PlayListManager.isOnSuffleMode()) {
                    currentMediaIndex =  PlayListManager.getRandom(playList.length -1);
                    play(currentMediaIndex);
                    return;
                }

                if (PlayListManager.getRepeatMode() == RepeatMode.ONE) {
                    play(currentMediaIndex);
                    return;
                } else if (currentMediaIndex == playList.length - 1) {

                    if (PlayListManager.getRepeatMode() == RepeatMode.NONE) {
                        PlayerManager.stop();
                        return;
                    }
                }
                playNextMedia();
            }
        });
    }

    public static boolean isOnSuffleMode() {
        return isOnSuffleMode;
    }

    public static void setSuffleMode(boolean isActivated) {
        PlayListManager.isOnSuffleMode = isActivated;
    }

    public static int getRandom (int max) {
        Random random = new Random();
        int position = random.nextInt(max);
        return position;
    }

    public static int getDuration(){
        return PlayerManager.getDuration();
    }

    public static void resume () {
        PlayerManager.resume();
    }

    public static int getCurrentPosition(){
        return PlayerManager.getCurrentPosition();
    }
}
