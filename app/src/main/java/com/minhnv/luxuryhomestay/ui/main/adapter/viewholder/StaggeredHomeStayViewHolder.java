package com.minhnv.luxuryhomestay.ui.main.adapter.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.widget.ANImageView;
import com.google.android.material.card.MaterialCardView;
import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.Homestay;
import com.minhnv.luxuryhomestay.data.model.HomestayPrice;

public class StaggeredHomeStayViewHolder extends RecyclerView.ViewHolder{
    private TextView tvName,tvAddress,tvRating;
    private ANImageView imgView;
    private MaterialCardView cardView;
    private StaggeredHomeStayViewHolder(@NonNull View itemView) {
        super(itemView);
        tvAddress = itemView.findViewById(R.id.tvAddressHomeStayHot);
        tvName = itemView.findViewById(R.id.tvNameHomStayHot);
        imgView = itemView.findViewById(R.id.imgHomeStayHot);
        cardView = itemView.findViewById(R.id.itemTouchHomeStayHot);
        tvRating = itemView.findViewById(R.id.tvRatingDt);
    }
    public void bind(Homestay homestay, UserActionListener callBack){
        imgView.setImageUrl(homestay.getImage());
        imgView.setErrorImageResId(R.drawable.uploadfailed);
        imgView.setDefaultImageResId(R.drawable.img_home1);
        tvName.setText(homestay.getName());
        tvAddress.setText(homestay.getAddress());
        tvRating.setText(homestay.getRating());
        cardView.setOnClickListener(v -> callBack.onActionViewDetailHsPriceByUser(getAdapterPosition()));
    }

    public void bindTo(HomestayPrice homestay, UserActionListener callBack){
        imgView.setImageUrl(homestay.getImage());
        imgView.setErrorImageResId(R.drawable.uploadfailed);
        imgView.setDefaultImageResId(R.drawable.img_home1);
        tvName.setText(homestay.getTitle());
        tvAddress.setText(homestay.getAddress());
        cardView.setOnClickListener(v -> callBack.onActionViewDetailHsPriceByUser(getAdapterPosition()));
    }
    public static StaggeredHomeStayViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_staggeredgridlayout,parent,false);
        return new StaggeredHomeStayViewHolder(view);
    }

    public interface UserActionListener {
        void onActionViewDetailHsPriceByUser(int position);
    }


}