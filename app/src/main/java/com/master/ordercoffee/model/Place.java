package com.master.ordercoffee.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Place implements Parcelable {

    public String id;
    public String name;
    public String address;
    public double lat;
    public double lng;

    public Place() {
    }

    protected Place(Parcel in) {
        id = in.readString();
        name = in.readString();
        address = in.readString();
        lat = in.readDouble();
        lng = in.readDouble();
    }

    public static final Creator<Place> CREATOR = new Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(address);
        dest.writeDouble(lat);
        dest.writeDouble(lng);
    }
}
