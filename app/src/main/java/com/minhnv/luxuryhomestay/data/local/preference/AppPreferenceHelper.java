package com.minhnv.luxuryhomestay.data.local.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.minhnv.luxuryhomestay.di.PreferenceInfo;

import javax.inject.Inject;

public class AppPreferenceHelper implements PreferenceHelper  {

    private static final String PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN";
    private static final String PREF_KEY_PHONE_NUMBER = "PREF_KEY_PHONE_NUMBER";
    private static final String PREF_KEY_ADDRESS = "PREF_KEY_ADDRESS";
    private static final String PREF_PASS = "PREF_PASS";
    private static final String PREF_IS_CHECKED = "PREF_IS_CHECKED";



    private final SharedPreferences preferences;

    @Inject
    AppPreferenceHelper(Context context, @PreferenceInfo String prefileName) {
        preferences = context.getSharedPreferences(prefileName, Context.MODE_PRIVATE);
    }


    @Override
    public String accessToken() {
        return preferences.getString(PREF_KEY_ACCESS_TOKEN,null);
    }

    @Override
    public void setAccessToken(String accessToken) {
        preferences.edit().putString(PREF_KEY_ACCESS_TOKEN, accessToken).apply();
    }


    @Override
    public String getCurrentPhoneNumber() {
        return preferences.getString(PREF_KEY_PHONE_NUMBER, null);
    }

    @Override
    public void setCurrentPhoneNumber(String currentPhoneNumber) {
        preferences.edit().putString(PREF_KEY_PHONE_NUMBER, currentPhoneNumber).apply();
    }

    @Override
    public String getCurrentAddress() {
        return preferences.getString(PREF_KEY_ADDRESS, null);
    }

    @Override
    public void setCurrentAddress(String currentAddress) {
        preferences.edit().putString(PREF_KEY_ADDRESS, currentAddress).apply();
    }

    @Override
    public String getCurrentPassword() {
        return preferences.getString(PREF_PASS, null);
    }

    @Override
    public void setCurrentPassword(String currentPassword) {
        preferences.edit().putString(PREF_PASS, currentPassword).apply();
    }

    @Override
    public Boolean getCheckedToSwitchDarkMode() {
        return preferences.getBoolean(PREF_IS_CHECKED,false);
    }

    @Override
    public void setCheckedToSwitchDarkMode(Boolean isCheck) {
        preferences.edit().putBoolean(PREF_IS_CHECKED,isCheck).apply();
    }

    public void deleteSavedData(String key) {
        preferences.edit().remove(key).apply();
    }
}
