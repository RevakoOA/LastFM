package com.just_me.task.audiolistener.data.responses;

import com.google.gson.annotations.SerializedName;
import com.just_me.task.audiolistener.data.models.Album;

import java.util.ArrayList;

/**
 * Created by USER on 28-Aug-17.
 */

public class TopAlbums {

    @SerializedName("album")
    public ArrayList<Album> albums;
    @SerializedName("@attr")
    public MetaData metaData;

    public static class MetaData {
        public String artist;
        public int page;
        public int perPage;
        public int totalPages;
        public int total;
    }
}
