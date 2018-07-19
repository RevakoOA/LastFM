package com.just_me.task.audiolistener.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.just_me.task.audiolistener.Constants;
import com.just_me.task.audiolistener.MainApplication;
import com.just_me.task.audiolistener.R;
import com.just_me.task.audiolistener.adapters.AlbumAdpt;
import com.just_me.task.audiolistener.adapters.ArtistAdpt;
import com.just_me.task.audiolistener.data.models.Album;
import com.just_me.task.audiolistener.data.models.Artist;
import com.just_me.task.audiolistener.data.models.TopArtists;
import com.just_me.task.audiolistener.data.responses.AlbumTracksResp;
import com.just_me.task.audiolistener.data.responses.TopAlbums;
import com.just_me.task.audiolistener.data.responses.TopAlbumsResp;
import com.just_me.task.audiolistener.utils.General;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.just_me.task.audiolistener.utils.General.gson;

/**
 * Created by USER on 28-Aug-17.
 */

public class ListFragment extends BaseFragment {
    private static final String TAG = "ListFragment";
    private static final String DATA = "data";
    private static final String COUNTRY = "country";

    @BindView(R.id.rv)
    RecyclerView rv;

    private Constants.Country selectedCountry;
    private ArtistAdpt artistAdpt;
    private TopArtists artistsResp;
    private TopAlbums topAlbums;
    private AlbumAdpt albumAdpt;

    private boolean isForAlbum = false;
    private Artist selectedArtist = null;

    private General.Callback<Artist> artistCallback = artist -> {
        isForAlbum = true;
        selectedArtist = artist;
        updateTitle();
        loadAlbums(artist.name);
    };

    private General.Callback<Album> albumCallback = album -> {
        loadTracks(selectedArtist.name, album.name);
    };

    public static ListFragment newInstance(Constants.Country country, TopArtists resp) {

        Bundle args = new Bundle();

        ListFragment fragment = new ListFragment();
        args.putString(DATA, gson.toJson(resp));
        args.putSerializable(COUNTRY, country);
        fragment.artistsResp = resp;
        fragment.selectedCountry = country;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null && artistsResp == null) {
            artistsResp = gson.fromJson(savedInstanceState.getString(DATA), TopArtists.class);
            selectedCountry = (Constants.Country) savedInstanceState.getSerializable(COUNTRY);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        updateTitle();
        updateContent();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.frag_recycler, container, false);
        ButterKnife.bind(this, result);
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        return result;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateArtists(artistsResp.artists);
    }

    public void updateArtists(ArrayList<Artist> artists) {
        if (artistAdpt == null) {
            artistAdpt = new ArtistAdpt(artists);
            artistAdpt.setCallback(artistCallback);
        }
        rv.setAdapter(artistAdpt);
        artistAdpt.setArtists(artists);
        artistAdpt.notifyDataSetChanged();
        rv.scrollToPosition(0);
    }

    public void updateAlbums(ArrayList<Album> albums) {
        if (albumAdpt == null) {
            albumAdpt = new AlbumAdpt(albums);
            albumAdpt.setCallback(albumCallback);
        }
        rv.setAdapter(albumAdpt);
        albumAdpt.setAlbums(albums);
        albumAdpt.notifyDataSetChanged();
        rv.scrollToPosition(0);
    }

    public void updateTitle() {
        if (isForAlbum) {
            activity.setAppBarName(selectedArtist.name, false);
        } else {
            activity.setAppBarName(selectedCountry.name(), false);
        }
    }

    public void updateContent() {
        if (isForAlbum) {
            updateAlbums(topAlbums.albums);
        } else {
            updateArtists(artistsResp.artists);
        }
    }

    private void loadAlbums(String artist) {
        activity.setLoading("Loading...");
        ((MainApplication)activity.getApplication()).netRequest.getTopAlbums(artist).enqueue(
                new Callback<TopAlbumsResp>() {
                    @Override
                    public void onResponse(Call<TopAlbumsResp> call, Response<TopAlbumsResp> response) {
                        activity.restoreAppBarName();
                        topAlbums = response.body().topAlbums;
                        updateAlbums(topAlbums.albums);
                    }

                    @Override
                    public void onFailure(Call<TopAlbumsResp> call, Throwable t) {
                        activity.restoreAppBarName();
                        Log.e(TAG, t.toString());
                    }
                }
        );
    }

    private void loadTracks(String artist, String album) {
        activity.setLoading("Loading...");
        ((MainApplication)activity.getApplication()).netRequest.getAlbumRecords(artist, album).enqueue(
                new Callback<AlbumTracksResp>() {
                    @Override
                    public void onResponse(Call<AlbumTracksResp> call, Response<AlbumTracksResp> response) {
                        activity.restoreAppBarName();
                        Album album = response.body().album;
                        AlbumFragment albumFragment = AlbumFragment.newInstance(album);
                        activity.showFragment(albumFragment, true);
                    }

                    @Override
                    public void onFailure(Call<AlbumTracksResp> call, Throwable t) {
                        activity.restoreAppBarName();
                        Log.e(TAG, t.toString());
                    }
                }
        );
    }

    @Override
    public boolean onBackPressed() {
        if (isForAlbum) {
            selectedArtist = null;
            isForAlbum = false;
            updateTitle();
            updateContent();
            return true;
        } else {
            return false;
        }
    }
}
