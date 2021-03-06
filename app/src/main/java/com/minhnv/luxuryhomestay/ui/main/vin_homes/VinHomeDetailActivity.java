package com.minhnv.luxuryhomestay.ui.main.vin_homes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.androidnetworking.widget.ANImageView;
import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.ListVinHomes;
import com.minhnv.luxuryhomestay.data.model.VinHome;
import com.minhnv.luxuryhomestay.ui.base.BaseActivity;
import com.minhnv.luxuryhomestay.ui.main.adapter.LinearLayoutManagerWithSmoothScroller;
import com.minhnv.luxuryhomestay.ui.main.adapter.VinHomeDetailAdapter;
import com.minhnv.luxuryhomestay.ui.main.adapter.viewholder.CityDetailViewHolder;
import com.minhnv.luxuryhomestay.ui.main.homestay_detail.HomeStayDetailActivity;
import com.minhnv.luxuryhomestay.utils.AppLogger;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class VinHomeDetailActivity extends BaseActivity<VinHomeDetailViewModel> implements VinHomeDetailNavigator, CityDetailViewHolder.UserActionListener {
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkThemes);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vin_home_detail);
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
        toolbar.setNavigationOnClickListener(v -> 
                onBackPressed()
        );

    }

    private void setUpRecyclerView(){
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(getApplicationContext(), R.drawable.item_decoration)));
        RecyclerView recyclerView = findViewById(R.id.recyclerViewVinHomesDetail);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManagerWithSmoothScroller(this));
        listVinHomes = new ArrayList<>();
        adapter = new VinHomeDetailAdapter(listVinHomes, getApplicationContext());
        adapter.setUserAction(this);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(itemDecorator);
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
            .subscribe(response ->
                adapter.set(response)
            ,throwable ->
                AppLogger.d(TAG,throwable)
            ));
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
    public void onActionDetailByUser(int position) {
        Intent detail = HomeStayDetailActivity.newIntent(getApplicationContext());
        detail.putExtra("vinHomes",listVinHomes.get(position));
        startActivity(detail);
    }

}
