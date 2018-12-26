package com.master.ordercoffee.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Food implements Parcelable {

    public boolean active;
    public String id;
    public String name;
    public long price;
    public String description;
    public String[] images;

    public Food() {
    }

    protected Food(Parcel in) {
        active = in.readByte() != 0;
        id = in.readString();
        name = in.readString();
        price = in.readLong();
        description = in.readString();
        images = in.createStringArray();
    }

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
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
        dest.writeLong(price);
        dest.writeString(description);
        dest.writeStringArray(images);
    }
}
