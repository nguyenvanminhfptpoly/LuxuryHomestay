package com.minhnv.luxuryhomestay.ui.login.signin;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.User;
import com.minhnv.luxuryhomestay.ui.base.BaseActivity;
import com.minhnv.luxuryhomestay.ui.login.signup.SignUpActivity;
import com.minhnv.luxuryhomestay.ui.main.HomeActivity;
import com.minhnv.luxuryhomestay.utils.AppLogger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.androidnetworking.internal.ANImageLoader.initialize;


public class SignInActivity extends BaseActivity<SignInViewModel> implements SignInNavigator {

    private static final String TAG = "SignInActivity";
    private static final int CODE_SIGN_UP_REQUEST = 1001;
    private EditText passWord;
    private EditText phoneNumber;
    private SharedPreferences.Editor editor;
    public static final String PASSWORD = "PASSWORD";
    public static final String PHONENUMBER = "PHONENUMBER";
    private TextView tvSignUp;
    private Button btnLogin;
    private List<User> users;
    private final static int REQUEST_CODE_ASK_PERMISSIONS = 1;

    /**
     * Permissions that need to be explicitly requested from end user.
     */
    private static final String[] REQUIRED_SDK_PERMISSIONS = new String[] {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CALL_PHONE };

    @Override
    public int getLayoutId() {
        return R.layout.activity_signin;
    }


    @SuppressLint("CommitPrefEdits")
    @Override
    public void onCreateActivity(@Nullable Bundle savedInstanceState) {
        viewmodel = ViewModelProviders.of(this, factory).get(SignInViewModel.class);
        viewmodel.setNavigator(this);
        initView();
        checkPermissions();
    }

    private void initView() {
        tvSignUp = findViewById(R.id.tvSignUp);
        btnLogin = findViewById(R.id.btnLogin);
        passWord = findViewById(R.id.includePassWords);
        phoneNumber = findViewById(R.id.includeCountMember);

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
        phoneNumber.setText(mPassword);
        passWord.setText(mPhoneNumber);
        fetchData();
    }

    private void fetchData() {
        compositeDisposable.add(viewmodel.listBehaviorSubject.share()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(data -> {
                    users.addAll(data);
                }, throwable -> {
                    AppLogger.d(TAG, throwable);
                }));
    }

    /**
     * Checks the dynamically-controlled permissions and requests missing permissions from end user.
     */
    protected void checkPermissions() {
        final List<String> missingPermissions = new ArrayList<String>();
        // check all required dynamic permissions
        for (final String permission : REQUIRED_SDK_PERMISSIONS) {
            final int result = ContextCompat.checkSelfPermission(this, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(permission);
            }
        }
        if (!missingPermissions.isEmpty()) {
            // request all missing permissions
            final String[] permissions = missingPermissions
                    .toArray(new String[missingPermissions.size()]);
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_ASK_PERMISSIONS);
        } else {
            final int[] grantResults = new int[REQUIRED_SDK_PERMISSIONS.length];
            Arrays.fill(grantResults, PackageManager.PERMISSION_GRANTED);
            onRequestPermissionsResult(REQUEST_CODE_ASK_PERMISSIONS, REQUIRED_SDK_PERMISSIONS,
                    grantResults);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                for (int index = permissions.length - 1; index >= 0; --index) {
                    if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                        // exit the app if one permission is not granted
                        Toast.makeText(this, "Required permission '" + permissions[index]
                                + "' not granted, exiting", Toast.LENGTH_LONG).show();
                        finish();
                        return;
                    }
                }
                // all permissions were granted
                initialize();
                break;
        }
    }


    public static Intent newIntent(Context context) {
        return new Intent(context, SignInActivity.class);
    }


    @Override
    public void openSignInActivity() {
        Intent intent = SignUpActivity.newIntent(SignInActivity.this);
        startActivityForResult(intent, CODE_SIGN_UP_REQUEST);
    }

    @Override
    public void onSuccess() {
        startActivity(HomeActivity.newIntent(SignInActivity.this));
        finish();
        Toast.makeText(this, getString(R.string.login_success), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void handlerError(Throwable throwable) {
        AppLogger.d("failed" + throwable);
        Toast.makeText(this, getString(R.string.login_error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void login() {
        String InputPhoneNumber = passWord.getText().toString().trim();
        String InputPassword = phoneNumber.getText().toString().trim();
        if (viewmodel.isRequestValid(InputPhoneNumber, InputPassword) && isNetworkConnected()) {
            editor.putString(PASSWORD, InputPassword);
            editor.putString(PHONENUMBER, InputPhoneNumber);
            editor.commit();
            viewmodel.signin(InputPassword,InputPhoneNumber);
            hideKeyboard();
            showLoading();
        } else if(!isNetworkConnected()) {
            Toast.makeText(this, getString(R.string.login_error), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.email_password_valid), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailed() {
        hideLoading();
        Toast.makeText(this, getString(R.string.login_error), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode== RESULT_OK && requestCode == CODE_SIGN_UP_REQUEST) {
            passWord.setText(appPreferenceHelper.getCurrentPhoneNumber());
            phoneNumber.setText(appPreferenceHelper.getCurrentPassword());
        }
    }
}
