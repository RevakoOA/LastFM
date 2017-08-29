package com.just_me.task.audiolistener.data.models;

import com.google.gson.annotations.SerializedName;
import com.just_me.task.audiolistener.data.models.Artist;
import com.just_me.task.audiolistener.data.models.Image;
import com.just_me.task.audiolistener.data.responses.TrackResp;

import java.util.ArrayList;

public class Album {
    public String name;
    public int playcount;
    public String mbid;
    public String url;
    public String artistName;
    @SerializedName("image")
    public ArrayList<Image> image;
    public TrackResp tracks;
    public Wiki wiki;
    @SerializedName("tags")
    public TagList tagList;

    public static class Wiki {
        public String published;
        public String summary;
        public String content;
    }

    public static class TagList {
        @SerializedName("tag")
        public ArrayList<Tag> tags;
    }

    public Image getImage(String size) {
        int posSize = 0;
        for (int i = 0; i < Image.sizes.length; i++) {
            if (Image.sizes[i].equals(size)) {
                posSize = i;
            }
        }
        Image iterImage;
        int imagePos = Math.min(posSize, image.size()-1);
        iterImage = image.get(imagePos);
        if (iterImage.isValidUrl()) {
            return iterImage;
        } else {
            for (int i = 0; i < image.size(); i++) {
                iterImage = image.get(i);
                if (iterImage.isValidUrl()) {
                    return iterImage;
                }
            }
            return null;
        }
    }

}
