package com.just_me.task.audiolistener.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.just_me.task.audiolistener.data.models.Album;
import com.just_me.task.audiolistener.data.models.Artist;
import com.just_me.task.audiolistener.fragments.BaseFragment;

import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by USER on 28-Aug-17.
 */

public class General {

    public static Gson gson = new Gson();
    public interface BackHandlerInterface {
        void selected(BaseFragment backHandledFragment);
    }

    public interface Callback<T> {
        void selected(T element);
    }

}
