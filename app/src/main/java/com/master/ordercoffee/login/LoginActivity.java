package com.master.ordercoffee.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.j2objc.annotations.ObjectiveCName;
import com.master.ordercoffee.BaseActivity;
import com.master.ordercoffee.R;
import com.master.ordercoffee.service.DataChangeListener;
import com.master.ordercoffee.service.FragmentService;
import com.master.ordercoffee.utils.AnimationUtil;
import com.master.ordercoffee.utils.KeyboardUtil;
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
        mPhone.requestFocus();
        KeyboardUtil.appear(mPhone, this);
    }

    private void initLoginViewModel() {
        mLoginViewModel = LoginViewModel.getInstance(this);

        mLoginViewModel.setOnLoginModelListener(new LoginModelListener() {
            @Override
            public void onGetSmsCodeSuccess() {
                super.onGetSmsCodeSuccess();
                AnimationUtil.showView(LoginActivity.this, mViewVerify);
                mViewVerify.appearKeyboard();
            }

            @Override
            public void onGetSmsCodeFailed(String message) {
                super.onGetSmsCodeFailed(message);
                // TODO show meesage error
            }

            @Override
            public void onVerifySmsSuccess(FirebaseUser firebaseUser) {
                super.onVerifySmsSuccess(firebaseUser);
                if (firebaseUser != null) {
                    mLoginViewModel.checkExistUser(firebaseUser, new DataChangeListener<Boolean>() {
                        @Override
                        public void onDataSuccess(Boolean data) {
                            super.onDataSuccess(data);
                            if (data) {
                                // TODO main view
                            } else {
                                Utils.runOnUiThread(() -> {
                                    FragmentService.getInstance(LoginActivity.this).pushFragment(R.id.view_register, new RegisterFragment(), "register");
                                });

                            }
                        }

                        @Override
                        public void onDataFailed(Exception e) {
                            super.onDataFailed(e);
                            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onVerirySmsFailed(Exception e) {
                super.onVerirySmsFailed(e);
                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mViewVerify.getVisibility() == View.VISIBLE) {
            AnimationUtil.hideView(LoginActivity.this, mViewVerify);
            return;
        }

        super.onBackPressed();
    }
}
