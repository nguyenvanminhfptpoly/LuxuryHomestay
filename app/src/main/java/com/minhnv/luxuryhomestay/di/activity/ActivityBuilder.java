package com.minhnv.luxuryhomestay.di.activity;

import com.minhnv.luxuryhomestay.ui.intro.IntroductionActivity;
import com.minhnv.luxuryhomestay.ui.login.signin.SignInActivity;
import com.minhnv.luxuryhomestay.ui.login.signup.SignUpActivity;
import com.minhnv.luxuryhomestay.ui.main.HomeActivity;
import com.minhnv.luxuryhomestay.ui.main.booking.booking.BookingActivity;
import com.minhnv.luxuryhomestay.ui.main.booking.list.ListBookingActivity;
import com.minhnv.luxuryhomestay.ui.main.favorite.FavoriteActivity;
import com.minhnv.luxuryhomestay.ui.main.homestay_city.HomeStayCityActivity;
import com.minhnv.luxuryhomestay.ui.main.homestay_detail.HomeStayDetailActivity;
import com.minhnv.luxuryhomestay.ui.main.homestay_hot.HomeStayHotActivity;
import com.minhnv.luxuryhomestay.ui.main.homestay_price_ago.HomeStayPriceAgoActivity;
import com.minhnv.luxuryhomestay.ui.main.search.SearchActivity;
import com.minhnv.luxuryhomestay.ui.main.social.list.SocialActivity;
import com.minhnv.luxuryhomestay.ui.main.social.post.PostLuxuryActivity;
import com.minhnv.luxuryhomestay.ui.main.social.story.PostStoryActivity;
import com.minhnv.luxuryhomestay.ui.main.social.story.detail.DetailStoryActivity;
import com.minhnv.luxuryhomestay.ui.splash.SplashActivity;
import com.minhnv.luxuryhomestay.ui.main.vin_homes.VinHomeDetailActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract SignInActivity signInActivity();

    @ContributesAndroidInjector
    abstract SignUpActivity signUpActivity();

    @ContributesAndroidInjector
    abstract HomeActivity homeActivity();

    @ContributesAndroidInjector
    abstract BookingActivity bookingFragment();

    @ContributesAndroidInjector
    abstract FavoriteActivity favoriteFragment();

    @ContributesAndroidInjector
    abstract IntroductionActivity introductionFragment();

    @ContributesAndroidInjector
    abstract SocialActivity socialFragment();

    @ContributesAndroidInjector
    abstract SearchActivity searchActivity();

    @ContributesAndroidInjector
    abstract HomeStayCityActivity homestayCityActivity();

    @ContributesAndroidInjector
    abstract HomeStayDetailActivity homeStayDetailActivity();

    @ContributesAndroidInjector
    abstract ListBookingActivity listBookingActivity();

    @ContributesAndroidInjector
    abstract HomeStayHotActivity homeStayHotActivity();

    @ContributesAndroidInjector
    abstract HomeStayPriceAgoActivity homeStayPriceAgoActivity();

    @ContributesAndroidInjector
    abstract PostLuxuryActivity postLuxuryActivity();

    @ContributesAndroidInjector
    abstract SplashActivity splashActivity();

    @ContributesAndroidInjector
    abstract VinHomeDetailActivity vinHomeDetailActivity();

    @ContributesAndroidInjector
    abstract PostStoryActivity postStoryActivity();

    @ContributesAndroidInjector
    abstract DetailStoryActivity detailStoryActivity();
}
