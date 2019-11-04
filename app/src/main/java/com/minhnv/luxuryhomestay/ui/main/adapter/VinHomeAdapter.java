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

import java.util.List;

import javax.inject.Inject;

public class VinHomeAdapter extends RecyclerView.Adapter<VinHomeAdapter.ViewHolder> {
    private List<VinHome> vinHomes;
    private Context context;
    private RecyclerViewNavigator navigator;

    public VinHomeAdapter(List<VinHome> vinHomes, Context context, RecyclerViewNavigator navigator) {
        this.vinHomes = vinHomes;
        this.context = context;
        this.navigator = navigator;
    }
    @Inject
    public VinHomeAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recyclerview_vinhomes,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(vinHomes.get(position));
        holder.view.setOnClickListener(view -> {
            navigator.onItemClickListener(position);});
    }

    @Override
    public int getItemCount() {
        return vinHomes == null ? 0 : vinHomes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
    private ANImageView imgCity;
    private TextView tvName;
    private CardView view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCity = itemView.findViewById(R.id.imgVinHomes);
            tvName = itemView.findViewById(R.id.tvNameVinHomes);
            view = itemView.findViewById(R.id.cardViewVinHomes);
        }
        public void bind(VinHome home){
            imgCity.setDefaultImageResId(R.drawable.img_home1);
            imgCity.setErrorImageResId(R.drawable.uploadfailed);
            imgCity.setImageUrl(home.getImage());
            tvName.setText(home.getName());
        }
    }

    public void set(List<VinHome> list){
        vinHomes.clear();
        vinHomes.addAll(list);
        notifyDataSetChanged();
    }
}
