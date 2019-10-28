package com.minhnv.luxuryhomestay.ui.main.social.story;

import com.minhnv.luxuryhomestay.data.DataManager;
import com.minhnv.luxuryhomestay.ui.base.BaseViewModel;
import com.minhnv.luxuryhomestay.utils.rx.SchedulerProvider;

public class PostStoryViewModel extends BaseViewModel<PostStoryNavigator> {
    public PostStoryViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
