package com.minhnv.luxuryhomestay.ui.main.homestay_price_ago;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.HomestayPrice;
import com.minhnv.luxuryhomestay.ui.base.BaseActivity;
import com.minhnv.luxuryhomestay.ui.main.adapter.LinearLayoutManagerWithSmoothScroller;
import com.minhnv.luxuryhomestay.ui.main.adapter.StaggeredAdapter;
import com.minhnv.luxuryhomestay.ui.main.adapter.viewholder.StaggeredHomeStayViewHolder;
import com.minhnv.luxuryhomestay.ui.main.homestay_detail.HomeStayDetailActivity;
import com.minhnv.luxuryhomestay.utils.AppLogger;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class HomeStayPriceAgoActivity extends BaseActivity<HomeStayPriceViewModel> implements HomeStayPriceNavigator, StaggeredHomeStayViewHolder.UserActionListener {
    private static final String TAG = "HomeStayPriceAgoActivit";
    private List<HomestayPrice> homestays;
    @Inject
    public StaggeredAdapter adapter;
    private SlidrInterface slide;
    private Toolbar mToolbar;


    public static Intent newIntent(Context context) {
        return new Intent(context, HomeStayPriceAgoActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkThemes);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_stay_price_ago);
        initView();
        viewmodel = ViewModelProviders.of(this, factory).get(HomeStayPriceViewModel.class);
        viewmodel.setNavigator(this);
        slide = Slidr.attach(this);
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(getApplicationContext(), R.drawable.item_decoration)));
        viewmodel.ServerLoadHomeStaysPriceAsc();
        RecyclerView recyclerView = findViewById(R.id.recyclerViewHomeStayPrice);
        homestays = new ArrayList<>();
        adapter = new StaggeredAdapter(homestays, getApplicationContext());
        adapter.setUserAction(this);
        recyclerView.setLayoutManager(new LinearLayoutManagerWithSmoothScroller(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(itemDecorator);
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
    public void onActionViewDetailHsPriceByUser(int position) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 5000) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        Intent intent = HomeStayDetailActivity.newIntent(getApplicationContext());
        intent.putExtra("detailprice", homestays.get(position));
        startActivity(intent);
    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbarHomeStayPrice);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mToolbar.setNavigationOnClickListener(v -> onBackPressed());
    }
}
