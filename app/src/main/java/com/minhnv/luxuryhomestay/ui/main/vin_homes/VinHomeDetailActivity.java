package com.minhnv.luxuryhomestay.ui.main.vin_homes;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.androidnetworking.widget.ANImageView;
import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.ListVinHomes;
import com.minhnv.luxuryhomestay.data.model.VinHome;
import com.minhnv.luxuryhomestay.ui.base.BaseActivity;
import com.minhnv.luxuryhomestay.ui.main.adapter.VinHomeDetailAdapter;
import com.minhnv.luxuryhomestay.ui.main.adapter.viewholder.CityDetailViewHolder;
import com.minhnv.luxuryhomestay.ui.main.booking.booking.BookingActivity;
import com.minhnv.luxuryhomestay.ui.main.homestay_detail.HomeStayDetailActivity;
import com.minhnv.luxuryhomestay.utils.AppLogger;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class VinHomeDetailActivity extends BaseActivity<VinHomeDetailViewModel> implements VinHomeDetailNavigator, CityDetailViewHolder.CallBack {
    private static final String TAG = "VinHomeDetailActivity";
    private Toolbar toolbar;
    @Inject
    public VinHomeDetailAdapter adapter;
    private List<ListVinHomes> listVinHomes;
    private Integer idVinHomes;
    private ANImageView anImageView;
    private SnapHelper helper;
    private SlidrInterface slide;


    public static Intent newIntent(Context context) {
        return new Intent(context, VinHomeDetailActivity.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_vin_home_detail;
    }

    @Override
    public void onCreateActivity(@Nullable Bundle savedInstanceState) {
        viewmodel = ViewModelProviders.of(this,factory).get(VinHomeDetailViewModel.class);
        viewmodel.setNavigator(this);
        slide = Slidr.attach(this);
        initView();
        setUpRecyclerView();
        initIntent();
        fetchData();
    }

    private void initView() {
        toolbar = findViewById(R.id.toolBarVinHomesDetail);
        setSupportActionBar(toolbar);
        toolbar.setTitle("VinHomes");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(view ->
                onBackPressed()
        );

    }

    private void setUpRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recyclerViewVinHomesDetail);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        listVinHomes = new ArrayList<>();
        adapter = new VinHomeDetailAdapter(listVinHomes, getApplicationContext());
        adapter.setCallBack(this);
        recyclerView.setAdapter(adapter);
    }

    private void initIntent(){
        anImageView = findViewById(R.id.imgVinHomesDetail);
        anImageView.setErrorImageResId(R.drawable.uploadfailed);
        anImageView.setDefaultImageResId(R.drawable.img_home1);
        if(getIntent().getSerializableExtra("central") != null){
            VinHome vinHome = (VinHome) getIntent().getSerializableExtra("central");
            assert vinHome != null;
            toolbar.setTitle(vinHome.getName());
            idVinHomes = vinHome.getIdvinhomes();
            anImageView.setImageUrl(vinHome.getImage());
            viewmodel.ServerLoadList();
        }else if(getIntent().getSerializableExtra("landMark") != null){
            VinHome vinHome = (VinHome) getIntent().getSerializableExtra("landMark");
            assert vinHome != null;
            toolbar.setTitle(vinHome.getName());
            idVinHomes = vinHome.getIdvinhomes();
            anImageView.setImageUrl(vinHome.getImage());
            viewmodel.ServerLoadList();
        }else if(getIntent().getSerializableExtra("golden") != null){
            VinHome vinHome = (VinHome) getIntent().getSerializableExtra("golden");
            assert vinHome != null;
            toolbar.setTitle(vinHome.getName());
            idVinHomes = vinHome.getIdvinhomes();
            anImageView.setImageUrl(vinHome.getImage());
            viewmodel.ServerLoadList();
        }else if(getIntent().getSerializableExtra("timesCity") != null){
            VinHome vinHome = (VinHome) getIntent().getSerializableExtra("timesCity");
            assert vinHome != null;
            toolbar.setTitle(vinHome.getName());
            idVinHomes = vinHome.getIdvinhomes();
            anImageView.setImageUrl(vinHome.getImage());
            viewmodel.ServerLoadList();
        }
    }

    private void fetchData(){
        compositeDisposable.add(viewmodel.listPublishSubject.share()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(response -> {
                listVinHomes.addAll(response);
                adapter.notifyDataSetChanged();
            },throwable -> {
                AppLogger.d(TAG,throwable);
            }));
    }

    @Override
    public void handlerError(Throwable throwable) {
        AppLogger.d(TAG,throwable);
    }

    @Override
    public void onSuccess() {
        AppLogger.d(TAG,"success");
    }

    @Override
    public void ServerLoadList() {
        viewmodel.loadList(idVinHomes);
    }

    @Override
    public void openDetail(int position) {
        Intent detail = HomeStayDetailActivity.newIntent(getApplicationContext());
        detail.putExtra("vinHomes",listVinHomes.get(position));
        startActivity(detail);
    }

    @Override
    public void openBooking(int position) {
        Intent booking = BookingActivity.newIntent(getApplicationContext());
        booking.putExtra("vinHomes",listVinHomes.get(position));
        startActivity(booking);
    }
}
