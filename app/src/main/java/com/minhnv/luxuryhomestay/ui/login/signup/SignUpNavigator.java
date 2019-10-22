package com.minhnv.luxuryhomestay.ui.login.signup;

public interface SignUpNavigator {
    void handleError(Throwable throwable);

    void login();

    void openSignInActivity();

}
