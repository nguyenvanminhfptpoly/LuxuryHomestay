package com.minhnv.luxuryhomestay.ui.login.signin;

public interface SignInNavigator {
    void openSignInActivity();
    void onSuccess();
    void handlerError(Throwable throwable);
    void login();
    void onFailed();
    void updateUserInfo();
}
