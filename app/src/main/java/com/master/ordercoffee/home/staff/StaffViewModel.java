package com.master.ordercoffee.home.staff;

import android.content.Context;

import com.master.ordercoffee.model.Order;
import com.master.ordercoffee.service.DataChangeListener;
import com.master.ordercoffee.service.FirebaseService;

import java.util.List;

public class StaffViewModel {

    private static StaffViewModel instance = null;

    private static Context mContext;


    public static StaffViewModel getInstance(Context context) {
        mContext = context;
        if (instance == null)
            instance = new StaffViewModel();

        return instance;
    }

    private StaffViewModel() {

    }

    public void getListOrder () {
        FirebaseService.getInstance().getListOrder(new DataChangeListener<Order>() {
            @Override
            public void onListDataSuccess(List<Order> listData) {
                super.onListDataSuccess(listData);
            }

            @Override
            public void onDataFailed(Exception e) {
                super.onDataFailed(e);
            }
        });
    }

}
