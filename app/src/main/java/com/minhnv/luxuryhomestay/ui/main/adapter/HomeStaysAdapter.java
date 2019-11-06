package com.minhnv.luxuryhomestay.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.widget.ANImageView;
import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.Homestay;
import com.minhnv.luxuryhomestay.ui.main.adapter.viewholder.CityDetailViewHolder;
import com.minhnv.luxuryhomestay.ui.main.adapter.viewholder.HomeStayViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class HomeStaysAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Homestay> HomeStay;
    private Context context;
    private CityDetailViewHolder.CallBack callBack;

    @Inject
    public HomeStaysAdapter() {
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return HomeStayViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof HomeStayViewHolder){
            ((HomeStayViewHolder) holder).bind(HomeStay.get(position),callBack);
        }
    }

    public HomeStaysAdapter(List<Homestay> HomeStay, Context context) {
        this.HomeStay = HomeStay;
        this.context = context;

    }

    public void setCallBack(CityDetailViewHolder.CallBack callBack){
        this.callBack = callBack;
    }



    public void set(List<Homestay> list){
        HomeStay.clear();
        HomeStay.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return HomeStay == null ? 0 : HomeStay.size();
    }


}
