package com.master.ordercoffee.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.master.ordercoffee.R;
import com.master.ordercoffee.service.FragmentService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Toolbar extends RelativeLayout {

    private String mTbLable;
    private int mTbBackground;
    private int mTbTextColor;
    private Drawable mTbIconClose;
    private Drawable mTbIconAction;
    private boolean mTbEnabledFinish;

    private Context mContext;

    @BindView(R.id.img_close)
    ImageView mIconClose;
    @BindView(R.id.img_action)
    ImageView mIconAction;
    @BindView(R.id.tv_lable)
    TextView mLable;

    @OnClick(R.id.img_close)
    void closeViewlicked() {
        ((Activity)mContext).onBackPressed();
    }

    public Toolbar(Context context) {
        super(context);
        initAttributes(null, 0);
        initView(context);
    }

    public Toolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttributes(attrs, 0);
        initView(context);
    }

    public Toolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributes(attrs, defStyleAttr);
        initView(context);
    }

    private void initAttributes (AttributeSet attributeSet, int defStyle) {
        final TypedArray a = getContext().obtainStyledAttributes(attributeSet, R.styleable.ToolbarView, defStyle, 0);

        mTbLable = a.getString(R.styleable.ToolbarView_tb_lable);
        mTbTextColor = a.getInt(R.styleable.ToolbarView_tb_lableColor, defStyle);
        mTbBackground = a.getInt(R.styleable.ToolbarView_tb_background, defStyle);
        mTbEnabledFinish = a.getBoolean(R.styleable.ToolbarView_tb_action_finish, false);

        if (a.hasValue(R.styleable.ToolbarView_tb_iconClose)) {
            mTbIconClose = a.getDrawable(R.styleable.ToolbarView_tb_iconClose);
        }
        if (a.hasValue(R.styleable.ToolbarView_tb_iconAction)) {
            mTbIconAction = a.getDrawable(R.styleable.ToolbarView_tb_iconAction);
        }

        a.recycle();
    }

    private void initView(Context context) {
        mContext = context;
        View view = inflate(mContext, R.layout.view_custom_toolbar, null);
        addView(view, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        if (mTbIconClose != null) {
            mIconClose.setImageDrawable(mTbIconClose);
        }
        if (mTbIconAction != null) {
            mIconAction.setVisibility(VISIBLE);
            mIconAction.setImageDrawable(mTbIconAction);
        }
        if (!TextUtil.stringIsNullOrEmpty(mTbLable)) {
            mLable.setText(mTbLable);
        }
    }
}
