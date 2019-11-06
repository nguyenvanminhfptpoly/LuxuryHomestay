package com.minhnv.luxuryhomestay.ui.main.homestay_price_ago;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.HomestayPrice;
import com.minhnv.luxuryhomestay.ui.base.BaseActivity;
import com.minhnv.luxuryhomestay.ui.main.adapter.StaggeredAdapter;
import com.minhnv.luxuryhomestay.ui.main.adapter.viewholder.StaggeredPriceViewHolder;
import com.minhnv.luxuryhomestay.ui.main.homestay_detail.HomeStayDetailActivity;
import com.minhnv.luxuryhomestay.utils.AppLogger;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class
HomeStayPriceAgoActivity extends BaseActivity<HomeStayPriceViewModel> implements HomeStayPriceNavigator, StaggeredPriceViewHolder.CallBack {
    private static final String TAG = "HomeStayPriceAgoActivit";
    private List<HomestayPrice> homestays;
    @Inject
    public StaggeredAdapter adapter;
    private SlidrInterface slide;


    public static Intent newIntent(Context context) {
        return new Intent(context, HomeStayPriceAgoActivity.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home_stay_price_ago;
    }

    @Override
    public void onCreateActivity(@Nullable Bundle savedInstanceState) {
        viewmodel = ViewModelProviders.of(this, factory).get(HomeStayPriceViewModel.class);
        viewmodel.setNavigator(this);
        slide = Slidr.attach(this);
        setUpRecyclerView();
    }
    private void setUpRecyclerView(){
        viewmodel.ServerLoadHomeStaysPriceAsc();
        RecyclerView recyclerView = findViewById(R.id.recyclerViewHomeStayPrice);
        homestays = new ArrayList<>();
        adapter = new StaggeredAdapter(homestays, getApplicationContext());
        adapter.setCallBack(this);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerView.setHasFixedSize(true);
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
    public void doLoadHomeStaysPriceAsc() {
        viewmodel.loadListHomeStaysPriceAsc();
        compositeDisposable.add(viewmodel.listPublishSubject.share()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(data ->
                    adapter.set(data)
                ));
    }

    @Override
    public void viewDetail(int position) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 5000) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        Intent intent = HomeStayDetailActivity.newIntent(getApplicationContext());
        intent.putExtra("detailprice", homestays.get(position));
        startActivity(intent);
    }
}
