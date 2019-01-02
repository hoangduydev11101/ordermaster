package com.master.ordercoffee.utils;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.master.ordercoffee.R;

public class AnimationUtil {

    public enum ToGravity {LEFT, TOP, RIGHT, BOTTOM, SCALE}

    private static Animation mSlideInLeft;
    private static Animation mSlideOutRight;
    private static Animation mSlideIn;
    private static Animation mSlideOut;

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

    public static void showView (Context context, View view, ToGravity toGravity) {
        switch (toGravity) {
            case TOP:
                mSlideIn = AnimationUtils.loadAnimation(context, R.anim.slide_up);
                break;
            case LEFT:
                mSlideIn = AnimationUtils.loadAnimation(context, R.anim.slide_in_left);
                break;
            case RIGHT:
                mSlideIn = AnimationUtils.loadAnimation(context, R.anim.slide_out_right);
                break;
            case BOTTOM:
                mSlideIn = AnimationUtils.loadAnimation(context, R.anim.slide_down);
                break;
            case SCALE:
                mSlideIn = AnimationUtils.loadAnimation(context, R.anim.scale_in);
                break;
        }

        if (view.getVisibility() != View.VISIBLE) {
            view.startAnimation(mSlideIn);
            Utils.runOnUiThread(() -> view.setVisibility(View.VISIBLE), 250);
        }
    }

    public static void hideView (Context context, View view, ToGravity toGravity) {
        switch (toGravity) {
            case TOP:
                mSlideOut = AnimationUtils.loadAnimation(context, R.anim.slide_up);
                break;
            case LEFT:
                mSlideOut = AnimationUtils.loadAnimation(context, R.anim.slide_in_left);
                break;
            case RIGHT:
                mSlideOut = AnimationUtils.loadAnimation(context, R.anim.slide_out_right);
                break;
            case BOTTOM:
                mSlideOut = AnimationUtils.loadAnimation(context, R.anim.slide_down);
                break;
            case SCALE:
                mSlideOut = AnimationUtils.loadAnimation(context, R.anim.scale_out);
                break;
        }

        if (view.getVisibility() == View.VISIBLE) {
            view.startAnimation(mSlideOut);
            Utils.runOnUiThread(() -> view.setVisibility(View.INVISIBLE), 250);
        }
    }

}
