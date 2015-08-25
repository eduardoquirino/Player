package com.mymac.myapp.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by eduardo on 13/11/2014.
 */
public class Preferences {
    SharedPreferences prefs;


    public Preferences(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public SharedPreferences getSharedPreferences() {
        return prefs;
    }


    public String getMusicFolderPreference() {
        String musicFolder = prefs.getString(PreferenceConstants.MusicFolderPreference, " ");

        return musicFolder;
    }

    public void setMusicFolderPreference(String musicFolderPreference) {

    }
}