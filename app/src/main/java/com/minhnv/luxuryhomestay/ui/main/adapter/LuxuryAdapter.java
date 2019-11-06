package com.minhnv.luxuryhomestay.ui.main.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minhnv.luxuryhomestay.data.model.Luxury;
import com.minhnv.luxuryhomestay.ui.main.adapter.viewholder.LuxuryViewHolder;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class LuxuryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Luxury> luxuries;
    private Context context;
    private LuxuryViewHolder.UserActionListener callBack;
    public long mLastClickTime = 0;

    public LuxuryAdapter(List<Luxury> luxuries, Context context) {
        this.luxuries = luxuries;
        this.context = context;
    }

    @Inject
    public LuxuryAdapter() {
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return LuxuryViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof LuxuryViewHolder){
            ((LuxuryViewHolder) holder).bind(luxuries.get(position),callBack);
        }
    }

    public void setUserAction(LuxuryViewHolder.UserActionListener callBack){
        this.callBack = callBack;
    }


    @Override
    public int getItemCount() {
        return luxuries == null ? 0 : luxuries.size();
    }

    public void set(List<Luxury> list){
        luxuries.clear();
        luxuries.addAll(list);
        notifyDataSetChanged();
        Collections.reverse(luxuries);
    }

}
