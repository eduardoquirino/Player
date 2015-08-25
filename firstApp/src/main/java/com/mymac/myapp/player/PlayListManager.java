package com.mymac.myapp.player;

import com.mymac.myapp.models.Media;

import java.util.Random;

/**
 * Created by eduardo on 16/08/2015.
 */
public class PlayListManager {

    private static Media[] playList;

    private static int currentMediaIndex = 0;

//    private static PlayerManager player;

    private static boolean activateSuffleMode = true; //go to the playListManager

    public static enum  RepeatMode  {
        none,
        One,
        ALL
    };

    private static RepeatMode mode;

    private static boolean suffleMode = true;

    public static void setPlayList(Media[] medias) {
        playList = medias;
    }

    private static Media getMedia(int index) {
        return playList[index];
    }

//    public void setMedia (int position) {
//        Media media = getMedia(position);
//
//        PlayerManager.setMedia(media);
//        PlayerManager.play();
//    }

    public static void play (int position) {
        currentMediaIndex = position;
        Media media = getMedia(position);

        PlayerManager.setMedia(media);
        PlayerManager.play();

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
    }

    public static void playPreviousMedia() {
        currentMediaIndex = getPreviousIndex(currentMediaIndex);
        Media media = getMedia(currentMediaIndex);

        PlayerManager.setMedia(media);
        PlayerManager.play();
    }

    public static void seekTo(int msec) {
        PlayerManager.seekTo(msec);
    }

    public static boolean isPlaying() {
        return PlayerManager.isPlaying();
    }

    public static void setRepeatMode (RepeatMode repeatMode) {
        mode = repeatMode;
    }

    public static void enableSuffle(boolean activate) {

        suffleMode = activate;
    }

//TODO: Suffle

//    public static int getRamdon () {
//        int max = playList.length -1;
//        Random random = new Random();
//        int position = random.nextInt(max);
//    }

    //TODO: RepeatMode

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
