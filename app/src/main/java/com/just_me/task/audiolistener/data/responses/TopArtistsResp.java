package com.just_me.task.audiolistener.data.responses;

import com.google.gson.annotations.SerializedName;
import com.just_me.task.audiolistener.data.models.TopArtists;

/**
 * Created by USER on 28-Aug-17.
 */

public class TopArtistsResp {
    @SerializedName("topartists")
    public TopArtists topArtists;
}
