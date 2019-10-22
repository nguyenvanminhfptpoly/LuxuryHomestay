package com.minhnv.luxuryhomestay.ui.login.signin;

import android.text.TextUtils;
import android.util.Log;

import com.minhnv.luxuryhomestay.data.DataManager;
import com.minhnv.luxuryhomestay.data.model.UserResponse;
import com.minhnv.luxuryhomestay.ui.base.BaseViewModel;
import com.minhnv.luxuryhomestay.utils.CommonUtils;
import com.minhnv.luxuryhomestay.utils.rx.SchedulerProvider;


public class SignInViewModel extends BaseViewModel<SignInNavigator>  {

    private static final String TAG = "SignInViewModel";


    public SignInViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);

    }

    public boolean isRequestValid(String phoneNumber,String password){
        if(TextUtils.isEmpty(phoneNumber)){
            return false;
        }
        return !TextUtils.isEmpty(password);
    }

    public void signin(String phonenumber,String password){
        getCompositeDisposable().add(getDataManager()
            .doServerSignIn(new UserResponse.ServerSignInRequest(phonenumber,password))
            .subscribeOn(getSchedulerProvider().io())
            .observeOn(getSchedulerProvider().ui())
            .subscribe(response -> {
                if(response.equals("Success")){
                    getNavigator().onSuccess();
                }
            },throwable -> {
                getNavigator().handlerError(throwable);
                Log.d(TAG, "signin: "+throwable);
            })
        );
    }

    public void startActivity(){
        getNavigator().openSignInActivity();
    }

    public void onServerSignIn(){
        getNavigator().login();
    }

}
