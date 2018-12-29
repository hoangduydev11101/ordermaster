package com.master.ordercoffee.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import com.google.j2objc.annotations.ObjectiveCName;
import com.master.ordercoffee.BaseActivity;
import com.master.ordercoffee.R;
import com.master.ordercoffee.utils.TextUltil;
import com.master.ordercoffee.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    private LoginViewModel mLoginViewModel;

    @BindView(R.id.tv_phone)
    EditText mPhone;
    @BindView(R.id.v_verify)
    VerifySmsView mViewVerify;

    @OnClick(R.id.img_next)
    void onNextClicked() {
        if (!TextUltil.stringIsNullOrEmpty(mPhone.getText().toString().trim())) {
            mLoginViewModel.setCurrentPhone(mPhone.getText().toString().trim());
            mLoginViewModel.getSmsCode();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initLoginViewModel();
    }

    private Animation mSlideIn, mSlideOut;
    private void showVerifiSmsCode () {
        if (mSlideIn == null)
            mSlideIn = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        if (mSlideOut == null)
            mSlideOut = AnimationUtils.loadAnimation(this, R.anim.slide_out_left);

        if (mViewVerify.getVisibility() != View.VISIBLE) {
            mViewVerify.startAnimation(mSlideIn);
            Utils.runOnUiThread(() -> {
                mViewVerify.setVisibility(View.VISIBLE);
            }, 250);
        }
    }

    private void hideVerifiSmsCode () {
        if (mSlideIn == null)
            mSlideIn = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        if (mSlideOut == null)
            mSlideOut = AnimationUtils.loadAnimation(this, R.anim.slide_out_left);

        if (mViewVerify.getVisibility() == View.VISIBLE) {
            mViewVerify.startAnimation(mSlideOut);
            Utils.runOnUiThread(() -> {
                mViewVerify.setVisibility(View.INVISIBLE);
            }, 250);
        }
    }

    private void initLoginViewModel() {
        mLoginViewModel = LoginViewModel.getInstance(this);

        mLoginViewModel.setOnLoginModelListener(new LoginViewModel.LoginModelListener() {
            @Override
            public void onGetSmsCodeSuccess() {
                showVerifiSmsCode();
            }

            @Override
            public void onGetSmsCodeFailed(String message) {

            }

            @Override
            public void onVerifySmsSuccess() {

            }

            @Override
            public void onVerirySmsFailed(Exception e) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        hideVerifiSmsCode();
    }
}
