package com.minhnv.luxuryhomestay.ui.main.homestay_price_ago;

public interface HomeStayPriceNavigator {
    void HandlerError(Throwable throwable);
    void onSuccess();
    void doLoadHomeStaysPriceAsc();
}
