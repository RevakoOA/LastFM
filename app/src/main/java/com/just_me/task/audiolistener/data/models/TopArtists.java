package com.just_me.task.audiolistener.data.models;

import com.google.gson.annotations.SerializedName;
import com.just_me.task.audiolistener.data.models.Artist;

import java.util.ArrayList;

public class TopArtists {
    @SerializedName("artist")
    public ArrayList<Artist> artists;
    @SerializedName("@attr")
    public MetaData metadata;

    public static class MetaData {
        public String country;
        public int page;
        public int perPage;
        public int totalPages;
        public int total;
    }
}
