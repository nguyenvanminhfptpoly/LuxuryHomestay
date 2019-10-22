package com.minhnv.luxuryhomestay.ui.main.homestay_hot;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.Homestay;
import com.minhnv.luxuryhomestay.ui.base.BaseActivity;
import com.minhnv.luxuryhomestay.ui.main.adapter.RecyclerViewNavigator;
import com.minhnv.luxuryhomestay.ui.main.adapter.StaggeredRecyclerViewAdapter;
import com.minhnv.luxuryhomestay.ui.main.homestay_detail.HomeStayDetailActivity;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

import java.util.ArrayList;
import java.util.List;

public class HomeStayHotActivity extends BaseActivity<HomeStayHotViewModel> implements HomeStayHotNavigator {
    private static final String TAG = "HomeStayHotActivity";
    private List<Homestay> homestays;
    private StaggeredRecyclerViewAdapter adapter;
    private SlidrInterface slide;

    @Override
    public int getLayoutId() {
        return R.layout.activity_home_stay_hot;
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, HomeStayHotActivity.class);
    }


    @Override
    public void onCreateActivity(@Nullable Bundle savedInstanceState) {
        viewmodel = ViewModelProviders.of(this,factory).get(HomeStayHotViewModel.class);
        viewmodel.setNavigator(this);
        slide = Slidr.attach(this);
        viewmodel.ServerLoadHomeStaysRating();
        RecyclerView recyclerView = findViewById(R.id.recyclerViewHomeStayHot);
        homestays = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        adapter = new StaggeredRecyclerViewAdapter(homestays, getApplicationContext(), new RecyclerViewNavigator() {
            @Override
            public void onItemClickListener(int position) {
                Intent intent = HomeStayDetailActivity.newIntent(getApplicationContext());
                intent.putExtra("detail",homestays.get(position));
                startActivity(intent);
            }

            @Override
            public void onItemClickDetailListener(int position) {

            }
        });
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void HandlerError(Throwable throwable) {
        if(!isNetworkConnected()){
            backToLogin();
        }
        Log.d(TAG, "HandlerError: "+throwable);
    }

    @Override
    public void onSuccess() {
        Log.d(TAG, "onUploadImageSuccess: ");
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
}
