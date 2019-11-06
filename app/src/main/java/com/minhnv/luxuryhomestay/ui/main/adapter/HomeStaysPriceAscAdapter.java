package com.minhnv.luxuryhomestay.ui.main.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
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
import com.minhnv.luxuryhomestay.data.model.HomestayPrice;
import com.minhnv.luxuryhomestay.ui.main.adapter.viewholder.CityDetailViewHolder;
import com.minhnv.luxuryhomestay.ui.main.adapter.viewholder.HomeStayPriceViewHolder;
import com.minhnv.luxuryhomestay.utils.CommonUtils;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import javax.inject.Inject;

public class HomeStaysPriceAscAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<HomestayPrice> homeStays;
    private Context context;
    private HomeStayPriceViewHolder.CallBack callBack;
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

    public void setCallBack(HomeStayPriceViewHolder.CallBack callBack){
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
    }
}
