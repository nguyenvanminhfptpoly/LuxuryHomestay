package com.minhnv.luxuryhomestay.ui.main.social.list;

public interface SocialNavigator {
    void HandlerError(Throwable throwable);
    void onSuccess();
    void loadList();
}
