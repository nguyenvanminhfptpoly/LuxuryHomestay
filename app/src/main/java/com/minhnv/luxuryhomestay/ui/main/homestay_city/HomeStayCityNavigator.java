package com.minhnv.luxuryhomestay.ui.main.homestay_city;

public interface HomeStayCityNavigator {
    void HandlerError(Throwable throwable);
    void onSuccess();
    void loadList();

}
