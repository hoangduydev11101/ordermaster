package com.master.ordercoffee.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.master.ordercoffee.PickImageActivity;
import com.master.ordercoffee.R;
import com.master.ordercoffee.utils.Constants;
import com.master.ordercoffee.utils.TextUtil;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.master.ordercoffee.utils.Constants.Requests.REQUEST_IMAGE_FOR_AVATAR;

public class RegisterFragment extends Fragment {

    private Context mContext;
    private String mImageUrl;

    @BindView(R.id.edt_name)
    EditText mName;
    @BindView(R.id.edt_email)
    EditText mEmail;
    @BindView(R.id.img_avatar)
    CircleImageView mAvatar;

    @OnClick(R.id.img_avatar)
    void onAvatarClicked() {
        Intent intent = new Intent(mContext, PickImageActivity.class);
        intent.putExtra(Constants.Keys.kCaptureImageType, PickImageActivity.ImageShapeType.CIRCLE);
        startActivityForResult(intent, REQUEST_IMAGE_FOR_AVATAR);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @OnClick(R.id.tv_register)
    void onRegisterClicked() {
        LoginViewModel.getInstance(mContext).registerUser(mName.getText().toString().trim(), mEmail.getText().toString().trim(), mImageUrl);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, view);


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_FOR_AVATAR) {
         if (data != null) {
             String imageUrl = data.getStringExtra(Constants.Keys.kImageUrl);
             if (!TextUtil.stringIsNullOrEmpty(imageUrl)) {
                 mImageUrl = imageUrl;
                 Picasso.get().load(imageUrl).placeholder(R.drawable.ic_avatar_holder).into(mAvatar);
             }
         }
        }
    }
}
