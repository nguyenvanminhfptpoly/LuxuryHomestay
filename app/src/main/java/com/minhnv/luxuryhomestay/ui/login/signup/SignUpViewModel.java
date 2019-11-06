package com.minhnv.luxuryhomestay.ui.login.signup;

import android.text.TextUtils;
import android.util.Log;

import com.minhnv.luxuryhomestay.data.DataManager;
import com.minhnv.luxuryhomestay.data.model.User;
import com.minhnv.luxuryhomestay.data.model.UserResponse;
import com.minhnv.luxuryhomestay.ui.base.BaseViewModel;
import com.minhnv.luxuryhomestay.utils.AppLogger;
import com.minhnv.luxuryhomestay.utils.rx.SchedulerProvider;

public class SignUpViewModel extends BaseViewModel<SignUpNavigator> {
    private static final String TAG = "SignUpViewModel";
    private User user;

    public SignUpViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);

    }

    public boolean isRequestValidate(String password, String phonenumber, String address) {
        if (TextUtils.isEmpty(password)) {
            return false;
        }
        if (TextUtils.isEmpty(phonenumber)) {
            return false;
        }
        return !TextUtils.isEmpty(address);
    }

    public void signup(String password, String phonenumber, String address) {
        getCompositeDisposable().add(
                getDataManager().doServerSignUp(new UserResponse.ServerSignUpRequest(password, phonenumber, address))
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {
                                    if (response.equals("Success")) {
                                        getDataManager().setCurrentPassword(password);
                                        getDataManager().setCurrentPhoneNumber(phonenumber);
                                        getDataManager().setCurrentAddress(address);
                                        getNavigator().openSignInActivity();
                                    } else if (response.equals("Failed") || response.equals("Null")) {
                                        getNavigator().onFailed();
                                    }
                                }, throwable ->
                                        getNavigator().handleError(throwable)
                        ));
    }

    public void insertUser(String pass, String phone, String name) {
        getCompositeDisposable().add(
                getDataManager().insertUser(new User(pass, phone, name))
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(data ->
                            AppLogger.d(TAG, "insertUser: " + data)
                        , throwable ->
                            AppLogger.d(TAG, throwable)
                        )
        );
    }

    public void onSignUp() {
        getNavigator().login();
    }


}
