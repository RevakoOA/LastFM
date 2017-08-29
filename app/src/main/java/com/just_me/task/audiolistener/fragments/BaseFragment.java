package com.just_me.task.audiolistener.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.just_me.task.audiolistener.activities.BaseActivity;

/**
 * Created by ratnadeep on 30/5/17.
 * A simple {@link Fragment} subclass.
 */

public abstract class BaseFragment extends Fragment {

    private static final String TAG = "BaseFragment";

    protected ActionBar mActionBar;
    protected ProgressDialog mProgressDialog;
    protected BaseActivity activity;

    protected View mParentView;

    public String getFragmentName() {
        return "BaseFragment";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActionBar = ((BaseActivity) getActivity()).getSupportActionBar();
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (BaseActivity) context;
    }

    @Override
    public void onDetach() {
        activity = null;
        super.onDetach();
    }

    /**
     * {@inheritDoc}
     *  Should be OVERRIDE to work
     *  activity will shut this callback in currently active fragment
     * @return boolean - true if the event is consumed, else otherwise
     */
    public boolean onBackPressed() {
        return false;
    }

    public void restoreUI() {

    }

}
