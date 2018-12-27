package com.master.ordercoffee.main;

import android.content.Context;

import com.master.ordercoffee.model.Store;
import com.master.ordercoffee.service.DataChangeListener;
import com.master.ordercoffee.service.FirebaseService;

import java.util.List;

public class MainViewModel {
    private static MainViewModel instance = null;
    private static Context mContext;
    private DataChangeListener<Store> mListner;

    public static MainViewModel getInstance(Context context) {
        mContext = context;

        if (instance == null)
            instance = new MainViewModel();

        return instance;
    }

    public MainViewModel() {

    }

    public void setOnMainModelListener (DataChangeListener<Store> listener) {
        mListner = listener;
    }

    public void getData () {
        FirebaseService.getInstance().getListStore(new DataChangeListener<Store>() {
            @Override
            public void onListDataSuccess(List<Store> listData) {
                super.onListDataSuccess(listData);
                if (listData != null && listData.size() != 0) {
                    if (mListner != null) {
                        mListner.onListDataSuccess(listData);
                    }
                }
            }

            @Override
            public void onDataFailed(Exception e) {
                super.onDataFailed(e);
                if (mListner != null && e != null) {
                    mListner.onDataFailed(e);
                }
            }
        });
    }
}
