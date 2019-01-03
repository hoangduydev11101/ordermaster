package com.master.ordercoffee.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.master.ordercoffee.PickImageActivity;
import com.master.ordercoffee.R;
import com.master.ordercoffee.model.Store;
import com.master.ordercoffee.service.DataChangeListener;
import com.master.ordercoffee.service.FirebaseService;
import com.master.ordercoffee.utils.Constants;
import com.master.ordercoffee.utils.Loader;
import com.master.ordercoffee.utils.TextUtil;
import com.master.ordercoffee.utils.Utils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static com.master.ordercoffee.utils.Constants.Requests.REQUEST_CHOOSE_IMAGE_STORE;

public class AddStoreFragment extends Fragment {

    private Context mContext;
    private String mImageUrl;

    @BindView(R.id.edt_name_store)
    EditText mNameStore;
    @BindView(R.id.img_store)
    ImageView mImageStore;

    @OnClick(R.id.tv_add)
    void addStoreClicked() {
        createStore();
    }

    @OnClick(R.id.img_store)
    void pickImageStoreClicked() {
        Intent intent = new Intent(mContext, PickImageActivity.class);
        intent.putExtra(Constants.Keys.kCaptureImageType, PickImageActivity.ImageShapeType.REC_NOT_AC);
        startActivityForResult(intent, REQUEST_CHOOSE_IMAGE_STORE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_store, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    private void createStore() {
        Loader.start(mContext);
        String name = mNameStore.getText().toString().trim();
        String ownerId = FirebaseService.getInstance().getFirebaseId();

        Store store = new Store();
        store.ownerId = ownerId;
        store.name = name;
        store.image = mImageUrl;

        FirebaseService.getInstance().createStore(store, new DataChangeListener<Boolean>() {
            @Override
            public void onDataSuccess(Boolean data) {
                super.onDataSuccess(data);
                Utils.runOnUiThread(() -> {
                    Loader.stop(mContext);
                    Toast.makeText(mContext, "Create store successed", Toast.LENGTH_SHORT).show();
                    ((Activity)mContext).onBackPressed();
                });

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CHOOSE_IMAGE_STORE: {
                    String imageUrl = data.getStringExtra(Constants.Keys.kImageUrl);
                    if (!TextUtil.stringIsNullOrEmpty(imageUrl)) {
                        mImageUrl = imageUrl;
                        Picasso.get().load(imageUrl).placeholder(R.drawable.ic_avatar_holder).into(mImageStore);
                    }
                }
            }
        }
    }
}
