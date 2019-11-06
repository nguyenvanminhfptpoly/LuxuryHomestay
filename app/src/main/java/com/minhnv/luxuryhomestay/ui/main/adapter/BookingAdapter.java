package com.minhnv.luxuryhomestay.ui.main.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minhnv.luxuryhomestay.data.model.Booking;
import com.minhnv.luxuryhomestay.ui.main.adapter.viewholder.BookingViewHolder;

import java.util.List;

import javax.inject.Inject;

public class BookingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Booking> bookings;
    private Context context;
    private BookingViewHolder.UserActionListener callBack;

    public BookingAdapter(List<Booking> bookings, Context context) {
        this.bookings = bookings;
        this.context = context;
    }


    @Inject
    public BookingAdapter() {
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return BookingViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof BookingViewHolder){
            ((BookingViewHolder) holder).bind(bookings.get(position),callBack);
        }
    }

    @Override
    public int getItemCount() {
        return bookings == null ? 0 : bookings.size();
    }

    public void set(List<Booking> list) {
        bookings.clear();
        bookings.addAll(list);
        notifyDataSetChanged();
    }

    public void setUserAction(BookingViewHolder.UserActionListener callBack){
        this.callBack = callBack;
    }


}
