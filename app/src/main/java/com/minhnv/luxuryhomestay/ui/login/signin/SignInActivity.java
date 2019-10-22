package com.minhnv.luxuryhomestay.ui.login.signin;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;

import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.ui.base.BaseActivity;
import com.minhnv.luxuryhomestay.ui.login.signup.SignUpActivity;
import com.minhnv.luxuryhomestay.ui.main.HomeActivity;


public class SignInActivity extends BaseActivity<SignInViewModel> implements SignInNavigator {

    private static final String TAG = "SignInActivity";
    private EditText phonenumber;
    private EditText password;
    private SharedPreferences.Editor editor;
    public static final String PASSWORD = "PASSWORD";
    public static final String PHONENUMBER = "PHONENUMBER";


    @Override
    public int getLayoutId() {
        return R.layout.activity_signin;
    }


    @SuppressLint("CommitPrefEdits")
    @Override
    public void onCreateActivity(@Nullable Bundle savedInstanceState) {
        viewmodel = ViewModelProviders.of(this, factory).get(SignInViewModel.class);
        viewmodel.setNavigator(this);
        TextView tvSignUp = findViewById(R.id.tvSignUp);
        Button btnLogin = findViewById(R.id.btnLogin);
        phonenumber = findViewById(R.id.includePassWords);
        password = findViewById(R.id.includeCountMember);

        tvSignUp.setOnClickListener(view -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 5000) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            viewmodel.startActivity();
        });
        btnLogin.setOnClickListener(view -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 5000) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            viewmodel.onServerSignIn();
        });
        SharedPreferences preferences = getSharedPreferences("Login", MODE_PRIVATE);
        editor = preferences.edit();
        String mPassword = preferences.getString(PASSWORD, "");
        String mPhoneNumber = preferences.getString(PHONENUMBER, "");
        password.setText(mPassword);
        phonenumber.setText(mPhoneNumber);
        initPermission2();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {

            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "onRequestPermissionsResult: ");
            } else {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Quyền Chưa được cấp")
                        .setMessage(getString(R.string.denied_permission))
                        .setPositiveButton("Đi đến cài đặt", (dialog, which) -> {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                    Uri.fromParts("package", getPackageName(), null));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        })
                        .setNegativeButton("Thoát", (dialog, which) -> {
                        })
                        .setCancelable(false)
                        .create()
                        .show();
                Log.d(TAG, "onRequestPermissionsResult: denied");

            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void initPermission2() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                //Permisson don't granted
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Log.d(TAG, "initPermission2: success");
                }
                // Permisson don't granted and dont show dialog again.
                else {
                    Log.d(TAG, "initPermission2: failed");
                }
                //Register permission
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

            }
        }
    }


    public static Intent newIntent(Context context) {
        return new Intent(context, SignInActivity.class);
    }


    @Override
    public void openSignInActivity() {
        Intent intent = SignUpActivity.newIntent(SignInActivity.this);
        startActivity(intent);
    }

    @Override
    public void onSuccess() {
        startActivity(HomeActivity.newIntent(SignInActivity.this));
        finish();
        Toast.makeText(this, getString(R.string.login_success), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void handlerError(Throwable throwable) {
        Log.d(TAG, "handlerError: " + throwable);
        Toast.makeText(this, getString(R.string.login_error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void login() {
        String InputPhoneNumber = phonenumber.getText().toString().trim();
        String InputPassword = password.getText().toString().trim();
        if (viewmodel.isRequestValid(InputPhoneNumber, InputPassword)) {
            editor.putString(PASSWORD, InputPassword);
            editor.putString(PHONENUMBER, InputPhoneNumber);
            editor.commit();
            viewmodel.signin(InputPhoneNumber, InputPassword);
            hideKeyboard();
            showLoading();
        } else {
            Toast.makeText(this, getString(R.string.email_password_valid), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailed() {
        Toast.makeText(this, getString(R.string.login_error), Toast.LENGTH_SHORT).show();
    }
}
