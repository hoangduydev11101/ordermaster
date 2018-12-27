package com.master.ordercoffee.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

public class Order implements Parcelable {

    public String id;
    public int reservationId;
    public String staffId;
    public String chefId;
    public Integer[] status;
    public Map<Integer, Food> foods = new HashMap<>();
    public long totalPrice;

    public Order() {
    }


    protected Order(Parcel in) {
        id = in.readString();
        reservationId = in.readInt();
        staffId = in.readString();
        chefId = in.readString();
        totalPrice = in.readLong();
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeInt(reservationId);
        dest.writeString(staffId);
        dest.writeString(chefId);
        dest.writeLong(totalPrice);
    }
}
