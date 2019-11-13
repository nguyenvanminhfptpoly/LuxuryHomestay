package com.minhnv.luxuryhomestay.ui.login.signup;

import android.text.TextUtils;

import com.minhnv.luxuryhomestay.data.DataManager;
import com.minhnv.luxuryhomestay.data.model.User;
import com.minhnv.luxuryhomestay.data.model.UserResponse;
import com.minhnv.luxuryhomestay.ui.base.BaseViewModel;
import com.minhnv.luxuryhomestay.utils.rx.SchedulerProvider;

public class SignUpViewModel extends BaseViewModel<SignUpNavigator> {
    private static final String TAG = "SignUpViewModel";
    private User user;

    public SignUpViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);

    }

    public boolean isRequestValidate(String password, String phonenumber, String address) {
        if (TextUtils.isEmpty(password) || password.length() < 5) {
            return false;
        }
        if (TextUtils.isEmpty(phonenumber) || phonenumber.length() < 10) {
            return false;
        }
        if (TextUtils.isEmpty(address) || address.length() <= 10) {
            return false;
        }
        return (!TextUtils.isEmpty(address));
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


    public void onSignUp() {
        getNavigator().login();
    }


}
