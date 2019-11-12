package com.minhnv.luxuryhomestay.ui.main.homestay_detail;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.widget.ANImageView;
import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.Homestay;
import com.minhnv.luxuryhomestay.data.model.HomestayPrice;
import com.minhnv.luxuryhomestay.data.model.ImageDetail;
import com.minhnv.luxuryhomestay.data.model.ListVinHomes;
import com.minhnv.luxuryhomestay.data.model.Luxury;
import com.minhnv.luxuryhomestay.ui.base.BaseActivity;
import com.minhnv.luxuryhomestay.ui.main.adapter.ImageDetailAdapter;
import com.minhnv.luxuryhomestay.ui.main.adapter.LinearLayoutManagerWithSmoothScroller;
import com.minhnv.luxuryhomestay.ui.main.adapter.SnapHelperOneByOne;
import com.minhnv.luxuryhomestay.ui.main.booking.booking.BookingActivity;
import com.minhnv.luxuryhomestay.utils.AppLogger;
import com.minhnv.luxuryhomestay.utils.CommonUtils;
import com.minhnv.luxuryhomestay.utils.CustomToast;
import com.r0adkll.slidr.model.SlidrInterface;

import java.util.ArrayList;
import java.util.List;

public class HomeStayDetailActivity extends BaseActivity<HomeStaysDetailViewModel> implements HomeStayDetailNavigator {
    private static final String TAG = "HomeStayDetailActivity";
    private TextView tvName, tvAddress, tvHasTag, tvPrice, tvEvaLute;
    private ReadMoreTextView tvDetail;
    private ANImageView imgDetail;
    private String nameImage;
    private SlidrInterface slide;
    private Button viewRating,btnBooking;private Animator curentAnimator;
    private int shortAnimationDuration;
    private List<ImageDetail> imageDetails;
    private ImageDetailAdapter adapter;
    private int idHomeStay;
    private SnapHelperOneByOne helperOneByOne;


    public static Intent newIntent(Context context) {
        return new Intent(context, HomeStayDetailActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkThemes);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_stay_detail);
        viewmodel = ViewModelProviders.of(this, factory).get(HomeStaysDetailViewModel.class);
        viewmodel.setNavigator(this);

