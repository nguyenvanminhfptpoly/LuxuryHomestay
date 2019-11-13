package com.minhnv.luxuryhomestay.ui.main.adapter.viewholder;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.widget.ANImageView;
import com.google.android.material.card.MaterialCardView;
import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.Homestay;

public class CityDetailViewHolder extends RecyclerView.ViewHolder {
    private ANImageView image;
    private TextView tvTitle;
    private TextView tvDetail;
    private MaterialCardView cardView;
    public long mLastClickTime = 0;

    private CityDetailViewHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.imgCityDetail);
        tvTitle = itemView.findViewById(R.id.tvTitleCityDetail);
        tvDetail = itemView.findViewById(R.id.tvDetailCity);
        cardView = itemView.findViewById(R.id.itemTouchCity);
    }

    public void bind(Homestay homestay, UserActionListener callBack) {
        tvTitle.setText(homestay.getName());
        tvDetail.setText(homestay.getEvalute());
        image.setErrorImageResId(R.drawable.uploadfailed);
        image.setDefaultImageResId(R.drawable.img_home1);
        image.setImageUrl(homestay.getImage());
        cardView.setOnClickListener(view -> {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 5000) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            callBack.onActionDetailByUser(getAdapterPosition());});
    }

    public static CityDetailViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city_detail_recyclerview,parent,false);
        return new CityDetailViewHolder(view);
    }

    public interface UserActionListener {
        void onActionDetailByUser(int position);
    }
}
