package com.just_me.task.audiolistener.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just_me.task.audiolistener.R;
import com.just_me.task.audiolistener.data.models.Album;
import com.just_me.task.audiolistener.utils.General;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by USER on 28-Aug-17.
 */

public class AlbumAdpt extends RecyclerView.Adapter<AlbumAdpt.AlbumVH> {

    private ArrayList<Album> albums = new ArrayList<>();
    private General.Callback<Album> callback;

    public AlbumAdpt(ArrayList<Album> albums) {
        this.albums = albums;
    }

    public void setAlbums(ArrayList<Album> albums) {
        this.albums = albums;
    }

    public void setCallback(General.Callback<Album> callback) {
        this.callback = callback;
    }

    @Override
    public AlbumVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new AlbumVH(view);
    }

    @Override
    public void onBindViewHolder(AlbumVH holder, int position) {
        Album album = albums.get(position);
        holder.tvName.setText(album.name);
        holder.tvDesc.setText("(" + album.playcount + " playCounts)");
        String url;
        if (holder.iv != null && album.image != null && !album.image.isEmpty()) {
            url = album.image.get(album.image.size()/2).url;
            if (url != null && !url.isEmpty()) {
                Picasso.with(holder.iv.getContext()).load(url).placeholder(R.drawable.img_placeholder).into(holder.iv);
            }
        }
        holder.llFullContent.setOnClickListener(view -> {
            callback.selected(album);
        });
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public static class AlbumVH extends RecyclerView.ViewHolder {

        @BindView(R.id.ll_full_content)
        LinearLayout llFullContent;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_description)
        TextView tvDesc;
        @BindView(R.id.iv)
        ImageView iv;


        AlbumVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
