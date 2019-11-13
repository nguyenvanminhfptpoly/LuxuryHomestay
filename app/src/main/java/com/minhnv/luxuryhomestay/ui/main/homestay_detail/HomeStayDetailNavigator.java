package com.minhnv.luxuryhomestay.ui.main.homestay_detail;

public interface HomeStayDetailNavigator {
    void HandlerError(Throwable throwable);
    void onSuccess();
    void onFailed();
    void addFavorite();
    void loadImageDetail();
}
