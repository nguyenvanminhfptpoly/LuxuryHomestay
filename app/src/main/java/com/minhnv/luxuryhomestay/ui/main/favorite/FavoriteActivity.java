package com.minhnv.luxuryhomestay.ui.main.favorite;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.Booking;
import com.minhnv.luxuryhomestay.data.model.Favorite;
import com.minhnv.luxuryhomestay.ui.base.BaseActivity;
import com.minhnv.luxuryhomestay.ui.main.adapter.FavoriteAdapter;
import com.minhnv.luxuryhomestay.ui.main.adapter.RecyclerViewNavigator;
import com.minhnv.luxuryhomestay.ui.main.booking.list.ListBookingActivity;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FavoriteActivity extends BaseActivity<FavoriteViewModel> implements FavoriteNavigator {
    private static final String TAG = "FavoriteActivity";
    private List<Favorite> favorites;
    private FavoriteAdapter adapter;
    private SlidrInterface slide;

    public static Intent newIntent(Context context) {
        return new Intent(context, FavoriteActivity.class);

    }


    @Override
    public int getLayoutId() {
        return R.layout.favorite_fragment;
    }

    @Override
    public void onCreateActivity(@Nullable Bundle savedInstanceState) {
        viewmodel = ViewModelProviders.of(this,factory).get(FavoriteViewModel.class);
        viewmodel.setNavigator(this);
        slide = Slidr.attach(this);
        initView();
    }

    private void initView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerViewFavorite);
        Toolbar toolbar = findViewById(R.id.toolbarFavorite);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setTitle("Homestay yêu thích");
        toolbar.setNavigationOnClickListener(view -> {
            onBackPressed();
        });
        onNextList();
        viewmodel.ServerLoadFavorite();
        favorites = new ArrayList<>();
        adapter = new FavoriteAdapter(favorites, getApplicationContext(), new RecyclerViewNavigator() {
            @Override
            public void onItemClickListener(int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FavoriteActivity.this);
                builder.setTitle("Xóa homstay yêu thích này? ");
                builder.setMessage("Bạn có muốn xóa không?");
                builder.setCancelable(false);
                Favorite favorite = favorites.get(position);
                Integer id = Integer.valueOf(favorite.getId());
                builder.setPositiveButton("Đồng ý", (dialogInterface, i) -> {
                    favorites.clear();
                    viewmodel.ServerLoadFavorite();
                    viewmodel.deleteFavorite(id);
                    dialogInterface.dismiss();
                });
                builder.setNegativeButton("Không", (dialogInterface, i) -> dialogInterface.dismiss());
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }

            @Override
            public void onItemClickDetailListener(int position) {

            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
    }

    private void onNextList(){
        compositeDisposable.add(viewmodel.listBehaviorSubject.share()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(response -> {
                favorites.addAll(response);
                Collections.reverse(favorites);
                adapter.notifyDataSetChanged();
            },throwable -> {
                Log.d(TAG, "onNextList: "+throwable);
            }));
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
        adapter.notifyDataSetChanged();
        Log.d(TAG, "onSuccess: ");
    }

    @Override
    public void ServerLoadListFavorite() {
        viewmodel.loadFavorite();
    }
}
