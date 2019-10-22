package com.minhnv.luxuryhomestay.data.local;

public interface PreferenceHelper {
    String accessToken();
    void setAccessToken(String accessToken);

    String getCurrentPhoneNumber();
    void setCurrentPhoneNumber(String currentPhoneNumber);

    String getCurrentAddress();
    void setCurrentAddress(String currentAddress);



}
