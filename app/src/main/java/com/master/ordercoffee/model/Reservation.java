package com.master.ordercoffee.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Reservation implements Parcelable {

    public int id;
    public boolean active;

    public Reservation() {
    }

    protected Reservation(Parcel in) {
        id = in.readInt();
        active = in.readByte() != 0;
    }

    public static final Creator<Reservation> CREATOR = new Creator<Reservation>() {
        @Override
        public Reservation createFromParcel(Parcel in) {
            return new Reservation(in);
        }

        @Override
        public Reservation[] newArray(int size) {
            return new Reservation[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeByte((byte) (active ? 1 : 0));
    }
}
