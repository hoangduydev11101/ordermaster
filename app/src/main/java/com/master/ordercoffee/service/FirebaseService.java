package com.master.ordercoffee.service;

import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.master.ordercoffee.model.Order;
import com.master.ordercoffee.model.Reservation;
import com.master.ordercoffee.model.Store;
import com.master.ordercoffee.model.User;
import com.master.ordercoffee.utils.Logger;
import com.master.ordercoffee.utils.TextUltil;
import com.master.ordercoffee.utils.TimeUtil;
import com.master.ordercoffee.utils.Utils;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import static android.content.ContentValues.TAG;
import static com.master.ordercoffee.utils.Constants.PROJECT_ID;

public class FirebaseService {

    private static FirebaseService instance;

    private FirebaseFirestore mDatabase;
    private FirebaseStorage mStorage;

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

    public void registerUser (User user, DataChangeListener<Boolean> listener) {
        if (user != null) {
            mDatabase.collection("Users").document(user.id).set(user).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    listener.onDataSuccess(true);
                } else {
                    listener.onDataFailed(task.getException());
                }
            });
        }
    }

    public void checkUserExist(String userId, DataChangeListener<User> listener) {
        DocumentReference reference = mDatabase.collection("Users").document(userId);
        reference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                User user = task.getResult().toObject(User.class);
                if (user != null && !TextUltil.stringIsNullOrEmpty(user.id)) {
                    listener.onDataSuccess(user);
                } else {
                    listener.onDataSuccess(null);
                }
            } else {
                listener.onDataSuccess(null);
            }
        });
    }

    public void uploadImages(Bitmap bitmap, String path, final FirebaseStorageListener callback) {
        mStorage = FirebaseStorage.getInstance();

        if (TextUltil.stringIsNullOrEmpty(path))
            path = String.format("images/image_%s.png", TimeUtil.currentTimeStamp());

        StorageReference storageRef = mStorage.getReference().child(path);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = storageRef.putBytes(data);
        Task<Uri> uriTask = uploadTask.continueWithTask(task -> {
            if (!task.isSuccessful()) {
                callback.onUploadImageFailed();
            }
            return storageRef.getDownloadUrl();
        }).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callback.onUploadSuccess(task.getResult().toString());
            } else {
                callback.onUploadImageFailed();
            }
        });
    }

    public interface FirebaseStorageListener {
        void onUploadSuccess(String imageUrl);

        void onUploadImageFailed();
    }

}
