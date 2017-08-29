package com.just_me.task.audiolistener.data.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by USER on 28-Aug-17.
 */

public class Image {
    public static String[] sizes = new String[]{"small", "medium", "large", "extralarge", "mega"};
    @SerializedName("#text")
    public String url;
    public String size;

    public boolean isValidUrl() {
        return url != null && !url.isEmpty();
    }
}
