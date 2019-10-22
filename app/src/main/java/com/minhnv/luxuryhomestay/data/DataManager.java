package com.minhnv.luxuryhomestay.data;

import com.minhnv.luxuryhomestay.data.local.PreferenceHelper;
import com.minhnv.luxuryhomestay.data.remote.ApiHelper;

public interface DataManager extends PreferenceHelper, ApiHelper {

    void updateUserInfo(
            String mPass,
            String mPhone,
            String mAdd
    );

}
