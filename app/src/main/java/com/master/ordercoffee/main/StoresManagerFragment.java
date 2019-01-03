package com.master.ordercoffee.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.master.ordercoffee.R;
import com.master.ordercoffee.home.HomeActivity;
import com.master.ordercoffee.model.Store;
import com.master.ordercoffee.service.DataChangeListener;
import com.master.ordercoffee.service.FragmentService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StoresManagerFragment extends Fragment {

    private Context mContext;
    private ListStoreAdapter mAdapter;

    @BindView(R.id.list_store)
    RecyclerView mListStore;

    @OnClick(R.id.add_store)
    void addStoreClicked() {
        FragmentService.getInstance(mContext).pushFragment(R.id.view_add_store, new AddStoreFragment(), "add-store");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stores_manager, container, false);
        ButterKnife.bind(this, view);

        initAdapter();
        getData();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void getData() {
        MainViewModel.getInstance(mContext).getMyStore(new DataChangeListener<Store>() {
            @Override
            public void onListDataSuccess(List<Store> listData) {
                super.onListDataSuccess(listData);
                if (listData != null && listData.size() != 0) {
                    mAdapter.setData(listData);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void initAdapter() {
        mAdapter = new ListStoreAdapter(mContext);
        mListStore.setAdapter(mAdapter);
        mListStore.setItemAnimator(new DefaultItemAnimator());
        mListStore.setHasFixedSize(true);
        mListStore.setLayoutManager(new LinearLayoutManager(mContext));

        mAdapter.setOnItemClickListener(new DataChangeListener<Store>() {
            @Override
            public void onDataSuccess(Store data) {
                super.onDataSuccess(data);
                if (data != null) {
                    Intent intent = new Intent(mContext, HomeActivity.class);
                    intent.putExtra("store", data);
                    startActivity(intent);
                }
            }
        });
    }
}
