package com.minhnv.luxuryhomestay.ui.main.booking.booking;

public interface BookingNavigator {
    void handleError(Throwable throwable);
    void onSuccess();
    void doServerBooking();

}
