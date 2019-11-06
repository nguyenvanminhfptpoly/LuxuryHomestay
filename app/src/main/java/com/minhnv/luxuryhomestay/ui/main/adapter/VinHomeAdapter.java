package com.minhnv.luxuryhomestay.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.widget.ANImageView;
import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.VinHome;
import com.minhnv.luxuryhomestay.ui.main.adapter.viewholder.CityViewHolder;
import com.minhnv.luxuryhomestay.ui.main.adapter.viewholder.VinHomeViewHolder;

import java.util.List;

import javax.inject.Inject;

public class VinHomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<VinHome> vinHomes;
    private Context context;
    private VinHomeViewHolder.CallBack callBack;

    public VinHomeAdapter(List<VinHome> vinHomes, Context context) {
        this.vinHomes = vinHomes;
        this.context = context;
    }
    @Inject
    public VinHomeAdapter() {
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return VinHomeViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof VinHomeViewHolder){
            ((VinHomeViewHolder) holder).bind(vinHomes.get(position),callBack);
        }
    }

    public void setCallBack(VinHomeViewHolder.CallBack callBack){
        this.callBack = callBack;
    }


    @Override
    public int getItemCount() {
        return vinHomes == null ? 0 : vinHomes.size();
    }



    public void set(List<VinHome> list){
        vinHomes.clear();
        vinHomes.addAll(list);
        notifyDataSetChanged();
    }
}
