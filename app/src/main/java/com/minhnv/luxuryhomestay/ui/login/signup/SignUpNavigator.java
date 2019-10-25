package com.minhnv.luxuryhomestay.ui.login.signup;

public interface SignUpNavigator {
    void handleError(Throwable throwable);

    void login();
    void onFailed();
    void openSignInActivity();
    //database
    void onInsertSuccess();
    void onInsertFailed();

}
