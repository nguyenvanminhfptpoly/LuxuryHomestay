package com.minhnv.luxuryhomestay.di.activity;

import com.minhnv.luxuryhomestay.di.activity.home.HomeScope;
import com.minhnv.luxuryhomestay.di.activity.main.booking.BookingScope;
import com.minhnv.luxuryhomestay.di.activity.main.favorite.FavoriteScope;
import com.minhnv.luxuryhomestay.di.activity.main.homestay_city.HomeCityScope;
import com.minhnv.luxuryhomestay.di.activity.main.homestay_hot.HomeHotScope;
import com.minhnv.luxuryhomestay.di.activity.main.homestay_price_ago.HomePriceScope;
import com.minhnv.luxuryhomestay.di.activity.main.social.SocialScope;
import com.minhnv.luxuryhomestay.di.activity.main.vinhomes.VinHomeScope;
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
import com.minhnv.luxuryhomestay.ui.main.vin_homes.VinHomeDetailActivity;
import com.minhnv.luxuryhomestay.ui.splash.SplashActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract SignInActivity signInActivity();

    @ContributesAndroidInjector
    abstract SignUpActivity signUpActivity();

    @HomeScope
    @ContributesAndroidInjector
    abstract HomeActivity homeActivity();


    @ContributesAndroidInjector
    abstract BookingActivity bookingFragment();

    @FavoriteScope
    @ContributesAndroidInjector
    abstract FavoriteActivity favoriteFragment();

    @ContributesAndroidInjector
    abstract IntroductionActivity introductionFragment();

    @SocialScope
    @ContributesAndroidInjector
    abstract SocialActivity socialFragment();


    @ContributesAndroidInjector
    abstract SearchActivity searchActivity();

    @HomeCityScope
    @ContributesAndroidInjector
    abstract HomeStayCityActivity homestayCityActivity();

    @ContributesAndroidInjector
    abstract HomeStayDetailActivity homeStayDetailActivity();

    @BookingScope
    @ContributesAndroidInjector
    abstract ListBookingActivity listBookingActivity();

    @HomeHotScope
    @ContributesAndroidInjector
    abstract HomeStayHotActivity homeStayHotActivity();

    @HomePriceScope
    @ContributesAndroidInjector
    abstract HomeStayPriceAgoActivity homeStayPriceAgoActivity();

    @ContributesAndroidInjector
    abstract PostLuxuryActivity postLuxuryActivity();

    @ContributesAndroidInjector
    abstract SplashActivity splashActivity();

    @VinHomeScope
    @ContributesAndroidInjector
    abstract VinHomeDetailActivity vinHomeDetailActivity();

    @ContributesAndroidInjector
    abstract PostStoryActivity postStoryActivity();

    @ContributesAndroidInjector
    abstract DetailStoryActivity detailStoryActivity();
}
