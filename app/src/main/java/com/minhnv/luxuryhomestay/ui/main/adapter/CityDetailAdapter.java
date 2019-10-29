package com.minhnv.luxuryhomestay.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.widget.ANImageView;
import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.Homestay;

import java.util.List;

public class CityDetailAdapter extends RecyclerView.Adapter<CityDetailAdapter.ViewHolder> {
    private List<Homestay> HomeStay;
    private Context context;
    private RecyclerViewNavigator listener;


    public CityDetailAdapter(List<Homestay> HomeStay, Context context, RecyclerViewNavigator listener) {
        this.HomeStay = HomeStay;
        this.context = context;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_city_detail_recyclerview, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(HomeStay != null) {
            holder.bind(HomeStay.get(position));
            holder.btnListDetail.setOnClickListener(view -> {
                listener.onItemClickListener(position);
            });
            holder.btnListBooking.setOnClickListener(view -> {
                listener.onItemClickDetailListener(position);
            });
        }
    }

    @Override
    public int getItemCount() {
        return HomeStay == null ? 0 : HomeStay.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ANImageView image;
        TextView tvTitle;
        TextView tvDetail;
        Button btnListDetail, btnListBooking;
        LinearLayout linearLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imgCityDetail);
            tvTitle = itemView.findViewById(R.id.tvTitleCityDetail);
            tvDetail = itemView.findViewById(R.id.tvDetailCity);
            btnListBooking = itemView.findViewById(R.id.btnListCityBooking);
            btnListDetail = itemView.findViewById(R.id.btnListCityDetail);
        }

        void bind(Homestay homestay) {
            tvTitle.setText(homestay.getName());
            tvDetail.setText(homestay.getEvalute());
            image.setErrorImageResId(R.drawable.uploadfailed);
            image.setDefaultImageResId(R.drawable.img_home1);
            image.setImageUrl(homestay.getImage());
        }
    }
}