package com.minhnv.luxuryhomestay.ui.main.booking.booking;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.InputType;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.androidnetworking.widget.ANImageView;
import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.Homestay;
import com.minhnv.luxuryhomestay.data.model.HomestayPrice;
import com.minhnv.luxuryhomestay.data.model.ListVinHomes;
import com.minhnv.luxuryhomestay.ui.base.BaseActivity;
import com.minhnv.luxuryhomestay.utils.AppLogger;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

import java.util.Calendar;

public class BookingActivity extends BaseActivity<BookingViewModel> implements BookingNavigator {
    private static final String TAG = "BookingActivity";
    private TextView tvDateStart, tvDateEnd,tvNameHomeStay,tvAddressHomeStay;
    private EditText edCountMember;

    private ANImageView imgBooking;


    public static Intent newIntent(Context context) {
        return new Intent(context, BookingActivity.class);

    }

    @Override
    public int getLayoutId() {
        return R.layout.booking_fragment;
    }

    @Override
    public void onCreateActivity(@Nullable Bundle savedInstanceState) {
        viewmodel = ViewModelProviders.of(this, factory).get(BookingViewModel.class);
        viewmodel.setNavigator(this);
        SlidrInterface slide = Slidr.attach(this);
        bindViewModel();
        initIntent();

    }

    private void initIntent(){
        if(getIntent().getSerializableExtra("booking") != null) {
            Homestay homestay = (Homestay) getIntent().getSerializableExtra("booking");
            assert homestay != null;
            tvNameHomeStay.setText(homestay.getName());
            tvAddressHomeStay.setText(homestay.getAddress());
            imgBooking.setImageUrl(homestay.getImage());
        }else if(getIntent().getSerializableExtra("detailprice") != null){
            HomestayPrice price = (HomestayPrice) getIntent().getSerializableExtra("detailprice");
            assert price != null;
            tvNameHomeStay.setText(price.getTitle());
            tvAddressHomeStay.setText(price.getAddress());
            imgBooking.setImageUrl(price.getImage());
        }else if(getIntent().getSerializableExtra("vinHomes") != null){
            ListVinHomes homes = (ListVinHomes) getIntent().getSerializableExtra("vinHomes");
            assert homes != null;
            tvNameHomeStay.setText(homes.getName());
            tvAddressHomeStay.setText(homes.getAddress());
            imgBooking.setImageUrl(homes.getImage());

        }

    }

    private void bindViewModel() {
        Button btnDateStart = findViewById(R.id.btnDateStart);
        Button btnDateEnd = findViewById(R.id.btnDateEnd);
        Button btnBooking = findViewById(R.id.btnBookingTrip);
        tvDateEnd = findViewById(R.id.tvDateEnd);
        tvDateStart = findViewById(R.id.tvDateStart);
        edCountMember = findViewById(R.id.includeCountMember).findViewById(R.id.edValue);
        imgBooking = findViewById(R.id.imgBooking);

        edCountMember.setInputType(InputType.TYPE_CLASS_NUMBER);

        tvAddressHomeStay = findViewById(R.id.tvAddressHomeStay);
        tvNameHomeStay = findViewById(R.id.tvNameHomeStay);

        Toolbar toolbar = findViewById(R.id.toolbarBooking);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setTitle(getString(R.string.booking));
        toolbar.setNavigationOnClickListener(view -> {
            onBackPressed();
        });


        btnBooking.setOnClickListener(view -> {
            if(SystemClock.elapsedRealtime() - mLastClickTime < 5000){
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            viewmodel.ServerBooking();
        });

        tvNameHomeStay.setText(getString(R.string.select_name_hs));
        tvAddressHomeStay.setText(getString(R.string.select_address_hs));
        btnDateStart.setOnClickListener(view -> {viewmodel.didSelectCheckIn();});
        btnDateEnd.setOnClickListener(view -> {viewmodel.didSelectCheckOut();});

        imgBooking.setErrorImageResId(R.drawable.uploadfailed);
        imgBooking.setDefaultImageResId(R.drawable.img_home1);
    }



    @Override
    public void handleError(Throwable throwable) {
        if(!isNetworkConnected()){
            backToLogin();
        }
        AppLogger.d(TAG,throwable);
    }

    @Override
    public void onSuccess() {
        AppLogger.d(TAG, "onUploadImageSuccess: ");
        Toast.makeText(this,   getString(R.string.booking_success), Toast.LENGTH_SHORT).show();
        hideLoading();
    }

    @Override
    public void onFailed() {
        hideLoading();
        Toast.makeText(this, getString(R.string.booking_failed), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void doServerBooking() {
        String dateStart = tvDateStart.getText().toString().trim();
        String dateEnd = tvDateEnd.getText().toString().trim();
        String countMember = edCountMember.getText().toString().trim();
        String nameHomeStay = tvNameHomeStay.getText().toString();
        String address = tvAddressHomeStay.getText().toString();

        if (viewmodel.isRequestValid(dateStart, dateEnd, countMember,nameHomeStay,address) && isNetworkConnected()) {
            viewmodel.booking(dateStart, dateEnd, countMember,nameHomeStay,address);
            showLoading();
        } else {
            Toast.makeText(this, getString(R.string.booking_error), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void triggerCheckIn(int mYear, int mMonth, int mDay) {
        DatePickerDialog dialog = new DatePickerDialog(this,
                (datePicker, year, month, date) -> {
                    if (date < mDay || month < mMonth || year < mYear) {
                        Toast.makeText(this, getString(R.string.date_valid), Toast.LENGTH_SHORT).show();
                    }
                    String dateEnd = "" + year + "-" + (month + 1) + "-" + date + "";
                    tvDateStart.setText(dateEnd);
                }, mYear, mMonth, mDay);
        dialog.show();
    }

    @Override
    public void triggerCheckOut(int mYear, int mMonth, int mDay) {
        DatePickerDialog dialog = new DatePickerDialog(this,
                (datePicker, year, month, date) -> {
                    if (date < mDay || month < mMonth || year < mYear) {
                        Toast.makeText(this, getString(R.string.date_valid), Toast.LENGTH_SHORT).show();
                    }
                    String dateEnd = "" + year + "-" + (month + 1) + "-" + date + "";
                    tvDateEnd.setText(dateEnd);
                }, mYear, mMonth, mDay);
        dialog.show();
    }


}


