package com.master.ordercoffee.home;

import android.content.Context;

import com.master.ordercoffee.model.Store;
import com.master.ordercoffee.model.User;
import com.master.ordercoffee.service.DataChangeListener;
import com.master.ordercoffee.service.FirebaseService;

public class HomeViewModel {

    private static HomeViewModel instance = null;
    private static Context mContext;
    private Store mCurrentStore;

    public static HomeViewModel getInstance(Context context) {
        mContext = context;

        if (instance == null)
            instance = new HomeViewModel();

        return instance;
    }

    private HomeViewModel() {

    }

    public void setCurrentStore(Store store) {
        mCurrentStore = store;
    }

    public Store getCurrentStore() {
        return mCurrentStore;
    }

    public void invitaionUser(String phone) {
        if (phone.startsWith("0"))
            phone = phone.replaceFirst("0", "+84");

        FirebaseService.getInstance().getUserFromPhone(phone, new DataChangeListener<User>() {
            @Override
            public void onDataSuccess(User data) {
                super.onDataSuccess(data);
            }
        });
    }

}
