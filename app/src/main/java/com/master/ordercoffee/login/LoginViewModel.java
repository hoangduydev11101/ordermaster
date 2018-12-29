package com.master.ordercoffee.login;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

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
import com.master.ordercoffee.utils.TextUltil;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class LoginViewModel {
    public interface LoginModelListener {
        void onGetSmsCodeSuccess();

        void onGetSmsCodeFailed(String message);

        void onVerifySmsSuccess();

        void onVerirySmsFailed(Exception e);
    }

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
        final String phone = String.format("%s%s", "+84", mCurrentPhone);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,
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
                    };
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
            if (mListener != null) {
                mListener.onVerifySmsSuccess();
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

                if (user != null) {

                }
            }
        });
    }
}