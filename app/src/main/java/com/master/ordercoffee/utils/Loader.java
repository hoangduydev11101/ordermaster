package com.master.ordercoffee.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;

import com.master.ordercoffee.R;

public class Loader {

    private static Dialog loading = null;

    public static void start(final Context activity) {
        if (activity == null || (loading != null && loading.isShowing()))
            return;

        if (!((Activity) activity).isFinishing()) {
            Utils.runOnUiThread(() -> {
                if (loading != null) {
                    loading.dismiss();
                    loading = null;
                }

                loading = new Dialog(activity, R.style.CircularProgress);
                loading.setContentView(R.layout.view_loading);
                loading.setCancelable(false);
                loading.show();
            });
        }
    }

    public static void stop(Context activity) {
        if (activity == null || (loading != null && !loading.isShowing())) {
            return;
        }
        if (!((Activity) activity).isFinishing()) {
            Utils.runOnUiThread(() -> {
                if (loading != null)
                    loading.dismiss();
            });
        }
    }
}
