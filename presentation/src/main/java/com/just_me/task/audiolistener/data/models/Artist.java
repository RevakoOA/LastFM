package com.just_me.task.audiolistener.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by USER on 28-Aug-17.
 */

public class Artist {
    public String name;
    @SerializedName("listeners")
    public int listenersCount;
    public String mbid;
    public String url;
    @SerializedName("streamable")
    public int streamable;
    public ArrayList<Image> image;
}
