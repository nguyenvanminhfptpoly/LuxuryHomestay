package com.minhnv.luxuryhomestay.ui.login.signup;

import android.text.TextUtils;

import com.minhnv.luxuryhomestay.data.DataManager;
import com.minhnv.luxuryhomestay.data.model.UserResponse;
import com.minhnv.luxuryhomestay.ui.base.BaseViewModel;
import com.minhnv.luxuryhomestay.utils.CommonUtils;
import com.minhnv.luxuryhomestay.utils.rx.SchedulerProvider;

import io.reactivex.functions.Function;

public class SignUpViewModel extends BaseViewModel<SignUpNavigator> {
    private static final String TAG = "SignUpViewModel";
    public SignUpViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);

    }

    public boolean isRequestValidate(String password,String phonenumber,String address){
        if(TextUtils.isEmpty(password)){
            return false;
        }
        if(TextUtils.isEmpty(phonenumber)){
            return false;
        }
        return !TextUtils.isEmpty(address);
    }

    public void signup(String password, String phonenumber, String address){
        getCompositeDisposable().add(
        getDataManager().doServerSignUp(new UserResponse.ServerSignUpRequest(password,phonenumber,address))
            .subscribeOn(getSchedulerProvider().io())
            .observeOn(getSchedulerProvider().ui())
            .subscribe(response ->
                getNavigator().openSignInActivity()
            ,throwable ->
                getNavigator().handleError(throwable)
            ));
    }

    public void onSignUp(){
        getNavigator().login();
    }
}
