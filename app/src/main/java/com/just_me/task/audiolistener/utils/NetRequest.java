package com.just_me.task.audiolistener.utils;

import com.just_me.task.audiolistener.data.responses.AlbumTracksResp;
import com.just_me.task.audiolistener.data.responses.TopAlbumsResp;
import com.just_me.task.audiolistener.data.responses.TopArtistsResp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.just_me.task.audiolistener.Constants.API_KEY;

/**
 * Created by USER on 28-Aug-17.
 */

public interface NetRequest {

    @GET("?method=geo.gettopartists&api_key=" + API_KEY + "&format=json")
    Call<TopArtistsResp> getTopArtists(@Query("country") String country);

    @GET("?method=artist.gettopalbums&api_key=" + API_KEY + "&format=json")
    Call<TopAlbumsResp> getTopAlbums(@Query("artist") String artist);

    @GET("?method=album.getinfo&api_key=" + API_KEY + "&format=json")
    Call<AlbumTracksResp> getAlbumRecords(@Query("artist") String artist, @Query("album") String album);

}
