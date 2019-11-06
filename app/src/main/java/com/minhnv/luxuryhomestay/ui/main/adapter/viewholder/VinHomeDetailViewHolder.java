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
import com.minhnv.luxuryhomestay.data.model.ListVinHomes;

public class VinHomeDetailViewHolder extends RecyclerView.ViewHolder {
    private ANImageView image;
    private TextView tvTitle;
    private TextView tvDetail;
    private Button btnListDetail, btnListBooking;
    private LinearLayout linearLayout;

    private VinHomeDetailViewHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.imgDetailVH);
        tvTitle = itemView.findViewById(R.id.tvNameVH);
        tvDetail = itemView.findViewById(R.id.tvAddressVH);
        btnListDetail = itemView.findViewById(R.id.btnDetailVH);
        btnListBooking = itemView.findViewById(R.id.btnBookingVH);
    }
    public void bind(ListVinHomes homes, CityDetailViewHolder.CallBack callBack){
        image.setDefaultImageResId(R.drawable.img_home1);
        image.setErrorImageResId(R.drawable.uploadfailed);
        image.setImageUrl(homes.getImage());
        tvTitle.setText(homes.getName());
        tvDetail.setText(homes.getCreateroom());

        btnListBooking.setOnClickListener(v -> callBack.openBooking(getAdapterPosition()));
        btnListDetail.setOnClickListener(v -> callBack.openDetail(getAdapterPosition()));
    }

    public static VinHomeDetailViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_vinhomes_detail,parent,false);
        return new VinHomeDetailViewHolder(view);
    }
}
