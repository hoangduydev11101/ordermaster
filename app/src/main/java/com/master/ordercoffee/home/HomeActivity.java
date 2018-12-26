package com.master.ordercoffee.home;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.master.ordercoffee.BaseActivity;
import com.master.ordercoffee.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity {

    private HomeViewPagerAdapter mAdapter;


    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.sliding_tabs)
    TabLayout mTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        initViewPager();
    }

    private void initViewPager() {
        mAdapter = new HomeViewPagerAdapter(this, getSupportFragmentManager());
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(mAdapter);
        mTab.setupWithViewPager(mViewPager);
    }
}
