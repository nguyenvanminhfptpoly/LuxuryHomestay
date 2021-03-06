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
import com.minhnv.luxuryhomestay.data.model.ListVinHomes;

public class VinHomeDetailViewHolder extends RecyclerView.ViewHolder {
    private ANImageView image;
    private TextView tvTitle;
    private TextView tvDetail;
    private TextView tvRatingVh;
    private MaterialCardView cardView;

    private VinHomeDetailViewHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.imgDetailVH);
        tvTitle = itemView.findViewById(R.id.tvNameVH);
        tvDetail = itemView.findViewById(R.id.tvAddressVH);
        cardView = itemView.findViewById(R.id.itemTouchVinHomes);
        tvRatingVh = itemView.findViewById(R.id.tvRatingVH);
    }
    public void bind(ListVinHomes homes, CityDetailViewHolder.UserActionListener callBack){
        image.setDefaultImageResId(R.drawable.img_home1);
        image.setErrorImageResId(R.drawable.uploadfailed);
        image.setImageUrl(homes.getImage());
        tvTitle.setText(homes.getName());
        tvDetail.setText(homes.getCreateroom());
        tvRatingVh.setText(homes.getRating());

        cardView.setOnClickListener(v -> callBack.onActionDetailByUser(getAdapterPosition()));
    }

    public static VinHomeDetailViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_vinhomes_detail,parent,false);
        return new VinHomeDetailViewHolder(view);
    }
}
