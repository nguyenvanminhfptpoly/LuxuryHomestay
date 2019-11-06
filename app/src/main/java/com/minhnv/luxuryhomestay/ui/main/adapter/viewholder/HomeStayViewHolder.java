package com.minhnv.luxuryhomestay.ui.main.adapter.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.widget.ANImageView;
import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.Homestay;

public class HomeStayViewHolder extends RecyclerView.ViewHolder {
    private ANImageView image;
    private TextView tvTitle;
    private TextView tvDetail;
    private Button btnListDetail, btnListBooking;
    private LinearLayout linearLayout;

    private HomeStayViewHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.image);
        tvTitle = itemView.findViewById(R.id.tvTitle);
        tvDetail = itemView.findViewById(R.id.tvDetail);
        btnListBooking = itemView.findViewById(R.id.btnListBooking);
        btnListDetail = itemView.findViewById(R.id.btnListDetail);
        linearLayout = itemView.findViewById(R.id.linear_layout);
    }

    public void bind(Homestay homestay, CityDetailViewHolder.UserActionListener callBack) {
        tvTitle.setText(homestay.getName());
        tvDetail.setText(homestay.getEvalute());
        image.setErrorImageResId(R.drawable.uploadfailed);
        image.setDefaultImageResId(R.drawable.img_home1);
        image.setImageUrl(homestay.getImage());
        btnListBooking.setOnClickListener(view -> callBack.onActionBookingByUser(getAdapterPosition()));
        btnListDetail.setOnClickListener(v -> callBack.onActionDetailByUser(getAdapterPosition()));
    }

    public static HomeStayViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_homestays_recyclerview,parent,false);
        return new HomeStayViewHolder(view);
    }


}
