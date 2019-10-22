package com.minhnv.luxuryhomestay.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.widget.ANImageView;
import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.City;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {
    public Context context;
    private List<City> cities;
    private RecyclerViewNavigator onItemClick;

    public CityAdapter(Context context, List<City> cities, RecyclerViewNavigator onItemClick) {
        this.context = context;
        this.cities = cities;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_city_recyclerview, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (cities != null) {
            holder.bind(cities.get(position));
            holder.item_city.setOnClickListener(view -> {
                onItemClick.onItemClickListener(position);
            });
        }
    }

    @Override
    public int getItemCount() {
        return cities == null ? 0 : cities.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ANImageView imageView;
        TextView tvName;
        ConstraintLayout item_city;
        Context context;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgCity);
            tvName = itemView.findViewById(R.id.tvNameCity);
            item_city = itemView.findViewById(R.id.item_city);
        }

        void bind(City city) {
            imageView.setErrorImageResId(R.drawable.uploadfailed);
            imageView.setDefaultImageResId(R.drawable.img_home1);
            imageView.setImageUrl(city.getImage());
            tvName.setText(city.getName());
        }
    }
}
