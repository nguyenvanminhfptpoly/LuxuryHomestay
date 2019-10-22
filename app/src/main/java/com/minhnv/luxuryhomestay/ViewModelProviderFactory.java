package com.minhnv.luxuryhomestay;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.minhnv.luxuryhomestay.data.DataManager;
import com.minhnv.luxuryhomestay.ui.intro.IntroductionViewModel;
import com.minhnv.luxuryhomestay.ui.login.signin.SignInViewModel;
import com.minhnv.luxuryhomestay.ui.login.signup.SignUpViewModel;
import com.minhnv.luxuryhomestay.ui.main.HomeViewModel;
import com.minhnv.luxuryhomestay.ui.main.booking.booking.BookingViewModel;
import com.minhnv.luxuryhomestay.ui.main.booking.list.ListBookingViewModel;
import com.minhnv.luxuryhomestay.ui.main.favorite.FavoriteViewModel;
import com.minhnv.luxuryhomestay.ui.main.homestay_city.HomeStayCityViewModel;
import com.minhnv.luxuryhomestay.ui.main.homestay_detail.HomeStaysDetailViewModel;
import com.minhnv.luxuryhomestay.ui.main.homestay_hot.HomeStayHotViewModel;
import com.minhnv.luxuryhomestay.ui.main.homestay_price_ago.HomeStayPriceViewModel;
import com.minhnv.luxuryhomestay.ui.main.search.SearchViewModel;
import com.minhnv.luxuryhomestay.ui.main.social.list.SocialViewModel;
import com.minhnv.luxuryhomestay.ui.main.social.post.PostLuxuryViewModel;
import com.minhnv.luxuryhomestay.utils.rx.SchedulerProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ViewModelProviderFactory extends ViewModelProvider.NewInstanceFactory {
    private static final String TAG = "ViewModelProviderFactor";

    private DataManager dataManager;
    private SchedulerProvider schedulerProvider;

    @Inject
    public ViewModelProviderFactory(DataManager dataManager,
                             SchedulerProvider schedulerProvider) {
        this.dataManager = dataManager;
        this.schedulerProvider = schedulerProvider;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(SignInViewModel.class)){
            return (T) new SignInViewModel(dataManager,schedulerProvider);
        }else if(modelClass.isAssignableFrom(SignUpViewModel.class)){
            return (T) new SignUpViewModel(dataManager,schedulerProvider);
        }else if(modelClass.isAssignableFrom(HomeViewModel.class)){
            return (T) new HomeViewModel(dataManager,schedulerProvider);
        }else if(modelClass.isAssignableFrom(BookingViewModel.class)){
            return (T) new BookingViewModel(dataManager,schedulerProvider);
        }else if(modelClass.isAssignableFrom(FavoriteViewModel.class)){
            return (T) new FavoriteViewModel(dataManager,schedulerProvider);
        }else if(modelClass.isAssignableFrom(IntroductionViewModel.class)){
            return (T) new IntroductionViewModel(dataManager,schedulerProvider);
        }else if(modelClass.isAssignableFrom(SocialViewModel.class)){
            return (T) new SocialViewModel(dataManager,schedulerProvider);
        }else if(modelClass.isAssignableFrom(SearchViewModel.class)){
            return (T) new SearchViewModel(dataManager,schedulerProvider);
        }else if(modelClass.isAssignableFrom(HomeStayCityViewModel.class)){
            return (T) new HomeStayCityViewModel(dataManager,schedulerProvider);
        }else if(modelClass.isAssignableFrom(HomeStaysDetailViewModel.class)){
            return (T) new HomeStaysDetailViewModel(dataManager,schedulerProvider);
        }else if(modelClass.isAssignableFrom(ListBookingViewModel.class)){
            return (T) new ListBookingViewModel(dataManager,schedulerProvider);
        }else if(modelClass.isAssignableFrom(HomeStayPriceViewModel.class)){
            return (T) new HomeStayPriceViewModel(dataManager,schedulerProvider);
        }else if(modelClass.isAssignableFrom(HomeStayHotViewModel.class)){
            return (T) new HomeStayHotViewModel(dataManager,schedulerProvider);
        }else if(modelClass.isAssignableFrom(PostLuxuryViewModel.class)){
            return (T) new PostLuxuryViewModel(dataManager,schedulerProvider);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
