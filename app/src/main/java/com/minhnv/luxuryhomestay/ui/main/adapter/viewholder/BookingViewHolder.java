package com.minhnv.luxuryhomestay.ui.main.adapter.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.Booking;

public class BookingViewHolder extends RecyclerView.ViewHolder {
    private TextView titleDateStart, titleDateEnd, titleCountMember, tvNameHs, tvAddressHs;
    private ConstraintLayout constraintLayout;
    private UserActionListener callBack;

    private BookingViewHolder(@NonNull View itemView) {
        super(itemView);
        titleCountMember = itemView.findViewById(R.id.titleCountMember);
        titleDateEnd = itemView.findViewById(R.id.titleDateEnd);
        titleDateStart = itemView.findViewById(R.id.titleDateStart);
        tvAddressHs = itemView.findViewById(R.id.tvAddressHs);
        tvNameHs = itemView.findViewById(R.id.tvNameHs);
        constraintLayout = itemView.findViewById(R.id.constraintLayout_delete);
    }
    public void bind(Booking booking, UserActionListener callBack) {
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
        constraintLayout.setOnClickListener(view ->
            callBack.onActionSelectedByUser(getAdapterPosition())
        );
    }

    public static BookingViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_booking,parent,false);
        return new BookingViewHolder(view);
    }

    public interface UserActionListener {
        void onActionSelectedByUser(int position);
    }

}
