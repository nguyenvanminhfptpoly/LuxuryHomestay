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

public class CityDetailViewHolder extends RecyclerView.ViewHolder {
    private ANImageView image;
    private TextView tvTitle;
    private TextView tvDetail;
    private Button btnListDetail, btnListBooking;
    private LinearLayout linearLayout;

    private CityDetailViewHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.imgCityDetail);
        tvTitle = itemView.findViewById(R.id.tvTitleCityDetail);
        tvDetail = itemView.findViewById(R.id.tvDetailCity);
        btnListBooking = itemView.findViewById(R.id.btnListCityBooking);
        btnListDetail = itemView.findViewById(R.id.btnListCityDetail);
    }

    public void bind(Homestay homestay,CallBack callBack) {
        tvTitle.setText(homestay.getName());
        tvDetail.setText(homestay.getEvalute());
        image.setErrorImageResId(R.drawable.uploadfailed);
        image.setDefaultImageResId(R.drawable.img_home1);
        image.setImageUrl(homestay.getImage());
        btnListBooking.setOnClickListener(view -> callBack.openBooking(getAdapterPosition()));
        btnListDetail.setOnClickListener(view -> callBack.openDetail(getAdapterPosition()));
    }

    public static CityDetailViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city_detail_recyclerview,parent,false);
        return new CityDetailViewHolder(view);
    }

    public interface CallBack{
        void openDetail(int position);
        void openBooking(int position);
    }
}
