package com.master.ordercoffee.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.master.ordercoffee.R;
import com.master.ordercoffee.model.Store;
import com.master.ordercoffee.service.DataChangeListener;
import com.master.ordercoffee.utils.TextUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListStoreAdapter extends RecyclerView.Adapter<ListStoreAdapter.StoreViewHolder> {

    private Context mContext;
    private DataChangeListener mListener;
    private List<Store> mListData = new ArrayList<>();

    public void setOnItemClickListener (DataChangeListener<Store> listener) {
        mListener = listener;
    }

    public ListStoreAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData (List<Store> list) {
        mListData = new ArrayList<>(list);
    }

    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.row_store, null);
        if (view != null)
            return new StoreViewHolder(view);

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder storeViewHolder, int i) {
//        if (i == mListData.size() - 1) {
//            storeViewHolder.mLine.setVisibility(View.INVISIBLE);
//        }
        final Store store = mListData.get(i);
        if (store != null) {
            if (!TextUtil.stringIsNullOrEmpty(store.name)) {
                storeViewHolder.mName.setText(store.name);
            }
            if (!TextUtil.stringIsNullOrEmpty(store.image)) {
                Picasso.get().load(store.image).error(R.drawable.ic_shop_store).into(storeViewHolder.mImageStore);
            }
        }

        storeViewHolder.mViewMain.setOnClickListener(v -> {
            if (mListener != null && store != null) {
                mListener.onDataSuccess(store);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mListData != null? mListData.size(): 0;
    }

    public class StoreViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_store)
        ImageView mImageStore;
        @BindView(R.id.tv_name)
        TextView mName;
        @BindView(R.id.v_line)
        View mLine;
        @BindView(R.id.v_main)
        ConstraintLayout mViewMain;

        public StoreViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(StoreViewHolder.this, itemView);
        }
    }
}
