package com.mymac.myapp.Helpers;

import android.media.MediaMetadataRetriever;
import android.util.Log;

import com.mpatric.mp3agic.*;
import com.mymac.myapp.models.Album;
import com.mymac.myapp.models.Media;
import com.mymac.myapp.models.Song;

import java.io.File;
/**
 * Created by eduardo on 3/08/2015.
 */
public class OrganizeMediaHelper {

    private Media[] medias;

    public OrganizeMediaHelper(File[] files){
        Media[] medias = new Media[files.length];
        for (int i=0; i < files.length; i++)
        {
//            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
//            mmr.setDataSource(filePath);
//
//            String albumName = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);

            if (files[i].isFile() && checkExtension(files[i].getName())) {
                Song music = new Song();
                music.setTitle(files[i].getName());
                music.setAlbumTitle(files[i].getParent());
                music.setPath(files[i].getPath());
                medias[i] = music;

               // MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
                //metaRetriever.setDataSource(files[i].getPath());

                String out = "";

                //String duration =  metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);

               // music.setTitle(metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM));

                try{
                    Mp3File mp3file = new Mp3File(files[i].getPath());
                    if (mp3file.hasId3v2Tag()) {
                        ID3v2 id3v2Tag = mp3file.getId3v2Tag();
                        //System.out.println("Track: " + id3v2Tag.getTrack());
                        //System.out.println("Artist: " + id3v2Tag.getArtist());
                        System.out.println("Title: " + id3v2Tag.getTitle());
                        music.setTitle(id3v2Tag.getTitle());
                    }
                }catch(Exception e){
                    Log.i("##erro", "hasId3v2Tag");
                }



            } else if(files[i].isDirectory()) {
                ///Album
                medias[i] = new Album();
                medias[i].setTitle(files[i].getName());
                medias[i].setPath(files[i].getPath());
            }
        }

        this.medias=medias;
    }

    public Media[] getMedias(){
        return this.medias;
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
}
