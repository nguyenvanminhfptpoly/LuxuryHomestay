package com.minhnv.luxuryhomestay.ui.main.adapter.viewholder;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.BitmapRequestListener;
import com.androidnetworking.widget.ANImageView;
import com.google.android.material.card.MaterialCardView;
import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.Luxury;
import com.minhnv.luxuryhomestay.utils.AppLogger;
import com.rx2androidnetworking.Rx2AndroidNetworking;

public class LuxuryViewHolder extends RecyclerView.ViewHolder {
    private TextView tvName, tvAddress;
    private TextView tvDetail,tvCountLove;
    private ImageView imgLuxury;
    private ImageButton imgShare;
    private MaterialCardView cardView;


    private LuxuryViewHolder(@NonNull View itemView) {
        super(itemView);
        tvName = itemView.findViewById(R.id.tvNameUser);
        tvAddress = itemView.findViewById(R.id.tvAddressUser);
        tvDetail = itemView.findViewById(R.id.tvDetailLuxury);
        imgLuxury = itemView.findViewById(R.id.imgLuxury);
        imgShare = itemView.findViewById(R.id.imgShare);
        cardView = itemView.findViewById(R.id.itemTouchLuxury);
    }

    public void bind(Luxury luxury, UserActionListener callBack) {
        tvName.setText(luxury.getUsername());
        tvAddress.setText(luxury.getAddress());
        String detail = luxury.getDetail();
        tvDetail.setText(detail);
        Rx2AndroidNetworking.get(luxury.getImage()).setBitmapMaxWidth(830).setBitmapMaxHeight(315).build().getAsBitmap(new BitmapRequestListener() {
            @Override
            public void onResponse(Bitmap response) {
                imgLuxury.setImageBitmap(response);
            }

            @Override
            public void onError(ANError anError) {
                AppLogger.d("imageError", "onError: "+anError);
            }
        });
        imgShare.setOnClickListener(v -> callBack.onActionShareByUser(getAdapterPosition()));
        cardView.setOnClickListener(v -> callBack.onActionViewDetailLuxuryByUser(getAdapterPosition()));
    }

    public static LuxuryViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_luxury,parent,false);
        return new LuxuryViewHolder(view);
    }

    public interface UserActionListener {
        void onActionShareByUser(int position);
        void onActionViewDetailLuxuryByUser(int position);
    }
}
