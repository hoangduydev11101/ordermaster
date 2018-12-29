package com.master.ordercoffee.utils;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.master.ordercoffee.R;

public class AnimationUtil {

    private static Animation mSlideInLeft;
    private static Animation mSlideOutRight;

    public static void showView (Context context, View view) {
        if (mSlideInLeft == null) {
            mSlideInLeft = AnimationUtils.loadAnimation(context, R.anim.slide_in_right);
        }
        if (mSlideOutRight == null) {
            mSlideOutRight = AnimationUtils.loadAnimation(context, R.anim.slide_out_left);
        }

        if (view.getVisibility() != View.VISIBLE) {
            view.startAnimation(mSlideInLeft);
            Utils.runOnUiThread(() -> view.setVisibility(View.VISIBLE), 250);
        }
    }

    public static void hideView (Context context, View view) {
        if (mSlideInLeft == null) {
            mSlideInLeft = AnimationUtils.loadAnimation(context, R.anim.slide_in_left);
        }
        if (mSlideOutRight == null) {
            mSlideOutRight = AnimationUtils.loadAnimation(context, R.anim.slide_out_right);
        }

        if (view.getVisibility() == View.VISIBLE) {
            view.startAnimation(mSlideOutRight);
            Utils.runOnUiThread(() -> view.setVisibility(View.INVISIBLE), 250);
        }
    }

}
