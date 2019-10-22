package com.minhnv.luxuryhomestay.ui.main.homestay_detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.Homestay;
import com.minhnv.luxuryhomestay.data.model.HomestayPrice;
import com.minhnv.luxuryhomestay.data.model.Luxury;
import com.minhnv.luxuryhomestay.ui.base.BaseActivity;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class HomeStayDetailActivity extends BaseActivity<HomeStaysDetailViewModel> implements HomeStayDetailNavigator {
    private static final String TAG = "HomeStayDetailActivity";
    private TextView tvName, tvAddress, tvHasTag, tvPrice, tvRating, tvEvaLute;
    private ReadMoreTextView tvDetail;
    private ImageView imgDetail;
    private String nameImage;
    private SlidrInterface slide;

    public static Intent newIntent(Context context) {
        return new Intent(context, HomeStayDetailActivity.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home_stay_detail;
    }

    @Override
    public void onCreateActivity(@Nullable Bundle savedInstanceState) {
        viewmodel = ViewModelProviders.of(this, factory).get(HomeStaysDetailViewModel.class);
        viewmodel.setNavigator(this);
        slide = Slidr.attach(this);
        tvName = findViewById(R.id.tvNameHomeStaysDetail);
        tvAddress = findViewById(R.id.tvAddressHomeStayDetail);
        tvPrice = findViewById(R.id.tvPriceHomeStayDetail);
        tvHasTag = findViewById(R.id.tvHastagHomeStayDetail);
        tvDetail = findViewById(R.id.tvDetailHomeStay);
        tvRating = findViewById(R.id.tvRatingHomeStayDetail);
        imgDetail = findViewById(R.id.imgHomeStayDetail);
        tvEvaLute = findViewById(R.id.tvEvaluteHomeStayDetail);
        Toolbar toolbar = findViewById(R.id.toolbarHomeStaysDetail);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Chi tiết Homestay");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(view -> {
            onBackPressed();
        });
        initIntent();
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
            if(!isNetworkConnected()){
                backToLogin();
            }else {
                viewmodel.ServerPostFavorite();
                Toast.makeText(this, "Thêm Yêu Thích", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void initIntent() {
        if (getIntent().getSerializableExtra("detail") != null) {
            Homestay homestay = (Homestay) getIntent().getSerializableExtra("detail");
            assert homestay != null;
            Picasso.get().load(homestay.getImage()).error(R.drawable.uploadfailed).into(imgDetail);

            String name = getString(R.string.name_hs) + homestay.getName();
            String rating = getString(R.string.rating) + homestay.getRating();
            String detail = getString(R.string.detail_hs) + homestay.getDetail();
            String hasTag = getString(R.string.has_tag_home_stay) + homestay.getHastag();
            String address = getString(R.string.addressHs) + homestay.getAddress();
            nameImage = homestay.getImage();
            tvName.setText(name);
            tvRating.setText(rating);
            tvDetail.setText(detail);
            tvHasTag.setText(hasTag);

            tvAddress.setText(address);

            Double price = homestay.getPrice();
            DecimalFormat format = new DecimalFormat("#,### VND/đêm");
            String credit = getString(R.string.price) + format.format(price);
            tvPrice.setText(credit);
            tvEvaLute.setText(homestay.getEvalute());
        } else if (getIntent().getSerializableExtra("detailprice") != null) {
            HomestayPrice price = (HomestayPrice) getIntent().getSerializableExtra("detailprice");
            assert price != null;
            Picasso.get().load(price.getImage()).error(R.drawable.uploadfailed).into(imgDetail);
            String name = getString(R.string.name_hs) + price.getTitle();
            String rating = getString(R.string.rating) + price.getRating();
            String detail = "Giá cũ: " + price.getPriceago();
            String hasTag = getString(R.string.has_tag_home_stay) + price.getHastag();
            String address = getString(R.string.addressHs) + price.getAddress();
            nameImage = price.getImage();
            tvName.setText(name);
            tvRating.setText(rating);
            tvDetail.setText(detail);
            tvHasTag.setText(hasTag);
            tvAddress.setText(address);
            tvEvaLute.setText("");

            Double price2 = price.getPrice();
            DecimalFormat format = new DecimalFormat("#,### VND/đêm");
            String credit = getString(R.string.price) + format.format(price2);
            tvPrice.setText(credit);
        }else if(getIntent().getSerializableExtra("luxury_detail") != null){
            Luxury luxury = (Luxury) getIntent().getSerializableExtra("luxury_detail");
            assert luxury != null;
            Picasso.get().load(luxury.getImage()).error(R.drawable.uploadfailed).into(imgDetail);
            tvHasTag.setText(luxury.getUsername());
            tvDetail.setText(luxury.getDetail());
            tvPrice.setText("");
            tvRating.setText("");
            tvName.setText("");
            tvEvaLute.setText("");
            tvAddress.setText("");
        }

    }

    @Override
    public void HandlerError(Throwable throwable) {

        Log.d(TAG, "HandlerError: "+throwable);
    }

    @Override
    public void onSuccess() {
        Log.d(TAG, "onSuccess: ");
    }

    @Override
    public void addFavorite() {
        String name = tvName.getText().toString();
        String address = tvAddress.getText().toString();
        String image = nameImage;
        viewmodel.addFavorite(image,name,address);
    }
}
