package com.minhnv.luxuryhomestay.ui.main.booking.booking;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.InputType;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.androidnetworking.widget.ANImageView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.ui.base.BaseActivity;
import com.minhnv.luxuryhomestay.utils.AppLogger;
import com.minhnv.luxuryhomestay.utils.CustomToast;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

public class BookingActivity extends BaseActivity<BookingViewModel> implements BookingNavigator {
    private static final String TAG = "BookingActivity";
    private TextView tvDateStart, tvDateEnd, tvNameHomeStay, tvAddressHomeStay;
    private EditText edCountMember;

    private ANImageView imgBooking;


    public static Intent newIntent(Context context) {
        return new Intent(context, BookingActivity.class);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkThemes);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_fragment);
        viewmodel = ViewModelProviders.of(this, factory).get(BookingViewModel.class);
        viewmodel.setNavigator(this);
        SlidrInterface slide = Slidr.attach(this);
        bindViewModel();
        initIntent();
    }

    private void initIntent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            String name = bundle.getString("bookingName");
            String address = bundle.getString("bookingAddress");
            String image = bundle.getString("image");
            tvNameHomeStay.setText(name);
            tvAddressHomeStay.setText(address);
            imgBooking.setImageUrl(image);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
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
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.bounce);

        FloatingActionButton floatingActionButton = findViewById(R.id.fabCall);
        floatingActionButton.startAnimation(animation);
        animation.setRepeatCount(Animation.INFINITE);
        floatingActionButton.setOnClickListener(view -> {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:0342460704"));
            if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
            startActivity(callIntent);
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
        CustomToast.makeTake(this,getString(R.string.booking_success),Toast.LENGTH_LONG,CustomToast.SUCCESS).show();
        hideLoading();
    }

    @Override
    public void onFailed() {
        hideLoading();
        CustomToast.makeTake(this,getString(R.string.booking_failed),Toast.LENGTH_LONG,CustomToast.ERROR).show();
    }

    @Override
    public void doServerBooking() {
        String dateStart = tvDateStart.getText().toString().trim();
        String dateEnd = tvDateEnd.getText().toString().trim();
        String countMember = edCountMember.getText().toString().trim();
        String nameHomeStay = tvNameHomeStay.getText().toString();
        String address = tvAddressHomeStay.getText().toString();
        try {
            int idUser = Integer.parseInt(appPreferenceHelper.getCurrentId());
            if (viewmodel.isRequestValid(dateStart, dateEnd, countMember,nameHomeStay,address) && isNetworkConnected()) {
                viewmodel.booking(dateStart, dateEnd, countMember,nameHomeStay,address,idUser);
                showLoading();
            } else {
                CustomToast.makeTake(this,getString(R.string.booking_error),Toast.LENGTH_LONG,CustomToast.ERROR).show();
            }
        }catch (NumberFormatException e){
            e.getMessage();
        }


    }

    @Override
    public void triggerCheckIn(int mYear, int mMonth, int mDay) {
        DatePickerDialog dialog = new DatePickerDialog(this,
                (datePicker, year, month, date) -> {
                    if (date < mDay || month < mMonth || year < mYear) {
                        CustomToast.makeTake(this,getString(R.string.date_valid),Toast.LENGTH_LONG,CustomToast.ERROR).show();
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
                        CustomToast.makeTake(this,getString(R.string.date_valid),Toast.LENGTH_LONG,CustomToast.ERROR).show();
                    }
                    String dateEnd = "" + year + "-" + (month + 1) + "-" + date + "";
                    tvDateEnd.setText(dateEnd);
                }, mYear, mMonth, mDay);
        dialog.show();
    }


}


