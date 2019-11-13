package com.minhnv.luxuryhomestay.ui.main.adapter.viewholder;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.BitmapRequestListener;
import com.androidnetworking.widget.ANImageView;
import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.Story;
import com.minhnv.luxuryhomestay.utils.AppLogger;
import com.rx2androidnetworking.Rx2AndroidNetworking;

public  class SocialViewHolder extends RecyclerView.ViewHolder{
    private CardView view;
    private ImageView imgSocial;
    private SocialViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView.findViewById(R.id.cardSocial);
        imgSocial = itemView.findViewById(R.id.imgSocial);
    }
    public void bind(Story luxury, UserActionListener callBack){
        Rx2AndroidNetworking.get(luxury.getImage()).setBitmapMaxWidth(830).setBitmapMaxHeight(315).build().getAsBitmap(new BitmapRequestListener() {
            @Override
            public void onResponse(Bitmap response) {
                imgSocial.setImageBitmap(response);
            }

            @Override
            public void onError(ANError anError) {
                AppLogger.d("imageError", "onError: "+anError);
            }
        });

        imgSocial.setOnClickListener(v -> callBack.onActionViewStoryDetailByUser(getAdapterPosition()));
    }
    public interface UserActionListener {
        void onActionViewStoryDetailByUser(int position);
    }

    public static SocialViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_social_home,parent,false);
        return new SocialViewHolder(view);
    }
}
