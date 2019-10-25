package com.minhnv.luxuryhomestay.data;

import com.minhnv.luxuryhomestay.data.local.db.DbHelper;
import com.minhnv.luxuryhomestay.data.local.preference.PreferenceHelper;
import com.minhnv.luxuryhomestay.data.remote.ApiHelper;


public interface DataManager extends PreferenceHelper, ApiHelper, DbHelper {
    void updateUserInfo(
            String mPass,
            String mPhone,
            String mAdd
    );
}
