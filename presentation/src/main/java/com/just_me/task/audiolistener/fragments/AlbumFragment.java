package com.just_me.task.audiolistener.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.just_me.task.audiolistener.R;
import com.just_me.task.audiolistener.adapters.AlbumAdpt;
import com.just_me.task.audiolistener.adapters.TrackAdpt;
import com.just_me.task.audiolistener.data.models.Album;
import com.just_me.task.audiolistener.data.models.Image;
import com.just_me.task.audiolistener.data.models.Track;
import com.just_me.task.audiolistener.utils.General;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.just_me.task.audiolistener.utils.General.gson;

/**
 * Created by USER on 29-Aug-17.
 */

public class AlbumFragment extends BaseFragment {
    private static final String TAG = "AlbumFragment";
    private static final String ALBUM_DATA = "album_data";

    @BindView(R.id.iv_album)
    ImageView ivAlbum;
    @BindView(R.id.rv_tracks)
    RecyclerView rvTracks;

    private TrackAdpt trackAdpt;
    private Album album;

    private General.Callback<Track> callback = track -> {

    };

    public static AlbumFragment newInstance(Album album) {

        Bundle args = new Bundle();

        AlbumFragment fragment = new AlbumFragment();

        fragment.album = album;
        args.putString(ALBUM_DATA, gson.toJson(album));

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (album == null && savedInstanceState != null) {
            album = gson.fromJson(savedInstanceState.getString(ALBUM_DATA), Album.class);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        activity.setAppBarName(album.name, false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.frag_abum, container, false);
        ButterKnife.bind(this, result);
        rvTracks.setLayoutManager(new LinearLayoutManager(rvTracks.getContext()));
        rvTracks.setNestedScrollingEnabled(false);
        return result;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Picasso.with(view.getContext()).load(album.getImage(Image.sizes[2]).url).placeholder(R.drawable.img_placeholder).into(ivAlbum);
        updateTracks(album.tracks.tracks);
    }

    public void updateTracks(ArrayList<Track> tracks) {
        if (trackAdpt == null) {
            trackAdpt = new TrackAdpt(tracks, rvTracks);
            trackAdpt.setCallback(callback);
        }
        rvTracks.setAdapter(trackAdpt);
        trackAdpt.setTracks(tracks);
        trackAdpt.notifyDataSetChanged();
        rvTracks.scrollToPosition(0);
    }
}
