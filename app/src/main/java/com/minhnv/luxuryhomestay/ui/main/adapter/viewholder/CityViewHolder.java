package com.minhnv.luxuryhomestay.ui.main.adapter.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.widget.ANImageView;
import com.elyeproj.loaderviewlibrary.LoaderTextView;
import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.City;

public class CityViewHolder extends RecyclerView.ViewHolder {
    private ANImageView imageView;
    private LoaderTextView tvName;

    private CityViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imgCity);
        tvName = itemView.findViewById(R.id.tvNameCity);
    }
    public void bind(City city, UserActionListener callBack) {
        imageView.setErrorImageResId(R.drawable.uploadfailed);
        imageView.setDefaultImageResId(R.drawable.img_home1);
        imageView.setImageUrl(city.getImage());
        tvName.setText(city.getName());
        imageView.setOnClickListener(v -> callBack.onActionViewDetailByUser(getAdapterPosition()));
    }

    public static CityViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city_recyclerview,parent,false);
        return new CityViewHolder(view);
    }

    public interface UserActionListener {
        void onActionViewDetailByUser(int position);
    }
}
