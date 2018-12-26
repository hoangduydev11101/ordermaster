package com.master.ordercoffee.service;

import com.google.firebase.firestore.FirebaseFirestore;

import static com.master.ordercoffee.utils.Constants.PROJECT_ID;

public class FirebaseService {

    private static FirebaseService instance;

    FirebaseFirestore mDatabase;

    public static FirebaseService getInstance() {
        if (instance == null)
            instance = new FirebaseService();

        return instance;
    }

    private FirebaseService() {
        mDatabase = FirebaseFirestore.getInstance();
    }

    public void addOrder() {

    }

}
