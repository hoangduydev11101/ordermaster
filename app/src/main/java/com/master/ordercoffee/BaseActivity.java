package com.master.ordercoffee;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.uber.rib.core.RibActivity;
import com.uber.rib.core.ViewRouter;

public class BaseActivity extends AppCompatActivity {

//    @SuppressWarnings("unchecked")
//    @Override
//    protected ViewRouter<?, ?, ?> createRouter(ViewGroup parentViewGroup) {
//        RootBuilder rootBuilder = new RootBuilder(new RootBuilder.ParentComponent() {
//        });
//        return rootBuilder.build(parentViewGroup);
//    }

    protected boolean isActive;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTheme(R.style.MainStyle);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isActive = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isActive = true;
    }
}
