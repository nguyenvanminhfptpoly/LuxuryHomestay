package com.minhnv.luxuryhomestay.ui.main.homestay_price_ago;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.HomestayPrice;
import com.minhnv.luxuryhomestay.ui.base.BaseActivity;
import com.minhnv.luxuryhomestay.ui.main.adapter.RecyclerViewNavigator;
import com.minhnv.luxuryhomestay.ui.main.adapter.StaggeredAdapter;
import com.minhnv.luxuryhomestay.ui.main.homestay_detail.HomeStayDetailActivity;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class
HomeStayPriceAgoActivity extends BaseActivity<HomeStayPriceViewModel> implements HomeStayPriceNavigator {
    private static final String TAG = "HomeStayPriceAgoActivit";
    private List<HomestayPrice> homestays;
    private StaggeredAdapter adapter;
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
        viewmodel.ServerLoadHomeStaysPriceAsc();
        RecyclerView recyclerView = findViewById(R.id.recyclerViewHomeStayPrice);
        homestays = new ArrayList<>();
        adapter = new StaggeredAdapter(homestays, getApplicationContext(), new RecyclerViewNavigator() {
            @Override
            public void onItemClickListener(int position) {
                Intent intent = HomeStayDetailActivity.newIntent(getApplicationContext());
                intent.putExtra("detailprice", homestays.get(position));
                startActivity(intent);
            }

            @Override
            public void onItemClickDetailListener(int position) {

            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void HandlerError(Throwable throwable) {
        if (!isNetworkConnected()) {
            backToLogin();
        }
        Log.d(TAG, "HandlerError: " + throwable);
    }

    @Override
    public void onSuccess() {
        Log.d(TAG, "onUploadImageSuccess: ");
    }

    @Override
    public void doLoadHomeStaysPriceAsc() {
        viewmodel.loadListHomeStaysPriceAsc();
        compositeDisposable.add(viewmodel.listPublishSubject.share()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(data -> {
                    homestays.addAll(data);
                    adapter.notifyDataSetChanged();
                        }, throwable ->
                                Log.d(TAG, "doLoadHomeStaysRating: " + throwable)
                ));
    }
}
