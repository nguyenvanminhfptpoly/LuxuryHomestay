package com.minhnv.luxuryhomestay.ui.main.social.post.detail;

import com.minhnv.luxuryhomestay.data.DataManager;
import com.minhnv.luxuryhomestay.ui.base.BaseViewModel;
import com.minhnv.luxuryhomestay.utils.rx.SchedulerProvider;

public class PostDetailViewModel extends BaseViewModel<PostDetailNavigator> {
    public PostDetailViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
