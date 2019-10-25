package com.minhnv.luxuryhomestay.ui.main.social.list;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.Luxury;
import com.minhnv.luxuryhomestay.ui.base.BaseActivity;
import com.minhnv.luxuryhomestay.ui.main.adapter.LuxuryAdapter;
import com.minhnv.luxuryhomestay.ui.main.adapter.RecyclerViewNavigator;
import com.minhnv.luxuryhomestay.ui.main.homestay_detail.HomeStayDetailActivity;
import com.minhnv.luxuryhomestay.ui.main.social.post.PostLuxuryActivity;
import com.minhnv.luxuryhomestay.utils.AppLogger;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SocialActivity extends BaseActivity<SocialViewModel> implements SocialNavigator {
    private static final String TAG = "SocialActivity";
    private List<Luxury> luxuries;
    private LuxuryAdapter adapter;
    private Integer love = 0;
    private SlidrInterface slide;
    public static Intent newIntent(Context context) {
        return new Intent(context, SocialActivity.class);

    }

    @Override
    public int getLayoutId() {
        return R.layout.social_fragment;
    }

    @Override
    public void onCreateActivity(@Nullable Bundle savedInstanceState) {
        viewmodel = ViewModelProviders.of(this,factory).get(SocialViewModel.class);
        viewmodel.setNavigator(this);
        slide = Slidr.attach(this);
        initView();
    }

    private void initView(){
        Toolbar toolbar = findViewById(R.id.toolbarLuxury);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewLuxury);
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Luxury");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        floatingActionButton.setOnClickListener(view -> startActivity(PostLuxuryActivity.newIntent(getApplicationContext())));

        viewmodel.ServerLoadListLuxury();
        luxuries = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new LuxuryAdapter(luxuries, getApplicationContext(), new RecyclerViewNavigator() {
            @Override
            public void onItemClickListener(int position) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 5000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                Intent intent = HomeStayDetailActivity.newIntent(getApplicationContext());
                intent.putExtra("luxury_detail", luxuries.get(position));
                startActivity(intent);
            }

            @Override
            public void onItemClickDetailListener(int position) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 5000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                Luxury luxury = luxuries.get(position);
                Integer id = Integer.valueOf(luxury.getId());
                Integer countLove =  love++;
                viewmodel.addLoveForPost(id, countLove);
            }

            @Override
            public void onItemSharing(int position) {
                Luxury luxury = luxuries.get(position);
                Intent senIntent = new Intent();
                senIntent.setAction(Intent.ACTION_SEND);
                senIntent.putExtra(Intent.EXTRA_SUBJECT,luxury.getAddress());
                senIntent.putExtra(Intent.EXTRA_TEXT,luxury.getImage());
                senIntent.setType("text/plain");
                startActivity(Intent.createChooser(senIntent,"Chia sẻ đến"));
            }
        });
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            luxuries.clear();
            viewmodel.ServerLoadListLuxury();
            new Handler().postDelayed(() -> {
                // Stop animation (This will be after 3 seconds)
                swipeRefreshLayout.setRefreshing(false);
            }, 2000);
        });

       fetchData();
    }
    private void fetchData(){
        compositeDisposable.add(viewmodel.listPublishSubject.share()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(response -> {
                            luxuries.addAll(0,response);
                            Collections.reverse(luxuries);
                            adapter.notifyDataSetChanged();
                        },throwable ->
                        AppLogger.d(TAG, "loadList: "+throwable)
                ));
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
        AppLogger.d(TAG, "onSuccess: ");
    }

    @Override
    public void loadList() {
        viewmodel.luxuryList();
    }

}
