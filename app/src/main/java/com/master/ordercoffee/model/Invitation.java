package com.master.ordercoffee.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

public class Invitation implements Parcelable {

    public String storeId;
    public String ownerId;
    public Map<Integer, String> userInvitedId = new HashMap<>();

    public Invitation() {
    }

    public Invitation(String storeId, String ownerId, Map<Integer, String> userInvitedId) {
        this.storeId = storeId;
        this.ownerId = ownerId;
        this.userInvitedId = userInvitedId;
    }

    protected Invitation(Parcel in) {
        storeId = in.readString();
        ownerId = in.readString();
    }

    public static final Creator<Invitation> CREATOR = new Creator<Invitation>() {
        @Override
        public Invitation createFromParcel(Parcel in) {
            return new Invitation(in);
        }

        @Override
        public Invitation[] newArray(int size) {
            return new Invitation[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(storeId);
        dest.writeString(ownerId);
    }
}
