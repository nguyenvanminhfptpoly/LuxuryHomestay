package com.minhnv.luxuryhomestay.ui.main.homestay_city;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.City;
import com.minhnv.luxuryhomestay.data.model.Homestay;
import com.minhnv.luxuryhomestay.ui.base.BaseActivity;
import com.minhnv.luxuryhomestay.ui.main.adapter.CityDetailAdapter;
import com.minhnv.luxuryhomestay.ui.main.adapter.HomeStaysAdapter;
import com.minhnv.luxuryhomestay.ui.main.adapter.RecyclerViewNavigator;
import com.minhnv.luxuryhomestay.ui.main.booking.booking.BookingActivity;
import com.minhnv.luxuryhomestay.ui.main.homestay_detail.HomeStayDetailActivity;
import com.minhnv.luxuryhomestay.utils.AppLogger;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class HomeStayCityActivity extends BaseActivity<HomeStayCityViewModel> implements HomeStayCityNavigator {

    private static final String TAG = "HomeStayCityActivity";
    private List<Homestay> homeStays;
    @Inject
    public CityDetailAdapter adapter;
    private Integer idCity;
    private Toolbar toolbarCity;
    private SlidrInterface slide;
    public static Intent newIntent(Context context) {
        return new Intent(context, HomeStayCityActivity.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_homestay_city;
    }

    @Override
    public void onCreateActivity(@Nullable Bundle savedInstanceState) {
        viewmodel = ViewModelProviders.of(this,factory).get(HomeStayCityViewModel.class);
        viewmodel.setNavigator(this);
        slide = Slidr.attach(this);
        setUpToolBar();
        setUpRecyclerViewCity();
        initIntent();
        fetchData();
    }
    private void setUpToolBar(){
        toolbarCity = findViewById(R.id.toolbarCity);
        setSupportActionBar(toolbarCity);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbarCity.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbarCity.setNavigationOnClickListener(view  -> {onBackPressed();});
    }
    private void setUpRecyclerViewCity(){
        RecyclerView recyclerViewCity = findViewById(R.id.recyclerviewFollowCity);
        recyclerViewCity.setHasFixedSize(true);
        recyclerViewCity.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        homeStays = new ArrayList<>();
        adapter = new CityDetailAdapter(homeStays, getApplicationContext(), new RecyclerViewNavigator() {
            @Override
            public void onItemClickListener(int position) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 5000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                Intent intent = HomeStayDetailActivity.newIntent(getApplicationContext());
                intent.putExtra("detail",homeStays.get(position));
                startActivity(intent);
            }

            @Override
            public void onItemClickDetailListener(int position) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 5000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                Intent intent = BookingActivity.newIntent(getApplicationContext());
                intent.putExtra("booking",homeStays.get(position));
                startActivity(intent);
            }

            @Override
            public void onItemSharing(int position) {

            }
        });
        recyclerViewCity.setAdapter(adapter);
    }
    private void fetchData(){
        compositeDisposable.add(viewmodel.listPublishSubject.share()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(data -> adapter.set(data)));
    }

    private void initIntent(){
        if(getIntent().getSerializableExtra("tphcm") != null){
            City city = (City) getIntent().getSerializableExtra("tphcm");
            assert city != null;
            idCity = city.getIdhomestays();
            toolbarCity.setTitle(city.getName());
            viewmodel.ServerLoadList();
        }else if(getIntent().getSerializableExtra("hanoi") != null){
            City city = (City) getIntent().getSerializableExtra("hanoi");
            assert city != null;
            idCity = city.getIdhomestays();
            toolbarCity.setTitle(city.getName());
            viewmodel.ServerLoadList();
        }else if(getIntent().getSerializableExtra("dalat") != null){
            City city = (City) getIntent().getSerializableExtra("dalat");
            assert city != null;
            idCity = city.getIdhomestays();
            toolbarCity.setTitle(city.getName());
            viewmodel.ServerLoadList();
        }
    }

    @Override
    public void HandlerError(Throwable throwable) {
        if(!isNetworkConnected()){
            backToLogin();
        }
        AppLogger.d(TAG, "HandlerError: "+throwable);
    }

    @Override
    public void onSuccess() {
        AppLogger.d(TAG, "onUploadImageSuccess: ");
    }

    @Override
    public void loadList() {
       viewmodel.loadlist(idCity);
    }


}
