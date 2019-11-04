package com.minhnv.luxuryhomestay.ui.main.booking.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.Booking;
import com.minhnv.luxuryhomestay.ui.base.BaseActivity;
import com.minhnv.luxuryhomestay.ui.main.adapter.BookingAdapter;
import com.minhnv.luxuryhomestay.ui.main.adapter.RecyclerViewNavigator;
import com.minhnv.luxuryhomestay.utils.AppLogger;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class ListBookingActivity extends BaseActivity<ListBookingViewModel> implements ListBookingNavigator {

    private static final String TAG = "ListBookingActivity";
    private RecyclerView recyclerView;
    private List<Booking> bookings;
    @Inject
    public BookingAdapter adapter;
    private SlidrInterface slide;

    public static Intent newIntent(Context context) {
        return new Intent(context, ListBookingActivity.class);

    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_list_booking;
    }

    @Override
    public void onCreateActivity(@Nullable Bundle savedInstanceState) {
        viewmodel = ViewModelProviders.of(this, factory).get(ListBookingViewModel.class);
        viewmodel.setNavigator(this);
        slide = Slidr.attach(this);
        bindViewModel();
    }

    private void bindViewModel() {
        Toolbar toolbar = findViewById(R.id.toolbarListBooking);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setTitle(getString(R.string.list_booking));
        toolbar.setNavigationOnClickListener(view -> {
            onBackPressed();
        });
        viewmodel.ServerLoadList();
        fetchData();
        recyclerView = findViewById(R.id.recyclerViewBooking);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        bookings = new ArrayList<>();
        adapter = new BookingAdapter(bookings, getApplicationContext(), new RecyclerViewNavigator() {
            @Override
            public void onItemClickListener(int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ListBookingActivity.this);
                builder.setTitle("Xóa lịch đặt phòng này");
                builder.setMessage("Bạn có muốn xóa không?");
                builder.setCancelable(false);
                Booking booking = bookings.get(position);
                int id = Integer.parseInt(booking.getId());
                builder.setPositiveButton("Đồng ý", (dialogInterface, i) -> {
                    viewmodel.ServerDeleteBooking(id);
                    showLoading();
                    bookings.clear();
                    viewmodel.ServerLoadList();
                    dialogInterface.dismiss();
                });
                builder.setNegativeButton("Không", (dialogInterface, i) -> dialogInterface.dismiss());
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }

            @Override
            public void onItemClickDetailListener(int position) {

            }

            @Override
            public void onItemSharing(int position) {

            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void fetchData(){
        compositeDisposable.add(viewmodel.listPublishSubject.share()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(data -> {
                    Collections.reverse(bookings);
                    adapter.set(data);}));
    }

    @Override
    public void handleError(Throwable throwable) {
        if(!isNetworkConnected()){
            backToLogin();
        }
        AppLogger.d(TAG, "handleError: " + throwable);
    }

    @Override
    public void onSuccess() {
        adapter.notifyDataSetChanged();
        AppLogger.d(TAG, "onUploadImageSuccess: ");
    }

    @Override
    public void onFailed() {
        hideLoading();
        Toast.makeText(this, getString(R.string.delete_error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteSuccess() {
        hideLoading();
        AppLogger.d(TAG, "onDeleteSuccess: ");
    }

    @Override
    public void ServerLoadList() {
        viewmodel.loadList();
    }

    @Override
    public void ServerDeleteBooking(int id) {
        viewmodel.deleteBooking(id);
    }

}
