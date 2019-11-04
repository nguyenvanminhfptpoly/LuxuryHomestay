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

import java.util.List;

import javax.inject.Inject;

public class VinHomeDetailAdapter extends RecyclerView.Adapter<VinHomeDetailAdapter.ViewHolder> {
    private List<ListVinHomes> listVinHomes;
    private Context context;
    private RecyclerViewNavigator navigator;

    public VinHomeDetailAdapter(List<ListVinHomes> listVinHomes, Context context,RecyclerViewNavigator navigator) {
        this.listVinHomes = listVinHomes;
        this.context = context;
        this.navigator = navigator;
    }

    @Inject
    public VinHomeDetailAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recyclerview_vinhomes_detail,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(listVinHomes.get(position));
        holder.btnListDetail.setOnClickListener(view -> {navigator.onItemClickListener(position);});
        holder.btnListBooking.setOnClickListener(view -> navigator.onItemClickDetailListener(position));
    }

    @Override
    public int getItemCount() {
        return listVinHomes == null ? 0 : listVinHomes.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ANImageView image;
        TextView tvTitle;
        TextView tvDetail;
        Button btnListDetail, btnListBooking;
        LinearLayout linearLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imgDetailVH);
            tvTitle = itemView.findViewById(R.id.tvNameVH);
            tvDetail = itemView.findViewById(R.id.tvAddressVH);
            btnListDetail = itemView.findViewById(R.id.btnDetailVH);
            btnListBooking = itemView.findViewById(R.id.btnBookingVH);
        }
        void bind(ListVinHomes homes){
            image.setDefaultImageResId(R.drawable.img_home1);
            image.setErrorImageResId(R.drawable.uploadfailed);
            image.setImageUrl(homes.getImage());
            tvTitle.setText(homes.getName());
            tvDetail.setText(homes.getCreateroom());
        }
    }

    public void set(List<ListVinHomes> list){
        listVinHomes.clear();
        listVinHomes.addAll(list);
        notifyDataSetChanged();
    }
}
