package com.minhnv.luxuryhomestay.ui.main.vin_homes;

public interface VinHomeDetailNavigator {
    void handlerError(Throwable throwable);
    void onSuccess();
    void ServerLoadList();
}
