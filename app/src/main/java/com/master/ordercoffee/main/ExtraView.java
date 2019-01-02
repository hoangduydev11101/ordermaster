package com.master.ordercoffee.main;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.master.ordercoffee.R;
import com.master.ordercoffee.service.UserService;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExtraView extends LinearLayout {

    private Context mContext;

    @OnClick(R.id.tv_my_store)
    void myStoreClicked() {

    }

    @OnClick(R.id.tv_logout)
    void logoutClicked() {
        UserService.clearCurrentUser(mContext);
        FirebaseAuth.getInstance().signOut();
    }

    public ExtraView(Context context) {
        super(context);
        initView(context);
    }

    public ExtraView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ExtraView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        View view = inflate(context, R.layout.view_main_extra, null);
        addView(view, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ButterKnife.bind(this, view);

    }
}
