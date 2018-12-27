package com.master.ordercoffee.service;

import java.util.List;

public abstract class DataChangeListener<T> {
    public void onDataSuccess(T data) {
    }

    public void onListDataSuccess(List<T> listData) {
    }

    public void onDataFailed(Exception e) {
    }
}
