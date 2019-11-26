package com.minhnv.luxuryhomestay.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.ViewModelProviderFactory;
import com.minhnv.luxuryhomestay.data.local.preference.AppPreferenceHelper;
import com.minhnv.luxuryhomestay.ui.login.signin.SignInActivity;
import com.minhnv.luxuryhomestay.utils.CommonUtils;
import com.minhnv.luxuryhomestay.utils.CustomToast;
import com.minhnv.luxuryhomestay.utils.rx.NetworkUtils;
import com.minhnv.luxuryhomestay.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseActivity<V extends BaseViewModel> extends AppCompatActivity implements BaseFragment.Callback {
    @Inject
    public  ViewModelProviderFactory factory;
    @Inject
    public  SchedulerProvider schedulerProvider;
    @Inject
    public AppPreferenceHelper appPreferenceHelper;

    public V viewmodel;

    public View mRootView;

    public CommonUtils commonUtils;
    public static CompositeDisposable compositeDisposable;

    private ProgressDialog progressDialog;
    public long mLastClickTime = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        performDependencyInjection();
        super.onCreate(savedInstanceState);
        compositeDisposable = new CompositeDisposable();
    }


    public void performDependencyInjection(){
        AndroidInjection.inject(this);
    }

    public void showLoading(){
        progressDialog = CommonUtils.showLoadingDialog(this);
    }
    public void hideLoading(){
        if(progressDialog != null && progressDialog.isShowing()){
            progressDialog.cancel();
        }
    }


    public boolean isNetworkConnected(){
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }

    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }
    public void backToLogin(){
        Intent  intent = SignInActivity.newIntent(getApplicationContext());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        CustomToast.makeText(this,getString(R.string.internet_error),Toast.LENGTH_LONG,CustomToast.ERROR).show();
        startActivity(intent);
    }


}
