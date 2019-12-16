package com.minhnv.luxuryhomestay.ui.main.booking.booking;

public interface BookingNavigator {
    void handleError(Throwable throwable);
    void onSuccess();
    void onFailed();
    void doServerBooking();
    void triggerCheckIn(int mYear,int mMonth,int mDay);
    void triggerCheckOut(int mYear,int mMonth,int mDay);

}
