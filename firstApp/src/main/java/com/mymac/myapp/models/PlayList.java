package com.mymac.myapp.models;

import com.mymac.myapp.player.PlayerManager;

/**
 * Created by eduardo on 5/08/2015.
 */
public class PlayList extends Media {

    private PlayerManager playerManager;
    private Song[] songs;
    private int currentSongIndex;

    public Song[] getSongs() { return songs; }

    public void setSongs(Song[] songs) {
        this.songs = songs;
    }

    public void play() {

        //if tocando
           //stop

//        if (songs != null && songs.length > 0) {
//            PlayerManager.setMedia(songs[currentSongIndex]);
//            PlayerManager.play();
//        }
    }

    public void playNext(){
//        currentSongIndex++;
        //play()
    }

    public void goForward() { }

    public void goBackward() {

    }

    public void playPrevious(){
       // currentSongIndex--;

        //play()
    }


}
