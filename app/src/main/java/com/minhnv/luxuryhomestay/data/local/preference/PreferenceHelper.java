package com.minhnv.luxuryhomestay.data.local.preference;

public interface PreferenceHelper {
    String accessToken();
    void setAccessToken(String accessToken);

    String getCurrentPhoneNumber();
    void setCurrentPhoneNumber(String currentPhoneNumber);

    String getCurrentAddress();
    void setCurrentAddress(String currentAddress);


    String getCurrentPassword();
    void setCurrentPassword(String currentPassword);





}
