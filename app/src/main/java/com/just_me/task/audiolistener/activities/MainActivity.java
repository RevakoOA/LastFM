package com.just_me.task.audiolistener.activities;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.just_me.task.audiolistener.Constants;
import com.just_me.task.audiolistener.MainApplication;
import com.just_me.task.audiolistener.R;
import com.just_me.task.audiolistener.data.responses.TopArtistsResp;
import com.just_me.task.audiolistener.fragments.ListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by USER on 28-Aug-17.
 */

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    @BindView(R.id.rl_full_content)
    RelativeLayout rlFullContent;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    ImageView ivMenu;
    @BindView(R.id.fl_content)
    FrameLayout flContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        ButterKnife.bind(this);
        setActionBar();
        setBackButtonEnabled(false);
        setAppBarName("Top Artists", false);
        loadArtists(Constants.Country.UKRAINE.toString());
    }

    private void setActionBar() {
        toolbar.setContentInsetsAbsolute(0, 0);
        this.setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar == null) {
            Log.wtf(TAG, "action bar is null");
        }
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.action_bar_home, toolbar, false);
        ivMenu = mCustomView.findViewById(R.id.iv_button_options);
        pbToolBar = mCustomView.findViewById(R.id.pb_toolbar);
        pbToolBar.getIndeterminateDrawable()
                .setColorFilter(getResources().getColor(R.color.colorAccent),
                        PorterDuff.Mode.SRC_IN);
        Toolbar.LayoutParams layoutParams = new Toolbar.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT,
                Toolbar.LayoutParams.MATCH_PARENT);
        tvCenteredAppBar = mCustomView.findViewById(R.id.tv_center_appBar);
        ivBackButton = mCustomView.findViewById(R.id.iv_back_button);
        ivBackButton.setOnClickListener(view -> onBackPressed());
        ivMenu.setOnClickListener(view -> showPopupMenu(ivMenu));

        actionBar.setCustomView(mCustomView, layoutParams);
    }


    public void showPopupMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        Menu menu = popupMenu.getMenu();
        Constants.Country[] countries = Constants.Country.values();
        for (int i = 0; i < countries.length; i++) {
            menu.add(countries[i].toString());
        }
        popupMenu.setOnMenuItemClickListener(item -> {
            loadArtists(item.getTitle().toString());
            return true;
        });
        popupMenu.show();
    }

    private void loadArtists(String country) {
        String appBarName = getAppBarName();
        setAppBarName("Loading...", true);
        ((MainApplication)getApplication()).netRequest.getTopArtists(country).enqueue(
                new Callback<TopArtistsResp>() {
                    @Override
                    public void onResponse(Call<TopArtistsResp> call, Response<TopArtistsResp> response) {
                        setAppBarName(appBarName, false);
                        Log.d(TAG, response.toString());
                        ListFragment listFragment = ListFragment.newInstance(Constants.Country.fromString(country), response.body().topArtists);
                        showFragment(listFragment, true);
                    }

                    @Override
                    public void onFailure(Call<TopArtistsResp> call, Throwable t) {
                        setAppBarName(appBarName, false);
                        Log.e(TAG, t.toString());
                    }
                }
        );
    }
}
