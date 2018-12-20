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

    public HomeViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
        mListTitle = new String[]{mContext.getString(R.string.s_staff), mContext.getString(R.string.s_chef), mContext.getString(R.string.s_admin)};
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0: return new StaffFragment();
            case 1: return new ChefFragment();
            case 2: return new AdminFragment();
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
