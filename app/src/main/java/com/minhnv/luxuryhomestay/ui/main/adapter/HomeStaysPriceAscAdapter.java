package com.minhnv.luxuryhomestay.ui.main.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minhnv.luxuryhomestay.data.model.HomestayPrice;
import com.minhnv.luxuryhomestay.ui.main.adapter.viewholder.HomeStayPriceViewHolder;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class HomeStaysPriceAscAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<HomestayPrice> homeStays;
    private Context context;
    private HomeStayPriceViewHolder.UserActionListener callBack;
    public HomeStaysPriceAscAdapter(List<HomestayPrice> homestays, Context context) {
        this.homeStays = homestays;
        this.context = context;
    }
    @Inject
    public HomeStaysPriceAscAdapter() {
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return HomeStayPriceViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof HomeStayPriceViewHolder){
            ((HomeStayPriceViewHolder) holder).bind(homeStays.get(position), callBack);
        }
    }

    public void setUserAction(HomeStayPriceViewHolder.UserActionListener callBack){
        this.callBack = callBack;
    }


    @Override
    public int getItemCount() {
        return homeStays == null ? 0 : homeStays.size();
    }



    public void set(List<HomestayPrice> list){
        homeStays.clear();
        homeStays.addAll(list);
        notifyDataSetChanged();
        Collections.reverse(homeStays);
    }
}
