package com.master.ordercoffee.login;

import android.app.Service;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.master.ordercoffee.R;
import com.master.ordercoffee.service.DataChangeListener;
import com.master.ordercoffee.service.FragmentService;
import com.master.ordercoffee.utils.KeyboardUtil;
import com.master.ordercoffee.utils.TextUltil;
import com.master.ordercoffee.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VerifySmsView extends LinearLayout {

    private Context mContext;
    private TextWatcher textWatcher;
    private View.OnFocusChangeListener focusListener;
    private View.OnKeyListener keyListener;
    private LoginViewModel mLoginViewModel;

    // code view
    @BindView(R.id.edt_fake)
    EditText mFakeCode;
    @BindView(R.id.edt_f1)
    EditText mFake1;
    @BindView(R.id.edt_f2)
    EditText mFake2;
    @BindView(R.id.edt_f3)
    EditText mFake3;
    @BindView(R.id.edt_f4)
    EditText mFake4;
    @BindView(R.id.edt_f5)
    EditText mFake5;
    @BindView(R.id.edt_f6)
    EditText mFake6;

    @BindView(R.id.tv_phone)
    TextView mPhone;

    public VerifySmsView(Context context) {
        super(context);
        initView(context);
    }

    public VerifySmsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public VerifySmsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        View view = inflate(mContext, R.layout.view_verify_sms, null);
        addView(view, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        ButterKnife.bind(this, view);

        initKeyboardListener();
        initEditText();
        initLoginViewModel();

    }

    private void initLoginViewModel() {
        mLoginViewModel = LoginViewModel.getInstance(mContext);
        mPhone.setText(mLoginViewModel.getCurrentPhone());
    }

    private void initEditText() {
        mFakeCode.addTextChangedListener(textWatcher);

        mFake1.setOnFocusChangeListener(focusListener);
        mFake2.setOnFocusChangeListener(focusListener);
        mFake3.setOnFocusChangeListener(focusListener);
        mFake4.setOnFocusChangeListener(focusListener);
        mFake5.setOnFocusChangeListener(focusListener);
        mFake6.setOnFocusChangeListener(focusListener);
    }

    public void appearKeyboard() {
        Utils.runOnUiThread(() -> setFocus(mFakeCode));
    }

    private void initKeyboardListener() {
        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
//                mNext.setEnable(false);
                mFakeCode.removeTextChangedListener(textWatcher);
                mFake1.setText("");
                mFake2.setText("");
                mFake3.setText("");
                mFake4.setText("");
                mFake5.setText("");
                mFake6.setText("");

                setBackgroundBox(R.drawable.bg_input_code_off);

                for (int i = 0; i < editable.toString().length(); i++) {
                    String c = String.valueOf(editable.charAt(i));
                    switch (i) {
                        case 0:
                            mFake1.setText(c);
                            mFake1.setBackgroundResource(R.drawable.bg_input_code_on);
                            break;
                        case 1:
                            mFake2.setText(c);
                            mFake2.setBackgroundResource(R.drawable.bg_input_code_on);
                            break;
                        case 2:
                            mFake3.setText(c);
                            mFake3.setBackgroundResource(R.drawable.bg_input_code_on);
                            break;
                        case 3:
                            mFake4.setText(c);
                            mFake4.setBackgroundResource(R.drawable.bg_input_code_on);
                            break;
                        case 4:
                            mFake5.setText(c);
                            mFake5.setBackgroundResource(R.drawable.bg_input_code_on);
                            break;
                        case 5:
                            mFake6.setText(c);
                            mFake6.setBackgroundResource(R.drawable.bg_input_code_on);
                            break;
                    }
                }
                if (mFakeCode.length() == 6) {
                    mLoginViewModel.setCurrentCode(mFakeCode.getText().toString().trim());
                    mLoginViewModel.signInWithPhoneAuthCredential();
                }

                mFakeCode.addTextChangedListener(textWatcher);
            }
        };

        focusListener = (view, b) -> {
            switch (view.getId()) {
                case R.id.edt_f1:
                    if (b)
                        setFocus(mFakeCode);
                    showSoftKeyboard(mFakeCode);
                    break;
                case R.id.edt_f2:
                    if (b)
                        setFocus(mFakeCode);
                    showSoftKeyboard(mFakeCode);
                    break;
                case R.id.edt_f3:
                    if (b)
                        setFocus(mFakeCode);
                    showSoftKeyboard(mFakeCode);
                    break;
                case R.id.edt_f4:
                    if (b)
                        setFocus(mFakeCode);
                    showSoftKeyboard(mFakeCode);
                    break;
                case R.id.edt_f5:
                    if (b)
                        setFocus(mFakeCode);
                    showSoftKeyboard(mFakeCode);
                    break;
                case R.id.edt_f6:
                    if (b)
                        setFocus(mFakeCode);
                    showSoftKeyboard(mFakeCode);
                    break;
            }
        };

    }

    private void setBackgroundBox(int res) {
        mFake1.setBackgroundResource(res);
        mFake2.setBackgroundResource(res);
        mFake3.setBackgroundResource(res);
        mFake4.setBackgroundResource(res);
        mFake5.setBackgroundResource(res);
        mFake6.setBackgroundResource(res);
    }

    private void showSoftKeyboard(EditText editText) {
        if (editText == null)
            return;

        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, 0);
    }

    private void setFocus(EditText editText) {
        if (editText == null)
            return;

        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
    }

}
