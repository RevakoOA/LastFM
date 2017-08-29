package com.just_me.task.audiolistener.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

/**
 * Created by USER on 28-Aug-17.
 */

public class Track {

    public String name;
    public Artist artist;
    public Album album;
    public String url;
    public int duration;
    @SerializedName("@attr")
    public Attr attr;
//  "streamable": {
//      "#text": "0",
//      "fulltrack": "0"
//  }
public HashMap<String, String> streamable;

    public static class Attr {
        public int rank;
    }

}
