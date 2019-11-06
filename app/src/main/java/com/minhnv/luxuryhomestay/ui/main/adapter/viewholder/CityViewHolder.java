package com.minhnv.luxuryhomestay.ui.main.adapter.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.widget.ANImageView;
import com.elyeproj.loaderviewlibrary.LoaderTextView;
import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.City;

public class CityViewHolder extends RecyclerView.ViewHolder {
    ANImageView imageView;
    LoaderTextView tvName;
    ConstraintLayout item_city;
    Context context;

    private CityViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imgCity);
        tvName = itemView.findViewById(R.id.tvNameCity);
        item_city = itemView.findViewById(R.id.item_city);
    }
    public void bind(City city,CallBack callBack) {
        imageView.setErrorImageResId(R.drawable.uploadfailed);
        imageView.setDefaultImageResId(R.drawable.img_home1);
        imageView.setImageUrl(city.getImage());
        tvName.setText(city.getName());
        imageView.setOnClickListener(v -> callBack.viewCityDetail(getAdapterPosition()));
    }

    public static CityViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city_recyclerview,parent,false);
        return new CityViewHolder(view);
    }

    public interface CallBack{
        void viewCityDetail(int position);
    }
}
