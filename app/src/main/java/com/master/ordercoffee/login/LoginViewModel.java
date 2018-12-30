package com.master.ordercoffee.login;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.master.ordercoffee.main.MainActivity;
import com.master.ordercoffee.model.User;
import com.master.ordercoffee.service.DataChangeListener;
import com.master.ordercoffee.service.FirebaseService;
import com.master.ordercoffee.service.NavigationService;
import com.master.ordercoffee.service.UserService;
import com.master.ordercoffee.utils.TextUltil;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class LoginViewModel {

    private String mCurrentUserId;
    private String mCurrentPhone;
    private String mCurrentCode;
    private String mCurrentVerificationId;
    private PhoneAuthProvider.ForceResendingToken mCurrentToken;

    private LoginModelListener mListener;
    private static Context mContext;
    private static final LoginViewModel ourInstance = new LoginViewModel();

    public static LoginViewModel getInstance(Context context) {
        mContext = context;
        return ourInstance;
    }

    public void setCurrentPhone(String phone) {
        mCurrentPhone = phone;
    }

    public String getCurrentPhone() {
        return mCurrentPhone;
    }

    public void setCurrentCode(String code) {
        mCurrentCode = code;
    }

    public String getCurrentCode() {
        return mCurrentCode;
    }

    public void setOnLoginModelListener(LoginModelListener listener) {
        mListener = listener;
    }

    public void getSmsCode() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mCurrentPhone,
                30,
                TimeUnit.SECONDS,
                (Activity) mContext,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                        mCurrentCode = phoneAuthCredential.getSmsCode();
                        if (!TextUltil.stringIsNullOrEmpty(mCurrentCode)) {
                            phoneAuthSignIn(phoneAuthCredential);
                        } else {
//                            if (mListener != null) {
//                                mListener.onGetSmsCodeFailed("");
//                            }
                        }

                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        if (mListener != null) {
                            mListener.onVerirySmsFailed(e);
                        }
                    }

                    @Override
                    public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
                        mCurrentVerificationId = verificationId;
                        mCurrentToken = token;
                        savePhoneVerificationID(mCurrentVerificationId);
                        if (mListener != null) {
                            mListener.onGetSmsCodeSuccess();
                        }
                    }

                    /* This one is also called */
                    @Override
                    public void onCodeAutoRetrievalTimeOut(String s) {
                        super.onCodeAutoRetrievalTimeOut(s);
                        if (mListener != null) {
                            mListener.onGetSmsCodeFailed(s);
                        }
                        ;
                    }


                });
    }

    private void savePhoneVerificationID(String verifyId) {
        mCurrentVerificationId = verifyId;
        SharedPreferences sharedPref = mContext.getSharedPreferences("Phone auth", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("authPhoneVerificationID", verifyId);
        editor.apply();
    }

    private void phoneAuthSignIn(PhoneAuthCredential credential) {

        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(task -> {
            if (task.getException() != null) {
                if (mListener != null) {
                    mListener.onVerirySmsFailed(task.getException());
                    return;
                }

            }
            if (mListener != null && task != null && task.getResult().getUser() != null) {
                mListener.onVerifySmsSuccess(task.getResult().getUser());
            }

        });
    }

    public PhoneAuthCredential getPhoneCredential() {
        String verificationID = getPhoneVericationID();
        PhoneAuthCredential phoneCredential = PhoneAuthProvider.getCredential(verificationID, this.mCurrentCode);
        return phoneCredential;
    }

    private String getPhoneVericationID() {
        if (!TextUltil.stringIsNullOrEmpty(mCurrentVerificationId))
            return mCurrentVerificationId;

        SharedPreferences sharedPref = mContext.getSharedPreferences("Phone auth", Context.MODE_PRIVATE);
        mCurrentVerificationId = sharedPref.getString("authPhoneVerificationID", "");
        return mCurrentVerificationId;
    }


    public void signInWithPhoneAuthCredential() {
        FirebaseAuth.getInstance().signInWithCredential(getPhoneCredential()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = task.getResult().getUser();
                if (user != null && !TextUltil.stringIsNullOrEmpty(user.getUid())) {
                    mCurrentUserId = user.getUid();
                    if (mListener != null) {
                        mListener.onVerifySmsSuccess(user);
                    } else {
                        if (mListener != null) {
                            mListener.onVerifySmsSuccess(null);
                        }
                    }
                } else {
                    if (mListener != null) {
                        mListener.onVerirySmsFailed(task.getException());
                    }
                }
            }
        });
    }

    public void checkExistUser(FirebaseUser user, DataChangeListener<User> listener) {
        FirebaseService.getInstance().checkUserExist(user.getUid(), new DataChangeListener<User>() {
            @Override
            public void onDataSuccess(User data) {
                super.onDataSuccess(data);
                if (data != null) {
                    listener.onDataSuccess(data);
                } else {
                    listener.onDataSuccess(null);
                }
            }

            @Override
            public void onDataFailed(Exception e) {
                super.onDataFailed(e);
                listener.onDataFailed(e);
            }
        });
    }

    public void registerUser (String name, String email, String avatar) {
        User user = new User(true, mCurrentUserId, name, mCurrentPhone, avatar, email);
        FirebaseService.getInstance().registerUser(user, new DataChangeListener<Boolean>() {
            @Override
            public void onDataSuccess(Boolean data) {
                super.onDataSuccess(data);
                Toast.makeText(mContext, "register success", Toast.LENGTH_SHORT).show();
                UserService.saveCurrentUser(mContext, user);
                NavigationService.goIntoView(mContext, MainActivity.class);
            }

            @Override
            public void onDataFailed(Exception e) {
                super.onDataFailed(e);
                Toast.makeText(mContext, "register failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}