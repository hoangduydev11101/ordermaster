package com.master.ordercoffee.service;

public class FirebaseService {

    private static FirebaseService instance;

    public static FirebaseService getInstance() {
        if (instance == null)
            instance = new FirebaseService();

        return instance;
    }
}
