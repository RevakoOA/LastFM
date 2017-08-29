package com.just_me.task.audiolistener.data.responses;

import com.google.gson.annotations.SerializedName;
import com.just_me.task.audiolistener.data.models.Track;

import java.util.ArrayList;

/**
 * Created by USER on 29-Aug-17.
 */

public class TrackResp {
    @SerializedName("track")
    public ArrayList<Track> tracks;
}
