package com.just_me.task.audiolistener;

import android.app.Application;

import com.just_me.task.audiolistener.utils.NetRequest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.just_me.task.audiolistener.Constants.BASE_URL;

/**
 * Created by USER on 28-Aug-17.
 */

public class MainApplication extends Application {

    public Retrofit retrofit;
    public NetRequest netRequest;

    @Override
    public void onCreate() {
        super.onCreate();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        netRequest = retrofit.create(NetRequest.class);
    }
}
