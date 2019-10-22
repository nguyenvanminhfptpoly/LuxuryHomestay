package com.minhnv.luxuryhomestay.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.Booking;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {
    private List<Booking> bookings;
    private Context context;
    private RecyclerViewNavigator listener;

    public BookingAdapter(List<Booking> bookings, Context context, RecyclerViewNavigator listener) {
        this.bookings = bookings;
        this.context = context;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recyclerview_booking,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(bookings != null) {
            holder.bind(bookings.get(position));
            holder.constraintLayout.setOnClickListener(view -> listener.onItemClickListener(position));
        }
    }

    @Override
    public int getItemCount() {
        return bookings == null ? 0 : bookings.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
    TextView titleDateStart,titleDateEnd,titleCountMember,tvNameHs,tvAddressHs;
    ConstraintLayout constraintLayout;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleCountMember = itemView.findViewById(R.id.titleCountMember);
            titleDateEnd = itemView.findViewById(R.id.titleDateEnd);
            titleDateStart = itemView.findViewById(R.id.titleDateStart);
            tvAddressHs = itemView.findViewById(R.id.tvAddressHs);
            tvNameHs = itemView.findViewById(R.id.tvNameHs);
            constraintLayout = itemView.findViewById(R.id.constraintLayout_delete);
        }
        void bind(Booking booking){
            String dateStart = "Ngày đến: " + booking.getDatestart();
            String dateEnd = "Ngày đi: " + booking.getDateend();
            String countMember = "Số lượng người: " + booking.getCountmember();
            String nameHs = "Tên homestay: " + booking.getNameHomeStay();
            String addressHs = "Địa chỉ: " + booking.getAddress();
            titleDateStart.setText(dateStart);
            titleDateEnd.setText(dateEnd);
            titleCountMember.setText(countMember);
            tvAddressHs.setText(addressHs);
            tvNameHs.setText(nameHs);
        }

    }
}
