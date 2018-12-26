package com.master.ordercoffee.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    public boolean active;
    public String id;
    public String name;
    public String avatar;
    public int userType;

    protected User(Parcel in) {
        active = in.readByte() != 0;
        id = in.readString();
        name = in.readString();
        avatar = in.readString();
        userType = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (active ? 1 : 0));
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(avatar);
        dest.writeInt(userType);
    }
}
