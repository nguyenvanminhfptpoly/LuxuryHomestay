package com.minhnv.luxuryhomestay.ui.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.TextView;

import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.ui.login.signin.SignInActivity;
import com.minhnv.luxuryhomestay.ui.login.signup.SignUpActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(SignInActivity.newIntent(getApplicationContext()));
        finish();

    }
}
