package com.minhnv.luxuryhomestay.ui.main.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.Homestay;
import com.minhnv.luxuryhomestay.ui.base.BaseActivity;
import com.minhnv.luxuryhomestay.ui.main.adapter.CityDetailAdapter;
import com.minhnv.luxuryhomestay.ui.main.adapter.viewholder.CityDetailViewHolder;
import com.minhnv.luxuryhomestay.ui.main.homestay_detail.HomeStayDetailActivity;
import com.minhnv.luxuryhomestay.utils.AppLogger;
import com.minhnv.luxuryhomestay.utils.CommonUtils;
import com.minhnv.luxuryhomestay.utils.CustomToast;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity<SearchViewModel> implements SearchNavigator, CityDetailViewHolder.UserActionListener {
    private static final String TAG = "SearchActivity";

    private EditText edRating;
    private CityDetailAdapter adapter;
    private List<Homestay> homestays;
    private int rating;
    private SlidrInterface slide;
    int maxLength = 5;
    private ImageButton btnSearch;
    private Spinner spinnerRating;
    private Spinner spinnerPrice;

    public static Intent newIntent(Context context) {
        return new Intent(context, SearchActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkThemes);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        viewmodel = ViewModelProviders.of(this, factory).get(SearchViewModel.class);
        viewmodel.setNavigator(this);
        slide = Slidr.attach(this);
        initView();
        setUpRecyclerView();
    }

    private void initView() {
        btnSearch = findViewById(R.id.btnSearch);
        edRating = findViewById(R.id.includeSearch);
        spinnerRating = (Spinner) findViewById(R.id.spinnerRating);
        spinnerPrice = (Spinner) findViewById(R.id.spinnerPrice);
        edRating.setHint(R.string.hint_search);
        ArrayAdapter<CharSequence> price = ArrayAdapter.createFromResource(this,
                R.array.price, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> rating = ArrayAdapter.createFromResource(this,
                R.array.rating, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        price.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rating.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPrice.setAdapter(price);
        spinnerRating.setAdapter(rating);
    }


    private void setUpRecyclerView() {
        RecyclerView recyclerViewSearch = findViewById(R.id.recyclerViewSearch);
        homestays = new ArrayList<>();
        adapter = new CityDetailAdapter(homestays, getApplicationContext());
        adapter.setUserAction(this);
        recyclerViewSearch.setHasFixedSize(true);
        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewSearch.setAdapter(adapter);

        btnSearch.setOnClickListener(view -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 5000) {
                return;
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
        if (!isNetworkConnected()) {
            backToLogin();
        }
        AppLogger.d(TAG, "HandlerError: " + throwable);
    }

    @Override
    public void onSuccess() {
        hideLoading();
        AppLogger.d(TAG, "onUploadImageSuccess: ");
    }

    @Override
    public void loadList() {
        String ratingNumber = CommonUtils.stripNonDigits(spinnerRating.getSelectedItem().toString());
        String price = CommonUtils.stripNonDigits(spinnerPrice.getSelectedItem().toString());
        String ratingString = edRating.getText().toString().trim();
        if (viewmodel.isRequestValid(ratingString)) {
            viewmodel.search(ratingString, ratingNumber, price);
            showLoading();
            compositeDisposable.add(viewmodel.listPublishSubject.share()
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .subscribe(response ->
                            adapter.set(response)
                    ));
        } else {
            CustomToast.makeText(this, getString(R.string.validate), Toast.LENGTH_LONG, CustomToast.ERROR).show();
        }

    }


    @Override
    public void onActionDetailByUser(int position) {
        Intent intent = HomeStayDetailActivity.newIntent(getApplicationContext());
        intent.putExtra("detail", homestays.get(position));
        startActivity(intent);
    }

}
