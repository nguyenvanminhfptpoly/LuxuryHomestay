package com.minhnv.luxuryhomestay.ui.main.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minhnv.luxuryhomestay.data.model.City;
import com.minhnv.luxuryhomestay.ui.main.adapter.viewholder.CityViewHolder;

import java.util.List;

import javax.inject.Inject;

public class CityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public Context context;
    private List<City> cities;
    private CityViewHolder.UserActionListener callBack;

    @Inject
    public CityAdapter() {
    }

    public CityAdapter(Context context, List<City> cities) {
        this.context = context;
        this.cities = cities;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return CityViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof CityViewHolder){
            ((CityViewHolder) holder).bind(cities.get(position),callBack);
        }
    }

    public void setUserAction(CityViewHolder.UserActionListener callBack){
        this.callBack = callBack;
    }
    @Override
    public int getItemCount() {
        return cities == null ? 0 : cities.size();
    }

    public void set(List<City> list){
        cities.clear();
        cities.addAll(list);
        notifyDataSetChanged();
    }


}
