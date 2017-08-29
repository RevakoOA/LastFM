package com.just_me.task.audiolistener.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.just_me.task.audiolistener.R;
import com.just_me.task.audiolistener.fragments.BaseFragment;
import com.just_me.task.audiolistener.utils.General;
import com.transitionseverywhere.ChangeText;
import com.transitionseverywhere.TransitionManager;

import java.util.Stack;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;


/**
 * BaseActivity which will have useful methods used across other activities and fragments.
 *
 * @author Ratnadeep Rakshit <qedrix@gmail.com>
 */

public class BaseActivity extends AppCompatActivity implements General.BackHandlerInterface {

    private static final String TAG = "BaseActivity";
    protected ActionBar actionBar;
    protected TextView tvCenteredAppBar;
    protected MaterialProgressBar pbToolBar;
    protected ImageView ivBackButton;
    private ProgressDialog pdLoading;
    protected BaseFragment selectedFragment;
    protected Stack<String> appBarNames = new Stack<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Showing a fragment with delay
     *
     * @param resId, Fragment fragment, boolean delayed
     */
    protected void showFragment(final int resId, final BaseFragment newFragment,
                                long delayedMillis, boolean addToBackStack) {
        if (delayedMillis > 0) {
            new Handler().postDelayed(() -> showFragment(resId, newFragment, addToBackStack), delayedMillis);
        } else {
            showFragment(resId, newFragment, addToBackStack);
        }
    }

    /**
     * Replacing the fragment with another fragment
     *
     * @param resId, fragment
     */
    protected void showFragment(int resId, BaseFragment newFragment, boolean addToBackStack) {
        selected(newFragment);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(resId, newFragment);
        if (addToBackStack) {
            ft.addToBackStack(null);
        }
        ft.commitAllowingStateLoss();
    }

    public void setLoading(String newText) {
        appBarNames.push(getAppBarName());
        setAppBarName(newText, true);
    }

    public void restoreAppBarName() {
        String lastName = "";
        if (!appBarNames.isEmpty()) {
            lastName = appBarNames.pop();
        }
        setAppBarName(lastName, false);
    }

    public void setAppBarName(String newText, boolean isForLoading) {
        if (!isForLoading) {
            appBarNames.push(newText);
        }
        if (tvCenteredAppBar != null && !tvCenteredAppBar.getText().toString().equals(newText)) {
            TransitionManager.beginDelayedTransition((ViewGroup) actionBar.getCustomView(),
                    new ChangeText());
            tvCenteredAppBar.setText(newText);
        }
        pbToolBar.setVisibility((isForLoading) ? View.VISIBLE : View.INVISIBLE);
    }

    public String getAppBarName() {
        String name = "";
        if (tvCenteredAppBar != null) {
            name = tvCenteredAppBar.getText().toString();
        }
        return name;
    }

    public void setBackButtonEnabled(boolean enabled) {
        if (ivBackButton != null) {
            ivBackButton.setVisibility((enabled) ? View.VISIBLE : View.GONE);
        }
    }

    protected void showFragment(BaseFragment newFragment, long millisDelay, boolean addToBackStack) {
        showFragment(R.id.fl_content, newFragment, millisDelay, addToBackStack);
    }

    public void showFragment(BaseFragment newFragment, boolean addToBackStack) {
        showFragment(R.id.fl_content, newFragment, 0, addToBackStack);
    }

    @Override
    protected void onPause() {
        if (pdLoading != null) {
            pdLoading.dismiss();
            pdLoading = null;
        }
        super.onPause();
    }

    @Override
    public void selected(BaseFragment backHandledFragment) {
        Log.d(TAG, "setSelectedFragment " + backHandledFragment);
        selectedFragment = backHandledFragment;
        selectedFragment.restoreUI();
    }

    @Override
    public void onBackPressed() {
        if (selectedFragment == null || !selectedFragment.onBackPressed()) {
            // Selected fragment did not consume the back press event.
            int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
            if (backStackEntryCount > 1) {
                selected((BaseFragment) getSupportFragmentManager().getFragments().get(backStackEntryCount-2));
                super.onBackPressed();
            }
        }
    }
}
