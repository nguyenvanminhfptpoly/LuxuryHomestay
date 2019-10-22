package com.minhnv.luxuryhomestay.ui.main;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.City;
import com.minhnv.luxuryhomestay.data.model.Homestay;
import com.minhnv.luxuryhomestay.data.model.HomestayPrice;
import com.minhnv.luxuryhomestay.ui.base.BaseActivity;
import com.minhnv.luxuryhomestay.ui.intro.IntroductionActivity;
import com.minhnv.luxuryhomestay.ui.login.signin.SignInActivity;
import com.minhnv.luxuryhomestay.ui.main.adapter.CityAdapter;
import com.minhnv.luxuryhomestay.ui.main.adapter.HomeStaysAdapter;
import com.minhnv.luxuryhomestay.ui.main.adapter.HomeStaysPriceAscAdapter;
import com.minhnv.luxuryhomestay.ui.main.adapter.RecyclerViewNavigator;
import com.minhnv.luxuryhomestay.ui.main.booking.booking.BookingActivity;
import com.minhnv.luxuryhomestay.ui.main.booking.list.ListBookingActivity;
import com.minhnv.luxuryhomestay.ui.main.favorite.FavoriteActivity;
import com.minhnv.luxuryhomestay.ui.main.homestay_city.HomeStayCityActivity;
import com.minhnv.luxuryhomestay.ui.main.homestay_detail.HomeStayDetailActivity;
import com.minhnv.luxuryhomestay.ui.main.homestay_hot.HomeStayHotActivity;
import com.minhnv.luxuryhomestay.ui.main.homestay_price_ago.HomeStayPriceAgoActivity;
import com.minhnv.luxuryhomestay.ui.main.search.SearchActivity;
import com.minhnv.luxuryhomestay.ui.main.social.list.SocialActivity;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class HomeActivity extends BaseActivity<HomeViewModel> implements HomeNavigator {
    private static final String TAG = "HomeActivity";
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private List<Homestay> homestays;
    private List<City> cities;
    private HomeStaysAdapter adapter;
    private CityAdapter cityAdapter;
    private HomeStaysPriceAscAdapter ascAdapter;
    private List<HomestayPrice> homestayPrices;
    private ViewFlipper viewFlipper;


    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, HomeActivity.class);
    }



    @Override
    public void onCreateActivity(@Nullable Bundle savedInstanceState) {
        viewmodel = ViewModelProviders.of(this, factory).get(HomeViewModel.class);
        viewmodel.setNavigator(this);

        drawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbarHome = findViewById(R.id.toolbar_home);
        navigationView = findViewById(R.id.navigation_view);
        Button btnGotoBoking = findViewById(R.id.btnGotoBooking);
        viewFlipper = findViewById(R.id.viewflipper);
        Button btnDetailHSH = findViewById(R.id.buttonDetailHSH);
        Button btnDetailHSP = findViewById(R.id.btnDetailHSP);


        setSupportActionBar(toolbarHome);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbarHome.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        toolbarHome.setNavigationOnClickListener(view -> drawerLayout.openDrawer(GravityCompat.START));
        toolbarHome.setOnMenuItemClickListener(item -> {
            if  (item.getItemId() == R.id.ic_search) {
                startActivity(SearchActivity.newIntent(HomeActivity.this));
            }
            return false;
        });
        setUpNavigationView();

        //recyclerViewHomeStays
        RecyclerView recyclerViewHomeStays = findViewById(R.id.recyclerViewRating);
        RecyclerView recyclerViewPriceAsc = findViewById(R.id.recyclerViewPriceAsc);
        homestays = new ArrayList<>();
        homestayPrices = new ArrayList<>();
        viewmodel.ServerLoadHomeStaysRating();
        viewmodel.ServerLoadHomeStaysPriceAsc();
        ascAdapter = new HomeStaysPriceAscAdapter(homestayPrices, getApplicationContext(), new RecyclerViewNavigator() {
            @Override
            public void onItemClickListener(int position) {
                Intent intent = HomeStayDetailActivity.newIntent(getApplicationContext());
                intent.putExtra("detailprice",homestayPrices.get(position));
                startActivity(intent);
            }

            @Override
            public void onItemClickDetailListener(int position) {
                Intent intent = BookingActivity.newIntent(getApplicationContext());
                intent.putExtra("detailprice",homestayPrices.get(position));
                startActivity(intent);
            }
        });
        adapter = new HomeStaysAdapter(homestays, getApplicationContext(), new RecyclerViewNavigator() {
            @Override
            public void onItemClickListener(int position) {
                Intent intent = HomeStayDetailActivity.newIntent(getApplicationContext());
                intent.putExtra("detail",homestays.get(position));
                startActivity(intent);
            }

            @Override
            public void onItemClickDetailListener(int position) {
                Intent intent = BookingActivity.newIntent(getApplicationContext());
                intent.putExtra("booking",homestays.get(position));
                startActivity(intent);
            }
        });
        recyclerViewHomeStays.setHasFixedSize(true);
        recyclerViewHomeStays.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewHomeStays.setAdapter(adapter);
        recyclerViewPriceAsc.setHasFixedSize(true);
        recyclerViewPriceAsc.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerViewPriceAsc.setAdapter(ascAdapter);


        //city
        RecyclerView recyclerViewCity = findViewById(R.id.recyclerCity);
        cities = new ArrayList<>();
        viewmodel.ServerLoadCity();
        cityAdapter = new CityAdapter(getApplicationContext(), cities, new RecyclerViewNavigator() {
            @Override
            public void onItemClickListener(int position) {
                switch (position){
                    case 0:
                        Intent tphcm = HomeStayCityActivity.newIntent(HomeActivity.this);
                        tphcm.putExtra("tphcm",cities.get(position));
                        startActivity(tphcm);
                        break;
                    case 1:
                        Intent dalat = HomeStayCityActivity.newIntent(HomeActivity.this);
                        dalat.putExtra("dalat",cities.get(position));
                        startActivity(dalat);
                        break;
                    case 2:
                        Intent hanoi = HomeStayCityActivity.newIntent(HomeActivity.this);
                        hanoi.putExtra("hanoi",cities.get(position));
                        startActivity(hanoi);
                        break;
                }
            }

            @Override
            public void onItemClickDetailListener(int position) {

            }
        });
        recyclerViewCity.setHasFixedSize(true);
        recyclerViewCity.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerViewCity.setAdapter(cityAdapter);

        btnGotoBoking.setOnClickListener(view -> startActivity(ListBookingActivity.newIntent(getApplicationContext())));
        ActionViewFlipper();

        btnDetailHSH.setOnClickListener(view -> {startActivity(HomeStayHotActivity.newIntent(HomeActivity.this));});
        btnDetailHSP.setOnClickListener(view -> {startActivity(HomeStayPriceAgoActivity.newIntent(getApplicationContext()));});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void setUpNavigationView() {
        navigationView.findViewById(R.id.btnBooking).setOnClickListener(view -> viewmodel.openBookingFragment());
        navigationView.findViewById(R.id.btnFavorite).setOnClickListener(view -> viewmodel.openFavoriteFragment());
        navigationView.findViewById(R.id.btnSocial).setOnClickListener(view -> viewmodel.openSocialFragment());
        navigationView.findViewById(R.id.btnIntro).setOnClickListener(view -> viewmodel.openIntroductionFragment());
        navigationView.findViewById(R.id.btnCancelDrawer).setOnClickListener(view -> viewmodel.closeDrawer());
        navigationView.findViewById(R.id.btnLogout).setOnClickListener(view -> viewmodel.logout());
    }

    @Override
    public void openBookingActivity() {
        Intent intent = ListBookingActivity.newIntent(HomeActivity.this);
        startActivity(intent);
        drawerLayout.closeDrawers();
    }

    @Override
    public void openFavoriteActivity() {
        Intent intent = FavoriteActivity.newIntent(HomeActivity.this);
        startActivity(intent);
        drawerLayout.closeDrawers();
    }

    @Override
    public void openSocialActivity() {
        Intent intent = SocialActivity.newIntent(HomeActivity.this);
        startActivity(intent);
        drawerLayout.closeDrawers();
    }

    @Override
    public void openIntroActivity() {
        Intent intent = IntroductionActivity.newIntent(HomeActivity.this);
        startActivity(intent);
        drawerLayout.closeDrawers();
    }

    @Override
    public void logout() {
        startActivity(SignInActivity.newIntent(this));

    }

    @Override
    public void close() {
        drawerLayout.closeDrawers();
    }

    @Override
    public void HandlerError(Throwable throwable) {
        if(!isNetworkConnected()){
            backToLogin();
        }
        Log.d(TAG, "HandlerError: " + throwable);
    }

    @Override
    public void onSuccess() {
        Log.d(TAG, "onUploadImageSuccess: ");
    }



    @Override
    public void doLoadCity() {
        viewmodel.loadCity();
        compositeDisposable.add(viewmodel.listCityPublishSubject.share()
        .subscribeOn(schedulerProvider.io())
        .observeOn(schedulerProvider.ui())
        .subscribe(data -> {
            cities.addAll(data);
            cityAdapter.notifyDataSetChanged();
        },throwable ->
            Log.d(TAG, "doLoadCity: "+throwable)
        ));
    }

    @Override
    public void doLoadHomeStaysRating() {
        viewmodel.loadListHomeStayRating();
        compositeDisposable.add(viewmodel.homeStayPublishObservable.share()
        .subscribeOn(schedulerProvider.io())
        .observeOn(schedulerProvider.ui())
        .subscribe(data -> {
                homestays.addAll(data);
                adapter.notifyDataSetChanged();
        },throwable ->
            Log.d(TAG, "doLoadHomeStaysRating: "+throwable)
        ));
    }

    @Override
    public void doLoadHomeStaysPriceAsc() {
        viewmodel.loadListHomeStaysPriceAsc();
        compositeDisposable.add(viewmodel.listPublishSubject.share()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(data -> {
                    homestayPrices.addAll(data);
                    ascAdapter.notifyDataSetChanged();
                },throwable ->
                    Log.d(TAG, "doLoadHomeStaysRating: "+throwable)
                ));
    }

    private void ActionViewFlipper(){
        ArrayList<String> adMob = new ArrayList<>();
        adMob.add("https://www.luxstay.com/blog/wp-content/uploads/2019/09/BANNER-03-1024x536.jpg");
        adMob.add("https://www.luxstay.com/blog/wp-content/uploads/2019/09/BANNER-04-1024x536.jpg");
        adMob.add("https://www.luxstay.com/blog/wp-content/uploads/2019/09/Untitled-1-03-1024x536.jpg");
        adMob.add("https://www.luxstay.com/blog/wp-content/uploads/2019/09/banner-01-1-1024x536.jpg");
        for (int i = 0;i< adMob.size(); i++){
            ImageView img = new ImageView(HomeActivity.this);
            Picasso.get().load(adMob.get(i)).error(R.drawable.uploadfailed).into(img);
            img.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(img);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        viewFlipper.setAnimation(animation);
    }


}