        initView();
    }

    private void initView(){
        helperOneByOne = new SnapHelperOneByOne();
        tvName = findViewById(R.id.tvNameHomeStaysDetail);
        tvAddress = findViewById(R.id.tvAddressHomeStayDetail);
        tvPrice = findViewById(R.id.tvPriceHomeStayDetail);
        tvHasTag = findViewById(R.id.tvHastagHomeStayDetail);
        tvDetail = findViewById(R.id.tvDetailHomeStay);
        viewRating = findViewById(R.id.viewRating);
        imgDetail = findViewById(R.id.imgHomeStayDetail);
        tvEvaLute = findViewById(R.id.tvEvaluteHomeStayDetail);
        btnBooking = findViewById(R.id.btbBookingHomeStay);
        Toolbar toolbar = findViewById(R.id.toolbarHomeStaysDetail);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Chi tiết Homestay");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(view ->
                onBackPressed()
        );
        imgDetail.setDefaultImageResId(R.drawable.img_home1);
        imgDetail.setErrorImageResId(R.drawable.uploadfailed);
        String name = tvName.getText().toString();
        String address = tvAddress.getText().toString();

        btnBooking.setOnClickListener(v -> {
            Intent intent = BookingActivity.newIntent(getApplicationContext());
            intent.putExtra("bookingName", name );
            intent.putExtra("bookingAddress",address);
            startActivity(intent);
        });
        initIntent();
        imageDetails = new ArrayList<>();
        viewmodel.ServerLoadImageDetail();
        RecyclerView recyclerView = findViewById(R.id.recyclerViewListImage);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManagerWithSmoothScroller(this, LinearLayoutManager.HORIZONTAL,false));
        adapter = new ImageDetailAdapter(imageDetails,getApplicationContext());
        recyclerView.setAdapter(adapter);
        helperOneByOne.attachToRecyclerView(recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_favorite, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.ic_favorite) {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 5000) {
                return false;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            if (!isNetworkConnected()) {
                backToLogin();
            } else {
                viewmodel.ServerPostFavorite();

            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void initIntent() {
        if (getIntent().getSerializableExtra("detail") != null) {
            Homestay homestay = (Homestay) getIntent().getSerializableExtra("detail");
            assert homestay != null;
            imgDetail.setImageUrl(homestay.getImage());

            String name = getString(R.string.name_hs) + homestay.getName();
            String rating = homestay.getRating();
            String detail = getString(R.string.detail_hs) + homestay.getDetail();
            String hasTag = getString(R.string.has_tag_home_stay) + homestay.getHastag();
            String address = getString(R.string.addressHs) + homestay.getAddress();
            idHomeStay = Integer.parseInt(homestay.getId());
            nameImage = homestay.getImage();
            tvName.setText(name);
            viewRating.setText(rating);
            tvDetail.setText(detail);
            tvHasTag.setText(hasTag);
            tvAddress.setText(address);
            tvPrice.setText(CommonUtils.FormatCredits(homestay.getPrice()));
            tvEvaLute.setText(homestay.getEvalute());
        } else if (getIntent().getSerializableExtra("detailprice") != null) {
            HomestayPrice price = (HomestayPrice) getIntent().getSerializableExtra("detailprice");
            assert price != null;
            imgDetail.setImageUrl(price.getImage());
            String name = getString(R.string.name_hs) + price.getTitle();
            String rating = price.getRating();
            String hasTag = getString(R.string.has_tag_home_stay) + price.getHastag();
            String address = getString(R.string.addressHs) + price.getAddress();
            idHomeStay = Integer.parseInt(price.getId());
            nameImage = price.getImage();
            tvName.setText(name);
            viewRating.setText(rating);
            tvDetail.setText(CommonUtils.FormatCredits(price.getPriceago()));
            tvDetail.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            tvHasTag.setText(hasTag);
            tvAddress.setText(address);
            tvEvaLute.setText("");
            tvPrice.setText(CommonUtils.FormatCredits(price.getPrice()));
        } else if (getIntent().getSerializableExtra("luxury_detail") != null) {
            Luxury luxury = (Luxury) getIntent().getSerializableExtra("luxury_detail");
            assert luxury != null;
            idHomeStay = Integer.parseInt(luxury.getId());
            nameImage = luxury.getImage();
            imgDetail.setImageUrl(luxury.getImage());
            tvName.setText(luxury.getUsername());
            tvDetail.setText(luxury.getDetail());
            tvPrice.setVisibility(View.GONE);
            viewRating.setVisibility(View.GONE);
            tvHasTag.setVisibility(View.GONE);
            tvEvaLute.setVisibility(View.GONE);
            tvAddress.setVisibility(View.GONE);
            btnBooking.setVisibility(View.GONE);
        } else if(getIntent().getSerializableExtra("vinHomes") != null){
            ListVinHomes homes = (ListVinHomes) getIntent().getSerializableExtra("vinHomes");
            assert homes != null;
            idHomeStay = Integer.parseInt(homes.getId());
            nameImage = homes.getImage();
            imgDetail.setImageUrl(homes.getImage());
            tvName.setText(homes.getName());
            tvDetail.setText(homes.getDetail());
            viewRating.setText(homes.getRating());
            tvHasTag.setVisibility(View.GONE);
            tvEvaLute.setText(homes.getCreateroom());
            tvAddress.setText(homes.getAddress());

            tvPrice.setText(CommonUtils.FormatCredits(homes.getPrice()));
        }

    }

    @Override
    public void HandlerError(Throwable throwable) {
        AppLogger.d(TAG, "HandlerError: " + throwable);
    }

    @Override
    public void onSuccess() {
        CustomToast.makeTake(this,getString(R.string.add_favorite_success),Toast.LENGTH_LONG,CustomToast.SUCCESS).show();
        AppLogger.d(TAG, "onSuccess: ");
    }

    @Override
    public void onFailed() {
        hideLoading();
        CustomToast.makeTake(this,getString(R.string.add_favorite_failed),Toast.LENGTH_LONG,CustomToast.ERROR).show();
    }

    @Override
    public void addFavorite() {
        String name = tvName.getText().toString();
        String address = tvAddress.getText().toString();
        String image = nameImage;
        viewmodel.addFavorite(image, name, address);
    }

    @Override
    public void loadImageDetail() {
        viewmodel.loadList(idHomeStay);
        compositeDisposable.add(viewmodel.listPublishSubject.share()
            .subscribe(data -> adapter.set(data)));
    }

}
