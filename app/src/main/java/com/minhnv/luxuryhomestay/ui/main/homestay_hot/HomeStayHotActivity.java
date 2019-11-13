package com.minhnv.luxuryhomestay.ui.main.homestay_hot;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.Homestay;
import com.minhnv.luxuryhomestay.ui.base.BaseActivity;
import com.minhnv.luxuryhomestay.ui.main.adapter.StaggeredRecyclerViewAdapter;
import com.minhnv.luxuryhomestay.ui.main.adapter.viewholder.StaggeredHomeStayViewHolder;
import com.minhnv.luxuryhomestay.ui.main.homestay_detail.HomeStayDetailActivity;
import com.minhnv.luxuryhomestay.utils.AppLogger;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class HomeStayHotActivity extends BaseActivity<HomeStayHotViewModel> implements HomeStayHotNavigator, StaggeredHomeStayViewHolder.UserActionListener {
    private static final String TAG = "HomeStayHotActivity";
    private List<Homestay> homestays;
    @Inject
    public StaggeredRecyclerViewAdapter adapter;
    private SlidrInterface slide;
    private Toolbar mToolBar;

    public static Intent newIntent(Context context) {
        return new Intent(context, HomeStayHotActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkThemes);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_stay_hot);
        initView();
        viewmodel = ViewModelProviders.of(this, factory).get(HomeStayHotViewModel.class);
        viewmodel.setNavigator(this);
        slide = Slidr.attach(this);
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        viewmodel.ServerLoadHomeStaysRating();
        RecyclerView recyclerView = findViewById(R.id.recyclerViewHomeStayHot);
        homestays = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        adapter = new StaggeredRecyclerViewAdapter(homestays, getApplicationContext());
        adapter.setUserAction(this);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void HandlerError(Throwable throwable) {
        if (!isNetworkConnected()) {
            backToLogin();
        }
        AppLogger.d(TAG, "HandlerError: " + throwable);
    }

    @Override
    public void onSuccess() {
        AppLogger.d(TAG, "onUploadImageSuccess: ");
    }

    @Override
    public void doLoadHomeStaysRating() {
        viewmodel.loadListHomeStayRating();
        compositeDisposable.add(viewmodel.homeStayPublishObservable.share()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(data -> adapter.set(data)));
    }

    @Override
    public void onActionViewDetailHsPriceByUser(int position) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 5000) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        Intent intent = HomeStayDetailActivity.newIntent(getApplicationContext());
        intent.putExtra("detail", homestays.get(position));
        startActivity(intent);
    }

    private void initView() {
        mToolBar = findViewById(R.id.toolbar4);
        setSupportActionBar(mToolBar);
        mToolBar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mToolBar.setNavigationOnClickListener(v -> onBackPressed());
    }
}
