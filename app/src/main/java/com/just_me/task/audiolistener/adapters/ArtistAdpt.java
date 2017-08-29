package com.just_me.task.audiolistener.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just_me.task.audiolistener.R;
import com.just_me.task.audiolistener.data.models.Artist;
import com.just_me.task.audiolistener.utils.General;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by USER on 28-Aug-17.
 */

public class ArtistAdpt extends RecyclerView.Adapter<ArtistAdpt.ArtistVH> {

    private ArrayList<Artist> artists = new ArrayList<>();
    private General.Callback<Artist> callback;

    public ArtistAdpt(ArrayList<Artist> artists) {
        this.artists = artists;
    }

    public void setArtists(ArrayList<Artist> artists) {
        this.artists = artists;
    }

    public void setCallback(General.Callback<Artist> callback) {
        this.callback = callback;
    }

    @Override
    public ArtistVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ArtistVH(view);
    }

    @Override
    public void onBindViewHolder(ArtistVH holder, int position) {
        Artist artist = artists.get(position);
        holder.tvName.setText(artist.name);
        holder.tvDesc.setText("(" + artist.listenersCount + " listeners)");
        Picasso.with(holder.iv.getContext()).load(artist.image.get(artist.image.size()/2).url).placeholder(R.drawable.img_placeholder).into(holder.iv);
        holder.llFullContent.setOnClickListener(view -> {
            if (callback != null) {
                callback.selected(artist);
            }
        });
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }

    public static class ArtistVH extends RecyclerView.ViewHolder {

        @BindView(R.id.ll_full_content)
        LinearLayout llFullContent;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_description)
        TextView tvDesc;
        @BindView(R.id.iv)
        ImageView iv;


        ArtistVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
