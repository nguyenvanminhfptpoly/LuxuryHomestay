package com.minhnv.luxuryhomestay.ui.main.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minhnv.luxuryhomestay.data.model.Homestay;
import com.minhnv.luxuryhomestay.ui.main.adapter.viewholder.CityDetailViewHolder;

import java.util.List;

import javax.inject.Inject;

public class CityDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Homestay> HomeStay;
    private Context context;
    private CityDetailViewHolder.UserActionListener callBack;


    public CityDetailAdapter(List<Homestay> HomeStay, Context context) {
        this.HomeStay = HomeStay;
        this.context = context;
    }

    @Inject
    public CityDetailAdapter() {
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return CityDetailViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof CityDetailViewHolder){
            ((CityDetailViewHolder) holder).bind(HomeStay.get(position),callBack);
        }
    }

    public void setUserAction(CityDetailViewHolder.UserActionListener callBack){
        this.callBack = callBack;
    }


    @Override
    public int getItemCount() {
        return HomeStay == null ? 0 : HomeStay.size();
    }

    public void set(List<Homestay> list){
        HomeStay.clear();
        HomeStay.addAll(list);
        notifyDataSetChanged();
    }
}
