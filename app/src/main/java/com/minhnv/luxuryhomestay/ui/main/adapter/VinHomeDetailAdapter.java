package com.minhnv.luxuryhomestay.ui.main.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minhnv.luxuryhomestay.data.model.ListVinHomes;
import com.minhnv.luxuryhomestay.ui.main.adapter.viewholder.CityDetailViewHolder;
import com.minhnv.luxuryhomestay.ui.main.adapter.viewholder.VinHomeDetailViewHolder;

import java.util.List;

import javax.inject.Inject;

public class VinHomeDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ListVinHomes> listVinHomes;
    private Context context;
    private CityDetailViewHolder.UserActionListener callBack;

    public VinHomeDetailAdapter(List<ListVinHomes> listVinHomes, Context context) {
        this.listVinHomes = listVinHomes;
        this.context = context;
    }

    @Inject
    public VinHomeDetailAdapter() {
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return VinHomeDetailViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof VinHomeDetailViewHolder){
            ((VinHomeDetailViewHolder) holder).bind(listVinHomes.get(position), callBack);
        }
    }

    public void setUserAction(CityDetailViewHolder.UserActionListener callBack){
        this.callBack = callBack;
    }


    @Override
    public int getItemCount() {
        return listVinHomes == null ? 0 : listVinHomes.size();
    }


    public void set(List<ListVinHomes> list){
        listVinHomes.clear();
        listVinHomes.addAll(list);
        notifyDataSetChanged();
    }
}
