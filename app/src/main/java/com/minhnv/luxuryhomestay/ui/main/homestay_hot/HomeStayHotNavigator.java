package com.minhnv.luxuryhomestay.ui.main.homestay_hot;

public interface HomeStayHotNavigator {
    void HandlerError(Throwable throwable);
    void onSuccess();
    void doLoadHomeStaysRating();
}
