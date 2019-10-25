package com.minhnv.luxuryhomestay.ui.main.booking.list;

public interface ListBookingNavigator {
    void handleError(Throwable throwable);
    void onSuccess();
    void onFailed();
    void onDeleteSuccess();
    void ServerLoadList();
    void ServerDeleteBooking(int id);
}
