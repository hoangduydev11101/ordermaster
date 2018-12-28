package com.master.ordercoffee;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.master.ordercoffee.root.RootBuilder;
import com.uber.rib.core.RibActivity;
import com.uber.rib.core.ViewRouter;

public class BaseActivity extends RibActivity {

    @SuppressWarnings("unchecked")
    @Override
    protected ViewRouter<?, ?, ?> createRouter(ViewGroup parentViewGroup) {
        RootBuilder rootBuilder = new RootBuilder(new RootBuilder.ParentComponent() {
        });
        return rootBuilder.build(parentViewGroup);
    }
}
