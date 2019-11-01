package com.minhnv.luxuryhomestay.ui.main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.google.android.material.navigation.NavigationView;
import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.City;
import com.minhnv.luxuryhomestay.data.model.Homestay;
import com.minhnv.luxuryhomestay.data.model.HomestayPrice;
import com.minhnv.luxuryhomestay.data.model.Luxury;
import com.minhnv.luxuryhomestay.data.model.VinHome;
import com.minhnv.luxuryhomestay.ui.base.BaseActivity;
import com.minhnv.luxuryhomestay.ui.intro.IntroductionActivity;
import com.minhnv.luxuryhomestay.ui.login.signin.SignInActivity;
import com.minhnv.luxuryhomestay.ui.main.adapter.CityAdapter;
import com.minhnv.luxuryhomestay.ui.main.adapter.HomeStaysAdapter;
import com.minhnv.luxuryhomestay.ui.main.adapter.HomeStaysPriceAscAdapter;
import com.minhnv.luxuryhomestay.ui.main.adapter.LinearLayoutManagerWithSmoothScroller;
import com.minhnv.luxuryhomestay.ui.main.adapter.RecyclerViewNavigator;
import com.minhnv.luxuryhomestay.ui.main.adapter.SnapHelperOneByOne;
import com.minhnv.luxuryhomestay.ui.main.adapter.SocialAdapter;
import com.minhnv.luxuryhomestay.ui.main.adapter.StartSnapHelper;
import com.minhnv.luxuryhomestay.ui.main.adapter.VinHomeAdapter;
import com.minhnv.luxuryhomestay.ui.main.booking.booking.BookingActivity;
import com.minhnv.luxuryhomestay.ui.main.booking.list.ListBookingActivity;
import com.minhnv.luxuryhomestay.ui.main.favorite.FavoriteActivity;
import com.minhnv.luxuryhomestay.ui.main.homestay_city.HomeStayCityActivity;
import com.minhnv.luxuryhomestay.ui.main.homestay_detail.HomeStayDetailActivity;
import com.minhnv.luxuryhomestay.ui.main.homestay_hot.HomeStayHotActivity;
import com.minhnv.luxuryhomestay.ui.main.homestay_price_ago.HomeStayPriceAgoActivity;
import com.minhnv.luxuryhomestay.ui.main.search.SearchActivity;
import com.minhnv.luxuryhomestay.ui.main.social.list.SocialActivity;
import com.minhnv.luxuryhomestay.ui.main.vin_homes.VinHomeDetailActivity;
import com.minhnv.luxuryhomestay.utils.AppLogger;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
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
    private SnapHelper helper;
    private Button btnGotoBooking,btnDetailHSH,btnDetailHSP;
    private List<VinHome> vinHomes;
    private VinHomeAdapter homeAdapter;
    private ImageView imgPayment,imgGuideBooking;
    private TextView tvUserName,tvAddress,tvNameUser;

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
        initView();
        setUpToolBar();
        setUpNavigationView();
        setUpRecyclerViewCity();
        setUpRecyclerViewHomeStayHot();
        setUpRecyclerViewHomeStayPriceAsc();
        setUpRecyclerViewVinHomes();
        ActionViewFlipper();
    }
    private void initView(){
        drawerLayout = findViewById(R.id.drawer_layout);
        btnGotoBooking = findViewById(R.id.btnGotoBooking);
        btnDetailHSH = findViewById(R.id.buttonDetailHSH);
        btnDetailHSP = findViewById(R.id.btnDetailHSP);
        tvNameUser = findViewById(R.id.tvNameUser);
        btnGotoBooking.setOnClickListener(view -> startActivity(ListBookingActivity.newIntent(getApplicationContext())));
        btnDetailHSH.setOnClickListener(view -> {startActivity(HomeStayHotActivity.newIntent(HomeActivity.this));});
        btnDetailHSP.setOnClickListener(view -> {startActivity(HomeStayPriceAgoActivity.newIntent(getApplicationContext()));});
        imgGuideBooking = findViewById(R.id.imgGuideBooking);
        imgPayment = findViewById(R.id.imgGuidePayment);
        imgPayment.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
            View view1 = getLayoutInflater().inflate(R.layout.dialog_guide_payments,null);
            builder.setView(view1);
            Dialog dialog = builder.create();
            dialog.show();
        });
        imgGuideBooking.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
            View v = getLayoutInflater().inflate(R.layout.dialog_guide_booking,null);
            builder.setView(v);
            Dialog dialog = builder.create();
            dialog.show();
        });


    }
    private void setUpRecyclerViewCity(){
        //city

        helper = new SnapHelperOneByOne();
        RecyclerView recyclerViewCity = findViewById(R.id.recyclerCity);
        viewmodel.ServerLoadCity();
        cities = new ArrayList<>();
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

            @Override
            public void onItemSharing(int position) {

            }
        });
        recyclerViewCity.setHasFixedSize(true);
        recyclerViewCity.setLayoutManager(new LinearLayoutManagerWithSmoothScroller(this, LinearLayoutManagerWithSmoothScroller.HORIZONTAL,false));
        recyclerViewCity.setAdapter(cityAdapter);
        helper.attachToRecyclerView(recyclerViewCity);

    }
    private void setUpRecyclerViewHomeStayHot(){

        helper = new SnapHelperOneByOne();
        RecyclerView recyclerViewHomeStays = findViewById(R.id.recyclerViewRating);
        homestays = new ArrayList<>();
        viewmodel.ServerLoadHomeStaysRating();
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

            @Override
            public void onItemSharing(int position) {

            }
        });
        recyclerViewHomeStays.setHasFixedSize(true);
        recyclerViewHomeStays.setLayoutManager(new LinearLayoutManagerWithSmoothScroller(this, LinearLayoutManagerWithSmoothScroller.HORIZONTAL,false));
        recyclerViewHomeStays.setAdapter(adapter);
        helper.attachToRecyclerView(recyclerViewHomeStays);
    }
    private void setUpRecyclerViewHomeStayPriceAsc(){

        helper = new SnapHelperOneByOne();
        RecyclerView recyclerViewPriceAsc = findViewById(R.id.recyclerViewPriceAsc);
        homestayPrices = new ArrayList<>();
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

            @Override
            public void onItemSharing(int position) {

            }
        });

        recyclerViewPriceAsc.setHasFixedSize(true);
        recyclerViewPriceAsc.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerViewPriceAsc.setAdapter(ascAdapter);
        helper.attachToRecyclerView(recyclerViewPriceAsc);
    }
    private void setUpToolBar(){
        Toolbar toolbarHome = findViewById(R.id.toolbar_home);
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
    }
    private void setUpRecyclerViewVinHomes(){

        helper = new SnapHelperOneByOne();
        RecyclerView recyclerView = findViewById(R.id.recyclerViewVinHomes);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManagerWithSmoothScroller(this, LinearLayoutManagerWithSmoothScroller.HORIZONTAL,false));
        vinHomes = new ArrayList<>();
        viewmodel.ServerLoadCityVinHomes();
        homeAdapter = new VinHomeAdapter(vinHomes, getApplicationContext(), new RecyclerViewNavigator() {
            @Override
            public void onItemClickListener(int position) {
                switch (position){
                    case 0:
                        Intent central = VinHomeDetailActivity.newIntent(getApplicationContext());
                        central.putExtra("central",vinHomes.get(position));
                        startActivity(central);
                        break;
                    case 1:
                        Intent landMark = VinHomeDetailActivity.newIntent(getApplicationContext());
                        landMark.putExtra("landMark",vinHomes.get(position));
                        startActivity(landMark);
                        break;
                    case 2:
                        Intent golden = VinHomeDetailActivity.newIntent(getApplicationContext());
                        golden.putExtra("golden",vinHomes.get(position));
                        startActivity(golden);
                        break;
                    case 3:
                        Intent timesCity = VinHomeDetailActivity.newIntent(getApplicationContext());
                        timesCity.putExtra("timesCity",vinHomes.get(position));
                        startActivity(timesCity);
                        break;
                }
            }

            @Override
            public void onItemClickDetailListener(int position) {
                //never use
            }

            @Override
            public void onItemSharing(int position) {
                //never use
            }
        });
        recyclerView.setAdapter(homeAdapter);
        helper.attachToRecyclerView(recyclerView);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void setUpNavigationView() {
        navigationView = findViewById(R.id.navigation_view);
        navigationView.findViewById(R.id.btnBooking).setOnClickListener(view -> viewmodel.openBookingFragment());
        navigationView.findViewById(R.id.btnFavorite).setOnClickListener(view -> viewmodel.openFavoriteFragment());
        navigationView.findViewById(R.id.btnSocial).setOnClickListener(view -> viewmodel.openSocialFragment());
        navigationView.findViewById(R.id.btnIntro).setOnClickListener(view -> viewmodel.openIntroductionFragment());
        navigationView.findViewById(R.id.btnCancelDrawer).setOnClickListener(view -> viewmodel.closeDrawer());
        navigationView.findViewById(R.id.btnLogout).setOnClickListener(view -> viewmodel.logout());
        tvUserName = navigationView.findViewById(R.id.tvUserName);
        tvAddress = navigationView.findViewById(R.id.tvAddress);
        tvAddress.setText(appPreferenceHelper.getCurrentPhoneNumber());
        tvUserName.setText(appPreferenceHelper.getCurrentAddress() == null ? "" : appPreferenceHelper.getCurrentAddress());
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
        AppLogger.d(TAG, "HandlerError: " + throwable);
    }

    @Override
    public void onSuccess() {
        AppLogger.d(TAG, "onUploadImageSuccess: ");
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
                AppLogger.d(TAG, "doLoadCity: "+throwable)
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
                AppLogger.d(TAG, "doLoadHomeStaysRating: "+throwable)
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
                        AppLogger.d(TAG, "doLoadHomeStaysRating: "+throwable)
                ));
    }

    @Override
    public void doLoadCityVinHome() {
        viewmodel.loadCityVinHome();
        compositeDisposable.add(viewmodel.listBehaviorSubject.share()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(data -> {
                vinHomes.addAll(data);
                homeAdapter.notifyDataSetChanged();
            },throwable -> {
                AppLogger.d(TAG,throwable);
            }));
    }




    private void ActionViewFlipper(){
        viewFlipper = findViewById(R.id.viewflipper);
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
