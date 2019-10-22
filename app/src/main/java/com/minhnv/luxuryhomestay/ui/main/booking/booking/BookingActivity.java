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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.Homestay;
import com.minhnv.luxuryhomestay.data.model.HomestayPrice;
import com.minhnv.luxuryhomestay.ui.base.BaseActivity;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class BookingActivity extends BaseActivity<BookingViewModel> implements BookingNavigator {
    private static final String TAG = "BookingActivity";
    private Button btnDateStart;
    private Button btnDateEnd;
    private TextView tvDateStart, tvDateEnd,tvNameHomeStay,tvAddressHomeStay;
    private EditText edCountMember;
    private Integer day, mMonth, mYear;
    private ImageView imgBooking;
    private SlidrInterface slide;


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
        slide = Slidr.attach(this);
        bindViewModel();
        openCalendar();
        initIntent();
    }

    private void initIntent(){
        if(getIntent().getSerializableExtra("booking") != null) {
            Homestay homestay = (Homestay) getIntent().getSerializableExtra("booking");
            assert homestay != null;
            tvNameHomeStay.setText(homestay.getName());
            tvAddressHomeStay.setText(homestay.getAddress());
            Picasso.get().load(homestay.getImage()).error(R.drawable.uploadfailed).into(imgBooking);
        }else if(getIntent().getSerializableExtra("detailprice") != null){
            HomestayPrice price = (HomestayPrice) getIntent().getSerializableExtra("detailprice");
            assert price != null;
            tvNameHomeStay.setText(price.getTitle());
            tvAddressHomeStay.setText(price.getAddress());
            Picasso.get().load(price.getImage()).error(R.drawable.uploadfailed).into(imgBooking);
        }

    }

    private void bindViewModel() {
        btnDateStart = findViewById(R.id.btnDateStart);
        btnDateEnd = findViewById(R.id.btnDateEnd);
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
            viewmodel.ServerBooking();
        });

        tvNameHomeStay.setText(getString(R.string.select_name_hs));
        tvAddressHomeStay.setText(getString(R.string.select_address_hs));

    }

    private void openCalendar() {
        Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        mMonth = calendar.get(Calendar.MONTH);
        mYear = calendar.get(Calendar.YEAR);
        String dayStart = "" + mYear + "-" + (mMonth + 1) + "-" + day + "";
        tvDateStart.setText(dayStart);
        tvDateEnd.setText(dayStart);

        btnDateStart.setOnClickListener(view -> {
            DatePickerDialog dialog = new DatePickerDialog(this,
                    (datePicker, year, month, date) -> {
                        if (date < day || month < mMonth || year < mYear) {
                            Toast.makeText(this, getString(R.string.date_valid), Toast.LENGTH_SHORT).show();
                        }
                        String dateStart = "" + year + "-" + (month + 1) + "-" + date + "";
                        tvDateStart.setText(dateStart);
                    }, mYear, mMonth, day);
            dialog.show();
        });
        btnDateEnd.setOnClickListener(view -> {
            DatePickerDialog dialog = new DatePickerDialog(this,
                    (datePicker, year, month, date) -> {
                        if (date < day || month < mMonth || year < mYear) {
                            Toast.makeText(this, getString(R.string.date_valid), Toast.LENGTH_SHORT).show();
                        }
                        String dateEnd = "" + year + "-" + (month + 1) + "-" + date + "";
                        tvDateEnd.setText(dateEnd);
                    }, mYear, mMonth, day);
            dialog.show();
        });
    }


    @Override
    public void handleError(Throwable throwable) {
        if(!isNetworkConnected()){
            backToLogin();
        }
        Log.d(TAG, "handleError: " + throwable);
    }

    @Override
    public void onSuccess() {
        Log.d(TAG, "onUploadImageSuccess: ");
        Toast.makeText(this,   getString(R.string.booking_success), Toast.LENGTH_SHORT).show();
        hideLoading();
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



}


