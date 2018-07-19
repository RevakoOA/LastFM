package com.just_me.task.audiolistener.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just_me.task.audiolistener.R;
import com.just_me.task.audiolistener.data.models.Track;
import com.just_me.task.audiolistener.utils.General;

import java.util.ArrayList;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by USER on 28-Aug-17.
 */

public class TrackAdpt extends RecyclerView.Adapter<TrackAdpt.TrackVH> {

    private ArrayList<Track> tracks = new ArrayList<>();
    private General.Callback<Track> callback;

    @BindColor(R.color.main_backgnd)
    int cMainBgd;
    @BindColor(R.color.main_backgnd_second)
    int cMainBgd2;

    public TrackAdpt(ArrayList<Track> tracks, View context) {
        this.tracks = tracks;
        ButterKnife.bind(this, context);
    }

    public void setTracks(ArrayList<Track> tracks) {
        this.tracks = tracks;
    }

    public void setCallback(General.Callback<Track> callback) {
        this.callback = callback;
    }

    @Override
    public TrackVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new TrackVH(view);
    }

    @Override
    public void onBindViewHolder(TrackVH holder, int position) {
        Track track = tracks.get(position);
        holder.llFullContent.setBackgroundColor((position % 2 == 0)?cMainBgd:cMainBgd2);
        holder.iv.setVisibility(View.GONE);
        holder.tvName.setText(track.name);
        holder.tvDesc.setText("(" + track.duration + " seconds)");
        holder.llFullContent.setOnClickListener(view -> {
            if (callback != null) {
                callback.selected(track);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

    public static class TrackVH extends RecyclerView.ViewHolder {

        @BindView(R.id.ll_full_content)
        LinearLayout llFullContent;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_description)
        TextView tvDesc;
        @BindView(R.id.iv)
        ImageView iv;


        TrackVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
