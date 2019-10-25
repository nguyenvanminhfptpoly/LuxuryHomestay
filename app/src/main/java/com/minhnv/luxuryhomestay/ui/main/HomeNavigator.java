package com.minhnv.luxuryhomestay.ui.main;

public interface HomeNavigator {
    void openBookingActivity();
    void openFavoriteActivity();
    void openSocialActivity();
    void openIntroActivity();
    void logout();
    void close();

    void HandlerError(Throwable throwable);
    void onSuccess();
    void doLoadCity();
    void doLoadHomeStaysRating();
    void doLoadHomeStaysPriceAsc();
    void doLoadCityVinHome();
}
