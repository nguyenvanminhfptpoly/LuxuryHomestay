package com.minhnv.luxuryhomestay.ui.main.adapter.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.widget.ANImageView;
import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.Luxury;

public class LuxuryViewHolder extends RecyclerView.ViewHolder {
    private TextView tvName, tvAddress;
    private TextView tvDetail,tvCountLove;
    private ANImageView imgLuxury;
    ImageView imgFavorite;
    ImageButton imgShare;
    private Button btnDetailLuxury;

    private LuxuryViewHolder(@NonNull View itemView) {
        super(itemView);
        tvName = itemView.findViewById(R.id.tvNameUser);
        tvAddress = itemView.findViewById(R.id.tvAddressUser);
        tvDetail = itemView.findViewById(R.id.tvDetailLuxury);
        imgFavorite = itemView.findViewById(R.id.imgFavoriteLuxury);
        imgLuxury = itemView.findViewById(R.id.imgLuxury);
        imgShare = itemView.findViewById(R.id.imgShare);
        btnDetailLuxury = itemView.findViewById(R.id.btnDetailLuxury);
    }

    public void bind(Luxury luxury,CallBack callBack) {
        tvName.setText(luxury.getUsername());
        tvAddress.setText(luxury.getAddress());
        String detail = luxury.getDetail();
        tvDetail.setText(detail);
        imgLuxury.setDefaultImageResId(R.drawable.img_home1);
        imgLuxury.setErrorImageResId(R.drawable.uploadfailed);
        imgLuxury.setImageUrl(luxury.getImage());
        imgShare.setOnClickListener(v -> callBack.continueShare(getAdapterPosition()));
        btnDetailLuxury.setOnClickListener(v -> callBack.viewDetail(getAdapterPosition()));
    }

    public static LuxuryViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_luxury,parent,false);
        return new LuxuryViewHolder(view);
    }

    public interface CallBack{
        void continueShare(int position);
        void viewDetail(int position);
    }
}
