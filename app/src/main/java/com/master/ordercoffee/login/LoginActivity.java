package com.master.ordercoffee.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.master.ordercoffee.BaseActivity;
import com.master.ordercoffee.R;
import com.master.ordercoffee.main.MainActivity;
import com.master.ordercoffee.model.User;
import com.master.ordercoffee.service.DataChangeListener;
import com.master.ordercoffee.service.FragmentService;
import com.master.ordercoffee.service.NavigationService;
import com.master.ordercoffee.service.UserService;
import com.master.ordercoffee.utils.AnimationUtil;
import com.master.ordercoffee.utils.KeyboardUtil;
import com.master.ordercoffee.utils.Loader;
import com.master.ordercoffee.utils.PhoneUtil;
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
        String phone = mPhone.getText().toString().trim();
        if (!PhoneUtil.isPhoneCorrectFormat(phone, this)) {
            Toast.makeText(this, "Số điện thoại chưa hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }
        mLoginViewModel.setCurrentPhone(PhoneUtil.formatPhone(phone));
        mLoginViewModel.getSmsCode();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        checkUserLoggedIn();
    }

    private void checkUserLoggedIn() {
        User user = UserService.getCurrentUser(this);
        if (UserService.isUserAvailable(user)) {
            goMainView();
        } else {
            initLoginViewModel();
            mPhone.requestFocus();
            KeyboardUtil.appear(mPhone, this);
        }
    }

    private void goMainView() {
        NavigationService.goIntoView(this, MainActivity.class);
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
                Loader.stop(LoginActivity.this);
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVerifySmsSuccess(FirebaseUser firebaseUser) {
                super.onVerifySmsSuccess(firebaseUser);
                if (firebaseUser != null) {
                    mLoginViewModel.checkExistUser(firebaseUser, new DataChangeListener<User>() {
                        @Override
                        public void onDataSuccess(User data) {
                            super.onDataSuccess(data);
                            Loader.stop(LoginActivity.this);
                            if (data != null) {
                                UserService.saveCurrentUser(LoginActivity.this, data);
                                goMainView();
                            } else {
                                Utils.runOnUiThread(() -> {
                                    FragmentService.getInstance(LoginActivity.this).pushFragment(R.id.view_register, new RegisterFragment(), "register");
                                });

                            }
                        }

                        @Override
                        public void onDataFailed(Exception e) {
                            super.onDataFailed(e);
                            Loader.stop(LoginActivity.this);
                            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onVerirySmsFailed(Exception e) {
                super.onVerirySmsFailed(e);
                Loader.stop(LoginActivity.this);
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
