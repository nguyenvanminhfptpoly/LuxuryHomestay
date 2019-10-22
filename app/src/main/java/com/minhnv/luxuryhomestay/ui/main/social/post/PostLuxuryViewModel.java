package com.minhnv.luxuryhomestay.ui.main.social.post;

import com.minhnv.luxuryhomestay.data.DataManager;
import com.minhnv.luxuryhomestay.ui.base.BaseViewModel;
import com.minhnv.luxuryhomestay.utils.rx.SchedulerProvider;

public class PostLuxuryViewModel extends BaseViewModel<PostLuxuryNavigator> {
    private static final String TAG = "PostLuxuryViewModel";
    public String nameImage;
    public PostLuxuryViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }




}
