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
import com.minhnv.luxuryhomestay.data.model.ListVinHomes;
import com.minhnv.luxuryhomestay.ui.main.adapter.viewholder.CityDetailViewHolder;
import com.minhnv.luxuryhomestay.ui.main.adapter.viewholder.VinHomeDetailViewHolder;

import java.util.List;

import javax.inject.Inject;

public class VinHomeDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ListVinHomes> listVinHomes;
    private Context context;
    private CityDetailViewHolder.CallBack callBack;

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

    public void setCallBack(CityDetailViewHolder.CallBack callBack){
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
