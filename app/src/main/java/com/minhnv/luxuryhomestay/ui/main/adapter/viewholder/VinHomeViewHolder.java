package com.minhnv.luxuryhomestay.ui.main.adapter.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.widget.ANImageView;
import com.google.android.material.card.MaterialCardView;
import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.VinHome;

public  class VinHomeViewHolder extends RecyclerView.ViewHolder{
    private ANImageView imgCity;
    private TextView tvName;
    private MaterialCardView view;
    private VinHomeViewHolder(@NonNull View itemView) {
        super(itemView);
        imgCity = itemView.findViewById(R.id.imgVinHomes);
        tvName = itemView.findViewById(R.id.tvNameVinHomes);
        view = itemView.findViewById(R.id.cardViewVinHomes);
    }
    public void bind(VinHome home, UserActionListener callBack){
        imgCity.setDefaultImageResId(R.drawable.img_home1);
        imgCity.setErrorImageResId(R.drawable.uploadfailed);
        imgCity.setImageUrl(home.getImage());
        tvName.setText(home.getName());

        view.setOnClickListener(v -> callBack.onActionViewVinHomeDetailByUser(getAdapterPosition()));
    }
    public static VinHomeViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_vinhomes,parent,false);
        return new VinHomeViewHolder(view);
    }

    public interface UserActionListener {
        void onActionViewVinHomeDetailByUser(int position);
    }
}
