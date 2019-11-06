package com.minhnv.luxuryhomestay.ui.main.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minhnv.luxuryhomestay.data.model.HomestayPrice;
import com.minhnv.luxuryhomestay.ui.main.adapter.viewholder.StaggeredPriceViewHolder;

import java.util.List;

import javax.inject.Inject;

public class StaggeredAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<HomestayPrice> homeStay;
    private Context context;
    private StaggeredPriceViewHolder.UserActionListener callBack;

    public StaggeredAdapter(List<HomestayPrice> homestays, Context context) {
        this.homeStay = homestays;
        this.context = context;
    }

    @Inject
    public StaggeredAdapter() {
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return StaggeredPriceViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof StaggeredPriceViewHolder){
            ((StaggeredPriceViewHolder) holder).bind(homeStay.get(position),callBack);
        }
    }

    public void setUserAction(StaggeredPriceViewHolder.UserActionListener callBack){
        this.callBack = callBack;
    }

    @Override
    public int getItemCount() {
        return homeStay == null ? 0 : homeStay.size();
    }


    public void set(List<HomestayPrice> list){
        homeStay.clear();
        homeStay.addAll(list);
        notifyDataSetChanged();
    }
}
