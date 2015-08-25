package com.mymac.myapp.adapter;

import com.mpatric.mp3agic.*;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mymac.myapp.Helpers.FileSystemHelper;
import com.mymac.myapp.models.Album;
import com.mymac.myapp.models.Media;
import com.mymac.myapp.models.Song;
import com.mymac.myapp.activities.R;


/**
 * Created by eduardo on 5/12/2014.
 */
public class ArrayMusicAdapter extends ArrayAdapter<Media> {

    Context mContext;
    int layoutResourceId;
    Media medias[] = null;



    public ArrayMusicAdapter(Context mContext, int layoutResourceId, Media[] medias) {

        super(mContext, layoutResourceId, medias);

        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.medias = medias;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        /*
         * The convertView argument is essentially a "ScrapView" as described is Lucas post
         * http://lucasr.org/2012/04/05/performance-tips-for-androids-listview/
         * It will have a non-null value when ListView is asking you recycle the row layout.
         * So, when convertView is not null, you should simply update its contents instead of inflating a new row layout.
         */
        if(convertView == null){
            // inflate the layout
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
        }


        // object item based on the position
        Media media = medias[position];

        if (media instanceof Song){
            // get the TextView and then set the text (item name) and tag (item ID) values
            TextView musicTitle = (TextView) convertView.findViewById(R.id.musicTitle);
            musicTitle.setText(media.getTitle());

            TextView albumTitle = (TextView) convertView.findViewById(R.id.albumTitle);
            albumTitle.setText(FileSystemHelper.getParentFolder(((Song) media).getAlbumTitle()));
        }
        else if (media instanceof Album){
            TextView musicTitle = (TextView) convertView.findViewById(R.id.musicTitle);
            musicTitle.setText(media.getTitle());

            ImageView imageView = (ImageView) convertView.findViewById(R.id.musicThumb);
            imageView.setVisibility(View.VISIBLE);
        }



        return convertView;

    }

}
