package com.master.ordercoffee.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Store implements Parcelable {

    public String id;
    public String ownerId;
    public String name;
    public Place place;
    public String[] images;
    public String introduce;

    public Store() {
    }

    protected Store(Parcel in) {
        id = in.readString();
        ownerId = in.readString();
        name = in.readString();
        place = in.readParcelable(Place.class.getClassLoader());
        images = in.createStringArray();
        introduce = in.readString();
    }

    public static final Creator<Store> CREATOR = new Creator<Store>() {
        @Override
        public Store createFromParcel(Parcel in) {
            return new Store(in);
        }

        @Override
        public Store[] newArray(int size) {
            return new Store[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(ownerId);
        dest.writeString(name);
        dest.writeParcelable(place, flags);
        dest.writeStringArray(images);
        dest.writeString(introduce);
    }
}
