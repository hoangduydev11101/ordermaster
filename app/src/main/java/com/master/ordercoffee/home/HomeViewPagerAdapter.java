package com.master.ordercoffee.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.master.ordercoffee.R;

public class HomeViewPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private String[] mListTitle;

    private StaffFragment mStaffView;
    private ChefFragment mChefView;
    private AdminFragment mAdminView;

    private void initAllFragment() {
        mStaffView = new StaffFragment();
        mChefView = new ChefFragment();
        mAdminView = new AdminFragment();
    }

    public HomeViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
        mListTitle = new String[]{mContext.getString(R.string.s_staff), mContext.getString(R.string.s_chef), mContext.getString(R.string.s_admin)};
        initAllFragment();
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0: return mStaffView;
            case 1: return mChefView;
            case 2: return mAdminView;
        }
        return new StaffFragment();
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mListTitle[position];
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }
}
