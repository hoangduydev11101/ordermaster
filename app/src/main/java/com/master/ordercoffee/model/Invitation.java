package com.master.ordercoffee.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

public class Invitation implements Parcelable {

    public String storeId;
    public String ownerId;
    public int jobs;

    public Invitation() {
    }

    public Invitation(String storeId, String ownerId, int jobs) {
        this.storeId = storeId;
        this.ownerId = ownerId;
        this.jobs = jobs;
    }

    protected Invitation(Parcel in) {
        storeId = in.readString();
        ownerId = in.readString();
        jobs = in.readInt();
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
        dest.writeInt(jobs);
    }
}
