package com.just_me.task.audiolistener.data.responses;

import com.google.gson.annotations.SerializedName;
import com.just_me.task.audiolistener.data.models.Album;

/**
 * Created by USER on 28-Aug-17.
 */

public class AlbumTracksResp {
    @SerializedName("album")
    public Album album;
}
