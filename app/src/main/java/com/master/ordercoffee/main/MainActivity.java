package com.master.ordercoffee.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.master.ordercoffee.BaseActivity;
import com.master.ordercoffee.R;
import com.master.ordercoffee.home.HomeActivity;
import com.master.ordercoffee.model.Store;
import com.master.ordercoffee.model.User;
import com.master.ordercoffee.service.DataChangeListener;
import com.master.ordercoffee.service.FragmentService;
import com.master.ordercoffee.utils.AnimationUtil;
import com.master.ordercoffee.utils.TextUltil;
import com.master.ordercoffee.utils.TimeUtil;
import com.master.ordercoffee.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity {

    private ListStoreAdapter mAdapter;
    private MainViewModel mMainViewModel;
    private boolean isExtraShowing;
    private long mLastClicked;

    @BindView(R.id.ry_list_store)
    RecyclerView mViewStore;
    @BindView(R.id.img_avatar)
    CircleImageView mAvatar;
    @BindView(R.id.tv_name)
    TextView mName;
    @BindView(R.id.v_extra)
    ExtraView mExtraView;

    @OnClick(R.id.img_avatar)
    void avatarClicked() {
        if (mLastClicked != 0 && mLastClicked + 1000 > TimeUtil.currentTimeStamp())
            return;

        if (!isExtraShowing) {
            AnimationUtil.showView(this, mExtraView, AnimationUtil.ToGravity.SCALE);
        } else {
            AnimationUtil.hideView(this, mExtraView, AnimationUtil.ToGravity.SCALE);
        }
        isExtraShowing = !isExtraShowing;
    }

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
        loadInfoUser();
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

    private void loadInfoUser() {
        mMainViewModel.getDataUser(new DataChangeListener<User>() {
            @Override
            public void onDataSuccess(User data) {
                super.onDataSuccess(data);
                Utils.runOnUiThread(() -> {
                    if (!TextUltil.stringIsNullOrEmpty(data.avatar)) {
                        Picasso.get().load(data.avatar).placeholder(R.drawable.ic_avatar_holder).error(R.drawable.ic_avatar_holder).into(mAvatar);
                    }
                    if (!TextUltil.stringIsNullOrEmpty(data.name)) {
                        mName.setText(data.name);
                    }
                });
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
