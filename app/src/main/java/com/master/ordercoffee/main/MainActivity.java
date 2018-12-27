package com.master.ordercoffee.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.master.ordercoffee.BaseActivity;
import com.master.ordercoffee.R;
import com.master.ordercoffee.home.HomeActivity;
import com.master.ordercoffee.model.Store;
import com.master.ordercoffee.service.DataChangeListener;
import com.master.ordercoffee.service.FragmentService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    private ListStoreAdapter mAdapter;
    private MainViewModel mMainViewModel;

    @BindView(R.id.ry_list_store)
    RecyclerView mViewStore;


    @OnClick(R.id.tv_add_store)
    void onAddStoreClicked() {
        FragmentService.getInstance(this).pushFragment(R.id.view_add_store, new AddStoreFragment(), "add-store");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initAdapter();
        initMainViewModel();
    }

    private void initAdapter() {
        mAdapter = new ListStoreAdapter(this);
        mViewStore.setAdapter(mAdapter);
        mViewStore.setItemAnimator(new DefaultItemAnimator());
        mViewStore.setHasFixedSize(true);
        mViewStore.setLayoutManager(new LinearLayoutManager(this));

        mAdapter.setOnItemClickListener(new DataChangeListener<Store>() {
            @Override
            public void onDataSuccess(Store data) {
                super.onDataSuccess(data);
                if (data != null) {
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    intent.putExtra("store", data);
                    startActivity(intent);
                }
            }
        });
    }

    private void initMainViewModel() {
        mMainViewModel = MainViewModel.getInstance(this);
        mMainViewModel.setOnMainModelListener(new DataChangeListener<Store>() {
            @Override
            public void onListDataSuccess(List<Store> listData) {
                super.onListDataSuccess(listData);
                if (listData != null && listData.size() != 0) {
                    mAdapter.setData(listData);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onDataFailed(Exception e) {
                super.onDataFailed(e);
            }
        });
        mMainViewModel.getData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
