package com.master.ordercoffee.service;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.master.ordercoffee.model.Order;
import com.master.ordercoffee.model.Reservation;
import com.master.ordercoffee.model.Store;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.master.ordercoffee.utils.Constants.PROJECT_ID;

public class FirebaseService {

    private static FirebaseService instance;

    private FirebaseFirestore mDatabase;

    public static FirebaseService getInstance() {
        if (instance == null)
            instance = new FirebaseService();

        return instance;
    }

    private FirebaseService() {
        mDatabase = FirebaseFirestore.getInstance();
    }

    public void addOrder(Order order) {

    }

    public void getListStore(DataChangeListener<Store> listener) {
        if (mDatabase == null)
            mDatabase = FirebaseFirestore.getInstance();

        mDatabase.collection("Store")
                .get()
                .addOnCompleteListener(task -> {
                    if (task != null && task.isSuccessful()) {
                        List<Store> list = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Store store = document.toObject(Store.class);
                            if (store != null)
                                list.add(store);
                        }
                        listener.onListDataSuccess(list);
                    } else {
                        listener.onDataFailed(task.getException());
                    }
                });
    }

    public void getListOrder(DataChangeListener<Order> listener) {

    }

    public void getListReservation(DataChangeListener<Reservation> listener) {
        listener.onDataSuccess(null);
    }

}
