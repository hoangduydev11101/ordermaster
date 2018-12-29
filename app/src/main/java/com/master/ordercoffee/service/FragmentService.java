package com.master.ordercoffee.service;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.master.ordercoffee.R;

public class FragmentService {

    private static Context mContext;
    private static FragmentService instance = null;

    private FragmentTransaction mTransaction;
    private FragmentManager mFragmentManager;

    public static FragmentService getInstance(Context context) {
        mContext = context;

        if (instance == null)
            instance = new FragmentService();

        return instance;
    }


    public FragmentService() {
        mFragmentManager = ((FragmentActivity)mContext).getSupportFragmentManager();
//        mTransaction = ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction();
    }


    public void clearInstance() {
        instance = null;
    }

    public void pushFragment(int viewId, Fragment fragment, String tag) {
        if (fragment == null)
            return;

        mTransaction = mFragmentManager.beginTransaction();
        mTransaction.add(viewId, fragment);
        mTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_left);
        mTransaction.addToBackStack(tag);
        mTransaction.commit();
    }

    public void pushFragment(int viewId, Fragment fragment) {
        if (fragment == null || mTransaction == null)
            return;

        mTransaction = ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction();
        mTransaction.add(viewId, fragment);
        mTransaction.setCustomAnimations(R.anim.anim_in_view, R.anim.anim_out_view, R.anim.anim_in_view, R.anim.anim_out_view);
        mTransaction.addToBackStack(null);
        mTransaction.commit();
    }
}
