package com.minhnv.luxuryhomestay.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.widget.ANImageView;
import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.Homestay;
import com.minhnv.luxuryhomestay.ui.main.adapter.viewholder.StaggeredHomeStayViewHolder;
import com.minhnv.luxuryhomestay.ui.main.adapter.viewholder.StaggeredPriceViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

public class StaggeredRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Homestay> homeStay;
    private Context context;
    private StaggeredPriceViewHolder.CallBack callBack;

    public StaggeredRecyclerViewAdapter(List<Homestay> homestays, Context context) {
        this.homeStay = homestays;
        this.context = context;
    }
    @Inject
    public StaggeredRecyclerViewAdapter() {
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return StaggeredHomeStayViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof StaggeredHomeStayViewHolder){
            ((StaggeredHomeStayViewHolder) holder).bind(homeStay.get(position),callBack);
        }
    }

    public void setCallBack(StaggeredPriceViewHolder.CallBack callBack){
        this.callBack = callBack;
    }

    @Override
    public int getItemCount() {
        return homeStay == null ? 0 : homeStay.size();
    }


    public void set(List<Homestay> list){
        homeStay.clear();
        homeStay.addAll(list);
        notifyDataSetChanged();
    }
}
