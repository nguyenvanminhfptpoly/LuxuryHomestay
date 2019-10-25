package com.minhnv.luxuryhomestay.ui.main.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.InputFilter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.Homestay;
import com.minhnv.luxuryhomestay.ui.base.BaseActivity;
import com.minhnv.luxuryhomestay.ui.main.adapter.HomeStaysAdapter;
import com.minhnv.luxuryhomestay.ui.main.adapter.RecyclerViewNavigator;
import com.minhnv.luxuryhomestay.ui.main.booking.booking.BookingActivity;
import com.minhnv.luxuryhomestay.ui.main.homestay_detail.HomeStayDetailActivity;
import com.minhnv.luxuryhomestay.utils.AppLogger;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity<SearchViewModel> implements SearchNavigator {
    private static final String TAG = "SearchActivity";

    private EditText edRating;
    private HomeStaysAdapter adapter;
    private List<Homestay> homestays;
    private int rating;
    private SlidrInterface slide;
    int maxLength = 5;
    private ImageButton btnSearch;

    public static Intent newIntent(Context context) {
        return new Intent(context, SearchActivity.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void onCreateActivity(@Nullable Bundle savedInstanceState) {
        viewmodel = ViewModelProviders.of(this, factory).get(SearchViewModel.class);
        viewmodel.setNavigator(this);
        slide = Slidr.attach(this);
        initView();
        setUpRecyclerView();
    }
    private void initView(){
        btnSearch = findViewById(R.id.btnSearch);
        edRating = findViewById(R.id.includeSearch);
        edRating.setHint(R.string.hint_search);
        edRating.setFilters(new InputFilter[]{new InputFilterMinMax("1","5")});
    }
    private void setUpRecyclerView(){
        RecyclerView recyclerViewSearch = findViewById(R.id.recyclerViewSearch);
        homestays = new ArrayList<>();
        adapter = new HomeStaysAdapter(homestays, getApplicationContext(), new RecyclerViewNavigator() {
            @Override
            public void onItemClickListener(int position) {
                Intent intent = HomeStayDetailActivity.newIntent(getApplicationContext());
                intent.putExtra("detail",homestays.get(position));
                startActivity(intent);
            }

            @Override
            public void onItemClickDetailListener(int position) {
                Intent intent = BookingActivity.newIntent(getApplicationContext());
                intent.putExtra("booking",homestays.get(position));
                startActivity(intent);
            }

            @Override
            public void onItemSharing(int position) {

            }
        });
        recyclerViewSearch.setHasFixedSize(true);
        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewSearch.setAdapter(adapter);

        btnSearch.setOnClickListener(view -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 5000) {
                return ;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            viewmodel.ServerLoadList();
            homestays.clear();
            adapter.notifyDataSetChanged();
            hideKeyboard();
        });

    }

    @Override
    public void HandlerError(Throwable throwable) {
        if(!isNetworkConnected()){
            backToLogin();
        }
        AppLogger.d(TAG, "HandlerError: " + throwable);
    }

    @Override
    public void onSuccess() {
        AppLogger.d(TAG, "onUploadImageSuccess: ");
    }

    @Override
    public void loadList() {
        String ratingString = edRating.getText().toString().trim();
        if (viewmodel.isRequestValid(ratingString)) {
            int rating = Integer.parseInt(ratingString);
            viewmodel.search(rating);
            showLoading();
            compositeDisposable.add(viewmodel.listPublishSubject.share()
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .subscribe(response -> {
                        homestays.addAll(response);
                        adapter.notifyDataSetChanged();
                        hideLoading();
                    }, throwable ->
                            AppLogger.d(TAG, "loadList: " + throwable)
                    ));
        } else {
            Toast.makeText(this, getString(R.string.validate), Toast.LENGTH_SHORT).show();
        }

    }
}
