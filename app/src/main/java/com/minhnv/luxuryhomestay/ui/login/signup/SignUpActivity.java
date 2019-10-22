package com.minhnv.luxuryhomestay.ui.login.signup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.ui.base.BaseActivity;
import com.minhnv.luxuryhomestay.ui.login.signin.SignInActivity;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

public class SignUpActivity extends BaseActivity<SignUpViewModel> implements SignUpNavigator {
    private static final String TAG = "SignUpActivity";
    private EditText username,address, phoneNumber;
    private EditText password;
    private TextView tvBack;
    private SlidrInterface anInterface;


    public static Intent newIntent(Context context) {
       return new Intent(context, SignUpActivity.class);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_sign_up;
    }

    @Override
    public void onCreateActivity(@Nullable Bundle savedInstanceState) {
        viewmodel = ViewModelProviders.of(this, factory).get(SignUpViewModel.class);
        viewmodel.setNavigator(this);
        anInterface = Slidr.attach(this);
        password = findViewById(R.id.includePassword);
        address = findViewById(R.id.includeAddress);
        phoneNumber = findViewById(R.id.includeCountMember);
        tvBack = findViewById(R.id.tvBackSignUp);
        tvBack.setOnClickListener(tv -> {onBackPressed();});
        Button btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(view ->{
            if(SystemClock.elapsedRealtime() - mLastClickTime < 5000){
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            viewmodel.onSignUp();});
    }

    @Override
    public void handleError(Throwable throwable) {
        Log.d(TAG, "handleError: "+throwable);
    }

    @Override
    public void login() {
        String InputPassword = password.getText().toString().trim();
        String InputAddress = address.getText().toString().trim();
        String InputPhoneNumber = phoneNumber.getText().toString();
        if(viewmodel.isRequestValidate(InputPassword,InputPhoneNumber,InputAddress)){
            viewmodel.signup(InputPassword,InputPhoneNumber,InputAddress);
            showLoading();
        }else {
            Toast.makeText(this, getString(R.string.email_password_valid), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void openSignInActivity() {
        Intent intent = SignInActivity.newIntent(SignUpActivity.this);
        startActivity(intent);
        finish();
        Toast.makeText(this, getString(R.string.add_successfully), Toast.LENGTH_SHORT).show();
    }
}
