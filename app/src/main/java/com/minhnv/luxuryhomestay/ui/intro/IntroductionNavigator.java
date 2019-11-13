package com.minhnv.luxuryhomestay.ui.intro;

public interface IntroductionNavigator {
    void ANError(Throwable throwable);
    void onSuccess();
    void loadUser();
}
