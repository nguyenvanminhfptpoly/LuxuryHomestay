package com.minhnv.luxuryhomestay.ui.main.search;

public interface SearchNavigator {
    void HandlerError(Throwable throwable);
    void onSuccess();
    void loadList();
}
