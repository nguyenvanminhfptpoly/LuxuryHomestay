package com.minhnv.luxuryhomestay.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.widget.ANImageView;
import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.Story;
import com.minhnv.luxuryhomestay.ui.main.adapter.viewholder.SocialViewHolder;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class SocialAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Story> stories;
    private Context context;
    private SocialViewHolder.CallBack callBack;

    public SocialAdapter(List<Story> luxuries, Context context) {
        this.stories = luxuries;
        this.context = context;
    }

    @Inject
    public SocialAdapter() {
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return SocialViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof SocialViewHolder){
            ((SocialViewHolder) holder).bind(stories.get(position),callBack);
        }
    }

    public void setCallBack(SocialViewHolder.CallBack callBack){
        this.callBack = callBack;
    }

    public void set(List<Story> list){
        stories.clear();
        stories.addAll(list);
        notifyDataSetChanged();
        Collections.reverse(stories);
    }

    @Override
    public int getItemCount() {
        return stories == null ? 0 : stories.size();
    }

}
